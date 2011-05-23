package rest.src;

import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;


public class ServerApplication extends Application {

	@Override
	public Restlet createInboundRoot() {
		Router router = new Router(getContext());
		router.attach("/patients/{id}",PatientId.class);
		router.attach("/patients/",PatientId.class);
		router.attach("/annuaire/{nom}",Annuaire.class);
		router.attach("/annuaire/",Annuaire.class);
		return router;
	}
	
}
