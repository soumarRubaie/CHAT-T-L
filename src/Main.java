import java.io.IOException;
import com.google.gson.JsonObject;

import Serveur.AuthentificationUser;
import Serveur.Requests;
import Serveur.Requests.RequestType;
import jdk.nashorn.internal.ir.RuntimeNode.Request;



public class Main {

	private static Main instance;
	public static boolean arret_Serveur = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JsonObject obj = new JsonObject();	
		obj.addProperty("username", "soumar");
		obj.addProperty("password", "password");
		
		Requests requests= new AuthentificationUser(obj); 
		
		System.out.println(requests.getType()); 

	}

}
