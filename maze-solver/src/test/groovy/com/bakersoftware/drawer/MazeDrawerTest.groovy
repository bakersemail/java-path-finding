package com.bakersoftware.drawer

import java.awt.image.BufferedImage

import javax.imageio.ImageIO

import com.bakersoftware.image.ImageTrimmer

class MazeDrawerTest extends GroovyTestCase {

	void testDraw() {
		def image = ImageIO.read(new File("src/test/resources/maze.gif"))
		image = new ImageTrimmer().trimBorder(image, 4)
		
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB)
		new MazeDrawer().draw(image, result)
		
		assert image != null
		assert image.getWidth() == result.getWidth()
		assert image.getHeight() == result.getHeight()
	}
}