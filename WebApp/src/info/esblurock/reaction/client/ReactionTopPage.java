package info.esblurock.reaction.client;

import gwt.material.design.client.ui.MaterialParallax;
import gwt.material.design.client.ui.MaterialToast;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ReactionTopPage extends Composite implements HasText {

	private static ReactionTopPageUiBinder uiBinder = GWT
			.create(ReactionTopPageUiBinder.class);

	interface ReactionTopPageUiBinder extends UiBinder<Widget, ReactionTopPage> {
	}

	public ReactionTopPage() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button button;

	public ReactionTopPage(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		MaterialToast.fireToast("Hello!");
	}

	@Override
	public void setText(String text) {
		button.setText(text);
	}

	@Override
	public String getText() {
		return button.getText();
	}

}
