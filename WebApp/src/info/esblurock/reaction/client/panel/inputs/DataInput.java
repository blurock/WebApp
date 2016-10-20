package info.esblurock.reaction.client.panel.inputs;

import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.client.TextToDatabaseAsync;
import info.esblurock.reaction.client.panel.description.DataDescription;

import info.esblurock.reaction.client.resources.InputConstants;
import info.esblurock.reaction.data.upload.FileSourceSpecification;
import gwt.material.design.addins.client.fileuploader.MaterialFileUploader;
import gwt.material.design.addins.client.fileuploader.base.UploadFile;
import gwt.material.design.addins.client.fileuploader.events.DragOverEvent;
import gwt.material.design.addins.client.fileuploader.events.SuccessEvent;
import gwt.material.design.addins.client.fileuploader.events.TotalUploadProgressEvent;
import gwt.material.design.client.constants.ButtonType;
import gwt.material.design.client.ui.MaterialButton;
import gwt.material.design.client.ui.MaterialColumn;
import gwt.material.design.client.ui.MaterialImage;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialProgress;
import gwt.material.design.client.ui.MaterialRadioButton;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTextBox;
import gwt.material.design.client.ui.MaterialToast;
import gwt.material.design.client.ui.animate.MaterialAnimator;
import gwt.material.design.client.ui.animate.Transition;
import gwtupload.client.BaseUploadStatus;
import gwtupload.client.HasProgress;

import com.google.appengine.api.datastore.Text;
/*
import gwtupload.client.IFileInput.FileInputType;
import gwtupload.client.IUploadStatus.Status;
import gwtupload.client.IUploader;
import gwtupload.client.IUploader.UploadedInfo;
import gwtupload.client.SingleUploader;
*/
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class DataInput extends Composite implements HasText {

	private static DataInputUiBinder uiBinder = GWT.create(DataInputUiBinder.class);
	public static String textAsSource = "Text";
	public static String httpAsSource = "Http";
	public static String fileAsSource = "File";

	public static int inputTextLimit = 500000;

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
	MaterialTextBox uploadTextName;
	@UiField
	MaterialTextArea textarea;
	@UiField
	MaterialButton uploadText;
	@UiField
	MaterialButton uploadHTTP;

	@UiField
	MaterialFileUploader cardUploader;
	@UiField
	MaterialImage imgPreview;
	@UiField
	MaterialProgress progress;
	@UiField
	MaterialLabel lblName, lblSize;

	MaterialLink label;
	boolean requiredInput = true;
	DataDescription description;
	String specName;
	String transName;

	String keyword;
	String uploaderPath;
	// SingleUploader uploader;

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
		objecttitle.setTitle(inputConstants.title());
		uploadText.setText(inputConstants.uploadtext());
		httpaddress.setPlaceholder(inputConstants.httptitle());
		httpaddress.setText(inputConstants.httptext());
		textarea.setText(inputConstants.documentText());
		textarea.setPlaceholder(inputConstants.texttitle());
		uploadTextName.setPlaceholder(inputConstants.documentNamePlaceholder());
		uploadTextName.setText(inputConstants.documentName());
		uploadfile.setText(inputConstants.nodataupload());
		// uploadfile.setPlaceholder(inputConstants.uploadPlaceholder());
		uploadID.setText(inputConstants.nodataupload());
		// uploadID.setPlaceholder(inputConstants.uploadIDPlaceholder());
		uploadHTTP.setText(inputConstants.uploadhttp());
	}

	public void setKeyword(String keyword, String source) {
		this.keyword = keyword;
		/*
		 * String msg = uploaderPath + "?transname=" + transName + "&keyword=" +
		 * keyword + "&source=" + source; uploader.setServletPath(msg);
		 */
	}

	public void setVisibility(boolean visible) {
		this.setVisibility(visible);
	}

	public DataInput() {
		initWidget(uiBinder.createAndBindUi(this));
		fillInText();
		init();
	}

	public DataInput(String specName, String transName, DataDescription description, String type, String title,
			String titletooltip, String httptext, String texttitle, String texttext) {
		initWidget(uiBinder.createAndBindUi(this));
		this.description = description;
		this.specName = specName;
		this.transName = transName;
		fillInText();

		datatype.setText(type);
		objecttitle.setText(title);
		/*
		 * objecttitle.setTitle(title); objecttitle.setTooltip(titletooltip);
		 */
		httpaddress.setText(httptext);
		textarea.setText(texttext);
		textarea.setPlaceholder(texttitle);

		init();
	}

	private void init() {
		InputConstants inputConstants = GWT.create(InputConstants.class);
		MaterialButton button = new MaterialButton();

		button.setType(ButtonType.FLOATING);
		button.setText(inputConstants.upload());
		button.setTextColor("blue-text text-darken-2 light-blue lighten-5");

		// Added the progress to card uploader
		cardUploader.addTotalUploadProgressHandler(new TotalUploadProgressEvent.TotalUploadProgressHandler() {
			@Override
			public void onTotalUploadProgress(TotalUploadProgressEvent event) {
				progress.setPercent(event.getProgress());
			}
		});
		cardUploader.addSuccessHandler(new SuccessEvent.SuccessHandler<UploadFile>() {
			@Override
			public void onSuccess(SuccessEvent<UploadFile> event) {
				MaterialToast.fireToast("Success");
				lblName.setText(event.getTarget().getName());
				lblSize.setText(event.getTarget().getType());
				FileInputCallback callback = new FileInputCallback();
				TextToDatabaseAsync async = TextToDatabase.Util.getInstance();
				async.fileAsInput(specName, fileAsSource, event.getTarget().getName(), 
						uploadTextName.getText(), description.getInputKey(),
						description.createObjectKeyword(), callback);
			}
		});

		cardUploader.addDragOverHandler(new DragOverEvent.DragOverHandler() {
			@Override
			public void onDragOver(DragOverEvent event) {
				MaterialAnimator.animate(Transition.RUBBERBAND, cardUploader, 0);
			}
		});
	}

	AsyncCallback<String> callbackText = new AsyncCallback<String>() {

		@Override
		public void onFailure(Throwable caught) {
			System.out.println("textToDatabase: ERROR");
			System.out.println(caught.toString());
			Window.alert(caught.toString());
		}

		@Override
		public void onSuccess(String result) {
			MaterialToast.fireToast("Key: " + result);
			setUploadID(result);
			setuUploadFile(uploadTextName.getText());
			setInputSource(inputConstants.textfile());
		}
	};

	@UiHandler("uploadText")
	void onTextUpload(ClickEvent e) {
		if (textarea.getText().length() < inputTextLimit) {
			TextToDatabaseAsync async = TextToDatabase.Util.getInstance();
			// Window.alert("Keyword: " + description.createObjectKeyword());
			// Window.alert("Name: " + uploadTextName.getText());
			// Window.alert("Text: " + textarea.getText());
			async.textToDatabase(specName, textAsSource, description.createObjectKeyword(), uploadTextName.getText(),
					textarea.getText(), callbackText);
			MaterialToast.fireToast("Upload Text");
		} else {
			MaterialToast.fireToast(
					"Maximum Text size exceeded (" + textarea.getText().length() + " > " + inputTextLimit + " bytes) ");
		}
	}

	@UiHandler("uploadHTTP")
	void onHTTPUpload(ClickEvent e) {
		TextToDatabaseAsync async = TextToDatabase.Util.getInstance();
		async.textToDatabase(specName, httpAsSource, description.createObjectKeyword(), httpaddress.getText(),
				httpaddress.getText(), callbackText);
		MaterialToast.fireToast("Upload http");
	}

	@UiHandler("submitHTTP")
	void onHTTPClick(ClickEvent e) {
		MaterialToast.fireToast("Input as HTTP");
	}

	@UiHandler("submitText")
	void onSubmitClick(ClickEvent e) {
		MaterialToast.fireToast("Input as pasted text");
	}

	/*
	 * @UiHandler("fileinput") void onSubmitFileClick(ClickEvent e) {
	 * MaterialToast.fireToast("Input as uploaded file"); }
	 */
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
		}
		/*
		 * else if (source.equals(inputConstants.upload())) {
		 * fileinput.setValue(true); sourceInputType = inputConstants.upload();
		 * }
		 */
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
