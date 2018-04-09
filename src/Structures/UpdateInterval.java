package Structures;

public class UpdateInterval extends Thread {
	public void run() {
		// run in a second
		final long timeInterval = 1000;
		Runnable runnable = new Runnable() {
			public void run() {
				while (true) {
					// ------- code for task to run
					System.out.println("Hello !!");
					// ------- ends here
					try {
						Thread.sleep(timeInterval);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};

	}
} 