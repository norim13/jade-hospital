package jadehospital;


import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class AgentGenerateurPatientBehaviourGenPatient extends TickerBehaviour {

	public AgentGenerateurPatientBehaviourGenPatient(Agent a, long period) {
		super(a, period);
		// TODO Auto-generated constructor stub
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void onTick() {
		// TODO Auto-generated method stub
		AgentContainer agentContainer=Library.getMainContainer();
		
		AgentController ac = agentContainer.createNewAgent("Patient","jadehospital.Patient",null);
		ac.start();
		
		
	}



}
