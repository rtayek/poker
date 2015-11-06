package poker.holdem;
import equipment.Rank;
public class Junk {
	public static Rank[] toCanonicalForm(Rank[] rank) {
		Rank[] r=new Rank[rank.length];
		for(int i=0;i<rank.length;i++) {
			r[i]=rank[i];
			if(r[i]==Rank.aceLow) r[i]=Rank.ace;
		}
		//sortDescending(r); // yikes! how could this ever work?
		return r;
	}
}
