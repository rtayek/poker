package poker.machine;
import poker.*;
public class HighPayoff implements PayMaster {
	public int payoff(PokerHand.HighType typeOfPokerHand,int handNumber) {
		int payoff=payoffs[typeOfPokerHand.ordinal()];
		if(handNumber==royalFlush) payoff=250;
		if(handNumber>jacksOrBetter) payoff=0;
		return payoff;
	}
	private static final int jacksOrBetter=4218,royalFlush=14;
	private static final int payoffs[]= {0,1,2,3,4,6,9,25,50,250};
}
