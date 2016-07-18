package info.esblurock.reaction.data.delete;

import java.io.IOException;

import java.util.StringTokenizer;

import info.esblurock.reaction.data.upload.FileUploadLines;
import info.esblurock.reaction.server.queries.QueryBase;
import info.esblurock.reaction.data.chemical.elements.ChemicalElementListData;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.data.chemical.thermo.NASAPolynomialData;
import info.esblurock.reaction.data.chemical.transport.SpeciesTransportProperty;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.upload.ChemkinMechanismFileSpecification;
import info.esblurock.reaction.data.upload.NASAPolynomialFileSpecification;
import info.esblurock.reaction.data.upload.TransportFileSpecification;
import info.esblurock.reaction.data.upload.types.NASAPolynomialFileUpload;
import info.esblurock.reaction.data.upload.types.TransportFileUpload;
import info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload;
import info.esblurock.reaction.data.transaction.chemkin.MechanismMoleculesToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.chemkin.MechanismReactionsToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.chemkin.NASAPolynomialsToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.chemkin.TransportPropertiesToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.chemkin.rdf.MechanismMoleculeRDFTransaction;
import info.esblurock.reaction.data.transaction.chemkin.rdf.MechanismReactionsRDFTransaction;
import info.esblurock.reaction.data.transaction.chemkin.rdf.NASAPolynomialRDFTransaction;
import info.esblurock.reaction.data.transaction.chemkin.rdf.TransportPropertiesRDFTransaction;

public enum DeleteDataStructures {

	DescriptionDataData {

		@Override
		public String deleteStructure(String key) throws IOException {
			QueryBase.deleteWithStringKey(DescriptionDataData.class,key);
			return "delete DescriptionDataData with key: " + key;
		}
		
	}, ChemkinMechanismFileSpecification {

		@Override
		public String deleteStructure(String key) throws IOException {
			QueryBase.deleteWithStringKey(ChemkinMechanismFileSpecification.class, key);
			return "delete ChemkinMechanismFileUpload with key: " + key;
		}
		
	}, NASAPolynomialFileSpecification {

		@Override
		public String deleteStructure(String key) throws IOException {
			QueryBase.deleteWithStringKey(NASAPolynomialFileSpecification.class, key);
			return "delete ChemkinMechanismFileUpload with key: " + key;
		}
		
	}, TransportFileSpecification {

		@Override
		public String deleteStructure(String key) throws IOException {
			QueryBase.deleteWithStringKey(TransportFileSpecification.class, key);
			return "delete TransportFileSpecification with key: " + key;
		}
		
	}, ChemkinMechanismFileUpload {
		@Override
		public String deleteStructure(String key) throws IOException {
			ChemkinMechanismFileUpload upload =  (ChemkinMechanismFileUpload) 
					QueryBase.getObjectById(ChemkinMechanismFileUpload.class, key);
			QueryBase.deleteFromIdentificationCode(FileUploadLines.class, "fileCode", upload.getFileCode());
			QueryBase.deleteWithStringKey(ChemkinMechanismFileUpload.class, key);
			return "delete TransportFileSpecification with key: " + key;
		}
		
	}, NASAPolynomialFileUpload {
		@Override
		public String deleteStructure(String key) throws IOException {
			NASAPolynomialFileUpload upload =  (NASAPolynomialFileUpload) 
					QueryBase.getObjectById(NASAPolynomialFileUpload.class, key);
			QueryBase.deleteFromIdentificationCode(FileUploadLines.class, "fileCode", upload.getFileCode());
			QueryBase.deleteWithStringKey(NASAPolynomialFileUpload.class, key);
			return "delete ChemkinMechanismFileUpload with key: " + key;
		}
		
	}, TransportFileUpload {
		@Override
		public String deleteStructure(String key) throws IOException {
			TransportFileUpload upload =  (TransportFileUpload) 
					QueryBase.getObjectById(TransportFileUpload.class, key);
			QueryBase.deleteFromIdentificationCode(FileUploadLines.class, "fileCode", upload.getFileCode());
			QueryBase.deleteWithStringKey(TransportFileUpload.class, key);
			return "delete TransportFileUpload with key: " + key;
		}
	},
	
	MechanismMoleculesToDatabaseTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			MechanismMoleculesToDatabaseTransaction transaction = (MechanismMoleculesToDatabaseTransaction)
					QueryBase.getObjectById(MechanismMoleculesToDatabaseTransaction.class,key);
			QueryBase.deleteWithStringKey(MechanismMoleculesToDatabaseTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(MechanismMoleculeData.class, "mechanismKeyword", transaction.getKeyWord());
			QueryBase.deleteFromIdentificationCode(ChemicalElementListData.class, "mechanismKeyword", transaction.getKeyWord());
			return "delete MechanismMoleculesToDatabaseTransaction with key: " + key;
		}
		
	}, MechanismReactionsToDatabaseTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			MechanismReactionsToDatabaseTransaction transaction = (MechanismReactionsToDatabaseTransaction)
					QueryBase.getObjectById(MechanismReactionsToDatabaseTransaction.class,key);
			QueryBase.deleteWithStringKey(MechanismReactionsToDatabaseTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(ChemkinReactionData.class, "mechanismKeyword", transaction.getKeyWord());
			return "delete MechanismReactionsToDatabaseTransaction with key: " + key;
		}
		
	}, NASAPolynomialsToDatabaseTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			NASAPolynomialsToDatabaseTransaction transaction = (NASAPolynomialsToDatabaseTransaction)
					QueryBase.getObjectById(NASAPolynomialsToDatabaseTransaction.class,key);
			QueryBase.deleteWithStringKey(MechanismReactionsToDatabaseTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(NASAPolynomialData.class, "mechanismKeyword", transaction.getKeyWord());
			return "delete NASAPolynomialsToDatabaseTransaction with key: " + key;
		}
		
	}, TransportPropertiesToDatabaseTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			TransportPropertiesToDatabaseTransaction transaction = (TransportPropertiesToDatabaseTransaction)
					QueryBase.getObjectById(TransportPropertiesToDatabaseTransaction.class,key);
			QueryBase.deleteWithStringKey(TransportPropertiesToDatabaseTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(SpeciesTransportProperty.class, "mechanismKeyword", transaction.getKeyWord());
			return "delete TransportPropertiesToDatabaseTransaction with key: " + key;
		}
		
	}, MechanismMoleculeRDFTransaction{

		@Override
		public String deleteStructure(String key) throws IOException {
			MechanismMoleculeRDFTransaction transaction = (MechanismMoleculeRDFTransaction)
					QueryBase.getObjectById(MechanismMoleculeRDFTransaction.class,key);
			QueryBase.deleteWithStringKey(MechanismMoleculeRDFTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(KeywordRDF.class, "sourceCode", transaction.getFileCode());
			return "delete MechanismMoleculeRDFTransaction with key: " + key;
		}
		
	}, MechanismReactionsRDFTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			MechanismReactionsRDFTransaction transaction = (MechanismReactionsRDFTransaction)
					QueryBase.getObjectById(MechanismReactionsRDFTransaction.class,key);
			QueryBase.deleteWithStringKey(MechanismReactionsRDFTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(KeywordRDF.class, "sourceCode", transaction.getFileCode());
			return "delete MechanismMoleculeRDFTransaction with key: " + key;
		}
		
	}, NASAPolynomialRDFTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			NASAPolynomialRDFTransaction transaction = (NASAPolynomialRDFTransaction)
					QueryBase.getObjectById(NASAPolynomialRDFTransaction.class,key);
			QueryBase.deleteWithStringKey(NASAPolynomialRDFTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(KeywordRDF.class, "sourceCode", transaction.getFileCode());
			return "delete NASAPolynomialRDFTransaction with key: " + key;
		}
		
	}, TransportPropertiesRDFTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			TransportPropertiesRDFTransaction transaction = (TransportPropertiesRDFTransaction)
					QueryBase.getObjectById(TransportPropertiesRDFTransaction.class,key);
			QueryBase.deleteWithStringKey(TransportPropertiesRDFTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(KeywordRDF.class, "sourceCode", transaction.getFileCode());
			return "delete MechanismMoleculeRDFTransaction with key: " + key;
		}
		
	},
	TextSetUploadData {
		@Override
		public String deleteStructure(String key) throws IOException {
			//DeleteTextSetUploadData delete = new DeleteTextSetUploadData();
			//String ans = delete.delete(key);
			return null;
		}
	},
	ChemicalMechanismData {
		@Override
		public String deleteStructure(String key) throws IOException {			
			//ChemicalMechanismDataQuery query = new ChemicalMechanismDataQuery();
			//query.deleteChemicalMechanismDataFromKey(key);
			return key;
		}
		
	},
	SetOfNASAPolynomialData {

		@Override
		public String deleteStructure(String key) throws IOException {
			//NASAPolynomialDataQuery query = new NASAPolynomialDataQuery();
			//query.deleteNASAPolynomialDataFromKey(key);
			return key;
		}
		
	};
	

	public abstract String deleteStructure(String key) throws IOException;
	
	/**
	 * Find key root.
	 *
	 * @param key the key
	 * @return the string
	 */
	protected static String findKeyRoot(String key) {
		StringTokenizer tok = new StringTokenizer(key, ".");
		String ans = "";
		while (tok.hasMoreTokens()) {
			ans = tok.nextToken();
		}
		return ans;
	}
	
	public static String deleteObject(String fullclassname, String key) throws IOException {
		System.out.println("DeleteDataStructure: " + fullclassname);
		String root = findKeyRoot(fullclassname);
		System.out.println("DeleteDataStructure: " + root);
		String ans = valueOf(root).deleteStructure(key);
		return ans;
	}
}
