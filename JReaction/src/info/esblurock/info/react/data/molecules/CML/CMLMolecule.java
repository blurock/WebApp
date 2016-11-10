/*
 * CMLMolecule.java
 *
 * Created on January 30, 2004, 3:57 PM
 */
package info.esblurock.info.react.data.molecules.CML;

import info.esblurock.react.common.IRestorableElement;
import info.esblurock.react.common.Parser;
import info.esblurock.CML.generated.Atom;
import info.esblurock.CML.generated.AtomArray;
import info.esblurock.CML.generated.Bond;
import info.esblurock.CML.generated.BondArray;
import info.esblurock.CML.generated.Molecule;
import info.esblurock.CML.generated.ObjectFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import info.esblurock.CML.generated.Name;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Marshaller;

import info.esblurock.CML.generated.Cml;
import info.esblurock.info.react.data.molecules.ReactAtom;
import info.esblurock.info.react.data.molecules.ReactBond;
import info.esblurock.info.react.data.molecules.ReactMolecule;

/**
 *
 * @author  moliate
 */
public class CMLMolecule extends ReactMolecule implements IRestorableElement {

    public int reaction_id = Integer.MIN_VALUE;
    public boolean is_reactant;

    public void parse(byte[] data) throws java.text.ParseException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try {
            JAXBContext jc = JAXBContext.newInstance("blurock.reaction.generated.core");
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            //unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
            Cml cml = (Cml) unmarshaller.unmarshal(bis);
            List<Object> list = cml.getAnyCmlOrAnyOrAny();
            Molecule molecule = (Molecule) list.get(0);
            fromCML(molecule);
        } catch (Exception e) {
            System.out.println("Marshal Exception: " + e);
        }
    }

    public byte[] restore() {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try {
            Molecule molecule = toCML();
            JAXBContext jc = JAXBContext.newInstance(molecule.getClass().getPackage().getName());
            Marshaller marshaller = jc.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(molecule, new PrintStream(bos));
        } catch (Exception e) {
            return e.toString().getBytes();
        }

        return bos.toString().getBytes();
    }

    public Molecule toCML() {
        try {
            ObjectFactory factory = new ObjectFactory();

            Molecule molecule = factory.createMolecule();
            molecule.setId("m" + ID + ((Integer.MIN_VALUE == reaction_id) ? "" : ("_" + (is_reactant ? 'r' : 'p') + reaction_id)));
            //Name
            Name name = factory.createName();
            name.setConvention("IUPAC");
            name.setValue(MoleculeName);
            molecule.getAnyCmlOrAnyOrAny().add(name);
            molecule.setTitle(name.getValue());
            //Atoms
            AtomArray aa = factory.createAtomArray();
            for (int i = 0; i < nbrOfAtoms(); i++) {
                CMLAtom atom = new CMLAtom();
                atom.setData((ReactAtom) Atoms.elementAt(i));
                atom.ID = "" + i;
                aa.getAnyCmlOrAnyOrAny().add(i, (Atom) atom.toCML());
            }
            molecule.getAnyCmlOrAnyOrAny().add(aa);
            //Bonds
            BondArray ba = factory.createBondArray();
            for (int i = 0; i < nbrOfBonds(); i++) {
                CMLBond bond = new CMLBond();
                bond.setData((ReactBond) Bonds.elementAt(i));
                ba.getAnyCmlOrAnyOrAny().add((Bond) bond.toCML());
            }
            molecule.getAnyCmlOrAnyOrAny().add(ba);
            return molecule;
        } catch (Exception e) {
            return null;
        }
    }

    public void fromCML(Molecule molecule) {
        Parser parser = new Parser(molecule.getId());
        ID = parser.nextInt();
        reaction_id = parser.nextInt();

        java.util.List<Object> lst = molecule.getAnyCmlOrAnyOrAny();

        for (int i = 0; i < lst.size(); i++) {
            Object obj = lst.get(i);
            if (obj instanceof Name) {
                Name name = (Name) obj;
                String id = name.getId();
                String nnn = name.getValue();
                MoleculeName = name.getValue();

            } else if (obj instanceof AtomArray) {
                AtomArray aa = (AtomArray) obj;
                java.util.List la = aa.getAnyCmlOrAnyOrAny();
                for (int iaa = 0; iaa < la.size(); iaa++) {
                    Atom atm = (Atom) aa.getAnyCmlOrAnyOrAny().get(iaa);
                    CMLAtom atom = new CMLAtom();
                    atom.fromCML((Atom) la.get(iaa));
                    Atoms.add(atom);
                }
            } else if (obj instanceof BondArray) {
                BondArray ba = (BondArray) obj;
                java.util.List lb = ba.getAnyCmlOrAnyOrAny();
                for (int iba = 0; iba < lb.size(); iba++) {
                    Bond bnd = (Bond) ba.getAnyCmlOrAnyOrAny().get(iba);
                    CMLBond bond = new CMLBond();
                    bond.fromCML((Bond) lb.get(iba));
                    Bonds.add(bond);
                }
            }
        }
    }
}
