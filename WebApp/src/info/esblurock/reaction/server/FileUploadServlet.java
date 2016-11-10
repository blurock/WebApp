package info.esblurock.reaction.server;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import info.esblurock.reaction.client.ui.login.UserDTO;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.CreateBufferedReaderForSourceFile;
import info.esblurock.reaction.server.upload.InputStreamToLineDatabase;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;
import info.esblurock.reaction.server.utilities.ManageDataSourceIdentification;
import info.esblurock.reaction.server.utilities.WriteObjectTransactionToDatabase;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.*;

public class FileUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    public static String uploadSource = "Upload";

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {

        if (! ServletFileUpload.isMultipartContent(request)) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    "Not a multipart request");
            return;
        }

        ServletFileUpload upload = new ServletFileUpload(); // from Commons

        try {
            FileItemIterator iter = upload.getItemIterator(request);

            if (iter.hasNext()) {
                FileItemStream fileItem = iter.next();

        		HttpSession session = request.getSession();
        		ContextAndSessionUtilities util 
        			= new ContextAndSessionUtilities(getServletContext(), session);
        		UserDTO user = util.getUserInfo();
          
				ServletOutputStream out = response.getOutputStream();
                response.setBufferSize(32768);
                String outputSourceCode = ManageDataSourceIdentification.getDataSourceIdentification(user.getName());
                InputStreamToLineDatabase input = new InputStreamToLineDatabase();
                UploadFileTransaction utransaction = 
                		new UploadFileTransaction(user.getName(), 
                				fileItem.getName(), 
                				outputSourceCode, 
                				CreateBufferedReaderForSourceFile.uploadFileAsSource, 0);
                InputStream in = fileItem.openStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader breader = new BufferedReader(reader);
                input.uploadFile(utransaction, breader);
    			WriteObjectTransactionToDatabase.writeObjectWithoutTransaction(utransaction);
                in.close();
                out.flush();
                out.close();
            }
        } catch(Exception caught) {
            throw new RuntimeException(caught);
        }
    }
}