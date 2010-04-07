package com.bakersoftware.game.common.ai;

import java.util.Comparator;

public class EqualsComparator<T> implements Comparator<T> {

	public int compare(T lhs, T rhs) {
		if (lhs.equals(rhs)) {
			return 0;
		}
		return 1;
	}

}
