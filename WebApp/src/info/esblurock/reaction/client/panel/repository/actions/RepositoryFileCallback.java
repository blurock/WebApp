package info.esblurock.reaction.client.panel.repository.actions;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.client.panel.repository.data.RepositoryFileItem;
import info.esblurock.reaction.data.transaction.TransactionInfo;

public class RepositoryFileCallback implements AsyncCallback<TransactionInfo> {

	RepositoryFileItem fileitem;

	public RepositoryFileCallback(RepositoryFileItem fileitem) {
		this.fileitem = fileitem;
	}

	@Override
	public void onFailure(Throwable caught) {
	}

	@Override
	public void onSuccess(TransactionInfo result) {
	}

}
