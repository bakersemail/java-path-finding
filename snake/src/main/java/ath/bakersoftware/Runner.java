package ath.bakersoftware;


public class Runner {

	private final Ai ai;
	private final EventHandler eventHandler;

	public Runner(EventHandler eventHandler, Ai ai) {
		this.eventHandler = eventHandler;
		this.ai = ai;
	}
	
	public boolean runStep() {
        boolean okay = ai.step();
        eventHandler.redraw();
		return okay;
	}
	
	public Ai getAi() {
		return ai;
	}
}
