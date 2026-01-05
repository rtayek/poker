package game;

public class Ante {
	Ante(Blinds blinds,double value) {
		this.blinds=blinds;
		this.value=value;
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}
	final double value;
	final Blinds blinds; // what is this doing here?
	// it's here because blinds are part of the ante
}
