/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package info.esblurock.reaction.server;

import java.io.File;

/**
 *
 * @author edwardblurock
 */
public class Resource {
    private String categorizeResource= "resources/en-pos-maxent.bin";
    private String tokenResource= "resources/en-token.bin";
    private String chunkerResource = "resources/en-chunker.bin";
    
    public Resource() {
        setToEnglish();
    }

    public File getCategorizeResource() {
    	ClassLoader classLoader = getClass().getClassLoader();
    	File file = new File(classLoader.getResource(categorizeResource).getFile());
        return file;
    }

    public File getTokenResource() {
    	ClassLoader classLoader = getClass().getClassLoader();
    	File file = new File(classLoader.getResource(tokenResource).getFile());
        return file;
    }   

    public File getChunkerResource() {
    	ClassLoader classLoader = getClass().getClassLoader();
    	File file = new File(classLoader.getResource(chunkerResource).getFile());
        return file;
    }
    
    
    public void setToEnglish(){
    categorizeResource = "resources/en-pos-maxent.bin";
    tokenResource      = "resources/en-token.bin";
    chunkerResource    = "resources/en-chunker.bin";
    }
}
