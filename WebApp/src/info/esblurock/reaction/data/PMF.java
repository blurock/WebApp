package info.esblurock.reaction.data;

import java.util.HashMap;
import java.util.Map;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {
	private static PersistenceManagerFactory pmfInstance;
	private static Map props = new HashMap();
	static {
	    props.put("javax.jdo.PersistenceManagerFactoryClass", 
	    		"org.datanucleus.api.jdo.JDOPersistenceManagerFactory");
	    props.put("javax.jdo.option.ConnectionURL", "appengine");
	    props.put("javax.jdo.option.NontransactionalRead", "true");
	    props.put("javax.jdo.option.NontransactionalWrite", "true");
	    props.put("javax.jdo.option.RetainValues", "true");
	    props.put("datanucleus.appengine.autoCreateDatastoreTxns", "true");
	    props.put("datanucleus.appengine.singletonPMFForName", "true");
	    pmfInstance = JDOHelper.getPersistenceManagerFactory(props);
	}

	
	
	/*
	private static PersistenceManagerFactory pmfInstance = JDOHelper
			.getPersistenceManagerFactory("transactions-optional");
*/
	private PMF() {
	}

	public static PersistenceManagerFactory get() {
		if(pmfInstance.isClosed()) {
			pmfInstance = JDOHelper.getPersistenceManagerFactory(props);
		}
		return pmfInstance;
	}
}
