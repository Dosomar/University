package Engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Points implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int posicao;
	private String nomeplayer;
	private int pontos;
	
	public Points (int posicao, String nomeplayer,int pontos ){
		this.posicao=posicao;
		this.nomeplayer=nomeplayer;
		this.pontos=pontos;
	}

	public int getPosicao() {
		return posicao;
	}

	public void setPosicao(int posicao) {
		this.posicao = posicao;
	}

	public String getNomeplayer() {
		return nomeplayer;
	}

	public void setNomeplayer(String nomeplayer) {
		this.nomeplayer = nomeplayer;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	
			
	// OBTER OS SCORES
	public static ArrayList<Points> getScore(File Score) {
		// Cria a Linha
		String Linha = "";
		// Obtem o File
		Scanner txt;
		try {
			txt = new Scanner(Score);
			// Criar a ArrayList
			ArrayList<Points> leitura = new ArrayList<Points>();
			// Leitor de txt
			while (txt.hasNextLine()) {
				Linha = txt .nextLine();
				String[] parts = Linha.split(" ");
				leitura.add(new Points(Integer.parseInt(parts[0]),parts[1],Integer.parseInt(parts[2])));
			}
			txt.close();
			return leitura;
		} catch (FileNotFoundException e) {
			System.out.println("Não Encontrou o Ficheiro");
			ArrayList<Points> leituravazia = new ArrayList<Points>();
			return leituravazia;
		}
	}
	
	// SET SCORES
	public static void setScore(File Score) {
		SokobanGame skgame = SokobanGame.getInstance();
		ArrayList<Points> Escrita = getScore(Score);
		Escrita.add(new Points(1, skgame.getNome(), skgame.getMoves()));
		Collections.sort(Escrita, new PointsComparator());
		boolean flag = false;
		// Escrita no txt
		try {
			PrintWriter Tabela = new PrintWriter(Score);
			for (int i = 0; !flag && i < Escrita.size(); i++) {
				Escrita.get(i).setPosicao(i + 1);
				Tabela.println(Escrita.get(i).getPosicao() + " " + Escrita.get(i).getNomeplayer() + " "
						+ Escrita.get(i).getPontos());
				if (i == 4) {
					flag = true;
				}
				System.out.println(Escrita.get(i).getPontos());
			}
			Tabela.close();
		}
		catch (IOException e) {
			System.out.println("Não Encontrou o Ficheiro");
		}
	}
	
	
	
	
}
