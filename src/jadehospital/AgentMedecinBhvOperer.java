package jadehospital;

import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;

import rest.src.Client;
import rest.src.ImageMainServer;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgentMedecinBhvOperer extends OneShotBehaviour {

	AID patient;
	
	public AgentMedecinBhvOperer(AID patient)
	{
		this.patient = patient;
	}
	
	@Override
	public void action() {
		
		/*
		//examiner le REST pour trouver le nom d'un patient à opérer
		HashMap<String, String> infosPatient = ((AgentMedecin)myAgent).choisirPatient(Client.getPatient(ImageMainServer.baseURL() + "patients/"));
		if(infosPatient==null)
			return;
		
		System.out.println(myAgent.getLocalName() + " : j'ai un patient à opérer : " + infosPatient.get("nom"));
		
		//chercher ce patient dans le DF
		AID patient = Library.getFirstReceiverByName(infosPatient.get("nom"), myAgent);
		*/
		
		//envoyer un message au patient lui indiquant qu'on l'opère
		ACLMessage msgOperer= new ACLMessage(ACLMessage.REQUEST);
		msgOperer.addReceiver(patient);
		
		HashMap<String, String> request = new HashMap<String, String>();
		request.put("operation", "operer");
		request.put("experience", ((AgentMedecin) myAgent).experienceAsString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			msgOperer.setContent(mapper.writeValueAsString(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		myAgent.send(msgOperer);
		
		//recevoir la réponse
		System.out.println(myAgent.getLocalName() + " : j'opère le patient...");
		MessageTemplate mt = MessageTemplate.MatchSender(patient);
		ACLMessage msgResultatExamen = myAgent.blockingReceive(mt);
		
//		ObjectMapper mapperResultatOperation = new ObjectMapper();
//		HashMap<String,String> resultatOperation = null;
//		try {
//			resultatOperation = mapperResultatOperation.readValue(msgResultatExamen.getContent(), HashMap.class);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return;
//		}
//		
//		String etat = resultatOperation.get("resultat");
//		if(etat.equals("vivant"))
//			((HospitalAgent)myAgent).println("Le patient est soigné !");
//		else if(etat.equals("mort"))
//		{
//			((HospitalAgent)myAgent).println("Uh-oh, j'ai tué le patient...");
//		}	
		
		ObjectMapper mapperResultatOperation = new ObjectMapper();
		EtatSante resultatOperation = null;
		try {
			resultatOperation = mapperResultatOperation.readValue(msgResultatExamen.getContent(), EtatSante.class);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		int nouvelEtat = resultatOperation.getEtat();
		if(nouvelEtat == EtatSante.HIGHER)
			((HospitalAgent)myAgent).println("Le patient est soigné !");
		else if(nouvelEtat == EtatSante.LOWER)
		{
			((HospitalAgent)myAgent).println("Uh-oh, j'ai tué le patient...");
		}
		
		// dans les 2 cas (qu'il soit soigné = sorti de l'hôpital, ou mort) on le retire du REST
		
	}

}
