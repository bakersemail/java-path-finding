package ath.bakersoftware;


public class Runner {

	private final Rules rules;
	private final Ai ai;
	private final SnakePart snake;
	private final EventHandler eventHandler;
	
	private boolean running;

	public Runner(EventHandler eventHandler, SnakePart snake, Rules rules, Ai ai) {
		this.eventHandler = eventHandler;
		this.snake = snake;
		this.rules = rules;
		this.ai = ai;
	}
	
	public boolean runStep() {
		running = true;
		if (running) {
			eventHandler.redraw();
			ai.step();
			eventHandler.redraw();
			
			running = !rules.isGameOver(getSnake());
		}
		return running;
	}
	
	public Ai getAi() {
		return ai;
	}

	public SnakePart getSnake() {
		return snake;
	}
}
