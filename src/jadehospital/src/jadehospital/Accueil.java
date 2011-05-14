package jadehospital;


import jade.core.Agent;
import jade.core.behaviours.Behaviour;



public class Accueil extends Agent {
	
	public void setup(){
		
		this.addBehaviour(new AccueilBehaviourServirPatient());	

	}
}
