package info.esblurock.reaction.server.process;

import java.util.ArrayList;

import info.esblurock.react.data.DatabaseObject;
import info.esblurock.reaction.server.process.upload.HttpUploadFileProcess;

public enum DataProcesses {

	HTTPReadChemkinMechanismFile {

		@Override
		public ProcessBase getProcess(String user, String keyword, String sourceCode) {
			HttpUploadFileProcess process = new HttpUploadFileProcess(getProcessName(),user,keyword,"");
			return process;
		}

		@Override
		public ArrayList<String> getInputTransactionObjectNames() {
			return new ArrayList<String>();
		}

		@Override
		public ArrayList<String> getOutputTransactionObjectNames() {
			String o1 = "info.esblurock.reaction.data.upload.UploadFileTransaction";
			ArrayList<String> output = new ArrayList<String>();
			output.add(o1);
			return output;
		}

		@Override
		public String getProcessName() {
			return "HTTPReadChemkinMechanismFile";
		}		
	};
	
	public abstract ProcessBase getProcess(String user, String keyword, String sourceCode);
	public abstract String getProcessName();
	public abstract ArrayList<String> getInputTransactionObjectNames();
	public abstract ArrayList<String> getOutputTransactionObjectNames();
 }
