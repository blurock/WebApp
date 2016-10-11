package info.esblurock.react.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * @author edwardblurock
 *
 */
/**
 * @author edwardblurock
 *
 */
public class ReadToString {
	private String urlToken = "URL";
	private String fileToken = "FILE";
	private String resourceToken = "RESOURCE";
	
	String expectedS = "Expected Token: " + urlToken + ", " + fileToken + " or " + resourceToken;
	
	public ReadToString() {
	}

	/** Parse a source file from URL, FILE or RESOURCE
	 * @param line The specification
	 * @return The source as a string
	 * @throws IOException
	 * 
	 * The specification has the form:
	 *  type  specification
	 *  where: 
	 *  type:  URL, FILE or RESOURCE
	 *  specification: The string source (no blanks)
	 * 
	 */
	public String parseFromLineSpecification(String line) throws IOException {
		StringTokenizer tok = new StringTokenizer(line," ");
		String result = null;
		if(tok.countTokens() == 2) {
			String token = tok.nextToken();
			String name = tok.nextToken();
			if(token.compareToIgnoreCase(urlToken) == 0) {
				URL url = new URL(name);
				result = fromURL(url);
			} else if(token.compareToIgnoreCase(fileToken) == 0) {
				File file = new File(name);
				result = fromFile(file);
			} else if(token.compareToIgnoreCase(resourceToken) == 0) {
				result = fromResource(name);
			} else {
				throw new IOException(expectedS + "\ngot" + line);
			}
		} else {
			throw new IOException(expectedS + " and source string(no blanks)\ngot" + line);
		}
		return result;
		
	}
	
	/** Parse from a resource specification
	 * @param resource The resource specification
	 * @return The source as a sring
	 * @throws FileNotFoundException
	 */
	public String fromResource(String resource) throws FileNotFoundException {
		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(resource).getFile());
		return fromFile(file);
	}
	
	/** Parse from a file specification
	 * @param file The file system specification
	 * @return The file parsed to string
	 * @throws FileNotFoundException
	 */
	public String fromFile(File file) throws FileNotFoundException {
		Scanner scan = new Scanner(file);
		return fromScanner(scan);
	}
	/** Parse form a URL
	 * @param url The URL of the source
	 * @return The file from the url parsed to a string
	 * @throws IOException
	 */
	public String fromURL(URL url) throws IOException {
		URLConnection connection = url.openConnection();
		InputStreamReader reader = new InputStreamReader(connection.getInputStream(),StandardCharsets.UTF_8);
		BufferedReader buf = new BufferedReader(reader);
		return readLines(buf);
	}
	/**
	 * 
	 */
	public String readLines(BufferedReader buf) {
		StringBuilder build = new StringBuilder();
		try {
		String line = buf.readLine();
		while(line != null) {
			build.append(line);
			build.append("\n");
			line = buf.readLine();
		}
		} catch(IOException ex) {
			System.out.println("readLine exception");
		}
		return build.toString();
	}
	/** Parse from a Scanner class
	 * @param scan The scanner class
	 * @return the parsed file
	 * 
	 * This transfers the Scanner line by line to a String
	 * 
	 */
	public String fromScanner(Scanner scan) {
		StringBuilder build = new StringBuilder();
		while(scan.hasNextLine()) {
			String next = scan.nextLine();
			build.append(next);
			build.append("\n");
		}
		return build.toString();
	}
	
}
