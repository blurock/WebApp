package info.esblurock.reaction.client;

import java.io.IOException;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import info.esblurock.reaction.data.description.DescriptionDataData;


@RemoteServiceRelativePath("reactionprocessuploadedlines")
public interface ReactionProcessUploadedLines extends RemoteService {

	   public static class Util
	   {
	       private static ReactionProcessUploadedLinesAsync instance;

	       public static ReactionProcessUploadedLinesAsync getInstance()
	       {
	           if (instance == null) {
	               instance = GWT.create(ReactionProcessUploadedLines.class);
	           }
	           return instance;
	       }
	   }
	   String processUploadedMechanism(DescriptionDataData description, String key, String filename, boolean process) throws IOException;
}
