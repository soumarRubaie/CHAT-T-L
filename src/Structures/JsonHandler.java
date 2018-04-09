package Structures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonException;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

public class JsonHandler {

	public static void initFolders() {
		File userFolder = (new File(Utils.userRessourcePath));
		userFolder.mkdirs();
		File salleFolder = (new File(Utils.salleRessourcePath));
		salleFolder.mkdirs();
	}

	public static void saveDatas(ArrayList<User> userArray, ArrayList<Salle> salleArray) {
		System.out.println("Exporten cours...");
		saveUsersDatas(userArray);
		saveSallesDatas(salleArray);
		System.out.println("Exportation terminée!");
	}

	public static void saveUsersDatas(ArrayList<User> userArray) {
		for (User tempUser : userArray) {
			userToJson(tempUser);
		}
		System.out.println("Données des utilisateurs enregistrées");
	}

	public static void saveSallesDatas(ArrayList<Salle> salleArray) {
		for (Salle tempSalle : salleArray) {
			salleToJson(tempSalle);
		}
		System.out.println("Données des salles enregistrées");
	}

	public static void userToJson(User user) {
		OutputStream file;
		try {
			file = new FileOutputStream(Utils.userRessourcePath + Integer.toString(user.getId()) + ".txt");

			JsonGenerator jsonGenerator = Json.createGenerator(file);
			// user = UserJSONWriter.createUser();
			jsonGenerator.writeStartObject(); // {
			jsonGenerator.write("id", user.getId());
			jsonGenerator.write("username", user.getUsername());
			jsonGenerator.write("password", user.getPassword());

			jsonGenerator.writeEnd(); // }
			jsonGenerator.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void salleToJson(Salle salle) {
		OutputStream file;
		try {
			file = new FileOutputStream(Utils.salleRessourcePath + Integer.toString(salle.getId()) + ".txt");
			JsonGenerator jsonGenerator = Json.createGenerator(file);
			// salle = SalleJSONWriter.createSalle();
			jsonGenerator.writeStartObject(); // {
			jsonGenerator.write("id", salle.getId());
			jsonGenerator.write("salleNom", salle.getSalleNom());
			jsonGenerator.write("description", salle.getDescription());

			ArrayList<User> x = salle.getSuscribersList();
			if (x != null) {
				jsonGenerator.writeStartArray("suscribersList");
				for (User user : salle.getSuscribersList()) {
					jsonGenerator.writeStartObject(); // {

					jsonGenerator.write("id", user.getId());
					jsonGenerator.write("username", user.getUsername());
					jsonGenerator.write("password", user.getPassword());

					jsonGenerator.writeEnd(); // }
				}
				jsonGenerator.writeEnd(); // end of room array
			}

			ArrayList<Message> y = salle.getMessagesList();
			if (y != null) {
				jsonGenerator.writeStartArray("messagesList");
				for (Message msg : salle.getMessagesList()) {
					jsonGenerator.writeStartObject();
					jsonGenerator.write("idMessage", msg.getIdMessage());
					jsonGenerator.write("idUtilisateur", msg.getIdUtilisateur());
					jsonGenerator.write("contenuMessage", msg.getContenuMessage());
					jsonGenerator.write("idSalle", msg.getIdSalle());
					jsonGenerator.writeEnd();
				}
				jsonGenerator.writeEnd(); // end of room array
			}
			jsonGenerator.writeEnd(); // }
			jsonGenerator.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ArrayList<User> importerUsers() {
		ArrayList<User> usersArray = new ArrayList<User>();

		File repUsers = new File(Utils.userRessourcePath);
		File[] fichiersUsers = repUsers.listFiles();
		for (int n = 0; n < fichiersUsers.length; n++) {
			usersArray.add(readUser(fichiersUsers[n]));
		}

		return usersArray;
	}

	public static ArrayList<Salle> importerSalles() {
		ArrayList<Salle> sallesArray = new ArrayList<Salle>();

		File repSalles = new File(Utils.salleRessourcePath);
		File[] fichiersSalles = repSalles.listFiles();
		for (int n = 0; n < fichiersSalles.length; n++) {
			sallesArray.add(readSalle(fichiersSalles[n]));
		}

		return sallesArray;
	}

	public User readUser(String userId) {
		File fichier = new File(Utils.userRessourcePath + userId + ".txt");
		return readUser(fichier);
	}

	public static User readUser(File fichier) {
		InputStream fis;
		try {
			fis = new FileInputStream(fichier);
			JsonReader reader = Json.createReader(fis);
			JsonObject object = reader.readObject();
			return userFromJsonObject(object);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Salle readSalle(String salleId) {
		File fichier = new File(Utils.salleRessourcePath + salleId + ".txt");
		return readSalle(fichier);
	}

	public static Salle readSalle(File fichier) {
		InputStream fis;
		try {
			fis = new FileInputStream(fichier);
			JsonReader reader = Json.createReader(fis);
			JsonObject obj = reader.readObject();
			return salleFromJsonObject(obj);
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (JsonException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static User userFromJsonObject(JsonObject jsonUser) {
		String username = null;
		String password = null;
		int id = 0;

		id = jsonUser.getInt("id");
		username = jsonUser.getString("username");
		password = jsonUser.getString("password");

		User user = new User(username, password, id);
		return user;

	}
	
	public static User userFromString(String jsonUser) {
		if (jsonUser.length()>0) {
			JsonReader jsonReader = Json.createReader(new StringReader(jsonUser));
			JsonObject objectUser = jsonReader.readObject();
			jsonReader.close();
			int id = objectUser.getInt("id");
			String username = objectUser.getString("username");
			String password = objectUser.getString("password");
			boolean isConnected = objectUser.getBoolean("isConnected");
			User user = new User(username, password, id, isConnected);
			return user;
		} else {
		return null;
		}
	}
	
	public static Salle salleFromJsonObject(JsonObject objectSalle) {
	    String salleNom = null;
		String Description = null;
		int id = 0;
		ArrayList<User> sub = new ArrayList<User>();
		ArrayList<Message> msg = new ArrayList<Message>();

		id = objectSalle.getInt("id");
		salleNom = objectSalle.getString("salleNom");
		Description = objectSalle.getString("description");

		JsonArray array = objectSalle.getJsonArray("suscribersList");
		for (int n = 0; n < array.size(); n++) {
			JsonObject object;
			object = array.getJsonObject(n);
			User objetUser = userFromJsonObject(object);
			sub.add(objetUser);
		}

		JsonArray arrayMsg = objectSalle.getJsonArray("messagesList");
		for (int n = 0; n < arrayMsg.size(); n++) {
			JsonObject objectMsg;
			objectMsg = arrayMsg.getJsonObject(n);
			Message objetM = msgFromJsonObject(objectMsg);
			msg.add(objetM);
		}

		Salle salle = new Salle(salleNom, id, Description, sub, msg);
		return salle;
	    
	}
	
	public static Salle salleFromString(String jsonSalle) {
		
		if (jsonSalle.length()>0) {
		JsonReader jsonReader = Json.createReader(new StringReader(jsonSalle));
	    JsonObject objectSalle = jsonReader.readObject();
	    jsonReader.close();
	    
		return salleFromJsonObject(objectSalle);
		} else {
		return null;
		}
	}

	public static Message msgFromJsonObject(JsonObject jsonMsg) {
		String contenuMessage = null;
		int idMessage = 0;
		int idUtilisateur = 0;
		int idSalle = 0;

		idMessage = jsonMsg.getInt("idMessage");
		idUtilisateur = jsonMsg.getInt("idUtilisateur");
		contenuMessage = jsonMsg.getString("contenuMessage");
		idSalle = jsonMsg.getInt("idSalle");
		
		Message m = new Message(idMessage, contenuMessage, idSalle, idUtilisateur);
		return m;

	}
}
