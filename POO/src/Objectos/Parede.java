package Objectos;

import java.io.Serializable;

import Diretrizes.*;
import Interface.ImageTile;

public class Parede extends AbstractObject implements ImageTile, Serializable {

	private static final long serialVersionUID = 1L;
	// CONSTRUTOR
	public Parede(Position initialPosition) {
		super(initialPosition, "Parede", 1);
	}

	// SABER SE É TRANSPOSTO
	@Override
	public boolean isTransposable() {
		return false;
	}

}
