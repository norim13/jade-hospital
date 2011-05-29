package jadehospital;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AccueilBehaviourServirPatient extends CyclicBehaviour {
	
	@Override
	public void action() {
		
		ACLMessage msg = myAgent.receive();
		if(msg != null)
		{
			System.out.println("Accueil : j'ai recu un nouveau patient");
			//((AgentAccueil) myAgent).pseudoREST.add(msg.getSender());
			
			System.out.println("### Liste des patients ###");
			//ListIterator<AID> it = ((AgentAccueil) myAgent).pseudoREST.listIterator();
			//while(it.hasNext())
				//System.out.println(it.next());
		}
		else{
			
			block();
		}

	}

}