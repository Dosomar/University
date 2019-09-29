package Apps;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Objects.Task;

public class Worker extends Thread{
	
	private int workerID = -1;
	private Socket s;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	public Worker() {
	}

		
	@Override
	public void run() {
		while(!isInterrupted()) {
			try {
				Task toDo = (Task) in.readObject();
				out.writeObject(toDo.call());
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("Connection Lost!");
				System.out.println("Attempting to Reconnect...");
				connectToServer();
			} catch (Exception e) {
				System.out.println("Task not assigned");
			}
		}
	}

	private void connectToServer() {
		boolean scanning = true;
		while (scanning) {
			try {
				s = new Socket("localHost", 8080);
				out = new ObjectOutputStream(s.getOutputStream());
				out.writeObject(workerID);
				in = new ObjectInputStream(s.getInputStream());
				System.out.println("Connection Sucessful!");
				scanning = false;
			} catch (IOException e) {
				System.out.println("Connection failed! trying again");
				try {
					System.out.println("Waiting...");
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}	
	}

	public static void main(String[] args) {
		for(int i = 0; i<100; i++) {
			Worker w = new Worker();
			w.connectToServer();
			w.start();
		}
	}
}
