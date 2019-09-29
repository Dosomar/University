package Diretrizes;

public class Vector2D {
	private int x;
	private int y;
	
	public Vector2D(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	public Vector2D plus(Vector2D v) {
		return new Vector2D(getX() + v.getX(), getY() + v.getY());
	}

	public Vector2D minus(Vector2D v) {
		return new Vector2D(getX() - v.getX(), getY() - v.getY());
	}

	@Override
	public String toString() {
		return "<" + x + ", " + y + ">";
	}
	
	@Override
	public boolean equals(Object obj) {
		Vector2D d = (Vector2D) obj;
		if(this.x == d.getX() && this.y == d.getY()){
			return true;
		}
		return false;
	}

}
