package com.bakersoftware.drawer

import java.awt.Color
import java.awt.Graphics
import java.awt.Image

import com.bakersoftware.image.SizeDetector
import com.bakersoftware.solver.MazeBuilder
import com.google.code.java_path_finding.ShortestPathCalculator

class MazeDrawer {
	def draw(Image image, Image outputImage) {
		MazeBuilder builder = new MazeBuilder()
		builder.build image
		
		def entranceNode = builder.pathBuilder.findNodeWithAttached(builder.entrance)
		def exitNode = builder.pathBuilder.findNodeWithAttached(builder.exit)
		def path = new ShortestPathCalculator().calculatePath(entranceNode, exitNode, builder.gridWidth * builder.gridHeight)
		def cellSize = new SizeDetector().findPointWidth(image)
		
		Graphics g = outputImage.getGraphics()
		g.setColor Color.WHITE
		g.fillRect 0, 0, image.getWidth(), image.getHeight()
				
		g.setColor Color.RED
		path.each {
			drawPoint(g, it.attached, cellSize)
		}
		
		g.setColor Color.GREEN
		drawPoint(g, entranceNode.attached, cellSize)
		g.setColor Color.BLUE
		drawPoint(g, exitNode.attached, cellSize)
		
		g.setColor Color.BLACK
		builder.sprites.findAll { it.wall }.each {
			drawPoint(g, it, cellSize)
		}
	}
	
	def drawPoint(Graphics g, def point, int cellSize) {
		int x = point.positionX * cellSize - cellSize / 2
		int y = point.positionY * cellSize - cellSize / 2
		g.fillRect x, y, cellSize, cellSize
	}
}
