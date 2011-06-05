package jadehospital;

import java.io.IOException;
import java.util.HashMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import rest.src.Client;
import rest.src.ImageMainServer;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class AgentRestBhvTraiterRequete extends CyclicBehaviour {

	@Override
	public void action() {
		
		ACLMessage msg = myAgent.receive();
		
		if(msg==null)
			return;
		
		ObjectMapper mapperRequest = new ObjectMapper();
		HashMap<String, String> request = null;
		try {
			request = mapperRequest.readValue(msg.getContent(), HashMap.class);
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		String operation = request.get("operation");
		String ressource = request.get("ressource");
		
		String responseString = null;
		ObjectMapper mapperResponse = new ObjectMapper();
		
		try {
			if(ressource.equals("patient")) {
	
				// récupérer tous les patients
				if(operation.equals("get")) {
					HashMap<Integer, HashMap<String, String>> response = Client.getPatient(ImageMainServer.baseURL() + "patients/");
					try {
						responseString = mapperResponse.writeValueAsString(response);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				// ajouter un patient
				else if(operation.equals("add")) {
					boolean response = Client.addPatient(ImageMainServer.baseURL() + "patients/", request.get("nom"), request.get("symptome"), request.get("etat"));
					try {
						responseString = mapperResponse.writeValueAsString(response);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				// modifier un patient
				else if(operation.equals("change")) {
					boolean response = Client.changePatient(ImageMainServer.baseURL() + "patients/", request.get("nom"), request.get("symptome"), request.get("etat"), request.get("infirmier"));
					try {
						responseString = mapperResponse.writeValueAsString(response);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				// supprimer un patient
				else if(operation.equals("delete")) {
					boolean response = Client.deletePatient(ImageMainServer.baseURL() + "patients/", request.get("nom"));
					try {
						responseString = mapperResponse.writeValueAsString(response);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
				else {
					throw new RestAgentException(operation, ressource);
				}
				
			}
			
			else if(ressource.equals("infirmier")) {
				
				// ajouter un infirmier
				if(operation.equals("add")) {
					responseString = Client.addInfirmier(ImageMainServer.baseURL() + "annuaire/", request.get("nom"));
				}
				
				else {
					throw new RestAgentException(operation, ressource);
				}
				
			}
			
			else if(ressource.equals("medecin")) {
				
				// ajouter un médecin
				if(operation.equals("add")) {
					responseString = Client.addMedecin(ImageMainServer.baseURL() + "annuaire/", request.get("nom"), request.get("specialite"));
				}
				
				else {
					throw new RestAgentException(operation, ressource);
				}
				
			}
			
			else if(ressource.equals("personnel")) {
				
				// obtenir le personnel
				if(operation.equals("get")) {
					responseString = Client.getPersonnel(ImageMainServer.baseURL() + "annuaire/");
				}
				
				else {
					throw new RestAgentException(operation, ressource);
				}
				
			}
			
			else {
				throw new RestAgentException(operation, ressource);
			}
		}
		catch(RestAgentException e) {
			e.printStackTrace();
		}
		
		// envoi du message de réponse
		ACLMessage msgResponse = msg.createReply();
		msgResponse.setPerformative(ACLMessage.INFORM); // TODO mettre FAILURE si on attendait une réponse boolean qui vaut false ?
		msgResponse.setContent(responseString);
		myAgent.send(msgResponse);
	}
	
	
	class RestAgentException extends Exception {
		
		private RestAgentException(String msg) { super(msg); }
		
		public RestAgentException(String operation, String ressource) {
			this("La requête suivante ne peut pas être traitée :\nOpération : " + operation + "\nRessource : " + ressource);
		}
		
	}

}
