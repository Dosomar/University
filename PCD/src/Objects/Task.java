package Objects;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;

@SuppressWarnings("rawtypes")
public class Task implements Serializable, Callable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int path;
	private int clienteID;
	private String searchWord;
	private File searchFile;

	public Task(int path, int clienteID, File searchFile, String searchWord) {
		this.path = path;
		this.clienteID = clienteID;
		this.searchFile = searchFile;
		this.searchWord = searchWord;
	}

	public int getPath() {
		return path;
	}

	public int getClienteID() {
		return clienteID;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public File getSearchFile() {
		return searchFile;
	}

	@Override
	public Object call() throws Exception {
		String title = "";
		String line = "";
		String aux = "";
		int words = 0;
		int found = 0;
		Scanner s = new Scanner(getSearchFile(), "UTF-8");
		title = s.nextLine();
		while (s.hasNextLine()) {
			line = title + s.nextLine();
			found = line.indexOf(searchWord);
			while(found != -1) {
				words++;
				line = line.substring(found+1);
				found = line.indexOf(searchWord);
			}
			if (words != 0) {
				Index ref = new Index(title, getPath(), words);
				s.close();
				return ref;
			}
		}
		s.close();
		return null;
	}
}
