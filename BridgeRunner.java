/**
 * Runs all threads
 */

public class BridgeRunner {

	public static void main(String[] args) {
		// TODO - check command line inputs
		int maxCars = -1;
		int numCars = -1;

		try{
			maxCars = Integer.parseInt(args[0]);
			numCars = Integer.parseInt(args[1]);
		}
		catch(Exception e){
			System.err.println("Usage: java BridgeRunner <bridge limit> <num cars>");
			System.exit(0);
		}
		
		if(maxCars <= 0 || numCars <=0){
			System.err.println("Error: bridge limit and/or num cars must be positive.");
			System.exit(0);
		}


		// TODO - instantiate the bridge
		OneLaneBridge bridge = new OneLaneBridge(maxCars);
		
		// TODO - allocate space for threads
		Thread[] threads = new Thread[numCars];

		// TODO - start then join the threads

		//create and start threads
		for (int i = 0; i < numCars; i++) {
            Car car = new Car(i, bridge);
            threads[i] = new Thread(car);
            threads[i].start();
        }

		// wait for all car threads to complete
        for (int i = 0; i < numCars; i++) {
            try {
                threads[i].join();
            } 
			catch (InterruptedException e) {
                System.out.println("Error: Thread Interrupted");
            }
        }

		System.out.println("All cars have crossed!!");
	}

}