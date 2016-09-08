package info.esblurock.reaction.client.mvp;



import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

import info.esblurock.reaction.client.activity.place.ReactionFirstPlace;
import info.esblurock.reaction.client.activity.place.ReactionQueryPlace;
import info.esblurock.reaction.client.activity.place.ReactionTopPlace;
import info.esblurock.reaction.client.activity.place.ReactionLoginValidationPlace;
import info.esblurock.reaction.client.activity.place.ReactionInformationPlace;
/**
 * PlaceHistoryMapper interface is used to attach all places which the
 * PlaceHistoryHandler should be aware of. This is done via the @WithTokenizers
 * annotation or by extending PlaceHistoryMapperWithFactory and creating a
 * separate TokenizerFactory.
 */
//@WithTokenizers( { HelloPlace.Tokenizer.class, GoodbyePlace.Tokenizer.class })
@WithTokenizers( { ReactionTopPlace.Tokenizer.class, 
	ReactionFirstPlace.Tokenizer.class,
	ReactionQueryPlace.Tokenizer.class,
	ReactionLoginValidationPlace.Tokenizer.class,
	ReactionInformationPlace.Tokenizer.class}
)

public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {
}
