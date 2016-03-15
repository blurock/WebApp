package info.esblurock.reaction.client.panel.data;

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

import info.esblurock.reaction.client.panel.query.objects.ObjectQueryResult;

public class BaseDataPresentation extends Composite implements HasText {

	private static BaseDataPresentationUiBinder uiBinder = GWT.create(BaseDataPresentationUiBinder.class);

	interface BaseDataPresentationUiBinder extends UiBinder<Widget, BaseDataPresentation> {
	}

	
	String title; 
	
	public BaseDataPresentation(String title) {
		initWidget(uiBinder.createAndBindUi(this));
		this.title = title;
	}

	@Override
	public String getText() {
		return title;
	}

	@Override
	public void setText(String text) {
		title = text;
	}

}
