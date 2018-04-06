package Structures;

import java.util.ArrayList;
import java.util.Iterator;

import Structures.Salle;

public class User {

	int id;
	String username;
	String password;
	ArrayList<Salle> sallesSuscribed;
	boolean isConnected;  
	
	//Création d'un nouveau user
	public User(String username, String password, int id) {
		this.password = password;
		this.username = username;
		this.id = id;
	}

	public boolean isConnected() {
		return isConnected;
	}

	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	// Se desabonner d'une salle
	public boolean seDesabonner(int idSalle){
		Iterator itr= sallesSuscribed.iterator(); 
		
		while(itr.hasNext()){  
			 Salle salle = (Salle)itr.next();
			 if (salle.getId() == idSalle){
				 itr.remove();		 
				 return true; 
			 }
		}  
		return false; 
	}
	
	//Récupération d'un user déjà créé
	public User(String username, String password, int id, ArrayList<Salle> sallesSuscribed) {
		this.password = password;
		this.username = username;
		this.id = id;
		this.sallesSuscribed = sallesSuscribed;
	}

	public String toString() {
		return "Username: " + username + " Password: " + password + " Id:" + id;
	}

	//Getters et Setters 
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ArrayList<Salle> getSallesSuscribed() {
		return sallesSuscribed;
	}

	public void setSallesSuscribed(ArrayList<Salle> sallesSuscribed) {
		this.sallesSuscribed = sallesSuscribed;
	}
}