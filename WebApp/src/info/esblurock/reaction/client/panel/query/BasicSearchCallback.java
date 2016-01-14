package info.esblurock.reaction.client.panel.query;


import java.util.ArrayList;
import java.util.Set;

import gwt.material.design.client.ui.MaterialCollapsible;
import gwt.material.design.client.ui.MaterialCollapsibleItem;
import gwt.material.design.client.ui.MaterialLabel;
import gwt.material.design.client.ui.MaterialLink;
import gwt.material.design.client.ui.MaterialTextArea;
import gwt.material.design.client.ui.MaterialTitle;
import info.esblurock.reaction.data.rdf.RDFQueryToStringSet;
import info.esblurock.reaction.data.rdf.SetOfKeywordQueryAnswers;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;

public class BasicSearchCallback implements AsyncCallback<SetOfKeywordQueryAnswers> {

	MaterialCollapsibleItem topSearch;
	
	public BasicSearchCallback(MaterialCollapsibleItem search) {
		topSearch= search;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.toString());
	}

	@Override
	public void onSuccess(SetOfKeywordQueryAnswers result) {

		String message = "Size: " + result.getKeys().size();
		Window.alert(message);

		HTMLPanel toppanel = new HTMLPanel("");		
		topSearch.addContent(toppanel);

		
		Set<String> topset = result.getKeys();
		MaterialCollapsible collapse = new MaterialCollapsible();
		collapse.setType("Popout");
		toppanel.add(collapse);
		
		String stringKey = "String";
		HTMLPanel stringpanel = new HTMLPanel("");
		MaterialLabel stringlabel = new MaterialLabel(stringKey);
		MaterialCollapsibleItem stringitem = new MaterialCollapsibleItem();
		stringitem.addHeader(stringlabel);
		stringitem.addContent(stringpanel);
		collapse.addItem(stringitem);

		
		RDFQueryToStringSet stringset = result.get(stringKey);
		Set<String> stringkeys = stringset.keySet();
		
		StringBuilder build = new StringBuilder();
		for(String topkey : stringkeys) {
			build.append(topkey);
			build.append(",\t");
		}
		Window.alert(build.toString());
		
		
		
		MaterialCollapsible stringsetcollapse = new MaterialCollapsible();
		stringsetcollapse.setType("popout");
		stringpanel.add(stringsetcollapse);
		for(String key: stringkeys) {
			MaterialCollapsibleItem item = new MaterialCollapsibleItem();
			MaterialLabel label = new MaterialLabel(key);
			HTMLPanel substringpanel = new HTMLPanel("");
			item.addHeader(label);
			item.addContent(substringpanel);
			stringsetcollapse.addItem(item);
			
			ArrayList<String> strings = stringset.get(key);
			MaterialCollapsible stringanscollapse = new MaterialCollapsible();
			substringpanel.add(stringanscollapse);
			for(String ans : strings) {
				MaterialCollapsibleItem substring = new MaterialCollapsibleItem();
				MaterialLink sublabel = new MaterialLink(key);
				if(ans.length() < 80) {
					MaterialTitle slabel = new MaterialTitle();
					slabel.setDescription(ans);
					substring.addContent(slabel);
				} else {
					MaterialTextArea subtext = new MaterialTextArea();
					//subtext.setTitle(key);
					subtext.setText(ans);
					substring.addContent(subtext);
				}
				substring.setHeader(sublabel);
				stringsetcollapse.addItem(substring);
			}
		}
		
		}
		

	}
