package ath.bakersoftware;

public class Part {
	private int positionX;
	private int positionY;

	public Part(int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}

	public int getPositionX() {
		return positionX;
	}

	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}

	public int getPositionY() {
		return positionY;
	}

	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public boolean equals(Part other) {
		return this.getPositionX() == other.getPositionX()
				&& this.getPositionY() == other.getPositionY();
	}

	@Override
	public String toString() {
		return "(" + getPositionX() + ", " + getPositionY() + ")";
	}
}
