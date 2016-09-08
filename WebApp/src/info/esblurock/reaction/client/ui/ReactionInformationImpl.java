package info.esblurock.reaction.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import info.esblurock.reaction.client.ui.ReactionFirstView.Presenter;

public class ReactionInformationImpl extends Composite implements ReactionInformationView {

	private static ReactionInformationImplUiBinder uiBinder = GWT.create(ReactionInformationImplUiBinder.class);

	interface ReactionInformationImplUiBinder extends UiBinder<Widget, ReactionInformationImpl> {
	}
	Presenter listener;

	public ReactionInformationImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public ReactionInformationImpl(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}


	public void setText(String text) {
	}

	public String getText() {
		return "Information";
	}

	@Override
	public void setName(String helloName) {
	}

	@Override
	public void setPresenter(Presenter listener) {
		this.listener = listener;
	}

}
