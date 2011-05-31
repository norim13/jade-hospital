package rest.src;

import jadehospital.LocalConfig;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.restlet.data.Form;
import org.restlet.data.MediaType;
import org.restlet.data.Status;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.Get;
import org.restlet.resource.Post;
import org.restlet.resource.Put;
import org.restlet.resource.ServerResource;
import org.xml.sax.SAXException;

public class PatientId extends ServerResource {
	
	//private HashMap<String,String> docs;
	private static String Id="";
	private static ParserXML xml;
	private String Nom="",Symptome="",Etat="";
	
	@Get
	public void retrieve() throws FileNotFoundException, SAXException, IOException, JDOMException {
		
		//Recupere les attibruts de la requete
		Map<String,Object> attributes =getRequest().getAttributes();
		
		//Recupere l'ID du patient dont les informations sont demand�es
		Id = (String) attributes.get("id");

		//Recupere le Header de la requete et verifie qu'elle type d'element
		//est voulu par le client
		Form form = (Form) attributes.get("org.restlet.http.headers");
		String s = form.getValues("Accept");
		String reponse = null;
		if(s.equals("text/xml")){
			//Parse le fichier XML
			xml = new ParserXML(LocalConfig.REST_DIRECTORY + "test.xml");
	
		    //retourne les elements concernant l'id fournit dans l'URI
		    if(Id == null){
		    	Element racine = xml.getRacine();
				
		 	   //On cr�e une List contenant tous les noeuds "patient" de l'Element racine
		 	   List listEtudiants = racine.getChildren("patient");
		 	  
			    	  XMLOutputter outputter = new XMLOutputter(); 
			    	   reponse = outputter.outputString(listEtudiants);
		    }else{
		    	reponse = getElementForId();
		    }
		   
			if(!reponse.equals("")){
		    	getResponse().setEntity(new StringRepresentation(reponse,MediaType.APPLICATION_ALL_XML));
		    	getResponse().setStatus(Status.SUCCESS_OK);
		    }
	    }
	}
	
	//Ajouter cette m�thodes � la classe JDOM2
	static String getElementForId() throws IOException
	{
		org.jdom.Document document = xml.getDocument();
		Element racine = xml.getRacine();
		
	   //On cr�e une List contenant tous les noeuds "patient" de l'Element racine
	   List listEtudiants = racine.getChildren("patient");

	   //On cr�e un Iterator sur notre liste pour parcourir tous les patients
	   Iterator i = listEtudiants.iterator();
	   while(i.hasNext())
	   {
	      //On recr�e l'Element courant � chaque tour de boucle afin de
	      //pouvoir utiliser les m�thodes propres aux Element comme :
	      //selectionner un noeud fils, modifier du texte, etc...
	      Element courant = (Element)i.next();
	      if(courant.getChild("nom").getText().equals(Id) ){
	    	  //On affiche le nom de l'element courant
	    	  XMLOutputter outputter = new XMLOutputter(); 
	  	      //outputter.output(document, System.out);
	    	  //String a = courant.getChild("id").getText() + courant.getChild("nom").getText()+ courant.getChild("symptome").getText();
	 
	    	  return outputter.outputString(courant);   	  
	      }
	   }
	   return "";
	}
	  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	 //																	PUT 													     //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Put 
	public void insert() throws JDOMException, IOException {
		Map<String,Object> attributes =getRequest().getAttributes();
		Form form = (Form) attributes.get("org.restlet.http.headers");
		Nom = form.getValues("nom");
		Symptome=form.getValues("symptome");
		Etat=form.getValues("etat");
		
		//parsing du fichier XML concentant l'information sur les patients
		xml = new ParserXML(LocalConfig.REST_DIRECTORY + "test.xml");

		//retourne les elements concernant l'id fournit dans l'URI
		boolean reponse = ajoutpersonne();
	    /*if(!reponse.equals("")){
	    	getResponse().setEntity(new StringRepresentation(reponse,MediaType.APPLICATION_ALL_XML));
	    	getResponse().setStatus(Status.SUCCESS_OK);
	    }*/
	}

	private boolean ajoutpersonne() throws FileNotFoundException, IOException {
		org.jdom.Document document = xml.getDocument();
		Element child = new Element("patient");
		
		Element nom = new Element("nom");
		nom.addContent(Nom);
		
		Element symptome = new Element("symptome");
		symptome.addContent(Symptome);
		
		Element etat = new Element("etat");
		etat.addContent(Etat);
		
		child.addContent(nom);
		child.addContent(symptome);
		child.addContent(etat);
		Element racine = xml.getRacine();
		racine.addContent(child);
		
		XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        sortie.output(document, new FileOutputStream(LocalConfig.REST_DIRECTORY + "test.xml"));
		return true;
	   }
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	@Post
	public void update(Representation entity) throws JDOMException, IOException {
		Map<String,Object> attributes =getRequest().getAttributes();
		Form form = (Form) attributes.get("org.restlet.http.headers");
		Nom = form.getValues("nom");
		Symptome=form.getValues("symptome");
		Etat=form.getValues("etat");
		
		xml = new ParserXML(LocalConfig.REST_DIRECTORY + "test.xml");
		if(xml.getErreur().equals("notfound")){
			getResponse().setStatus(Status.SERVER_ERROR_NOT_IMPLEMENTED);
		}
		//retourne les elements concernant l'id fournit dans l'URI
		String reponse = changeInfos();
	    if(!reponse.equals("")){
	    	getResponse().setEntity(new StringRepresentation(reponse,MediaType.APPLICATION_ALL_XML));
	    	getResponse().setStatus(Status.SUCCESS_OK);
	    }
	}
	
	
	private String changeInfos() throws FileNotFoundException, IOException {
		org.jdom.Document document = xml.getDocument();
		Element racine = xml.getRacine();
		
	   //On cr�e une List contenant tous les noeuds "medecin" de l'Element racine
	   List listEtudiants = racine.getChildren("patient");

	   //On cr�e un Iterator sur notre liste pour parcourir tous les patients
	   Iterator i = listEtudiants.iterator();
	   while(i.hasNext()){
		 //On recr�e l'Element courant � chaque tour de boucle afin de
		      //pouvoir utiliser les m�thodes propres aux Element comme :
		      //selectionner un noeud fils, modifier du texte, etc...
		      Element courant = (Element)i.next();
		      if(courant.getChild("nom").getText().equals(Nom) ){
		    	  //On affiche le nom de l'element courant
		    	  //String a = courant.getChildText("nom")+ " " + courant.getChildText("portable")+ " " + courant.getChildText("bureau")+" "+courant.getChildText("specialite")+" "+ courant.getChildText("arrivee")+ " " + courant.getChildText("present")+ " " + courant.getChildText("garde");
		    	  if(!Nom.equals(""))
		    		  courant.getChild("nom").setText(Nom);
		    	  if(!Symptome.equals(""))
		    		  courant.getChild("symptome").setText(Symptome);
		    	  if(!Etat.equals(""))
		    		  courant.getChild("etat").setText(Etat);
		    	  
		    	  //Creation du nouveau XML qui va remplacer l'ancien
		    	  XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		          sortie.output(document, new FileOutputStream(LocalConfig.REST_DIRECTORY + "test.xml"));
		          return sortie.toString();
		      }
	   }
	   return "";
	}
	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	private void error() {
		getResponse().setEntity(new StringRepresentation("{\"status\" : \"Parameter Error\"}",MediaType.TEXT_PLAIN));
		getResponse().setStatus(Status.CLIENT_ERROR_BAD_REQUEST);
	}
}
