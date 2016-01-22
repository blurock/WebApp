package info.esblurock.react.mechanisms.chemkin;

import java.util.ArrayList;
import java.util.Iterator;

public class SetOfReactionForwardReverseTypes extends ArrayList<ReactionForwardReverseType> {
	String reactants;
	String products;
	

	private static final boolean ReactionForwardReverseType = false;

	public SetOfReactionForwardReverseTypes() {
		ReactionForwardReverseType t1 = new ReactionForwardReverseType("<=>",false,false,true);
		ReactionForwardReverseType t2 = new ReactionForwardReverseType("=>",true,false,false);
		ReactionForwardReverseType t3 = new ReactionForwardReverseType("<=",false,true,false);
		ReactionForwardReverseType t4 = new ReactionForwardReverseType("=",false,false,true);
		
		int index = 0;
		this.add(index++, t1);
		this.add(index++, t2);
		this.add(index++, t3);
		this.add(index++, t4);
		
	}
	
	public  ReactionForwardReverseType findReactionType(String reaction) {
		ReactionForwardReverseType type = null;
		Iterator<ReactionForwardReverseType> iter = this.iterator();
		while(iter.hasNext() && type == null) {
			ReactionForwardReverseType rtype = iter.next();
			int pos = reaction.indexOf(rtype.getEquivString());
			if(pos != -1) {
				reactants = reaction.substring(0,pos);
				products = reaction.substring(pos+rtype.getEquivString().length());
				type = rtype;
			}
		}
		return type;
	}

	public String getReactants() {
		return reactants;
	}

	public String getProducts() {
		return products;
	}
	
}
