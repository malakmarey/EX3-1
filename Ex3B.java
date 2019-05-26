
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * The class contains methods of: create files, delete files and count the number of
 * lines in all files together in three different ways: 
 * with threads, without threads or threadPoll.
 * @author Eyad Amer, Ahmad Dregat and Malak Marey
 */
public class Ex3B {

	/**
	 * main function check what way is quicker for create numfiles and calculate 
	 * the sum of lines in them and finally delete them.
	 * @param args
	 * @throws Exception - handle the exception that may be thrown in the functions above.
	 */
	public static void main(String[] args) throws Exception {
		int num = 1000;

		countLinesThread(num);
		countLinesOneProcess(num);
		countLinesThreadPool(num);
	} // main

	/**
	 * the function createFiles gets number n, create n files, 
	 * and write a random number of lines of "Hello World".
	 * @param n - the number of the files to create.
	 * @return a string array of file that we created.
	 */
	public static String[] createFiles(int n){
		
		Random r = new Random(123);

		int a = 1;

		String[] array = new String[n];
		for (int i = 0; i < n; i++)
		{	
			int Lines = r.nextInt(n); 

			StringBuilder stringBuilder = new StringBuilder();
			for (int j = 0; j < Lines; j++) 
				stringBuilder.append("Hello World" + System.lineSeparator());

			array[i] = "File_" + (a++) + ".txt";
			try {
				FileWriter temp = new FileWriter(array[i].toString());
				temp.write(stringBuilder.toString());
				temp.close();

			} catch (IOException e) {
				System.out.println("Cant create the file");
				e.printStackTrace();
			}
			stringBuilder.delete(0, stringBuilder.length());
		} // loop
		return array;
	} // createFiles

	/**
	 * the function delete the files that we created in "createFiles".
	 * @param String fileNames - string array that we got from "createFiles".
	 */
	public static void deleteFiles(String[] fileNames) {
		int temp = fileNames.length;
		for (int i = 0; i < temp; i++) {
			File temp1 = new File(fileNames[i]);
			try{ 
				temp1.delete();
			}
			catch (Exception e) {
				System.out.println("error");
			}
		} // loop
	} // deleteFiles


	/**
	 * the function uses the "createFiles" and "deleteFiles" 
	 * to create num files and calculate how many lines they have 
	 * and the time that it takes.
	 * @param numFiles - the number of file to create.
	 * @throws InterruptedException - exception that might be thrown.
	 * @throws ExecutionException - exception that might be thrown.
	 */
	public static void countLinesThread(int numFiles) throws InterruptedException, ExecutionException {
		String[] filenames = createFiles(numFiles);

		long start = System.currentTimeMillis();
		ExecutorService executor = Executors.newFixedThreadPool(numFiles);
		Integer result2 = 0;
		for (int i = 0; i < numFiles; i++) {
			Callable<Integer> worker = new LineCounter(filenames[i]);
			Future<Integer> x1 = executor.submit(worker);
			result2 += x1.get();
		}
		executor.shutdown();
		long stop = System.currentTimeMillis();
		System.out.println("Threads time = " + (stop - start) + "ms, lines = " + result2);

		deleteFiles(filenames);
	} // countLinesThread


	/**
	 * the function creates files with random lines 
	 * and checks the total number of lines by pool thread for all the files.
	 * @param num - the number of file to create.
	 */
	public static void countLinesThreadPool(int num) {
		String fileNames[] = createFiles(num);
		long start = System.currentTimeMillis();
		ExecutorService executorpool = Executors.newCachedThreadPool();
		ArrayList<Future<Integer>> all_lines = new ArrayList<Future<Integer>>();
		Integer result = 0;

		for (int i = 0; i < num; i++) {
			Callable<Integer> worker = new LineCounter(fileNames[i]);
			Future<Integer> x = executorpool.submit(worker);
			all_lines.add(x);
		}
		executorpool.shutdown();
		long stop = System.currentTimeMillis();

		for(int i = 0; i < fileNames.length; i++) {
			try {
				result += all_lines.get(i).get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.print("Thread Pool Time = " +  (stop - start) + "ms, lines = " + result);
		deleteFiles(fileNames);
	} // countLinesThreadPool


	/**
	 * the function uses the "createFiles" and "deleteFiles" 
	 * to create numfiles and calculate how many lines 
	 * they have and the time that it takes.
	 * @param numFiles - the number of file to create
	 * @throws Exception - exception that might be thrown.
	 */
	public static void countLinesOneProcess(int numFiles) throws Exception {
		String[] array = createFiles(numFiles);
		Integer result = new Integer(0);
		long start = System.currentTimeMillis();
		for (int i = 0; i < numFiles; i++)
		{
			LineCounter linecounter = new LineCounter(array[i]);
			result += linecounter.call();
		}

		long stop = System.currentTimeMillis();
		System.out.println("One Process Time = " + (stop - start) + "ms, lines = " + result);

		deleteFiles(array);

	} // countLinesOneProcess
} // Ex3B

