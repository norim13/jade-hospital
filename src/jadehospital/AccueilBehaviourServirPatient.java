package jadehospital;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import rest.src.Client;
import rest.src.ImageMainServer;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AccueilBehaviourServirPatient extends CyclicBehaviour {
	
	@Override
	public void action() {
		
		ACLMessage msg = myAgent.receive();
		if(msg != null)
		{
			System.out.println("Accueil : j'ai recu un nouveau patient : " + msg.getSender().getLocalName());
			ObjectMapper mapper = new ObjectMapper();
			EtatSante etat = null;
			try {
				etat = mapper.readValue(msg.getContent(), EtatSante.class);
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			
			//inscription du nouveau patient dans le REST
			Client.addPatient(ImageMainServer.baseURL() + "patients/", msg.getSender().getLocalName(), etat.symptomeAsString(), etat.etatAsString());
			
			System.out.println("### Liste des patients ###");
			HashMap<Integer, HashMap<String, String>> patients = Client.getPatient(ImageMainServer.baseURL() + "patients/");
			Collection c = patients.values();
			Iterator it = c.iterator();
			while(it.hasNext())
			{
				HashMap<String, String> patient = (HashMap<String, String>) it.next();
				System.out.println(patient.get("nom") + ", " + patient.get("symptome") + ", " + patient.get("etat"));
			}
		}
		else{
			
			block();
		}

	}

}