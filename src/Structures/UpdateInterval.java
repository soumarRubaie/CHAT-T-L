package Structures;

import java.io.UnsupportedEncodingException;

import Client.Client;

public class UpdateInterval implements Runnable{
	private int timeInterval = 5000;
	Client clt;
	public UpdateInterval(Client clt) {
		this.clt = clt;
	}
	
	@Override
	public void run() {
		while (true) {
			// ------- code for task to run
			System.out.println("Ping: Thread d'update!");
			try {
				clt.updateClientLists();
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// ------- ends here
			try {
				Thread.sleep(timeInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}