package Objectos;

import java.io.Serializable;

import Diretrizes.Direction;
import Diretrizes.Position;
import Engine.SokobanGame;
import Interface.ImageTile;

public class Bateria extends AbstractObject implements ImageTile, Serializable {

	// ATRIBUTO EXTRA
	private static final long serialVersionUID = 1L;


	// CONSTRUTOR
	public Bateria(Position initialPosition) {
		super(initialPosition, "Bateria", 1);
		
	}

	// SABER SE É TRANSPOSTO
	@Override
	public boolean isTransposable() {
		return true;
	}

	// SABER SE É CONSUMIVEL
	@Override
	public boolean isConsumable() {
		return true;
	}

	// FUNÇÃO PARA EXECUTAR ACTION
	@Override
	public void action(Direction Dir, AbstractObject obj) {
		SokobanGame skgame = SokobanGame.getInstance();
		skgame.setEnergia(101);
		skgame.remObject(this);
	}
}
