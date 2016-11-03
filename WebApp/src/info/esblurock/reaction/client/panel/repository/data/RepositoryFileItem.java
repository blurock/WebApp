package info.esblurock.reaction.client.panel.repository.data;

import java.util.ArrayList;

import info.esblurock.reaction.client.panel.repository.RepositoryBaseItem;
import info.esblurock.reaction.client.panel.repository.RepositoryFileItemTextField;
import info.esblurock.reaction.client.panel.transaction.TransactionService;
import info.esblurock.reaction.client.panel.transaction.TransactionServiceAsync;
import info.esblurock.reaction.data.upload.FileUploadTextBlock;

public class RepositoryFileItem extends RepositoryBaseItem {

	RepositoryFileItemTextField itemField;
	String classname;
	
	public RepositoryFileItem(String datasetkeyword, String classname) {
		super(datasetkeyword);
		this.classname = classname;
		//TransactionServiceAsync async = TransactionService.Util.getInstance();
		//FileUploadTextCallback callback = new FileUploadTextCallback(this);
		//async.getFileUploadTextBlockFromTransaction(datasetkeyword, classname, callback);
	}
/*
	public RepositoryFileItemTextField initializeText(ArrayList<String> result) {
		int pos = classname.lastIndexOf('.');
		RepositoryFileItemTextField itemField = new RepositoryFileItemTextField(classname.substring(pos));
		itemField.initializeText(result);
		setText(itemField.getText());
		setcollapse.add(itemField);
		initialSettings();
		return itemField;
	}
	*/
} 
