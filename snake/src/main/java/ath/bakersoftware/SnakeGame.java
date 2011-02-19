package ath.bakersoftware;

import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

public class SnakeGame {
	private static final int STARTING_FOOD = 20;
	private static final int STEP_WAIT = 30;
	private static final int BOARD_WIDTH = 80;
	private static final int BOARD_HEIGHT = 80;
	
	public static void main(String[] args) throws Exception {
		final JFrame frame = new JFrame("Snake");
		frame.setPreferredSize(new Dimension(488, 514));
		frame.setSize(488, 514);
		SnakeBoard snakeBoard = new SnakeBoard(BOARD_WIDTH, BOARD_HEIGHT, STARTING_FOOD);
		
		Drawer drawer = new Drawer(snakeBoard, frame);
		prepareFrame(frame, drawer);

		SnakeManager manager = new SnakeManager(snakeBoard, drawer, STEP_WAIT);
		manager.addSnake().run();
	}

	private static void prepareFrame(final JFrame frame, final Drawer drawer) {
		frame.setContentPane(drawer);
		frame.pack();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent arg0) {
				drawer.setDrawWidth(frame.getWidth());
				drawer.setDrawHeight(frame.getHeight());
			}
		});
	}
}
