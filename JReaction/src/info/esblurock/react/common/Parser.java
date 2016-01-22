package info.esblurock.react.common;
import java.util.regex.*;
import java.util.*;

/**
 * A helper class for parsing output. Parses numeric values in a String
 *
 * @author moliate
 */
public class Parser
{
        /**
         * A number that is either an integer or float
         */
	public final static String NUMBER  	= "[+-]?([0-9]*\\.?[0-9]+|[0-9]+\\.?[0-9]*)([eE][+-]?[0-9]+)?";
        /**
         * An integer
         */
	public final static String INT          = "[+-]?([0-9]+)";
        /**
         * A float
         */
	public final static String FLOAT 	= "[+-]?([0-9]*\\.[0-9]+|[0-9]+\\.[0-9]*)([eE][+-]?[0-9]+)?";
        /**
         * A "stand alone" character. "dsfsd A sdfs" will match 'A'
         */
	public final static String CHAR		= "(\\s|^)(\\S)(\\s|$)";
        /**
         * next character. "dsfsd A sdfs" will match 'd'
         */
        public final static String _CHAR	= "(\\S)";
        /**
         * A "stand alone" String. "dsfsd A sdfs" will match "dsfsd"
         */
	public final static String STRING	= "(\\S)+";
        /**
         * Next full String. "dsfsd A sdfs" will match "dsfsd A sdfs". Next token will be considered as terminator. Example:
         * <pre>
         * String[] pattern = {_STRING, "*A"}
         * Parser p = new Parser(pattern);
         * p.parse("dsfsd A sdfs");
         * </pre>
         * will return "dsfsd ". If the pattern looked like this: {_STRING, "A"}, the reslut would have been "dsfsd A"
         */
	public final static String _STRING	= "(.)+";
        
	public final static String LB	= "*(?m)$";       
	static Pattern numPattern = Pattern.compile(NUMBER);
	
	Matcher numMatcher;
        String[] RE;
	String[] elements;
	int index = 0;
        String remainder = "";

        /**
         * Instanciate a Parser class.
         * "Quick constructor" for parsing numerics only. For example:
         * <b>"34 ---> 34.5 a test hjdfj  e-4 45.4e-34"</b> will return:<br>
         * <ol>
         * <li> 34 </li>
         * <li> 34.5 </li>
         * <li> -4 </li>
         * <li> 45.4e-34 </li>
         * </ol>
         * @param data the string to parse
         */
	public Parser(String data)
	{
		numMatcher = numPattern.matcher(data);
		Vector v = new Vector();
		while(numMatcher.find())
			v.add(numMatcher.group());

		elements = new String[v.size()];
		v.copyInto(elements);
	}
	
	public Parser(String[] RE)
	{
            this.RE = RE;
	}
        
        public void parse(String input) throws java.text.ParseException
        {
            Vector v = new Vector();
            index = 0; 
            remainder = "";
            String data = input;
            
            for (int i = 0; i < RE.length; i++)
            {
                String re = RE[i];
                String suffix = "";
                boolean add = true;
                
                if (re.startsWith("*"))
                { 
                    re = re.substring(1);
                    add = false;
                }
                
                if (re.equals(_STRING))
                {
                    if (i < RE.length-1)
                    {
                        String su = RE[++i];
                        if (su.startsWith("*"))
                        {
                              su = su.substring(1);
                              suffix = su;
                        }
                        re += suffix;
                    }
                }
                
                //System.out.println(re);
                Pattern pattern = Pattern.compile(re);
                Matcher matcher = pattern.matcher(data);
                if (!matcher.find())
                    {
                        int pos = input.lastIndexOf(data);
                        throw new java.text.ParseException("\""+data+"\" does not match: " + re + "\nfrom: \""+input+"\"", pos);
                    }
                
                String res = matcher.group();
                res = res.replaceAll(suffix, "");
                if (add) v.add(res);
                //System.out.println("-> " + data);
                data = data.substring(matcher.end());
                //System.out.println("+> " + data);
            }
            remainder = data;
            elements = new String[v.size()];
            v.copyInto(elements);
        }
        
        /**
         * Check if there are more numeric matches
         * @return 'true' if there are more matches
         */
    public boolean hasNext()
    {
        if (null == elements)
            return false;
        
        return (index < elements.length);
    }
    
    public String getRemainder()
    {
        return remainder;
    }
        
        /**
         * Return next numeric value
         * @return next value paresd to float or MIN_VALUE if not exists
         */	
	public float nextFloat()
	{
        if ( !hasNext() ) return Float.MIN_VALUE;
		return Float.parseFloat(elements[index++]);
	}
        /**
         * Return next numeric value
         * @return next value paresd to double or MIN_VALUE if not exists
         */	
	public double nextDouble()
	{
        if ( !hasNext() ) return Double.MIN_VALUE;
		return Double.parseDouble(elements[index++]);
	}
        /**
         * Return next numeric value
         * @return next value paresd to int or MIN_VALUE if not exists
         */		
	public int nextInt()
	{
        if ( !hasNext() ) return Integer.MIN_VALUE;
		return Integer.parseInt(elements[index++]);
	}
        /**
         * Return next numeric value
         * @return next value paresd to long or MIN_VALUE if not exists
         */	
	public long nextLong()
	{
        if ( !hasNext() ) return Long.MIN_VALUE;
		return Long.parseLong(elements[index++]);
	}
        /**
         * Return next value as String
         * @return next value parsed to String or "" if not exists
         */	
        public String nextString()
	{
         if ( !hasNext() ) return "";
	 return elements[index++];
	}
        /**
         * Return next value as char
         * @return next value parsed to char or ' ' if not exists
         */        
        public char nextChar()
	{
         if ( !hasNext() ) return ' ';
	 return elements[index++].trim().charAt(0);
	}    
        
        /*
	public static void main(String[] args)
	{
                String[] regg = {_STRING, "*:",  INT, _CHAR, CHAR};
                Parser p = new Parser( regg ); //new Parser("34 ---> 34.5 a test hjdfj  e-4 45.4e-34");
                p.parse("343 hfg 343.54 djfklds f :432   324.4 k");
                
		//System.out.println(p.nextInt());
		//System.out.println(p.nextFloat());
		System.out.println(p.nextString());
                System.out.println(p.nextInt());
                System.out.println(p.nextChar());
                                System.out.println(p.nextChar());
		//System.out.println(p.nextFloat());
	}
        /**/
}
