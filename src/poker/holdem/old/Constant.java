package poker.holdem.old;
public class Constant {
	private Constant() {}
	public static void main(String[] args) {}
	public static final int first=0;
	static final int second=1;
	static final int third=2;
	static final int fourth=3;
	static final int fifth=4;
	static final int True=1;
	static final int False=0;
	static final int high_hands=7462;
	static final int aces_straights_and_flushes=1;
	static final int flushes=1287;
	static final int wild=13;
	static final int others=(high_hands+wild-flushes);
	static final int total=(high_hands+wild);
	static final int ace_low=0;
	static final int duece=1;
	static final int trey=2;
	static final int four=3;
	static final int five=4;
	static final int six=5;
	static final int seven=6;
	static final int eight=7;
	static final int nine=8;
	static final int ten=9;
	static final int jack=10;
	static final int queen=11;
	static final int king=12;
	static final int ace_high=13;
	static final int joker=14;
	static final int nac=15;
	static final int clubs=0;
	static final int diamonds=1;
	static final int hearts=2;
	static final int spades=3;
	static final int nas=4;
	static final int no_pair=0;
	static final int one_pair=1;
	static final int two_pair=2;
	static final int three_of_a_kind=3;
	static final int straight=4;
	static final int flush=5;
	static final int full_house=6;
	static final int four_of_a_kind=7;
	static final int straight_flush=8;
	static final int five_of_a_kind=9;
	static final int nah=10;
}
