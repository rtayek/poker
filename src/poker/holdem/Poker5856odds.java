package poker.holdem;
import java.util.SortedSet;
import java.util.TreeSet;
import com.tayek.utilities.Histogram;
import equipment.Card;
import equipment.Deck;
public class Poker5856odds {
    static void pn(String string) {
	System.out.print(string);
    }
    static void p(String string) {
	System.out.println(string);
    }
    void run() {
	for(int i=1;i<=1_000_000;i++) {
	    deck.shuffle();
	    set.clear();
	    for(int player=0;player<players;player++) {
		Card card1=deck.draw();
		Card card2=deck.draw();
		SpecificHoldemHand hand=card1.rank().ordinal()>=card2.rank().ordinal()?new SpecificHoldemHand(card1,card2):new SpecificHoldemHand(card2,card1);
		// p(player+" "+hand+" "+hand.holdemHand);
		specificHoldemHands[player]=hand;
		set.add(hand.holdemHand);
	    }
	    histogram.add(set.size());
	    if(set.size()!=players) {
		duplicates++;
		if(true&&set.size()<7) {
		    pn(set.size()+" ");
                    for(int player=0;player<players;player++)
                        pn(specificHoldemHands[player].holdemHand+" ");
                    p("");
		    //for(int player=0;player<players;player++)
			//pn(specificHoldemHands[player]+" ");
		    //p("");
		}
	    }
	    if(i%1_000_000==0) p(i+" "+histogram);
	}
    }
    public static void main(String[] args) {
	new Poker5856odds().run();
    }
    int duplicates;
    Deck deck=new Deck();
    SortedSet<HoldemHand> set=new TreeSet<>();
    final int players=9;
    SpecificHoldemHand[] specificHoldemHands=new SpecificHoldemHand[players];
    Histogram histogram=new Histogram(10,0,10);
}
