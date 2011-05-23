package jadehospital;

import jade.core.Agent;

public class AgentGenerateurPatient extends Agent{
	
	public void setup(){
		Library.registerInDF(Library.DF_AGENT_GENERATEUR_PATIENT_TYPE, this);
		this.addBehaviour(new AgentGenerateurPatientBehaviourGenPatient(this,5000));//en ms
	}

}
