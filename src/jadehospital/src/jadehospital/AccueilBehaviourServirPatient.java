package jadehospital;

import org.codehaus.jackson.map.ObjectMapper;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import rest.Client;

public class AccueilBehaviourServirPatient extends CyclicBehaviour {

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
		ACLMessage msg = myAgent.receive();
		if(msg != null)
		{
			System.out.println("Accueil : j'ai recu un nouveau patient");
			
			String s = msg.getContent();
			System.out.println("symptome: " + msg.getContent());

			ObjectMapper mapper = new ObjectMapper(); 
			try {
			EtatSante etatSante = mapper.readValue(s, EtatSante.class);
			//System.out.println("symptome: " + etatSante.getSymptome());
			//System.out.println("etat: " + etatSante.getEtat());
			
			Client.addPatient("http://localhost:8182/patients/",msg.getSender().getLocalName() ,etatSante.getSymptome().toString(), Integer.toString(etatSante.getEtat()));

			} catch(Exception ex) { System.out.println(ex.getMessage().toString());}
			

		}
		else{
			
			block();
		}

	}

}