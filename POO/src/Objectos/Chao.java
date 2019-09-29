package Objectos;

import java.io.Serializable;

import Diretrizes.Direction;
import Diretrizes.Position;
import Interface.ImageTile;

public class Chao extends AbstractObject implements ImageTile, Serializable {

	private static final long serialVersionUID = 1L;

	// CONSTRUTOR
	public Chao(Position position) {
		super(position, "Chao", 0);
	}

	// SABER SE É TRANSPOSTO
	@Override
	public boolean isTransposable() {
		return true;
	}

	// FUNÇÃO PARA EXECUTAR ACTION
	@Override
	public void action(Direction Dir, AbstractObject obj) {

	}

}
