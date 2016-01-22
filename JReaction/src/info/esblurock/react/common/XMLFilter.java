/*
 * XMLFilter.java
 *
 * Created on March 21, 2004, 5:56 AM
 */

package info.esblurock.react.common;
import java.io.*;
/**
 *
 * @author  moliate
 */
public class XMLFilter {
    
    private final static int REMOVE_EMPTY_ATTRIBUTES     = 0x00000001;
    private final static int REMOVE_IGNORABLE_WHITESPACE = 0x00000002;
    public static int flags = REMOVE_EMPTY_ATTRIBUTES;//|REMOVE_IGNORABLE_WHITESPACE;
    
    public static byte[] filter(byte[] xml)
    {
        String in = new String(xml);
        if (0 != (flags & REMOVE_EMPTY_ATTRIBUTES))
            in = in.replaceAll(" \\w+=\"\"", ""); // remove all empty attributes that JAXB generated
        
        return in.getBytes();
    }
    /**
     * @param args the command line arguments
     */
    /*
    public static void main(String[] args) throws Exception
    {
        	XMLFilter filter = new XMLFilter();
                //File file = new File(args[0]);
                File file = new File("C:\\Java\\terminus\\rxn.cml");
		InputStream is = new FileInputStream(file);
		byte[] bytes = new byte[(int)file.length()];
		int offset = 0;
		int numRead = 0;
		while (offset < bytes.length && (numRead=is.read(bytes, offset, bytes.length-offset)) >= 0) 
		{
			offset += numRead;
		}

		if (offset < file.length()) 
		{
			System.err.println("Failed to completly read file ("+offset+"/"+file.length()+")");
		}
		is.close();
		
		String output = new String(filter.filter(bytes));
		System.out.println(output);
    }
     **/
    
}
