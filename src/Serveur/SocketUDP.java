package Serveur;
import java.io.*;
import java.net.*;

import Structures.Message;
import Structures.Utils;

public class SocketUDP extends Thread {
	/*Simple socketUDP. Port selon Utils.udpPort ou argument en ligne de commande si donné
	 * edit
	 * */
	
	protected int port = Utils.udpPort;
    protected DatagramSocket udpsocket = null;
    protected BufferedReader in = null;
    protected SocketTCP socketTCP;

    public SocketUDP(String name, int portServerUDP, int portServerTCP) throws IOException {
        super(name);
        port = portServerUDP;
        udpsocket = new DatagramSocket(port);
        socketTCP = SocketTCP.getInstance("dummy", portServerUDP, portServerTCP);
        System.out.println("udpserver on port:"+ port);
    }
    
    public void treatNewMessge(Message msg) {
    	//Reçoit un message destiné à une salle - passe le message à la salle en question
    	socketTCP.addMsgSalle(msg);
    	System.out.println("UDP msg recu:"+ msg.toString());
	}

    public void run() {
        while (true) {
            try {
                byte[] buf = new byte[Utils.totalBytes];
                
            	//recevoir un paquet - peut être n'importe qui
                DatagramPacket inPacket = new DatagramPacket(buf, buf.length);
                udpsocket.receive(inPacket);
               
                //Constructeur de Messge prend les byte[] et reconstruit le contenu
                //Appel le traitement du message
                Message msg = new Message(inPacket.getData());
                treatNewMessge(msg);
                
                //DEBUG: juste pour montrer qu'on maîtrise les données
    			System.out.println("Msg de client:" + msg.toString());
                buf = msg.toBytes();
                
                // renvoyer une réponse au client - la même chose qu'il nous a envoyé
                InetAddress address = inPacket.getAddress();
                int port = inPacket.getPort();	//e.g. le port sur lequel le client attend un éventuelle réponse
                DatagramPacket outPacket = new DatagramPacket(buf, buf.length, address, port);
                udpsocket.send(outPacket);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    

}