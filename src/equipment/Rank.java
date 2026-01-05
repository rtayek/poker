package equipment;
import java.util.EnumSet;
public enum Rank {
	jokerRank('*',"Joker"),aceLow('a',"Ace(low)"),deuce('2',"Deuce"),trey('3',"Trey"),four('4',"Four"),five('5',"Five"),six('6',"Six"),seven('7',"Seven"),eight(
			'8',"Eight"),nine('9',"Nine"),ten('T',"Ten"),jack('J',"Jack"),queen('Q',"Queen"),king('K',"King"),ace('A',"Ace");
		// permute the order of these guys and see if anything breaks 
	Rank(final Character c,final String s) {
		this.c=c;
		this.s=s;
	}
	public Rank fixAces() { // use ace low internally for the most part
		return this==Rank.ace?Rank.aceLow:this;
	}
	public static Rank fromInt(int n) {
		return Rank.values()[n];
	}
	public static Rank fromCharacter(char c) {
		for(Rank r:Rank.values())
			if(r.c==c) return r;
		return null;
	}
	public static Rank[] fromCharacters(final String cards) {
		final Rank[] rank=new Rank[cards.length()];
		for(int i=0;i<cards.length();i++)
			rank[i]=Rank.fromCharacter(cards.charAt(i));
		return rank;
	}
	public Character toCharacter() {
		return c;
	}
	public String toString() {
		return s;
	}
	public static String toString(final Rank[] rank) {
		String s="";
		for(Rank r:rank)
			s+=r.toCharacter();
		return s;
	}
	final Character c;
	final String s;
	static final EnumSet<Rank> natural=EnumSet.range(Rank.aceLow,Rank.king);
}
