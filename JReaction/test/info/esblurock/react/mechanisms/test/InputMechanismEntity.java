package info.esblurock.react.mechanisms.test;

import java.io.IOException;
import java.text.ParseException;

import javax.jdo.annotations.PersistenceCapable;

import info.esblurock.react.mechanisms.chemkin.entity.MechanismEntityFactory;

import org.junit.Test;



@PersistenceCapable
public class InputMechanismEntity {

	@Test
	public void test() {
		
		

		MechanismEntityFactory factory = new MechanismEntityFactory();
		
		//String text1 = "/Users/edwardblurock/Box Sync/ScientificNotebook/Project/";
		//String text2 = "JReaction/src/info/esblurock/react/resources/mechanisms/";
		String text0 ="/Users/edwardblurock/Desktop/";
		String text3 = "LLNL-NHeptane-2011v3.1";
		String line = "FILE " + text0 + text3;
		try {
			//DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			System.out.println("Step 1.1");
			factory.readFromSourceSpecification(line);
			/*
			Entity entity = factory.createEntity();
			datastore.put(entity);
			
			MechanismEntity entity = factory.createMechanismEntity();
			System.out.println("Step 1.2");
			Key<MechanismEntity> key = ofy().save().entity(entity).now();
			System.out.println("Step 1");
			String id = "LLNL-nHeptane-2011v3.1";
			//MechanismEntity result = ofy().load().type(MechanismEntity.class).id(id).now();
			System.out.println("Step 2");
			QueryKeys<MechanismEntity> keys = ofy().load().type(MechanismEntity.class).keys();
			System.out.println("Step 3");
			
			QueryResultIterator<Key<MechanismEntity>> iter = keys.iterator();
			System.out.println("Step 4");
			
			Key<MechanismEntity> load = keys.first().now();
			System.out.println("Step 5");
			System.out.println(load.getName());
			*/
			
			/*
			 * Query q = new Query("MechanismData");
			
			PreparedQuery pq = datastore.prepare(q);
			for (MechanismEntity result : results.) {
				Map<String,Object> props = result.getProperties();
				Set<String> keys = props.keySet();
				for(String key : keys) {
					System.out.println(key + "\t\t :" + props.get(key));
				}
			 */
			
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}

}
