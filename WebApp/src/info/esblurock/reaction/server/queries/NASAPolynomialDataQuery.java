package info.esblurock.reaction.server.queries;

import java.io.IOException;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.data.chemical.elements.ChemicalElementListData;
import info.esblurock.reaction.data.chemical.mechanism.ChemicalMechanismData;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.data.chemical.thermo.NASAPolynomialData;
import info.esblurock.reaction.data.chemical.thermo.SetOfNASAPolynomialData;
import info.esblurock.reaction.server.datastore.PMF;

public class NASAPolynomialDataQuery extends QueryBase {
	
	public void deleteNASAPolynomialDataFromName(String mechanismName){
		String mechanismKeyword = "mechanismKeyword";
		deleteFromIdentificationCode(NASAPolynomialData.class,mechanismKeyword,mechanismName);
	}
	
	public void deleteNASAPolynomialDataFromKey(String key)  throws IOException  {
		PersistenceManager pm = PMF.get().getPersistenceManager();

		SetOfNASAPolynomialData nasa = pm.getObjectById(SetOfNASAPolynomialData.class, key);
		if(nasa != null) {
			deleteNASAPolynomialDataFromName(nasa.getMechanismKeyword());

			pm.deletePersistent(nasa);
			pm.flush();
			pm.close();
		} else {
			throw new IOException("ChemkinMechanismData deleteStructure fail with key: " + key);
		}
	}
	

}
