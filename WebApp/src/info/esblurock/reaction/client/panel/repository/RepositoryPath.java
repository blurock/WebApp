package info.esblurock.reaction.client.panel.repository;

import java.io.Serializable;
import java.util.ArrayList;

import info.esblurock.reaction.data.DatabaseObject;

public class RepositoryPath extends ArrayList<String> implements Serializable {
	private static final long serialVersionUID = 1L;
	DatabaseObject endNode;
	
	public RepositoryPath() {
		endNode = null;
	}
	public RepositoryPath(ArrayList<String> path, String label) {
		endNode = null;
		this.addAll(path);
		this.add(label);
	}
	public RepositoryPath(ArrayList<String> path) {
		endNode = null;
		this.addAll(path);
	}
	public RepositoryPath(ArrayList<String> path, DatabaseObject obj) {
		endNode = obj;
		this.addAll(this);
	}
	public DatabaseObject getEndNode() {
		return endNode;
	}
	
}
