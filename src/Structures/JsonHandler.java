package Structures;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.stream.JsonGenerator;

public class JsonHandler {

	public static void initFolders() {
		File userFolder = (new File(Utils.userRessourcePath));
		userFolder.mkdirs();
		File salleFolder = (new File(Utils.salleRessourcePath));
		salleFolder.mkdirs();
	}

	public static void saveDatas(ArrayList<User> userArray, ArrayList<Salle> salleArray) {
		for (User tempUser : userArray) {
			userToJson(tempUser);
		}
		for (Salle tempSalle : salleArray) {
			salleToJson(tempSalle);
		}
	}

	public static void userToJson(User user) {
		OutputStream file;
		try {
			file = new FileOutputStream(Utils.userRessourcePath + Integer.toString(user.getId()) + ".txt");

			JsonGenerator jsonGenerator = Json.createGenerator(file);
			// user = UserJSONWriter.createUser();
			jsonGenerator.writeStartObject(); // {
			jsonGenerator.write("id", user.getId());
			jsonGenerator.write("name", user.getUsername());
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
			jsonGenerator.write("name", salle.getSalleNom());
			jsonGenerator.write("description", salle.getDescription());

			ArrayList<User> x = salle.getSuscribersList();
			if (x != null) {
				jsonGenerator.writeStartArray("suscribersList");
				for (User user : salle.getSuscribersList()) {
					jsonGenerator.writeStartObject(); // {

					jsonGenerator.write("id", user.getId());
					jsonGenerator.write("name", user.getUsername());
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

			String salleNom = null;
			String Description = null;
			int id = 0;
			ArrayList<User> sub = new ArrayList<User>();
			ArrayList<Message> msg = new ArrayList<Message>();

			JsonObject obj = reader.readObject();
			id = obj.getInt("id");
			salleNom = obj.getString("name");
			Description = obj.getString("description");

			JsonArray array = obj.getJsonArray("suscribersList");
			for (int n = 0; n < array.size(); n++) {
				JsonObject object;
				object = array.getJsonObject(n);
				User objetUser = userFromJsonObject(object);
				sub.add(objetUser);
			}

			JsonArray arrayMsg = obj.getJsonArray("messagesList");
			for (int n = 0; n < arrayMsg.size(); n++) {
				JsonObject objectMsg;
				objectMsg = arrayMsg.getJsonObject(n);
				Message objetM = msgFromJsonObject(objectMsg);
				msg.add(objetM);
			}

			Salle salle = new Salle(salleNom, id, Description, sub, msg);
			return salle;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static User userFromJsonObject(JsonObject jsonUser) {
		String username = null;
		String password = null;
		int id = 0;

		id = jsonUser.getInt("id");
		username = jsonUser.getString("name");
		password = jsonUser.getString("password");

		User user = new User(username, password, id);
		return user;

	}

	public static Message msgFromJsonObject(JsonObject jsonUser) {
		String contenuMessage = null;
		int idMessage = 0;
		int idUtilisateur = 0;
		int idSalle = 0;

		idMessage = jsonUser.getInt("idMessage");
		idUtilisateur = jsonUser.getInt("idUtilisateur");
		contenuMessage = jsonUser.getString("contenuMessage");
		idSalle = jsonUser.getInt("idSalle");

		Message m = new Message(idMessage, contenuMessage, idSalle, idUtilisateur);
		return m;

	}
}
