package rest.src;

import jadehospital.LocalConfig;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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


public class Annuaire extends ServerResource {
	
	private ParserXML xml;
	private String nomDemande;
	private String newNom="",newPortable="",newBureau="",newSpecialite="",newArrivee="",newPresent="",newGarde="",newTraite="";
	
	/****************************************************************************************************************************/
	/*                                                           GET                                                            */
	/****************************************************************************************************************************/  
	@Get
	public void retrieve() throws JDOMException, IOException{
		Map<String,Object> attributes =getRequest().getAttributes();
		nomDemande = (String) attributes.get("nom");
		
		xml = new ParserXML(LocalConfig.REST_DIRECTORY + "annuaire.xml");
		
		
		
		//retourne les elements concernant l'id fournit dans l'URI
		String reponse = "";
		if(nomDemande.equals("medecins")){
			List listMedecins = xml.getRacine().getChildren("medecin");
			XMLOutputter outputter = new XMLOutputter(); 
	    	reponse = outputter.outputString(listMedecins);
		}else{
			if(nomDemande.equals("infirmier")){
				List listMedecins = xml.getRacine().getChildren("infirmier");
				XMLOutputter outputter = new XMLOutputter(); 
		    	reponse = outputter.outputString(listMedecins);
			}else{
				reponse = getInfos();
			}
		}
		
		if(!reponse.equals("")){
	    	getResponse().setEntity(new StringRepresentation(reponse,MediaType.APPLICATION_ALL_XML));
	    	getResponse().setStatus(Status.SUCCESS_OK);
	    }else{
	    	getResponse().setStatus(Status.CLIENT_ERROR_NOT_FOUND);
	    }
	}
	
	private String getInfos() {
		org.jdom.Document document = xml.getDocument();
		Element racine = xml.getRacine();
		
	   //On crée une List contenant tous les noeuds "patient" de l'Element racine
	   List listMedecins = racine.getChildren("medecin");
	   List listInfirmier = racine.getChildren("infirmier");

	   //On crée un Iterator sur notre liste pour parcourir tous les patients
	   Iterator i = listMedecins.iterator();
	   while(i.hasNext()){
		   //On recrée l'Element courant à chaque tour de boucle afin de
		      //pouvoir utiliser les méthodes propres aux Element comme :
		      //selectionner un noeud fils, modifier du texte, etc...
		      Element courant = (Element)i.next();
		      if(courant.getChild("nom").getText().equals(nomDemande) ){
		    	  //On affiche le nom de l'element courant
		    	  //String a = courant.getChildText("nom")+ " " + courant.getChildText("portable")+ " " + courant.getChildText("bureau")+" "+courant.getChildText("specialite")+" "+ courant.getChildText("arrivee")+ " " + courant.getChildText("present")+ " " + courant.getChildText("garde");
		    	  XMLOutputter outputter = new XMLOutputter();
		    	  return outputter.outputString(courant);  
		      }
	   }
	   
	   return null;
	}
	
	/****************************************************************************************************************************/
	/****************************************************************************************************************************/
	/****************************************************************************************************************************/
	/*                                                           PUT                                                            */
	/****************************************************************************************************************************/  
	
	@Put
	public void insert() throws JDOMException, IOException{
		Map<String,Object> attributes =getRequest().getAttributes();
		nomDemande = (String) attributes.get("nom");
		Form form = (Form) attributes.get("org.restlet.http.headers");
		newNom = form.getValues("nom");
		newPortable=form.getValues("portable");
		newBureau=form.getValues("bureau");
		newSpecialite=form.getValues("specialite");
		newArrivee=form.getValues("arrivee");
		newPresent=form.getValues("present");
		newGarde=form.getValues("garde");
		
		xml = new ParserXML(LocalConfig.REST_DIRECTORY + "annuaire.xml");
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
		
	   //On crée une List contenant tous les noeuds "medecin" de l'Element racine
	   List listEtudiants = racine.getChildren("medecin");

	   //On crée un Iterator sur notre liste pour parcourir tous les patients
	   Iterator i = listEtudiants.iterator();
	   while(i.hasNext()){
		 //On recrée l'Element courant à chaque tour de boucle afin de
		      //pouvoir utiliser les méthodes propres aux Element comme :
		      //selectionner un noeud fils, modifier du texte, etc...
		      Element courant = (Element)i.next();
		      if(courant.getChild("nom").getText().equals(nomDemande) ){
		    	  //On affiche le nom de l'element courant
		    	  //String a = courant.getChildText("nom")+ " " + courant.getChildText("portable")+ " " + courant.getChildText("bureau")+" "+courant.getChildText("specialite")+" "+ courant.getChildText("arrivee")+ " " + courant.getChildText("present")+ " " + courant.getChildText("garde");
		    	  System.out.println("ooooooooooo");
		    	  if(!newNom.equals(""))
		    		  courant.getChild("nom").setText(newNom);
		    	  if(!newPortable.equals(""))
		    		  courant.getChild("portable").setText(newPortable);
		    	  if(!newBureau.equals(""))
		    		  courant.getChild("bureau").setText(newBureau);
		    	  if(!newSpecialite.equals(""))
		    		  courant.getChild("specialite").setText(newSpecialite);
		    	  if(!newArrivee.equals(""))
		    		  courant.getChild("arrivee").setText(newArrivee);
		    	  if(!newPresent.equals(""))
		    		  courant.getChild("present").setText(newPresent);
		    	  if(!newGarde.equals(""))
		    		  courant.getChild("garde").setText(newGarde);
		    	  XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
		          sortie.output(document, new FileOutputStream(LocalConfig.REST_DIRECTORY + "annuaire.xml"));
		          return sortie.toString();
		      }
	   }
	   return "";
	}
	
	/****************************************************************************************************************************/
	/****************************************************************************************************************************/
	/****************************************************************************************************************************/
	/*                                                           POST                                                           */
	/****************************************************************************************************************************/  
	 
	@Post
	public void update(Representation entity) throws JDOMException, IOException {
		Map<String,Object> attributes =getRequest().getAttributes();
		nomDemande = (String) attributes.get("nom");
		Form form = (Form) attributes.get("org.restlet.http.headers");
		newNom = form.getValues("nom");
		newPortable=form.getValues("portable");
		newBureau=form.getValues("bureau");
		newSpecialite=form.getValues("specialite");
		newArrivee=form.getValues("arrivee");
		newPresent=form.getValues("present");
		newGarde=form.getValues("garde");
		
		xml = new ParserXML(LocalConfig.REST_DIRECTORY + "annuaire.xml");

		//retourne les elements concernant l'id fournit dans l'URI
		boolean reponse = ajoutpersonne();
	    /*if(!reponse.equals("")){
	    	getResponse().setEntity(new StringRepresentation(reponse,MediaType.APPLICATION_ALL_XML));
	    	getResponse().setStatus(Status.SUCCESS_OK);
	    }*/
	}

	private boolean ajoutpersonne() throws FileNotFoundException, IOException {
		org.jdom.Document document = xml.getDocument();
		Element child = new Element("medecin");
		
		Element nom = new Element("nom");
		nom.addContent(newNom);
		
		Element portable = new Element("portable");
		portable.addContent(newPortable);
		
		Element bureau = new Element("bureau");
		bureau.addContent(newBureau);

		Element specialite = new Element("specialite");
		specialite.addContent(newSpecialite);
		
		Element arrivee = new Element("arrivee");
		arrivee.addContent(newArrivee);
		
		Element present = new Element("present");
		present.addContent(newPresent);
		
		Element garde = new Element("garde");
		garde.addContent(newGarde);

		child.addContent(nom);
		child.addContent(bureau);
		child.addContent(specialite);
		child.addContent(arrivee);
		child.addContent(present);
		child.addContent(garde);
		Element racine = xml.getRacine();
		racine.addContent(child);
		
		XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        sortie.output(document, new FileOutputStream(LocalConfig.REST_DIRECTORY + "annuaire.xml"));
		return true;
	   }
	/********************************************************************************************************************************/
	/********************************************************************************************************************************/
}
