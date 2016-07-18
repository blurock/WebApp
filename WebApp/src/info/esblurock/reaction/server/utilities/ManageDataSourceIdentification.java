package info.esblurock.reaction.server.utilities;

import java.util.logging.Logger;

import javax.jdo.JDOObjectNotFoundException;
import javax.jdo.PersistenceManager;

import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.upload.DataSourceIdentification;

public class ManageDataSourceIdentification {
	private static final Logger log = Logger.getLogger(ManageDataSourceIdentification.class.getName());

	public static String getDataSourceIdentification(String username) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DataSourceIdentification count = null;
		try {
			count = pm.getObjectById(DataSourceIdentification.class, username);
		} catch (JDOObjectNotFoundException ex) {
			System.out.println("new UploadFileCount");
			count = new DataSourceIdentification(username);
			pm.makePersistent(count);
		}
		count.increment();
		log.info("Final UploadFileCount  count=" + count.getCountAsString());
		pm.makePersistent(count);
		pm.close();
		return count.getCountAsString();

	}

}
