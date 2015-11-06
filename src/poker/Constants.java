package poker;
import equipment.Cards;
public class Constants { // make this an enum?
	public static void main(String[] arguments) {
		System.out.println(ranks+" ranks");
		System.out.println(suits+" suits");
		System.out.println(flushes+" flushes");
		System.out.println(nonFlushes+" non flushes");
		System.out.println((flushes+nonFlushes)+" flushes+nonFlushes");
		System.out.println(wild+" wild");
		System.out.println(naturalHighHands+" natural high hands");
		System.out.println(totalHighHands+" total high hands");
		System.out.println(totalHighHands2+" total high hands2");
		System.out.println(lowHands+" low hands");
		System.out.println(naturalFiveCardCombinations+" naturalFiveCardCombinations");
		System.out.println(allFiveCardCombinationsWithOneJoker+" allFiveCardCombinationsWithOneJoker");
		if(totalHighHands!=totalHighHands2)
			throw new RuntimeException("oops");
	}
	public static final int ranks=13;
	public static final int suits=4;
	public static final int flushes=Cards.c(ranks,5);
	public static final int nonFlushes=Cards.c(ranks+5-1,5); // non flushes (includes 5 of a kinds) https://en.wikipedia.org/wiki/Combination#Number_of_combinations_with_repetition
	public static final int wild=Cards.c(ranks,1); // five of a kinds
	public static final int naturalHighHands=flushes+nonFlushes-wild; // 7462;
	public static final int totalHighHands=naturalHighHands+wild;
	public static final int totalHighHands2=flushes+nonFlushes;
	public static final int lowHands=naturalHighHands-flushes; // 6175
	public static final int naturalFiveCardCombinations=Cards.c(52,5);
	public static final int allFiveCardCombinationsWithOneJoker=Cards.c(53,5);
}
