package Serveur;


import com.google.gson.JsonObject;


public class AuthentificationUser {

	private String username = null;
	private String password = null;

	
	public AuthentificationUser(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public boolean checkAuth(String username, String password) {
		/*Checks if provided auth matches this user*/
		
		return this.username.equals(username) && this.password.equals(password);
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
	
}
