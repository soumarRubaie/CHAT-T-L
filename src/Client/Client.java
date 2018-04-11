package Client;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.swing.JFrame;

import Serveur.Requests;
import Serveur.SocketTCP;

import Structures.JsonHandler;
import Structures.Message;
import Structures.Salle;
import Structures.UpdateInterval;
import Structures.User;
import Structures.Utils;
import jdk.nashorn.internal.ir.RuntimeNode.Request;

public class Client extends Thread {
	// ############## Gestion du singleton
	private static Client instance;

	public static Client getInstance(int portServerUDP, int portServerTCP) {
		if (instance == null) {
			instance = new Client(portServerUDP, portServerTCP);
			return instance;
		}
		return instance;
	}
	
	public static Client getInstance() {
		//Cette metho suppose que le client a été initéi - devrait toujours être le cas sauf au démarrage,
		//qui utilise l'autre getisntance
		return instance;
	}
	
	/* Il faudra redéfinir cela mieux/ailleurs mais pour fins de tests */
	int userId = 0;
	int salleId = 0;
	int msgId = 999999; // default pour les msg - sera updaté par le serveur lors de l'arrivée
	int portUDP;
	int portTCP;

	InetAddress address = null;

	// Définit un socket,
	DatagramSocket socket = null;
	List<User> connectedUsers = new ArrayList<User>();
	List<User> usagers = new ArrayList<User>();
	List<Salle> salles = new ArrayList<>();
	
	User currentUser;
	Salle currentSalle;
	Thread thread ;
	
	// Control the UI update
	JFrame currentPage;
	

	private Client(int portServerUDP, int portServerTCP) {
		Utils.tcpPort = portServerTCP;
		Utils.udpPort = portServerUDP;
		portUDP = portServerUDP;
		portTCP = portServerTCP;

		// Démarrage du socketUDP pour le client
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			socket.close();

		}
		try {
			address = InetAddress.getByName(Utils.serverName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			socket.close();
		}

	}


	public void run() {

		// Démarrer la page Login pour authentification
		try {
			initClient();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoginPage lp = new LoginPage();
		currentPage = lp; 
	};

	//############# UPDATE RELATED METHOS
	private void initClient() throws UnsupportedEncodingException {
		// TODO Checker que jsonData!=null (e.g. empty DB)
		
		usagers = getUsersFromServer();
		salles = getSallesFromServer();

		UpdateInterval ui = new UpdateInterval(this);
		Thread test = new Thread(new UpdateInterval(this));
		test.start();
		
		System.out.println("INITCLT: User list:" + usagers.toString());
		System.out.println("INITCLT: salle list:" + salles.toString());
	}
	
	public void updateClientLists() throws UnsupportedEncodingException {
		/*Quand on créer une salle, usager on veut udpate les listes*/
		udapteConnectedUserList();
		usagers = getUsersFromServer();
		salles = getSallesFromServer();
		
		updateCurrentSalle();
		updateCurrentUser(); 
	
	} 
	
	public void udapteConnectedUserList() {
		for (User user : connectedUsers) {
			user.decreaseDisconectNumber();
			if (user.getDisconnectNumber()<1) {
				connectedUsers.remove(user);
			}
		}
	}

	public void updateCurrentSalle() throws UnsupportedEncodingException {
		if (currentSalle!=null) {
			setCurrentSalle(currentSalle.getId());
			SallePage.updateSalle();
		} 
		
		if (currentPage!=null) {
			if (currentPage instanceof Home) {
				Home home = (Home) currentPage;
				home.Update();
			}
		} 
		
		if (currentUser!=null) {
			setCurrentUser(currentUser.getUsername());
		} 
		/*
		if (currentSalle!=null && currentUser!=null) {
			String resp = Requests.GetArchives(currentUser.getId(), currentSalle.getId());
			System.out.println("getarchives:" + resp);
		}
	*/
	}
	public void updateCurrentUser() throws UnsupportedEncodingException {
		if (currentUser!=null) {
			setCurrentUser(currentUser.getUsername());
		} 
	}
	
	
	//############# END UPDATE RELATED METHOS

	
	public String[] getSalleLabels() {
		ArrayList<String> dum = new ArrayList<String>();
		for (Salle s : getSalles()) {
			if (s!=null) {
				dum.add(s.getId() + "_" + s.getSalleNom());
			}
		}
		String salleLabels[] = new String[dum.size()];
		return dum.toArray(salleLabels);
	}
	
	public String[] getUserLabels() {
		ArrayList<String> dum = new ArrayList<String>();
		for (User s : getUsagers()) {
			if (s!=null) {
				dum.add(s.getId() + "_" + s.getUsername());
			}
		}
		String userLabels[] = new String[dum.size()];
		return dum.toArray(userLabels);
	}
	
	public String[] getConnectedUserLabels() {
		ArrayList<String> dum = new ArrayList<String>();
		for (User s : currentSalle.getSuscribersList()) {
			if (s!=null) {
				dum.add(s.getId() + "_" + s.getUsername());
			}
		}
		String userLabels[] = new String[dum.size()];
		return dum.toArray(userLabels);
	}
	
	public List<User> getUsersFromServer() throws UnsupportedEncodingException {
		String jsonData = Requests.getUsersFromServer();
		List<User> dum = new ArrayList<User>();
		if (jsonData==Utils.ERR_NO_DATA || jsonData==Utils.ERR_REFUSED_LOGGIN) {
			System.out.println("No data:" + jsonData.toString());
		} else {	
			
			//Get a list out of the string
			List<String> jsonStrings = new ArrayList<String>(Arrays.asList(jsonData.split(Utils.jsonarrayStringSeparator))) ;
			for (String user : jsonStrings) {
				dum.add(JsonHandler.userFromString(user));
			}
			}
		return dum;
	}
	
	public List<User> getConnectedUsersFromServer() throws UnsupportedEncodingException {
		String jsonData = Requests.getConnectedUsersFromServer();
		List<User> dum = new ArrayList<User>();
		
		try {
			JsonReader jsonReader = Json.createReader(new StringReader(jsonData));
			JsonObject json = jsonReader.readObject();
			jsonReader.close();
			
			JsonArray users = json.getJsonArray("connectedUsers");
			for (int i = 0; i < users.size(); i++) {
			    JsonObject obj = users.getJsonObject(i);
			    dum.add(JsonHandler.userFromJsonObject(obj));
			}
		} catch (JsonException e) {
			e.printStackTrace();
		}
		return dum;
	}
	
	public List<Salle> getSallesFromServer() throws UnsupportedEncodingException {
		String jsonData = Requests.getSallesFromServer();
		List<Salle> dum = new ArrayList<>();

		if (jsonData==Utils.ERR_NO_DATA || jsonData==Utils.ERR_REFUSED_LOGGIN || jsonData=="") {
			System.out.println("No data:" + jsonData.toString());
		} else {	
			
			//Get a list out of the string
			List<String> jsonStrings = new ArrayList<String>(Arrays.asList(jsonData.split(Utils.jsonarrayStringSeparator))) ;
			
			for (String salle : jsonStrings) {

			if (salle!="") {
					dum.add(JsonHandler.salleFromString(salle));
				} 
			}
			}
		return dum;
	}

	public void sendMsg(String strMessage) {
		byte[] buf = new byte[Utils.datagrameSizeBytes];
		
		//Construire un obj message avec le string & ids etc et ensuite un byte[] pour l'envoie avec toutes ces infos
		Message msg = build_msg(strMessage);
		try {
			buf = msg.toBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			socket.close();


		}

		DatagramPacket outpacket = new DatagramPacket(buf, buf.length, address, portUDP);
		try {
			socket.send(outpacket);
		} catch (IOException e) {
			e.printStackTrace();
			socket.close();
		}
		
		//processus inverse
		DatagramPacket inpacket = new DatagramPacket(new byte[Utils.datagrameSizeBytes], Utils.datagrameSizeBytes);
		try {
			socket.receive(inpacket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			socket.close();

		}
		
		//DEBUG: display response
		msg = new Message(inpacket.getData());

	//Would normally close the socket at some point
	//socket.close();

	}

	public Message build_msg(String content) {

		return new Message(msgId, content, currentSalle.getId(), currentUser.getId());
	}

	// Getters & setters

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	public void setCurrentUser(String username) {
		for (User u : usagers) {
			if (u.getUsername().equals(username)) {
				currentUser = u;
			}
		}
	} 
	
	public void setCurrentSalle(int salleId) {
		for (Salle u : salles) {
			if (salleId==u.getId()) {
				currentSalle= u;
			}
		}
	}
	
	public void goToAnotherPage(JFrame newPage) {
		newPage.setVisible(true);
		
		if (currentPage != null)
			currentPage.dispose();
		
		currentPage = newPage;
	}
	
	public void setNoSalle() {
		currentSalle = null;
	}
	
	public void suscribeUserToSalle(int salleIdAJoindre, int userId) throws UnsupportedEncodingException {
		Requests.suscribeUser(salleIdAJoindre, userId);
	}
	
	public void unsuscribeUserToSalle(int salleIdAJoindre, int userId) throws UnsupportedEncodingException {
		Requests.unsuscribeUser(salleIdAJoindre, userId);
	}
	
	public User getCurrentUser() {
		return currentUser;
	}

	public Salle getCurrentSalle() {
		return currentSalle;
	}

	public String getSignatureForMessage() {
		return currentUser.getSignatureForMessage();
	}

	public int getSalleId() {
		return salleId;
	}

	public void setSalleId(int salleId) {
		this.salleId = salleId;
	}


	public List<User> getUsagers() {
		return usagers;
	}


	public List<Salle> getSalles() {
		return salles;
	}

	
}