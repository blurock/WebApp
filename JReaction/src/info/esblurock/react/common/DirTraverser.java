/*
 * DirTraverser.java
 *
 * Created on February 22, 2004, 8:06 AM
 */

package info.esblurock.react.common;
import info.esblurock.CML.generated.Cml;
import info.esblurock.CML.generated.ObjectFactory;
import info.esblurock.CML.generated.Scalar;
import java.io.*;
import java.util.*;

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
    

        public static Cml parseDir(String home, String suffix, boolean showFiles) throws Exception
        {
             
                   ObjectFactory factory = new ObjectFactory();
                   Cml cml = factory.createCml();
                   info.esblurock.CML.generated.List list = factory.createList();
          
                   String home_replace = home;
                   //home_replace = home_replace.replaceAll(FS+FS, "");
                   if (FS.equals("\\"))
                       home_replace = home.replaceAll("\\\\", "\\\\\\\\");
                   
                   
                   File[] files = traverse(new File(home), suffix);
                   for (int i = 0; i < files.length; i++)
                   {
                       String file = files[i].getPath();
                       //file = file.replaceAll(FS+FS, "");
                       file = file.replaceAll("^"+home_replace, "");    // remove prefix
                       file = file.replaceAll(suffix + "$", "");// and suffix
                       StringTokenizer st = new StringTokenizer(file, FS);
                       
                       info.esblurock.CML.generated.List base = list;
                       
path_traverser:
                       while( st.hasMoreTokens() )
                       {
                           String token = st.nextToken();
                           if (!st.hasMoreTokens()) // ----- a file ----- 
                           {
                               if (showFiles)
                               {
                                   Scalar scalar = factory.createScalar();
                                   scalar.setValue(token);
                                   scalar.setDataType("xsd:string");
                                   base.getAnyCmlOrAnyOrAny().add(scalar);
                               }
                           }
                           else // ----- a directory ----- 
                           {
                               // search for existing directory list
                               List l = base.getAnyCmlOrAnyOrAny();
                               for (int ii = 0; ii < l.size(); ii++)
                               {
                                   Object item = l.get(ii);
                                   if (item instanceof info.esblurock.CML.generated.List)
                                   {
                                	   info.esblurock.CML.generated.List li = (info.esblurock.CML.generated.List)item;
                                       if (li.getTitle().equals(token))
                                       {   
                                           base = li;
                                           continue path_traverser;
                                       }
                                   }
                               }
                               // add a new directory list
                               info.esblurock.CML.generated.List cl = factory.createList();
                               cl.setTitle(token);
                               base.getAnyCmlOrAnyOrAny().add(cl);
                               base = cl;
                           }
                       }
                   }
                   
                   cml.getAnyCmlOrAnyOrAny().add(list);
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
