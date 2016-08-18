package info.esblurock.reaction.client.activity.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class ReactionLoginValidationPlace   extends Place {
	private String helloName;
	
	public ReactionLoginValidationPlace(String token)
	{
		this.helloName = token;
	}

	public String getHelloName()
	{
		return helloName;
	}

	public static class Tokenizer implements PlaceTokenizer<ReactionLoginValidationPlace>
	{

		@Override
		public String getToken(ReactionLoginValidationPlace place)
		{
			return place.getHelloName();
		}

		@Override
		public ReactionLoginValidationPlace getPlace(String token)  {
			return new ReactionLoginValidationPlace(token);
		}

	}
	


}
