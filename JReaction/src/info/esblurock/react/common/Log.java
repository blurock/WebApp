/*
 * Log.java
 *
 * Created on February 11, 2004, 9:48 AM
 */

package info.esblurock.react.common;
import java.io.*;
import java.util.*;
/**
 *
 * @author  moliate
 */
public class Log extends SProperties
{
    public static final int NONE      = 0;
    public static final int CRITICAL  = 1;    
    public static final int ERROR     = 2;
    public static final int WARN      = 3;
    public static final int INFO      = 4;
    public static final int ALL       = 5;

    private static boolean FP = false;
    private static boolean print_LEVEL = true;
    private static boolean print_LINE  = false;
    private static boolean print_TIME  = false;
    private static boolean print_SEP   = true;
    private static int logLevels[];
    private static String logDir = ".";
    private static PrintStream[] streams = { System.out};
    
    static
    {
        //org.apache.log4j.BasicConfigurator.configure();
        if (null == properties.getProperty("log.level")) 
    		properties.setProperty("log.level", "4" );
        if (null == properties.getProperty("log.print.level")) 
    		properties.setProperty("log.print.level", Boolean.toString(print_LEVEL) );
        if (null == properties.getProperty("log.print.line")) 
    		properties.setProperty("log.print.level", Boolean.toString(print_LINE) );
        if (null == properties.getProperty("log.print.time")) 
    		properties.setProperty("log.print.level", Boolean.toString(print_TIME) );
        if (null == properties.getProperty("log.print.separator"))
    		properties.setProperty("log.print.separator", Boolean.toString(print_SEP) );
	if (null == properties.getProperty("log.directory") )
    		properties.setProperty("log.directory", logDir );
        logLevels = new int[1];    
        logLevels[0] = Integer.parseInt(properties.getProperty("log.level"));
        print_LEVEL = Boolean.valueOf( properties.getProperty("log.print.level") ).booleanValue();
        print_LINE = Boolean.valueOf( properties.getProperty("log.print.line") ).booleanValue();
        print_TIME = Boolean.valueOf( properties.getProperty("log.print.time") ).booleanValue();
        print_SEP = Boolean.valueOf( properties.getProperty("log.print.separator") ).booleanValue();
	logDir = properties.getProperty("log.directory");
    }
    
    public static String getLogDir()
    {
        return logDir;
    }
    
    public static void setLogDir(String dir)
    {
        logDir = dir;
    }

    public static void println(String message, int level)
    {      
 
            String prefix = (!FP)?createPrefix(level):"";
            StringTokenizer st = new StringTokenizer(message, "\n");
            int i = 0;
            while(st.hasMoreTokens())
            {
                if (print_LINE && !FP)
                    printA("M"+ i++ +"\t", level);
                printlnA(prefix + st.nextToken(), level ); 
            }
        
            FP = false;
    }
    
    public static void println(String message)
    {
        println(message, INFO);
    }    
    
    public static void println(Throwable e, int level)
    {
 
            String prefix = createPrefix(level);
            StackTraceElement[] el = e.getStackTrace();
            for (int i = 0; i < el.length; i++)
            {
                if (print_LINE)
                    printA("E"+i +"\t", level);
                printlnA(prefix + el[i].toString(), level);
            }

    }  
    
    public static void println(Throwable e)
    {
        println(e, ERROR);
    }   
    
    public static void print(String message, int level)
    {
        String prefix = (!FP)?createPrefix(level):"";
        
        if (print_LINE)
            printA("M0" +"\t", level);     
        
        printA(prefix + message, level);
        
        FP = true;
    }
  
    public static void print(String message)
    {
        print(message, INFO);
    }
    
    public static void setLevel(int level)
    {
        logLevels[0] = level; 
        setProperty("log.level", new Integer(level).toString() );
    }
    
    public static int getLevel()
    {
        return logLevels[0];
    }
    
    /*
    public static void main(String[] args)
    {

    }
    /**/
    protected static String createPrefix(int level)
    {
        StringBuffer sb = new StringBuffer();
        if (print_LEVEL)
        {
            switch(level)
            {
                case NONE       :   sb.append("    [NONE]"); break;
                case CRITICAL   :   sb.append("[CRITICAL]"); break;
                case ERROR      :   sb.append("   [ERROR]"); break; 
                case WARN       :   sb.append(" [WARNING]"); break;  
                case INFO       :   sb.append("    [INFO]"); break;  
                case ALL        :   sb.append("     [ALL]"); break;  
            }
        }
        
        if (print_TIME)
            sb.append( " " +java.text.DateFormat.getTimeInstance(java.text.DateFormat.LONG).format( new Date() ) );
        
        
        sb.append(" ");
        
        if (print_SEP) sb.append("> ");
        
        return sb.toString();
    }

    public static void setPrintLevel(boolean on)
    {
        properties.setProperty("log.print.level", Boolean.toString(print_LEVEL = on));
    }
    
    public static boolean getPrintLevel()
    {
        return print_LEVEL;
    }    
    
    public static void setPrintLine(boolean on)
    {
        properties.setProperty("log.print.line", Boolean.toString(print_LINE = on));
    }
    
    public static boolean getPrintLine()
    {
        return print_LINE;
    }    
        
    public static void setPrintTime(boolean on)
    {
        properties.setProperty("log.print.time", Boolean.toString(print_TIME = on));
    }
    
    public static boolean getPrintTime()
    {
        return print_TIME;
    }  
        
    public static void setPrintSeparator(boolean on)
    {
        properties.setProperty("log.print.separator", Boolean.toString(print_SEP = on));
    }
    
    public static boolean getPrintSeparator()
    {
        return print_SEP;
    }  
    
    private static void printlnA(String s, int level)
    {
        System.out.println(s);
        for (int i= 0; i < streams.length; i++) 
            if (level <= logLevels[i])
            {//streams[i].println(s);
             //streams[i].flush();
                System.out.println(s);
            }
    }
    
    private static void printA(String s, int level)
    {
        System.out.println(s);
        for (int i= 0; i < streams.length; i++)
            if (level <= logLevels[i])
            {streams[i].println(s);
             streams[i].flush();
            }
    }
    
    public static void addLogger(PrintStream s, int level)
    {
        int[] levels = new int[logLevels.length+1];
        PrintStream[] ps = new PrintStream[logLevels.length+1];
        for (int i = 0; i < logLevels.length; i++)
        {
            levels[i] = logLevels[i];
            ps[i] = streams[i];
        }

        levels[logLevels.length] = level;
        ps[logLevels.length] = s;

        logLevels = levels;
        streams = ps;
    }
    
    public static boolean setLogger(PrintStream s, int level, int logger)
    {
        try
	{
        	logLevels[logger]= level;
        	streams[logger] = s;
	}
	catch(Exception e) {
            System.out.println("(setLogger(PrintStream): Failure to create a Logging File: " + s.toString());
            return false;
        }

	return true;

    }
    
    public static boolean setLogger(String filename, int level, int logger)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(new File(filename), true);
            PrintStream ps = new PrintStream(fos);
            return setLogger(ps, level, logger);
        }
        catch(Exception e) {
            System.out.println("(setLogger): Failure to create a Logging File: " + filename);
            return false;
        }

    }
    
    public static void addLogger(String filename, int level)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(new File(filename), true);
            PrintStream ps = new PrintStream(fos);
            addLogger(ps, level);
        }
        catch(Exception e) {
            System.out.println("(addLogger): Failure to create a Logging File: " + filename);
        }
    }
     


}
