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
	//Subject.java:
	//Pour la gestion des salles actives et la redirection des messages reçus sur le port UDP vers la bonne URI
	// Gestion des usagers similairement
    private List<Salle> salles = new ArrayList<>();
    private List<User> usagers = new ArrayList<>();
    
    //Gestion des suscribers - chaque salle dispose d'une list suscriberSalle et y ajoute les usagers 
    private List<User> suscribersSalle = new ArrayList<>();

    public void attachSalle(Salle s) {
    	System.out.println("Salle attachée: " + s.toString());
    	System.out.println("Nombre de salles: " + salles.size());

    	salles.add(s);
    }
    
    public void attachUsager(User u) {
    	System.out.println("Usager attachée: " + u.toString());
    	System.out.println("Nombre usagers: " + usagers.size());

    	usagers.add(u);
    }
    
    public void attachSuscriber(User u) {
    	System.out.println("Sucriber attachée: " + u.toString());
    	System.out.println("Nombre suscriber: " + suscribersSalle.size());
    	suscribersSalle.add(u);
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
    public List<User> getSuscriberSalle() {
		return suscribersSalle;
	}

	// Based on code submitted as accepted on stack overflow question #11640025,
	// regarding httpHandler structure
	private static final Serveur serveur = new Serveur();

	// ####################### endpoint et paramètres associés à chacun ##########
	private static String creationSalleURI = "/creationSalle";
	private static String salleNomParam = "salleNom";

	private static String creationUsagerURI = "/creationUsager";
	private static String usagerNomParam = "username";
	private static String usagerPasswordParam = "password";

	private static String suscribeUsagerURI = "/suscribeUsagerSalle";
	private static String usagerIdParam = "userId";
	private static String salleIdParam = "salleId";
	// ####################### FIND endpoint et param ###########

	private static int inetSocketPort = 8000;

	public static Serveur getInstance() {
		return serveur;
	}

	public static void main(String[] args) throws Exception {
		/* So this is our main - starts the serveur to listen on port inetSocketPort */
		HttpServer serveur = HttpServer.create(new InetSocketAddress(inetSocketPort), 0);

		// ################ CONTEXT CREATION
		// createContext. Must be a subdomain /mySubDom. Then every request to that
		// subdomain will be redirect to the designated handler
		// D'autres mots - ce sont les endpoints du REST api
		serveur.createContext(creationSalleURI, new CreationSalleHandler());
		serveur.createContext(creationUsagerURI, new CreationUsagerHandler());
		serveur.createContext(suscribeUsagerURI, new SuscribeUsagerHandler());
		//
		// ################ CONTEXT CREATION

		serveur.setExecutor(null); // creates a default executor
		serveur.start();

		System.out.println("Serveur online @ " + serveur.getAddress());
		System.out.println("Context " + creationSalleURI + " actif");

	}

	static class CreationSalleHandler implements HttpHandler {
		// localhost:inetSocketPort/creationSalle?salleNom=testNom
		@Override
		public void handle(HttpExchange t) throws IOException {

			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());

			// Creer la nouvelle salle & attacher au observeur pattern
			serveur.attachSalle(new Salle(params.get(salleNomParam), serveur.getSalleCount(),""));

			// #######################BEGIN DEBUG PRINT
			String lineREturn = System.lineSeparator();
			String response = "Creation d'une nouvelle salle" + lineREturn + salleNomParam + ": "
					+ params.get(salleNomParam) + " nmb total salles: " + serveur.getSalleCount();
			response += serveurStateResponse();
			t.sendResponseHeaders(200, response.length());

			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
			// ################### END DEBUG PRINT
		}
	}

	static class CreationUsagerHandler implements HttpHandler {

		/* Gérer la création d'une nouvel usager */
		@Override
		public void handle(HttpExchange t) throws IOException {
			// localhost:inetSocketPort/creationUsager?username=billybob&password=secrepass

			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());

			// Creer nouvel usager & attacher au observeur pattern
			serveur.attachUsager(
					new User(params.get(usagerNomParam), params.get(usagerPasswordParam), serveur.getUserCount()));

			// #######################BEGIN DEBUG PRINT
			String lineREturn = System.lineSeparator();
			String response = "Creation d'une nouvel usager" + lineREturn + usagerNomParam + ": "
					+ params.get(usagerNomParam) + " pass: " + params.get(usagerPasswordParam) + " nmb total usagers: "
					+ serveur.getUserCount() + lineREturn;
			String resp = response + serveurStateResponse();
			t.sendResponseHeaders(200, resp.length());
			OutputStream os = t.getResponseBody();
			os.write(resp.getBytes());
			os.close();
			// ################### END DEBUG PRINT

		}
	}

	static class SuscribeUsagerHandler implements HttpHandler {
		// localhost:inetSocketPort/suscribeUsagerSalle?userId=1&salleId=01
		public void handle(HttpExchange t) throws IOException {

			Map<String, String> params = parseQueryString(t.getRequestURI().getQuery());

			// Suscriber revient à enregistrer l'Utilisateur comme observateur de la salle
			// la salle devient subject, et l'utilisateur observeur
			// serveur.attachUsager(new User(params.get(usagerNomParam),
			// params.get(usagerPasswordParam), serveur.getUserCount()));

			// 1 - Trouver la salle correspondante au salleId
			Salle salle = null;
			User user = null;
			List<Object> l = findObjectsFromIds(params.get(salleIdParam), params.get(usagerIdParam));

			if (l.size() == 2) {
				salle = (Salle) l.get(1);
				user = (User) l.get(0);

				// 2 - Attacher l'usager spécifié à la salle
				//salle.attachSuscriber(user);
			} else {
				System.out.println("Impossible de trouver objets pour id spécifié - essayer d'autres id");
			}

			// #######################BEGIN DEBUG PRINT
			String lineREturn = System.lineSeparator();
			String response = "Attache user: " + user.toString() + " a salle: " + salle.toString();
			String resp = response + serveurStateResponse();
			t.sendResponseHeaders(200, resp.length());
			OutputStream os = t.getResponseBody();
			os.write(resp.getBytes());
			os.close();
			// ################### END DEBUG PRINT
		}

		private List<Object> findObjectsFromIds(String salleId, String userId) {
			/*
			 * Matches the id provided (strings) to corresponding obj. Not check for
			 * existence
			 */
			List<Object> obj = new ArrayList<>();
			for (Salle s : serveur.getSalles()) {
				if (Integer.toString(s.getId()).equals(salleId)) {
					for (User u : serveur.getUsers()) {
						if (Integer.toString(u.getId()).equals(userId)) {
							obj.add(u);
							obj.add(s);
						}
					}
				}
			}
			return obj;

		}

	}

	public static String serveurStateResponse() {
		/*
		 * Cette méthode format une réponse qui donne un apperçu complet de l'état du
		 * serveur (salle usagers etc...) pour fin de debug
		 */

		String response = System.lineSeparator() + "Users - sommaire de l'etat:" + System.lineSeparator();
		for (User u : serveur.getUsers()) {
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
