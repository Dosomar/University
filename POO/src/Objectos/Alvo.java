package Objectos;


import java.io.Serializable;

import Diretrizes.*;
import Engine.SokobanGame;
import Interface.*;

public class Alvo extends AbstractObject implements ImageTile, Serializable {
	
	// ATRIBUTO EXTRA
	private static final long serialVersionUID = 1L;
	
	
	// CONSTRUTOR
	public Alvo(Position initialPosition) {
		super(initialPosition, "Alvo", 1);
	
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
		if (obj.getName().equals("Caixote")) {
			skgame.setLevelCaixote(obj,5);
			skgame.checkComplete();
			} else {
			return;
		}

	}

}
