/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.esblurock.react.common;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author blurock
 */
public class SProperties {

    protected static Properties properties = new Properties();
    protected static Vector listeners = new Vector();

    static {
        load();
    }

    public static void addPropertyChangeListener(PropertyChangeListener pcl) {
        listeners.add(pcl);
    }

    public static void removePropertyChangeListener(PropertyChangeListener pcl) {
        listeners.remove(pcl);
    }

    public static String setProperty(String name, String value) {
        String old = (String) properties.setProperty(name, value);

        PropertyChangeEvent pce = new PropertyChangeEvent(new Object(), name, old, value);
        for (Enumeration e = listeners.elements(); e.hasMoreElements();) {
            ((PropertyChangeListener) e.nextElement()).propertyChange(pce);
        }

        return old;
    }

    public static String getProperty(String name) {
        return properties.getProperty(name);
    }

    public static void save() {
        try {
            java.io.FileOutputStream fos = new java.io.FileOutputStream("reaction.properties");
            properties.store(fos, "Reaction properties");
        } catch (java.io.IOException e) {
            java.util.logging.Logger.getLogger("global").log(java.util.logging.Level.WARNING, "Failed to save settings." + e.toString());
        }

    }

    public static void load() {
        try {
            String current = new java.io.File( "." ).getCanonicalPath();
        System.out.println("Current dir:"+current);
 String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" +currentDir);
            java.io.FileInputStream fis = new java.io.FileInputStream("/opt/ReactUsers/users/trial/reaction.properties");
            properties.load(fis);
        } catch (FileNotFoundException ex) {
            Logger.getLogger("global").log(Level.SEVERE, "Properties file not found: " + ex.toString());
        } catch (IOException ex) {
            Logger.getLogger("global").log(Level.SEVERE, "Exception on reading the properties file" + ex.toString());
        }
    }

    public static void loadOnClassPath() {
        try {
            java.io.InputStream in = new Object().getClass().getResourceAsStream("/terminus.properties");
            properties.load(in);
        } catch (java.io.IOException e) {
            //Interact.report("Failed to load settings.", e);
            }
    }
}
