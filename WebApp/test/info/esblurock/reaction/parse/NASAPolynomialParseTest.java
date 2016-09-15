package info.esblurock.reaction.parse;


import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;

import thermo.data.benson.NASAPolynomial;

public class NASAPolynomialParseTest {

	@Test
	public void test() {
		String l1 = "ch2oh              mar97c   1h   3o   1     g   300.00   5000.00  1410.00      1";
		String l2 = " 6.00127803e 00 4.98721568e-03-1.60953515e-06 2.40227135e-10-1.35582700e-14    2";
		String l3 = "-3.50157098e 03-6.92836844e 00 2.60067849e 00 1.28447854e-02-8.33796185e-06    3";
		String l4 = " 2.75727606e-09-3.57041106e-13-2.33478724e 03 1.13272307e 01                   4";
		
		
		NASAPolynomial nasa = new NASAPolynomial();
		try {
			nasa.parse(l1, l2, l3, l4);
			
			System.out.println(nasa.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
