package ath.bakersoftware;

import java.awt.Color;

public class WallPart extends DrawablePart {

	public WallPart(int positionX, int positionY) {
		super(positionX, positionY);
	}

	@Override
	public Color getColour() {
		return Color.WHITE;
	}

}
