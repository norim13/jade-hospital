package jadehospital;
import java.util.ArrayList;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CompositeBehaviour;
import jade.util.leap.Collection;


public class MutexCompositeBehaviour extends CompositeBehaviour {

	private ArrayList<MutexBehaviour> childBehaviours;
	private MutexBehaviour mutexOwner = null;
	private int currentChildRunning = 0;
	
	public MutexCompositeBehaviour(MutexBehaviour... bhvList)
	{
		childBehaviours = new ArrayList<MutexBehaviour>();
		
		for(MutexBehaviour mb : bhvList)
			childBehaviours.add(mb);
	}
	
	@Override
	protected boolean checkTermination(boolean arg0, int arg1) {
		return false;
	}

	@Override
	public Collection getChildren() {
		return null;
	}

	@Override
	protected Behaviour getCurrent() {
		return childBehaviours.get(currentChildRunning);
	}

	@Override
	protected void scheduleFirst() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void scheduleNext(boolean arg0, int arg1) {
		// TODO Auto-generated method stub

	}
	
	public void addBehaviour(MutexBehaviour mb)
	{
		childBehaviours.add(mb);
	}
	
	/**
	 * Indique si un MutexBehaviour donné a le mutex
	 * @param mb
	 * @return
	 */
	public boolean hasMutex(MutexBehaviour mb)
	{
		return (mutexOwner == mb);
	}
	
	/**
	 * Attribue si possible le mutex à un MutexBehaviour
	 * @param mb : MutexBehaviour qui demande le mutex
	 * @return : true si mb a déjà le mutex ou si on peut le lui attribuer ; false sinon
	 */
	public boolean getMutex(MutexBehaviour mb)
	{
		if(mutexOwner != null)
			return (mutexOwner == mb); // si le bhv qui demande le mutex l'a déjà : ok ; si un autre bhv a le mutex : ko
		
		mutexOwner = mb; // mutex libre : on l'attribue à mb
		return true;
	}

}
