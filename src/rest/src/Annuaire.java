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
			reponse = Integer.toString(listMedecins.size());		
		}else{
			if(nomDemande.equals("infirmier")){
				List listInfirmier = xml.getRacine().getChildren("infirmier");
		    	reponse = Integer.toString(listInfirmier.size());
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
	
	@Post
	public void update(Representation entity) throws JDOMException, IOException{
		Map<String,Object> attributes =getRequest().getAttributes();
		nomDemande = (String) attributes.get("nom");
		Form form = (Form) attributes.get("org.restlet.http.headers");
		newNom = form.getValues("nom");
		newSpecialite=form.getValues("specialite");

		xml = new ParserXML("src/annuaire.xml");
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
		    	  if(!newSpecialite.equals(""))
		    		  courant.getChild("specialite").setText(newSpecialite);
		    	  
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
	/*                                                           Put                                                           */
	/****************************************************************************************************************************/  
	 
	@Put
	public void insert() throws JDOMException, IOException {
		Map<String,Object> attributes =getRequest().getAttributes();
		nomDemande = (String) attributes.get("nom");
		Form form = (Form) attributes.get("org.restlet.http.headers");
		boolean reponse;
		if(nomDemande.equals("medecin")){
			newNom = form.getValues("nom");
			newSpecialite=form.getValues("specialite");
		}else{
			if(nomDemande.equals("infirmier")){
				newNom = form.getValues("nom");
			}
		}
		
		xml = new ParserXML(LocalConfig.REST_DIRECTORY + "annuaire.xml");
		
		//retourne les elements concernant l'id fournit dans l'URI
		reponse = ajoutpersonne();
	    /*if(!reponse.equals("")){
	    	getResponse().setEntity(new StringRepresentation(reponse,MediaType.APPLICATION_ALL_XML));
	    	getResponse().setStatus(Status.SUCCESS_OK);
	    }*/
	}

	private boolean ajoutpersonne() throws FileNotFoundException, IOException {
		
		org.jdom.Document document = xml.getDocument();
		Element child ;
		if(nomDemande.equals("medecin")){
			child = new Element("medecin");
			
			Element nom = new Element("nom");
			nom.addContent(newNom);
					
			Element specialite = new Element("specialite");
			specialite.addContent(newSpecialite);
			

			child.addContent(nom);
			child.addContent(specialite);
		}else{
			child = new Element("infirmier");
			
			Element nom = new Element("nom");
			nom.addContent(newNom);	

			child.addContent(nom);
		}

		Element racine = xml.getRacine();
		racine.addContent(child);
		
		XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
        sortie.output(document, new FileOutputStream(LocalConfig.REST_DIRECTORY + "annuaire.xml"));
		return true;
	   }
	/********************************************************************************************************************************/
	/********************************************************************************************************************************/
}
