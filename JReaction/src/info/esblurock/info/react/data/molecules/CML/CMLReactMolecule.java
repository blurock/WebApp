/*
 * CMLMolecule.java
 *
 * Created on January 30, 2004, 3:57 PM
 */
package info.esblurock.info.react.data.molecules.CML;

import info.esblurock.react.common.IRestorableElement;
import thermo.data.structure.structure.StructureAsCML;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import javax.vecmath.Point3d;

import org.openscience.cdk.Atom;
import org.openscience.cdk.AtomContainer;
import org.openscience.cdk.Bond;
import org.openscience.cdk.SingleElectron;
import org.openscience.cdk.exception.CDKException;
import org.openscience.cdk.interfaces.IAtom;
import org.openscience.cdk.interfaces.IBond;
import org.openscience.cdk.interfaces.IBond.Order;
import org.xmlcml.cml.element.CMLMolecule;
import org.openscience.cdk.interfaces.ISingleElectron;

import info.esblurock.info.react.data.molecules.ReactAtom;
import info.esblurock.info.react.data.molecules.ReactBond;
import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.info.react.data.molecules.ReactPeriodicTable;

/**
 *
 * @author  moliate
 */
public class CMLReactMolecule extends ReactMolecule implements IRestorableElement {

    public int reaction_id = Integer.MIN_VALUE;
    public boolean is_reactant;
    
    public CMLReactMolecule(){
    }
    public CMLReactMolecule(ReactMolecule mol){
    	super(mol);
    }

    public void parse(byte[] data) throws java.text.ParseException {
    	
    	StructureAsCML structCML = new StructureAsCML(this.MoleculeName, new String(data));
    	try {
	    	AtomContainer molecule = structCML.getMolecule();
	    	fromCML(molecule);
		} catch (CDKException e) {
			throw new ParseException(e.toString(), 0);
		}
    }

    public byte[] restore() {
            AtomContainer molecule = toCML();
            StructureAsCML cmlstruct;
            byte[] bytes = null;
			try {
				cmlstruct = new StructureAsCML(molecule);
				bytes = cmlstruct.getCmlStructureString().getBytes();
			} catch (CDKException e) {
				e.printStackTrace();
			}
            return bytes;
    }

    public AtomContainer toCML() {
    	AtomContainer molecule = new AtomContainer();
    	ArrayList<Atom> atmlst = new ArrayList<Atom>();
    	for(ReactAtom atom : this.Atoms) {
    		String symbol = ReactPeriodicTable.AtomName(atom.AtomicNumber);
    		
    		Point3d pnt = new Point3d(atom.X,atom.Y, atom.Z);
    		Atom cdkatom = new Atom(symbol,pnt);
    		cdkatom.setID(atom.ID);
    		if(atom.Info == ReactAtom.radical) {
    			SingleElectron radical = new SingleElectron(cdkatom);
    			molecule.addSingleElectron(radical);
    		}
    		molecule.addAtom(cdkatom);
    		atmlst.add(cdkatom);
    	}
    	for(ReactBond bond : this.Bonds) {
    		Order order = Order.SINGLE;
    		if(bond.getBondType().startsWith("Double")) {
    			order = Order.DOUBLE;
    		} else if(bond.getBondType().startsWith("Triple")){
    			order = Order.TRIPLE;
    		}
    		Bond cdkbond = new Bond(atmlst.get(bond.getI()), atmlst.get(bond.getJ()),order);
    		String bondref = "a" + bond.getI() + " a" + bond.getJ() + " i" + bond.getInfo();
    		cdkbond.setID(bondref);
    	}
    	return molecule;
    }

    public void fromCML(AtomContainer molecule) {
    	HashMap<String,ReactAtom> atmmap = new HashMap<String,ReactAtom>();
    	for(IAtom atm : molecule.atoms()) {
    		String atmtype = atm.getSymbol();
    		int atomicnumber = ReactPeriodicTable.AtomNumber(atmtype);
    		Point3d p3d = atm.getPoint3d();
    		ReactAtom reactatom = new ReactAtom();
    		reactatom.AtomicNumber = atomicnumber;
    		reactatom.ID = atm.getID();
    		reactatom.X = (float) p3d.x;
    		reactatom.Y = (float) p3d.y;
    		reactatom.Z = (float) p3d.z;
    		atmmap.put(atm.getID(), reactatom);
    	}
    	for(IBond bnd : molecule.bonds()) {
    		String bondref = bnd.getID();
    		StringTokenizer tok = new StringTokenizer(bondref, " ");
    		String a1S = tok.nextToken();
    		String a2S = tok.nextToken();
    		String iS = tok.nextToken();
    		
       		int a1 = Integer.parseInt(a1S.substring(1));
       		int a2 = Integer.parseInt(a2S.substring(1));
       		int i1 = Integer.parseInt(iS.substring(1));
       		
       		ReactBond reactbond = new ReactBond();
       	    reactbond.setI(a1);
       	    reactbond.setJ(a2);
       	    reactbond.setInfo(i1);
       	    int bondorder = 1;
       	    if(bnd.getOrder() == Order.DOUBLE) {
       	    	bondorder = 2;
       	    } else if(bnd.getOrder() == Order.TRIPLE) {
       	    	bondorder = 3;
       	    }
    		reactbond.setBondOrder(bondorder);
    	}
    	for(ISingleElectron single : molecule.singleElectrons()) {
    		IAtom atm = single.getAtom();
    		String id = atm.getID();
    		ReactAtom reactatom = atmmap.get(id);
    		reactatom.Info = ReactAtom.radical;
    	}
    }
}
