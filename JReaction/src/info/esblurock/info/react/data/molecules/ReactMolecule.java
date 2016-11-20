// ===================================================================================================================================
//  Author: Henrik Springfors (moliate@yahoo.se)
//   Copyright 2003
// 
// ===================================================================================================================================

package info.esblurock.info.react.data.molecules;
//import debug.utilities.*;
import info.esblurock.react.common.IParsableElement;
import info.esblurock.react.common.ReactionLog;
import java.util.*;

public abstract class ReactMolecule implements IParsableElement
{
    protected String molcommand;
    protected String MoleculeName;
    protected int ID;
    protected Integer CHOHashCode = null;
    protected Integer HashCode = null;
 
    protected int NumberOfBonds;
    protected int NumberOfAtoms;
    
    protected Vector<ReactAtom> Atoms = new Vector<ReactAtom>();
    protected Vector<ReactBond> Bonds = new Vector<ReactBond>();
    


    public ReactMolecule() {
    }
    public ReactMolecule(ReactMolecule mol) {
    	Atoms = mol.getAtoms();
    	Bonds = mol.getBonds();
    	molcommand = mol.molcommand;
    	MoleculeName = mol.MoleculeName;
    	ID = mol.ID;
    	NumberOfBonds = mol.NumberOfBonds;
    	NumberOfAtoms = mol.NumberOfAtoms;
    	createHashCode();
    }
    
    
    public int nbrOfBonds()
    {
        return Bonds.size();
    }
    
    public int nbrOfAtoms()
    {
        return Atoms.size();
    }
    
    public void createHashCode() 
    {
        int iCHOHashCode = 0;
        int iHashCode = 0;
        int extra = 1000000;
        for(int i=0;i<NumberOfAtoms;i++) {
            ReactAtom atom = (ReactAtom) Atoms.elementAt(i);
            int atnum = atom.AtomicNumber;
            if(atnum == 6)
                iCHOHashCode += 1;
            else if(atnum == 1) 
                iCHOHashCode += 100;
            else if(atnum == 8)
                iCHOHashCode += 10000;
            else {
                iHashCode += atnum*extra;
                extra *= 100;
            }
        }
        CHOHashCode = new Integer(iCHOHashCode);
        HashCode = new Integer(iCHOHashCode + iHashCode);
    }
    
    public boolean isIsomer(ReactMolecule mol) 
    {
        if(HashCode == null)
            createHashCode();
        if(mol.HashCode == null)
            mol.createHashCode();
        
        return HashCode.equals(mol.HashCode);
    }
    
    public ReactAtom getAtom(int index) throws NullPointerException 
    {
        if(index < 0 || index >= Atoms.size()) {
            throw new NullPointerException("Atom not Found: " + index);
        } else {
            return (ReactAtom) Atoms.elementAt(index);
        }
    }
    
    public ReactBond getBond(int index) throws NullPointerException 
    {
        if(index < 0 || index >= Bonds.size()) {
            throw new NullPointerException("Bond not Found: " + index);
        } else {
            return (ReactBond) Bonds.elementAt(index);  
        }
    }
    
    public void addAtom(ReactAtom atom)  {
        //Log.println(atom.toString());
	Atoms.addElement(atom);
    }

    public void addBond(ReactBond bond)  {
        //Log.println(bond.toString());
	Bonds.addElement(bond);
    }

    public void print()
    {
            //System.out.println("\""+ MoleculeName+"\"");
	    ReactionLog.logInfo("\""+ MoleculeName+"\"");
	    for(int i=0; i < nbrOfAtoms() ; i++)
	    {
			ReactAtom a = (ReactAtom) Atoms.elementAt(i);
			a.print();
	    }
	    for(int j=0; j < nbrOfBonds() ; j++)
	    {
			ReactBond b = (ReactBond) Bonds.elementAt(j);
			b.print();
	    }
    }
        
    public void setData(IParsableElement element) 
    {
	if (! (element instanceof ReactMolecule) )
        {   
            ReactionLog.logError(" > Tried to parse an element of wrong type: " + element.getClass().getName() + " where " + this.getClass().getName()+ " was expected.");
            return;
        }
        
        ReactMolecule e = (ReactMolecule)element;
        
        MoleculeName = e.MoleculeName;
    	ID = e.ID;
    	NumberOfBonds = e.nbrOfBonds();
    	NumberOfAtoms = e.nbrOfAtoms();
        Atoms = new Vector<ReactAtom>();
        Iterator<ReactAtom> iteratm =  e.Atoms.iterator();
        while(iteratm.hasNext()) {
            ReactAtom atm = iteratm.next();
            Atoms.add(atm);
        }
        Bonds = new Vector<ReactBond>();
        Iterator<ReactBond> iterbnd =  e.Bonds.iterator();
        while(iterbnd.hasNext()){
            ReactBond bnd = iterbnd.next();
            Bonds.add(bnd);
        }
    }
    
    public int getAtomicNumber(String atom)
    {
        return ReactPeriodicTable.AtomNumber(atom);
    }
    
    @SuppressWarnings("empty-statement")
    public boolean has2Dlayout()
    {
        boolean ok = false;
        for (int i = 0; i < Atoms.size(); i++)
            if ( ((ReactAtom)Atoms.get(i)).X != 0 || ((ReactAtom)Atoms.get(i)).Y != 0)
                {ok=true; break;};
        
        return ok;
    }
  
    @SuppressWarnings("empty-statement")
    public boolean has3Dlayout()
    {
        boolean ok = false;
        for (int i = 0; i < Atoms.size(); i++)
            if ( ((ReactAtom)Atoms.get(i)).Y != 0)
                {ok=true; break;};
        
        return ok;
    }
    /** Getter for property Atoms.
     * @return Value of property Atoms.
     *
     */
    public java.util.Vector getAtoms() {
        return Atoms;
    }
    

    /** Getter for property Bonds.
     * @return Value of property Bonds.
     *
     */
    public java.util.Vector getBonds() {
        return Bonds;
    }
    

    
    /** Getter for property MoleculeName.
     * @return Value of property MoleculeName.
     *
     */
    public java.lang.String getMoleculeName() {
        return MoleculeName;
    }
    
    /** Setter for property MoleculeName.
     * @param MoleculeName New value of property MoleculeName.
     *
     */
    public void setMoleculeName(java.lang.String MoleculeName) {
        this.MoleculeName = MoleculeName;
    }
    
    /** Getter for property ID.
     * @return Value of property ID.
     *
     */
    public int getID() {
        return ID;
    }
    
    /** Setter for property ID.
     * @param ID New value of property ID.
     *
     */
    public void setID(int ID) {
        this.ID = ID;
    }
    public String toString() {
        StringBuffer buf = new StringBuffer();

        buf.append("Molecule: '" + this.getMoleculeName() + "'");
        buf.append("Atoms: " + this.nbrOfAtoms() + "\t Bonds: " + this.nbrOfBonds() + "\n");
        Iterator<ReactAtom> iteratm = this.Atoms.iterator();
        while(iteratm.hasNext()) {
            ReactAtom atm = iteratm.next();
            buf.append(atm.toString());
            buf.append("\n");
        }
         Iterator<ReactBond> iterbnd = this.Bonds.iterator();
        while(iterbnd.hasNext()) {
            ReactBond bnd = iterbnd.next();
            buf.append(bnd.toString());
            buf.append("\n");
        }
        return buf.toString();
    }
}
