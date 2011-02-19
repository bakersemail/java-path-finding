package ath.bakersoftware;

import java.awt.Color;
import java.awt.Graphics;

public class SnakePart extends DrawablePart {
	private final Color colour;

	private SnakePart nextPart;
	private int directionX;
	private int directionY;

	public SnakePart(int positionX, int positionY, Color colour) {
		super(positionX, positionY);
		this.colour = colour;
	}

	public SnakePart getNextPart() {
		return nextPart;
	}

	public SnakePart extend(SnakePart nextPart) {
		this.nextPart = nextPart;
		return this;
	}

	@Override
	protected void postDraw(Graphics g, int drawWidth, int drawHeight) {
		if (nextPart != null) {
			nextPart.draw(g, drawWidth, drawHeight);
		}
	}

	public void move() {
		int positionX = getPositionX();
		int positionY = getPositionY();

		setPositionX(positionX + directionX);
		setPositionY(positionY + directionY);

		if (nextPart != null) {
			move(positionX, positionY, directionX, directionY);
		}
	}

	private void move(int positionX, int positionY, int directionX,
			int directionY) {
		if (nextPart != null) {
			int oldPositionX = nextPart.getPositionX();
			int oldPositionY = nextPart.getPositionY();
			int oldDirectionX = nextPart.getDirectionX();
			int oldDirectionY = nextPart.getDirectionY();

			nextPart.setPositionX(positionX);
			nextPart.setPositionY(positionY);
			nextPart.setDirectionX(directionX);
			nextPart.setDirectionY(directionY);

			nextPart.move(oldPositionX, oldPositionY, oldDirectionX,
					oldDirectionY);
		}
	}

	public void setDirectionX(int directionX) {
		this.directionX = directionX;
	}

	public void setDirectionY(int directionY) {
		this.directionY = directionY;
	}

	public void grow() {
		if (this.nextPart != null) {
			this.nextPart.grow();
		} else {
			int positionX = getPositionX() + directionX;
			int positionY = getPositionY() + directionY;

			SnakePart nextPart = new SnakePart(positionX, positionY, colour);
			extend(nextPart);
			nextPart.setDirectionX(directionX);
			nextPart.setDirectionY(directionY);
		}
	}

	public int getDirectionX() {
		return directionX;
	}

	public int getDirectionY() {
		return directionY;
	}

	@Override
	public Color getColour() {
		return colour;
	}
	
	private SnakePart follow(SnakePart current) {
		if (current.nextPart == null) {
			return current;
		}
		return follow(current.nextPart);
	}

	public SnakePart getTail() {
		return follow(this);
	}

	public static SnakePart newSnake(int startX, int startY) {
		return new SnakePart(startX, startY, Color.RED)
			.extend(new SnakePart(startX + 1, startY, Color.YELLOW)
			.extend(new SnakePart(startX + 2, startY, Color.YELLOW)));
	}
}
