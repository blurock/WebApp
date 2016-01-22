/*
 * SColor.java
 *
 * Created on February 24, 2004, 12:35 PM
 */

package info.esblurock.react.common;

/**
 * static (global) class that contains color information and manipulation members
 * @author  moliate
 */
public class SColor extends SProperties
{   
	static
	{
		java.awt.Color fg = java.awt.Color.BLACK;
		java.awt.Color bg = new java.awt.Color(230, 230, 255);
		if (null == properties.getProperty("color.foreground")) 
					properties.setProperty("color.foreground", ""+fg.getRGB());
		if (null == properties.getProperty("color.background")) 
					properties.setProperty("color.background", ""+bg.getRGB());
	}
    
    public static java.awt.Color getForeground()
    {
	    return java.awt.Color.decode(getProperty("color.foreground"));
    }
	
    public static java.awt.Color getBackground()
    {
	    return java.awt.Color.decode(getProperty("color.background"));
    }

    public static void setForeground(java.awt.Color color)
    {
	    setProperty("color.foreground", ""+color.getRGB());
    }
	
    public static void setBackground(java.awt.Color color)
    {
	    setProperty("color.background", ""+color.getRGB());
	}	
		    
    public static java.awt.Color brighter(java.awt.Color c)
    {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        if (10 >= r) r += 80;           
        else         r = java.lang.Math.min((int)(r*1.3), 0xff);
        if (10 >= g) g += 80;            
        else         g = java.lang.Math.min((int)(g*1.3), 0xff);
        if (10 >= b) b += 80;           
        else         b = java.lang.Math.min((int)(b*1.3), 0xff);
        return new java.awt.Color(r,g,b);      
    }
    
    public static java.awt.Color darker(java.awt.Color c)
    {
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        r = java.lang.Math.max((int)(r*0.9), 0x00);
        g = java.lang.Math.max((int)(g*0.9), 0x00);
        b = java.lang.Math.max((int)(b*0.9), 0x00);
        return new java.awt.Color(r,g,b);      
    }
    
    public static int brightness(java.awt.Color c)
    {
        return c.getBlue() + c.getGreen() +  c.getRed();   
    }
}
