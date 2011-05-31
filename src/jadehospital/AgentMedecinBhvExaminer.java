package jadehospital;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import rest.src.Client;
import rest.src.ImageMainServer;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class AgentMedecinBhvExaminer extends CyclicBehaviour {

	@Override
	public void action() {
		
		//examiner le REST pour trouver le nom d'un patient à examiner
		HashMap<String, String> infosPatient = choisirPatient(Client.getPatient(ImageMainServer.baseURL() + "patients/"));
		if(infosPatient==null)
			return;
		
		System.out.println(myAgent.getLocalName() + " : j'ai un patient à examiner : " + infosPatient.get("nom"));
		
		//chercher ce patient dans le DF
		AID patient = Library.getFirstReceiverByName(infosPatient.get("nom"), myAgent);
		
		//envoyer un message au patient lui demandant ses symptômes
		ACLMessage msgExaminer = new ACLMessage(ACLMessage.REQUEST);
		msgExaminer.addReceiver(patient);
		
		HashMap<String, String> request = new HashMap<String, String>();
		request.put("operation", "examiner");
		ObjectMapper mapper = new ObjectMapper();
		try {
			msgExaminer.setContent(mapper.writeValueAsString(request));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		myAgent.send(msgExaminer);
		
		//recevoir la réponse
		System.out.println(myAgent.getLocalName() + " : j'attends la réponse du patient...");
		MessageTemplate mt = MessageTemplate.MatchSender(patient);
		ACLMessage msgResultatExamen = myAgent.blockingReceive(mt);
		
		ObjectMapper mapperResultatExamen = new ObjectMapper();
		EtatSante resultatExamen = null;
		try {
			resultatExamen = mapperResultatExamen.readValue(msgResultatExamen.getContent(), EtatSante.class);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		System.out.println(myAgent.getLocalName() + " : le symptôme du patient est : " + resultatExamen.symptomeAsString() + " ; état de santé : " + resultatExamen.etatAsString());
	}
	
	private HashMap<String, String> choisirPatient(HashMap<Integer, HashMap<String, String>> patients) {
		int nbPatients = patients.size();
		
		if(nbPatients==0)
			return null;
		
		Collection c = patients.values();
		Iterator it = c.iterator();
		int numPatientChoisi = (new Random()).nextInt(nbPatients);
		
		HashMap<String, String> ret=null;
		for(int i=0 ; i < numPatientChoisi ; i++)
			ret = (HashMap<String, String>) it.next();
		
		return ret;
	}

}
