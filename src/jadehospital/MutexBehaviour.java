package jadehospital;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CompositeBehaviour;

public abstract class MutexBehaviour extends Behaviour {

	/**
	 * Cette méthode ne peut pas être réimplémentée car
	 * c'est elle qui assure le comportement de mutex
	 */
	@Override
	public final void action() {
		if(getMutex())
			mutexAction();
	}

	public boolean getMutex() {
		
		return (((MutexCompositeBehaviour) getParent()).getMutex(this));
	}

	/**
	 * Cette méthode remplace la méthode action() d'un behaviour normal
	 */
	public abstract void mutexAction();
	
}
