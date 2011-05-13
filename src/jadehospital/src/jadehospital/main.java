package jadehospital;

import jade.wrapper.AgentContainer;
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
		try{
			AgentController ac = mc.createNewAgent("AgentGenerateurPatient", "jadehospital.AgentGenerateurPatient" ,null );
			ac.start();
			
		}catch(Exception e){System.out.println("#####\n" + e.getMessage() + "\n#####\n");}
		System.out.println("fini");
		
		
	}

}
