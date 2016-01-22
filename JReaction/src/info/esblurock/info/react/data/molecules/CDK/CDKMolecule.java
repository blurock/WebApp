/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package info.esblurock.info.react.data.molecules.CDK;

import info.esblurock.info.react.data.molecules.ReactMolecule;
import info.esblurock.info.react.data.molecules.CML.CMLMolecule;

import java.text.ParseException;

/**
 *
 * @author blurock
 */
public class CDKMolecule extends ReactMolecule {

    public void parse(byte[] data) throws ParseException {
        CMLMolecule cmlmolecule = new CMLMolecule();
        cmlmolecule.parse(data);
    }

}
