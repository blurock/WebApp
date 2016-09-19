/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.esblurock.react.parse.keywords;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author edwardblurock
 */
public class SetOfKeyWords extends ArrayList<String> implements Serializable {
	private static final long serialVersionUID = 1L;

	public String toString() {
        StringBuilder build = new StringBuilder();
        Iterator<String> iterator = this.iterator();
        while(iterator.hasNext()) {
            String next = iterator.next();
            build.append(next);
            build.append("\n");
        }
        return build.toString();
    }
}
