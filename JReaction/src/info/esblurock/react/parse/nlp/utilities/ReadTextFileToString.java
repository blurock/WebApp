/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.esblurock.react.parse.nlp.utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author edwardblurock
 */
public class ReadTextFileToString {
        public String outputString = null;
    /** Creates a new instance of ReadFileToString */
    public ReadTextFileToString() {
    }
    public void read(File f) {
      int bt;
      try {
        FileReader reader = new FileReader(f);
        BufferedReader breader = new BufferedReader(reader);
        read(breader);
        breader.close();
        reader.close();
        } catch(FileNotFoundException io) {

            outputString = null;
        } catch(IOException io) {
            outputString = null;
        }
    }
  public void read(BufferedReader reader) throws IOException {
 
     StringBuilder tLine         = new StringBuilder();
           String line = reader.readLine();
           while(line != null) {
               tLine.append(line);
               tLine.append("\n");
            line = reader.readLine();
           }
        outputString = tLine.toString();
  }    

}
