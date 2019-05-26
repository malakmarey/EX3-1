import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * this class is the cover of the "isPrime" function
 * @author Eyad Amer, Ahmad Dregat and Malak Marey
 */
public class  PrimeWrapper implements Runnable {
	long n; // the number that we want to check if it is a prime number
	boolean result; // Boolean variable

	/** constructor */
	PrimeWrapper(long n) {
		this.n = n;
	}

	/** the concurrent thread that start the function "isPrime" in Ex3_tester class */
	@Override 
	public void run() {
		result = Ex3_tester.isPrime(n);
	} // run

	/**
	 * main function
	 * @param args
	 */
	public static void main(String[] args) {
		long n = 7;
		PrimeWrapper isPrime = new PrimeWrapper(n);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<?> future = executor.submit(isPrime);

		try {
			future.get(3, TimeUnit.SECONDS);
		} catch (Exception e) { // if future could not get() the result
			future.cancel(true);
			executor.shutdownNow();
			System.out.println("isPrime went in to an infintie loop. Exiting...");
			System.exit(-1);
		}
		future.cancel(true);
		executor.shutdownNow();
		System.out.println("Is " + n + " a prime number: " + isPrime.result);
	} // main
} // PrimeWrapper