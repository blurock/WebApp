package info.esblurock.reaction.client.panel.transaction;

import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.FileUploadTextBlock;
import info.esblurock.reaction.data.upload.TextSetUploadData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface TransactionServiceAsync {

	void getTransactions(String user, String keyword, String objecttype,
			AsyncCallback<List<TransactionInfo>> callback);

	void removeTransactionOfObject(String key, AsyncCallback<String> callback);

	void getAllTransactions(AsyncCallback<List<TransactionInfo>> callback);

	void getUserSet(AsyncCallback<Set<String>> callback);

	void getKeywordSet(AsyncCallback<Set<String>> callback);

	void getObjectTypeSet(AsyncCallback<Set<String>> callback);

	void getAllUploadTransactions(AsyncCallback<List<TextSetUploadData>> callback);

	void removeTransactionWithTypeAndKeyword(String objecttype, String keyword, AsyncCallback<String> callback);

	void deleteTransactionInfoFromKey(String objectType, String keyword, String transactionkey,
			AsyncCallback<String> callback);

	void removeFromRDFsFromDate(Date date, AsyncCallback<String> callback);

	void findValidProcessing(String keyword, AsyncCallback<List<String>> callback);

	void runProcess(String processName, String keyword, AsyncCallback<String> callback);

	void getTransactionInfo(String datasetkeyword, String classname, AsyncCallback<TransactionInfo> callback);

	void getFileUploadTextBlockFromTransaction(String datasetkeyword, String classname,
			AsyncCallback<ArrayList<String>> callback);

}
