package Serveur;


import com.google.gson.JsonObject;


public class AuthentificationUser extends Requests {

	private String username = null;
	private String password = null;
	
	public AuthentificationUser(JsonObject json) {
		super(RequestType.AUTHETIFIER_UTILISATEUR);
		this.username = json.get("username").getAsString();
		this.password = json.get("password").getAsString();
	}
	
	public AuthentificationUser(String username, String password) {
		super(RequestType.AUTHETIFIER_UTILISATEUR);
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setUsername(String username) {
		this.username=username;
	}
	
	public void setPassword(String password) {
		this.password= password;
	}
	
	@Override
	public String toJsonString() {
		JsonObject json = new JsonObject();
		json.addProperty("type", type.name());
		json.addProperty("username", username);
		json.addProperty("password", password);
		return json.toString();
	}
	
	
	
}
