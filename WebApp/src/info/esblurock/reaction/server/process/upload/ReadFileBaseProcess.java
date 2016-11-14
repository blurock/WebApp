package info.esblurock.reaction.server.process.upload;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.FileSourceSpecification;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.CreateBufferedReaderForSourceFile;
import info.esblurock.reaction.server.StringToKeyConversion;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.queries.QueryBase;
import info.esblurock.reaction.server.queries.TransactionInfoQueries;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;

public class ReadFileBaseProcess extends ProcessBase {
	InputStreamToLineDatabase input;
	StringToKeyConversion conversion;

	UploadFileTransaction upload;
	protected String textBody;
	protected String textName;
	protected String sourceType;

	String descriptionS;
	String specificationS;
	String uploadS;
	DescriptionDataData description;
	FileSourceSpecification specification;

	public ReadFileBaseProcess() {
		super();
	}

	public ReadFileBaseProcess(ProcessInputSpecificationsBase specs) {
		super(specs);
		input = new InputStreamToLineDatabase();
		conversion = new StringToKeyConversion();
	}

	@Override
	public void initialization() {
		descriptionS   = "info.esblurock.reaction.data.description.DescriptionDataData";
	}

	@Override
	protected String getProcessName() {
		return "ReadFileBaseProcess";
	}

	@Override
	protected String getProcessDescription() {
		return "Base Process";
	}

	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
		input.add(descriptionS);
		input.add(specificationS);
		return input;
	}

	@Override
	protected ArrayList<String> getOutputTransactionObjectNames() {
		ArrayList<String> output = new ArrayList<String>();
		output.add(uploadS);
		return output;
	}
    protected void setUpInputDataObjects() throws IOException {
    	super.setUpInputDataObjects();
    	description = (DescriptionDataData) getInputSource(descriptionS);
    	specification = (FileSourceSpecification) getInputSource(specificationS);
		textBody = specification.getTextBody().getValue();
		textName = specification.getTextName();
		sourceType = specification.getSourceType();
    }
	@Override
	protected void createObjects() throws IOException {
		BufferedReader br = CreateBufferedReaderForSourceFile.getBufferedReader(sourceType, textName, textBody);
		if(br != null) {
			log.info("User verified: to read text: " + textName);
			upload = input.uploadFile(upload, br);
		} else {
			UploadFileTransaction trans = TransactionInfoQueries.getFirstUploadFileTransactionFromKeywordUserSourceCodeAndObjectType(user, textName);
			log.info("UploadFileTransaction: fileCode=" + upload.getFileCode() 
			+ ", upload file code=" + trans.getFileCode());
			upload.setFileCode(trans.getFileCode());
			for(TransactionInfo info : transactionOutputs) {
				info.setSourceCode(trans.getFileCode());
			}
			//QueryBase.deleteWithStringKey(cls, key);
		}
	}
	public String getTextBody() {
		return textBody;
	}
	public String getTextName() {
		return textName;
	}
	public String getSourceType() {
		return sourceType;
	}
	public String getUploadTransactionType() {
		return uploadS;
	}
}
