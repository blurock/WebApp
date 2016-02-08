package info.esblurock.reaction.client.panel.transaction;

import java.util.List;
import java.util.Set;

import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.TextSetUploadData;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("transactionservice")
public interface TransactionService extends RemoteService {

	   public static class Util
	   {
	       private static TransactionServiceAsync instance;

	       public static TransactionServiceAsync getInstance()
	       {
	           if (instance == null)
	           {
	               instance = GWT.create(TransactionService.class);
	           }
	           return instance;
	       }
	   }
	   List<TransactionInfo> getAllTransactions();
	   List<TextSetUploadData> getAllUploadTransactions();
	   List<TransactionInfo> getTransactions(String user, String keyword,
			String objecttype);
	   String removeTransaction(String key) throws Exception;
	   Set<String> getUserSet();
	   Set<String> getKeywordSet();
	   Set<String> getObjectTypeSet();
}
