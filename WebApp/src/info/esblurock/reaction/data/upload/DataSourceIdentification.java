package info.esblurock.reaction.data.upload;

import java.io.Serializable;

import javax.jdo.annotations.Extension;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class DataSourceIdentification {

	@PrimaryKey
    @Persistent
    String user;

    @Persistent
    Integer count;
	
    public DataSourceIdentification() {
    	
    }
    public DataSourceIdentification(String user) {
    	this.user = user;
    	this.count = new Integer(0);
    }
    
    public String getCountAsString() {
    	return count.toString();
    }
    public int increment() {
    	return ++count;
    }
}
