package jadehospital;

import jade.core.Agent;

public class AgentDirecteur extends Agent {
	
	public void setup(){
		this.addBehaviour(new AgentDirecteurBehvDemandePersonnelDRH());
		this.addBehaviour(new AgentDirecteurBehvNommerChefService());
	}

}
