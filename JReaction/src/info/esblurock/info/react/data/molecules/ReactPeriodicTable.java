
package info.esblurock.info.react.data.molecules;
import javax.swing.*;
import java.util.*;

public class ReactPeriodicTable
{

    private static final String[] at_table ={
    "H                                                                   He  ",
    "Li  Be                                          B   C   N   O   F   Ne  ",
    "Na  Mg                                          Al  Si  P   S   Cl  Ar  ",
    "K   Ca  Sc  Ti  V   Cr  Mn  Fe  Co  Ni  Cu  Zn  Ga  Ge  As  Se  Br  Kr  ",
    "Rb  Sr  Y   Zr  Nb  Mo  Tc  Ru  Rh  Pd  Ag  Cd  In  Sn  Sb  Te  I   Xe  ",
    "Cs  Ba  Lu  Hf  Ta  W   Re  Os  Ir  Pt  Au  Hg  Tl  Pb  Bi  Po  At  Rn  ",
    "Fr  Ra  Lr  Rf  Db  Sg  Bh  Hs  Mt  Ds  Uuu Uub Uut Uuq Uup Uuh Uus Uuo ",
    "                                                                        ",
    "La  Ce  Pr  Nd  Pm  Sm  Eu  Gd  Tb  Dy  Ho  Er  Tm  Yb                  ",
    "Ac  Th  Pa  U   Np  Pu  Am  Cm  Bk  Cf  Es  Fm  Md  No                  ",
    "                                                                        ",    
//    "R   Q   X   Q                                                           "};
    "R   R   R   R                                                           "};


    private static final String[] nr_table ={
    "1                                                                   2   ",
    "3   4                                           5   6   7   8   9   10  ",
    "11  12                                          13  14  15  16  17  18  ",
    "19  20  21  22  23  24  25  26  27  28  29  30  31  32  33  34  35  36  ",
    "37  38  39  40  41  42  43  44  45  46  47  48  49  51  51  52  53  54  ",
    "55  56  71  72  73  74  75  76  77  78  79  80  81  82  83  84  85  86  ",
    "87  88  103 104 105 106 107 108 109 110 111 112 113 114 115 116 117 118 ",
    "                                                                        ",
    "57  58  59  60  61  62  63  64  65  66  67  68  69  70                  ",
    "89  90  91  92  93  94  95  96  97  98  99  100 101 102                 ",
    "                                                                        ",    
    "320 300 400 221                                                         "};


    static HashMap a_map = init(at_table, nr_table);
    static HashMap n_map = init(nr_table, at_table);

    private static HashMap init(String[] X, String[] Y)
    {
    	HashMap res = new HashMap();
	for (int i=0; i < X.length; i++)
	{
		String[] x = X[i].split("\\b");
		String[] y = Y[i].split("\\b");
		for (int j=0; j < x.length; j++)
			{
				if (x[j].equals("")) continue;
				res.put(x[j].trim(), y[j].trim());
			}
	}
	return res;
    }

    public static String AtomName(int n)
    {
        if (!n_map.containsKey(new Integer(n).toString()))
		return "Du";

	return (String)n_map.get(new Integer(n).toString());
    }

    public static int AtomNumber(String e)
    {
        if (!a_map.containsKey(e))
		return -1;

	return Integer.parseInt( (String)a_map.get(e) );
    }
    
    public String toString()
    {
     StringBuffer sb = new StringBuffer(1024);

     for (int i=0; i < at_table.length; i++)
	{
		sb.append(nr_table[i]);
		sb.append(System.getProperty("line.separator"));
		sb.append(at_table[i]);
		sb.append(System.getProperty("line.separator"));
		sb.append(System.getProperty("line.separator"));
	}

     return sb.toString();
    }
}
