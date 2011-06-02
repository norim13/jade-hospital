package rest.src;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.jdom.output.XMLOutputter;

public class ParserXML {
	Document document = null;
	static Element racine;
	String erreur="";
	
	ParserXML(String a) throws JDOMException, IOException, FileNotFoundException {
		//On crée une instance de SAXBuilder
	    SAXBuilder sxb = new SAXBuilder();
	      
	    //On crée un nouveau document JDOM avec en argument le fichier XML
	    //Le parsing est terminé ;)	    
	    try{
	    	document = sxb.build(new File(a));
	    }catch (FileNotFoundException e){
	    	erreur = "notfound";
	    	throw(e);
	    }
	    
	    //On initialise un nouvel élément racine avec l'élément racine du document.
	    racine = document.getRootElement();	   
	}
	
	public Document getDocument(){
		return document;
	}
	
	public Element getRacine(){
		return racine;
	}

	public String getErreur() {
		return erreur;
	}
}
