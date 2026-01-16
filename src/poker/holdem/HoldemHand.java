package poker.holdem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import equipment.Card;
import equipment.Cards;
import equipment.Rank;
import equipment.Suit;
public enum HoldemHand {
	aceAce(Rank.ace,Rank.ace,Type.pair,21.0),kingKing(Rank.king,Rank.king,Type.pair,17.0),queenQueen(Rank.queen,Rank.queen,Type.pair,15.0),aceKingSuited(
			Rank.ace,Rank.king,Type.suited,13.0),jackJack(Rank.jack,Rank.jack,Type.pair,13.0),aceKing(Rank.ace,Rank.king,Type.offsuit,11.0),aceQueenSuited(
			Rank.ace,Rank.queen,Type.suited,11.0),kingQueenSuited(Rank.king,Rank.queen,Type.suited,11.0),tenTen(Rank.ten,Rank.ten,Type.pair,11.0),aceJackSuited(
			Rank.ace,Rank.jack,Type.suited,10.0),queenJackSuited(Rank.queen,Rank.jack,Type.suited,10.0),nineNine(Rank.nine,Rank.nine,Type.pair,10.0),aceQueen(
			Rank.ace,Rank.queen,Type.offsuit,9.0),kingQueen(Rank.king,Rank.queen,Type.offsuit,9.0),kingJackSuited(Rank.king,Rank.jack,Type.suited,9.0),jackTenSuited(
			Rank.jack,Rank.ten,Type.suited,9.0),eightEight(Rank.eight,Rank.eight,Type.pair,9.0),aceJack(Rank.ace,Rank.jack,Type.offsuit,8.0),aceTenSuited(
			Rank.ace,Rank.ten,Type.suited,8.0),kingTenSuited(Rank.king,Rank.ten,Type.suited,8.0),queenJack(Rank.queen,Rank.jack,Type.offsuit,8.0),queenTenSuited(
			Rank.queen,Rank.ten,Type.suited,8.0),jackNineSuited(Rank.jack,Rank.nine,Type.suited,8.0),tenNineSuited(Rank.ten,Rank.nine,Type.suited,8.0),sevenSeven(
			Rank.seven,Rank.seven,Type.pair,8.0),nineEightSuited(Rank.nine,Rank.eight,Type.suited,7.5),aceNineSuited(Rank.ace,Rank.nine,Type.suited,7.0),aceEightSuited(
			Rank.ace,Rank.eight,Type.suited,7.0),aceSevenSuited(Rank.ace,Rank.seven,Type.suited,7.0),aceSixSuited(Rank.ace,Rank.six,Type.suited,7.0),aceFiveSuited(
			Rank.ace,Rank.five,Type.suited,7.0),aceFourSuited(Rank.ace,Rank.four,Type.suited,7.0),aceTreySuited(Rank.ace,Rank.trey,Type.suited,7.0),aceDeuceSuited(
			Rank.ace,Rank.deuce,Type.suited,7.0),kingJack(Rank.king,Rank.jack,Type.offsuit,7.0),queenNineSuited(Rank.queen,Rank.nine,Type.suited,7.0),jackTen(
			Rank.jack,Rank.ten,Type.offsuit,7.0),tenEightSuited(Rank.ten,Rank.eight,Type.suited,7.0),eightSevenSuited(Rank.eight,Rank.seven,Type.suited,7.0),sixSix(
			Rank.six,Rank.six,Type.pair,7.0),fiveFive(Rank.five,Rank.five,Type.pair,7.0),nineSevenSuited(Rank.nine,Rank.seven,Type.suited,6.5),sevenSixSuited(
			Rank.seven,Rank.six,Type.suited,6.5),aceTen(Rank.ace,Rank.ten,Type.offsuit,6.0),kingTen(Rank.king,Rank.ten,Type.offsuit,6.0),kingNineSuited(
			Rank.king,Rank.nine,Type.suited,6.0),queenTen(Rank.queen,Rank.ten,Type.offsuit,6.0),jackNine(Rank.jack,Rank.nine,Type.offsuit,6.0),jackEightSuited(
			Rank.jack,Rank.eight,Type.suited,6.0),tenNine(Rank.ten,Rank.nine,Type.offsuit,6.0),eightSixSuited(Rank.eight,Rank.six,Type.suited,6.0),sixFiveSuited(
			Rank.six,Rank.five,Type.suited,6.0),fourFour(Rank.four,Rank.four,Type.pair,6.0),treyTrey(Rank.trey,Rank.trey,Type.pair,6.0),deuceDeuce(Rank.deuce,
			Rank.deuce,Type.pair,6.0),nineEight(Rank.nine,Rank.eight,Type.offsuit,5.5),sevenFiveSuited(Rank.seven,Rank.five,Type.suited,5.5),fiveFourSuited(
			Rank.five,Rank.four,Type.suited,5.5),aceNine(Rank.ace,Rank.nine,Type.offsuit,5.0),aceEight(Rank.ace,Rank.eight,Type.offsuit,5.0),aceSeven(Rank.ace,
			Rank.seven,Type.offsuit,5.0),aceSix(Rank.ace,Rank.six,Type.offsuit,5.0),aceFive(Rank.ace,Rank.five,Type.offsuit,5.0),aceFour(Rank.ace,Rank.four,
			Type.offsuit,5.0),aceTrey(Rank.ace,Rank.trey,Type.offsuit,5.0),aceDeuce(Rank.ace,Rank.deuce,Type.offsuit,5.0),kingEightSuited(Rank.king,Rank.eight,
			Type.suited,5.0),kingSevenSuited(Rank.king,Rank.seven,Type.suited,5.0),kingSixSuited(Rank.king,Rank.six,Type.suited,5.0),kingFiveSuited(Rank.king,
			Rank.five,Type.suited,5.0),kingFourSuited(Rank.king,Rank.four,Type.suited,5.0),kingTreySuited(Rank.king,Rank.trey,Type.suited,5.0),kingDeuceSuited(
			Rank.king,Rank.deuce,Type.suited,5.0),queenNine(Rank.queen,Rank.nine,Type.offsuit,5.0),queenEightSuited(Rank.queen,Rank.eight,Type.suited,5.0),tenEight(
			Rank.ten,Rank.eight,Type.offsuit,5.0),tenSevenSuited(Rank.ten,Rank.seven,Type.suited,5.0),eightSeven(Rank.eight,Rank.seven,Type.offsuit,5.0),sixFourSuited(
			Rank.six,Rank.four,Type.suited,5.0),fourTreySuited(Rank.four,Rank.trey,Type.suited,5.0),nineSeven(Rank.nine,Rank.seven,Type.offsuit,4.5),nineSixSuited(
			Rank.nine,Rank.six,Type.suited,4.5),sevenSix(Rank.seven,Rank.six,Type.offsuit,4.5),fiveTreySuited(Rank.five,Rank.trey,Type.suited,4.5),treyDeuceSuited(
			Rank.trey,Rank.deuce,Type.suited,4.5),kingNine(Rank.king,Rank.nine,Type.offsuit,4.0),queenSevenSuited(Rank.queen,Rank.seven,Type.suited,4.0),queenSixSuited(
			Rank.queen,Rank.six,Type.suited,4.0),queenFiveSuited(Rank.queen,Rank.five,Type.suited,4.0),queenFourSuited(Rank.queen,Rank.four,Type.suited,4.0),queenTreySuited(
			Rank.queen,Rank.trey,Type.suited,4.0),queenDeuceSuited(Rank.queen,Rank.deuce,Type.suited,4.0),jackEight(Rank.jack,Rank.eight,Type.offsuit,4.0),jackSevenSuited(
			Rank.jack,Rank.seven,Type.suited,4.0),eightSix(Rank.eight,Rank.six,Type.offsuit,4.0),eightFiveSuited(Rank.eight,Rank.five,Type.suited,4.0),sixFive(
			Rank.six,Rank.five,Type.offsuit,4.0),fourDeuceSuited(Rank.four,Rank.deuce,Type.suited,4.0),sevenFive(Rank.seven,Rank.five,Type.offsuit,3.5),sevenFourSuited(
			Rank.seven,Rank.four,Type.suited,3.5),fiveFour(Rank.five,Rank.four,Type.offsuit,3.5),kingEight(Rank.king,Rank.eight,Type.offsuit,3.0),kingSeven(
			Rank.king,Rank.seven,Type.offsuit,3.0),kingSix(Rank.king,Rank.six,Type.offsuit,3.0),kingFive(Rank.king,Rank.five,Type.offsuit,3.0),kingFour(
			Rank.king,Rank.four,Type.offsuit,3.0),kingTrey(Rank.king,Rank.trey,Type.offsuit,3.0),kingDeuce(Rank.king,Rank.deuce,Type.offsuit,3.0),queenEight(
			Rank.queen,Rank.eight,Type.offsuit,3.0),jackSixSuited(Rank.jack,Rank.six,Type.suited,3.0),jackFiveSuited(Rank.jack,Rank.five,Type.suited,3.0),jackFourSuited(
			Rank.jack,Rank.four,Type.suited,3.0),jackTreySuited(Rank.jack,Rank.trey,Type.suited,3.0),jackDeuceSuited(Rank.jack,Rank.deuce,Type.suited,3.0),tenSeven(
			Rank.ten,Rank.seven,Type.offsuit,3.0),tenSixSuited(Rank.ten,Rank.six,Type.suited,3.0),sixFour(Rank.six,Rank.four,Type.offsuit,3.0),sixTreySuited(
			Rank.six,Rank.trey,Type.suited,3.0),fourTrey(Rank.four,Rank.trey,Type.offsuit,3.0),nineSix(Rank.nine,Rank.six,Type.offsuit,2.5),nineFiveSuited(
			Rank.nine,Rank.five,Type.suited,2.5),fiveTrey(Rank.five,Rank.trey,Type.offsuit,2.5),fiveDeuceSuited(Rank.five,Rank.deuce,Type.suited,2.5),treyDeuce(
			Rank.trey,Rank.deuce,Type.offsuit,2.5),queenSeven(Rank.queen,Rank.seven,Type.offsuit,2.0),queenSix(Rank.queen,Rank.six,Type.offsuit,2.0),queenFive(
			Rank.queen,Rank.five,Type.offsuit,2.0),queenFour(Rank.queen,Rank.four,Type.offsuit,2.0),queenTrey(Rank.queen,Rank.trey,Type.offsuit,2.0),queenDeuce(
			Rank.queen,Rank.deuce,Type.offsuit,2.0),jackSeven(Rank.jack,Rank.seven,Type.offsuit,2.0),tenFiveSuited(Rank.ten,Rank.five,Type.suited,2.0),tenFourSuited(
			Rank.ten,Rank.four,Type.suited,2.0),tenTreySuited(Rank.ten,Rank.trey,Type.suited,2.0),tenDeuceSuited(Rank.ten,Rank.deuce,Type.suited,2.0),eightFive(
			Rank.eight,Rank.five,Type.offsuit,2.0),eightFourSuited(Rank.eight,Rank.four,Type.suited,2.0),fourDeuce(Rank.four,Rank.deuce,Type.offsuit,2.0),nineFourSuited(
			Rank.nine,Rank.four,Type.suited,1.5),nineTreySuited(Rank.nine,Rank.trey,Type.suited,1.5),nineDeuceSuited(Rank.nine,Rank.deuce,Type.suited,1.5),sevenFour(
			Rank.seven,Rank.four,Type.offsuit,1.5),sevenTreySuited(Rank.seven,Rank.trey,Type.suited,1.5),jackSix(Rank.jack,Rank.six,Type.offsuit,1.0),jackFive(
			Rank.jack,Rank.five,Type.offsuit,1.0),jackFour(Rank.jack,Rank.four,Type.offsuit,1.0),jackTrey(Rank.jack,Rank.trey,Type.offsuit,1.0),jackDeuce(
			Rank.jack,Rank.deuce,Type.offsuit,1.0),tenSix(Rank.ten,Rank.six,Type.offsuit,1.0),eightTreySuited(Rank.eight,Rank.trey,Type.suited,1.0),eightDeuceSuited(
			Rank.eight,Rank.deuce,Type.suited,1.0),sixTrey(Rank.six,Rank.trey,Type.offsuit,1.0),sixDeuceSuited(Rank.six,Rank.deuce,Type.suited,1.0),nineFive(
			Rank.nine,Rank.five,Type.offsuit,0.5),sevenDeuceSuited(Rank.seven,Rank.deuce,Type.suited,0.5),fiveDeuce(Rank.five,Rank.deuce,Type.offsuit,0.5),tenFive(
			Rank.ten,Rank.five,Type.offsuit,0.0),tenFour(Rank.ten,Rank.four,Type.offsuit,0.0),tenTrey(Rank.ten,Rank.trey,Type.offsuit,0.0),tenDeuce(Rank.ten,
			Rank.deuce,Type.offsuit,0.0),eightFour(Rank.eight,Rank.four,Type.offsuit,0.0),nineFour(Rank.nine,Rank.four,Type.offsuit,-0.5),nineTrey(Rank.nine,
			Rank.trey,Type.offsuit,-0.5),nineDeuce(Rank.nine,Rank.deuce,Type.offsuit,-0.5),sevenTrey(Rank.seven,Rank.trey,Type.offsuit,-0.5),eightTrey(
			Rank.eight,Rank.trey,Type.offsuit,-1.0),eightDeuce(Rank.eight,Rank.deuce,Type.offsuit,-1.0),sixDeuce(Rank.six,Rank.deuce,Type.offsuit,-1.0),sevenDeuce(
			Rank.seven,Rank.deuce,Type.offsuit,-1.5);
	HoldemHand(Rank r1,Rank r2,Type type,Double value) {
		this.r1=r1;
		this.r2=r2;
		if (r1.ordinal()<r2.ordinal()) throw new RuntimeException("oops");
		this.type=type;
		boolean isNaN=value.isNaN();
		if(isNaN) System.out.println(value);
		this.value=isNaN?(ordinal()+1):value;
	}
	HoldemHand(Rank r1,Rank r2,Type type) {
		this(r1,r2,type,Double.NaN);
	}
	Card[] cards() {
		return cards(Suit.clubs,Suit.diamonds);
	}
	Card[] cards(Suit suit1,Suit suit2) {
		Card[] cards=new Card[2];
		switch (type) {
			case pair -> {
				cards[0]=Card.instance(r1,suit1);
				cards[1]=Card.instance(r2,suit2);
			}
			case suited -> {
				cards[0]=Card.instance(r1,suit1);
				cards[1]=Card.instance(r2,suit1);
			}
			case offsuit -> {
				cards[0]=Card.instance(r1,suit1);
				cards[1]=Card.instance(r2,suit2);
			}
		}
		return cards;
	}
	@Override public String toString() {
		return ""+r1.toCharacter()+r2.toCharacter()+(type==Type.suited?"s":"");
	}
	public static HoldemHand fromCharacters(String s) {
		Rank r1=Rank.fromCharacter(s.charAt(0));
		Rank r2=Rank.fromCharacter(s.charAt(1));
		String suffix=s.length()>=3&&s.charAt(2)=='s'?"Suited":"";
		String t=r1.name()+fTU(r2)+suffix;
		return HoldemHand.valueOf(t);
	}
	public static HoldemHand from(Rank r1,Rank r2,Type type) {
		if (r1.ordinal()<r2.ordinal()) throw new RuntimeException("first rank should be largest!");
		for(HoldemHand holdemHand:values())
			if (holdemHand.type==type&&holdemHand.r1==r1&&holdemHand.r2==r2) return holdemHand;
		System.out.println(r1+" "+r2+" "+type);
		// System.out.println("returning null!");
		throw new RuntimeException("oops");
		// return null;
	}
	private static String fTU(String s) {
		return s.substring(0,1).toUpperCase()+s.substring(1);
	}
	private static String fTL(String s) {
		return s.substring(0,1).toLowerCase()+s.substring(1);
	}
	private static String fTU(Rank r) {
		return fTU(r.name());
	}
	private static String fTL(Rank r) {
		return fTL(r.name());
	}
	public static enum Type { // or calculate from name?
		pair,suited,offsuit;
		public int frequency() {
			return switch (this) {
				case pair -> pairFrequency;
				case suited -> suitedFrequency;
				case offsuit -> offsuitFrequency;
			};
		}
	}
static record Range(HoldemHand.Type type,List<Rank> ranks) { // i.e. 22+ or AT+
		// seems like this is never used.
		Range(HoldemHand holdemHand) {
			this(holdemHand.type,List.of(holdemHand.r1,holdemHand.r2));
		}
		// top 11%, i.e. 77+, A9s+, KTs+, QTs+, ATo+, KQo. test this!
		Range(HoldemHand.Type type,Rank[] ranks) {
			this(type,toRanks(ranks));
		}
		private static List<Rank> toRanks(Rank[] ranks) {
			if (ranks.length!=2) throw new RuntimeException("oops");
			return List.of(ranks[0],ranks[1]);
		}
		public List<HoldemHand> hands() {
			ArrayList<HoldemHand> hands=new ArrayList<>();
			switch (type) {
				case pair -> {
					for(Rank rank:Rank.values())
						if (rank.ordinal()>=ranks.get(0).ordinal()) hands.add(from(rank,rank,type));
						else System.out.println("skipping pair: "+rank);
				}
				case suited, offsuit -> {
					Rank highCard=ranks.get(0);
					for(Rank rank:Rank.values())
						if (ranks.get(1).ordinal()<=rank.ordinal()&&rank.ordinal()<highCard.ordinal()) hands.add(from(highCard,rank,type));
						else System.out.println("skipping non pair: "+rank);
				}
				default -> throw new RuntimeException("oops");
			}
			return hands;
		}
	}
	int countCaps() {
		int n=0;
		for(char c:name().toCharArray())
			if (Character.isUpperCase(c)) n++;
		return n;
	}
	Type type() {
		int n=countCaps();
		return switch (n) {
			case 1 -> {
				if (name().length()%2!=0) yield Type.offsuit;
				String first=name().substring(0,name().length()/2);
				String second=fTL(name().substring(name().length()/2));
				if (first.equals(second)) yield Type.pair;
				yield Type.offsuit;
			}
			case 2 -> Type.suited;
			default -> throw new RuntimeException("oops");
		};
	}
	static HoldemHand type(Card[] cards) {
		if(cards[0].rank().ordinal()<cards[1].rank().ordinal())
			return type(new Card[] {cards[1],cards[0]}); // may not be teh right thing to do.
		boolean suited=cards[0].suit()==cards[1].suit();
		for(HoldemHand h:values())
			if (suited) {
				if (h.type==Type.suited&&h.r1==cards[0].rank()&&h.r2==cards[1].rank()) return h;
			} else if (h.type!=Type.suited&&h.r1==cards[0].rank()&&h.r2==cards[1].rank()) return h;
		System.out.println("can not find hand for: "+Arrays.asList(cards));
		throw new RuntimeException();
	}
	private static void printFrequencies() {
		System.out.println(Arrays.asList(values()));
		System.out.println("pair frequency "+Type.pair.frequency());
		System.out.println("suited frequency "+Type.suited.frequency());
		System.out.println("offsuit frequency "+Type.offsuit.frequency());
		System.out.println("enums "+values().length);
		int total=pairs+suited+unsuited;
		System.out.println("kinds of hands "+total);
		if (total!=values().length) throw new RuntimeException("badness");
		System.out.println("pair hands "+pairHands+" "+pairHands/(double)universe);
		System.out.println("suited hands "+suitedHands+" "+suitedHands/(double)universe);
		System.out.println("offsuit hands "+offsuitHands+" "+offsuitHands/(double)universe);
		System.out.println("universe "+universe);
		int totalHands=pairHands+suitedHands+offsuitHands;
		System.out.println("total hands "+totalHands);
		int check=pairs*pairFrequency+suited*suitedFrequency+unsuited*offsuitFrequency;
		System.out.println("check "+check);
		if (totalHands!=universe) throw new RuntimeException("badness");
		if (check!=universe) throw new RuntimeException("badness");
		System.out.println("pairs "+pairs);
		System.out.println("suited "+suited);
		System.out.println("unsuited "+unsuited);
		int frequency=0;
		for(HoldemHand holdemHand:HoldemHand.values()) {
		    frequency+=holdemHand.type.frequency();
		    System.out.println(holdemHand+" "+frequency+" "+(frequency/(double)universe));
		}
		double p77OrBetter=.11764705882352941,p13Folds=Math.pow(1-p77OrBetter,13);
		System.out.println("probability of "+sevenSeven+" or better: "+p77OrBetter);
		for(int i=1;i<=26;i++) {
		    System.out.println(i+" "+Math.pow(1-p77OrBetter,i));
		}
		System.out.println("probablity of 13 folds: "+p13Folds);
	}
	public static void main(String[] args) {
		printFrequencies();
	}
	public Rank r1() {
		return r1;
	}
	public Rank r2() {
		return r2;
	}
	public Type declaredType() {
		return type;
	}
	public Double value() {
		return value;
	}
	Double probability() {
		return probability;
	}
	final Rank r1,r2;
	private final Double value; // for sort order
	private Double probability; // of getting this hand or better
	private final Type type;
	static int ranks;
	static final int pairs=(int)Cards.c(13,1),suited=(int)Cards.c(13,2),unsuited=(int)Cards.c(13,2);
	static final int pairFrequency=(int)Cards.c(4,2);
	static final int suitedFrequency=(int)Cards.c(4,1);
	static final int offsuitFrequency=(int)(Cards.c(2,1)*Cards.c(4,2)); // ???
	static final int pairHands=pairs*pairFrequency;
	static final int suitedHands=suited*suitedFrequency;
	static final int offsuitHands=unsuited*offsuitFrequency;
	static final int universe=(int)Cards.c(52,2);
}
