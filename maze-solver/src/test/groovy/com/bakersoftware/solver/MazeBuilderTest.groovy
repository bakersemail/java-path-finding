package com.bakersoftware.solver

import javax.imageio.ImageIO
import com.google.code.java_path_finding.ShortestPathCalculator
import javax.imageio.ImageIO

import com.google.code.java_path_finding.ShortestPathCalculator

class MazeBuilderTest {
	void testBuildPathFromImage() {
		final int NUM_NODES = 17689
		
		MazeBuilder builder = new MazeBuilder()
		builder.build(ImageIO.read(new File("src/test/resources/maze-basic.png")))
		
		def entrance = builder.entrance
		assert entrance != null
		assert entrance.positionX == 132
		assert entrance.positionY == 110
		
		def exit = builder.exit
		assert exit != null
		assert exit.positionX == 132
		assert exit.positionY == 131
		
		assert builder.pathBuilder.list().size() == NUM_NODES
		
		def entranceNode = builder.pathBuilder.findNodeWithAttached(entrance)
		assert entranceNode != null
		
		def exitNode = builder.pathBuilder.findNodeWithAttached(exit)
		assert exitNode != null
		
		def path = new ShortestPathCalculator().calculatePath(entranceNode, exitNode, NUM_NODES)
		assert path != null
		assert path.size() == 63
	}
}