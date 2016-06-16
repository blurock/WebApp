package info.esblurock.reaction.data.upload.types;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CreateBufferedReaderForSourceFile {
	public static String textAsSource = "Text";
	public static String httpAsSource = "Http";


	static public BufferedReader getBufferedReader(String sourceType, String textName, String textBody) throws IOException {
		InputStream stream = null;
		if(sourceType.equals(textAsSource)) {
			stream = new ByteArrayInputStream(textBody.getBytes(StandardCharsets.UTF_8));
		} else if(sourceType.equals(httpAsSource)) {
			URL url = new URL(textName);
			stream = url.openStream();
		} else {
			throw new IOException("Source Type not found: " + sourceType);
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new IOException(e.toString());
		}
		return br;
	}

}