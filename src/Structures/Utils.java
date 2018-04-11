package Structures;


import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Utils {
	// Codes d'erreur & de réponses serveurTCP
	public static String OK = "200";
	public static String lineReturn = System.lineSeparator();

	public static String ERR_NO_DATA = "400";
	public static String ERR_REFUSED_LOGGIN= "401";
	public static String ERR_USER_EXIST = "403";
	public static String ERR_SALLE_EXIST = "413";
	public static String ERR_ACCES_REFUSED = "405";

	// 600 : Données non existantes (salle ou user)
	// 601 : Création Utilisateur: Username déjà utilisé
	// 602 : Accès refusé
	// 603 : Données non existantes (Message)
	
	public static String jsonarrayStringSeparator = "##";
	public static int intervalUpdateMiliseconds = 2000;
	public static int disconectCyclesAllowed = 10;
	
	//Params json path
	public static String userRessourcePath = "ressources/users/";
	public static String salleRessourcePath = "ressources/salles/";
	
	//Params connection par défaut - seront utilisés si rien n'est passé en argument à la ligne de cmd
		public static String CLIENT = "client";
		public static String SERVER = "serveur";
		public static String serverName = "localhost";
		public static int tcpPort = 8000;
		public static int udpPort = 8001;
		public static String serverURLNoPort = "http://localhost:";
		
		// ####################### endpoint et params ##########
		public static String creationSalleURI = "/creationSalle";
		public static String salleNomParam = "salleNom";
		public static String salleDescriptionParam = "description";

		// Endpoint: creation usager
		public static String creationUsagerURI = "/creationUsager";
		public static String updateUsagerURI = "/updateUsager";
		public static String usagerNomParam = "username";
		public static String usagerPasswordParam = "password";

		// Endpoint: suscriber un usager a une salle
		public static String suscribeUsagerURI = "/suscribeUsagerSalle";
		public static String usagerIdParam = "userId";
		public static String salleIdParam = "salleId";

		// Endpoint: delete msg
		public static String deleteMessageURI = "/deleteMessage";
		public static String msgIdParam = "messageId";

		// Endpoint: autres
		public static String getArchiveURI = "/getArchive";
		public static String getConnectedUsersURI = "/getConnectedUsers";
		public static String unsubscribeUsagerURI = "/unsubscribeUsager";
		public static String authUserURI = "/authUser";
		public static String initClientURI = "/initClient";
		
		// Endpoint: getters to update list in the client
		public static String getUsersFromServer = "/getUsersFromServer";
		public static String getSallesFromServer = "/getSallesFromServer";
		
	// ####################### endpoint et params ##########

	
	//Paquets structure - indique le nombre de bytes alloué à chaque info
	public static int messageIdBytes = 4;
	public static int authorIdBytes = 4;
	public static int salleIdBytes = 4;
	public static int datagrameSizeBytes = 10000;
	public static int totalBytes = messageIdBytes+ authorIdBytes+salleIdBytes+datagrameSizeBytes;




	/*Autres choses méthos etc.*/
	public static byte[] intTo4Bytes(int numb) {
		/*Transforme int en byte[4]. Pourrait être puls intelligent/générique*/
		return ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN).putInt(numb).array();
	}
	
	public static int bytesToInt(byte[] by) {
		//May need to use .order(BIG_ENDING)/.order(SMALL_ENDIAN) which defines how the byte[] is read
		ByteBuffer bb = ByteBuffer.wrap(by);

		return bb.getInt();
		
	}
	
	
}
