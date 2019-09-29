package Connections;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ConnectionThread extends Thread {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private int clientID;

	public ConnectionThread(Socket socket, ObjectInputStream in, ObjectOutputStream out, int clientID)
			throws IOException {
		this.socket = socket;
		this.in = in;
		this.out = out;
		this.clientID = clientID;
	}

	public int getClientID() {
		return clientID;
	}

	public void setClientID(int clientID) {
		this.clientID = clientID;
	}

	public ObjectOutputStream getOut() {
		return out;
	}

	public ObjectInputStream getIn() {
		return in;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public void setOut(ObjectOutputStream out) {
		this.out = out;
	}

	public void setIn(ObjectInputStream in) {
		this.in = in;
	}

}