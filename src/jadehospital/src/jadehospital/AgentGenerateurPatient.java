package jadehospital;

import jade.core.Agent;

public class AgentGenerateurPatient extends Agent{
	
	public void setup(){
		this.addBehaviour(new AgentGenerateurPatientBehaviourGenPatient(this,5000));//en ms
	}

}
