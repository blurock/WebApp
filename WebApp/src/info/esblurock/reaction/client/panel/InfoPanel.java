package info.esblurock.reaction.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTitle;

public class InfoPanel extends Composite implements HasText {

	private static InfoPanelUiBinder uiBinder = GWT.create(InfoPanelUiBinder.class);

	interface InfoPanelUiBinder extends UiBinder<Widget, InfoPanel> {
	}

	public InfoPanel() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	HorizontalPanel information;
	@UiField
	MaterialTitle infotitle;
	@UiField
	MaterialButton close;
	@UiField
	MaterialModal infomodal;

	public InfoPanel(String title, String description, String info) {
		initWidget(uiBinder.createAndBindUi(this));
		infotitle.setTitle(title);
		infotitle.setDescription(description);
		HTML html = new HTML(info);
		information.add(html);
	}

	@UiHandler("close")
	public void close(ClickEvent event) {
		infomodal.closeModal();
	}
	public void showModal() {
		infomodal.openModal();
	}
	public void setText(String title) {
		infotitle.setTitle(title);
	}

	public String getText() {
		return infotitle.getTitle();
	}

}
