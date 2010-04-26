package ath.bakersoftware;

import java.awt.Color;

public class FoodPart extends DrawablePart {

	public FoodPart(int positionX, int positionY) {
		super(positionX, positionY);
	}

	@Override
	protected Color getColour() {
		return Color.GREEN;
	}
}
