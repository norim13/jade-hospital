package jadehospital;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;

public class StaffAgentBhvVieillir extends TickerBehaviour {

	public StaffAgentBhvVieillir(Agent a, long period) {
		super(a, period);
	}

	@Override
	protected void onTick() {
		((StaffAgent)myAgent).vieillir();
	}

}
