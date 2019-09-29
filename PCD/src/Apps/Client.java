package Apps;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Objects.Index;
import Objects.Request;

public class Client {

	private JFrame frame;
	private Socket s;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private ArrayList<Index> refs;
	private DefaultListModel<String> models;
	private JTextArea text;
	private JButton search;
	private int clientID = 0;

	public Client() {
		connectToServer();
		createInterface();
	}

	class InputThread extends Thread {

		@SuppressWarnings("unchecked")
		@Override
		public void run() {
			try {
				while (!isInterrupted()) {
					Object readObject = in.readObject();
					if (readObject instanceof ArrayList<?>) {
						refs = (ArrayList<Index>) readObject;
						SwingUtilities.invokeAndWait(new Runnable() {
							@Override
							public void run() {
								for (Index f : refs) {
									models.addElement(f.toString());
								}
								search.setEnabled(true);
							}
						});

					} else if (readObject instanceof String) {
						String news = (String) readObject;
						text.setText(news);
					}
				}
			} catch (IOException | ClassNotFoundException | InvocationTargetException | InterruptedException e) {
				System.out.println("Connection Lost!");
				System.exit(1);
			}
		}
	}

	private void createInterface() {

		frame = new JFrame();
		frame.setTitle("Googla-Lhe");
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					out.writeObject(new Request(3, 0, "End", clientID));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		frame.setLayout(new BorderLayout());

		JPanel panel1 = new JPanel();
		panel1.setLayout(new GridLayout());

		models = new DefaultListModel<>();

		JList<String> list = new JList<>(models);
		JScrollPane scroll1 = new JScrollPane(list);

		text = new JTextArea();
		text.setLineWrap(true);
		text.setWrapStyleWord(true);

		JScrollPane scroll2 = new JScrollPane(text);

		JTextField bar = new JTextField();

		search = new JButton("Search");

		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting()) {
					int selectedValue = list.getSelectedIndex();
					if (selectedValue != -1) {
						try {
							out.writeObject(new Request(2, refs.get(selectedValue).getPath(), " ", clientID));
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});

		search.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					models.clear();
					out.writeObject(new Request(1, 0, bar.getText(), clientID));
					search.setEnabled(false);
				} catch (IOException e) {
					e.printStackTrace();
				}
				text.setText("");
			}
		});

		panel1.add(bar);
		panel1.add(search);

		frame.add(panel1, BorderLayout.NORTH);
		frame.add(scroll1, BorderLayout.WEST);
		frame.add(scroll2, BorderLayout.CENTER);
		frame.pack();
	}

	private void connectToServer() {
		boolean scanning = true;
		while (scanning) {
			try {
				s = new Socket("localHost", 8080);
				out = new ObjectOutputStream(s.getOutputStream());
				out.writeObject(clientID);
				in = new ObjectInputStream(s.getInputStream());
				clientID = (int) in.readObject();
				System.out.println("Client given ID: " + clientID);
				InputThread reader = new InputThread();
				reader.start();
				System.out.println("Connection Sucessful!");
				scanning = false;
			} catch (IOException | ClassNotFoundException e) {
				System.out.println("Connection failed! trying again");
				try {
					System.out.println("Waiting...");
					Thread.sleep(100);
				} catch (InterruptedException e1) {
					System.out.println("Connection Lost");
					System.exit(0);
				}
			}
		}
	}

	public void open() {
		frame.setVisible(true);
		frame.setSize(1000, 1200);
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		Client b = new Client();
		b.open();
	}
}
