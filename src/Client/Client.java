package Client;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.json.JsonArray;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Serveur.Requests;
import Serveur.SocketTCP;
import Serveur.Requests.RequestType;
import Structures.JsonHandler;
import Structures.Message;
import Structures.Salle;
import Structures.User;
import Structures.Utils;

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
	
	List<User> usagers = new ArrayList<User>();
	List<Salle> salles = new ArrayList<>();


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
		
	}

	private void initClient() throws UnsupportedEncodingException {
		// TODO Checker que jsonData!=null (e.g. empty DB)
		usagers = getUsersFromServer();
		salles = getSallesFromServer();
		
	}
	private List<User> getUsersFromServer() throws UnsupportedEncodingException {
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
			System.out.println("INITCLT: User list:" + usagers.toString());
			}
		return dum;
	}
	private List<Salle> getSallesFromServer() throws UnsupportedEncodingException {
		String jsonData = Requests.getSallesFromServer();
		List<Salle> dum = new ArrayList<>();

		if (jsonData==Utils.ERR_NO_DATA || jsonData==Utils.ERR_REFUSED_LOGGIN) {
			System.out.println("No data:" + jsonData.toString());
		} else {	
			
			//Get a list out of the string
			List<String> jsonStrings = new ArrayList<String>(Arrays.asList(jsonData.split(Utils.jsonarrayStringSeparator))) ;
			for (String salle : jsonStrings) {
				dum.add(JsonHandler.salleFromString(salle));
			}
			System.out.println("INITCLT: User list:" + usagers.toString());
			}
		return dum;
	}

	public void sendMsg(String strMessage) {
		byte[] buf = new byte[Utils.datagrameSizeBytes];
		//lire l'input du clavier
		Scanner scan = new Scanner(System.in);
		String myLine = scan.nextLine();
		
		//Construire un obj message avec le string & ids etc et ensuite un byte[] pour l'envoie avec toutes ces infos
		Message msg = build_msg(myLine);
		try {
			buf = msg.toBytes();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			socket.close();


		}
		System.out.println("Msg AUTHETIFIER_UTILISATEURorigine: " + msg.toString());

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
		System.out.println("From server:" + msg.toString());

	//Would normally close the socket at some point
	//socket.close();

	}

	public Message build_msg(String content) {

		return new Message(msgId, content, salleId, userId);
	}

	// Getters & setters

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
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