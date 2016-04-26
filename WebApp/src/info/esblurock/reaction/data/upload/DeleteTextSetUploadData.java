/*
 * 
 */
package info.esblurock.reaction.data.upload;

import java.io.IOException;
import java.util.ArrayList;

import javax.jdo.PersistenceManager;

import java.util.logging.Logger;
import org.mortbay.log.Log;


import info.esblurock.reaction.data.delete.DeleteStructuresBase;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.transaction.ActionsUsingIdentificationCode;
import info.esblurock.reaction.server.datastore.PMF;

/**
 * @author edwardblurock
 *
 */
public class DeleteTextSetUploadData extends DeleteStructuresBase {
	private static Logger log = Logger.getLogger(DeleteTextSetUploadData.class.getName());
	/*
	 * 
	 * @see
	 * info.esblurock.reaction.data.delete.DeleteStructuresBase#delete(java.lang
	 * .String)
	 */
	public String delete(String fullkey) throws IOException {
		root = super.delete(fullkey);
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String ans = "";
		try {
			TextSetUploadData object = pm.getObjectById(TextSetUploadData.class, root);
			if (object != null) {
				DescriptionDataData description = object.getDescription();
				if (description != null)
					pm.deletePersistent(description);
				ArrayList<InputTextSource> inputTextSources = object.getInputTextSources();
				for (InputTextSource source : inputTextSources) {
					String id = source.getID();
					UploadFileTransaction transaction = pm.getObjectById(UploadFileTransaction.class, id);
					String out = removeUpload(id,transaction);
					log.info(out);
					pm.deletePersistent(source);
				}
				pm.deletePersistent(object);
				ans = "SUCCESS: Deleted an id: " + fullkey;
			} else {
				ans += "ERROR: Unable to find TextSetUploadData with root= '" + root + "'\n";
			}
		} catch (Exception e) {
			ans = "ERROR: Unable to delete id: " + fullkey;
			throw new IOException(ans);
		}
		return ans;
	}
	

	public String removeUpload(String key,UploadFileTransaction uploadinfo) throws IOException {
		String ans = "SUCCESS";
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			String fileCode = uploadinfo.getFileCode();
			int start = 0;
			int increment = 1000;
			boolean notdone = true;
			while (notdone) {
				notdone = ActionsUsingIdentificationCode.deleteFileUploadLines(fileCode, start, increment);
				start += increment;
			}
			pm = PMF.get().getPersistenceManager();
			uploadinfo = pm.getObjectById(UploadFileTransaction.class, key);
			pm.deletePersistent(uploadinfo);
			pm.close();
		} catch (Exception ex) {
			log.info(ex.toString());
			throw new IOException("Error in remove upload: \n" +  ex.toString());
		}
		return ans;
		
	}

}
