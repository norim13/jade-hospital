package jadehospital;

import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import org.apache.solr.common.util.Hash;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Behaviour d'interaction avec le médecin
 * Le médecin peut examiner ou opérer le patient
 *
 */
public class AgentPatientBhvInteractionMedecin extends CyclicBehaviour {

	@Override
	public void action() {
		
		ACLMessage msg = myAgent.receive();
		if(msg == null) {
			return;
		}
		
		ObjectMapper mapper = new ObjectMapper();
		HashMap<String, String> input = null;
		try {
			input = mapper.readValue(msg.getContent(), HashMap.class);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		EtatSante etat = ((AgentPatient) myAgent).getEtatSante(); //état de santé à communiquer au médecin
		ACLMessage msgReponse = msg.createReply();
		msgReponse.setPerformative(ACLMessage.INFORM);
		
		if(input.get("operation").equals("examiner"))
		{
			System.out.println(myAgent.getLocalName() + " : je suis examiné");
			etat = ((AgentPatient) myAgent).getEtatSante();
			
			((HospitalAgent) myAgent).sleep(30);
		}
		else if(input.get("operation").equals("operer"))
		{
			System.out.println(myAgent.getLocalName() + " : je suis opéré, priez pour moi...");
			
			// TODO utiliser l'état de santé pour gérer différents types d'opérations
			int experienceMedecin = Integer.parseInt(input.get("experience"));
			
			int rouletteRusse = new Random().nextInt(100);
			boolean gueri = (rouletteRusse < experienceMedecin);
			
			if(gueri)
				etat.setEtatMax();
			else
				etat.setEtatMin(); // TODO faire une gestion + fine : l'opération peut échouer sans que le patient meure
			
			((HospitalAgent) myAgent).sleep(50);
		}
		
		// qu'on ait été examiné ou opéré, on communique le (nouvel) état de santé au médecin
		ObjectMapper mapperReponse = new ObjectMapper();
		try {
			msgReponse.setContent(mapperReponse.writeValueAsString(etat));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		myAgent.send(msgReponse);
	}

}
