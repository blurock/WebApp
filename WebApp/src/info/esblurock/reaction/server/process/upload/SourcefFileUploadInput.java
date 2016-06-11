package info.esblurock.reaction.server.process.upload;

import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class SourcefFileUploadInput extends ProcessInputSpecificationsBase {
	protected String textBody;
	protected String textName;
	protected String sourceType;

	public SourcefFileUploadInput(String userName, String keyword, String sourceCode,
			String sourceType, String textName, String textBody) {
		super(userName, keyword, sourceCode);
		this.textBody = textBody;
		this.textName = textName;
		this.sourceType = sourceType;
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

}
