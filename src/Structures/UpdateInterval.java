package Structures;

public class UpdateInterval {
	public static void main(String[] args) {
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
		Thread thread = new Thread(runnable);
		thread.start();
	}
}