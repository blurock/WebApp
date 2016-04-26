package info.esblurock.reaction.client;

import com.google.gwt.user.client.Window;

public class FindShortNameFromString {
	public static String findShortName(String classname, String delimiter) {
		String ans = classname;
		boolean notdone = true;
		while(notdone) {
			int pos = ans.indexOf(delimiter);
			if(pos >= 0) {
				ans = ans.substring(pos+1);
			} else {
				notdone = false;
			}
		}
		return ans;
	}
	public static String findShortClassName(String classname) {
		return findShortName(classname, ".");
	}
}
