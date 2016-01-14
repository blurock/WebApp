package info.esblurock.reaction.client.activity.place;


import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

//public class HelloPlace extends ActivityPlace<HelloActivity>
public class ReactionTopPlace extends Place
{
	private String helloName;
	
	public ReactionTopPlace(String token)
	{
		this.helloName = "Top-" + token;
	}

	public String getHelloName()
	{
		return helloName;
	}

	public static class Tokenizer implements PlaceTokenizer<ReactionTopPlace>
	{

		@Override
		public String getToken(ReactionTopPlace place) {
			return place.getHelloName();
		}

		@Override
		public ReactionTopPlace getPlace(String token) {
			return new ReactionTopPlace(token);
		}

	}
	
//	@Override
//	protected Place getPlace(String token)
//	{
//		return new ReactionTopPlace(token);
//	}
//
//	@Override
//	protected Activity getActivity()
//	{
//		return new HelloActivity("David");
//	}
}
