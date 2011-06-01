package jadehospital;

import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;

import rest.src.Client;
import rest.src.ImageMainServer;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentMedecinBhvOperer extends CyclicBehaviour {

	@Override
	public void action() {
		
		//examiner le REST pour trouver le nom d'un patient à opérer
		HashMap<String, String> infosPatient = ((AgentMedecin)myAgent).choisirPatient(Client.getPatient(ImageMainServer.baseURL() + "patients/"));
		if(infosPatient==null)
			return;
		
		System.out.println(myAgent.getLocalName() + " : j'ai un patient à opérer : " + infosPatient.get("nom"));
		
		//chercher ce patient dans le DF
		AID patient = Library.getFirstReceiverByName(infosPatient.get("nom"), myAgent);
		
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
	}

}
