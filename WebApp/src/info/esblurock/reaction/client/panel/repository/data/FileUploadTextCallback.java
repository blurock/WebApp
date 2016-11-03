package info.esblurock.reaction.client.panel.repository.data;

import java.util.ArrayList;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.reaction.client.panel.repository.RepositoryFileItemTextField;
import info.esblurock.reaction.data.upload.FileUploadTextBlock;

public class FileUploadTextCallback implements AsyncCallback<ArrayList<String>> {
	RepositoryFileItemTextField fileitem;
	boolean filefound;
	public FileUploadTextCallback(RepositoryFileItemTextField fileitem) {
		this.fileitem = fileitem;
	}
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
		filefound = false;
	}

	@Override
	public void onSuccess(ArrayList<String> result) {
		fileitem.initializeText(result);
		filefound = true;
	}

}
