package Structures;

import java.util.ArrayList;
import java.util.Iterator;

import Structures.Message;
import Structures.User;

public class Salle {
	String salleNom;
	int id;
	String description;
	ArrayList<User> suscribersList = new ArrayList<User>();
	ArrayList<Message> messagesList = new ArrayList<Message>();
	
	
	
	// Création d'une salle
	public Salle(String salleNom, int id, String description) {
		this.salleNom = salleNom;
		this.id = id;
		this.description = description;
	}
	
	public boolean estVide(){
		return suscribersList.isEmpty(); 
	}
	
	@Override
	public String toString() {
		return "SalleNom: " + salleNom + ", id: " + id + ", Description: " + description + ", suscribersList: "
				+ suscribersList + ", messagesList: " + messagesList +"";
	}

	// Récupération d'une salle déjà créée
	public Salle(String salleNom, int id, String description, ArrayList<User> suscribersList,
			ArrayList<Message> messagesList) {
		this.salleNom = salleNom;
		this.id = id;
		this.description = description;
		this.suscribersList = suscribersList;
		this.messagesList = messagesList;
	}
	
	
	
	public void addSubscriber(User u) {
		for(int i = 0; i<suscribersList.size(); i++) {
			if (suscribersList.get(i).getId() == u.getId()) {
				System.out.println("Utilisateur " + u.getId() + " déjà abonné à la salle n°" + id);
				return;
			}
		}
		suscribersList.add(u);
		System.out.println("Ajout de l'utilisateur " + u.getId() + " à la salle n°" + id);
		
	}
	
	public void addMessage(Message msg) {
		messagesList.add(msg);
		System.out.println("Ajout du message " + msg.getIdMessage() + " à la salle n°" + id);
	}

	// Getters et setters
	public String getSalleNom() {
		return salleNom;
	}

	public void setSalleNom(String salleNom) {
		this.salleNom = salleNom;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<User> getSuscribersList() {
		return suscribersList;
	}

	public void setSuscribersList(ArrayList<User> suscribersList) {
		this.suscribersList = suscribersList;
	}

	public ArrayList<Message> getMessagesList() {
		return messagesList;
	}

	public void setMessagesList(ArrayList<Message> messagesList) {
		this.messagesList = messagesList;
	}
}
