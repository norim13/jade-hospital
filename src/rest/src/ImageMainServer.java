package rest.src;

import java.util.HashMap;

import org.restlet.Component;
import org.restlet.data.Protocol;



public class ImageMainServer {
	
	private static String BASE_URL = "http://localhost:8182/";
	
	public static String baseURL() { return BASE_URL; }
	
	public static void main(String[] args) {
		startServer();
		Client cl= new Client();
		HashMap<Integer,HashMap<String,String> > a = cl.getPatient("http://localhost:8182/patients/john_smith");
		//cl.addPatient("http://localhost:8182/patients/","jordan","lol","4");
		//cl.changePatient("http://localhost:8182/patients/", "jordan", "mdr", "5");
		//cl.changeInformation("http://localhost:8182/annuaire/siva");
		//cl.addPersonne("http://localhost:8182/annuaire/");
	}
	
	public static void startServer() {
		try { // Create a new Component.
			Component component = new Component();
			// Add a new HTTP server listening on port 8182.
			component.getServers().add(Protocol.HTTP, 8182);
			component.getDefaultHost().attach(new ServerApplication());
			component.start(); // Start the component.
		} catch(Exception ex) {}
	}
}
