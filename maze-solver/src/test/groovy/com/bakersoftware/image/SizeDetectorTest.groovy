package com.bakersoftware.image

import javax.imageio.ImageIO


class SizeDetectorTest extends GroovyTestCase {
	void testGetCellSize() {
		int width = new SizeDetector().findPointWidth(ImageIO.read(new File("src/test/resources/maze-basic.png")))
		assert width == 4
	}
}
