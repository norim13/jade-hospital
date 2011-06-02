package jadehospital;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jadehospital.EtatSante.symptomeEnum;

public class AgentPatient extends HospitalAgent {

	public static int nbPatients = 0;
	private EtatSante etatSante;
	
	public void setup()
	{
		addBehaviour(new AgentPatientBhvInteractionMedecin());
		
		etatSante = new EtatSante();
		
		Library.registerInDF(Library.DF_AGENT_PATIENT_TYPE, this);
		
		// on prévient l'accueil de notre arrivée
		AID accueil = Library.getFirstReceiver(Library.DF_ACCUEIL_TYPE, Library.DF_ACCUEIL_NAME, this);
		ACLMessage msgAccueil = new ACLMessage(ACLMessage.INFORM);
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			msgAccueil.setContent(mapper.writeValueAsString(etatSante));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		msgAccueil.addReceiver(accueil);
		send(msgAccueil);
	}
	
	public EtatSante getEtatSante()	{ return etatSante; }
	
}
