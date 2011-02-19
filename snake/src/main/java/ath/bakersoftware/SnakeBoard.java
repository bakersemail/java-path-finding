package ath.bakersoftware;

import java.util.Collection;
import java.util.HashSet;
import java.util.Random;

public class SnakeBoard {

	private final int width;
	private final int height;
	private final Collection<DrawablePart> parts;
	private final Collection<FoodPart> food;
	private final Collection<DrawablePart> walls;

	public SnakeBoard(int width, int height, int numFood) {
		this.width = width;
		this.height = height;
		
		this.food = new HashSet<FoodPart>(numFood);
		this.parts = new HashSet<DrawablePart>(numFood);
		this.walls = new HashSet<DrawablePart>();
		addWalls();
		for (int i = 0; i < numFood; i++) {
			addRandomFood();
		}
	}

	private void addWalls() {
		for (int x=0; x < width; x++) {
			walls.add(new WallPart(x, 0));
		}
		
		for (int x=0; x < width; x++) {
			walls.add(new WallPart(x, height - 1));
		}
		
		for (int y=0; y < height; y++) {
			walls.add(new WallPart(0, y));
		}
		
		for (int y=0; y < height; y++) {
			walls.add(new WallPart(width - 1, y));
		}
		parts.addAll(walls);
	}

	public void addRandomFood() {
		int positionX = new Random().nextInt(width - 2) + 1;
		int positionY = new Random().nextInt(height - 2) + 1;
		FoodPart newFood = new FoodPart(positionX, positionY);
		food.add(newFood);
		parts.add(newFood);
	}
	
	public Collection<DrawablePart> getParts() {
		return parts;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Collection<DrawablePart> getStaticParts() {
		return walls;
	}

	public Collection<FoodPart> getFood() {
		return food;
	}

	public void removeFood(FoodPart eaten) {
		parts.remove(eaten);
		food.remove(eaten);
	}
}
