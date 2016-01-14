package info.esblurock.reaction.client.panel.inputs;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.TextToDatabase;
import info.esblurock.reaction.client.TextToDatabaseAsync;
import info.esblurock.reaction.client.panel.transaction.SourceTransaction;
import info.esblurock.reaction.client.resources.InputConstants;

import com.google.appengine.api.datastore.Key;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.Date;
import java.util.Set;
public class ClientDataInputToDatabase {

	TextToDatabaseAsync async = TextToDatabase.Util.getInstance();
	InputConstants inputConstants = GWT.create(InputConstants.class);
	
	DataInput data;
	String inputname;
	
	public void textToDatabase(String name, String text, DataInput datainput) {
		
		this.data = datainput;
		this.inputname = name;
		
	AsyncCallback<String> callback = new AsyncCallback<String>() {

		@Override
		public void onFailure(Throwable caught) {
			System.out.println("textToDatabase: ERROR");
			System.out.println(caught.toString());
			Window.alert(caught.toString());
		}

		@Override
		public void onSuccess(String result) {
			MaterialToast.alert("Key: " + result);
			/*
			data.setUploadID(result);
			data.setuUploadFile(inputname);
			data.setInputSource(inputConstants.textfile());		
			*/	
		}
	};
	async.textToDatabase(name, text, callback);
	}

	public void httpToDatabase(String http, DataInput datainput) {
	AsyncCallback<String> callback = new AsyncCallback<String>() {

		@Override
		public void onFailure(Throwable caught) {
			MaterialToast.alert(caught.toString());
		}

		@Override
		public void onSuccess(String result) {
			MaterialToast.alert("Key: " + result);
			/*
			data.setuUploadFile(http);
			data.setUploadID(result);
			data.setInputSource(inputConstants.uploadhttp());
			*/
		}
		
		
	};
	async.httpToDatabase(http, callback);
	}



}
