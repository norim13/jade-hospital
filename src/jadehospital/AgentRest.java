package jadehospital;

public class AgentRest extends HospitalAgent {

	protected void setup() {
		
		Library.registerInDF(Library.DF_AGENT_REST_TYPE, this);
		addBehaviour(new AgentRestBhvTraiterRequete());
	}
	
}
