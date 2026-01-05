package equipment;
import java.util.EnumSet;
public enum Suit { // maybe should be diamonds, clubs, hearts, spades?
	jokerSuit('*',"Joker"),clubs('c'/* try character for a real dimond? */,"Club"),diamonds('d',"Diamond"),hearts('h',"Heart"),spades('s',"Spade");
	Suit(final Character c,final String s) {
		// this.b_=b;
		this.c=c;
		this.s=s;
	}
	public static Suit fromCharacter(char c) {
		for(Suit s:Suit.values())
			if(s.c==c) return s;
		return null;
	}
	public Character toCharacter() {
		return c;
	}
	public String toString() {
		return s;
	}
	public static String toString(final Suit[] suit) {
		String s_="";
		for(Suit s:suit)
			s_+=s.toCharacter();
		return s_;
	}
	public static boolean areSuited(final Suit[] suit) {
		final int n=suit.length;
		for(int i=0;i<n-1;i++)
			if(suit[i]!=suit[i+1]) return false;
		return true;
	}
	final Character c;
	final String s;
	public static final EnumSet<Suit> natural=EnumSet.range(Suit.clubs,Suit.spades);
}
