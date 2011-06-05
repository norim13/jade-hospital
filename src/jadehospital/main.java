package jadehospital;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import rest.src.ImageMainServer;
import jade.wrapper.AgentContainer;
import jade.core.Agent;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;



public class Main {

	/**
	 * @param args
	 * @throws StaleProxyException 
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws StaleProxyException, FileNotFoundException {
		File logFile = new File("debug.log");
		System.setErr(new PrintStream(logFile));
//		System.out.println(System.out);
//		System.setOut(new PrintStream(logFile));
//		System.out.println(System.out);
		
		Runtime rt =Runtime.instance();
		Profile pf=null;
		System.out.println("debut");
		try{
			pf = new ProfileImpl();
		} catch(Exception e){System.out.println(e.getStackTrace());}
		
		AgentContainer mc=null;
		try {
			mc = rt.createMainContainer(pf);
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		Library.setMainContainer(mc);
		ImageMainServer.startServer();
		try{
			AgentController agentGenerateurPatient = mc.createNewAgent("AgentGenerateurPatient", "jadehospital.generateurPatient.AgentGenerateurPatient" ,null );
			AgentController agentAccueil=mc.createNewAgent("Accueil","jadehospital.accueil.Accueil",null);
			
			AgentController agentDRH=mc.createNewAgent("DRH", "jadehospital.drh.AgentDRH", null);
			AgentController agentDirecteur=mc.createNewAgent("Directeur","jadehospital.directeur.AgentDirecteur", null);
			
			agentGenerateurPatient.start();
			agentAccueil.start();
			agentDRH.start();
			agentDirecteur.start();
			
		}catch(Exception e){System.out.println("#####\n" + e.getStackTrace() + "\n" + e.getMessage() + "\n#####\n");}
		
		
	}

}
