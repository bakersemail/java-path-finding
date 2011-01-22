package com.bakersoftware.image


import java.awt.Color
import java.awt.Image

def getColour(Image image, int x, int y) {
	int c = image.getRGB(x, y)
	
	int  red = (c & 0x00ff0000) >> 16;
	int  green = (c & 0x0000ff00) >> 8;
	int  blue = c & 0x000000ff;
	
	new Color(red, green, blue)
}

