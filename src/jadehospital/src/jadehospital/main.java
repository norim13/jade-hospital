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
			AgentController ac = mc.createNewAgent("AgentGenerateurPatient", "jadehospital.AgentGenerateurPatient" ,null );
			ac.start();
			Library.registerInDF("AgentGenerateurPatient","AgentGenerateurPatient",(Agent) ac);
			
		}catch(Exception e){System.out.println("#####\n" + e.getMessage() + "\n#####\n");}
		System.out.println("fini");
		
		
	}

}
