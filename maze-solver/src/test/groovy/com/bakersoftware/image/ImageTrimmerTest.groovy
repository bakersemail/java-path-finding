package com.bakersoftware.image

import java.awt.Image

import javax.imageio.ImageIO

class ImageTrimmerTest extends GroovyTestCase {
	
	void testImageTrimBorder() {
		def image = ImageIO.read(new File("src/test/resources/maze.gif"))
		assert image != null
		
		Image newImage = new ImageTrimmer().trimBorder(image, 4)
		
		ImageIO.write(newImage, "PNG", new File("c:\\yourImageName.PNG"))
		
		assert image.getWidth() - 8 == newImage.getWidth()
		assert image.getHeight() - 8 == newImage.getHeight()
	}
}