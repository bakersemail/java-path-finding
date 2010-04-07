package ath.bakersoftware;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Test;

public class SnakePartTest {
	private static Color COLOUR = Color.RED;

	@Test
	public void shouldGrow() {
		int positionX = 0;
		int positionY = 0;

		SnakePart snakePart = new SnakePart(positionX, positionY, 1, 1, COLOUR);
		snakePart.setDirectionX(1);
		snakePart.setDirectionY(1);
		snakePart.grow();

		SnakePart nextPart = snakePart.getNextPart();
		assertEquals(1, nextPart.getPositionX());
		assertEquals(1, nextPart.getPositionY());
	}

	@Test
	public void shouldMove() {
		SnakePart nextPart = new SnakePart(1, 1, 1, 1, COLOUR);
		SnakePart snakePart = new SnakePart(0, 0, 1, 1, COLOUR);
		snakePart.setNextPart(nextPart);

		snakePart.setDirectionX(1);
		snakePart.setDirectionY(1);
		snakePart.move(null);

		assertEquals(1, snakePart.getPositionX());
		assertEquals(1, snakePart.getPositionY());
		assertEquals(0, nextPart.getPositionX());
		assertEquals(0, nextPart.getPositionY());
	}

	@Test
	public void shouldMoveAll() {
		SnakePart tailPart = new SnakePart(-2, 0, 1, 1, COLOUR);
		SnakePart nextPart = new SnakePart(-1, 0, 1, 1, COLOUR);
		nextPart.setNextPart(tailPart);

		SnakePart snakePart = new SnakePart(0, 0, 1, 1, COLOUR);
		snakePart.setNextPart(nextPart);

		snakePart.setDirectionX(1);
		snakePart.move(null);

		assertEquals(1, snakePart.getPositionX());
		assertEquals(0, nextPart.getPositionX());
		assertEquals(-1, tailPart.getPositionX());
	}
}
