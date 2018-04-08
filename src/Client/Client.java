package Client;

import java.io.*;
import java.net.*;
import java.util.*;

import javax.json.JsonArray;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import Serveur.Requests;
import Serveur.Requests.RequestType;
import Structures.JsonHandler;
import Structures.Message;
import Structures.User;
import Structures.Utils;

public class Client extends Thread {
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


	public Client(int portServerUDP, int portServerTCP) {
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
		
		
		String jsonData = Requests.initClient();

			
		if (jsonData==Utils.ERR_NO_DATA || jsonData==Utils.ERR_REFUSED_LOGGIN) {
			System.out.println("No data:" + jsonData.toString());
		} else {	
			
			//Get a list out of the string
			List<String> jsonStrings = new ArrayList<String>(Arrays.asList(jsonData.split("#"))) ;
			
			for (String user : jsonStrings) {
				usagers.add(JsonHandler.userFromString(user));
			}

			
			
			User u = JsonHandler.userFromString(jsonData);
			/*
			JsonParser par = new JsonParser();
			com.google.gson.JsonArray arr = par.parse(jsonData).getAsJsonArray();
			ArrayList<User> usagers = new ArrayList<>();
			for (JsonElement je: arr) {
				User u = JsonHandler.userFromString(je.getAsString());
				usagers.add(u);
				
			}
*/
			System.out.println("INITCLT: User list:" + usagers.toString());
			}

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

}