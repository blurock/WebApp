package info.esblurock.reaction.client.panel.description;

import java.util.ArrayList;
import java.util.HashSet;

import com.google.gwt.user.client.rpc.AsyncCallback;

import info.esblurock.react.parse.keywords.SetOfKeyWords;

public class KeywordsFromTextCallback implements AsyncCallback<HashSet<String>>{
	
	DataDescription datadescr;
	
	public KeywordsFromTextCallback(DataDescription datadescr) {
		this.datadescr = datadescr;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSuccess(HashSet<String> result) {
		ArrayList<KeywordChip> keywordset = new ArrayList<KeywordChip>();
		for(String name : result) {
			KeywordChip chip = new KeywordChip(name);
			keywordset.add(chip);
		}
		datadescr.setKeywords(keywordset);
	}

}
