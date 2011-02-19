package ath.bakersoftware;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Window;
import java.util.List;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Drawer extends JPanel {
	private final SnakeBoard board;
	
	private List<SnakePart> snakes;
	private int partWidth;
	private int partHeight;
	
	public Drawer(SnakeBoard board, Window parent) {
		this.board = board;
		
		setDrawWidth(parent.getWidth());
		setDrawHeight(parent.getHeight());		
		
		setBackground(Color.BLACK);
		setPreferredSize(new Dimension(parent.getWidth(), parent.getHeight()));
	}

	@Override
	public synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		for (DrawablePart part : board.getParts()) {
			part.draw(g, partWidth, partHeight);
		}
		for (SnakePart snake : snakes) {
			snake.draw(g, partWidth, partHeight);
		}
	}

	public void setDrawWidth(int drawWidth) {
		this.partWidth = drawWidth / board.getWidth(); 
	}

	public void setDrawHeight(int drawHeight) {
		this.partHeight = drawHeight / board.getHeight();
	}

	public void setSnakes(List<SnakePart> snakes) {
		this.snakes = snakes;
	}
}
