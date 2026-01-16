package poker.universe;
import equipment.*;
public class SevenCards {
    public void run() { // all combinations of 7 cards
        System.out.println(Cards.c(52,7));
        System.out.println(Cards.f(7));
        System.out.println(Cards.c(52,7)*Cards.f(7));
        System.out.println(Integer.MAX_VALUE);
        int n=0;
        long t0=System.nanoTime();
        Card[] cards=new Card[7];
        for(Card c1:Card.naturals)
            for(Card c2:Card.naturals)
                if(!c2.equals(c1)) for(Card c3:Card.naturals)
                    if(!c3.equals(c2)&&!c3.equals(c1)) for(Card c4:Card.naturals)
                        if(!c4.equals(c3)&&!c4.equals(c2)&&!c4.equals(c1)) for(Card c5:Card.naturals)
                            if(!c5.equals(c4)&&!c5.equals(c3)&&!c5.equals(c2)&&!c5.equals(c1)) for(Card c6:Card.naturals)
                                if(!c6.equals(c5)&&!c6.equals(c4)&&!c6.equals(c3)&&!c6.equals(c2)&&!c6.equals(c1)) for(Card c7:Card.naturals)
                                    if(!c7.equals(c6)&&!c7.equals(c5)&&!c7.equals(c4)&&!c7.equals(c3)&&!c7.equals(c2)&&!c7.equals(c1)) {
                                        cards[0]=c1;
                                        cards[1]=c2;
                                        cards[2]=c3;
                                        cards[3]=c4;
                                        cards[4]=c5;
                                        cards[5]=c6;
                                        cards[6]=c7;
                                        //lookup(cards);
                                        n++;
                                    }
        long dt=System.nanoTime()-t0;
        double rate=n*(double)oneBillion/(double)dt;
        double rate2=n/(double)dt*oneBillion;
        System.out.println(n+" hands");
        System.out.println(rate+" hands/sec.");
        System.out.println(rate2+" hands/sec.");
        if(Cards.c(52,7)*Cards.f(7)!=n) System.out.println(Cards.c(52,7)*Cards.f(7)+"!="+n+"badness!");
    }
    public static void main(String[] args) {
        new SevenCards().run();
    }
    static final int oneBillion=1_000_000_000;
}
