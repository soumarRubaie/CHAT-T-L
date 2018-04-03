package Structures;

public class Message {
	int idMessage;
	int contenuMessage;
	int idSalle;
	int idUtilisateur;
	
	//Créer un message
	public Message(int idMessage, int contenuMessage, int idSalle, int idUtilisateur) {
		this.idMessage = idMessage;
		this.contenuMessage = contenuMessage;
		this.idSalle = idSalle;
		this.idUtilisateur = idUtilisateur;
	}

	
	//Getters et Setters
	public int getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
	}

	public int getContenuMessage() {
		return contenuMessage;
	}

	public void setContenuMessage(int contenuMessage) {
		this.contenuMessage = contenuMessage;
	}

	public int getIdSalle() {
		return idSalle;
	}

	public void setIdSalle(int idSalle) {
		this.idSalle = idSalle;
	}

	public int getIdUtilisateur() {
		return idUtilisateur;
	}

	public void setIdUtilisateur(int idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
	}

}
