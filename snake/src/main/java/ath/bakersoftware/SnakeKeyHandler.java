package ath.bakersoftware;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SnakeKeyHandler extends KeyAdapter {
	private final SnakeBoard board;
	private final SnakePart snake;

	public SnakeKeyHandler(SnakeBoard board, SnakePart snake) {
		this.board = board;
		this.snake = snake;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		board.setRunning(true);
		if (arg0.getKeyCode() == KeyEvent.VK_LEFT && snake.getDirectionX() == 0) {
			snake.setDirectionX(-1);
			snake.setDirectionY(0);
		}

		if (arg0.getKeyCode() == KeyEvent.VK_RIGHT
				&& snake.getDirectionX() == 0) {
			snake.setDirectionX(1);
			snake.setDirectionY(0);
		}

		if (arg0.getKeyCode() == KeyEvent.VK_UP && snake.getDirectionY() == 0) {
			snake.setDirectionX(0);
			snake.setDirectionY(-1);
		}

		if (arg0.getKeyCode() == KeyEvent.VK_DOWN && snake.getDirectionY() == 0) {
			snake.setDirectionX(0);
			snake.setDirectionY(1);
		}
		
		if (arg0.getKeyCode() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
		board.repaint();
	}
}