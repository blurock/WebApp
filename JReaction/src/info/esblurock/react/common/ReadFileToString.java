package info.esblurock.react.common;

import java.io.*;


/**
 *
 * @author  reaction
 */
public class ReadFileToString {
	Log log = new Log();
    public String outputString = null;
    /** Creates a new instance of ReadFileToString */
    public ReadFileToString() {
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
            Log.println("File not found: \n" + f.toString());
            outputString = null;
        } catch(IOException io) {
        	Log.println("Error while reading file: " +f.toString());
            outputString = null;
        }
    }
  public void read(BufferedReader reader) throws IOException {
     boolean endofline = true;
     StringBuffer tLine         = new StringBuffer();
           String line = reader.readLine();
           while(line != null) {
           endofline = true;
               if(!line.startsWith("%")) {
                   int index = line.indexOf('%');
                   if(index > 0) {
                       tLine.append(line.substring(0,index));
                   } else {
                       int lastcharindex = line.length() - 1;
                       if(line.lastIndexOf("\\",lastcharindex) > 0) {
                           endofline = false;
                       } else {
                          tLine.append(line);
                       }
                   }
               }
               if(endofline) {
                   tLine.append("\n");
               }
            line = reader.readLine();
           }
        outputString = tLine.toString();
  }    
}
