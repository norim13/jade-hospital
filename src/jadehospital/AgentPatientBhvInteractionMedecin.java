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
		
		if(input.get("operation").equals("examiner")) // le médecin veut m'examiner, je lui communique mes symptômes
		{
			System.out.println(myAgent.getLocalName() + " : je suis examiné");
			
			ACLMessage msgReponse = msg.createReply();
			EtatSante etat = ((AgentPatient) myAgent).getEtatSante();
			
			ObjectMapper mapperReponse = new ObjectMapper();
			try {
				msgReponse.setContent(mapperReponse.writeValueAsString(etat));
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
			
			myAgent.send(msgReponse);
		}
		else if(input.get("operation").equals("operer")) // le médecin veut m'opérer, je lui communique le résultat
		{
			System.out.println(myAgent.getLocalName() + " : je suis opéré, priez pour moi...");
			
			EtatSante etat = ((AgentPatient) myAgent).getEtatSante(); // TODO utiliser l'état de santé pour gérer différents types d'opérations
			int experienceMedecin = Integer.parseInt(input.get("experience"));
			
			int rouletteRusse = new Random().nextInt(100);
			boolean gueri = (rouletteRusse < experienceMedecin);
			
			if(gueri)
				etat.setEtatMax();
			else
				etat.setEtatMin();
			
//			HashMap<String, String> resultat = new HashMap<String, String>();
//			resultat.put("resultat", (rouletteRusse < experienceMedecin ? "vivant" : "mort")); // TODO faire une gestion + fine : l'opération peut échouer sans que le patient meure
			
			ACLMessage msgReponse = msg.createReply();
			msgReponse.setPerformative(ACLMessage.INFORM);
			
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

}
