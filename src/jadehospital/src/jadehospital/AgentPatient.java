package jadehospital;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class AgentPatient extends Agent {

	private double etatSante; // pour l'instant c'est un nombre qui indique l'état de santé ; plus tard ça pourra être une structure de données plus complexe (différents symptômes, etc.) 
	
	public void setup()
	{
		etatSante = 0; // 0 = très malade, 1 = guérison terminée
		
		System.out.println("salut, je suis un patient");
		
		// on prévient l'accueil de notre arrivée
		AID accueil = Library.getFirstReceiver(Library.DF_ACCUEIL_TYPE, Library.DF_ACCUEIL_NAME, this);
		ACLMessage msgAccueil = new ACLMessage(ACLMessage.INFORM);
		msgAccueil.setContent(Double.toString(etatSante));
		msgAccueil.addReceiver(accueil);
		send(msgAccueil);
	}
	
}
