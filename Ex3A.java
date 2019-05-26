
/**
 * The class represents: calculation of prime numbers, 
 * the class with the function that receives a natural number and duration, 
 * and calculates whether the natural number is prime 
 * as long as the time allotted for calculating the time has passed the function to throw an exception.
 * @author Eyad Amer, Ahmad Dregat and Malak Marey
 */
public class Ex3A implements Runnable{

	static long number; // to calculates if the number is prime
	static Boolean result = null; // Boolean variable - if the Time passed

	/**
	 * The function receives a natural number and duration, 
	 * and calculates whether the natural number is prime 
	 * as long as the time allotted for calculating the time has passed 
	 * the function to throw an exception.
	 * @param n - the number that we want to check if it is a prime number
	 * @param maxTime - the wrapper time in which the function should work and not pass over it 
	 * @return - true if the number is prime, false if not
	 * @throws RuntimeException - if the Time "maxTime" passed
	 */
	public boolean isPrime(long n, double maxTime) throws RuntimeException {

		number = n;
		long time = (long)(maxTime * 1000); // time in seconds

		Thread prime = new Thread(this);
		prime.setDaemon(true);

		// Calculate the time
		long start = System.currentTimeMillis();
		prime.start();
		long end = System.currentTimeMillis();

		while((end - start) < time){
			if (result != null)
				return result;
			end = System.currentTimeMillis();
		} // while

		throw new RuntimeException();
	} // isPrime

	/** the concurrent thread that start the function "isPrime" in Ex3_tester class */
	@Override
	public void run() {
		result = Ex3_tester.isPrime(number);
	} // run
} // Ex3A


