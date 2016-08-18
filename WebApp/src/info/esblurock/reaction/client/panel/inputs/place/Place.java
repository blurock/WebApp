package info.esblurock.reaction.client.panel.inputs.place;

import info.esblurock.reaction.client.panel.contact.OrganizationInput;
import info.esblurock.reaction.client.panel.contact.UserContactInput;
import info.esblurock.reaction.client.panel.inputs.InputSet;
import info.esblurock.reaction.client.panel.inputs.SetOfInputs;
import info.esblurock.reaction.client.panel.transaction.ObjectTransaction;
import info.esblurock.reaction.client.panel.transaction.TransactionSources;
import info.esblurock.reaction.client.panel.transaction.UploadFileSetsTransactions;
import com.google.gwt.user.client.Cookies;

import com.google.gwt.user.client.ui.Widget;

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
			return "UserContact";
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
			return new UserContactInput(name);
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
			SetOfInputs inputs = new SetOfInputs(InputSet.chemkin, "Chemkin");
			return inputs;
		}
	},
	molecules {
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
			SetOfInputs inputs = new SetOfInputs(InputSet.sdfmolecules, "2D-Molecules(SDF)");
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
			SetOfInputs inputs = new SetOfInputs(InputSet.sdfsubstructures,"2D-Substructures(SDF)");
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
			SetOfInputs inputs = new SetOfInputs(InputSet.thergasmolecules, "2D-Thermo");
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
			SetOfInputs inputs = new SetOfInputs(InputSet.nasapolynomials, "NASAPolynomials");
			return inputs;
		}
	},
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
