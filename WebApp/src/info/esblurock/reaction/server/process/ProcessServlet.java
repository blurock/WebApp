package info.esblurock.reaction.server.process;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import info.esblurock.reaction.client.ui.login.UserDTO;
import info.esblurock.reaction.server.event.RegisterTransaction;
import info.esblurock.reaction.server.utilities.ContextAndSessionUtilities;

public class ProcessServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    	//HttpSession session = req.getSession();
    	//ServletContext context = req.getServletContext();
    	//ContextAndSessionUtilities util = new ContextAndSessionUtilities(context, session);
    	
        String processName =  req.getParameter("processName");
        String keyword  = req.getParameter("keyword");
        String user  = req.getParameter("user");
        
		DataProcesses p = DataProcesses.valueOf(processName);
		ProcessInputSpecificationsBase spec = new ProcessInputSpecificationsBase(user, keyword, null);
		ProcessBase process = p.getProcess(spec);
		//String source = processName + "#" + keyword;
		String result = process.process();
    }


}
