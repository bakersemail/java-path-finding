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

	@Override
	public int hashCode() {
		int hash = 23;
		hash = hash * 31 + Integer.valueOf(positionX).hashCode();
		hash = hash * 31 + Integer.valueOf(positionY).hashCode();
		return hash;
	}

	public final boolean equals(Object o) {
		Part other = (Part)o;
		return this.getPositionX() == other.getPositionX()
				&& this.getPositionY() == other.getPositionY();
	}
}
