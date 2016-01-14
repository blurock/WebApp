package info.esblurock.reaction.data;

import java.text.DateFormat;
import java.util.Date;

public class DateAsString {
	static DateFormat format = DateFormat.getInstance();
	public static String dateAsString(Date date) {
		return format.format(date);
	}
}
