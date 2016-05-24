package info.esblurock.reaction.server.process;

import info.esblurock.reaction.server.process.upload.ReadChemkinMechanismFile;
import info.esblurock.reaction.server.process.upload.ReadNASAPolynomialFile;

public enum DataProcesses {

	ReadChemkinMechanismFile {

		@Override
		public ProcessBase getProcess(ProcessInputSpecificaitonsBase specs) {
			ReadChemkinMechanismFile p = new ReadChemkinMechanismFile(specs);
			return p;
		}

		@Override
		public String getInputSpecificationType() {
			return "SourcefFileUploadInput";
		}

	}, ReadNASAPolynomialFile {

		@Override
		public ProcessBase getProcess(ProcessInputSpecificaitonsBase specs) {
			ReadNASAPolynomialFile p = new ReadNASAPolynomialFile(specs);
			return p;
		}

		@Override
		public String getInputSpecificationType() {
			return "SourcefFileUploadInput";
		}
		
	};	
	public abstract ProcessBase getProcess(ProcessInputSpecificaitonsBase specs);
	public abstract String getInputSpecificationType();
	
	static public ProcessBase getProcess(String processName,ProcessInputSpecificaitonsBase specs) {
		DataProcesses process = valueOf(processName);
		return process.getProcess(specs);
	}
 }
