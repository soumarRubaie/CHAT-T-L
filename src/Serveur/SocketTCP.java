package Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import Structures.Message;
import Structures.Salle;
import Structures.User;
import Structures.Utils;

public class SocketTCP extends Thread {

	// ############## Gestion du singleton
	private static SocketTCP instance;

	public static SocketTCP getInstance(String name, int portUDP, int portTCP) throws IOException {
		// Probablement destiné à partir, mais pour démo ça va
		if (instance == null) {
			instance = new SocketTCP(name, portTCP);
			return instance;
		}
		return instance;
	}

	// ####################### endpoint et params ##########
	//####### IMPORTANT: mettre ces infos dans UTILS car le client en a aussi besoin pour les requêtes...
	// Endpoint: creation salle
	private static String creationSalleURI = Utils.creationSalleURI;
	private static String salleNomParam = Utils.salleNomParam;

	// Endpoint: creation usager
	private static String creationUsagerURI = Utils.creationUsagerURI;
	private static String usagerNomParam = Utils.usagerNomParam;
	private static String usagerPasswordParam = Utils.usagerPasswordParam;

	// Endpoint: suscriber un usager a une salle
	private static String suscribeUsagerURI = Utils.suscribeUsagerURI;
	private static String usagerIdParam = Utils.usagerIdParam;
	private static String salleIdParam = Utils.salleIdParam;

	// Endpoint: delete msg
	private static String deleteMessageURI = Utils.deleteMessageURI;
	private static String msgIdParam = Utils.msgIdParam;

	// Endpoint: autres
	private static String getArchiveURI = Utils.getArchiveURI;
	private static String getConnectedUsersURI = Utils.getConnectedUsersURI;
	private static String unsubscribeUsagerURI = Utils.unsubscribeUsagerURI;
	private static String authUser = Utils.authUser;

	// ####################### FIN endpoint et param ###########

	// ####################### Serveur addresse & port
	private HttpServer serveur = null;
	private static int serverIP = 0; // roule en localhost
	private static int inetSocketPort = 8000;
	// ####################### FIN Serveur addresse & port

	// ####################### Listes pour gestion des objets/entités
	private List<Salle> salles = new ArrayList<>();
	private List<User> usagers = new ArrayList<>();
	// ####################### FIN Listes obj

	// ####################### Autres attributs
	public static String lineReturn = System.lineSeparator();

	private SocketTCP(String name, int portTCP) throws IOException {
		super(name);
		inetSocketPort = portTCP;
		serveur = HttpServer.create(new InetSocketAddress(inetSocketPort), serverIP);
		System.out.println("server:" + super.getName() + " on port:" + inetSocketPort);
	}

	public void run() {
		// CREATION des contextes - si de nouveaux endpoint sont ajouteré, créer les
		// contextes ici
		// et écrire le handler correspondant plus bas
		serveur.createContext(creationSalleURI, new CreationSalleHandler());
		serveur.createContext(creationUsagerURI, new CreationUsagerHandler());
		serveur.createContext(suscribeUsagerURI, new SuscribeUsagerHandler());
		serveur.createContext(deleteMessageURI, new DeleteMessageHandler());
		serveur.createContext(getArchiveURI, new GetArchiveHandler());
		serveur.createContext(getConnectedUsersURI, new GetConnectedUsersHandler());
		serveur.createContext(unsubscribeUsagerURI, new UnsuscribeUsagerHandler());
		serveur.createContext(authUser, new authenticateUser());

		serveur.setExecutor(null); // Associe un thread par défaut au Serveur
		serveur.start(); // Démarre le serveur

	}

	// ###################### HANDLERS pour endpoints
	// ##################################
	// Chaque handler a une section PRINT, surtout pour afficher à la console pour
	// debug... voir si utile long terme ou non
	// la logique de réponse au browser est probablement pertinente par contre

	class CreationSalleHandler implements HttpHandler {
		// TODO Passer la description de la salle en parametre
		// localhost:inetSocketPort/creationSalle?salleNom=testNom
		@Override
		public void handle(HttpExchange t) throws IOException {

			// Récupération des variables de la requête
			// (salleNom)
			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());

			// Creer la nouvelle salle & attacher au observeur pattern
			initSalle(new Salle(params.get(salleNomParam), getSalleCount(),
					"Description de la salle"));

			// ### REPONSE ###
			String response = "Creation d'une nouvelle salle" + lineReturn + salleNomParam + ": "
					+ params.get(salleNomParam) + lineReturn + "Nombre total de salles: " + getSalleCount();
			response += serveurStateResponse();
			t.sendResponseHeaders(200, response.length());

			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
			// ### FIN REPONSE ###
		}
	}

	class CreationUsagerHandler implements HttpHandler {
		@Override
		// localhost:8000/unsubscribeUsager?userId=1&salleId=1
		public void handle(HttpExchange t) throws IOException {

			// Récupération des variables de la requête
			// (username et password)
			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());

			// Création d'un usager
			// TODO Test sur la présence d'un Usager avec le même nom d'utilisateur dans la
			// BDD

			initUsager(
					new User(params.get(usagerNomParam), params.get(usagerPasswordParam), getUserCount()));

			// ### REPONSE ###
			String response = "Creation d'un nouvel usager: " + lineReturn + "Nom d'utilisateur: "
					+ params.get(usagerNomParam) + lineReturn + "Mot de passe: " + params.get(usagerPasswordParam)
					+ lineReturn + " Nombre total d'usagers: " + getUserCount() + lineReturn;

			String resp = response + serveurStateResponse();
			t.sendResponseHeaders(200, resp.length());
			OutputStream os = t.getResponseBody();
			os.write(resp.getBytes());
			os.close();
			// ### FIN REPONSE ###

		}
	}
	
	class SuscribeUsagerHandler implements HttpHandler {
		// localhost:8000/suscribeUsagerSalle?userId=1&salleId=01
		public void handle(HttpExchange t) throws IOException {

			// Récupération des paramètres:
			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());
			int idUser = Integer.parseInt(params.get(usagerIdParam));
			int idSalle = Integer.parseInt(params.get(salleIdParam));

			String resp = "";

			// ### REPONSE ###
			if (subscribeUser(idSalle, idUser)) {
				String response = "Abonnement utilisateur: " + lineReturn + "Utilisateur " + idUser
						+ " abonne a la salle " + idSalle;
				resp = response + serveurStateResponse();
				t.sendResponseHeaders(200, resp.length());
			} else {
				String response = "ERREUR lors de l'abonnement utilisateur" + lineReturn + "User ou salle non-existants" + lineReturn
						+ "Utilisateur " + idUser + " a la salle " + idSalle + lineReturn;
				resp = response + serveurStateResponse();
				t.sendResponseHeaders(600, resp.length());
			}
			OutputStream os = t.getResponseBody();
			os.write(resp.getBytes());
			os.close();
			// ### FIN REPONSE ###
		}
	}

	class UnsuscribeUsagerHandler implements HttpHandler {
		// localhost:8000/unsubscribeUsager?userId=1&salleId=1
		public void handle(HttpExchange t) throws IOException {

			// Récupération des paramètres:
			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());
			int idUser = Integer.parseInt(params.get(usagerIdParam));
			int idSalle = Integer.parseInt(params.get(salleIdParam));

			String resp = "";
				
			// ### REPONSE ###
			if (unsubscribeUser(idSalle, idUser)) {
				String response = "Desabonnement: " + lineReturn + "Utilisateur " + idUser
						+ " se desabonne de la salle " + idSalle;
				resp = response + serveurStateResponse();
				t.sendResponseHeaders(200, resp.length());
			} else {
				String response = "ERREUR lors du desabonnement!" + lineReturn + "User ou salle non-existants"
						+ lineReturn + "Utilisateur " + idUser + " Salle " + idSalle + lineReturn;
				resp = response + serveurStateResponse();
				t.sendResponseHeaders(600, resp.length());
			}
			OutputStream os = t.getResponseBody();
			os.write(resp.getBytes());
			os.close();
			// ### FIN REPONSE ###
		}
	}

	class DeleteMessageHandler implements HttpHandler {
		// localhost:8000/deleteMessage?userId=1&salleId=1&messageId=1
		public void handle(HttpExchange t) throws IOException {
			// TODO Delete Message
			// Récupération des paramètres:
			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());
			int idUser = Integer.parseInt(params.get(usagerIdParam));
			int idSalle = Integer.parseInt(params.get(salleIdParam));
			int idMsg = Integer.parseInt(params.get(msgIdParam));

			// ### REPONSE ###
			String response = "A implementer (DeleteMessage)" + lineReturn + "id user: " + idUser + lineReturn
					+ "id salle: " + idSalle + lineReturn + "id msg: " + idMsg;
			String resp = response + serveurStateResponse();
			t.sendResponseHeaders(200, resp.length());
			OutputStream os = t.getResponseBody();
			os.write(resp.getBytes());
			os.close();
			// ### FIN REPONSE ###
		}
	}

	class GetArchiveHandler implements HttpHandler {
		// localhost:8000/getArchive?userId=1&salleId=1
		public void handle(HttpExchange t) throws IOException {
			// TODO GetArchive
			// Récupération des paramètres:
			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());
			int idUser = Integer.parseInt(params.get(usagerIdParam));
			int idSalle = Integer.parseInt(params.get(salleIdParam));

			// ### REPONSE ###
			String response = "A implementer (GetArchive)" + lineReturn + "id user: " + idUser + lineReturn
					+ "id salle: " + idSalle;
			String resp = response + serveurStateResponse();
			t.sendResponseHeaders(200, resp.length());
			OutputStream os = t.getResponseBody();
			os.write(resp.getBytes());
			os.close();
			// ### FIN REPONSE ###
		}
	}

	class GetConnectedUsersHandler implements HttpHandler {
		// localhost:8000/getConnectedUsers?userId=1
		public void handle(HttpExchange t) throws IOException {
			// TODO GetConnectedUsers
			// Récupération des paramètres:
			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());
			int idUser = Integer.parseInt(params.get(usagerIdParam));

			// ### REPONSE ###
			String response = "A implementer (GetConnectedUsers)" + lineReturn + "id user: " + idUser;
			String resp = response + serveurStateResponse();
			t.sendResponseHeaders(200, resp.length());
			OutputStream os = t.getResponseBody();
			os.write(resp.getBytes());
			os.close();
			// ### FIN REPONSE ###
		}
	}
	
	class authenticateUser implements HttpHandler {
		// localhost:8000/getConnectedUsers?userId=1
		public void handle(HttpExchange t) throws IOException {
			// TODO GetConnectedUsers
			// Récupération des paramètres:

			//int idUser = Integer.parseInt(params.get(usagerIdParam));
			
			
            InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
            BufferedReader br = new BufferedReader(isr);
            String query = br.readLine();
            Map<String, String>  params = parseQueryString(query);
            System.out.println("Params du serveur" + params.toString());

            
            
			// ### REPONSE ###
 
			String response = "true";
			String resp = response;
			t.sendResponseHeaders(200, resp.length());
			OutputStream os = t.getResponseBody();
			os.write(resp.getBytes());
			os.close();

			// ### FIN REPONSE ###
		}
	}

	// ###################### FIN HANDLERS pour endpoints
	// ##################################

	public String serveurStateResponse() {
		/*
		 * Cette méthode format une réponse qui donne un apperçu complet de l'état du
		 * serveur (salle usagers etc...) pour fin de debug surtout
		 */
		String response = System.lineSeparator() + "Users - sommaire de l'etat:" + System.lineSeparator();
		for (User u : getUsers()) {
			response += u.toString();
			response += System.lineSeparator();
		}
		response += System.lineSeparator() + "Salles - sommaire de l'etat:" + System.lineSeparator();

		for (Salle s : getSalles()) {
			response += s.toString();
			response += System.lineSeparator();
		}
		return response;
	}

	public Map<String, String> parseQueryString(String qs) {
		/**
		 * Prends en entrée un string, qui represente l'ensemble de la query qui a été
		 * envoyée au serveur En extrait les params avec "&" et les fous dans un hashmap
		 * Thanks to "Oliv" from stack overflow question #11640025 for code snippet
		 */
		Map<String, String> result = new HashMap<>();
		if (qs == null)
			return result;

		int last = 0, next, l = qs.length();
		while (last < l) {
			next = qs.indexOf('&', last);
			if (next == -1)
				next = l;

			if (next > last) {
				int eqPos = qs.indexOf('=', last);
				try {
					if (eqPos < 0 || eqPos > next)
						result.put(URLDecoder.decode(qs.substring(last, next), "utf-8"), "");
					else
						result.put(URLDecoder.decode(qs.substring(last, eqPos), "utf-8"),
								URLDecoder.decode(qs.substring(eqPos + 1, next), "utf-8"));
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e); // will never happen, utf-8 support is mandatory for java
				}
			}
			last = next + 1;
		}
		return result;
	}

	public void addMsgSalle(Message msg) {
		// Méthode initiale pour ajouter un message à une salle
		int idSalle = msg.getIdSalle();
		for (Salle s : salles) {
			if (s.getId() == idSalle) {
				s.addMessage(msg);
			}
		}
	}

	public void initSalle(Salle s) {
		/*
		 * Ajouter une salle à sa liste, possiblement d'autres opérations pertinentes
		 * lors de la création
		 */
		salles.add(s);
		System.out.println("Salle créée: " + s.toString());
		System.out.println("Nombre de salles: " + salles.size());
	}

	public void initUsager(User u) {
		/*
		 * Ajouter une usager à sa liste, possiblement d'autres opérations pertinentes
		 * lors de la création
		 */
		usagers.add(u);
		System.out.println("Usager créé: " + u.toString());
		System.out.println("Nombre usagers: " + usagers.size());
	}

	public int getSalleCount() {
		return salles.size();
	}

	public int getUserCount() {
		return usagers.size();
	}

	public List<Salle> getSalles() {
		return salles;
	}

	public List<User> getUsers() {
		return usagers;
	}

	public boolean subscribeUser(int idSalle, int idUser) {
		User currentUser = getUserFromId(idUser);
		Salle currentSalle = getSalleFromId(idSalle);

		if (currentUser != null && currentSalle != null) {
			currentSalle.addSubscriber(currentUser);
			return true;
		}

		return false;
	}

	public boolean unsubscribeUser(int idSalle, int idUser) {
		User currentUser = getUserFromId(idUser);
		return currentUser.seDesabonner(idSalle); 
	}

	public User getUserFromId(int userId) {
		for (int i = 0; i < usagers.size(); i++) {
			if (usagers.get(i).getId() == userId) {
				return usagers.get(i);
			}
		}
		System.out.println("ID d'usager n°" + userId + "introuvable.");
		return null;
	}

	public Salle getSalleFromId(int idSalle) {
		for (int i = 0; i < salles.size(); i++) {
			if (salles.get(i).getId() == idSalle) {
				return salles.get(i);
			}
		}
		System.out.println("ID salle n°" + idSalle + " introuvable.");
		return null;
	}

}
