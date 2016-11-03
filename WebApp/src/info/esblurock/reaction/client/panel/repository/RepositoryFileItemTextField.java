package info.esblurock.reaction.client.panel.repository;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import info.esblurock.reaction.client.panel.repository.data.FileUploadTextCallback;
import info.esblurock.reaction.client.panel.transaction.TransactionService;
import info.esblurock.reaction.client.panel.transaction.TransactionServiceAsync;
import info.esblurock.reaction.data.upload.FileUploadTextBlock;

public class RepositoryFileItemTextField extends Composite implements HasText {

	private static RepositoryFileItemTextFieldUiBinder uiBinder = GWT.create(RepositoryFileItemTextFieldUiBinder.class);

	interface RepositoryFileItemTextFieldUiBinder extends UiBinder<Widget, RepositoryFileItemTextField> {
	}

	public RepositoryFileItemTextField() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	MaterialLink more;
	@UiField
	MaterialLink name;
	@UiField
	MaterialLink lowerbound;
	@UiField
	MaterialLink upperbound;
	@UiField
	MaterialTextArea textarea;
	
	int initialLine;
	int finalLine;
	int currentBlock;
	int bytePosition;
	int numberOfLinesToRetrieve;
	String block;
	ArrayList<String> blocks;
	
	String classname;

	public RepositoryFileItemTextField(String datasetkeyword, String classname) {
		initWidget(uiBinder.createAndBindUi(this));
		int pos = classname.lastIndexOf('.');
		setText(classname.substring(pos));
		this.classname = classname;
		TransactionServiceAsync async = TransactionService.Util.getInstance();
		FileUploadTextCallback callback = new FileUploadTextCallback(this);
		async.getFileUploadTextBlockFromTransaction(datasetkeyword, classname, callback);
	}

	public void initializeText(ArrayList<String> result) {
		numberOfLinesToRetrieve = 100;
		finalLine = 0;
		currentBlock = 0;
		bytePosition = 0;
		blocks = result;
		nextBlock();
		
	}
	private void nextBlock() {
		initialLine = finalLine++;
		block = blocks.get(currentBlock);
		int initialbyte = bytePosition;
		int count = numberOfLinesToRetrieve;
		byte[]  bytes = block.getBytes();
		while(count > 0 && bytePosition < bytes.length) {
			if(bytes[bytePosition] == '\n') {
				count--;
				finalLine++;
			}
			bytePosition++;
		}
		if(count != 0) {
			more.setVisible(false);
		} else {
			more.setVisible(true);
		}
		bytePosition--;
		StringBuilder build = new StringBuilder();
		build.append(textarea.getText());
		build.append(block.substring(initialbyte,bytePosition));
		lowerbound.setText(Integer.toString(0));
		upperbound.setText(Integer.toString(finalLine));
		textarea.setText(build.toString());
		textarea.setFontSize(0.50, Unit.EM);
	}
	
	@UiHandler("more")
	void moreClicked(ClickEvent event) {
		nextBlock();
	}
	public void setText(String text) {
		name.setText(text);
	}

	public String getText() {
		return name.getText();
	}

}
