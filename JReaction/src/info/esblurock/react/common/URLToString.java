package info.esblurock.react.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class URLToString {
	String url;

	public URLToString(String url) {
		this.url = url;
	}

	public String toString() {
		URL website;
		StringBuilder response = new StringBuilder();
		try {
			website = new URL(url);
			URLConnection connection = website.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
				response.append("\n");
			}
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return response.toString();

	}
}
