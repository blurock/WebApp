package info.esblurock.reaction.client;

import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

import gwt.material.design.client.ui.MaterialToast;
import info.esblurock.reaction.client.activity.ClientFactory;
import info.esblurock.reaction.client.activity.place.ReactionTopPlace;
import info.esblurock.reaction.client.mvp.AppActivityMapper;
import info.esblurock.reaction.client.mvp.AppPlaceHistoryMapper;
import info.esblurock.reaction.client.ui.ReactionQueryImpl;
import info.esblurock.reaction.client.ui.ReactionTopImpl;


public class ReactionTop implements EntryPoint {

	private Place defaultPlace = new ReactionTopPlace("Top");
	private SimplePanel appWidget = new SimplePanel();

	@Override
	public void onModuleLoad() {
	
		ClientFactory clientFactory = GWT.create(ClientFactory.class);
		EventBus eventBus = clientFactory.getEventBus();
		PlaceController placeController = clientFactory.getPlaceController();
		
		// Start ActivityManager for the main widget with our ActivityMapper
		ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
		ActivityManager activityManager = new ActivityManager(activityMapper, eventBus);
		activityManager.setDisplay(appWidget);

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
		PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
		historyHandler.register(placeController, eventBus, defaultPlace);
		
		
		RootPanel.get().add(appWidget);
		//ReactionQueryImpl query = new ReactionQueryImpl();
		//RootPanel.get().add(query);
		
		historyHandler.handleCurrentHistory();
	}

}
