package info.esblurock.reaction.data.delete;

import java.io.IOException;

import java.util.StringTokenizer;

import info.esblurock.reaction.data.upload.FileUploadLines;
import info.esblurock.reaction.data.upload.FileUploadTextBlock;
import info.esblurock.reaction.server.queries.QueryBase;
import info.esblurock.reaction.data.chemical.elements.ChemicalElementListData;
import info.esblurock.reaction.data.chemical.molecule.MechanismMoleculeData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinCoefficientsData;
import info.esblurock.reaction.data.chemical.reaction.ChemkinReactionData;
import info.esblurock.reaction.data.chemical.thermo.NASAPolynomialData;
import info.esblurock.reaction.data.chemical.thermo.ThergasMoleculeData;
import info.esblurock.reaction.data.chemical.transport.SpeciesTransportProperty;
import info.esblurock.reaction.data.description.DescriptionDataData;
import info.esblurock.reaction.data.description.DataSetReferencesTransaction;
import info.esblurock.reaction.data.description.DataSetReference;
import info.esblurock.reaction.data.rdf.KeywordRDF;
import info.esblurock.reaction.data.upload.types.ValidatedNASAPolynomialFile;
import info.esblurock.reaction.data.upload.types.ValidatedChemkinMechanismFile;
import info.esblurock.reaction.data.upload.types.ValidatedTransportFile;
import info.esblurock.reaction.data.upload.types.ValidatedThergasMoleculeFile;
import info.esblurock.reaction.data.upload.types.ValidatedReactSDFMoleculesFile;
import info.esblurock.reaction.data.upload.types.ValidatedReactMolCorrespondencesFile;
import info.esblurock.reaction.data.upload.ChemkinMechanismFileSpecification;
import info.esblurock.reaction.data.upload.NASAPolynomialFileSpecification;
import info.esblurock.reaction.data.upload.ReactMolCorrespondencesFileSpecification;
import info.esblurock.reaction.data.upload.SDFMoleculesFileSpecification;
import info.esblurock.reaction.data.upload.ThergasMoleculeFileSpecification;
import info.esblurock.reaction.data.upload.TransportFileSpecification;
import info.esblurock.reaction.data.upload.UploadFileTransaction;
import info.esblurock.reaction.data.upload.types.NASAPolynomialFileUpload;
import info.esblurock.reaction.data.upload.types.ReactMolCorrespondencesFileUpload;
import info.esblurock.reaction.data.upload.types.TransportFileUpload;
import info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload;
import info.esblurock.reaction.data.upload.types.ThergasMoleculeFileUpload;
import info.esblurock.reaction.data.upload.types.SDFMoleculesFileUpload;
import info.esblurock.reaction.data.transaction.chemkin.MechanismMoleculesToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.chemkin.MechanismReactionsToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.chemkin.NASAPolynomialsToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.chemkin.TransportPropertiesToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.chemkin.rdf.MechanismMoleculeRDFTransaction;
import info.esblurock.reaction.data.transaction.chemkin.rdf.MechanismReactionsRDFTransaction;
import info.esblurock.reaction.data.transaction.chemkin.rdf.NASAPolynomialRDFTransaction;
import info.esblurock.reaction.data.transaction.chemkin.rdf.TransportPropertiesRDFTransaction;
import info.esblurock.reaction.data.transaction.thergas.ThergasMoleculeThermodynamics;
import info.esblurock.reaction.data.transaction.thergas.ThergasMoleculesToDatabaseTransaction;
import info.esblurock.reaction.data.transaction.thergas.ThergasMoleculesRDFTransaction;
import info.esblurock.reaction.data.transaction.reaction.ReactSDFMoleculesToDatabaseTransaction;


public enum DeleteDataStructures {

	DescriptionDataData {

		@Override
		public String deleteStructure(String key) throws IOException {
			QueryBase.deleteWithStringKey(DescriptionDataData.class, key);
			return "delete DescriptionDataData with key: " + key;
		}

	}, DataSetReferencesTransaction {
		@Override
		public String deleteStructure(String key) throws IOException {
			DataSetReferencesTransaction reftransaction = (DataSetReferencesTransaction) QueryBase
					.getObjectById(DataSetReferencesTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(DataSetReference.class,
					"DatasetKeyword", reftransaction.getKeyWord());
			QueryBase.deleteFromIdentificationCode(KeywordRDF.class,
					"sourceCode",reftransaction.getFileCode());
			QueryBase.deleteWithStringKey(DataSetReferencesTransaction.class, key);
			return "Delete set of references for " + key;
		}
		
	}, ChemkinMechanismFileSpecification {
		@Override
		public String deleteStructure(String key) throws IOException {
			try {
				QueryBase.deleteWithStringKey(ChemkinMechanismFileSpecification.class, key);
			} catch (IOException ex) {
				if (!ex.getMessage().startsWith(QueryBase.notfound)) {
					throw ex;
				}
			}
			return "delete ChemkinMechanismFileUpload with key: " + key;
		}

	}, ReactMolCorrespondencesFileSpecification {
		@Override
		public String deleteStructure(String key) throws IOException {
			try {
				QueryBase.deleteWithStringKey(ReactMolCorrespondencesFileSpecification.class, key);
			} catch (IOException ex) {
				if (!ex.getMessage().startsWith(QueryBase.notfound)) {
					throw ex;
				}
			}
			return "delete ReactMolCorrespondencesFileSpecification with key: " + key;
		}
		
	},
	ValidatedNASAPolynomialFile {

		@Override
		public String deleteStructure(String key) throws IOException {
			QueryBase.deleteWithStringKey(ValidatedNASAPolynomialFile.class, key);
			return "delete ValidatedNASAPolynomialFile with key: " + key;
		}

	},
	ValidatedChemkinMechanismFile {

		@Override
		public String deleteStructure(String key) throws IOException {
			QueryBase.deleteWithStringKey(ValidatedChemkinMechanismFile.class, key);
			return "delete ValidatedChemkinMechanismFile with key: " + key;
		}

	},
	ValidatedTransportFile {

		@Override
		public String deleteStructure(String key) throws IOException {
			QueryBase.deleteWithStringKey(ValidatedTransportFile.class, key);
			return "delete ValidatedTransportFile with key: " + key;
		}

	},
	ValidatedThergasMoleculeFile {

		@Override
		public String deleteStructure(String key) throws IOException {
			QueryBase.deleteWithStringKey(ValidatedThergasMoleculeFile.class, key);
			return "delete ValidatedTransportFile with key: " + key;
		}
/*
 * import info.esblurock.reaction.data.upload.types.ValidatedReactSDFMoleculesFile;
import info.esblurock.reaction.data.upload.types.ValidatedReactMolCorrespondencesFile;

 */
	},
	ValidatedReactSDFMoleculesFile {

		@Override
		public String deleteStructure(String key) throws IOException {
			QueryBase.deleteWithStringKey(ValidatedReactSDFMoleculesFile.class, key);
			return "delete ValidatedTransportFile with key: " + key;
		}

	},
	ValidatedReactMolCorrespondencesFile {

		@Override
		public String deleteStructure(String key) throws IOException {
			QueryBase.deleteWithStringKey(ValidatedReactMolCorrespondencesFile.class, key);
			return "delete ValidatedTransportFile with key: " + key;
		}

	},
	NASAPolynomialFileSpecification {
		@Override
		public String deleteStructure(String key) throws IOException {
			try {
				QueryBase.deleteWithStringKey(NASAPolynomialFileSpecification.class, key);
			} catch (IOException ex) {
				if (!ex.getMessage().startsWith(QueryBase.notfound)) {
					throw ex;
				}
			}
			return "delete ChemkinMechanismFileUpload with key: " + key;
		}

	},
	TransportFileSpecification {

		@Override
		public String deleteStructure(String key) throws IOException {
			try {
				QueryBase.deleteWithStringKey(TransportFileSpecification.class, key);
			} catch (IOException ex) {
				if (!ex.getMessage().startsWith(QueryBase.notfound)) {
					throw ex;
				}
			}
			return "delete TransportFileSpecification with key: " + key;
		}

	},  ThergasMoleculeFileSpecification {
		@Override
		public String deleteStructure(String key) throws IOException {
				ThergasMoleculeFileSpecification spec = (ThergasMoleculeFileSpecification)
						QueryBase.getObjectById(ThergasMoleculeFileSpecification.class, key);
				String fileCode = spec.getFileCode();
				if(spec.getSourceType().matches("File")) {
					QueryBase.deleteFromIdentificationCode(UploadFileTransaction.class, "fileCode", fileCode);
					//QueryBase.deleteFromIdentificationCode(FileUploadTextBlock.class, "fileCode", fileCode);
				}
				QueryBase.deleteWithStringKey(ThergasMoleculeFileSpecification.class, key);
			return "delete ThergasMoleculeFileSpecification with key: " + key;
		}
		
	}, SDFMoleculesFileSpecification {
		@Override
		public String deleteStructure(String key) throws IOException {
			SDFMoleculesFileSpecification upload = (SDFMoleculesFileSpecification) QueryBase
					.getObjectById(SDFMoleculesFileSpecification.class, key);
			return "delete SDFMoleculesFileSpecification with key: " + key;
		}
		
	},ChemkinMechanismFileUpload {
		@Override
		public String deleteStructure(String key) throws IOException {
			ChemkinMechanismFileUpload upload = (ChemkinMechanismFileUpload) QueryBase
					.getObjectById(ChemkinMechanismFileUpload.class, key);
			QueryBase.deleteFromIdentificationCode(FileUploadLines.class, "fileCode", upload.getFileCode());
			QueryBase.deleteWithStringKey(ChemkinMechanismFileUpload.class, key);
			return "delete TransportFileSpecification with key: " + key;
		}

	},
	NASAPolynomialFileUpload {
		@Override
		public String deleteStructure(String key) throws IOException {
			NASAPolynomialFileUpload upload = (NASAPolynomialFileUpload) QueryBase
					.getObjectById(NASAPolynomialFileUpload.class, key);
			QueryBase.deleteFromIdentificationCode(FileUploadTextBlock.class, "fileCode", upload.getFileCode());
			QueryBase.deleteWithStringKey(NASAPolynomialFileUpload.class, key);
			return "delete ChemkinMechanismFileUpload with key: " + key;
		}

	},
	TransportFileUpload {
		@Override
		public String deleteStructure(String key) throws IOException {
			TransportFileUpload upload = (TransportFileUpload) QueryBase.getObjectById(TransportFileUpload.class, key);
			QueryBase.deleteFromIdentificationCode(FileUploadLines.class, "fileCode", upload.getFileCode());
			QueryBase.deleteWithStringKey(TransportFileUpload.class, key);
			return "delete TransportFileUpload with key: " + key;
		}
	},  ThergasMoleculeFileUpload {

		@Override
		public String deleteStructure(String key) throws IOException {
			ThergasMoleculeFileUpload upload = (ThergasMoleculeFileUpload) 
					QueryBase.getObjectById(ThergasMoleculeFileUpload.class, key);
			QueryBase.deleteFromIdentificationCode(FileUploadTextBlock.class, "fileCode", upload.getFileCode());
			QueryBase.deleteWithStringKey(ThergasMoleculeFileUpload.class, key);
			return "delete ThergasMoleculeFileUpload with key: " + key;
		}
		
	}, ReactMolCorrespondencesFileUpload {
		@Override
		public String deleteStructure(String key) throws IOException {
			ReactMolCorrespondencesFileUpload upload = (ReactMolCorrespondencesFileUpload) 
					QueryBase.getObjectById(ReactMolCorrespondencesFileUpload.class, key);
			QueryBase.deleteFromIdentificationCode(FileUploadTextBlock.class, "fileCode", upload.getFileCode());
			QueryBase.deleteWithStringKey(ReactMolCorrespondencesFileUpload.class, key);
			return "delete ReactMolCorrespondencesFileUpload with key: " + key;
		}
	}, SDFMoleculesFileUpload {
		@Override
		public String deleteStructure(String key) throws IOException {
			SDFMoleculesFileUpload upload = (SDFMoleculesFileUpload) 
					QueryBase.getObjectById(SDFMoleculesFileUpload.class, key);
			QueryBase.deleteFromIdentificationCode(FileUploadTextBlock.class, "fileCode", upload.getFileCode());
			QueryBase.deleteWithStringKey(SDFMoleculesFileUpload.class, key);
			return "delete ReactMolCorrespondencesFileUpload with key: " + key;
		}
	},
	
	MechanismMoleculesToDatabaseTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			MechanismMoleculesToDatabaseTransaction transaction = (MechanismMoleculesToDatabaseTransaction) QueryBase
					.getObjectById(MechanismMoleculesToDatabaseTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(MechanismMoleculeData.class, "mechanismKeyword",
					transaction.getKeyWord());
			QueryBase.deleteFromIdentificationCode(ChemicalElementListData.class, "mechanismKeyword",
					transaction.getKeyWord());
			QueryBase.deleteWithStringKey(MechanismMoleculesToDatabaseTransaction.class, key);
			return "delete MechanismMoleculesToDatabaseTransaction with key: " + key;
		}

	},
	MechanismReactionsToDatabaseTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			MechanismReactionsToDatabaseTransaction transaction = (MechanismReactionsToDatabaseTransaction) QueryBase
					.getObjectById(MechanismReactionsToDatabaseTransaction.class, key);
			QueryBase.deleteWithStringKey(MechanismReactionsToDatabaseTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(ChemkinReactionData.class, "mechanismKeyword",
					transaction.getKeyWord());
			QueryBase.deleteFromIdentificationCode(ChemkinCoefficientsData.class, "mechanismKeyword",
					transaction.getKeyWord());
			return "delete MechanismReactionsToDatabaseTransaction with key: " + key;
		}

	},
	NASAPolynomialsToDatabaseTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			NASAPolynomialsToDatabaseTransaction transaction = (NASAPolynomialsToDatabaseTransaction) QueryBase
					.getObjectById(NASAPolynomialsToDatabaseTransaction.class, key);
			QueryBase.deleteWithStringKey(NASAPolynomialsToDatabaseTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(NASAPolynomialData.class, "mechanismKeyword",
					transaction.getKeyWord());
			return "delete NASAPolynomialsToDatabaseTransaction with key: " + key;
		}

	},
	TransportPropertiesToDatabaseTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			TransportPropertiesToDatabaseTransaction transaction = (TransportPropertiesToDatabaseTransaction) QueryBase
					.getObjectById(TransportPropertiesToDatabaseTransaction.class, key);
			QueryBase.deleteWithStringKey(TransportPropertiesToDatabaseTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(SpeciesTransportProperty.class, "mechanismKeyword",
					transaction.getKeyWord());
			return "delete TransportPropertiesToDatabaseTransaction with key: " + key;
		}

	}, ThergasMoleculesToDatabaseTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			ThergasMoleculesToDatabaseTransaction transaction = (ThergasMoleculesToDatabaseTransaction) QueryBase
					.getObjectById(ThergasMoleculesToDatabaseTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(ThergasMoleculeData.class, "datasetKeyword",
					transaction.getKeyWord());
			QueryBase.deleteFromIdentificationCode(ThergasMoleculeThermodynamics.class, "datasetKeyword",
					transaction.getKeyWord());
			QueryBase.deleteWithStringKey(ThergasMoleculesToDatabaseTransaction.class, key);
			return "delete ThergasMoleculesToDatabaseTransaction with key: " + key;
		}
		
	}, ReactSDFMoleculesToDatabaseTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			ReactSDFMoleculesToDatabaseTransaction transaction = (ReactSDFMoleculesToDatabaseTransaction) QueryBase
					.getObjectById(ReactSDFMoleculesToDatabaseTransaction.class, key);
			
			QueryBase.deleteWithStringKey(ReactSDFMoleculesToDatabaseTransaction.class, key);
			return "delete ReactSDFMoleculesToDatabaseTransaction with key: " + key;
		}
		
	},
	MechanismMoleculeRDFTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			MechanismMoleculeRDFTransaction transaction = (MechanismMoleculeRDFTransaction) QueryBase
					.getObjectById(MechanismMoleculeRDFTransaction.class, key);
			QueryBase.deleteWithStringKey(MechanismMoleculeRDFTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(KeywordRDF.class, "sourceCode", transaction.getFileCode());
			return "delete MechanismMoleculeRDFTransaction with key: " + key;
		}

	},
	MechanismReactionsRDFTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			MechanismReactionsRDFTransaction transaction = (MechanismReactionsRDFTransaction) QueryBase
					.getObjectById(MechanismReactionsRDFTransaction.class, key);
			QueryBase.deleteWithStringKey(MechanismReactionsRDFTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(KeywordRDF.class, "sourceCode", transaction.getFileCode());
			return "delete MechanismMoleculeRDFTransaction with key: " + key;
		}

	},
	NASAPolynomialRDFTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			NASAPolynomialRDFTransaction transaction = (NASAPolynomialRDFTransaction) QueryBase
					.getObjectById(NASAPolynomialRDFTransaction.class, key);
			QueryBase.deleteWithStringKey(NASAPolynomialRDFTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(KeywordRDF.class, "sourceCode", transaction.getFileCode());
			return "delete NASAPolynomialRDFTransaction with key: " + key;
		}

	},
	TransportPropertiesRDFTransaction {

		@Override
		public String deleteStructure(String key) throws IOException {
			TransportPropertiesRDFTransaction transaction = (TransportPropertiesRDFTransaction) QueryBase
					.getObjectById(TransportPropertiesRDFTransaction.class, key);
			QueryBase.deleteWithStringKey(TransportPropertiesRDFTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(KeywordRDF.class, "sourceCode", transaction.getFileCode());
			return "delete MechanismMoleculeRDFTransaction with key: " + key;
		}

	}, ThergasMoleculesRDFTransaction {
		@Override
		public String deleteStructure(String key) throws IOException {
			ThergasMoleculesRDFTransaction transaction = (ThergasMoleculesRDFTransaction) QueryBase
					.getObjectById(ThergasMoleculesRDFTransaction.class, key);
			QueryBase.deleteWithStringKey(ThergasMoleculesRDFTransaction.class, key);
			QueryBase.deleteFromIdentificationCode(KeywordRDF.class, "sourceCode", transaction.getFileCode());
			return "delete ThergasMoleculesRDFTransaction with key: " + key;
		}
		
	},
	TextSetUploadData {
		@Override
		public String deleteStructure(String key) throws IOException {
			// DeleteTextSetUploadData delete = new DeleteTextSetUploadData();
			// String ans = delete.delete(key);
			return null;
		}
	},
	ChemicalMechanismData {
		@Override
		public String deleteStructure(String key) throws IOException {
			// ChemicalMechanismDataQuery query = new
			// ChemicalMechanismDataQuery();
			// query.deleteChemicalMechanismDataFromKey(key);
			return key;
		}

	},
	SetOfNASAPolynomialData {

		@Override
		public String deleteStructure(String key) throws IOException {
			// NASAPolynomialDataQuery query = new NASAPolynomialDataQuery();
			// query.deleteNASAPolynomialDataFromKey(key);
			return key;
		}

	};

	public abstract String deleteStructure(String key) throws IOException;

	/**
	 * Find key root.
	 *
	 * @param key
	 *            the key
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
		String root = findKeyRoot(fullclassname);
		System.out.println("DeleteDataStructure: " + root);
		String ans = valueOf(root).deleteStructure(key);
		return ans;
	}
}
