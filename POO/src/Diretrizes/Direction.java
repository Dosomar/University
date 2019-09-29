package Diretrizes;

import java.awt.event.KeyEvent;

import com.sun.media.sound.FFT;

/**
 * @author POO2016
 * 
 *         Cardinal directions
 *			Directions that can be used
 */
public enum Direction {
	/**
	 * Left Direction
	 */
	LEFT(new Vector2D(-1, 0)),
	/**
	 * Up Direction
	 */
	UP(new Vector2D(0, -1)), 
	/**
	 * Right Direction
	 */
	RIGHT(new Vector2D(1, 0)), 
	/**
	 * Down Direction
	 */
	DOWN(new Vector2D(0, 1)), 
	/**
	 * Null Direction
	 */
	NULO(new Vector2D(0,0));

	/**
	 * 
	 * Atribute of a Vector that points in any of the directions
	 * 
	 */
	private Vector2D vector;

	/**
	 * 
	 * Constructor that given a vector makes the atribute that vector
	 */
	
	Direction(Vector2D vector) {
		this.vector = vector;
	}

	/**
	 * 
	 * @return   Resturns the Direction as a vector
	 */
	
	public Vector2D asVector() {
		return vector;
	}

	/**
	 * @param keyCode     The key that needs to be converted to a Direction 
	 * @return			  returns a Direction using the param keyCode after a key is pressed
	 * 
	 */
	public static Direction directionFor(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_DOWN:
			return DOWN;
		case KeyEvent.VK_UP:
			return UP;
		case KeyEvent.VK_LEFT:
			return LEFT;
		case KeyEvent.VK_RIGHT:
			return RIGHT;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * 
	 * @param lastKeyPressed            the key that has been pressed on the keyboard
	 * @return			returns a boolean if the key pressed on the keyboard is a direction or not
	 */
	
	public static boolean isDirection(int lastKeyPressed) {
		return lastKeyPressed >= KeyEvent.VK_LEFT && lastKeyPressed <= KeyEvent.VK_DOWN;
	}

	/**
	 * 
	 * @return  Returns the opposite of a Direction
	 */
	
	public Direction inverse() {
		switch (this) {
		case UP:
			return DOWN;
		case DOWN:
			return UP;
		case RIGHT:
			return LEFT;
		case LEFT:
			return RIGHT;
		case NULO:
			return NULO;
		}
		return null;
	}
	
	public boolean equals(Direction gg , Direction ff){
		if ( gg.asVector() == ff.asVector() ){
			return true;
		}
		return false;
	}
}

