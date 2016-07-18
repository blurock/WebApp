package info.esblurock.reaction.data.transaction;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class ProcessPrerequisiteData extends DatabaseObject {

    /** The input transaction object class name (as a string)**/
    @Persistent
    String transactionObjectTypeInput;

    /** The output transaction object class name (as a string)**/
    @Persistent
    String transactionObjectTypeOutput;

    /** The name of the process */
    @Persistent
    String processName;
    
}
