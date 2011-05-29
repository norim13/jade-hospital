package jadehospital;

import jade.core.Agent;

public class AgentMedecin extends HospitalAgent {
	
	private int experience;
	
	public void setup()
	{
		experience = 20; // initialement 20% de chances de soigner le patient quand on l'opère
		addBehaviour(new AgentMedecinBhvOperer());
		addBehaviour(new AgentMedecinBhvExaminer());
	}
	
}
