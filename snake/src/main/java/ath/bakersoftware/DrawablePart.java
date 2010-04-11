package ath.bakersoftware;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

public abstract class DrawablePart extends Part {

	private final PartDrawProvider partDrawProvider;

	public DrawablePart(int positionX, int positionY,
			PartDrawProvider partDrawProvider) {
		super(positionX, positionY);
		this.partDrawProvider = partDrawProvider;
	}

	public final void draw(Graphics g) {
		preDraw(g);
		drawPart(g);
		postDraw(g);
	}

	private void drawPart(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setColor(getColour());
		int drawWidth = partDrawProvider.getPartDrawWidth();
		int drawHeight = partDrawProvider.getPartDrawHeight();
		graphics.fillRect(getPositionX() * drawWidth, getPositionY()
				* drawHeight, drawWidth, drawHeight);
	}

	public abstract Color getColour();

	protected void preDraw(Graphics g) {

	}

	protected void postDraw(Graphics g) {

	}

	@Override
	public String toString() {
		return getPositionX() + ", " + getPositionY();
	}

	public final boolean equals(DrawablePart part) {
		return this.getPositionX() == part.getPositionX()
				&& this.getPositionY() == part.getPositionY();
	}
}
