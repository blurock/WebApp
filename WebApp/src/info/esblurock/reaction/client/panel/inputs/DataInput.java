package info.esblurock.reaction.client.panel.inputs;

import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.client.TextToDatabaseAsync;
import info.esblurock.reaction.client.panel.description.DataDescription;

//import java.util.StringTokenizer;

import info.esblurock.reaction.client.resources.InputConstants;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwtupload.client.BaseUploadStatus;
import gwtupload.client.HasProgress;
import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.SingleUploader;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.Widget;

public class DataInput extends Composite implements HasText {

	private static DataInputUiBinder uiBinder = GWT.create(DataInputUiBinder.class);

	interface DataInputUiBinder extends UiBinder<Widget, DataInput> {
	}

	InputConstants inputConstants = GWT.create(InputConstants.class);

	MaterialLabel datatype = new MaterialLabel();
	@UiField
	MaterialLink objecttitle;
	@UiField
	MaterialRadioButton submitHTTP;
	@UiField
	MaterialRadioButton fileinput;
	@UiField
	MaterialLabel uploadfile;
	@UiField
	MaterialLabel uploadID;
	@UiField
	MaterialTextBox httpaddress;
	@UiField
	MaterialRadioButton submitText;
	@UiField
	MaterialLink uploadTextName;
	@UiField
	MaterialTextArea textarea;
	@UiField
	MaterialColumn columnfile;
	@UiField
	MaterialButton uploadText;
	@UiField
	MaterialButton uploadHTTP;

	MaterialLink label;
	boolean requiredInput = true;
	DataDescription description;

	String sourceInputType = inputConstants.unspecified();

	public void setRequiredInput(boolean required) {
		requiredInput = required;
	}

	public boolean requiredInput() {
		return requiredInput;
	}

	public String getUploadfileText() {
		return uploadfile.getText();
	}

	public String getUploadIDText() {
		return uploadID.getText();
	}

	public String getFileSourceType() {
		return sourceInputType;
	}

	public boolean fileUploaded() {
		return (!uploadfile.getText().equals(inputConstants.nodataupload())
				&& !uploadID.getText().equals(inputConstants.nodataupload()));
	}

	private void fillInText() {
		objecttitle.setText(inputConstants.title());
		uploadText.setText(inputConstants.uploadtext());
		httpaddress.setPlaceholder(inputConstants.httptitle());
		httpaddress.setText(inputConstants.httptext());
		textarea.setText(inputConstants.documentText());
		textarea.setPlaceholder(inputConstants.texttitle());
		uploadfile.setText(inputConstants.nodataupload());
		// uploadfile.setPlaceholder(inputConstants.uploadPlaceholder());
		uploadID.setText(inputConstants.nodataupload());
		// uploadID.setPlaceholder(inputConstants.uploadIDPlaceholder());
		uploadHTTP.setText(inputConstants.uploadhttp());
	}

	public DataInput() {
		initWidget(uiBinder.createAndBindUi(this));
		fillInText();
		init();
	}

	public DataInput(String title) {
		initWidget(uiBinder.createAndBindUi(this));
		fillInText();
		objecttitle.setText(title);
		init();
	}

	public DataInput(DataDescription description,
			String type, String title, 
			String titletooltip, String httptext, String texttitle,
			String texttext) {
		initWidget(uiBinder.createAndBindUi(this));
		this.description = description;
		fillInText();

		datatype.setText(type);

		objecttitle.setText(title);
		objecttitle.setTooltip(titletooltip);

		httpaddress.setText(httptext);
		textarea.setText(texttext);
		textarea.setPlaceholder(texttitle);

		init();
		/*
		*/
	}

	private void init() {
		InputConstants inputConstants = GWT.create(InputConstants.class);
		MaterialButton button = new MaterialButton(inputConstants.upload(),
				"blue-text text-darken-2 light-blue lighten-5", "");
		SingleUploader uploader = new SingleUploader(FileInputType.CUSTOM.with(button), new MyUploadStatus());
		columnfile.add(uploader);
		uploader.addOnFinishUploadHandler(onFinishUploaderHandler);
	}

	// Load the file in the document and in the case of success attach it to the
	// viewer
	private IUploader.OnFinishUploaderHandler onFinishUploaderHandler = new IUploader.OnFinishUploaderHandler() {
		@Override
		public void onFinish(IUploader uploader) {
			if (uploader.getStatus() == Status.SUCCESS) {

				UploadedInfo info = uploader.getServerInfo();

				Window.alert(info.getFileName() + "\n" + info.message + "\nField   : " + info.field + "\nFileName: "
						+ info.getFileName() + "\nURL     : " + info.getFileUrl() + "\nKey     :" + info.getKey()
						+ "\ngetField: " + info.getField());

				setUpLoadInformation(info.getFileName(), info.getField());
				setInputSource(inputConstants.upload());
			} else {
				Window.alert(uploader.getStatus().toString());
			}
		}

		private void setUpLoadInformation(String fileName, String field) {
			uploadfile.setText(fileName);
			uploadID.setText(field);
		}
	};

	AsyncCallback<String> callbackText = new AsyncCallback<String>() {

		@Override
		public void onFailure(Throwable caught) {
			System.out.println("textToDatabase: ERROR");
			System.out.println(caught.toString());
			Window.alert(caught.toString());
		}

		@Override
		public void onSuccess(String result) {
			MaterialToast.alert("Key: " + result);
			setUploadID(result);
			setuUploadFile(uploadTextName.getText());
			setInputSource(inputConstants.textfile());
		}
	};

	@UiHandler("uploadText")
	void onTextUpload(ClickEvent e) {
		TextToDatabaseAsync async = TextToDatabase.Util.getInstance();
		async.textToDatabase(uploadTextName.getText(), textarea.getText(), callbackText);
		// servercall.textToDatabase(uploadTextName.getText(),textarea.getText(),
		// this);
		MaterialToast.alert("Upload Text");
	}

	AsyncCallback<String> callbackHTTP = new AsyncCallback<String>() {

		@Override
		public void onFailure(Throwable caught) {
			Window.alert(caught.toString());
		}

		@Override
		public void onSuccess(String result) {
			if (result.startsWith("ERROR")) {

			} else {
				if (result != null) {
					MaterialToast.alert("Key: " + result);
					setuUploadFile(httpaddress.getText());
					setUploadID(result);
					setInputSource(inputConstants.uploadhttp());
				} else {

				}
			}
		}

	};

	@UiHandler("uploadHTTP")
	void onHTTPUpload(ClickEvent e) {
		TextToDatabaseAsync async = TextToDatabase.Util.getInstance();
		async.httpToDatabase(description.createObjectKeyword(), httpaddress.getText(), callbackHTTP);
		MaterialToast.alert("Upload http");
	}

	@UiHandler("submitHTTP")
	void onHTTPClick(ClickEvent e) {
		MaterialToast.alert("Input as HTTP");
	}

	@UiHandler("submitText")
	void onSubmitClick(ClickEvent e) {
		MaterialToast.alert("Input as pasted text");
	}

	@UiHandler("fileinput")
	void onSubmitFileClick(ClickEvent e) {
		MaterialToast.alert("Input as uploaded file");
	}

	public String getType() {
		return datatype.getText();
	}

	@Override
	public void setText(String text) {
		// objecttitle.setText(text);
	}

	@Override
	public String getText() {
		return textarea.getText();
	}

	public String getHTTPAddress() {
		return httpaddress.getText();
	}

	public void setuUploadFile(String text) {
		uploadfile.setText(text);
	}

	public void setUploadID(String text) {
		uploadID.setText(text);
	}

	public void setInputSource(String source) {
		if (source.equals(inputConstants.uploadhttp())) {
			submitHTTP.setValue(true);
			sourceInputType = inputConstants.uploadhttp();
		} else if (source.equals(inputConstants.textfile())) {
			submitText.setValue(true);
			sourceInputType = inputConstants.textfile();
		} else if (source.equals(inputConstants.upload())) {
			fileinput.setValue(true);
			sourceInputType = inputConstants.upload();
		}
	}

	// progress bar using HTML5 <progress> tag
	// Note that it does not works in earlier versions to IE10.
	public static class MyProgressBar extends Widget implements HasProgress {
		public MyProgressBar() {
			setElement(Document.get().createElement("progress"));
		}

		@Override
		public void setProgress(long done, long total) {
			getElement().setAttribute("max", "" + total);
			getElement().setAttribute("value", "" + done);
		}
	}

	public static class MyUploadStatus extends BaseUploadStatus {
		public MyUploadStatus() {
			setProgressWidget(new MyProgressBar());
		}
	}

}
