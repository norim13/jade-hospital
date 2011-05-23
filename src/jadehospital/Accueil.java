package jadehospital;


import jade.core.Agent;
import jade.core.behaviours.Behaviour;



public class Accueil extends Agent {
	
	public void setup(){
		Library.registerInDF(Library.DF_ACCUEIL_TYPE, this);
		this.addBehaviour(new AccueilBehaviourServirPatient());	

	}
}
