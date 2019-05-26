
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;

/**
 * The class represents the thread that calculates the number of lines of the file
 * @author Eyad Amer, Ahmad Dregat and Malak Marey
 */
public class LineCounter implements Callable<Integer>{

	String nameoffile; // the name of file

	/**
	 * constructor
	 * @param String name - the name of file.
	 */
	public LineCounter(String name) {
		nameoffile = name;
	}

	/** 
	 * implementing the Callable interface
	 * the function return the number of lines of the file 
	 */
	@Override
	public Integer call() throws Exception {

		Integer numoflines = 0; // the number of lines of the file

		try {
			FileReader reader = new FileReader(nameoffile);
			BufferedReader br = new BufferedReader(reader);

			while(br.readLine() != null)
				numoflines++;

			br.close();
			reader.close();

		} catch (IOException e) {
			System.out.println("Cant open the file...");
			e.printStackTrace();
		}

		return numoflines;
	} // call
} // LineCounter

