/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.esblurock.react.parse.keywords;

import java.util.HashSet;
import java.util.Iterator;

/**
 *
 * @author edwardblurock
 */
public class SetOfKeyWords extends HashSet<String> {
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
