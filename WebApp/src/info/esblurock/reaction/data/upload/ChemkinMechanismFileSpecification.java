package info.esblurock.reaction.data.upload;

import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.PersistenceCapable;

@PersistenceCapable
@Inheritance(customStrategy = "complete-table")
public class ChemkinMechanismFileSpecification extends FileSourceSpecification {
	public ChemkinMechanismFileSpecification() {
		super();
	}

}
