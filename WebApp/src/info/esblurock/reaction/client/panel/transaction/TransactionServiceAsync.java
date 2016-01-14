package info.esblurock.reaction.client.panel.transaction;

import info.esblurock.reaction.data.transaction.TransactionInfo;

import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TransactionServiceAsync {

	void getTransactions(String user, String keyword, String objecttype,
			AsyncCallback<List<TransactionInfo>> callback);

	void removeTransaction(String key, AsyncCallback<String> callback);

	void getAllTransactions(AsyncCallback<List<TransactionInfo>> callback);

	void getUserSet(AsyncCallback<Set<String>> callback);

	void getKeywordSet(AsyncCallback<Set<String>> callback);

	void getObjectTypeSet(AsyncCallback<Set<String>> callback);

}
