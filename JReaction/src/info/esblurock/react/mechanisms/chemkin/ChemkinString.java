package info.esblurock.react.mechanisms.chemkin;

import java.io.IOException;
import java.io.Serializable;
/*
 * ChemkinString.java
 * 
 * Defines tokens for each line, skips over the comment character lines
 * define by !
 * 
 *
 * Created on February 17, 2004, 8:54 PM
 */
import java.util.StringTokenizer;

import com.google.appengine.repackaged.org.apache.commons.codec.binary.BinaryCodec;

/**
 *
 * @author reaction
 */
public class ChemkinString implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StringBuffer lastComment;
	private String currentToken;
	StringTokenizer tok;
	String commentChar = "!";
	private boolean trim = true;

	/** Creates a new instance of ChemkinString */
	public ChemkinString(String input, String commentString) {
		tok = new StringTokenizer(input, "\n");
		commentChar = commentString;
	}
	
	public String nextToken() {
		String next = null;
		if (tok.hasMoreTokens()) {
			next = tok.nextToken();
		}
		//System.out.println("nextToken(): '" + next + "' (" + next.length() + ") '" + next.charAt(0) + "' '" + next.charAt(1));
		currentToken = next;
		return next;
	}
	
/*
	public String nextToken() {
		String next = null;
		lastComment = new StringBuffer();
		if (tok.hasMoreTokens()) {
			next = tok.nextToken();
			if (trim)
				next = next.trim();
			if (next == null) {
				System.out.println("token is null");
			}
			if (next.startsWith(commentChar) || next.length() == 0) {
				lastComment.append(next);
				next = nextToken();
			}
		}
		currentToken = next;
		System.out.println("nextToken(): '" + next + "' (" + next.length() + ") '" + next.charAt(0) + "' '" + next.charAt(1));
		return next;
	}
*/
	public String nextNLines(int n) throws IOException {
		StringBuilder build = new StringBuilder();
		int count = n - 1;
		build.append(getCurrent());
		while (tok.hasMoreTokens() && count-- > 0) {
			build.append(nextToken());
			build.append("\n");
		}
		if (count > 0) {
			int rest = n - count;
			String ex = "Expected " + n + "lines got only " + rest;
			throw new IOException(ex);
		}

		return build.toString();
	}

	public boolean tokenMatch(String element, String token) {
		return element.toUpperCase().startsWith(token.toUpperCase());
	}

	public String currentNonBlank() {
		String next = null;
		if (getCurrent() != null) {
			next = getCurrent().trim();
			while (next.length() == 0) {
				next = nextToken().trim();
			}
		}
		return next;
	}

	public String nextNonBlank() {
		nextToken();
		return currentNonBlank();
	}

	private boolean nonnormalStart(char c) {
		boolean ans = false;
		Integer iI = new Integer(c);
		int i = iI.byteValue();
		if (i == -1)
			ans = true;
		return ans;
	}

	private String normalStartingCharacter(String line) {
		if (line != null) {
			line = line.trim();
			while (nonnormalStart(line.charAt(0))) {
				line = line.substring(1);
				System.out.println("---> nonnormal");
			}
		}
		return line;
	}

	public String skipOverComments() {
		String comments = "";
		String next = currentNonBlank();
		boolean notdone = true;
		while (notdone) {
			next = normalStartingCharacter(next);
			if (next == null) {
				notdone = false;
			} else if (next.startsWith(commentChar)) {
				comments += next;
				next = nextNonBlank();
			} else {
				notdone = false;
			}
		}
		return comments;
	}

	String getLastComment() {
		return lastComment.toString();
	}

	public String getCurrent() {
		return currentToken;
	}

	public String getCommentChar() {
		return commentChar;
	}

	public boolean isTrim() {
		return trim;
	}

	public void setTrim(boolean trim) {
		this.trim = trim;
	}

}
