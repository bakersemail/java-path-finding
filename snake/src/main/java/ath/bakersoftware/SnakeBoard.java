package ath.bakersoftware;

import java.util.*;

import static java.util.Arrays.asList;

public class SnakeBoard {

	private final int width;
	private final int height;
	private final DrawablePart parts[];
	private final FoodPart[] food;
	private final Collection<DrawablePart> walls;

	public SnakeBoard(int width, int height, int numFood) {
		this.width = width;
		this.height = height;

        this.food = addFood(numFood);
		this.walls = createWalls();

        Set<DrawablePart> setUpParts = new HashSet<DrawablePart>();
        setUpParts.addAll(this.walls);
        setUpParts.addAll(asList(this.food));

        this.parts = new DrawablePart[setUpParts.size()];
        int j = 0;
        for (DrawablePart part : setUpParts) {
            this.parts[j] = part;
            j++;
        }
	}

	private Set<DrawablePart> createWalls() {
        Set<DrawablePart> walls = new HashSet<DrawablePart>();
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

        return walls;
	}

	private FoodPart[] addFood(int number) {
        FoodPart[] food = new FoodPart[number];
        for (int i = 0; i < number; i++) {
            food[i] = createRandomFood();
        }
        return food;
	}

    private FoodPart createRandomFood() {
        int positionX = new Random().nextInt(width - 2) + 1;
        int positionY = new Random().nextInt(height - 2) + 1;
        return new FoodPart(positionX, positionY);
    }

    public DrawablePart[] getParts() {
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

	public FoodPart[] getFood() {
		return food;
	}

    public void replaceFood(FoodPart eaten) {
        FoodPart newFood = createRandomFood();
        eaten.setPositionX(newFood.getPositionX());
        eaten.setPositionY(newFood.getPositionY());
    }
}
