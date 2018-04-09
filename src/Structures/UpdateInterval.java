package Structures;

public class UpdateInterval implements Runnable{
	private int timeInterval = 1000;
	
	@Override
	public void run() {
		while (true) {
			// ------- code for task to run
			System.out.println("Ping: Thread d'update!");
			// ------- ends here
			try {
				Thread.sleep(timeInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}