package info.esblurock.reaction.json;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class ParsingCrossRefJSON {

	@Test
	public void test() {
		try {
			String source = "resources/crossref.txt";
			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource(source).getFile());
			FileReader reader = new FileReader(file);
			BufferedReader breader = new BufferedReader(reader);
			String line = breader.readLine();
			StringBuilder build = new StringBuilder();
			while(line != null) {
				build.append(line + "\n");
				line = breader.readLine();
			}
			System.out.println(build.toString());
			
			JSONObject json = new JSONObject(build.toString());
			System.out.println("Status: " + json.getString("status"));
			JSONObject message = json.getJSONObject("message");
			JSONArray authors = message.getJSONArray("author");
			for(int i=0 ; i < authors.length() ; i++) {
				JSONObject author = authors.getJSONObject(i);
				System.out.println("Given:  " + author.getString("given"));
				System.out.println("Family: " + author.getString("family"));
			}
			JSONArray titles = message.getJSONArray("title");
			System.out.println("Title: " + titles.getString(0));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
