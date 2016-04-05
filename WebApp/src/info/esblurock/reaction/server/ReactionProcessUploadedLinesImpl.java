package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.ArrayList;

import javax.jdo.PersistenceManager;

import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.reaction.client.ReactionProcessUploadedLines;
import info.esblurock.reaction.data.chemical.mechanism.ChemicalMechanismData;
import info.esblurock.reaction.data.chemical.mechanism.CreateChemicalMechanismData;
import info.esblurock.reaction.data.chemical.mechanism.StoreChemkinMechanismData;
import info.esblurock.reaction.data.chemical.reaction.CreateChemkinReactionData;
import info.esblurock.reaction.data.chemical.thermo.ProcessNASAPolynomialUpload;
import info.esblurock.reaction.data.chemical.thermo.SetOfNASAPolynomialData;
import info.esblurock.reaction.data.contact.entities.OrganizationDescriptionData;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.FileUploadLines;
import info.esblurock.reaction.data.upload.InputTextSource;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.datastore.PMF;

/**
 * The Class ReactionProcessUploadedLinesImpl.
 */
public class ReactionProcessUploadedLinesImpl  extends ServerBase implements ReactionProcessUploadedLines {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The pm. */
	PersistenceManager pm = PMF.get().getPersistenceManager();

	/** The comment string. */
	String commentString = "!";
	
	/* (non-Javadoc)
	 * @see info.esblurock.reaction.client.ReactionProcessUploadedLines#processUploadedMechanism(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public String processUploadedMechanism(DescriptionDataData description, 
			String key, String filename, boolean process) throws IOException {
		String keyword = CreateChemicalMechanismData.createMechanismName(description.getSourcekey(),description.getKeyword());
		System.out.println("processUploadedMechanism: " + keyword);
		System.out.println("processUploadedMechanism: " + filename);
		HandleTransactions.transactionExists(keyword, ChemicalMechanismData.class.getName());
		System.out.println("Processing: parse");
		ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(key,filename,commentString);
		ChemkinMechanism mechanism = new ChemkinMechanism();
		mechanism.parse(chemkinstring, commentString);
		String ans = mechanism.toString();
		if(process) {
			System.out.println("Processing: add to database");
			TransactionInfo transaction = new TransactionInfo(description.getInputkey(),keyword,ChemicalMechanismData.class.getName());
			CreateChemicalMechanismData create = new CreateChemicalMechanismData(keyword);
			System.out.println("Mechanism Keyword: " + keyword);
			try {
				create.create(keyword, mechanism, transaction);
				create.finish();
			} catch(Exception ex) {
				HandleTransactions.exception(key, ex, transaction);
			}
			
		}
		return ans;
	}
	public String processUploadedSetOfNASAPolynomial(DescriptionDataData description, 
			String key, String filename, boolean process) throws IOException {
		String keyword = CreateChemicalMechanismData.createMechanismName(description.getSourcekey(),description.getKeyword());
		HandleTransactions.transactionExists(keyword, SetOfNASAPolynomialData.class.getName());
		ProcessNASAPolynomialUpload processNASA = new ProcessNASAPolynomialUpload();
		String ans = "";
		try {
			ans = processNASA.processUploadedNASAPolynomials(description, key, filename, process);
		} catch(Exception ex) {
			HandleTransactions.exception(key, ex, processNASA.getTransactionInfo());
		}
		return ans;
	}
	
	public String deleteTextSetUploadData(String key)  throws IOException {
		String ans = "";
		try {
			TextSetUploadData object = pm.getObjectById(TextSetUploadData.class, key);

			DescriptionDataData description = object.getDescription();
			pm.deletePersistent(description);
			ArrayList<InputTextSource> inputTextSources = object.getInputTextSources();
			for(InputTextSource source : inputTextSources) {
				String id = source.getID();
				UploadFileTransaction transaction = pm.getObjectById(UploadFileTransaction.class,id);
				ArrayList<String> setOfLinesKeys = transaction.getSetOfLinesKeys();
				for(String k : setOfLinesKeys) {
					FileUploadLines line = pm.getObjectById(FileUploadLines.class,k);
					pm.deletePersistent(line);
				}
				pm.deletePersistent(transaction);
				pm.deletePersistent(source);
			}
			pm.deletePersistent(object);
			ans = "SUCCESS: Deleted an id: " + key;
		} catch (Exception e) {
			ans = "ERROR: Unable to delete id: " + key;
			throw new IOException(ans);
		}
		return ans;
	}
}

