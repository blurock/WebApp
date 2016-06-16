package info.esblurock.reaction.server;

import info.esblurock.reaction.data.transaction.TransactionInfo;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;
import info.esblurock.reaction.server.upload.StoreUploadedFileData;
import info.esblurock.reaction.server.upload.UploadedFileData;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import gwtupload.client.File;

import org.apache.commons.fileupload.FileItem;

import gwtupload.server.exceptions.UploadActionException;
import gwtupload.server.gae.*;
import gwtupload.shared.UConsts;

/**
 * This is an example of how to use UploadAction class.
 * 
 * This servlet saves all received files in a temporary folder, and deletes them
 * when the user sends a remove request.
 * 
 * @author Manolo Carrasco Moñino
 *
 */



public class ReactionUploadServiceImpl extends AppEngineUploadAction {

	private static final long serialVersionUID = 1L;
	
    private ContextAndSessionUtilities getUtilities() {
        HttpSession session = getThreadLocalRequest().getSession();
    	ContextAndSessionUtilities util = new ContextAndSessionUtilities(this.getServletContext(),session);
    	return util;
    }

	Hashtable<String, String> receivedContentTypes = new Hashtable<String, String>();
	/**
	 * Maintain a list with received files and their content types.
	 */
	Hashtable<String, File> receivedFiles = new Hashtable<String, File>();

	/**
	 * Override executeAction to save the received files in a custom place and
	 * delete this items from session.
	 */
	@Override
	public String executeAction(HttpServletRequest request,
			List<FileItem> sessionFiles) throws UploadActionException {
		String response = "";
		ContextAndSessionUtilities util = getUtilities();
		// request.getSession().setAttribute(arg0, arg1);
		for (FileItem item : sessionFiles) {
			if (false == item.isFormField()) {
				try {
					String out = item.getFieldName() + "\n";
					out += item.getName() + "\n";
					out += item.getContentType() + "\n";
					out += item.getFieldName() + "\n  with size of "
							+ item.getSize() + "\n";
					System.out.println(out);
					/*
					InputStream str = item.getInputStream();
					InputStreamReader fstr = new InputStreamReader(str);
					BufferedReader buf = new BufferedReader(fstr);
					InputStreamToLineDatabase input = new InputStreamToLineDatabase();
					String user = util.getUserName();
					String type = "Upload";
					//UploadFileTransaction upload = input.uploadFile(user, item.getFieldName(), type, buf);
					//response += upload.getKey() + " ";
					 */
					
					
				} catch (Exception e) {
					throw new UploadActionException(e);
				}
			}

		}
		removeSessionFileItems(request);
		return response;
	}

	
	/**
	 * Get the content of an uploaded file.
	 */
	@Override
	public void getUploadedFile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String fieldName = request.getParameter(UConsts.PARAM_SHOW);
		File file = receivedFiles.get(fieldName);
		if (file != null) {
			response.setContentType(receivedContentTypes.get(fieldName));
			FileInputStream is = new FileInputStream(file.toString());
			copyFromInputStreamToOutputStream(is, response.getOutputStream());
		} else {
			renderXmlResponse(request, response, XML_ERROR_ITEM_NOT_FOUND);
		}
	}

	/**
	 * Remove a file when the user sends a delete request.
	 */
	@Override
	public void removeItem(HttpServletRequest request, String fieldName)
			throws UploadActionException {
		File file = receivedFiles.get(fieldName);
		receivedFiles.remove(fieldName);
		receivedContentTypes.remove(fieldName);
		if (file != null) {
			((FileItem) file).delete();
		}
	}
}