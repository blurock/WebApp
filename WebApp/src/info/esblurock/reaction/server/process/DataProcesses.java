package info.esblurock.reaction.server.process;

import info.esblurock.reaction.server.process.upload.ReadChemkinMechanismFile;
import info.esblurock.reaction.server.process.upload.ReadNASAPolynomialFile;
import info.esblurock.reaction.server.process.upload.ReadTransportPropertiesFile;
import info.esblurock.reaction.server.process.upload.ValidateChemkinMechanismFile;
import info.esblurock.reaction.server.process.upload.ValidateNASAPolynomialFile;
import info.esblurock.reaction.server.process.upload.ValidateTransportFile;
import info.esblurock.reaction.server.authorization.TaskTypes;
import info.esblurock.reaction.server.process.description.RegisterDataDescription;
import info.esblurock.reaction.server.process.description.RegisterDataSetReferences;
import info.esblurock.reaction.server.process.chemkin.MechanismMoleculesToDatabase;
import info.esblurock.reaction.server.process.chemkin.MechanismReactionsToDatabase;
import info.esblurock.reaction.server.process.chemkin.NASAPolynomialsToDatabase;
import info.esblurock.reaction.server.process.chemkin.TransportPropertiesToDatabase;
import info.esblurock.reaction.server.process.chemkin.rdf.MechanismMoleculeProcessRDF;
import info.esblurock.reaction.server.process.chemkin.rdf.MechanismReactionsProcessRDF;
import info.esblurock.reaction.server.process.chemkin.rdf.NASAPolynomialProcessRDF;
import info.esblurock.reaction.server.process.chemkin.rdf.TransportPropertiesProcessRDF;

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
		@Override
		public boolean asBackgroundJob() {
			return false;
		}
		
	}, RegisterDataSetReferences {
		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			RegisterDataSetReferences references = new RegisterDataSetReferences(specs);
			return references;
		}

		@Override
		public ProcessBase getEmptyProcess() {
			RegisterDataSetReferences references = new RegisterDataSetReferences();
			return references;
		}

		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}

		@Override
		public boolean asBackgroundJob() {
			return false;
		}
		
	},
	
	ReadChemkinMechanismFile {
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
		@Override
		public boolean asBackgroundJob() {
			return false;
		}
	}, ValidateChemkinMechanismFile {
		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			ValidateChemkinMechanismFile p = new ValidateChemkinMechanismFile(specs);
			return p;
		}
		@Override
		public ProcessBase getEmptyProcess() {
			return new ValidateChemkinMechanismFile();
		}
		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}
		@Override
		public boolean asBackgroundJob() {
			return false;
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
		@Override
		public boolean asBackgroundJob() {
			return false;
		}
		
	}, ValidateNASAPolynomialFile {
		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			ValidateNASAPolynomialFile p = new ValidateNASAPolynomialFile(specs);
			return p;
		}
		@Override
		public ProcessBase getEmptyProcess() {
			return new ValidateNASAPolynomialFile();
		}
		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}
		@Override
		public boolean asBackgroundJob() {
			return false;
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
		@Override
		public boolean asBackgroundJob() {
			return false;
		}
	}, ValidateTransportFile {

		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			ValidateTransportFile p = new ValidateTransportFile(specs);
			return p;
		}
		@Override
		public ProcessBase getEmptyProcess() {
			return new ValidateTransportFile();
		}
		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}
		@Override
		public boolean asBackgroundJob() {
			return false;
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

		@Override
		public boolean asBackgroundJob() {
			return true;
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

		@Override
		public boolean asBackgroundJob() {
			return true;
		}
		
	}, NASAPolynomialsToDatabase {

		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			NASAPolynomialsToDatabase process = new NASAPolynomialsToDatabase(specs);
			return process;
		}

		@Override
		public ProcessBase getEmptyProcess() {
			NASAPolynomialsToDatabase process = new NASAPolynomialsToDatabase();
			return process;
		}

		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}

		@Override
		public boolean asBackgroundJob() {
			return true;
		}
		
	}, TransportPropertiesToDatabase {

		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			TransportPropertiesToDatabase process = new TransportPropertiesToDatabase(specs);
			return process;
		}

		@Override
		public ProcessBase getEmptyProcess() {
			TransportPropertiesToDatabase process = new TransportPropertiesToDatabase();
			return process;
		}

		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}

		@Override
		public boolean asBackgroundJob() {
			return true;
		}
		
	}, MechanismMoleculeProcessRDF {

		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			MechanismMoleculeProcessRDF process = new MechanismMoleculeProcessRDF(specs);
			return process;
		}

		@Override
		public ProcessBase getEmptyProcess() {
			MechanismMoleculeProcessRDF process = new MechanismMoleculeProcessRDF();
			return process;
		}

		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}

		@Override
		public boolean asBackgroundJob() {
			return true;
		}
		
	}, MechanismReactionsProcessRDF {

		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			MechanismReactionsProcessRDF process = new MechanismReactionsProcessRDF(specs);
			return process;
		}

		@Override
		public ProcessBase getEmptyProcess() {
			MechanismReactionsProcessRDF process = new MechanismReactionsProcessRDF();
			return process;
		}

		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}

		@Override
		public boolean asBackgroundJob() {
			return true;
		}
		
	}, NASAPolynomialProcessRDF {

		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			NASAPolynomialProcessRDF process = new NASAPolynomialProcessRDF(specs);
			return process;
		}

		@Override
		public ProcessBase getEmptyProcess() {
			NASAPolynomialProcessRDF process = new NASAPolynomialProcessRDF();
			return process;
		}

		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}

		@Override
		public boolean asBackgroundJob() {
			return true;
		}
		
	}, TransportPropertiesProcessRDF {

		@Override
		public ProcessBase getProcess(ProcessInputSpecificationsBase specs) {
			TransportPropertiesProcessRDF process = new TransportPropertiesProcessRDF(specs);
			return process;
		}

		@Override
		public ProcessBase getEmptyProcess() {
			TransportPropertiesProcessRDF process = new TransportPropertiesProcessRDF();
			return process;
		}

		@Override
		public String getTaskType() {
			return TaskTypes.dataInput;
		}

		@Override
		public boolean asBackgroundJob() {
			return true;
		}
	};
	public abstract ProcessBase getProcess(ProcessInputSpecificationsBase specs);
	public abstract ProcessBase getEmptyProcess();
	public abstract String getTaskType();
	public abstract boolean asBackgroundJob();
	
	static public ProcessBase getProcess(String processName,ProcessInputSpecificationsBase specs) {
		DataProcesses process = valueOf(processName);
		return process.getProcess(specs);
	}
 }
