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
	
	//Effectif requis minimum pour le bon fonctionnement de l'hopital
	public static final int NB_MAX_MEDECINS = 5;
	public static final int NB_MAX_INFIRMIERES= 5;
	
	public static final String DF_ACCUEIL_TYPE = "type_Accueil";
	public static final String DF_ACCUEIL_NAME = "Accueil";
	
	public static final String DF_AGENT_GENERATEUR_PATIENT_TYPE="type_AgentGenerateurPatient";
	public static final String DF_AGENT_GENERATEUR_PATIENT_NAME="AgentGenerateurPatient";
	
	public static final String DF_AGENT_MEDECIN_TYPE = "type_AgentMedecin";
	public static final String DF_AGENT_MEDECIN_NAME = "AgentMedecin";
	
	public static final String DF_AGENT_PATIENT_TYPE = "type_AgentPatient";
	public static final String DF_AGENT_PATIENT_NAME = "AgentPatient";
	
	public static final String DF_AGENT_INFIRMIERE_TYPE = "type_AgentInfirmiere";
	public static final String DF_AGENT_INFIRMIERE_NAME = "AgentInfirmire";
	
	public static final String DF_AGENT_DRH_TYPE = "type_DRH";
	public static final String DF_AGENT_DRH_NAME = "AgentDRH";	
	
	public static final String DF_AGENT_DIRECTEUR_TYPE = "type_Directeur";
	public static final String DF_AGENT_DIRECTEUR_NAME = "AgentDirecteur";
	
	public static final String DF_AGENT_REST_TYPE = "AgentRest";
	
	public static final long UNITE_TEMPS = 100;
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
		
		if(type != null && ! type.isEmpty())
			sd.setType(type);
		
		if(name != null && ! name.isEmpty())
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
	 * Renvoie le premier agent qui a les type et nom demandés 
	 * 
	 * @param type : champ "type" dans la description de l'agent
	 * @param name : champ "name" dans la description de l'agent
	 * @param a : référence sur l'agent qui demande la recherche
	 * @return AID du premier agent trouvé
	 */
	public static AID getFirstReceiver(String type, String name, Agent a)
	{
		ArrayList<AID> receivers = getReceivers(type, name, a);
		
		if(receivers.isEmpty())
			return null;
		
		return receivers.get(0);
	}
	
	
	/**
	 * Renvoie le premier agent qui a le type demandé
	 * 
	 * @param type : champ "type" dans la description de l'agent
	 * @param a : référence sur l'agent qui demande la recherche
	 * @return AID du premier agent trouvé
	 */
	public static AID getFirstReceiverByType(String type, Agent a)
	{
		return getFirstReceiver(type, "", a);
	}
	
	
	/**
	 * Renvoie le premier agent qui a le nom demandé
	 * 
	 * @param name : champ "name" dans la description de l'agent
	 * @param a : référence sur l'agent qui demande la recherche
	 * @return AID du premier agent trouvé
	 */
	public static AID getFirstReceiverByName(String name, Agent a)
	{
		return getFirstReceiver("", name, a);
	}
	
	
	/**
	 * Effectue l'inscription d'un agent auprès du DF
	 * 
	 * @param type : champ "type" dans la description de l'agent
	 * @param a : référence sur l'agent qui demande à s'inscrire auprès du DF
	 */
	public static void registerInDF(String type, Agent a)
	{
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(a.getAID());
		ServiceDescription sd = new ServiceDescription();
		
		sd.setType(type);
		sd.setName(a.getLocalName());
		
		dfd.addServices(sd);
			
		try {
			DFService.register(a, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
	
}
