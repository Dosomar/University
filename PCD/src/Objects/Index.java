package Objects;


import java.io.Serializable;

//Classe Externa utilizada para guardar a referencia para cada noticia

public class Index implements Serializable, Comparable<Index> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String title;		//Titulo da Noticia
	private int path;		//Caminho para abrir o Ficheiro
	private int wordMatches;	//Número de Ocorrencias das Palavras
	
	public Index(String title, int path, int wordMatches){
		this.title = title;
		this.path = path;
		this.wordMatches = wordMatches;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getPath() {
		return path;
	}

	@Override
	public String toString() {
		return wordMatches + " - " + title;
	}

	public int getWordMatches() {
		return wordMatches;
	}

	@Override
	public int compareTo(Index arg0) {
		return arg0.getWordMatches() - this.wordMatches;
	}
}
