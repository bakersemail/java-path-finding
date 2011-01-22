package com.bakersoftware.image

import java.awt.Color
import java.awt.image.BufferedImage

import javax.imageio.ImageIO


class PixelReaderTest extends GroovyTestCase {
	
	void testReadColourAtPoint() {
		BufferedImage image = ImageIO.read(new File("src/test/resources/maze-basic.png"))
		assert image.getWidth() > 0
		assert image.getHeight() > 0
		
		for (x in 0..image.getWidth() - 1) {
			for (y in 0..image.getHeight() - 1) {
				Color c = new PixelReader().getColour(image, x, y)
				assert c != null
			}
		}
		
		Color c = new PixelReader().getColour(image, 10, 10)
		assert c.getRed() == 196
		assert c.getGreen() == 196
		assert c.getBlue() == 196
		
		assert new PixelReader().getColour(image, 38, 1) == Color.BLACK
		assert new PixelReader().getColour(image, 38, 50) == Color.WHITE
	}
}