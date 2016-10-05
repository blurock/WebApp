package info.esblurock.reaction.server;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.input.ReaderInputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class FileUploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

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

                ServletOutputStream out = response.getOutputStream();
                response.setBufferSize(32768);

                InputStream in = fileItem.openStream();
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader breader = new BufferedReader(reader);
                String line = breader.readLine();
                out.print(line);
                int length = 0;
                while(line != null) {
                	length += line.length();
                	line = breader.readLine();
                }
                response.setContentType("text/html");
                response.setContentLength(
                        (length > 0 && length <= Integer.MAX_VALUE) ? (int) length : 0);

                in.close();
                out.flush();
                out.close();
            }
        } catch(Exception caught) {
            throw new RuntimeException(caught);
        }
    }
}