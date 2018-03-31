package Serveur;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public abstract class Requests {
	
	RequestType type;
	
	public enum RequestType {
		//Client requests
		AUTHETIFIER_UTILISATEUR,
		LOGOUT,
		CREER_SALLE_DISCUSSION,
		JOIN_ROOM,
		ECRIRE_MESSAGE,
		SUPPRIMER_MESSAGE,
		
		//Server requests
		AUTHENTICATE_USER_RESPONSE,
		CREER_SALLE_DISCUSSION_RESPONSE,
		JOIN_ROOM_RESPONSE,
		UPDATE_USER,
		UPDATE_ROOM,
		UPDATE_MESSAGE,
		LOGOUT_RESPONSE
	}
	
	

	public Requests(RequestType type) {
		this.type = type;
	}
	
	public RequestType getType() {
		return type;
	}
	
	public static Requests parseRequest(String jsonString) {
		/***
		 * paser afin de lire le request type
		 */
		Requests request = null;
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(jsonString).getAsJsonObject();
		String type = json.get("type").getAsString().toUpperCase();
		
		switch(RequestType.valueOf(type)) {
			case AUTHETIFIER_UTILISATEUR:
				request = new AuthentificationUser(json);
				break;
		
			case CREER_SALLE_DISCUSSION:
				/* A remplacer */ 
				break;
				
			case CREER_SALLE_DISCUSSION_RESPONSE:
				/* A remplacer */
				break;
		
			case JOIN_ROOM:
				/* A remplacer */
				break;
				
			case ECRIRE_MESSAGE:
				/* A remplacer */
				break;
		
			case SUPPRIMER_MESSAGE:
				/* A remplacer */	
				break;
				
				
			case LOGOUT:
				/* A remplacer */
				break;
				
			default:
				System.out.println("WHAT ZE PHOQUE IS ZIS REQUETEUH? ");
		}
		return request;
	}
	
	public abstract String toJsonString();
	
	
}
