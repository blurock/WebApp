package info.esblurock.reaction.data.upload.types;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

public class CreateBufferedReaderForSourceFile {
	public static String textAsSource = "Text";
	public static String httpAsSource = "Http";
	public static String uploadFileAsSource = "File";

	static public BufferedReader getBufferedReader(String sourceType, String textName, String textBody) throws IOException {
		InputStream stream = null;
		BufferedReader br = null;
		if(sourceType.equals(textAsSource)) {
			stream = new ByteArrayInputStream(textBody.getBytes(StandardCharsets.UTF_8));
		} else if(sourceType.equals(httpAsSource)) {
			URL url = new URL(textName);
			URLConnection connection = url.openConnection();
			stream = connection.getInputStream();
		} else if(sourceType.equals(uploadFileAsSource)) {
			br = null;
		} else {
			throw new IOException("Source Type not found: " + sourceType);
		}
		if(stream != null) {
			br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
		}
		return br;
	}

}
