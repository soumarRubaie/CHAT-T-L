package Structures;

import java.util.ArrayList;
import java.util.Iterator;

import Structures.Message;
import Structures.User;

public class Salle  implements JsonUtils {
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
	
	public void deleteSubscriber(int idUser) {
		for(int i = 0; i<suscribersList.size(); i++) {
			if (suscribersList.get(i).getId() == idUser) {
				suscribersList.remove(i);
				System.out.println("Utilisateur n°" + idUser + " suprimé de la salle" + id);
				return;
			}
		}	
	}
	
	public void addMessage(Message msg) {
		msg.setIdMessage(messagesList.size());
		messagesList.add(msg);
		System.out.println("Ajout du message " + msg.getIdMessage() + " à la salle n°" + id);
	}
	
	public int deleteMessage(int idMsg) {
		//0 => Pas d'erreur
		//1 => Message introuvable
		
		for(int i = 0; i<messagesList.size(); i++) {
			if(messagesList.get(i).getIdMessage() == idMsg) {
				messagesList.remove(i);
				return 0;
			}
		}
		return 1;
	}

	public boolean isSubscribed(int idUser) {
		for(int i = 0; i<suscribersList.size(); i++) {
			if (suscribersList.get(i).getId() == idUser) {
				System.out.println("Utilisateur n°" + idUser + " abonné à la salle" + id);
				return true;
			}
		}	
		return false;
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
	
	public String messagesToJsonFormat() {
		boolean first = true;
		String jsonMessages="[";
		for(Message msg: messagesList) {
			if(!first) {
				jsonMessages += ",";
			} else {
				first = false;
			}
			jsonMessages += msg.toJsonFormat();
			
		}
		jsonMessages += "]";
		return jsonMessages;
	}
	
	public String salleToJsonFormat() {
		boolean first = true;
		String jsonUsers="[";
		for(User u: suscribersList) {
			if(!first) {
				jsonUsers += ",";
			} else {
				first = false;
			}
			jsonUsers += u.toJsonFormat();
			
		}
		jsonUsers += "]";
		return jsonUsers;
	}
	
	public String toJsonFormat() {
		String jsonSalle;
		jsonSalle = "{\"salleNom\":\""+ salleNom + "\",";
		jsonSalle += "\"id\":"+ id + ",";
		jsonSalle += "\"description\":\""+ description + "\",";
		jsonSalle += "\"suscribersList\":"+ salleToJsonFormat() + ",";
		jsonSalle += "\"messagesList\":"+ messagesToJsonFormat() + "}";
		
		return jsonSalle;
	}
}
