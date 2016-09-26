package info.esblurock.reaction.login;

import java.io.IOException;

import info.esblurock.reaction.server.authorization.SetOfAuthorizationLevels;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class Authorization extends GWTTestCase {

	@Test
	public void test() throws IOException {
		SetOfAuthorizationLevels set = new SetOfAuthorizationLevels();
		/*
		File file = new File(classLoader.getResource(resource).getFile());
		try {
			Scanner scan = new Scanner(file);
			StringBuilder build = new StringBuilder();
			while(scan.hasNextLine()) {
				String next = scan.nextLine();
				build.append(next);
				build.append("\n");
			}
			set.initAuthorization(build.toString());
			String userlevel = "Administrator";
			String task = "Login";
			boolean authorize = set.authorize(userlevel, task);
			if(authorize) {
				System.out.println("Authorized");
			} else {
				System.out.println("Not Authorized");
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//set.initAuthorization();
		*/
			//File file = new File(classLoader.getResource(resource).getFile());
			//URL url = getClass().getClassLoader().getResource(resource);
			//String content = Resources.toString(url, Charsets.UTF_8);
			set.initAuthorization();
			String userlevel = "SuperUser";
			String task = "Login";
			boolean authorize = set.authorize(userlevel, task);
			if(authorize) {
				System.out.println("Authorized");
			} else {
				System.out.println("Not Authorized");
			}
		
	}

	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return null;
	}

}
