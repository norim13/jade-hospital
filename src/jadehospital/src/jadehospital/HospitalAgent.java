package jadehospital;

import jade.core.Agent;
import jade.domain.DFService;

/**
 * 
 * Tous les agents de l'hôpital qui s'enregistrent auprès du DF doivent
 * hériter de cette classe et non pas directement de jade.core.Agent
 * Ainsi, on s'assure que le DF ne contient pas des AID d'agents qui
 * n'existent plus
 *
 */
public class HospitalAgent extends Agent {

	 protected void takeDown() 
     {
        try
        {
        	DFService.deregister(this);
        }
        catch (Exception e)
        {
        	System.err.println("Erreur lors de la désinscription du DF :\n" + e.getMessage() + "\n\nStacktrace :\n" + e.getStackTrace());
        }
     }
	
}
