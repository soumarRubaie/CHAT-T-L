
import Structures.UpdateInterval;
import Structures.Utils;
import Client.Client;
import Serveur.SocketTCP;
import Serveur.SocketUDP;

public class Main {

	public static boolean arret_Serveur = false;

	// Ports (défaut dans Utils, sinon spécifiés par ligne de command
	public static void main(String[] args) throws Exception {
		if (args.length != 3) {
			System.out.println("Usage:  [serveur|client] int tpcport int udpport");
		} 
		else {
			int portServerTCP = Integer.parseInt(args[1]);
			int portServerUDP = Integer.parseInt(args[2]);
			
			if (args[0].equals(Utils.CLIENT)) {
				// we start the client
				System.out.println("Démarrage client UDP. PortUDP (serveur):" + portServerUDP);
				Client.getInstance(portServerUDP, portServerTCP).start();

			} else if (args[0].equals(Utils.SERVER)) {
				// we start the server

				// Démarrage du thread UDP -
				System.out.println("Demarrage thread TCP...");
				// TCP est un singleton, d'ou le getInstance()
				SocketTCP.getInstance("tcpHost", portServerUDP, portServerTCP).start();

				// Démarrage du thread UDP
				System.out.println("Demarrage thread UDP...");
				new SocketUDP("udpHost", portServerUDP, portServerTCP).start();

			} else {
				// wrong arg
				System.out.println("Mauvais argument. Choix: " + Utils.CLIENT + " " + Utils.SERVER);
			}

		}

	}

}
