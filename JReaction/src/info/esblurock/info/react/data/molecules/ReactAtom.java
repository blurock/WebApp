
package info.esblurock.info.react.data.molecules;
import info.esblurock.react.common.IParsableElement;
import info.esblurock.react.common.ReactionLog;

public abstract class ReactAtom implements IParsableElement
{
    public float X,Y,Z;
    public int AtomicNumber;
    public int Info = 0;
    public String ID;
    
    public void print()
    {
		System.out.println("AtomicNumber:   : " + AtomicNumber);
		System.out.println("X,Y,Z:          : " + X + ", " + Y + ", " + Z);         
		ReactionLog.logInfo("AtomicNumber:   : " + AtomicNumber);
		ReactionLog.logInfo("X,Y,Z:          : " + X + ", " + Y + ", " + Z); 	
    }
    
    public String toString()
    {
		String name = ReactPeriodicTable.AtomName(AtomicNumber);
		if(Info == 4) {
	    	name += ".";
		}
		return "\""+name+"\"";
    }
    
    public void setData(IParsableElement el)
    {
        if (! (el instanceof ReactAtom) )
        {   
            ReactionLog.logError(" > Tried to parse an element of wrong type: " + el.getClass().getName() + " where " + this.getClass().getName()+ " was expected.");
            return;
        }
        
        ReactAtom e = (ReactAtom)el;
        X = e.X;
        Y = e.Y;
        Z = e.Z;
        AtomicNumber = e.AtomicNumber;
        Info = e.Info;
    }

}
