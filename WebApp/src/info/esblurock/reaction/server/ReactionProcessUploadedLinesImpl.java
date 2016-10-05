package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.jdo.PersistenceManager;


import info.esblurock.react.mechanisms.chemkin.ChemkinMechanism;
import info.esblurock.reaction.client.ReactionProcessUploadedLines;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.DeleteTextSetUploadData;
import info.esblurock.reaction.data.upload.InputTextSource;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.authorization.TaskTypes;
import info.esblurock.reaction.server.chemkin.ChemkinStringFromStoredFile;
import info.esblurock.reaction.server.event.RegisterTransaction;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;

/**
 * The Class ReactionProcessUploadedLinesImpl.
 */
public class ReactionProcessUploadedLinesImpl  extends ServerBase implements ReactionProcessUploadedLines {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The pm. */

	/** The comment string. */
	String commentString = "!";
	
	public ChemkinMechanism parseChemkinMechanismText(TransactionInfo transaction) throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		String event = "Process, " + transaction.getSourceCode() + ", " + transaction.getKeyword();
		String key = transaction.getKey();
		String user = transaction.getUser();
		RegisterTransaction.register(util.getUserInfo(),TaskTypes.dataInput, event, RegisterTransaction.checkLevel1);
		ChemkinStringFromStoredFile chemkinstring = new ChemkinStringFromStoredFile(key,user,commentString);
		ChemkinMechanism mechanism = new ChemkinMechanism();
		mechanism.parse(chemkinstring, commentString);
		return mechanism;		
	}
	public String deleteTextSetUploadData(String key)  throws IOException {
		String ans = "";
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			TextSetUploadData object = pm.getObjectById(TextSetUploadData.class, key);
			DescriptionDataData description = object.getDescription();
			pm.deletePersistent(description);
			
			ArrayList<InputTextSource> inputTextSources = object.getInputTextSources();
			for(InputTextSource source : inputTextSources) {
				String id = source.getID();
				
				pm = PMF.get().getPersistenceManager();
				UploadFileTransaction transaction = pm.getObjectById(UploadFileTransaction.class,id);
				pm.close();
				
				DeleteTextSetUploadData delete = new DeleteTextSetUploadData();
				String out = delete.removeUpload(id,transaction);
				
				pm = PMF.get().getPersistenceManager();
				source =  pm.getObjectById(InputTextSource.class,source.getKey());
				pm.deletePersistent(source);
				pm.close();
			}
			pm = PMF.get().getPersistenceManager();
			object = pm.getObjectById(TextSetUploadData.class, object.getKey());
			pm.deletePersistent(object);
			pm.close();
			ans = "SUCCESS: Deleted an id: " + key;
		} catch (Exception e) {
			ans = "ERROR: Unable to delete id: " + key;
			throw new IOException(ans);
		}
		return ans;
	}

}

