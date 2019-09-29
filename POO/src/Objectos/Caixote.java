package Objectos;



import java.io.Serializable;

import Diretrizes.*;
import Engine.SokobanGame;
import Interface.*;

public class Caixote extends AbstractObject implements ImageTile, MovableObject, Serializable {

	// ATRIBUTO EXTRA
	private static final long serialVersionUID = 1L;


	// CONSTRUTOR
	public Caixote(Position initialPosition, int level) {
		super(initialPosition, "Caixote", level);
		
	}

	// FUNÇÃO PARA MOVER
	public void move(Direction Dir) {
		Position newPosition = getPosition().plus(Dir.asVector());
		setPosition(newPosition);
	}

	// FUNÇÃO PARA EXECUTAR ACTION
	public void action(Direction Dir, AbstractObject obj) {
		SokobanGame skgame = SokobanGame.getInstance();
		Position r2 = this.getPosition().plus(Dir.asVector());
		for (int i = 0; i < skgame.getObjectos().size(); i++) {
			if (skgame.getObjectos().get(i).getPosition().equals(r2)) {
				if (skgame.getObjectos().get(i).isTransposable() == true 
						&& skgame.getObjectos().get(i).isMovable() == false
							&& skgame.getObjectos().get(i).isConsumable()==false) {
					this.move(Dir);
					skgame.getPlayer().move(Dir);
					skgame.setLevelCaixote(this,4);
					skgame.getObjectos().get(i).action(Dir,this);
					break;
				} else {
					this.move(Direction.NULO);
					skgame.getPlayer().move(Direction.NULO);
					break;
				}
			}
		}
	}
	

	// SABER SE É TRANSPOSTO
	@Override
	public boolean isTransposable() {
		return false;
	}

	// SABER SE PODE SER MOVIDO
	public boolean isMovable() {
		return true;
	}
	
	// SABER SE PODE SER DELETADO NO BURACO
	public boolean isDeletable() {
		return true;
	}

}
