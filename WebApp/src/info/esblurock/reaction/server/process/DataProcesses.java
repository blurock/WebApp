package info.esblurock.reaction.server.process;

import info.esblurock.reaction.server.process.upload.ReadChemkinMechanismFile;
import info.esblurock.reaction.server.process.upload.ReadNASAPolynomialFile;
import info.esblurock.reaction.server.process.upload.ReadTransportPropertiesFile;
import info.esblurock.reaction.server.authorization.TaskTypes;
import info.esblurock.reaction.server.process.description.RegisterDataDescription;
import info.esblurock.reaction.server.process.chemkin.MechanismMoleculesToDatabase;
import info.esblurock.reaction.server.process.chemkin.MechanismReactionsToDatabase;
public enum DataProcesses {
	
	RegisterDataDescription {
		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			RegisterDataDescription process = new RegisterDataDescription(specs);
			return process;
		}
		@Override
		public ProcessBase getEmptyProcess() {
			RegisterDataDescription process = new RegisterDataDescription();
			return process;
		}
		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}
		
	}, ReadChemkinMechanismFile {
		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			ReadChemkinMechanismFile p = new ReadChemkinMechanismFile(specs);
			return p;
		}
		@Override
		public ProcessBase getEmptyProcess() {
			return new ReadChemkinMechanismFile();
		}
		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}
	}, ReadNASAPolynomialFile {
		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			ReadNASAPolynomialFile p = new ReadNASAPolynomialFile(specs);
			return p;
		}
		@Override
		public ProcessBase getEmptyProcess() {
			return new ReadNASAPolynomialFile();
		}
		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}
		
	}, ReadTransportPropertiesFile {

		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			ReadTransportPropertiesFile p = new ReadTransportPropertiesFile(specs);
			return p;
		}
		@Override
		public ProcessBase getEmptyProcess() {
			return new ReadTransportPropertiesFile();
		}
		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}
	}, MechanismMoleculesToDatabase {

		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			MechanismMoleculesToDatabase process = new MechanismMoleculesToDatabase(specs);
			return process;
		}

		@Override
		public ProcessBase getEmptyProcess() {
			MechanismMoleculesToDatabase process = new MechanismMoleculesToDatabase();
			return process;
		}

		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}
		
	}, MechanismReactionsToDatabase {

		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			MechanismReactionsToDatabase process = new MechanismReactionsToDatabase(specs);
			return process;
		}

		@Override
		public ProcessBase getEmptyProcess() {
			MechanismReactionsToDatabase process = new MechanismReactionsToDatabase();
			return process;
		}

		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}
		
	};
	public abstract ProcessBase getProcess(ProcessInputSpecificationsBase specs);
	public abstract ProcessBase getEmptyProcess();
	public abstract String getTaskType();
	
	static public ProcessBase getProcess(String processName,ProcessInputSpecificationsBase specs) {
		DataProcesses process = valueOf(processName);
		return process.getProcess(specs);
	}
 }
