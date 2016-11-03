package info.esblurock.reaction.client.panel.repository.data;

import java.util.ArrayList;

import com.google.gwt.user.client.ui.Widget;

import info.esblurock.reaction.client.panel.repository.RepositoryBaseItem;
import info.esblurock.reaction.client.panel.repository.RepositoryFileItemTextField;
import info.esblurock.reaction.data.upload.types.ChemkinMechanismFileUpload;
import info.esblurock.reaction.data.upload.types.NASAPolynomialFileUpload;
import info.esblurock.reaction.data.upload.types.TransportFileUpload;

public enum RepositoryDataVisualization {
	Chemkin {

		@Override
		public ArrayList<Widget> getDataSetVisualizationItems(String datasetkey) {
			ArrayList<Widget> items = new ArrayList<Widget>();
			RepositoryFileItemTextField item1 
				= new RepositoryFileItemTextField(datasetkey, ChemkinMechanismFileUpload.class.getName());
			RepositoryFileItemTextField item2 
				= new RepositoryFileItemTextField(datasetkey, NASAPolynomialFileUpload.class.getName());
			RepositoryFileItemTextField item3 
				= new RepositoryFileItemTextField(datasetkey, TransportFileUpload.class.getName());
			items.add(item1);
			items.add(item2);
			items.add(item3);
			return items;
		}
		
	};
	
	public abstract ArrayList<Widget> getDataSetVisualizationItems(String datasetkey);

}
