package Objects;

import java.io.Serializable;

public class Request implements Serializable {

	private static final long serialVersionUID = 1L;
	private int type;
	private int path;
	private String word;
	private int userID;

	/*
	 * Type 1 = Search a Word; Type 2 = Open Request; Type 3 = Exit;
	 */

	public Request(int type, int path, String word, int userID) {
		this.type = type;
		this.path = path;
		this.word = word;
		this.userID = userID;
	}

	public synchronized int getUserID() {
		return userID;
	}

	public synchronized int getType() {
		return type;
	}

	public synchronized int getPath() {
		return path;
	}

	public synchronized void setPath(int path) {
		this.path = path;
	}

	public synchronized String getWord() {
		return word;
	}

}
