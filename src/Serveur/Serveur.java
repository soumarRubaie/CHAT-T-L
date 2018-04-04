package Serveur;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import Structures.Salle;
import Structures.User;

public class Serveur {
	// Subject.java:
	// Pour la gestion des salles actives et la redirection des messages reçus sur
	// le port UDP vers la bonne URI
	// Gestion des usagers similairement
	public static String lineReturn = System.lineSeparator();

	private static final Serveur serveur = new Serveur();
	private List<Salle> salles = new ArrayList<>();
	private List<User> usagers = new ArrayList<>();

	// Récupération du singleton
	public static Serveur getInstance() {
		return serveur;
	}

	public void attachSalle(Salle s) {
		System.out.println("Salle créée: " + s.toString());
		System.out.println("Nombre de salles: " + salles.size());

		salles.add(s);
	}

	// Ajoute un usager au serveur
	public void newUsager(User u) {
		System.out.println("Usager créé: " + u.toString());
		System.out.println("Nombre usagers: " + usagers.size());
		usagers.add(u);
	}

	public boolean attachUser(int idSalle, int idUser) {
		User currentUser = getUserFromId(idUser);
		Salle currentSalle = getSalleFromId(idSalle);

		if (currentUser != null && currentSalle != null) {
			currentSalle.addSubscriber(currentUser);
			return true;
		}

		return false;
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

	public int getSalleCount() {
		return salles.size();
	}

	public int getUserCount() {
		return usagers.size();
	}

	public List<Salle> getSalles() {
		return salles;
	}

	public List<User> getUsagers() {
		return usagers;
	}

	// Based on code submitted as accepted on stack overflow question #11640025,
	// regarding httpHandler structure
	// ####################### endpoint et paramètres associés à chacun ##########
	private static int inetSocketPort = 8000;

	private static String creationSalleURI = "/creationSalle";
	private static String salleNomParam = "salleNom";
	private static String salleDescParam = "salleDescription";

	private static String creationUsagerURI = "/creationUsager";
	private static String usagerNomParam = "username";
	private static String usagerPasswordParam = "password";

	private static String suscribeUsagerURI = "/suscribeUsagerSalle";
	private static String usagerIdParam = "userId";
	private static String salleIdParam = "salleId";

	// ####################### FIND endpoint et param ###########

	public static void main(String[] args) throws Exception {
		// Démarre le serveur qui écoute sur le port "inetSocketPort" et sans backlog
		// (0).
		HttpServer serveur = HttpServer.create(new InetSocketAddress(inetSocketPort), 0);

		// Création des contextes. On associe les URL aux fonctions.
		serveur.createContext(creationSalleURI, new CreationSalleHandler());
		serveur.createContext(creationUsagerURI, new CreationUsagerHandler());
		serveur.createContext(suscribeUsagerURI, new SuscribeUsagerHandler());

		serveur.setExecutor(null); // Associe un thread par défaut au Serveur
		serveur.start(); // Démarre le serveur

		System.out.println("Serveur online @ " + serveur.getAddress());
		System.out.println("Context " + creationSalleURI + " actif");

	}

	static class CreationSalleHandler implements HttpHandler {
		// TODO Passer la description de la salle en parametre
		// localhost:inetSocketPort/creationSalle?salleNom=testNom
		@Override
		public void handle(HttpExchange t) throws IOException {

			// Récupération des variables de la requête
			// (salleNom)
			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());

			// Creer la nouvelle salle & attacher au observeur pattern
			serveur.attachSalle(
					new Salle(params.get(salleNomParam), serveur.getSalleCount(), "Description de la salle"));

			// ### REPONSE ###
			String response = "Creation d'une nouvelle salle" + lineReturn + salleNomParam + ": "
					+ params.get(salleNomParam) + lineReturn + "Nombre total de salles: " + serveur.getSalleCount();
			response += serveurStateResponse();
			t.sendResponseHeaders(200, response.length());

			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
			// ### FIN REPONSE ###
		}
	}

	// Création d'un nouvel usager:
	// localhost:8000/creationUsager?username=billybob&password=secrepass
	static class CreationUsagerHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {

			// Récupération des variables de la requête
			// (username et password)
			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());

			// Création d'un usager
			// TODO Test sur la présence d'un Usager avec le même nom d'utilisateur dans la
			// BDD

			serveur.newUsager(
					new User(params.get(usagerNomParam), params.get(usagerPasswordParam), serveur.getUserCount()));

			// ### REPONSE ###
			String response = "Creation d'un nouvel usager: " + lineReturn + "Nom d'utilisateur: "
					+ params.get(usagerNomParam) + lineReturn + "Mot de passe: " + params.get(usagerPasswordParam)
					+ lineReturn + " Nombre total d'usagers: " + serveur.getUserCount() + lineReturn;

			String resp = response + serveurStateResponse();
			t.sendResponseHeaders(200, resp.length());
			OutputStream os = t.getResponseBody();
			os.write(resp.getBytes());
			os.close();
			// ### FIN REPONSE ###

		}
	}

	static class SuscribeUsagerHandler implements HttpHandler {
		// localhost:8000/suscribeUsagerSalle?userId=1&salleId=01
		public void handle(HttpExchange t) throws IOException {

			// Récupération des paramètres:
			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());
			int idUser = Integer.parseInt(params.get(usagerIdParam));
			int idSalle = Integer.parseInt(params.get(salleIdParam));

			String resp = "";

			// ### REPONSE ###
			if (serveur.attachUser(idSalle, idUser)) {
				String response = "Abonnement utilisateur: " + lineReturn + "Utilisateur " + idUser
						+ " abonne a la salle " + idSalle;
				resp = response + serveurStateResponse();
				t.sendResponseHeaders(200, resp.length());
			} else {
				String response = "ERREUR!" + lineReturn + "Abonnement utilisateur impossible: " + lineReturn
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

	public static String serveurStateResponse() {
		/*
		 * Cette méthode format une réponse qui donne un apperçu complet de l'état du
		 * serveur (salle usagers etc...) pour fin de debug
		 */

		String response = System.lineSeparator() + "Users - sommaire de l'etat:" + System.lineSeparator();
		for (User u : serveur.getUsagers()) {
			response += u.toString();
			response += System.lineSeparator();
		}
		response += System.lineSeparator() + "Salles - sommaire de l'etat:" + System.lineSeparator();

		for (Salle s : serveur.getSalles()) {
			response += s.toString();
			response += System.lineSeparator();
		}
		return response;
	}

	public static Map<String, String> parseQueryString(String qs) {
		/**
		 * Prends en entrée un string, qui represente l'ensemble de la query qui a été
		 * envoyé au serveur En extrait les params avec "&" et les fous dans un hashmap
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

}
