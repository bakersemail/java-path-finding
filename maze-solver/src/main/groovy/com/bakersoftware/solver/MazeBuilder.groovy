package com.bakersoftware.solver

import java.awt.Image

import com.bakersoftware.image.SizeDetector
import com.google.code.java_path_finding.PathBuilder

class MazeBuilder {

	def gridWidth
	def gridHeight
	def sprites = []
    PathBuilder pathBuilder = new PathBuilder<MazeSprite>()
	MazeSprite entrance
	MazeSprite exit
	
	def build(Image image) {
		int cellSize = new SizeDetector().findPointWidth(image)
		
		PointBuilder pointBuilder = new PointBuilder()
		sprites = pointBuilder.build(image, cellSize)
		gridWidth = pointBuilder.gridWidth
		gridHeight = pointBuilder.gridHeight
	
		def openings = sprites.findAll { MazeSprite sprite -> 
			!sprite.wall &&
			 ((sprite.positionX == 0 || sprite.positionX == pointBuilder.gridWidth - 1) ||
			 (sprite.positionY == 0 || sprite.positionY == pointBuilder.gridHeight - 1))
		}
		
		assert openings.size() == 2
		entrance = openings[0]
		exit = openings[1]
		
		preparePaths()
	}
	
	private def preparePaths() {
		def points = new MazeSprite[gridWidth][gridHeight]
		sprites.each {
			points[it.positionX][it.positionY] = it
		}
		
		gridWidth.times { x ->
			(gridHeight - 1).times { y ->
				pathBuilder.addBiDirectionalVertex points[x][y], points[x][y + 1]
			}
		}
		gridHeight.times { y ->
			(gridWidth - 1).times { x ->
				pathBuilder.addBiDirectionalVertex points[x][y], points[x + 1][y]
			}
		}
		
		sprites.findAll { it.wall }.each {
			pathBuilder.nodes.get(it).removeAllIncomingConnections()
		}
	}
	
	
}