package ath.bakersoftware;

import java.util.Comparator;

public class PartLocationComparator implements Comparator<Part> {

	@Override
	public int compare(Part a, Part b) {
		if (a.getPositionX() < b.getPositionX()
				|| a.getPositionY() < b.getPositionY()) {
			return -1;
		}
		if (a.getPositionX() > b.getPositionX()
				|| a.getPositionY() > b.getPositionY()) {
			return 1;
		}

		return 0;
	}

}
