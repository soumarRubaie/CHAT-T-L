package Structures;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class Message {
	int idMessage;
	String contenuMessage;
	int idSalle;
	int idUtilisateur;

	// Créer un message
	public Message(int idMessage, String contenuMessage, int idSalle, int idUtilisateur) {
		this.idMessage = idMessage;
		this.contenuMessage = contenuMessage;
		this.idSalle = idSalle;
		this.idUtilisateur = idUtilisateur;
	}

	// Reconstruction à partir d'un byte[] reçu. Pourrait être plus intelligent un
	// peu mais ça marche
	public Message(byte[] by) {
		// le byte[] contient 4 section - 3 ids et le message lui-même.
		// on définit les index des limites de chaque block
		int limit1 = Utils.messageIdBytes;
		int limit2 = Utils.authorIdBytes + Utils.messageIdBytes;
		int limit3 = Utils.authorIdBytes + Utils.messageIdBytes + Utils.salleIdBytes;

		// Casser le byte[] en plusieurs sous-byte[] et parser en int, String.
		this.idMessage = Utils.bytesToInt(Arrays.copyOfRange(by, 0, limit1));
		this.idUtilisateur = Utils.bytesToInt(Arrays.copyOfRange(by, limit1, limit2));
		this.idSalle = Utils.bytesToInt(Arrays.copyOfRange(by, limit2, limit3));
		this.contenuMessage = new String(Arrays.copyOfRange(by, limit3, by.length));
	}

	public byte[] toBytes() throws IOException {
		/* Convertir tout le msg en bytes pour envoie */
		byte byteRep[] = merge4Arrays(Utils.intTo4Bytes(idMessage), Utils.intTo4Bytes(idUtilisateur),
				Utils.intTo4Bytes(idSalle), contenuMessage.getBytes());

		return byteRep;
	}

	public byte[] merge4Arrays(byte[] a, byte[] b, byte[] c, byte[] d) throws IOException {
		/*
		 * Un peu paresseux, devrait être itératif pour être plus général... si on
		 * change pas les headers c'Est ok Sinon faudrait ajouter des
		 * outputStream.write() si on veut plus de 4 arguemnts
		 */
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(a);
		outputStream.write(b);
		outputStream.write(c);
		outputStream.write(d);
		byte e[] = outputStream.toByteArray();
		return e;
	}

	// Getters et Setters
	public int getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(int idMessage) {
		this.idMessage = idMessage;
	}

	public String getContenuMessage() {
		return contenuMessage;
	}

	public void setContenuMessage(String contenuMessage) {
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

	@Override
	public String toString() {
		return "{idMessage: " + idMessage + ", contenu: " + contenuMessage.trim() + ", idSalle: " + idSalle
				+ ", idUtilisateur: " + idUtilisateur+"}";
	}

	public String toJsonFormat() {
		String msgJson;

		
		msgJson = 
				"{\"idMessage\":" + idMessage + 
				",\"idUtilisateur\":" + idUtilisateur + 
				",\"contenuMessage\":\""+ contenuMessage.trim() + 
				"\",\"idSalle\":" + idSalle + "}";
		return msgJson;
	}

}
