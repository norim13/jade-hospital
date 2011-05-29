package jadehospital;

import java.io.StringWriter;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import org.codehaus.jackson.*;
import org.codehaus.jackson.map.ObjectMapper;

public class AgentPatient extends HospitalAgent {

	public static int nbPatients = 0;
	private double etatSante; // pour l'instant c'est un nombre qui indique l'état de santé ; plus tard ça pourra être une structure de données plus complexe (différents symptômes, etc.) 
	
	public void setup()
	{
		etatSante = 0; // 0 = très malade, 1 = guérison terminée
		
		System.out.println("salut, je suis un patient");
		
		// on prévient l'accueil de notre arrivée
		AID accueil = Library.getFirstReceiver(Library.DF_ACCUEIL_TYPE, Library.DF_ACCUEIL_NAME, this);
		ACLMessage msgAccueil = new ACLMessage(ACLMessage.INFORM);
		
		ObjectMapper mapper = new ObjectMapper(); 
		StringWriter sw = new StringWriter(); 
		EtatSante etatSante =new EtatSante();//genere un etat de sante al�atoire
			try { 
				mapper.writeValue(sw, etatSante); 
				String s = sw.toString();
				
				msgAccueil.setContent(s);
				msgAccueil.addReceiver(accueil);
				send(msgAccueil);
			}
			catch(Exception ex){}
		
	}
	
}
