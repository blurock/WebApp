package info.esblurock.reaction.parse;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;
import org.openscience.cdk.exception.CDKException;

import info.esblurock.react.parse.nlp.utilities.ReadTextFileToString;
import info.esblurock.reaction.server.process.react.ParseReactionSDFMolecule;

public class ReadSDFFile {

	@Test
	public void test() {
        File sampleFile = new File("test/resources/molecules.sdf");
        System.out.println(sampleFile.getAbsolutePath());
        ReadTextFileToString read = new ReadTextFileToString();
        read.read(sampleFile);
        System.out.println(read.outputString);
        String output = read.outputString;
        ParseReactionSDFMolecule parse = new ParseReactionSDFMolecule();
        try {
			parse.parse(read.outputString);
		} catch (IOException | CDKException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
