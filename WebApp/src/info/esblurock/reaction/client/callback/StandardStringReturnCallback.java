package info.esblurock.reaction.client.callback;

import gwt.material.design.client.ui.MaterialModal;
import gwt.material.design.client.ui.MaterialModal.TYPE;
import info.esblurock.reaction.client.panel.modal.TextMessagePopup;
import info.esblurock.reaction.client.resources.InterfaceConstants;

import com.google.gwt.core.shared.GWT;
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
		TextMessagePopup popup = new TextMessagePopup(errorTitle,
				caught.toString());
		MaterialModal.showModal(popup, TYPE.FIXED_FOOTER);
	}

	@Override
	public void onSuccess(String result) {
		String message = prefix + result;
		TextMessagePopup popup = new TextMessagePopup(successTitle,
				message);
		MaterialModal.showModal(popup, TYPE.FIXED_FOOTER);
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
