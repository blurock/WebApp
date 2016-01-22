/*
 * suffixFilter.java
 *
 * Created on April 27, 2004, 3:29 AM
 */

package info.esblurock.react.common;
import java.io.*;
/**
 *
 * @author  moliate
 */
    public class suffixFilter extends javax.swing.filechooser.FileFilter implements java.io.FileFilter
    {
        String suffix = "";
        String description = "";
        public suffixFilter(String suffix, String description)
        {
            if (null != suffix)
                this.suffix = suffix;
            if (null != description)
                this.description = description;
        }
        public suffixFilter(String suffix)
        {
            if (null != suffix)
                this.suffix = suffix;
        }    
        
        public boolean accept(File pathname)
        {
            return pathname.isDirectory() || pathname.getName().endsWith(suffix);
        }
        
        public String getDescription() {
            return description;
        }
        
    }
