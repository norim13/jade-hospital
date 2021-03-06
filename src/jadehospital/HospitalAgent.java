package jadehospital;

import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;

import jade.core.Agent;
import jade.domain.DFService;
import jade.lang.acl.ACLMessage;

/**
 * 
 * Tous les agents de l'hôpital qui s'enregistrent auprès du DF doivent
 * hériter de cette classe et non pas directement de jade.core.Agent
 * Ainsi, on s'assure que le DF ne contient pas des AID d'agents qui
 * n'existent plus
 *
 */
public abstract class HospitalAgent extends Agent {
	
	protected void takeDown() 
	{
		try {
			DFService.deregister(this);
		}
		catch (Exception e) {
			System.err.println("Erreur lors de la désinscription du DF :\n" + e.getMessage() + "\n\nStacktrace :\n" + e.getStackTrace());
		}
	}
	
	protected void println(String s) {
		System.out.println(getLocalName() + " : " + s);
	}
	
	protected void sleep(long nbPeriodes) {
		try {
			Thread.sleep(Library.UNITE_TEMPS * nbPeriodes);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
