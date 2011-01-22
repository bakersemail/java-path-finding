package com.bakersoftware.solver


import javax.imageio.ImageIO

import com.bakersoftware.image.SizeDetector

class PointBuilderTest extends GroovyTestCase {
	void testCreateSpritesFromImage() {
		def image = ImageIO.read(new File("src/test/resources/maze-basic.png"))
		PointBuilder builder = new PointBuilder()
		int cellSize = new SizeDetector().findPointWidth(image)
		
		def sprites = builder.build(image, cellSize)
		assert sprites.size() == 17689
		
		def walls = sprites.findAll { it.wall }
		def gaps = sprites.findAll { !it.wall }
		assert walls.size() == 10576
		assert gaps.size() == 7113
	}
}