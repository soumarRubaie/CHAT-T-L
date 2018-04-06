package Client;
import java.io.*;
import java.net.*;
import java.util.*;

import Structures.Message;
import Structures.Utils;


public class Client extends Thread {
	/*Il faudra redéfinir cela mieux/ailleurs mais pour fins de tests*/
	int userId = 999;
	int salleId = 1;
	int msgId=99;
	int port = Utils.udpPort;

	public Client(int portServerUDP) {
		port = portServerUDP;
	}


	public void run() {
		
		//Définit un socket, 
		DatagramSocket socket = null;
		try {
			socket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
			socket.close();

		}
		InetAddress address = null;
		try {
			address = InetAddress.getByName(Utils.serverName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
			socket.close();
		}
		
		//Démarrer la page Login pour authentification
		LoginPage lp = new LoginPage();
		
		//lire input du keyboard (ligne par line). Kill avec ctrl-c
		while (true) {
			
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
			System.out.println("Msg origine: " + msg.toString());

			DatagramPacket outpacket = new DatagramPacket(buf, buf.length, address, port);
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

		}
		//Would normally close the socket at some point
		//socket.close();
	}

	
	public Message build_msg(String content) {
		
		return new Message(msgId, content, salleId, userId);

	}


}