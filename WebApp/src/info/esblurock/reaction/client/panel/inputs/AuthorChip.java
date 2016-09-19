package info.esblurock.reaction.client.panel.inputs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialChip;
import gwt.material.design.client.ui.MaterialToast;

public class AuthorChip extends Composite implements HasText {

	private static AuthorChipUiBinder uiBinder = GWT.create(AuthorChipUiBinder.class);

	interface AuthorChipUiBinder extends UiBinder<Widget, AuthorChip> {
	}

	@UiField
	MaterialChip author;
	
	String name;
	String lastName;
	
	public AuthorChip() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public AuthorChip(String name, String lastName) {
		initWidget(uiBinder.createAndBindUi(this));
		this.name = name;
		this.lastName = lastName;
		String fullname = null;
		if(name != null) {
			if(lastName != null) {
				fullname = name + " " + lastName;
			} else {
				fullname = name;
			}
		} else {
			if(lastName != null) {
				fullname = lastName;
			}
		}
		if(fullname != null) {
			author.setText(fullname);
			author.getIcon().addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					MaterialToast.fireToast(author.getText());
				}
			});
		}
	}

	public void setText(String text) {
		author.setText(text);
	}

	public String getText() {
		return author.getText();
	}

	public String getName() {
		return name;
	}

	public String getLastName() {
		return lastName;
	}

}
