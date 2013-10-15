package ath.bakersoftware;

import java.util.*;

public class SnakeBoard {

	private final int width;
	private final int height;
	private final DrawablePart parts[];
	private final Set<FoodPart> food;
	private final Set<DrawablePart> walls;

	public SnakeBoard(int width, int height, int numFood) {
		this.width = width;
		this.height = height;

        this.walls = createWalls();
        this.food = setupFood(numFood);

        Set<DrawablePart> setUpParts = new HashSet<DrawablePart>();
        setUpParts.addAll(this.walls);
        setUpParts.addAll(this.food);
        this.parts = setUpParts.toArray(new DrawablePart[setUpParts.size()]);
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

        middleBlocks(walls);
        return walls;
	}

    private void middleBlocks(Set<DrawablePart> walls) {
        for (int m=0; m < 5; m++) {
            for (int k=0; k < 5; k++) {
                for (int i=5 + k * 10; i < 10 + k * 10; i++) {
                    for (int j=5 + m * 10; j < 10 + m * 10; j++) {
                        walls.add(new WallPart(i+13, j+13));
                    }
                }
            }
        }
    }

    private Set<FoodPart> setupFood(int number) {
        Set<FoodPart> food = new HashSet<FoodPart>();
        for (int i = 0; i < number; i++) {
            food.add(createRandomFood());
        }
        return food;
	}

    private FoodPart createRandomFood() {
        while (true) {
            int positionX = new Random().nextInt(width - 2) + 1;
            int positionY = new Random().nextInt(height - 2) + 1;
            FoodPart foodPart = new FoodPart(positionX, positionY);
            if (!this.walls.contains(foodPart)) {
                return foodPart;
            }
        }
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
	
	public Set<DrawablePart> getStaticParts() {
		return walls;
	}

	public Set<FoodPart> getFood() {
		return food;
	}

    public void moveFood(FoodPart eaten) {
        FoodPart newFood = createRandomFood();
        eaten.setPositionX(newFood.getPositionX());
        eaten.setPositionY(newFood.getPositionY());
    }
}
