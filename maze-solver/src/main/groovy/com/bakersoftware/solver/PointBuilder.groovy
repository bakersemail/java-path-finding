package com.bakersoftware.solver

import java.awt.Color
import java.awt.Image

import com.bakersoftware.image.PixelReader

class PointBuilder {
	int gridWidth
	int gridHeight
	
	def build(Image image, int cellSize) {
		def sprites = []
		
		PixelReader reader = new PixelReader()
		gridWidth = image.getWidth() / cellSize
		gridHeight = image.getHeight() / cellSize
		
		gridWidth.times() { x ->
			gridHeight.times() { y ->
				boolean wall = reader.getColour(image, x * cellSize, y * cellSize) != Color.WHITE
				sprites << new MazeSprite(positionX: x, positionY: y, wall: wall)
			}
		}
		return sprites
	}
}