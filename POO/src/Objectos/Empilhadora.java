package Objectos;

import java.io.Serializable;

import Diretrizes.*;
import Engine.Menu;
import Engine.SokobanGame;
import Interface.*;

public class Empilhadora extends AbstractObject implements ImageTile, MovableObject, Serializable {

	
	// ATRIBUTO EXTRA
	private static final long serialVersionUID = 1L;

	
	// CONSTRUTOR
	public Empilhadora(Position initialPosition) {
		super(initialPosition, "Empilhadora_U", 1);
		
	}

	// FUNÇÃO PARA MOVER
	public void move(Direction Dir) {
		SokobanGame sk = SokobanGame.getInstance();
		Position newPosition = getPosition().plus(Dir.asVector());
		setPosition(newPosition);
		if(Dir != Direction.NULO){sk.setMoves(sk.getMoves()+1); sk.setEnergia(sk.getEnergia()-1);sk.setMenu();}
		if(sk.getEnergia()==0){sk.setMenu();Menu.gameOver();}
		ImageMatrixGUI.getInstance().update();
	}

	// FUNÇÃO QUE TRATA DAS INTERÇÕES
	public void interage(Direction Dir) {
		SokobanGame skgame = SokobanGame.getInstance();
		Position r1 = skgame.getPlayer().getPosition().plus(Dir.asVector());
		for (int i = 0; i < skgame.getObjectos().size(); i++) {
			if (skgame.getObjectos().get(i).getPosition().equals(r1)) {
				if(skgame.getObjectos().get(i).isTransposable() == false
						&& skgame.getObjectos().get(i).isMovable() == true ) {
					skgame.getObjectos().get(i).action(Dir, this);
					break;
				}
				else if(skgame.getObjectos().get(i).isTransposable() == false 
						&& skgame.getObjectos().get(i).isMovable() == false ){
					this.move(Direction.NULO);
					break;
				}
				else {
					skgame.getObjectos().get(i).action(Dir, this);
					this.move(Dir);
					break;
				} 
			}
		}
	}
	
	// SABER SE PODE SER DELETADO NO BURACO
		public boolean isDeletable() {
			return true;
		}
}

