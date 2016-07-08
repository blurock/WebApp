package info.esblurock.reaction.server.process;

import java.io.IOException;
import java.util.ArrayList;

import javax.jdo.PersistenceManager;

import info.esblurock.reaction.client.FindShortNameFromString;
import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.datastore.PMF;
import info.esblurock.reaction.server.process.upload.ReadFileBaseProcess;

public class ProcessUtilities {
	static public UploadFileTransaction getUploadFileTransactionFromProcess(String processName) throws IOException {
		UploadFileTransaction uploadtrans = null;
		String shortname = FindShortNameFromString.findShortName(processName, ".");
		System.out.println("UploadFileTransaction getUploadFileTransaction processName=" + shortname);
		DataProcesses p = DataProcesses.valueOf(shortname);
		ProcessBase empty = p.getEmptyProcess();
		Class filecls = null;
		try {
			filecls = Class.forName("info.esblurock.reaction.server.process.upload.ReadFileBaseProcess");
		} catch (ClassNotFoundException e) {
			throw new IOException("ReadFileBaseProcess could not be created");
		}
		Class transcls = null;
		try {
			transcls = Class.forName("info.esblurock.reaction.data.upload.UploadFileTransaction");
		} catch (ClassNotFoundException e) {
			throw new IOException("UploadFileTransaction could not be created");
		}
		
		System.out.println("Class  : " + filecls.getName());
		System.out.println("Process: " + empty.getClass().getName());
		
		if (filecls.isAssignableFrom(empty.getClass())) {
			ReadFileBaseProcess process = (ReadFileBaseProcess) empty;
			Class objclass;
			try {
				System.out.println("UploadFileTransaction getUploadTransactionType(): " + process.getUploadTransactionType());
				objclass = Class.forName(process.getUploadTransactionType());
				if (transcls.isAssignableFrom(objclass)) {
					uploadtrans = (UploadFileTransaction) objclass.newInstance();
					System.out.println("objclass.newInstance(): " + uploadtrans);
				} else {
					throw new IOException(
							"Expected transaction of type: UploadFileTransaction, got" + objclass.getName());
				}
			} catch (ClassNotFoundException e) {
				throw new IOException("ClassNotFoundException: Transaction type could not be initialized: " 
						+ process.getUploadTransactionType());
			} catch (InstantiationException e) {
				throw new IOException("InstantiationException: Transaction type could not be initialized: " 
						+ process.getUploadTransactionType());
			} catch (IllegalAccessException e) {
				throw new IOException("IllegalAccessException: Transaction type could not be initialized: " 
						+ process.getUploadTransactionType());
			}
		} else {
			throw new IOException("Expected process of type: ReadFileBaseProcess, got " + empty.getClass().getName());
		}
		return uploadtrans;
	}
	static public UploadFileTransaction getUploadFileTransaction(String className) throws IOException {
		UploadFileTransaction uploadtrans = null;
		Class transcls = null;
		try {
			transcls = Class.forName("info.esblurock.reaction.data.upload.UploadFileTransaction");
		} catch (ClassNotFoundException e) {
			throw new IOException("UploadFileTransaction could not be created");
		}
		Class objclass;
			try {
				objclass = Class.forName(className);
				if (transcls.isAssignableFrom(objclass)) {
					uploadtrans = (UploadFileTransaction) objclass.newInstance();
					System.out.println("objclass.newInstance(): " + uploadtrans);
				} else {
					throw new IOException(
							"Expected transaction of type: UploadFileTransaction, got" + objclass.getName());
				}
			} catch (ClassNotFoundException e) {
				throw new IOException("ClassNotFoundException: Transaction type could not be initialized: " 
						+ className);
			} catch (InstantiationException e) {
				throw new IOException("InstantiationException: Transaction type could not be initialized: " 
						+ className);
			} catch (IllegalAccessException e) {
				throw new IOException("IllegalAccessException: Transaction type could not be initialized: " 
						+ className);
			}
		return uploadtrans;
	}
	static public void deleteEmptyTransactionInfo(ArrayList<TransactionInfo> transactionOutputs) {
		boolean notcompleted = false;
		for (TransactionInfo info : transactionOutputs) {
			if (info.getStoredObjectKey() == null) {
				notcompleted = true;
			}
		}
		if (notcompleted) {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			for (TransactionInfo info : transactionOutputs) {
				if(info.getStoredObjectKey() == null) {
					pm.deletePersistent(info);
				} else {
					pm.deletePersistent(info);
				}
			}
		}
	}

}
