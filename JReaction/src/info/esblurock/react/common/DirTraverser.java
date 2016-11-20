/*
 * DirTraverser.java
 *
 * Created on February 22, 2004, 8:06 AM
 */

package info.esblurock.react.common;
import java.io.*;
import java.util.*;

import org.xmlcml.cml.base.CMLElement;
import org.xmlcml.cml.element.CMLArrayList;
import org.xmlcml.cml.element.CMLCml;
import org.xmlcml.cml.element.CMLScalar;

/**
 *
 * @author  Blurock
 */

    
    
public class DirTraverser 
{
    
    private static String FS = System.getProperty("file.separator");
    
    public static File[] traverseDir(File base) throws IOException
    {
        return traverse(base, new suffixFilter("")); 
    }
    
    public static File[] traverse(File base, String suffix) throws IOException
    {
        return traverse(base, new suffixFilter(suffix)); 
    }
    
    public static File[] traverse(File base, FileFilter filter) throws IOException
    {
    	ArrayList v = new ArrayList();
        //Vector v = new Vector();
        File[] files = base.listFiles(filter);
        if (null == files)
            return new File[0];
        
        for (int i = 0; i < files.length; i++)
        {
            if (files[i].isFile())
                v.add(files[i]);
            else
            {
                File[] list = traverse(files[i],  filter);
                for (int ii = 0; ii < list.length; ii++)
                    v.add(list[ii]);
            }
        }
        
        File[] result = new File[v.size()];
        Object[] objects = v.toArray();
        Arrays.sort(objects);
        int i = 0;
        for(Object filename : v) {
        	result[i] = (File) filename;
        }
        return result;
    }
    

        public static CMLElement parseDir(String home, String suffix, boolean showFiles) throws Exception {
                   String home_replace = home;
                   //home_replace = home_replace.replaceAll(FS+FS, "");
                   if (FS.equals("\\"))
                       home_replace = home.replaceAll("\\\\", "\\\\\\\\");
                   
                   CMLElement cml = new CMLElement(home);                   
                   File[] files = traverse(new File(home), suffix);
                   for (int i = 0; i < files.length; i++) {
                       String file = files[i].getPath();
                       //file = file.replaceAll(FS+FS, "");
                       file = file.replaceAll("^"+home_replace, "");    // remove prefix
                       file = file.replaceAll(suffix + "$", "");// and suffix
                       StringTokenizer st = new StringTokenizer(file, FS);
                       CMLElement current = cml;
                       path_traverser:
                       while( st.hasMoreTokens() ) {
                           String token = st.nextToken();
                           if (!st.hasMoreTokens()) { // ----- a file ----- 
                               if (showFiles) {
                            	   CMLElement element = new CMLElement(token);
                            	   element.setAttribute("xsd:string", token);
                            	   
                            	   current.appendChild(element);
                               }
                           } else  { // ----- a directory -----
                        	   
                        	   for(CMLElement element : current.getChildCMLElements() ) {	   
                                   if(element.getId().matches(token)) {   
                                	   current = (CMLElement) element.getChild(0);
                                           continue path_traverser;
                                       }
                                   }
                               }
                               // add a new directory list
                           CMLElement dir = new CMLElement(token);
                           current.appendChild(dir);
                           current = dir;
                           }
                       }
                   return cml;
        }
    /**
    public static void main(String[] args) throws Exception
    {
        File f = new File("c:\\Java\\test");
        File[] l = DirTraverser.traverse(f, ".txt");        
    }
    /**/
}
