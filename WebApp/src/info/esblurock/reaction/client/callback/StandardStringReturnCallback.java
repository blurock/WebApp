package info.esblurock.reaction.client.callback;

import gwt.material.design.client.constants.ModalType;
import info.esblurock.reaction.client.panel.modal.TextMessagePopup;
import info.esblurock.reaction.client.resources.InterfaceConstants;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StandardStringReturnCallback   implements AsyncCallback<String> {

	InterfaceConstants constants = GWT.create(InterfaceConstants.class);
	
	String errorTitle = constants.errortitle();
	String successTitle = constants.successtitle();
	String prefix;
	
	public StandardStringReturnCallback(String messageprefix) {
		prefix = messageprefix;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
		TextMessagePopup popup = new TextMessagePopup(errorTitle,
				caught.toString());
		popup.openModal(ModalType.FIXED_FOOTER);
	}

	@Override
	public void onSuccess(String result) {
		String message = prefix + result;
		TextMessagePopup popup = new TextMessagePopup(successTitle,
				message);
		popup.openModal(ModalType.FIXED_FOOTER);
	}

	public String getErrorTitle() {
		return errorTitle;
	}

	public void setErrorTitle(String errorTitle) {
		this.errorTitle = errorTitle;
	}

	public String getSuccessTitle() {
		return successTitle;
	}

	public void setSuccessTitle(String successTitle) {
		this.successTitle = successTitle;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

}
