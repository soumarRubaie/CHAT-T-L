package Serveur;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Structures.Utils;

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
		CREER_USAGER,
		
		//Server requests
		AUTHENTICATE_USER_RESPONSE,
		CREER_SALLE_DISCUSSION_RESPONSE,
		JOIN_ROOM_RESPONSE,
		UPDATE_USER,
		UPDATE_ROOM,
		UPDATE_MESSAGE,
		LOGOUT_RESPONSE
	}
	
	public static boolean athenticateUser(String username, String password) throws UnsupportedEncodingException {
		/*Envoie une requête au serveur pour voir si un user matche avec ce qui a été donné*/
		System.out.println("auth 1");
		//Encoder tous les params de la requête
		String urlParameters = Utils.usagerNomParam+"=" + URLEncoder.encode(username, "UTF-8") 
		+ "&"+Utils.usagerPasswordParam+"=" + URLEncoder.encode(password, "UTF-8");
		
		System.out.println("urlParam:" + urlParameters);
		//Encoder l'URL - doit inclure le port & le context de la requête 
		String targetURL = Utils.serverURLNoPort + Utils.tcpPort + Utils.authUser;

		
		//TODO: science de la comparaison des string en java... ma sinon ça marche.
		String response = executePost(targetURL, urlParameters);
		String dum = "true";
		System.out.println(response);
		System.out.println("comparaison: "+response.length() + " " + "true".length());
		return response.equals(dum);
	}
	
	

	public Requests(RequestType type) {
		this.type = type;
	}
	
	public RequestType getType() {
		return type;
	}
	
	

	public static void parseRequest(Object reqObj) {
		/***
		 * paser afin de lire le request type
		 */
		
		/* 
		Requests request = null;
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(jsonString).getAsJsonObject();
		String type = json.get("type").getAsString().toUpperCase();
		*/
		/*
		switch(RequestType.valueOf(type)) {
			case AUTHETIFIER_UTILISATEUR:
				request = new AuthentificationUser(json);
				break;
		
			case CREER_SALLE_DISCUSSION:
				break;
				
			case CREER_SALLE_DISCUSSION_RESPONSE:
				break;
		
			case JOIN_ROOM:
				break;
				
			case ECRIRE_MESSAGE:
				break;
		
			case SUPPRIMER_MESSAGE:
				break;
				
				
			case LOGOUT:
				break;
				
			default:
				System.out.println("WHAT ZE PHOQUE IS ZIS REQUETEUH? ");
		}
		return request;privatee
		*/
	}

	public abstract String toJsonString();
	
	
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

		    //Send request
//		    System.out.println("Sending, URL:" + targetURL);
//		    System.out.println("Sending, params:" + urlParameters);

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
