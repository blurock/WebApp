package info.esblurock.reaction.client.panel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialTitle;
import info.esblurock.reaction.client.resources.InterfaceConstants;
import info.esblurock.reaction.client.resources.info.InformationTexts;
import info.esblurock.reaction.client.ui.ReactionTopImpl;

public class TopInformationModal extends Composite implements HasText {

	private static TopInformationModalUiBinder uiBinder = GWT.create(TopInformationModalUiBinder.class);

	interface TopInformationModalUiBinder extends UiBinder<Widget, TopInformationModal> {
	}

	InterfaceConstants interfaceconstants = GWT.create(InterfaceConstants.class);
	InformationTexts informationtexts = GWT.create(InformationTexts.class);
	
	@UiField
	MaterialTitle infotitle;
	
	@UiField
	MaterialLink title1;
	@UiField
	HorizontalPanel info1;
	@UiField
	MaterialLink title2;
	@UiField
	HorizontalPanel info2;
	@UiField
	MaterialLink title3;
	@UiField
	HorizontalPanel info3;
	@UiField
	MaterialLink title4;
	@UiField
	HorizontalPanel info4;
	@UiField
	MaterialModal infomodal;
	@UiField
	MaterialButton infoclose;
	@UiField
	MaterialButton infopagebutton;
	
	ReactionTopImpl toppanel;

	private void init() {
		infotitle.setTitle(interfaceconstants.topinfotitle());
		infotitle.setDescription("Very brief explanations of how to use ChemConnect");
		HTML htmlinfo1 = new HTML(informationtexts.topsearchgeneral().getText());
		HTML htmlinfo2 = new HTML(informationtexts.topsearchlogin().getText());
		HTML htmlinfo3 = new HTML(informationtexts.topsearchregister().getText());
		HTMLPanel htmlinfo4 = new HTMLPanel(informationtexts.topsearchsearching().getText());
		info1.add(htmlinfo1);
		info2.add(htmlinfo2);
		info3.add(htmlinfo3);
		info4.add(htmlinfo4);
		title1.setText(interfaceconstants.topsearchgeneralinformation());
		title2.setText(interfaceconstants.topsearchlogin());
		title3.setText(interfaceconstants.topsearchregister());
		title4.setText(interfaceconstants.topsearchsearching());
	}

	public TopInformationModal(ReactionTopImpl toppanel) {
		initWidget(uiBinder.createAndBindUi(this));
		this.toppanel = toppanel;
		init();
	}

	public TopInformationModal(String title) {
		initWidget(uiBinder.createAndBindUi(this));
		infotitle.setTitle(title);
		init();
	}

	public void setText(String title) {
		infotitle.setTitle(title);
	}

	public String getText() {
		return null;
		//return infotitle.getTitle();
	}
	public void openModal() {
		infomodal.openModal();
	}
	@UiHandler("infopagebutton")
	void onDemoClick(ClickEvent event) {
		toppanel.openDemoInformationPage();
	}
	@UiHandler("infoclose")
	void onCloseClick(ClickEvent event) {
		infomodal.closeModal();
	}
}
