package info.esblurock.reaction.server.process;

public class ProcessInputSpecificaitonsBase {
	protected String userName;
	protected String keyword;
	protected String sourceCode;
	
	public ProcessInputSpecificaitonsBase(String userName, String keyword, String sourceCode) {
		super();
		this.userName = userName;
		this.keyword = keyword;
		this.sourceCode = sourceCode;
	}
	public String getUserName() {
		return userName;
	}
	public String getKeyword() {
		return keyword;
	}
	public String getSourceCode() {
		return sourceCode;
	}
	
	
}
