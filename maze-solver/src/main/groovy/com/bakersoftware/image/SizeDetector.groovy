package com.bakersoftware.image

import java.awt.Color
import java.awt.Image

def scanWidth(Image image, int y) {
	PixelReader pixelReader = new PixelReader()
	boolean found = false
	int width = 0
	for (x in 0..image.getWidth() - 1) {
		def c = pixelReader.getColour(image, x, y)
		if (c == Color.WHITE) {
			width++
			found = true
		} else if (found && c != Color.WHITE) {
			return width
		}
	}
}

def scanHeight(Image image, int x) {
	PixelReader pixelReader = new PixelReader()
	boolean found = false
	int width = 0
	for (y in 0..image.getHeight() - 1) {
		def c = pixelReader.getColour(image, x, y)
		if (c == Color.WHITE) {
			width++
			found = true
		} else if (found && c != Color.WHITE) {
			return width
		}
	}
}

def findPointWidth(Image image) {
	assert image != null
	
	def zeroWeigthScan = scanWidth(image, 0)
	if (zeroWeigthScan != null) {
		return zeroWeigthScan
	}
	
	def maxWeigthScan = scanWidth(image, image.getHeight() - 1)
	if (maxWeigthScan != null) {
		return maxWeigthScan
	}
	
	def zeroHeightScan = scanHeight(image, 0)
	if (zeroHeightScan != null) {
		return zeroHeightScan
	}
	
	def maxHeightScan = scanHeight(image, image.getWidth() - 1)
	if (maxHeightScan != null) {
		return maxHeightScan
	}
}