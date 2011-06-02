package jadehospital;

import jade.wrapper.StaleProxyException;

public abstract class StaffAgent extends HospitalAgent {

	private float age;
	
	public StaffAgent(float age) {
		super();
		this.age = age;
	}
	
	public void vieillir() {
		age++;
		if(age >= 62) {
			println("je pars à la retraite");
			takeDown();
			try {
				this.getContainerController().kill();
			} catch (StaleProxyException e) {
				e.printStackTrace();
			}
		}
	}
	
}
