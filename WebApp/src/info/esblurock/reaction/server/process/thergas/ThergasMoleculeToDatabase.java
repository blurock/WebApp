package info.esblurock.reaction.server.process.thergas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import info.esblurock.reaction.data.chemical.thermo.ThergasMoleculeData;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.data.transaction.ActionsUsingIdentificationCode;
import info.esblurock.reaction.data.transaction.thergas.ThergasMoleculeThermodynamics;
import info.esblurock.reaction.data.transaction.thergas.ThergasMoleculesToDatabaseTransaction;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import jThergas.data.JThermgasThermoStructureDataPoint;
import jThergas.data.read.JThergasReadStructureThermo;
import jThergas.data.structure.JThergasAtomicStructure;
import jThergas.data.structure.JThergasStructureData;
import jThergas.data.thermo.JThergasThermoData;
import jThergas.exceptions.JThergasReadException;

public class ThergasMoleculeToDatabase extends ProcessBase {
	String delimitor = "#";

	String validateS;
	String toDatabaseS;
	String uploadS;
	UploadFileTransaction upload;
	ThergasMoleculesToDatabaseTransaction moltransaction;

	public ThergasMoleculeToDatabase() {
		
	}
	public ThergasMoleculeToDatabase(ProcessInputSpecificationsBase input) {
		super(input);
	}
	
	@Override
	protected String getProcessName() {
		return "ThergasMoleculeToDatabase";
	}

	@Override
	protected String getProcessDescription() {
		return "Store thergas molecules to database";
	}

	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
		input.add(validateS);
		input.add(uploadS);
		return input;
	}

	@Override
	protected ArrayList<String> getOutputTransactionObjectNames() {
		ArrayList<String> output = new ArrayList<String>();
		output.add(toDatabaseS);
		return output;
	}

	@Override
	protected void initializeOutputObjects() throws IOException {
		super.initializeOutputObjects();
		moltransaction = new ThergasMoleculesToDatabaseTransaction(user, outputSourceCode, keyword,0);
		objectOutputs.add(moltransaction);
		System.out.println("initializeOutputObjects(): " + outputSourceCode);
	}

	@Override
	protected void createObjects() throws IOException {
		System.out.println("createObjects(): " + outputSourceCode);
		upload = (UploadFileTransaction) getInputSource(uploadS);
		try {
			JThergasReadStructureThermo readtherm = new JThergasReadStructureThermo();
			String datastring = ActionsUsingIdentificationCode.getUploadedAsString(upload.getFileCode());
			readtherm.readAndParse(datastring);
			
			StoreObject store = new StoreObject(user, keyword, outputSourceCode);
			
			for (JThermgasThermoStructureDataPoint point : readtherm.getData()) {
				JThergasAtomicStructure atomicstructure = point.getAtomicStructure();
				JThergasStructureData structure = point.getStructure();
				JThergasThermoData thermo = point.getThermodynamics();

				String tableName = Integer.toString(atomicstructure.getTableNumber()) + "."
						+ Integer.toString(atomicstructure.getGroupNumber());
				ArrayList<String> atomNames = new ArrayList<String>();
				ArrayList<Integer> atomCounts = new ArrayList<Integer>();
				int[] atomnumbers = atomicstructure.getReferenceAtomNumbers();
				String[] atomnames = atomicstructure.getReferenceAtomNames();
				Iterator atoms = atomicstructure.getAtoms().iterator();
				int count = 0;
				while (atoms.hasNext()) {
					int atomcount = (int) atoms.next();
					if (atomcount != 0) {
						atomNames.add(atomnames[count]);
						atomCounts.add(new Integer(atomcount));
					}
					count++;
				}
				ThergasMoleculeData data = new ThergasMoleculeData(keyword, outputSourceCode, tableName,
						structure.getNameOfStructure(), structure.getNancyLinearForm(), structure.getOpticSymmetry(),
						structure.getInternalSymmetry(), structure.getExternalSymmetry(), atomNames, atomCounts,
						atomicstructure.getDateString());
				Double standardEnthalpy = new Double(thermo.getStandardEnthalpy());
				Double standardEntropy = new Double(thermo.getStandardEntropy());
				double[] cps = thermo.getCpValues();
				ArrayList<Double> cpValues = new ArrayList<Double>();
				for(int i=0; i<cps.length; i++) {
					cpValues.add(new Double(cps[i]));
				}
				ThergasMoleculeThermodynamics thermdata = new ThergasMoleculeThermodynamics(
						keyword, outputSourceCode, tableName, 
						structure.getNameOfStructure(),
						standardEnthalpy, 	standardEntropy, cpValues);
				
				store.store(data);
				store.store(thermdata);
			}
			store.flushStore();
		} catch (IOException ex) {
			storeNewTransactions();
			throw ex;
		} catch (JThergasReadException e) {
			throw new IOException(e.toString());
		}
	}

	@Override
	public void initialization() {
		uploadS = "info.esblurock.reaction.data.upload.types.ThergasMoleculeFileUpload";
		validateS = "info.esblurock.reaction.data.upload.types.ValidatedThergasMoleculeFile";
		toDatabaseS = "info.esblurock.reaction.data.transaction.thergas.ThergasMoleculesToDatabaseTransaction";
	}

}
