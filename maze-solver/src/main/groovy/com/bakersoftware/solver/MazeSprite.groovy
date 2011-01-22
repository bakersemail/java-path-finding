package com.bakersoftware.solver


class MazeSprite {
	int positionX
	int positionY
	boolean wall
	
	String toString() {
		return "${positionX}, ${positionY}" 
	}
}
