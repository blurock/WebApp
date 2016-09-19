package info.esblurock.reaction.client.panel.description;

import java.util.HashSet;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.http.client.Header;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.ibm.icu.util.BytesTrie.Iterator;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialIcon;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.panel.inputs.AuthorChip;

public class ReferenceDescriptions extends Composite implements HasText {

	private static ReferenceDescriptionsUiBinder uiBinder = GWT.create(ReferenceDescriptionsUiBinder.class);

	interface ReferenceDescriptionsUiBinder extends UiBinder<Widget, ReferenceDescriptions> {
	}

	@UiField
	MaterialButton doilookupbutton;
	@UiField
	MaterialTextBox doi;
	@UiField
	MaterialTextBox title;
	@UiField
	MaterialChip addauthor;
	@UiField
	MaterialIcon delete;
	@UiField
	MaterialLink objecttitle;
	@UiField
	MaterialTextBox referenceText;

	@UiField
	MaterialModal authorModal;
	@UiField
	MaterialTextBox nameText;
	@UiField
	MaterialTextBox lastNameText;
	@UiField
	MaterialButton newAuthorButton;
	@UiField
	MaterialButton authorCloseButton;
	@UiField
	HTMLPanel authorpanel;

	public ReferenceDescriptions() {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	public ReferenceDescriptions(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		init();
	}

	private void init() {
		doilookupbutton.setText("Look up DOI (using CrossRef)");
		doi.setText("10.1016/j.energy.2012.01.072");
		doi.setPlaceholder("Enter DOI (of the form 10.1016/j.energy.2012.01.072)");
		title.setText("title");
		title.setPlaceholder("title of article (can be filled in by DOI)");
		referenceText.setText("reference");
		referenceText.setPlaceholder("reference of article (free form)");
		objecttitle.setText("Reference");
	}

	public void doPost(String doi) throws RequestException {
		String url = "http://api.crossref.org/works/" + doi;
		RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);

		builder.sendRequest(null, new RequestCallback() {

			@Override
			public void onResponseReceived(com.google.gwt.http.client.Request request, Response response) {
				if (response.getStatusText().matches("OK")) {
					JSONObject obj = JSONParser.parseStrict(response.getText()).isObject();
					JSONValue messageV = obj.get("message");
					if (messageV != null) {
						JSONObject message = messageV.isObject();
						JSONValue authorsV = message.get("author");
						if (authorsV != null) {
							JSONArray authors = authorsV.isArray();
							for (int i = 0; i < authors.size(); i++) {
								JSONObject author = authors.get(i).isObject();
								String name = null;
								JSONValue nameV = author.get("given");
								if (nameV != null) {
									name = nameV.isString().stringValue();
								}
								String last = null;
								JSONValue lastV = author.get("family");
								if (lastV != null) {
									last = lastV.isString().stringValue();
								}
								AuthorChip chip = new AuthorChip(name, last);
								authorpanel.add(chip);
							}
						} else {
							MaterialToast.fireToast("No authors specified");
						}
						JSONValue titlesV = message.get("title");
						if (titlesV != null) {
							JSONArray titles = titlesV.isArray();
							if (titles.size() > 0) {
								String jsontitle = titles.get(0).isString().stringValue();
								title.setText(jsontitle);
							} else {
								MaterialToast.fireToast("No Title");
							}
						} else {
							MaterialToast.fireToast("No Title");
						}
					} else {
						MaterialToast.fireToast("No Message found in JSON object");
					}
				}
			}

			@Override
			public void onError(com.google.gwt.http.client.Request request, Throwable exception) {
				Window.alert(exception.toString());
			}

		});

	}

	@UiHandler("doilookupbutton")
	public void onDOILookup(ClickEvent event) {
		String doiS = doi.getText();
		try {
			doPost(doiS);
		} catch (RequestException e) {
			Window.alert(e.toString());
		}
	}

	@UiHandler("addauthor")
	public void onAddAuthor(ClickEvent event) {
		MaterialToast.fireToast("Add Author");
		authorModal.openModal();
	}

	@UiHandler("delete")
	public void onDeleteReference(ClickEvent event) {
		this.removeFromParent();
	}

	@UiHandler("newAuthorButton")
	void onNewKeywordButton(ClickEvent event) {
		MaterialToast.fireToast("New Author: " + nameText.getText() + " " + lastNameText.getText());
		AuthorChip chip = new AuthorChip(nameText.getText(), lastNameText.getText());
		authorpanel.add(chip);
	}

	@UiHandler("authorCloseButton")
	void onKeywordCloseButton(ClickEvent event) {
		authorModal.closeModal();
	}

	public void setText(String text) {
		doi.setText(text);
	}

	public String getText() {
		return doi.getText();
	}
	public String getReference() {
		return referenceText.getText();
	}
	public String getDOI() {
		return doi.getText();
	}
	public String getTitString() {
		return title.getText();
	}
	public HashSet<String> getAuthors() {
		HashSet<String> names = new HashSet<String>();
		for(Widget widget : authorpanel) {
			if(widget.getClass().getSimpleName().matches("AuthorChip")) {
				AuthorChip author = (AuthorChip) widget;
				String name = author.getName();
				String lastname = author.getLastName();
				String fullname = name + " " + lastname;
				names.add(fullname);
			}
		}
		return names;
	}
	public HashSet<String> getLastNames() {
		HashSet<String> lastnames = new HashSet<String>();
		for(Widget widget : authorpanel) {
			if(widget.getClass().getSimpleName().matches("AuthorChip")) {
				AuthorChip author = (AuthorChip) widget;
				lastnames.add(author.getLastName());
			}
		}
		return lastnames;
	}

}
