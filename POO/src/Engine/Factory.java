package Engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import Diretrizes.*;
import Interface.ImageTile;
import Objectos.*;

public class Factory {

	// DATABASE NIVEIS
	public static File[] dataNiveis() {
		File[] files = new File("levels").listFiles();
		return files;
	}

	/*
	 * OBTEM OS OBJECTOS DO NIVEL
	 */
	public static ArrayList<AbstractObject> nivel(File Nivel, SokobanGame skgame) {
		// Cria a Linha
		String Linha = "";
		// Obtem o File
		Scanner txt;
		try {
			txt = new Scanner(Nivel);
			// Iterador de Coluna
			int y = 0;
			// Criar a ArrayList
			ArrayList<AbstractObject> leitura = new ArrayList<AbstractObject>();
			// Leitor de txt
			while (txt.hasNextLine()) {
				Linha = txt.nextLine();
				defineTile(leitura, Linha, y, skgame);
				y++;
			}
			txt.close();
			return leitura;
		} catch (FileNotFoundException e) {
			System.out.println("Não Encontrou o Ficheiro Level");
			ArrayList<AbstractObject> leituraVazio = new ArrayList<AbstractObject>();
			return leituraVazio;
		}
	}

	/*
	 * DEFINE OBJECTOS CONSOANTE O CARACTER
	 */
	public static void defineTile(ArrayList<AbstractObject> leitura, String Linha, int y,SokobanGame skgame) {
		// Percorrer Caracter a Caracter da Linha
		for (int x = 0; x != 10; x++) {
			// Obter o Caracter
			char Caracter = Linha.charAt(x);
			// Paredes
			if (Caracter == ('#')) {
				leitura.add(new Parede(new Position(x, y)));
			}
			// Empilhadora
			else if (Caracter == ('E')) {
				Empilhadora player = new Empilhadora(new Position(x, y));
				leitura.add(player);
				skgame.setPlayer(player);
				leitura.add(new Chao(new Position(x, y)));
			}
			// Bateria
			else if (Caracter == ('b')) {
				leitura.add(new Bateria(new Position(x, y)));
				leitura.add(new Chao(new Position(x, y)));
			}
			// Alvo
			else if (Caracter == ('X')) {
				leitura.add(new Alvo(new Position(x, y)));
			}
			// Caixote
			else if (Caracter == ('C')) {
				leitura.add(new Caixote(new Position(x, y), 3));
				leitura.add(new Chao(new Position(x, y)));
			}
			// SMALLSTONE
			else if (Caracter == ('S')) {
				leitura.add(new SmallStone(new Position(x, y)));
				leitura.add(new Chao(new Position(x, y)));
			}
			// BIGSTONE
			else if (Caracter == ('L')) {
				leitura.add(new BigStone(new Position(x, y)));
				leitura.add(new Chao(new Position(x, y)));
			}
			// Buraco
			else if (Caracter == ('O')) {
				leitura.add(new Buraco(new Position(x, y)));
			}
			// Chao
			else {
				leitura.add(new Chao(new Position(x, y)));
			}
		}
	}
	
	/*
	 * ORGANIZAÇÃO DE DADOS
	 */
	// ORGANIZA OS OBJECTOS DO NIVEL EM IMAGETILES
	public static ArrayList<ImageTile> toTiles(ArrayList<AbstractObject> In) {
		ArrayList<ImageTile> tiles = new ArrayList<ImageTile>();
		for (int i = 0; i < In.size(); i++) {
			tiles.add(In.get(i));
		}
		return tiles;
	}

	
	
}
