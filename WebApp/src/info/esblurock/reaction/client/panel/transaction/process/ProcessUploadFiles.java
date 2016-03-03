package info.esblurock.reaction.client.panel.transaction.process;

import java.io.IOException;

import com.google.gwt.user.client.Window;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.ReactionProcessUploadedLines;
import info.esblurock.reaction.client.ReactionProcessUploadedLinesAsync;
import info.esblurock.reaction.data.chemical.thermo.ProcessNASAPolynomialUpload;
import info.esblurock.reaction.data.description.DescriptionDataData;

public enum ProcessUploadFiles {
	CHEMKINMechanism {

		@Override
		public void process(DescriptionDataData description, String key, String file, boolean process) {
			MaterialToast.alert("Process Data");
			ProcessUploadCallback callback =new ProcessUploadCallback();
			ReactionProcessUploadedLinesAsync async = ReactionProcessUploadedLines.Util.getInstance();
			async.processUploadedMechanism(description, key, file, process, callback);				
		}
		@Override
		public String getFullTypeName() {
			return "info.esblurock.react.mechanisms.chemkin.ChemkinMechanism";
		}
	},
	NASAPolynomials {

		public void process(DescriptionDataData description, String key, String file, boolean process) {
			MaterialToast.alert("Process Data");
			ProcessUploadCallback callback =new ProcessUploadCallback();
			ReactionProcessUploadedLinesAsync async = ReactionProcessUploadedLines.Util.getInstance();
			async.processUploadedSetOfNASAPolynomial(description, key, file, process, callback);				
			}

		@Override
		public String getFullTypeName() {
			return "info.esblurock.reaction.data.chemical.thermo.SetOfNASAPolynomialData";
		}
		
	},
	TransportConstants {

		@Override
		public void process(DescriptionDataData description, String key, String filename, boolean process) {
			if(process)
				Window.alert("Process TransportConstants:    Key=" + key + "    filename='" + filename + "'");
			else
				Window.alert("Test    TransportConstants:    Key=" + key + "    filename='" + filename + "'");
		}

		@Override
		public String getFullTypeName() {
			return null;
		}
		
	};
	
	
	public abstract void process(DescriptionDataData description, String key, String filename, boolean process);
	public abstract String getFullTypeName();

}
