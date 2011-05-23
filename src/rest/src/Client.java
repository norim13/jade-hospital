package rest.src;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;


public class Client {
	
	//Client(){};
	
	public static HashMap<Integer, HashMap<String, String>> getPatient(String url) {
		URI uri;
		String s = null;
	     HashMap<Integer, HashMap<String,String> > resultat = new HashMap();
		try {
			uri = new URI(url);
			HttpGet httpget = new HttpGet(uri);
			httpget.addHeader("Accept", "text/xml");
			HttpClient client = new DefaultHttpClient();
			HttpResponse res = client.execute(httpget);
			HttpEntity entity = res.getEntity();
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				s="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<patients>\n";
				s += EntityUtils.toString(entity);
				s+= "\n</patients>";

				 SAXBuilder build = new SAXBuilder();
			     Document document = build.build(new ByteArrayInputStream(s.getBytes()));
			   //On cr�e une List contenant tous les noeuds "patient" de l'Element racine
				   List listEtudiants = document.getRootElement().getChildren("patient");

				   //On cr�e un Iterator sur notre liste pour parcourir tous les patients
				   Iterator i = listEtudiants.iterator();
				   int j = 0;
				   while(i.hasNext())
				   {
					   Element courant = (Element)i.next();
					   HashMap<String,String> hashTemp = new HashMap<String,String>();
					   hashTemp.put("nom",courant.getChild("nom").getText());
					   hashTemp.put("symptome",courant.getChild("symptome").getText());
					   hashTemp.put("etat",courant.getChild("etat").getText());
					   resultat.put(j,hashTemp);
					   j++;
				   }
			}else{
				System.out.println(res.getStatusLine().getStatusCode());
			}
		} catch (Exception e) {}
	return resultat;
	}
	

	public static String addPatient(String url, String nom, String symptome,String etat){
		URI uri;
		String s = null;
		try {
			uri = new URI(url);
			HttpPut httpput = new HttpPut(uri);
			httpput.addHeader("Accept", "text/xml");
			httpput.addHeader("nom", nom);
			httpput.addHeader("symptome", symptome);
			httpput.addHeader("etat",etat);	
			
			HttpClient client = new DefaultHttpClient();
			HttpResponse res = client.execute(httpput);
			HttpEntity entity = res.getEntity();
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				s="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
				s += EntityUtils.toString(entity);
				System.out.println(s);
			}
		} catch (Exception e) {}
	return s;
	}
	
	public static boolean changePatient(String url,String nom,String symptome,String etat) {
		URI uri;
		String s = null;
		try {
			uri = new URI(url);
			HttpPost httppost = new HttpPost(uri);
			httppost.addHeader("Accept", "text/xml");
			httppost.addHeader("nom", nom);
			httppost.addHeader("symptome", symptome);
			httppost.addHeader("etat", etat);

			HttpClient client = new DefaultHttpClient();
			HttpResponse res = client.execute(httppost);
			HttpEntity entity = res.getEntity();
			
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				return true;
			}
		} catch (Exception e) {}
	return false;	
	}
////////////////////////////////////////////////////////////////////////////////////////////////////////:::::

	
	public static String changeInformation(String url) {
		URI uri;
		String s = null;
		try {
			uri = new URI(url);
			HttpPut httpput = new HttpPut(uri);
			httpput.addHeader("Accept", "text/xml");
			httpput.addHeader("nom", "tanguy");
			httpput.addHeader("portable", "");
			httpput.addHeader("bureau", "");
			httpput.addHeader("specialite", "ortho");
			httpput.addHeader("arrivee", "");
			httpput.addHeader("present", "");
			httpput.addHeader("garde", "");		
			
			HttpClient client = new DefaultHttpClient();
			HttpResponse res = client.execute(httpput);
			HttpEntity entity = res.getEntity();
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				s="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
				s += EntityUtils.toString(entity);
				System.out.println(s);
			}
		} catch (Exception e) {}
	return s;
	}

	
	public static String addPersonne(String url) {
		URI uri;
		String s = null;
		try {
			uri = new URI(url);
			HttpPost httppost = new HttpPost(uri);
			httppost.addHeader("Accept", "text/xml");
			httppost.addHeader("nom", "jordan");
			httppost.addHeader("portable", "0698767876");
			httppost.addHeader("bureau", "3456");
			httppost.addHeader("specialite", "orthopediste");
			httppost.addHeader("present", "oui");
			httppost.addHeader("arrivee", "12/05/2011_13h30");
			httppost.addHeader("garde", "non");
			HttpClient client = new DefaultHttpClient();
			HttpResponse res = client.execute(httppost);
			HttpEntity entity = res.getEntity();
			if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				s="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n";
				s += EntityUtils.toString(entity);
				System.out.println(s);
			}
		} catch (Exception e) {}
	return s;		
	}
	
}
