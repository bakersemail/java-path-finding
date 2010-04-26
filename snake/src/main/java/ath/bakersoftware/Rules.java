package ath.bakersoftware;

public class Rules {
	private final int width;
	private final int height;
	private final SnakePart snake;

	public Rules(SnakePart snake, int width, int height) {
		this.snake = snake;
		this.width = width;
		this.height = height;
	}
	
	public boolean isGameOver() {
		return isWallHit() || hitSelf();
	}
	
	private boolean isWallHit() {
		if (snake.getDirectionX() > 0 && snake.getPositionX() >= width - 1) {
			return true;
		}
		if (snake.getDirectionX() < 0 && snake.getPositionX() <= 0) {
			return true;
		}
		if (snake.getDirectionY() > 0 && snake.getPositionY() >= height - 1) {
			return true; 
		}
		if (snake.getDirectionY() < 0 && snake.getPositionY() <= 0) {
			return true;
		}
		return false;
	}

	private boolean hitSelf() {
		SnakePart part = snake.getNextPart();
		while (part != null) {
			if (snake.equals(part)) {
				return true;
			}
			part = part.getNextPart();
		}
		return false;
	}
}
