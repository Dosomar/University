package Engine;

import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

public class Menu{
	

	public static String name(){
		String NickGirls = JOptionPane.showInputDialog("Introduza o nome");
		if (NickGirls.equals("")){NickGirls = "Desconhecido";}
		return NickGirls;
	}
	
	public static void start() {
		setMenuColor(205, 250, 255);
		File file = new File("./Sounds/Start.wav");
		try {
			   Clip clip = AudioSystem.getClip();
			   clip.open(AudioSystem.getAudioInputStream(file));
			   clip.start();
			   ImageIcon Pepe = new ImageIcon("./menus/Start.png");
				int i = JOptionPane.showConfirmDialog(null,
						"Bem Vindo" + "\n" + "Yes,para começar Novo Jogo" + "\n" + "No, para sair", 
						"Menu", 1, JOptionPane.INFORMATION_MESSAGE, Pepe);
				if (i == 0) {
					SokobanGame.getInstance();
					clip.close();
				} else if (i == 1) {
					SokobanGame.getInstance();
					SokobanGame.loadGame();
					clip.close();
				} else {
					System.out.println("Cancelado");
					System.exit(0);
					clip.close();
				}
			  } catch (Exception e) {
			   System.err.println(e.getMessage());
			  }
	}
	public static void main() {
		ImageIcon Pepe = new ImageIcon("./menus/Main.png");
		JOptionPane.showMessageDialog(null,
				"Menu:" + "\n" + "Setas para mover" +"\n"+"Bateria = 100% de Energia"+"\n"+"Passar de nível = +25% de Energia" + "\n" + "S - Save Game" + "\n" + "L - Load Game"
						+"\n"+ "R - Restart(Moves e Energia não dão Reset)" +"\n" +"T - Top 5 do nível Actual" + "\n" + "C - Show Credits",
				"Menu Principal", JOptionPane.INFORMATION_MESSAGE, Pepe);
	}
	
	public static void credits() {
		ImageIcon Pepe = new ImageIcon("./menus/Credits.png");
		JOptionPane.showMessageDialog(null,
				"Jogo feito por:"+"\n"+"Fábio Cardoso"+"\n"+"Carlos Guerra"+"\n"+"Alunos do ISCTE-IUL",
				"Credits", JOptionPane.INFORMATION_MESSAGE, Pepe);
	}
	
	public static void scores() {
		File[] files = new File("Scores").listFiles();
		ArrayList<Points> Tabela =  Points.getScore(files[SokobanGame.getInstance().getLevel()]);
		ImageIcon Pepe = new ImageIcon("./menus/TableMenu.png");
		String Table = "Top 5 Scores:";
		for (int i = 0; i < Tabela.size();i++){
			Table+=("\n"+"Pos."+Tabela.get(i).getPosicao() + " "+Tabela.get(i).getNomeplayer()+" Points: "+Tabela.get(i).getPontos());
		}
		System.out.println(Table);
		JOptionPane.showMessageDialog(null,
				Table,
				"Tabela de Pontuações", JOptionPane.INFORMATION_MESSAGE, Pepe);
	}

	
	public static void end() {
		ImageIcon Pepe = new ImageIcon("./menus/End.png");
		Menu.music(new File("./Sounds/Finish.wav"));
		int i = JOptionPane.showConfirmDialog(null,
				"Fim do jogo!" + "\n" +"Obrigado por ter jogado!"+"\n"+ "Deseja reiniciar?", 
				"End", 1, JOptionPane.INFORMATION_MESSAGE, Pepe);
		if (i == 0) {
			SokobanGame.getInstance().reStart();
		} else {
			System.out.println("Obrigado por ter jogado!");
			System.exit(0);
		}
	}
	
	public static void gameOver() {
		File file = new File("./Sounds/Death.wav");
		  try {
			   Clip clip = AudioSystem.getClip();
			   clip.open(AudioSystem.getAudioInputStream(file));
			   clip.start();
			   ImageIcon Pepe = new ImageIcon("./menus/GameOver.png");
				int i = JOptionPane.showConfirmDialog(null,
						"Game Over" + "\n" +"Obrigado por ter jogado!"+"\n"+ "Deseja reiniciar?", 
						"Game Over", 1, JOptionPane.INFORMATION_MESSAGE, Pepe);
				if (i == 0) {
					SokobanGame.getInstance().reStart();
					clip.close();
				} else {
					System.out.println("Obrigado por ter jogado!");
					System.exit(0);
					clip.close();
				}
			  } catch (Exception e) {
			   System.err.println(e.getMessage());
			  }
	}

	public static void setMenuColor(int r, int g, int b) {
		UIManager.put("OptionPane.background", new ColorUIResource(r, g, b));
		UIManager.put("Panel.background", new ColorUIResource(r, g, b));	
	}

	public static void music(File file) {	
		  try {
			   Clip clip = AudioSystem.getClip();
			   clip.open(AudioSystem.getAudioInputStream(file));
			   clip.start();
			  } catch (Exception e) {
			   System.err.println(e.getMessage());
			  }
	}
	
	
}
