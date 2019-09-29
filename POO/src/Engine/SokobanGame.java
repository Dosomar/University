package Engine;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;

import Diretrizes.Direction;
import Interface.ImageMatrixGUI;
import Objectos.AbstractObject;
import Objectos.CompareObjects;
import Objectos.Empilhadora;



public class SokobanGame implements Observer, Serializable {

	/*
	 * ATRIBUTOS
	 */
	private int level = 0;
	private int Moves = 0;
	private File[] Niveis;
	private int Energia = 100;
	private Empilhadora player;
	private String nome;
	private transient ImageMatrixGUI GUI;
	private ArrayList<AbstractObject> objectos;
	private static SokobanGame INSTANCE = null;
	private static final long serialVersionUID = 1L;
	
	/*
	 * CONSTRUTOR
	 */
	
	private SokobanGame() {
		nome = Menu.name();
		Niveis = Factory.dataNiveis();
		objectos = Factory.nivel(Niveis[0],this);
		Collections.sort(objectos, new CompareObjects());
		GUI = ImageMatrixGUI.getInstance();
		GUI.setName("Sokoban Game   Jogador: "  +  nome);
		GUI.addImages(Factory.toTiles(objectos));
		GUI.addObserver(this);
		GUI.go();
		setMenu();
		GUI.update();
	}
	  
	/*
	 * JOGADAS
	 */
	
	@Override
	public void update(Observable arg0, Object arg1) {
		int lastKeyPressed = (Integer) arg1;
		System.out.println("Key pressed " + lastKeyPressed);
		switch (lastKeyPressed) {
		case KeyEvent.VK_UP:
			player.setImageName("Empilhadora_U");
			player.interage(Direction.UP);
			break;
		case KeyEvent.VK_DOWN:
			player.setImageName("Empilhadora_D");
			player.interage(Direction.DOWN);
			break;
		case KeyEvent.VK_LEFT:
			player.setImageName("Empilhadora_L");
			player.interage(Direction.LEFT);
			break;
		case KeyEvent.VK_RIGHT:
			player.setImageName("Empilhadora_R");
			player.interage(Direction.RIGHT);
			break;
		case KeyEvent.VK_S:
			saveGame();
			break;
		case KeyEvent.VK_T:
			Menu.scores();
			break;
		case KeyEvent.VK_L:
			loadGame();
			break;
		case KeyEvent.VK_C:
			Menu.credits();
			break;
		case KeyEvent.VK_ESCAPE:
			Menu.main();
			break;
		case KeyEvent.VK_R:
			this.objectos = Factory.nivel(Niveis[level],this);
			Collections.sort(objectos, new CompareObjects());
			refresh();
			break;
		}
	}
	
	
	/*
	 * GETTERS AND SETTERS
	 */
	
	// OBTER A ARRAY DE OBJECTOS
	public ArrayList<AbstractObject> getObjectos() {
		return objectos;
	}
	// SET A ARRAY DE OBJECTOS
	public void setObjectos(ArrayList<AbstractObject> objectos) {
		this.objectos = objectos;
		GUI.clearImages();
		GUI.addImages(Factory.toTiles(objectos));
	}
	// OBTER O PLAYER
	public Empilhadora getPlayer() {
		return player;
	}
	// SET PLAYER
	public void setPlayer(Empilhadora player) {
		this.player = player;
	}
	// OBTER O LEVEL
	public int getLevel() {
		return level;
	}
	// SET O LEVEL
	public void setLevel(int level) {
		this.level = level;
	}
	// SET LEVEL DO CAIXOTE
	public void setLevelCaixote(AbstractObject obj, int n) {
		for (int i = 0; i < objectos.size(); i++) {
			if (objectos.get(i).getPosition().equals(obj.getPosition())) {
				objectos.get(i).setLevel(n);
				break;
			}
		}
		Collections.sort(objectos, new CompareObjects());
		refresh();
	}
	// GET OS NIVEIS
	public File[] getNiveis() {
		return Niveis;
	}
	// SET OS NIVEIS
	public void setNiveis(File[] niveis) {
		Niveis = niveis;
	}
	// GET GUI
	public ImageMatrixGUI getGUI() {
		return GUI;
	}
	// SET GUI
	public void setGUI(ImageMatrixGUI gUI) {
		GUI = gUI;
	}
	// SET SERIAL
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	// OBTER A INSTANCE
	public static SokobanGame getInstance() {
		if(INSTANCE == null) {
	         INSTANCE = new SokobanGame();
	      }
	      return INSTANCE;
	}
	//OBTER MOVES
	public int getMoves() {
		return Moves;
	}
	// SET MOVES
	public void setMoves(int moves) {
		Moves = moves;
	}
	//OBTER ENERGIA
	public int getEnergia() {
		return Energia;
	}
	// SET ENERGIA
	public void setEnergia(int energia) {
		Energia = energia;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	
	/*
	 * LOAD/SAVE/RESTART/RODAPÉ/MUDAR DE NIVEL/REFRESH
	 */

	// CARREGAR UM JOGO
	public static SokobanGame loadGame(){
		SokobanGame Load = null;
		try {
			FileInputStream readfile = new FileInputStream("./Save/Array.ser");
			ObjectInputStream in = new ObjectInputStream(readfile);
			Load = (SokobanGame) in.readObject();
			System.out.println();
			in.close();
			System.out.println("Loaded");
			INSTANCE.setLevel(Load.getLevel());
			INSTANCE.setNiveis(Load.getNiveis());
			INSTANCE.setObjectos(Load.getObjectos());
			INSTANCE.setPlayer(Load.getPlayer());
			INSTANCE.setMoves(Load.getMoves());
			INSTANCE.setEnergia(Load.getEnergia());
			INSTANCE.getGUI().setName("Sokoban Game   Jogador: " + Load.nome);
			INSTANCE.getGUI().addImages(Factory.toTiles(Load.getObjectos()));
			INSTANCE.getGUI().addObserver(INSTANCE);
			INSTANCE.getGUI().go();
			INSTANCE.setMenu();
			INSTANCE.getGUI().update();
			return INSTANCE;
		} catch (IOException e){
			System.out.println("Msg: "+ e.getMessage());
		} catch (ClassNotFoundException e){
			throw new IllegalStateException("Error ao Carregar o Jogo");
		}
		return INSTANCE;
	}

	// GRAVAR UM JOGO
	public void saveGame() {
		try {
			FileOutputStream Save = new FileOutputStream("./Save/Array.ser");
			ObjectOutputStream out = new ObjectOutputStream(Save);
			out.writeObject(this);
			out.close();
			System.out.println("Saved");
		} catch (IOException e) {
			e.printStackTrace();
			throw new IllegalStateException("Error ao Gravar o Jogo");
		}
	}
	// RECOMEÇAR
	public void reStart() {
		INSTANCE.setLevel(0);
		INSTANCE.setNiveis(Factory.dataNiveis());
		INSTANCE.setObjectos(Factory.nivel(Niveis[0], SokobanGame.getInstance()));
		INSTANCE.setMoves(0);
		INSTANCE.setEnergia(100);
		Collections.sort(SokobanGame.getInstance().getObjectos(), new CompareObjects());
		INSTANCE.getGUI().setName("Sokoban Game   Jogador: " + nome);
		INSTANCE.getGUI().clearImages();
		INSTANCE.getGUI().addImages(Factory.toTiles(INSTANCE.getObjectos()));
		INSTANCE.getGUI().addObserver(INSTANCE);
		INSTANCE.getGUI().go();
		INSTANCE.getGUI().update();
		setMenu();
	}
	// REMOVER UM OBJECTO DA ARRAY
	public void remObject(AbstractObject obj) {
		for (int i = 0; i < objectos.size(); i++) {
			if (objectos.get(i).equals(obj)) {
				objectos.remove(i);
			}
		}
		refresh();
	}
	// RODAPÉ
	public void setMenu() {
		GUI.setStatusMessage("Menu: ESC   " + "Nivel:" + getLevel() + "   " + "Moves:" + getMoves() + "    "
				+ "Energia: " + getEnergia() + "%");
		GUI.update();
	}
	// MUDAR O NIVEL
	public void checkComplete() {
		int aux1 = 0;
		int aux2 = 0;
		for (int i = 0; i < objectos.size(); i++) {
			if (objectos.get(i).getName().equals("Caixote")) {
				aux1++;
			}
			if (objectos.get(i).getName().equals("Caixote") && objectos.get(i).getLevel() == 5) {
				aux2++;
			}
		}
		if (aux1 == aux2) {
			if (level != Niveis.length - 1) {
				File[] files = new File("Scores").listFiles();
				Points.setScore(files[level]);
				this.level++;
				this.objectos = Factory.nivel(Niveis[level], this);
				Collections.sort(objectos, new CompareObjects());
				setMoves(0);
				if(Energia < 75){Energia+=25;}
				else{Energia=100;}
				setMenu();
				refresh();
				Menu.music(new File("./Sounds/LevelUP.wav"));
			} else {
				Menu.end();
			}
		}
	}
	// REFRESH
	public void refresh() {
		GUI.clearImages();
		GUI.addImages(Factory.toTiles(objectos));
		GUI.update();
	}
	
	
}

