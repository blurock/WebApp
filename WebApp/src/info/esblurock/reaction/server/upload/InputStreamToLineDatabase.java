package info.esblurock.reaction.server.upload;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import com.google.appengine.api.datastore.Text;

import info.esblurock.reaction.data.PMF;
import info.esblurock.reaction.data.upload.DeleteTextSetUploadData;
import info.esblurock.reaction.data.upload.FileUploadTextBlock;
import info.esblurock.reaction.data.upload.UploadFileTransaction;

/**
 * @author edwardblurock
 *
 */
public class InputStreamToLineDatabase {
	private static final Logger log = Logger.getLogger(InputStreamToLineDatabase.class.getName());

	private int totalcount;
	private int bytecount;
	
	private int MAX_BLOCK_BYTE_SIZE = 800000;

	private String transactionKey;
	public UploadFileTransaction uploadFile(UploadFileTransaction transaction, BufferedReader buf)
			throws IOException {
		try {
			//PersistenceManager pm = PMF.get().getPersistenceManager();
			StringBuilder buffer = new StringBuilder();
			String line;
			bytecount = 0;
			int beginLineCount = 0;
			boolean notdone = true;
			while (notdone) {
				line = buf.readLine();
				if (line != null) {
					bytecount += line.length() + 3;
					if(bytecount > MAX_BLOCK_BYTE_SIZE) {
						System.out.println("Writing Block: buffersize=" + buffer.toString().length());
						System.out.println("First line of next buffer: '" + line + "'");
						writeTextBlock(buffer.toString(),beginLineCount,transaction.getFileCode());
						bytecount = line.length() + 1;
						
						StringTokenizer tok = new StringTokenizer(buffer.toString(),"\n");
						beginLineCount = totalcount;
						buffer = new StringBuilder();
					}
					totalcount++;
					buffer.append(line);
					buffer.append("  \n");
				} else {
					notdone = false;
				}
			}
			writeTextBlock(buffer.toString(),beginLineCount,transaction.getFileCode());
			transaction.setLineCount(totalcount);
		} catch (Exception ex) {
			if (transaction != null) {
				transaction.setLineCount(0);
				log.log(Level.SEVERE, "ERROR write incomplete transaction: ");
			}
			log.log(Level.SEVERE, "ERROR in upload file: " + ex.toString());
			throw new IOException("ERROR in upload file: " + ex.toString());
		}
		return transaction;

	}
	private void writeTextBlock(String text, int beginLineCount, String fileCode) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Text textblock = new Text(text);
		FileUploadTextBlock block = new FileUploadTextBlock(beginLineCount, totalcount-1, 
				fileCode,textblock);
		pm.makePersistent(block);
		pm.flush();
		pm.close();
	}
	
	public Set<UploadFileTransaction> getUploadedFiles() {
		HashSet<UploadFileTransaction> transset = new HashSet<UploadFileTransaction>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		javax.jdo.Query q = pm.newQuery(UploadFileTransaction.class);
		try {
			List<UploadFileTransaction> results = (List<UploadFileTransaction>) q.execute();
			if (!results.isEmpty()) {
				for (UploadFileTransaction transaction : results) {
					UploadFileTransaction trans = pm.detachCopy(transaction);
					transset.add(trans);
				}
			} else {
				// Handle "no results" case
			}
		} finally {
			q.closeAll();
			pm.close();
		}
		return transset;
	}

	public String removeUpload(String key) {
		String ans = "SUCCESS";
		try {
			PersistenceManager pm = PMF.get().getPersistenceManager();
			UploadFileTransaction uploadinfo = pm.getObjectById(UploadFileTransaction.class, key);
			DeleteTextSetUploadData delete = new DeleteTextSetUploadData();
			ans = delete.removeUpload(key, uploadinfo);
		} catch (IOException ex) {
			log.log(Level.SEVERE, ex.toString());
		} catch (Exception ex) {
			log.log(Level.SEVERE, ex.toString());
		}
		return ans;
	}
	public String getTransactionKey() {
		return transactionKey;
	}

}
