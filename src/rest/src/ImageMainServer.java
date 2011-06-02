package rest.src;

import jadehospital.LocalConfig;

import java.io.FileWriter;
import java.util.HashMap;

import org.restlet.Component;
import org.restlet.data.Protocol;



public class ImageMainServer {
	
	private static String BASE_URL = "http://localhost:8182/";
	
	public static String baseURL() { return BASE_URL; }
	public static void main(String[] args) {
		startServer();
//		System.getProperties().put("org.restlet.engine.loggerFacadeClass", "org.restlet.ext.slf4j.Slf4jLoggerFacade");
		Client cl= new Client();
		//HashMap<Integer,HashMap<String,String> > a = cl.getPatient("http://localhost:8182/patients/john_smith");
		//cl.addPatient("http://localhost:8182/patients/","jordan","lol","4");
		//cl.changePatient("http://localhost:8182/patients/", "jordan", "", "","oui");
		//cl.deletePatient("http://localhost:8182/patients/", "jordan");
		//cl.addPatient("http://localhost:8182/patients/","pierre","hihi","4");
		//cl.getPatient("http://localhost:8182/patients/");
		//cl.changeInformation("http://localhost:8182/annuaire/siva");
		//HashMap<Integer,HashMap<String,String> > a = cl.getPatient("http://localhost:8182/patients/");
		cl.addMedecin("http://localhost:8182/annuaire/medecin","jordan","ortho");
		cl.addInfirmier("http://localhost:8182/annuaire/infirmier", "siva");
		
		//cl.getMedecin("http://localhost:8182/annuaire/zohair");
		System.out.println(cl.getPersonnel("http://localhost:8182/annuaire/medecins"));
		System.out.println(cl.getPersonnel("http://localhost:8182/annuaire/infirmier"));

	}
	
	public static void startServer() {
		try { // Create a new Component.
			Component component = new Component();
			// Add a new HTTP server listening on port 8182.
			component.getServers().add(Protocol.HTTP, 8182);
			component.getDefaultHost().attach(new ServerApplication());
			component.start(); // Start the component.
			
			FileWriter fw = new FileWriter(LocalConfig.REST_DIRECTORY + "annuaire.xml");
	        fw.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<annuaire>\n</annuaire>");
	        fw.close();
	        FileWriter fw2 = new FileWriter(LocalConfig.REST_DIRECTORY + "patients.xml");
	        fw2.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<patients>\n</patients>");
	        fw2.close();
		} catch(Exception ex) {
			System.out.println(ex.getStackTrace());
		}
	}
}
