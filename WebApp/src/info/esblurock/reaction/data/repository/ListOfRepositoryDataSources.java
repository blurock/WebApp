package info.esblurock.reaction.data.repository;

import java.io.Serializable;
import java.util.ArrayList;

public class ListOfRepositoryDataSources extends ArrayList<DataPathName> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static String sourceKey = "sourcekey";	
	public static String keyword = "keyword";
	public static String path = "dataSetPath";
	
	public String toString() {
		StringBuilder build = new StringBuilder();
		boolean first = true;
		for(DataPathName dataset : this) {
			if(first) {
				build.append("'" + dataset.toString() + "'");
				first = false;
			} else {
				build.append(", '" + dataset.toString() + "'");				
			}
		}
		return build.toString();
	}
}
