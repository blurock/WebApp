package info.esblurock.reaction.data.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import info.esblurock.reaction.client.panel.repository.RepositoryPath;

public class DataPathName implements Serializable {
	private static final long serialVersionUID = 1L;

	String sourceKey;
	String keyword;
	ArrayList<String> path;
	
	public DataPathName() {
		
	}
	
	public DataPathName(String sourceKey, String keyword, ArrayList<String> path) {
		super();
		this.sourceKey = sourceKey;
		this.keyword = keyword;
		this.path = path;
	}
	public DataPathName(RepositoryPath path) {
		super();
		int cnt = path.size();
		this.sourceKey = path.get(0);
		for(int i=1; i<cnt-1;i++) {
			this.path.add(path.get(i));
		}
		this.keyword = path.get(cnt-1);
	}
	public String getSourceKey() {
		return sourceKey;
	}
	public String getKeyword() {
		return keyword;
	}
	public RepositoryPath asRepositoryPath() {
		RepositoryPath lst = new RepositoryPath();
		lst.add(sourceKey);
		if(path != null) {
			lst.addAll(path);
		}
		lst.add(keyword);
		return lst;
	}
	public String dataSetName() {
		return sourceKey + "#" + keyword;
	}
	public String toString() {
		return sourceKey + "#" + keyword + ":" + path;
	}
}
