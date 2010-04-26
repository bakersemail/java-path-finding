package ath.bakersoftware;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class DrawablePart extends Part {

	public DrawablePart(int positionX, int positionY) {
		super(positionX, positionY);
	}

	public void draw(Graphics g, int drawWidth, int drawHeight) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setColor(getColour());
		graphics.fillRect(getPositionX() * drawWidth, getPositionY()
				* drawHeight, drawWidth, drawHeight);
		
		postDraw(g, drawWidth, drawHeight);
	}

	protected abstract Color getColour();

	protected void postDraw(Graphics g, int drawWidth, int drawHeight) {
		
	}
}
