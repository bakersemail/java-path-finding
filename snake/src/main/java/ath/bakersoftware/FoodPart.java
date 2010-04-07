package ath.bakersoftware;

import java.awt.Color;

public class FoodPart extends DrawablePart {

	public FoodPart(int positionX, int positionY,
			PartDrawProvider partDrawProvider) {
		super(positionX, positionY, partDrawProvider);
	}

	@Override
	public Color getColour() {
		return Color.GREEN;
	}
}
