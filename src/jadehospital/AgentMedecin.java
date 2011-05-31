package jadehospital;

import jade.core.Agent;

public class AgentMedecin extends HospitalAgent {
	
	private int experience;
	
	public void setup()
	{
		Library.registerInDF(Library.DF_AGENT_MEDECIN_TYPE, this);
		
		experience = 20; // initialement 20% de chances de soigner le patient quand on l'opère
		addBehaviour(new AgentMedecinBhvOperer());
		addBehaviour(new AgentMedecinBhvExaminer());
	}
	
}
