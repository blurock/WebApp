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

	// info.esblurock.reaction.data.delete.DeleteStructuresBase#delete(java.lang.String)

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.esblurock.reaction.data.delete.DeleteStructuresBase#delete(java.lang
	 * .String)
	 */
	public String delete(String fullkey) throws IOException {
		root = super.delete(fullkey);

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
					if (transaction != null) {
						ArrayList<String> setOfLinesKeys = transaction.getSetOfLinesKeys();
						for (String k : setOfLinesKeys) {
							FileUploadLines line = pm.getObjectById(FileUploadLines.class, k);
							if (line == null) {
								pm.deletePersistent(line);
							} else {
								ans += "FileUploadLines with k='" + line + "'\n";
							}
						}
						pm.deletePersistent(transaction);
					} else {
						ans += "ERROR: Unable to find UploadFileTransaction with id= '" + id + "'\n";
					}
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

}
