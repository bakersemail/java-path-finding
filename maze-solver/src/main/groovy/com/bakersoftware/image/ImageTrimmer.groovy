package com.bakersoftware.image

import java.awt.Graphics2D
import java.awt.Image
import java.awt.image.BufferedImage

class ImageTrimmer {
	
	def trimBorder(Image image, int border) {
		BufferedImage trimmed = new BufferedImage(
			image.getWidth() - border * 2, image.getHeight() - border * 2, BufferedImage.TYPE_INT_ARGB)
		Graphics2D g = trimmed.createGraphics()
		g.drawImage image, -1 * border, -1 * border, null
		
		return trimmed
	}
}
