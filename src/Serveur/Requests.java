package Serveur;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Structures.Utils;

public abstract class Requests {
	

	
	//##################### LIST OF REQUESTS FOR EACH ENDPOINTS
	//#########################
	public static String initClient() throws UnsupportedEncodingException {
		/*Prendre les data du serveur: salles, users...
		 * Autres init() procedure at serveur start
		 * */
		String urlParameters = "";
		System.out.println("INITCLT: " + urlParameters);
		//Encoder l'URL - doit inclure le port & le context de la requête 
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.initClientURI;

		String response = executePost(targetURL, urlParameters);
		//REMOVE THE SPACES & LINE RETURN!!!!
		System.out.println("INITCLT: server response - " + response);
		
		return response.trim();
}
	
	public static String getUsersFromServer() {
		/*Prendre les data du serveur: users...
		 * 
		 * */
		String urlParameters = "";
		//Encoder l'URL - doit inclure le port & le context de la requête 
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.getUsersFromServer;

		String response = executePost(targetURL, urlParameters);
		//REMOVE THE SPACES & LINE RETURN!!!!		
		return response.trim();
	}
	
	public static String getConnectedUsersFromServer() {
		/*Prendre les data du serveur: users...
		 * 
		 * */
		String urlParameters = "";
		//Encoder l'URL - doit inclure le port & le context de la requête 
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.getConnectedUsersURI;

		String response = executePost(targetURL, urlParameters);
		//REMOVE THE SPACES & LINE RETURN!!!!		
		return response.trim();
	}


	public static String getSallesFromServer() {
		/*Prendre les data du serveur: salles...
		 * 
		 * */
		String urlParameters = "";
		//Encoder l'URL - doit inclure le port & le context de la requête 
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.getSallesFromServer;

		String response = executePost(targetURL, urlParameters);
		//REMOVE THE SPACES & LINE RETURN!!!!		
		return response.trim();
	}
	
	public static String GetArchives(int userId, int salleId) throws UnsupportedEncodingException {
		/*Prendre les data du serveur: salles...
		 * 
		 * */
		String urlParameters = Utils.usagerIdParam+"=" + URLEncoder.encode(Integer.toString(userId), "UTF-8") 
		+ "&"+Utils.salleIdParam+"=" + URLEncoder.encode(Integer.toString(salleId), "UTF-8");
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.getArchiveURI;

		String response = executePost(targetURL, urlParameters);
		//REMOVE THE SPACES & LINE RETURN!!!!		
		return response.trim();
	}
	
	public static String suscribeUser(int salleId, int userId) throws UnsupportedEncodingException {
		
		String urlParameters = Utils.salleIdParam+"=" + URLEncoder.encode(Integer.toString(salleId), "UTF-8") 
		+ "&"+Utils.usagerIdParam+"=" + URLEncoder.encode(Integer.toString(userId), "UTF-8");
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.suscribeUsagerURI;

		String response = executePost(targetURL, urlParameters);

		System.out.println("SUB: server response - " + response);
		//REMOVE THE SPACES & LINE RETURN!!!!		
		return response.trim();  
	} 
	public static String unsuscribeUser(int salleId, int userId) throws UnsupportedEncodingException {
		 
		String urlParameters = Utils.salleIdParam+"=" + URLEncoder.encode(Integer.toString(salleId), "UTF-8") 
		+ "&"+Utils.usagerIdParam+"=" + URLEncoder.encode(Integer.toString(userId), "UTF-8");
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.unsubscribeUsagerURI;

		String response = executePost(targetURL, urlParameters);
		System.out.println("UNSUB: server response - " + response);
		
		//REMOVE THE SPACES & LINE RETURN!!!!		
		return response.trim(); 
	}
	
	public static String getConnectedUsers(int salleId, int userId) throws UnsupportedEncodingException {
		
		String urlParameters = Utils.salleIdParam+"=" + URLEncoder.encode(Integer.toString(salleId), "UTF-8") 
		+ "&"+Utils.usagerIdParam+"=" + URLEncoder.encode(Integer.toString(userId), "UTF-8");
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.unsubscribeUsagerURI;

		String response = executePost(targetURL, urlParameters);
		//REMOVE THE SPACES & LINE RETURN!!!!		
		return response.trim(); 
	}
	
	
	public static boolean athenticateUser(String username, String password) throws UnsupportedEncodingException {
		/*Envoie une requête au serveur pour voir si un user matche avec ce qui a été donné*/
		//Encoder tous les params de la requête
		String urlParameters = Utils.usagerNomParam+"=" + URLEncoder.encode(username, "UTF-8") 
		+ "&"+Utils.usagerPasswordParam+"=" + URLEncoder.encode(password, "UTF-8");
		
		System.out.println("AUTH: urlParam - " + urlParameters);
		//Encoder l'URL - doit inclure le port & le context de la requête 
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.authUserURI;

		
		//TODO: science de la comparaison des string en java... ma sinon ça marche.
		//Found it: there was a /r (line return) in the response...
		String response = executePost(targetURL, urlParameters);
		//REMOVE THE SPACES & LINE RETURN!!!!private
		response = response.trim();
		String errorCode = response.substring(0, 3);
		String userInfo = response.substring(3, response.length());
		
		System.out.println("CREATESALLE: server response - " + response);
		
		return errorCode.equals(Utils.OK);
	}

	public static boolean createUser(String param_username, String param_password)throws UnsupportedEncodingException {
		/*Envoie une requête au serveur pour voir si un user matche avec ce qui a été donné*/
		//Encoder tous les params de la requête
		String urlParameters = Utils.usagerNomParam+"=" + URLEncoder.encode(param_username, "UTF-8") 
		+ "&"+Utils.usagerPasswordParam+"=" + URLEncoder.encode(param_password, "UTF-8");
		
		System.out.println("CREATEUSER: urlParam - " + urlParameters);
		//Encoder l'URL - doit inclure le port & le context de la requête 
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.creationUsagerURI;

		
		//TODO: science de la comparaison des string en java... ma sinon ça marche.
		//Found it: there was a /r (line return) in the response...
		String response = executePost(targetURL, urlParameters);
		//REMOVE THE SPACES & LINE RETURN!!!!
		response = response.trim();
		System.out.println("CREATEUSER: server response - " + response);

		return response.equals(Utils.OK);
	}

	public static boolean updateUser(String userID, String param_username, String param_password)throws UnsupportedEncodingException {
		/*Envoie une requête au serveur pour voir si un user matche avec ce qui a été donné*/
		//Encoder tous les params de la requête
		String urlParameters = Utils.usagerIdParam+"=" + URLEncoder.encode(userID, "UTF-8") 
		+ "&"+ Utils.usagerNomParam+"=" + URLEncoder.encode(param_username, "UTF-8") 
		+ "&"+ Utils.usagerPasswordParam+"=" + URLEncoder.encode(param_password, "UTF-8");
		
		System.out.println("CREATEUSER: urlParam - " + urlParameters);
		//Encoder l'URL - doit inclure le port & le context de la requête 
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.updateUsagerURI;

		
		//TODO: science de la comparaison des string en java... ma sinon ça marche.
		//Found it: there was a /r (line return) in the response...
		String response = executePost(targetURL, urlParameters);
		//REMOVE THE SPACES & LINE RETURN!!!!
		response = response.trim();
		System.out.println("CREATEUSER: server response - " + response);

		return response.equals(Utils.OK);
	}
	
	public static int createSalle(String salleNom, String description, String userId)throws UnsupportedEncodingException {
		/*Creer une salle*/
		String urlParameters = Utils.salleNomParam+"=" + URLEncoder.encode(salleNom, "UTF-8") 
		+ "&"+Utils.salleDescriptionParam+"=" + URLEncoder.encode(description, "UTF-8");
		
		System.out.println("CREATESALLE: urlParam - " + urlParameters);
		//Encoder l'URL - doit inclure le port & le context de la requête 
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.creationSalleURI;

		
		//TODO: verifier si une salle existe déjà meme nom
		String response = executePost(targetURL, urlParameters);
		//REMOVE THE SPACES & LINE RETURN!!!!
		response = response.trim();
		String errorCode = response.substring(0, 3);
		String salleInfo = response.substring(3, response.length());
		
		System.out.println("CREATESALLE: server response - " + response);
		
		if (errorCode.equals(Utils.OK))
		{
			JsonParser parser = new JsonParser();
			JsonObject json = parser.parse(salleInfo).getAsJsonObject();
			String salleId = json.get("id").toString();
			
			if (Requests.suscribeUsager(userId, salleId))
				return Integer.parseInt(salleId);
		}

		return -1;
	}
	
	public static boolean suscribeUsager(String userId, String salleId)throws UnsupportedEncodingException {
		/*Suscribe user to salle*/
		String urlParameters = Utils.usagerIdParam+"=" + URLEncoder.encode(userId, "UTF-8") 
		+ "&"+Utils.salleIdParam+"=" + URLEncoder.encode(salleId, "UTF-8");
		
		System.out.println("SUSC: urlParam - " + urlParameters);
		//Encoder l'URL - doit inclure le port & le context de la requête 
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.suscribeUsagerURI;

		String response = executePost(targetURL, urlParameters);
		//REMOVE THE SPACES & LINE RETURN!!!!
		response = response.trim();
		String errorCode = response.substring(0, 3);
		String otherInfo = response.substring(3, response.length());
		
		System.out.println("SUSC: server response - " + response);

		return errorCode.equals(Utils.OK);
	}
	
	public static boolean unsuscribeUsager(String userId, String salleId)throws UnsupportedEncodingException {
		/*UnsSuscribe user to salle*/
		String urlParameters = Utils.usagerIdParam+"=" + URLEncoder.encode(userId, "UTF-8") 
		+ "&"+Utils.salleIdParam+"=" + URLEncoder.encode(salleId, "UTF-8");
		
		System.out.println("UNSUSC: urlParam - " + urlParameters);
		//Encoder l'URL - doit inclure le port & le context de la requête 
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.unsubscribeUsagerURI;

		String response = executePost(targetURL, urlParameters);
		//REMOVE THE SPACES & LINE RETURN!!!!
		response = response.trim();
		System.out.println("UNSUSC: server response - " + response);

		return response.equals(Utils.OK);
	}
	
	public static boolean deleteMessage(String messageId, String salleId)throws UnsupportedEncodingException {
		/*UnsSuscribe user to salle*/
		String urlParameters = Utils.msgIdParam +"=" + URLEncoder.encode(messageId, "UTF-8") 
				+ "&" + Utils.salleIdParam + "=" + URLEncoder.encode(salleId, "UTF-8");
		
		System.out.println("DELMSG: urlParam - " + urlParameters);
		//Encoder l'URL - doit inclure le port & le context de la requête 
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.deleteMessageURI;

		String response = executePost(targetURL, urlParameters);
		//REMOVE THE SPACES & LINE RETURN!!!!
		response = response.trim();
		System.out.println("DELMSG: server response - " + response);

		return response.equals(Utils.OK);
	}
	
	
	
	//#########################
	//##################### END LIST OF REQUESTS FOR EACH ENDPOINTS

	
	public static String executePost(String targetURL, String urlParameters) {
		/*TODO: check that this works
		 * From SO: 1359689 & Oracla java docs with modifs
		 * */
		  HttpURLConnection connection = null;
		  try {
		    //Create connection
		    URL url = new URL(targetURL);
		    connection = (HttpURLConnection) url.openConnection();
		    connection.setRequestMethod("POST");
		    //connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

		    connection.setRequestProperty("Content-Length", 
		        Integer.toString(urlParameters.getBytes().length));
		    connection.setRequestProperty("Content-Language", "en-US");  
		    connection.setUseCaches(false);
		    connection.setDoOutput(true);


		    DataOutputStream wr = new DataOutputStream (
		        connection.getOutputStream());
		    wr.writeBytes(urlParameters);
		    wr.close();

		    //Get Response  
		    InputStream is = connection.getInputStream();
		    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		    StringBuffer response = new StringBuffer(); 
		    String line;
		    while ((line = rd.readLine()) != null) {
		      response.append(line);
		      response.append('\r');
		    }

		    rd.close();
		    return response.toString();
		  } catch (Exception e) {
		    e.printStackTrace();
		    return null;
		  } finally {
		    if (connection != null) {
		      connection.disconnect();
		    }
		  }
}




	
	
	
}
