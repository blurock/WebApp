package info.esblurock.reaction.data.chemical.transport;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import com.google.appengine.datanucleus.annotations.Unindexed;

import info.esblurock.reaction.data.DatabaseObject;

@PersistenceCapable
public class SpeciesTransportProperty extends DatabaseObject {
	
	private static final long serialVersionUID = 1L;

	@Persistent
	String mechanismKeyword;
	
   	@Persistent
	String speciesName;
   	
	/* An index indicating whether the molecule has a monatomic, 
	 * linear or nonlinear geometrical configuration.
	 *  If the index is 0, the molecule is a single atom. 
	 *  If the index is 1 the molecule is linear, and if it is 2, the molecule is nonlinear.
	*/
   	@Persistent
   	@Unindexed
	String geometricIndex;
   	
	/* The Lennard-Jones potential well depth in Kelvins.
	 */
   	@Persistent
   	@Unindexed
	String potentialWellDepth;
   	
	/*  The Lennard-Jones collision diameter σ in Angstroms.
	 */
   	@Persistent
   	@Unindexed
	String collisionDiameter;
   	
	/* The dipole moment μ in Debye. Note: a Debye is 10-18cm3/2erg1/2. 
	 * (Net Charge* Distance -- = 0 for linear molecule)
	 */
   	@Persistent
   	@Unindexed
	String dipole;

	/* The polarizability α in cubic Angstroms. (Tendency for charge distribution to be 
	 * distorted by external electric field)
	 */
   	@Persistent
   	@Unindexed
	String polarizability;
   	
	/* The rotational relaxation collision number at 298K. 
	 * (Number of collisions to reach rotational equilibrium)
	 */
   	@Persistent
   	@Unindexed
	String collisionNumber;
   	
   	
   	public SpeciesTransportProperty() {
   		
   	}
	public SpeciesTransportProperty(String mechanismKeyword, String speciesName, String geometricIndex,
			String potentialWellDepth, String collisionDiameter, String dipole, String polarizability,
			String collisionNumber) {
		super();
		this.mechanismKeyword = mechanismKeyword;
		this.speciesName = speciesName;
		this.geometricIndex = geometricIndex;
		this.potentialWellDepth = potentialWellDepth;
		this.collisionDiameter = collisionDiameter;
		this.dipole = dipole;
		this.polarizability = polarizability;
		this.collisionNumber = collisionNumber;
	}

	public String getMechanismKeyword() {
		return mechanismKeyword;
	}

	public String getSpeciesName() {
		return speciesName;
	}

	public String getGeometricIndex() {
		return geometricIndex;
	}

	public String getPotentialWellDepth() {
		return potentialWellDepth;
	}

	public String getCollisionDiameter() {
		return collisionDiameter;
	}

	public String getDipole() {
		return dipole;
	}

	public String getPolarizability() {
		return polarizability;
	}

	public String getCollisionNumber() {
		return collisionNumber;
	}
	
   	
}
