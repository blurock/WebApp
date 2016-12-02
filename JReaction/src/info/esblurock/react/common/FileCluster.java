/*
 * FileCluster.java
 *
 * Created on April 17, 2004, 5:32 PM
 */

package info.esblurock.react.common;
import java.io.*;
import java.text.ParseException;
import java.util.*;

import org.xmlcml.cml.base.CMLElement;

/**
 *
 * @author  moliate
 */

public class FileCluster implements IParsableElement, IRestorableElement
{
	//Base64.getEncoder().encodeToString("ccc");
    class FileData extends ByteArrayInputStream implements Comparable
    {     
        protected String[] name = {};
        protected boolean binary = true;
        protected final static String path_separator = "/";
        protected FileData(byte[] data, boolean binary)
        {
            super(data);
            this.binary = binary;
        }
        
        public String[] getName()
        {
            return name;
        }
        
        public boolean isBinary()
        {
            return binary;
        }
        
        public String getPath()
        {
            String path = "";
            for (int i = 0; i < name.length; i++)
                path = path + path_separator + name[i];
            
            return path;
        }
        
        public void parsePath(String path)
        {
             path = path.replaceAll("\\.\\.", "");
             path = path.replaceAll("//", "");
             StringTokenizer st = new StringTokenizer(path, path_separator);
             name = new String[st.countTokens()];
             for (int i = 0; i < name.length; i++)
                 name[i] = st.nextToken();
        }
        
        
        public void setName(String[] name)
        {
            this.name = name;
        }
        public int compareTo(Object o) {
            int ans = 0;
            FileData d = (FileData) o;
            if(d.getName().length > getName().length) {
                ans = 1;
            } else if(d.getName().length < getName().length) {
                ans = -1;
            } else {
                String pd = d.getPath();
                String p  = getPath();
                ans = p.compareTo(pd);
            }
            return ans;
        }
    }
    
    protected Vector files = new Vector();
    protected int current_index = -1;
    protected String ignore_path = "";
    /** Creates a new instance of FileCluster */
    public FileCluster() 
    {
        Log.println("FileCluster structure initialized");
    }
    
    
    public void insertFile(File file) throws IOException
    {
        insertFile(file, false);
    }
     
    public void insertFile(File file, boolean isBinary) throws IOException
    {
        byte[] data = null;
        // Read file with absolute path
        FileInputStream fis = new FileInputStream(file);
        data = new byte[fis.available()];
        fis.read(data);
        
        FileData file_data = new FileData(data, isBinary);
        // Strip off the relative location of the data from the server
        String path = file.getAbsolutePath();
        if (path.startsWith(ignore_path))
            path = path.substring(ignore_path.length());
        // parse directory structure (of relative path)
        StringTokenizer st = new StringTokenizer(path, File.separator);
        String[] name = new String[st.countTokens()];
        for (int i = 0; i < name.length; i++)
            name[i] = st.nextToken();
        // Set relative path in FileData
        file_data.setName(name); 
        // add to file cluster
        files.add(file_data);
    }
    
    public void insertFile(byte[] data, String[] name)
    {
        insertFile(data, name, false);
    }  
    
    public void insertFile(byte[] data, String[] name, boolean isBinary)
    {
        FileData file_data = new FileData(data, isBinary);
        file_data.setName(name); 
        files.add(file_data);
    }
    
    public void setRootPath(File directory)
    {
        ignore_path = directory.getAbsolutePath();
    }
    
    public FileData getFirst()
    {
        try
        {
            return (FileData)files.elementAt(current_index=0);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
    }
    
    public FileData getNext()
    {
        try
        {
            return (FileData)files.elementAt(++current_index);
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            return null;
        }
    }   
    
    public void parse(byte[] data) throws ParseException {
    	throw new ParseException("Not implemented yet: FileCluster:parse", 0);
    	/*
        Log.println("parse ");
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try {
                    //Log.println("parse 1");
                javax.xml.bind.JAXBContext jc = javax.xml.bind.JAXBContext.newInstance("blurock.reaction.generated.core"); 
                    //Log.println("parse 2");
                Unmarshaller unmarshaller = jc.createUnmarshaller();
                    //Log.println("parse 3");
                    //Log.println(new String(data));
                //unmarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                    Object obj = unmarshaller.unmarshal(bis);              
                    //Log.println("parse 3.1");
                    //Log.println(obj.toString());
                Cml cml = (Cml) obj;
                    //Log.println("parse 4");
                fromCML(cml); 
                    //Log.println("parse 5");

        } catch (Exception e) {
            Log.println("Invocation error in parsing CML: ");
            Log.println(e);
            Log.println("Unexpected error in parsing CML: " + e.toString(), Log.ERROR);
            Log.println(e, Log.INFO);
            return; 
        }  
        */
    }
    
    public byte[] restore()  {
    	/*
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {               
                Cml cml = toCML();    
                JAXBContext jc = JAXBContext.newInstance("blurock.reaction.generated.core");
                Marshaller marshaller = jc.createMarshaller();
                marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE );
                marshaller.marshal(cml, new PrintStream(bos) );
        } 
        catch (Exception e) 
        {   
            Log.println("Unexpected error in restoring CML: " + e.toString(), Log.ERROR);
            Log.println(e, Log.INFO);
            return e.toString().getBytes();
        }  
        return bos.toString().getBytes();
        */
    	return null;
    }
    
    /**
     * 
     * @return
     */
    public CMLElement toCML() {
    	/*
        Arrays.sort(files.toArray());      
        try
        {          
                ObjectFactory factory = new ObjectFactory();
                Cml cml = factory.createCml();
                blurock.reaction.generated.core.List list = factory.createList();
                list.setTitle("Set of files");
                FileData file_data = getFirst();
                while (null != file_data)
                {
                    Scalar file = factory.createScalar();
                    file_data.reset();
                    byte[] data = new byte[file_data.available()];
                    file_data.read(data);
                    file.setId(file_data.getPath());
                    if (file_data.isBinary())    
                    {
                        file.setValue(Base64.encodeBase64String(data));
                        file.setDataType("xsd:base64Binary");
                    }
                    else
                    {
                        file.setValue(new String(data));
                        file.setDataType("xsd:string");
                    }
                    list.getAnyCmlOrAnyOrAny().add(file);
                    file_data = getNext();
                }
                cml.getAnyCmlOrAnyOrAny().add(list);
                return cml; 
        } 
        catch (Exception e) 
        { 
            Log.println("Unexpected error in toCML: " + e.toString(), Log.ERROR);
            Log.println(e, Log.INFO);
            return null; 
        }   
        */
    	return null;
    }
    
    public void fromCML(CMLElement cml) {
    	/*
        Log.println("fromCML  0");
        Object[] list = cml.getAnyCmlOrAnyOrAny().toArray();
        Log.println("fromCML  1 - " + list.length);
        for (int i = 0; i < list.length; i++)
        {
            Log.println("fromCML  2 - " + i);
            if (list[i] instanceof blurock.reaction.generated.core.List)
            {
                Object[] elements = ((blurock.reaction.generated.core.List)list[i]).getAnyCmlOrAnyOrAny().toArray();
                for (int ii = 0; ii < elements.length; ii++)
                {
                    if (elements[ii] instanceof Scalar)
                    {
                        Scalar file = (Scalar)elements[ii];
                        byte[] data;
                        boolean binary;
                        String value = file.getValue();
                        /*
                        if (file.getDataType().equals("xsd:base64Binary"))
                        {
                            //data = org.apache.axis.encoding.Base64.decode(value);
                            //data = Base64.Decoder.getDecoder().decodeToString(value);
                            binary = true;
                        }
                        else
                        {
                        
                            data = value.getBytes();
                            binary = false;
                        //}
                        
                        FileData file_data = new FileData(data, binary);
                        file_data.parsePath(file.getId());
                        files.add(file_data);
                    }
                }
            }
        }
        Arrays.sort(files.toArray());      
        */
    }
    
    public void print() 
    {
        for (int i = 0; i < files.size(); i++)
        {
            FileData file_data = (FileData)files.elementAt(i);
            Log.println(file_data.getPath() + "(size: " + file_data.available() + " bytes, "+(file_data.isBinary()?"binary":"ascii")+")", Log.CRITICAL);
        }
    }
    
    public void setData(IParsableElement element) 
    {
        if (! (element instanceof FileCluster) )
        {   
            Log.println("Tried to parse an element of wrong type: " + element.getClass().getName() + " where " + this.getClass().getName()+ " was expected.", Log.ERROR);
            return;
        }
        FileCluster fc = (FileCluster)element;
        this.files = fc.files;
        this.current_index = fc.current_index;
    }
    
    public int saveAll()
    {
        int succeded = 0;
        Log.println("saveAll:");

        Log.println("saveAll: size=" + files.size());
       
        for (int i = 0; i < files.size(); i++)
        {
            try
            {
                Log.println("saveAll:" + i );
                FileData file_data = (FileData)files.elementAt(i);
                String path = ignore_path;
                String[] name = file_data.getName();
                for (int ii = 0; ii < name.length; ii++)
                    path += File.separator + name[ii];
                
               Log.println("saveAll: saving '" + path + "'");
                File output_file = new File(path);
                output_file.getParentFile().mkdirs();            
                output_file.createNewFile();

                FileOutputStream fos = new FileOutputStream(output_file);
                byte[] data = new byte[file_data.available()];
                String dataS = new String(data);
                //Log.println("first part: '" + dataS.substring(0,20) + "'");
                //String dataS1 = dataS.replace("\r\n","\n");
                //file_data.read(dataS1.getBytes());
                int count = 0;
                for(int iii=0; iii<data.length;iii++) {
                    if(data[iii] == '\r') count++;
                }
                Log.println("Length: " + data.length + "  number lines with extra: " + count + "\n");
                file_data.read(data);
                fos.write(data);
                fos.close();
                
                String convertcommand = "dos2unix " + output_file.toString();
                Log.println("Convert File (if UNIX): " + convertcommand);
                try {
                    Runtime.getRuntime().exec(convertcommand);
                } catch(IOException io) {
                    Log.println("not UNIX");
                }
                succeded++;
            }
            catch(IOException ioe)
            {
                Log.println("I/O exception: " + ioe.toString(), Log.ERROR);
                Log.println(ioe, Log.INFO);
            }
        }
        
       return succeded;
    }

/*
        public static void main(String[] args) 
        {
            try{
            FileCluster fc = new FileCluster();
            fc.setRootPath((new File("C:\\")));
            File file = new File("C:\\Program Files\\AviCreator 1.5\\Samples\\1\\Frame1.bmp");
            String[] name = new String[1];
            name[0] = "binary";
            fc.insertFile(file, true);
            file = new File("C:\\html\\ntfs_restore\\ntfs.txt");
            fc.insertFile(file);
            fc.print();
            Log.println(new String(fc.restore()));
            FileCluster reciever = new FileCluster();
            reciever.parse(fc.restore());
            Log.println(new String(fc.restore()));
            reciever.setRootPath(new File("C:\\tmp"));
            reciever.saveAll();
         }catch(Exception e)
         {e.printStackTrace();}
        
    }
    */
}
