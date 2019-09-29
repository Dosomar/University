package Apps;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import Connections.ConnectionThread;
import ObjectManagers.RequestQueue;
import ObjectManagers.TaskBarrier;
import ObjectManagers.TaskQueue;
import Objects.Index;
import Objects.Request;
import Objects.Task;

public class Server {

	private ServerSocket serverSocket; 
	private final int PORT = 8080;
	private ArrayList<ConnectionThread> clients;
	private ArrayList<ConnectionThread> workers;
	private TaskQueue<Task> tasks;
	private RequestQueue<Request> reqs;
	private TaskBarrier taskbarrier;

	private Manager manager;
	private File[] files;

	public Server() throws IOException {
		loadFiles();
		this.serverSocket = new ServerSocket(PORT);
		initialize();
	}
	
	public void loadFiles() {
		files = new File("pasta_notícias").listFiles();
		System.out.println("Files Loaded!");
	}
	
	public void initialize() {
		clients = new ArrayList<>();
		workers = new ArrayList<>();
		reqs = new RequestQueue<Request>();
		tasks = new TaskQueue<Task>();
		taskbarrier = new TaskBarrier(files.length);
		manager = new Manager();
		manager.start();
	}

	class Manager extends Thread {

		public Manager() {
			super();
		}

		@Override
		public void run() {
			while (!isInterrupted()) {
				Request request = null;
				try {
					request = reqs.poll();
					taskbarrier.clear();
					System.out.println("Olá saquei a Request: " + request);
					for (int i = 0; i < files.length; i++) {
						tasks.addTask(new Task(i, request.getUserID(), files[i], request.getWord()));
					}
					System.out.println("Tasks added");
					System.out.println("Waiting for All Tasks to be done...");
					taskbarrier.barrierWait();
					System.out.println("Tasks Done!");
					if(taskbarrier.getIndexes().size() == 0) {
						taskbarrier.addIndex(new Index("Error No Matches found!", 0, 0));
					}
					for (int i = 0; i < clients.size(); i++)
						if (clients.get(i).getClientID() == request.getUserID()) {
							ArrayList<Index> toSend = taskbarrier.getIndexes();
							Collections.sort(toSend);
							clients.get(i).getOut().writeObject(toSend);
							System.out.println("Results Sent");
						}
				} catch (Exception e) {
					System.out.println("The Request Couldn't Be Accept");
				}
			}
		}
	}

	class ClientConnection extends ConnectionThread {

		public ClientConnection(Socket socket, ObjectInputStream in, ObjectOutputStream out, int clientID)
				throws IOException {
			super(socket, in, out, clientID);
		}

		@Override
		public synchronized void run() {
			try {
				while (!isInterrupted()) {
					Request request = (Request) getIn().readObject();
					if (request.getType() == 1) {
						System.out.println("Received The word: " + request.getWord() + " to search");
						reqs.addRequest(request);
					} else if (request.getType() == 2) {
						getOut().writeObject(openFile(request.getPath()));
					} else if (request.getType() == 3) {
						System.out.println("The Client: " + request.getUserID() + " as Disconnected");
						interrupt();
					}
				}
			} catch (IOException | ClassNotFoundException e) {
				interrupt();
			}
		}
	}

	class WorkerConnection extends ConnectionThread{

		public WorkerConnection(Socket socket, ObjectInputStream in, ObjectOutputStream out, int clientID) throws IOException {
			super(socket, in, out, clientID);
		}

		@Override
		public synchronized void run() {
			while(!isInterrupted()) {
				try {
					getOut().writeObject(tasks.poll());
					Index index = (Index) getIn().readObject();
					if (index == null) {
						taskbarrier.Increment();
					} else {
						taskbarrier.addIndex(index);
						taskbarrier.Increment();
					}
				} catch (Exception e) {
					interrupt();
				}
			}

		}

	}

	private void startServing() {
		System.out.println(serverSocket + "\n" + "Waiting for Connections...");
		int clientID = 1;
		while (true) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
				int connectionID = (int) in.readObject();
				if (connectionID == 0) {
					out.writeObject(clientID);
					ClientConnection client = new ClientConnection(socket, in, out, clientID);
					clients.add(client);
					client.start();
					clientID++;
					System.out.println("Client accepted!");
				}if(connectionID == -1) {
					WorkerConnection worker = new WorkerConnection(socket, in, out, -1);
					workers.add(worker);
					worker.start();
					System.out.println("Worker as been added");
				}
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Could Not Accept a New Client.");
			}
		}
	}

	private String openFile(int path) {
		String text = "";
		File f = files[path];
		try (Scanner s = new Scanner(f, "UTF-8")) {
			while (s.hasNextLine()) {
				text += (s.nextLine() + "\n");
			}
		} catch (FileNotFoundException e) {
			System.out.println("The File Requested to Open Doesn't Exist");
		}
		return text;
	}

	public static void main(String[] args) throws IOException {
		Server s = new Server();
		s.startServing();
	}
}
