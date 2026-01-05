package poker.holdem;


public class HandRanks {
	public static void main(String[] args) {
		final Don don=new Don();
		don.fourth=don.fifth=true;
		HoldemHand h=HoldemHand.aceAce;
		if(!don.addPlayer(h,don.deck))
				System.out.println("problem with "+h);
		HoldemHand h2=HoldemHand.aceKingSuited;
		if(!don.addPlayer(h2,don.deck))
			System.out.println("problem with "+h2);
		don.print();
		don.run();
	}
}
