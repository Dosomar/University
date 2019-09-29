package Objectos;

import java.io.Serializable;

import Diretrizes.Direction;
import Diretrizes.Position;
import Engine.Menu;
import Engine.SokobanGame;
import Interface.ImageTile;

public class Buraco extends AbstractObject implements ImageTile, Serializable {

	// ATRIBUTO EXTRA
	private static final long serialVersionUID = 1L;


	// CONSTRUTOR
	public Buraco(Position initialPosition) {
		super(initialPosition, "Buraco", 1);
		
	}

	// SABER SE É TRANSPOSTO
	@Override
	public boolean isTransposable() {
		return true;
	}

	// FUNÇÃO PARA EXECUTAR ACTION
	@Override
	public void action(Direction Dir, AbstractObject obj) {
		SokobanGame skgame = SokobanGame.getInstance();
		if (obj.isDeletable() == true) {
			skgame.remObject(obj);
				if( obj == skgame.getPlayer()){
					Menu.gameOver();
				}
			return;
		} else {
			obj.action(Direction.NULO, obj);
			return;
		}
	}
	
	

	

}
