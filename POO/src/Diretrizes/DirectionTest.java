package Diretrizes;


import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;


public class DirectionTest {

//38
	@Test
	public void testAsVector() {
		Direction testado = Direction.UP;
		Vector2D paratestar = new Vector2D(0, -1);
		assertEquals( testado.asVector(),paratestar);
		
	}

	@Test
	public void testDirectionFor() {
		Direction testado = Direction.directionFor(38);
		Direction paratestar = Direction.UP;
		assertEquals(testado, paratestar);
	}

	@Test
	public void testIsDirection() {
		 boolean testado = Direction.isDirection(38);
		assertEquals(testado, true);
	}

	@Test
	public void testInverse() {
		Direction testado = Direction.UP;
		Direction paratestar = Direction.DOWN;
		assertEquals(testado.inverse(),paratestar);
	}

}
