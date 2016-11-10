package info.esblurock.reaction.client.panel.inputs.resource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface PanelInputResources extends ClientBundle {
	public static final PanelInputResources INSTANCE =  GWT.create(PanelInputResources.class);
	
	@Source("thermo.txt")
	  public TextResource exampleNASAPolynomialSet();
	
	@Source("mechanism.txt")
	  public TextResource exampleChemkinMechanismReactions();
	
	@Source("transport.txt")
	  public TextResource exampleTransportSet();
	
	@Source("sdfExample.txt")
	  public TextResource exampleSDFMoleculeSet();
	
	@Source("sdfSubstructureExample.txt")
	  public TextResource exampleSDFSubstructureSet();
	
	@Source("nancyMoleculesAndThermoExample.txt")
		public TextResource exampleThergasMoleculeSet();
	
	@Source("reactmolcorrs.txt")
		public TextResource exampleReactMoleculeCorrespondenceSet();
}
