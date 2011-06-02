package rest.src;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;
import org.slf4j.bridge.SLF4JBridgeHandler;


public class ServerApplication extends Application {

//	public ServerApplication()
//	{
//		super();
//		System.out.println("niveau log : " + getLogger().getLevel());
//		this.getLogger().setLevel(Level.OFF);
//		System.out.println("niveau log : " + getLogger().getLevel());
//	}
	
	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/patients/{id}",PatientId.class);
		router.attach("/patients/",PatientId.class);
		router.attach("/annuaire/{nom}",Annuaire.class);
		router.attach("/annuaire/",Annuaire.class);
		return router;
	}
	
//	static {
//	    // Install logging bridge (JUL -> LOG4J)
//	    SLF4JBridgeHandler.install();
//
//	    // Disable annoying console logging of requests..
//	    Logger logger = Logger.getLogger("org.restlet");
//	    for (Handler handler : logger.getParent().getHandlers()) {
//	        // Find the console handler
//	        if (handler.getClass().equals(java.util.logging.ConsoleHandler.class)) {
//	            // set level to SEVERE. We could disable it completely with 
//	            // a custom filter but this is good enough.
//	            handler.setLevel(Level.SEVERE);
//	        }
//	    }
//	}

}
