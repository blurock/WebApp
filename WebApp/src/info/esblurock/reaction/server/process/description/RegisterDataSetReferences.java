package info.esblurock.reaction.server.process.description;

import java.io.IOException;
import java.util.ArrayList;

import info.esblurock.reaction.data.description.DataSetReference;
import info.esblurock.reaction.data.description.DataSetReferencesTransaction;
import info.esblurock.reaction.data.store.StoreObject;
import info.esblurock.reaction.server.process.ProcessBase;
import info.esblurock.reaction.server.process.ProcessInputSpecificationsBase;

public class RegisterDataSetReferences extends ProcessBase {
	
	ArrayList<DataSetReference> referencelist;
	
	//static private String reference = "Reference";
	static private String lastnamePredicate = "AuthorLastName";
	static private String namePredicate = "Author";
	static private String title = "DataSetTitle";
	static private String doi = "DOI";
	static private String citation = "Citation";
	
	String datadescriptionS;
	String referenceS;
	
	public RegisterDataSetReferences() {
		super();
	}

	public RegisterDataSetReferences(ProcessInputSpecificationsBase input) {
		super(input);
		DataSetReferencesSpecifications references = (DataSetReferencesSpecifications) input;
		referencelist = references.getReferences();
	}

	@Override
	public void initialization() {
		referenceS = "info.esblurock.reaction.data.description.DataSetReferencesTransaction"; 
	}

	@Override
	protected String getProcessName() {
		return "RegisterDataSetReferences";
	}

	@Override
	protected String getProcessDescription() {
		return "Register the the set of references for the dataset";
	}

	@Override
	protected ArrayList<String> getInputTransactionObjectNames() {
		ArrayList<String> input = new ArrayList<String>();
		return input;
	}

	@Override
	protected ArrayList<String> getOutputTransactionObjectNames() {
		ArrayList<String> output = new ArrayList<String>();
		output.add(referenceS);
		return output;
	}

	@Override
	protected void initializeOutputObjects() throws IOException {
		super.initializeOutputObjects();
		DataSetReferencesTransaction transaction = new DataSetReferencesTransaction(user, outputSourceCode, keyword);
		objectOutputs.add(transaction);
	}
	
	@Override
	protected void createObjects() throws IOException {
		System.out.println("RegisterDataSetReferences: DataSetReferencesTransaction  createObjects()");
		StoreObject store = new StoreObject(user, keyword, outputSourceCode);		
		for(DataSetReference ref : referencelist) {
			store.store(ref);
			store.storeStringRDF(title, ref.getReferenceTitle());
			store.storeStringRDF(doi, ref.getDOI());
			store.storeStringRDF(citation, ref.getReferenceString());
			for(String lastname : ref.getAuthorLastNames()) {
				store.storeStringRDF(lastnamePredicate, lastname);
			}
			for(String name : ref.getAuthors()) {
				store.storeStringRDF(namePredicate, name);
			}
		}
		store.finish();
		for(DataSetReference ref : referencelist) {
			store.storeObjectRDF(ref);
		}
		store.finish();		
	}

}
