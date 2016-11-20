
/*
 * ReactBond.java
 *
 * Created on January 30, 2004, 11:18 AM
 */

package info.esblurock.info.react.data.molecules;
import java.text.ParseException;

import info.esblurock.react.common.IParsableElement;
import info.esblurock.react.common.ReactionLog;
/**
 *
 * @author  moliate
 */
public class ReactBond implements IParsableElement
{
    protected int I,J,Info;
    protected String BondType;

    public static String singleS = "Single Bond";
    public static String doubleS = "Double Bond";
    public static String tripleS = "Triple Bond";
    
    public String toString() {
		return BondType + " (" + I + "," + J + ")";
    }
    
    public void print() {
        System.out.println(" : " + BondType + " : " + I + " : " + J + " : " + Info + " : ");
	ReactionLog.logInfo(" : " + BondType + " : " + I + " : " + J + " : " + Info + " : ");
    }
    
    public int bondOrder() {
        if (BondType.startsWith("Single"))
            return 1;
        if (BondType.startsWith("Double"))
            return 2;
        if (BondType.startsWith("Triple"))
            return 3;
        return -1;
    }
    
    public void setBondOrder(int order) {
        if (1 == order)
            BondType = "Single Bond";
        if (2 == order)
            BondType = "Double Bond";
        if (3 == order)
            BondType = "Triple Bond";
      
    }
    
    /** Creates a new instance of ReactBond */
    public void setData(IParsableElement el) {
        if (! (el instanceof ReactBond) )
        {   
            ReactionLog.logError(" > Tried to parse an element of wrong type: " + el.getClass().getName() + " where " + this.getClass().getName()+ " was expected.");
            return;
        }
        
        ReactBond e = (ReactBond)el;
        
        I = e.I;
        J = e.J;
        BondType = e.BondType;
    }
    
    public void setI(int I) {this.I = I;}
    public void setJ(int J) {this.J = J;}  
    public void setInfo(int i) {Info = i;}     
    public int getI() {return I;}
    public int getJ() {return J;}  
    public int getInfo() {return Info;} 
    /**
     * @deprecated
     * @see toString()
     */      
    public String asString()
    {
	return BondType + "(" + I + "," + J + ")";
    }
    
    /** Getter for property BondType.
     * @return Value of property BondType.
     *
     */
    public java.lang.String getBondType() {
        return BondType;
    }
    
    /** Setter for property BondType.
     * @param BondType New value of property BondType.
     *
     */
    public void setBondType(java.lang.String BondType) {
        this.BondType = BondType;
    }

	@Override
	public void parse(byte[] data) throws ParseException {
		// TODO Auto-generated method stub
		
	}
    
}
