package Structures;

import java.util.ArrayList;

import Structures.Message;
import Structures.User;

public class Salle {
	String salleNom;
	int id;
	String Description;
	ArrayList<User> suscribersList;
	ArrayList<Message> messagesList;

	// Création d'une salle
	public Salle(String salleNom, int id, String description) {
		this.salleNom = salleNom;
		this.id = id;
		Description = description;
	}

	@Override
	public String toString() {
		return "SalleNom: " + salleNom + ", id: " + id + ", Description: " + Description + ", suscribersList: "
				+ suscribersList + ", messagesList: " + messagesList +"";
	}

	// Récupération d'une salle déjà créée
	public Salle(String salleNom, int id, String description, ArrayList<User> suscribersList,
			ArrayList<Message> messagesList) {
		this.salleNom = salleNom;
		this.id = id;
		Description = description;
		this.suscribersList = suscribersList;
		this.messagesList = messagesList;
	}

	// Getters et setters
	public String getSalleNom() {
		return salleNom;
	}

	public void setSalleNom(String salleNom) {
		this.salleNom = salleNom;
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
