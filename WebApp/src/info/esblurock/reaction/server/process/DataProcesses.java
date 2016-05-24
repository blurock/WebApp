package info.esblurock.reaction.server.process;

import info.esblurock.reaction.server.process.upload.ReadChemkinMechanismFile;

public enum DataProcesses {

	ReadChemkinMechanismFile {

		@Override
		public ProcessBase getProcess(String user, String keyword, String sourceCode) {
			ReadChemkinMechanismFile p = new ReadChemkinMechanismFile(user,keyword,sourceCode);
			return p;
		}

	};	
	public abstract ProcessBase getProcess(String user, String keyword, String sourceCode);
	
	static public ProcessBase getProcess(String processName,String user, String keyword, String sourceCode) {
		DataProcesses process = valueOf(processName);
		return process.getProcess(user, keyword, sourceCode);
	}
 }
