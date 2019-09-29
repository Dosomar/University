package Objectos;

import java.io.Serializable;

import Diretrizes.*;
import Interface.*;

public abstract class AbstractObject implements ImageTile, Serializable {

	private static final long serialVersionUID = 1L;
	/*
	 * Atributos
	 */
	private Position position;
	private String imageName;
	private int Level;

	/*
	 * Construtor
	 */
	public AbstractObject(Position initialPosition, String imageName, int Level) {
		this.position = initialPosition;
		this.imageName = imageName;
		this.Level = Level;
	}

	/*
	 * Getters
	 */

	public String getName() {
		return imageName;
	}

	public Position getPosition() {
		return position;
	}

	public int getLevel() {
		return Level;
	}

	/*
	 * Setters
	 */
	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setLevel(int level) {
		this.Level = level;
	}

	/*
	 * Funções
	 */
	// SABER SE É TRANSPOSTO
	public boolean isTransposable() {
		return true;
	}
	// EXECUTAR A AÇÃO 
	public void action(Direction Dir, AbstractObject obj) {
		return;
	}
	// SABER SE PODE SER DELETADO NO BURACO
	public boolean isDeletable() {
		return false;
	}
	// SABER SE PODE SER MOVIDO
	public boolean isMovable() {
		return false;
	}
	//SABER SE PODE SER CONSUMIDO
	public boolean isConsumable(){
		return false;
	}

}
