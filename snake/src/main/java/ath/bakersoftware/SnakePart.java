package ath.bakersoftware;

import java.awt.Color;
import java.awt.Graphics;

public class SnakePart extends DrawablePart {
	private final Color colour;
	private final SnakeEventHandler foodHandler;
	private final PartDrawProvider partDrawProvider;

	private SnakePart nextPart;
	private int directionX;
	private int directionY;

	public SnakePart(SnakeEventHandler eventHandler, int positionX,
			int positionY, Color colour, PartDrawProvider partDrawProvider) {
		super(positionX, positionY, partDrawProvider);
		this.foodHandler = eventHandler;
		this.colour = colour;
		this.partDrawProvider = partDrawProvider;
	}

	public SnakePart getNextPart() {
		return nextPart;
	}

	public void setNextPart(SnakePart nextPart) {
		this.nextPart = nextPart;
	}

	@Override
	protected void postDraw(Graphics g) {
		if (nextPart != null) {
			nextPart.draw(g);
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

		if (foodHandler != null) {
			foodHandler.snakeMoved(this);
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

			SnakePart nextPart = new SnakePart(null, positionX, positionY,
					colour, partDrawProvider);
			setNextPart(nextPart);
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
}
