package jadehospital;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.wrapper.AgentContainer;

public class Library {
	
	public static final String DF_ACCUEIL_TYPE = "hopital";
	public static final String DF_ACCUEIL_NAME = "accueil";
	
	
	private static AgentContainer mainContainer = null;
	
	/**
	 * Retourne le conteneur principal défini avec setMainContainer()
	 * @return
	 */
	public static AgentContainer getMainContainer()
	{
		return mainContainer;
	}
	
	/**
	 * Enregistre la référence sur le conteneur principal des agents
	 * Ceci permet d'y accéder depuis tous les agents qui en ont besoin pour instancier d'autres agents
	 * @param mc : référence sur le conteneur principal des agents
	 */
	public static void setMainContainer(AgentContainer mc)
	{
		mainContainer = mc;
	}
	
	/**
	 * Renvoie la liste des agents remplissant la fonction demandée, d'après une recherche dans le DF
	 * 
	 * @param type : champ "type" dans la description de l'agent
	 * @param name : champ "name" dans la description de l'agent
	 * @param a : référence sur l'agent qui demande la recherche
	 * @return
	 */
	public static ArrayList<AID> getReceivers(String type, String name, Agent a)
	{
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(type);
		sd.setName(name);
		template.addServices(sd);
				
		DFAgentDescription[] result = null;
		try {
			result = DFService.search(a, template);
		} catch (FIPAException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		ArrayList<AID> receivers = new ArrayList<AID>(); 
		for(int i = 0; i < result.length; ++i)		
			receivers.add(result[i].getName());
		
		return receivers;
	}
	
	/**
	 * Renvoie le premier agent remplissant la fonction demandée
	 * 
	 * @param type : champ "type" dans la description de l'agent
	 * @param name : champ "name" dans la description de l'agent
	 * @param a : référence sur l'agent qui demande la recherche
	 * @return
	 */
	public static AID getFirstReceiver(String type, String name, Agent a)
	{
		ArrayList<AID> receivers = getReceivers(type, name, a);
		
		if(receivers.isEmpty())
			return null;
		
		return receivers.get(0);
	}
	
	/**
	 * Effectue l'inscription d'un agent auprès du DF
	 * 
	 * @param type : champ "type" dans la description de l'agent
	 * @param name : champ "name" dans la description de l'agent
	 * @param a : référence sur l'agent qui demande à s'inscrire auprès du DF
	 */
	public static void registerInDF(String type, String name, Agent a)
	{
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(a.getAID());
		ServiceDescription sd = new ServiceDescription();
		
		sd.setType(type);
		sd.setName(name);
		
		dfd.addServices(sd);
			
		try {
			DFService.register(a, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
	
}
