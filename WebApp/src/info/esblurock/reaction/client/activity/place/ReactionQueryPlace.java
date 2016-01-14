package info.esblurock.reaction.client.activity.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ReactionQueryPlace  extends Place {

	private String helloName;
	
	public ReactionQueryPlace(String token)
	{
		this.helloName = token;
	}

	public String getHelloName()
	{
		return helloName;
	}

	public static class Tokenizer implements PlaceTokenizer<ReactionQueryPlace>
	{

		@Override
		public String getToken(ReactionQueryPlace place)
		{
			return place.getHelloName();
		}

		@Override
		public ReactionQueryPlace getPlace(String token)  {
			return new ReactionQueryPlace(token);
		}

	}
	

	
}
