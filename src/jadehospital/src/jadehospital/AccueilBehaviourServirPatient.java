package jadehospital;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AccueilBehaviourServirPatient extends CyclicBehaviour {

	@Override
	public void action() {
		// TODO Auto-generated method stub
		
		ACLMessage mess = myAgent.receive();
		if (mess != null) {
			System.out.println("message recu du patient");
		}
		else{
			
			block();
		}

	}

}
