package jadehospital;

import rest.src.ImageMainServer;
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
			
		}catch(Exception e){System.out.println("#####\n" + e.getMessage() + "\n#####\n");}
		
		ImageMainServer.startServer();
		
	}

}
