package jadehospital;

/**
 * Cette classe sert à configurer des constantes qui changent selon le poste de développement
 * (p. ex. emplacements de certains dossiers)
 * Donc elle doit être ignorée sur le SVN sauf quand il y a ajout d'une constante
 */
public class LocalConfig {

	public static final String REST_DIRECTORY = "src/rest/src/"; //répertoire où sont situées les sources du REST (chemin relatif par rapport au répertoire de base du projet)
	
}
