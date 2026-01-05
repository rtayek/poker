package game;

public enum Blinds {
	none(0), one(1), oneTwo(2), oneOneTwo(3);
	Blinds(int n) {
		this.n = n;
	}

	final int n;
	// maybe this guy should have the ante value.
}