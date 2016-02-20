/*
 * 
 */
package info.esblurock.reaction.data.upload;

import java.io.IOException;
import java.util.ArrayList;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;

import info.esblurock.reaction.data.delete.DeleteStructuresBase;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.transaction.TransactionInfo;

/**
 * @author edwardblurock
 *
 */
public class DeleteTextSetUploadData extends DeleteStructuresBase {
	
	//info.esblurock.reaction.data.delete.DeleteStructuresBase#delete(java.lang.String)

	
	/* (non-Javadoc)
	 * @see info.esblurock.reaction.data.delete.DeleteStructuresBase#delete(java.lang.String)
	 */
	public String delete(String fullkey) throws IOException {
		root = super.delete(fullkey);
		
		String ans = "";
		try {
			TextSetUploadData object = pm.getObjectById(TextSetUploadData.class, root);
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
			ans = "SUCCESS: Deleted an id: " + fullkey;
		} catch (Exception e) {
			ans = "ERROR: Unable to delete id: " + fullkey;
			throw new IOException(ans);
		}
		return ans;
	}
	

}
