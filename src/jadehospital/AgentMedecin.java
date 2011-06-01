package jadehospital;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import jade.core.Agent;

public class AgentMedecin extends HospitalAgent {
	
	private int experience;
	
	public void setup()
	{
		Library.registerInDF(Library.DF_AGENT_MEDECIN_TYPE, this);
		
		experience = 20; // initialement 20% de chances de soigner le patient quand on l'opère
//		addBehaviour(new AgentMedecinBhvOperer());
		addBehaviour(new AgentMedecinBhvExaminer());
	}
	
	public HashMap<String, String> choisirPatient(HashMap<Integer, HashMap<String, String>> patients) {
		int nbPatients = patients.size();
		
		if(nbPatients==0)
			return null;
		
		Collection c = patients.values();
		Iterator it = c.iterator();
		int numPatientChoisi = (new Random()).nextInt(nbPatients);
		
		HashMap<String, String> ret=null;
		for(int i=0 ; i < numPatientChoisi ; i++)
			ret = (HashMap<String, String>) it.next();
		
		return ret;
	}
	
	public int getExperience() { return experience; }
	
	public String experienceAsString() { return String.format("%d", experience); }
	
}
