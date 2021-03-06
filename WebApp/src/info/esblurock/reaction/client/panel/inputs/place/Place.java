package info.esblurock.reaction.client.panel.inputs.place;

import info.esblurock.reaction.client.StoreDescriptionData;
import info.esblurock.reaction.client.StoreDescriptionDataAsync;
import info.esblurock.reaction.client.panel.contact.OrganizationInput;
import info.esblurock.reaction.client.panel.contact.UserContactInput;
import info.esblurock.reaction.client.panel.inputs.InputSet;
import info.esblurock.reaction.client.panel.inputs.SetOfInputs;
import info.esblurock.reaction.client.panel.query.synonyms.SetOfSynonymsPanel;
import info.esblurock.reaction.client.panel.transaction.ObjectTransaction;
import info.esblurock.reaction.client.panel.transaction.TransactionSources;
import info.esblurock.reaction.client.panel.transaction.UploadFileSetsTransactions;
import info.esblurock.reaction.client.ui.FillInUserContactInputCallback;

import com.google.gwt.user.client.Cookies;

import com.google.gwt.user.client.ui.Widget;

import gwt.material.design.client.ui.MaterialToast;

public enum Place {
	organization {
		@Override
		public String getTitle() {
			return "Organization";
		}

		@Override
		public String getDescription() {
			return "To input organizational information";
		}

		@Override
		public Widget getContent() {
			return new OrganizationInput("Organization");
		}
	},
	usercontact {
		@Override
		public String getTitle() {
			return "User Contact";
		}

		@Override
		public String getDescription() {
			return "To input user contact information";
		}

		@Override
		public Widget getContent() {
			String name = Cookies.getCookie("user");
			if(name == null) {
				name = "UserContact";
			}
			UserContactInput user = new UserContactInput(name);
			FillInUserContactInputCallback callback = new FillInUserContactInputCallback(user);
			StoreDescriptionDataAsync async = StoreDescriptionData.Util.getInstance();
			async.getUserDescriptionData(name, callback);
			return user;
			
		}
	},
	chemkin {
		@Override
		public String getTitle() {
			return "CHEMKIN format input";
		}

		@Override
		public String getDescription() {
			return "To input data in CHEMKIN format";
		}

		@Override
		public Widget getContent() {
			SetOfInputs inputs = new SetOfInputs(InputSet.chemkin);
			return inputs;
		}
	},
	sdfmolecules {
		@Override
		public String getTitle() {
			return "Molecule 2D input";
		}

		@Override
		public String getDescription() {
			return "Input a molecule in 2D SDF form and in NASA polynomial form";
		}

		@Override
		public Widget getContent() {
			SetOfInputs inputs = new SetOfInputs(InputSet.sdfmolecules);
			return inputs;
		}
	},
	sdfsubstructures {
		@Override
		public String getTitle() {
			
			return "Molecule 2D input";
		}

		@Override
		public String getDescription() {
			return "Input a substructure in 2D SDF form and in NASA polynomial form";
		}

		@Override
		public Widget getContent() {
			SetOfInputs inputs = new SetOfInputs(InputSet.sdfsubstructures);
			return inputs;
		}
	},
	thergasmolecules {
		@Override
		public String getTitle() {
			
			return "Molecule 2D input";
		}

		@Override
		public String getDescription() {
			return "Input a substructure in 2D SDF form and in NASA polynomial form";
		}

		@Override
		public Widget getContent() {
			SetOfInputs inputs = new SetOfInputs(InputSet.thergasmolecules);
			return inputs;
		}
	},
	nasapolynomials {
		@Override
		public String getTitle() {
			return "Molecule 2D input";
		}

		@Override
		public String getDescription() {
			return "Input a substructure in 2D SDF form and in NASA polynomial form";
		}

		@Override
		public Widget getContent() {
			SetOfInputs inputs = new SetOfInputs(InputSet.nasapolynomials);
			return inputs;
		}
	},
	standardKeywords {

		@Override
		public String getTitle() {
			return "Standard Keywords";
		}

		@Override
		public String getDescription() {
			return "Set up standard keyword synonyms";
		}

		@Override
		public Widget getContent() {
			SetOfSynonymsPanel panel = new SetOfSynonymsPanel();
			return panel;
		}
		
	},
	/*
	toprocess {
		@Override
		public String getTitle() {
			return "Transactions to process";
		}

		@Override
		public String getDescription() {
			return "Data which has been read in but not parsed";
		}

		@Override
		public Widget getContent() {
			return new TransactionSources();
		}
	},
	*/
	transactions {
		@Override
		public String getTitle() {
			return "Transactions";
		}

		@Override
		public String getDescription() {
			return "Full set of database entries for a transaction";
		}

		@Override
		public Widget getContent() {
			return new ObjectTransaction();
		}

},
	uploadsets {
		@Override
		public String getTitle() {
			return "Upload Sets";
		}

		@Override
		public String getDescription() {
			return "Uploaded File Sets to process";
		}

		@Override
		public Widget getContent() {
			return new UploadFileSetsTransactions();
		}

};

	public abstract String getTitle();

	public abstract String getDescription();

	public abstract Widget getContent();

}
