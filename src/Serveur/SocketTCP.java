package Serveur;

import java.io.IOException;
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

public class SocketTCP extends Thread {
	
	//############## Gestion du singleton
	private static SocketTCP instance;
	public static SocketTCP getInstance(String name, int portUDP, int portTCP) throws IOException {
		//Probablement destiné à partir, mais pour démo ça va
		if (instance==null) {
			instance = new SocketTCP(name, portTCP);
			return instance;
		}
		return instance;
	}
	

	// ####################### endpoint et params  ##########

	// Endpoint: creation salle
	private static String creationSalleURI = "/creationSalle";
	private static String salleNomParam = "salleNom";

	// Endpoint: creation usager
	private static String creationUsagerURI = "/creationUsager";
	private static String usagerNomParam = "username";
	private static String usagerPasswordParam = "password";

	// Endpoint: suscriber un usager a une salle
	private static String suscribeUsagerURI = "/suscribeUsagerSalle";
	private static String usagerIdParam = "userId";
	private static String salleIdParam = "salleId";
	
	// Endpoint: delete msg 
	private static String deleteMessageURI = "/deleteMessage";
	private static String msgIdParam = "messageId";
	
	//Endpoint: autres
	private static String getArchiveURI = "/getArchive";
	private static String getConnectedUsersURI = "/getConnectedUsers";
	private static String unsubscribeUsagerURI = "/unsubscribeUsager";

	//TODO: Ajouter nouveau Endpoints si nécessaire ICI

	// ####################### FIND endpoint et param ###########

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
		// CREATION des contextes - si de nouveaux endpoint sont ajouteré, créer les contextes ici
		//et écrire le handler correspondant plus bas
		serveur.createContext(creationSalleURI, new CreationSalleHandler());
		serveur.createContext(creationUsagerURI, new CreationUsagerHandler());
		serveur.createContext(suscribeUsagerURI, new SuscribeUsagerHandler());
		serveur.createContext(deleteMessageURI, new DeleteMessageHandler());
		serveur.createContext(getArchiveURI, new GetArchiveHandler());
		serveur.createContext(getConnectedUsersURI, new GetConnectedUsersHandler());
		serveur.createContext(unsubscribeUsagerURI, new UnsuscribeUsagerHandler());

		serveur.setExecutor(null); // Associe un thread par défaut au Serveur
		serveur.start(); // Démarre le serveur

	}

	//###################### HANDLERS pour endpoints ##################################
	//Chaque handler a une section PRINT, surtout pour afficher à la console pour debug... voir si utile long terme ou non
	//la logique de réponse au browser est probablement pertinente par contre
	
	class CreationSalleHandler implements HttpHandler {
		/*
		 * Création d'une salle
		 */
		@Override
		public void handle(HttpExchange t) throws IOException {

			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());

			// Creer la nouvelle salle 
			//Le ID de la salle devient simplement le nombre de salle, getSalleCount()
			//initSalle pour l'instant ne fait que l'ajouter à la liste des salle
			initSalle(new Salle(params.get(salleNomParam), getSalleCount(), "DESCRIPTION A VENIR"));

		
			// #######################BEGIN DEBUG PRINT
			String lineREturn = System.lineSeparator();
			String response = "Creation d'une nouvelle salle" + lineREturn + salleNomParam + ": "
					+ params.get(salleNomParam) + " nmb total salles: " + getSalleCount();
			response += serverStateResponse();
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
			// ################### END DEBUG PRINT
		}
	}

	class CreationUsagerHandler implements HttpHandler {

		/* Gérer la création d'une nouvel usager */
		@Override
		public void handle(HttpExchange t) throws IOException {
			
			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());

			// Creer nouvel usager & attacher au observer pattern
			initUsager(new User(params.get(usagerNomParam), params.get(usagerPasswordParam), getUserCount()));

			// #######################BEGIN DEBUG PRINT
			String lineREturn = System.lineSeparator();
			String response = "Creation d'une nouvel usager" + lineREturn + usagerNomParam + ": "
					+ params.get(usagerNomParam) + " pass: " + params.get(usagerPasswordParam) + " nmb total usagers: "
					+ getUserCount() + lineREturn;
			String resp = response + serverStateResponse();
			t.sendResponseHeaders(200, resp.length());
			OutputStream os = t.getResponseBody();
			os.write(resp.getBytes());
			os.close();
			// ################### END DEBUG PRINT

		}
	}

	class SuscribeUsagerHandler implements HttpHandler {
		public void handle(HttpExchange t) throws IOException {

			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());

			// Suscriber revient à enregistrer l'Utilisateur comme observateur de la salle
			// la salle est subject, et l'utilisateur observer

			// 1 - Trouver la salle correspondante au salleId
			Salle salle = null;
			User user = null;
			List<Object> l = findObjectsFromIds(params.get(salleIdParam), params.get(usagerIdParam));

			if (l.size() == 2) {
				// 2 - Attacher l'usager spécifié à la salle
				salle = (Salle) l.get(1);
				user = (User) l.get(0);
				salle.addSubscriber(user);
			} else {
				System.out.println("Impossible de trouver objets pour id spécifié - essayer d'autres id");
			}

			// #######################BEGIN DEBUG PRINT
			String lineREturn = System.lineSeparator();
			String response = "Attache user: " + user.toString() + " a salle: " + salle.toString();
			String resp = response + serverStateResponse();
			t.sendResponseHeaders(200, resp.length());
			OutputStream os = t.getResponseBody();
			os.write(resp.getBytes());
			os.close();
			// ################### END DEBUG PRINT
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
				if (detachUser(idSalle, idUser)) {
					String response = "Desabonnement: " + lineReturn + "Utilisateur " + idUser
							+ " se desabonne de la salle " + idSalle;
					resp = response + serverStateResponse();
					t.sendResponseHeaders(200, resp.length());
				} else {
					String response = "ERREUR lors du desabonnement!" + lineReturn + "User ou salle non-existants"+ lineReturn
							+ "Utilisateur " + idUser + " Salle " + idSalle + lineReturn;
					resp = response + serverStateResponse();
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
				//TODO Delete Message
				// Récupération des paramètres:
				Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());
				int idUser = Integer.parseInt(params.get(usagerIdParam));
				int idSalle = Integer.parseInt(params.get(salleIdParam));
				int idMsg = Integer.parseInt(params.get(msgIdParam));

				// ### REPONSE ###
				String response = "A implementer (DeleteMessage)"+ lineReturn + "id user: " + idUser 
						+ lineReturn + "id salle: " + idSalle
						+ lineReturn + "id msg: " + idMsg;
				String resp = response + serverStateResponse();
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
				//TODO GetArchive
				// Récupération des paramètres:
				Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());
				int idUser = Integer.parseInt(params.get(usagerIdParam));
				int idSalle = Integer.parseInt(params.get(salleIdParam));

				// ### REPONSE ###
				String response = "A implementer (GetArchive)"+ lineReturn + "id user: " + idUser 
						+ lineReturn + "id salle: " + idSalle;
				String resp = response + serverStateResponse();
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
				//TODO GetConnectedUsers
				// Récupération des paramètres:
				Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());
				int idUser = Integer.parseInt(params.get(usagerIdParam));

				// ### REPONSE ###
				String response = "A implementer (GetConnectedUsers)"+ lineReturn + "id user: " + idUser;
				String resp = response + serverStateResponse();
				t.sendResponseHeaders(200, resp.length());
				OutputStream os = t.getResponseBody();
				os.write(resp.getBytes());
				os.close();
				// ### FIN REPONSE ###
			}
		}
		
		//###################### FIN HANDLERS pour endpoints ##################################


		private List<Object> findObjectsFromIds(String salleId, String userId) {
			/*
			 * Matches the id provided (strings) to corresponding obj. No check for
			 * existence
			 */
			// TODO: checker pour non-existence d'un obj correspondant au ID et gérer ce cas?
			List<Object> obj = new ArrayList<>();
			for (Salle s : getSalles()) {
				if (Integer.toString(s.getId()).equals(salleId)) {
					for (User u : getUsers()) {
						if (Integer.toString(u.getId()).equals(userId)) {
							obj.add(u);
							obj.add(s);
						}
					}
				}
			}
			return obj;
		}

	
	
	
	
	
	
	public String serverStateResponse() {
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
		//Méthode initiale pour ajouter un message à une salle
		int idSalle = msg.getIdSalle();
		for (Salle s: salles) {
			if (s.getId() == idSalle) {
				s.addMsg(msg);
			}
		}
	}


	public void initSalle(Salle s) {
		/*Ajouter une salle à sa liste, possiblement d'autres opérations pertinentes lors de la création*/
		salles.add(s);
	}

	public void initUsager(User u) {
		/*Ajouter une usager à sa liste, possiblement d'autres opérations pertinentes lors de la création*/
		usagers.add(u);
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
	
	public boolean detachUser(int idSalle, int idUser) {
		// TODO DetachUser
		return true;
	}


}
