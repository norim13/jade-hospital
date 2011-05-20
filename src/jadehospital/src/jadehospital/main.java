package jadehospital;

import jade.wrapper.AgentContainer;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;



public class main {

	/**
	 * @param args
	 * @throws StaleProxyException 
	 */
	public static void main(String[] args) throws StaleProxyException {
		Runtime rt =Runtime.instance();
		Profile pf=null;
		System.out.println("debut");
		try{
			pf = new ProfileImpl();
		} catch(Exception e){System.out.println(e.getStackTrace());}
		
		AgentContainer mc = rt.createMainContainer(pf);
		Library.setMainContainer(mc);
		try{
			AgentController agentGenerateurPatient = mc.createNewAgent("AgentGenerateurPatient", "jadehospital.AgentGenerateurPatient" ,null );
			AgentController agentAccueil=mc.createNewAgent("Accueil","jadehospital.Accueil",null);
			
			agentGenerateurPatient.start();
			agentAccueil.start();
			
			Library.registerInDF(Library.DF_AGENT_GENERATEUR_PATIENT_TYPE,Library.DF_AGENT_GENERATEUR_PATIENT_NAME,(Agent)agentGenerateurPatient);
			Library.registerInDF(Library.DF_ACCUEIL_NAME,Library.DF_ACCUEIL_TYPE,(Agent)agentAccueil);
			
		}catch(Exception e){System.out.println("#####\n" + e.getMessage() + "\n#####\n");}
		System.out.println("fini");
		
		
	}

}
