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
		System.out.println("début");
		try{
			pf = new ProfileImpl();
		} catch(Exception e){System.out.println(e.getStackTrace());}
		
		AgentContainer mc = rt.createMainContainer(pf);
		try{
			AgentController ac = mc.createNewAgent("Pierre", "KBAgent" ,null );
			
			ac.start();
		}catch(Exception e){System.out.println("#####\n" + e.getMessage() + "\n#####\n");}
		System.out.println("fini");
		
		//jade.wrapper.AgentContainer mc = rt.createMainContainer(pf);
		//AgentController ac = mc.createNewAgent("Pierre", "Chat.AgentChat" ,new Object[]{"Maurice"} );
		//AgentController ac2 = mc.createNewAgent("Maurice", "Chat.AgentChat" ,new Object[]{"Pierre"});
		
		//ac.start();
		//ac2.start();
		
		
	}

}
