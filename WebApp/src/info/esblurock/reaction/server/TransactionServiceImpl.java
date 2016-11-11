package info.esblurock.reaction.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.FetchGroup;
import javax.jdo.PersistenceManager;
import static com.google.appengine.api.taskqueue.RetryOptions.Builder.*;

import info.esblurock.reaction.client.panel.transaction.TransactionService;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.DatabaseObject;
import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.delete.DeleteTransactionInfoAndObject;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.FileUploadTextBlock;
import info.esblurock.reaction.data.upload.TextSetUploadData;
import info.esblurock.reaction.server.authorization.TaskTypes;
import info.esblurock.reaction.server.event.RegisterTransaction;
import info.esblurock.reaction.server.process.DataProcesses;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;
import info.esblurock.reaction.server.process.ProcessUtilities;
import info.esblurock.reaction.server.process.RegisteredProcesses;
import info.esblurock.reaction.server.process.upload.ReadFileBaseProcess;
import info.esblurock.reaction.server.queries.QueryBase;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.RetryOptions;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * The Class TransactionServiceImpl.
 */
public class TransactionServiceImpl extends ServerBase implements TransactionService {
	private static Logger log = Logger.getLogger(TextToDatabaseImpl.class.getName());

	/** The pm. */
	PersistenceManager pm = PMF.get().getPersistenceManager();
	// RegisteredProcesses registeredProcesses = new RegisteredProcesses();

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#
	 * getAllTransactions()
	 */
	@Override
	public List<TransactionInfo> getAllTransactions() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		javax.jdo.Query q = pm.newQuery(TransactionInfo.class);
		List<TransactionInfo> results = (List<TransactionInfo>) q.execute();
		ArrayList<TransactionInfo> lst = new ArrayList<TransactionInfo>();
		if (results != null) {
			for (TransactionInfo transaction : results) {
				TransactionInfo t = pm.detachCopy(transaction);
				lst.add(t);
			}
		}
		pm.close();
		return lst;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#
	 * getAllUploadTransactions()
	 */
	public List<TextSetUploadData> getAllUploadTransactions() {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		ArrayList<TextSetUploadData> lst = new ArrayList<TextSetUploadData>();
		pm.getFetchPlan().setGroup(FetchGroup.ALL);
		javax.jdo.Query q = pm.newQuery(TextSetUploadData.class);
		try {
			Object object = q.execute();
			if (object != null) {
				if (object instanceof List) {
					List<TextSetUploadData> results = (List<TextSetUploadData>) object;
					if (results.size() > 0) {
						for (TextSetUploadData data : results) {
							if (data != null) {
								pm.retrieve(data);
								TextSetUploadData datacopy = pm.detachCopy(data);
								lst.add(datacopy);
							} else {
								log.info("getAllUploadTransactions() an element is null");
							}
						}
					} else {
						log.info("getAllUploadTransactions() no results found (size = 0)");
					}
				} else {
					log.log(Level.SEVERE, "getAllUploadTransactions() did not return a list: " + object);
				}
			} else {
				log.info("getAllUploadTransactions() not results found (null)");
			}
		} catch (NullPointerException ex) {
			log.info("getAllUploadTransactions() results not found (null)");
		}
		pm.close();
		return lst;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#
	 * getTransactions(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public List<TransactionInfo> getTransactions(String user, String keyword, String objecttype) {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#
	 * removeTransaction(java.lang.String)
	 */
	@Override
	public String removeTransactionOfObject(String key) throws Exception {
		DeleteTransactionInfoAndObject delete = new DeleteTransactionInfoAndObject();
		String ans = delete.deleteFromObjectKey(key);
		pm.close();
		return ans;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#
	 * getUserSet()
	 */
	@Override
	public Set<String> getUserSet() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#
	 * getKeywordSet()
	 */
	@Override
	public Set<String> getKeywordSet() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see info.esblurock.reaction.client.panel.transaction.TransactionService#
	 * getObjectTypeSet()
	 */
	@Override
	public Set<String> getObjectTypeSet() {
		return null;
	}

	@Override
	public String removeTransactionWithTypeAndKeyword(String objecttype, String keyword) throws Exception {
		DeleteTransactionInfoAndObject delete = new DeleteTransactionInfoAndObject();
		String ans = delete.deleteFromTypeAndKeyword(objecttype, keyword);
		return ans;
	}

	@Override
	public String deleteTransactionInfoFromKey(String objectType, String keyword, String transactionkey)
			throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		String user = util.getUserName();
		String source = "DeleteTransactionInfoFromKey" + objectType + "#" + keyword;
		RegisterTransaction.register(util.getUserInfo(), TaskTypes.dataDelete, source, RegisterTransaction.checkLevel1);
		DeleteTransactionInfoAndObject delete = new DeleteTransactionInfoAndObject();
		String ans = delete.deleteTransactionInfoFromKey(transactionkey);
		pm.close();
		return ans;
	}

	public String removeFromRDFsFromDate(Date date) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter subjectfilter = new FilterPredicate("creationDate", FilterOperator.GREATER_THAN_OR_EQUAL, date);
		Query q = new Query("KeywordRDF").setFilter(subjectfilter).setKeysOnly();
		PreparedQuery pq = datastore.prepare(q);
		boolean haselement = pq.asIterable().iterator().hasNext();
		System.out.println("removeFromRDFsFromDate" + haselement);
		String delete = "Delete KeywordRDF  from " + date.toString();
		if (haselement) {
			int count = 0;
			for (Entity entity : pq.asIterable()) {
				System.out.println("Entity Properties" + entity.getProperties().keySet());
				Key rdfkey = entity.getKey();
				System.out.println("TransactionInfo key: " + rdfkey);
				KeywordRDF rdf = pm.getObjectById(KeywordRDF.class, rdfkey);
				pm.deletePersistent(rdf);
				count++;
			}
			Integer countI = new Integer(count);
			delete = delete + ":   Deleted " + countI.toString() + "records";
		} else {
			delete = delete + ": no records found";
		}
		pm.close();
		return delete;
	}

	public TransactionInfo getTransactionInfo(String datasetkeyword, String classname) throws IOException {
		TransactionInfo transaction = null;
		ArrayList<String> propertynames = new ArrayList<String>();
		ArrayList<String> propertyvalues = new ArrayList<String>();
		propertynames.add("keyword");
		propertynames.add("transactionObjectType");
		propertyvalues.add(datasetkeyword);
		propertyvalues.add(classname);
		String transactionC = TransactionInfo.class.getName();
		DatabaseObject obj = QueryBase.getFirstOjbectFromProperties(transactionC, propertynames, propertyvalues);
		transaction = (TransactionInfo) obj;
		return transaction;
	}
	
	public ArrayList<String> getFileUploadTextBlockFromTransaction(String datasetkeyword, String classname) throws IOException {
		ArrayList<String> files = new ArrayList<String>();
		TransactionInfo transaction = getTransactionInfo(datasetkeyword, classname);
		System.out.println("getFileUploadTextBlockFromTransaction: " + datasetkeyword + ": " + classname);
		System.out.println("getFileUploadTextBlockFromTransaction: " + transaction.getSourceCode());
		String propertyname = "fileCode";
		String propertyvalue = transaction.getSourceCode();
		List<DatabaseObject> objs = 
				QueryBase.getDatabaseObjectsFromSingleProperty(FileUploadTextBlock.class.getName(), 
				propertyname, propertyvalue);
		System.out.println("getFileUploadTextBlockFromTransaction: " + objs.size());
		for(DatabaseObject obj : objs) {
			FileUploadTextBlock fileupload = (FileUploadTextBlock) obj;
			String text = fileupload.getTextBlock().getValue();
			files.add(text);
		}
		return files;
	}
	public List<String> findValidProcessing(String keyword) throws IOException {
		ContextAndSessionUtilities util = getUtilities();
		String user = util.getUserName();
		List<String> processes = RegisteredProcesses.toBeProcessed(user, keyword);
		return processes;
	}

	public String runProcess(String processName, String keyword) throws IOException {
		DataProcesses p = DataProcesses.valueOf(processName);
		String answer = "";
		ContextAndSessionUtilities util = getUtilities();
		String user = util.getUserName();
		String source = processName + "#" + keyword;
		RegisterTransaction.register(util.getUserInfo(), p.getTaskType(), source, RegisterTransaction.checkLevel1);
		if (p.asBackgroundJob()) {
			/*
			 * return process.process();
			 */
			Queue queue = QueueFactory.getDefaultQueue();
			RetryOptions retry = withTaskRetryLimit(0);
			queue.add(TaskOptions.Builder.withUrl("/webapp/processservlet").param("processName", processName)
					.param("keyword", keyword).param("user", user).retryOptions(retry));
			answer = "Task Sent to background: " + processName;
		} else {
			ProcessInputSpecificationsBase spec = new ProcessInputSpecificationsBase(user, keyword, null);
			ProcessBase process = p.getProcess(spec);
			answer = "Task Performed:\n " + process.process();
		}
		return answer;
	}

}
