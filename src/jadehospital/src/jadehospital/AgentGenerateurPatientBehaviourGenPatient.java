package jadehospital;


import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;

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
	// toutes les 5 secondes gŽnŽration d'un agent patient
	protected void onTick() {
		// TODO Auto-generated method stub
		AgentContainer agentContainer=Library.getMainContainer();
		
		AgentController ac;
		try {
			ac = agentContainer.createNewAgent(String.format(Library.DF_PATIENT_NAME+"%d",AgentPatient.nbPatients++),"jadehospital.AgentPatient",null);
			ac.start();
			
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}



}
