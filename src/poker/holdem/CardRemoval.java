package poker.holdem;
import java.util.*;
import com.tayek.utilities.Histogram;
import equipment.*;
public class CardRemoval {
    void print(int i) {
        System.out.println("control: "+i);
        System.out.println("left:          "+/*hLeft.n()+" "+*/hLeft);
        System.out.println("right:         "+hRight);
        System.out.println("all fold     : "+(allFolded/(double)i)+" (frequency of).");
        System.out.println("allFolded:     "+hAllFolded);
        System.out.println("someoneOpened: "+hSomeoneOpened);
        System.out.println("all:           "+hAll);
    }
/*    
    control: 3000000
    left:          2.0<=7.9999495/78000026<=14.0 0,[0,0,6001072,6000185,5998783,5999167,6000590,6001253,5998521,6002784,5997354,5998911,6001977,6000210,5999219,0,0,0,0,0],0 NaNs: 0
    right:         2.0<=8.000051/78000026<=14.0 0,[0,0,5998932,5999819,6001221,6000837,5999414,5998751,6001483,5997220,6002650,6001093,5998027,5999794,6000785,0,0,0,0,0],0 NaNs: 0
    all foldedncy: 0.17154966666666666 (frequency).
    allFolded:     2.0<=8.295165/13380874<=14.0 0,[0,0,948547,949210,950248,950320,950339,979309,981348,1003603,1046186,1142860,1176138,1126860,1175906,0,0,0,0,0],0 NaNs: 0
    someoneOpened: 2.0<=7.93894/64619152<=14.0 0,[0,0,5050385,5050609,5050973,5050517,5049075,5019442,5020135,4993617,4956464,4858233,4821889,4872934,4824879,0,0,0,0,0],0 NaNs: 0
*/  
/*    
    control: 1000018
    left:          2.0<=7.9996114/26000494<=14.0 0,[0,0,1999129,2002020,1999328,2000391,2000420,1998721,2000626,2001027,2000844,2000455,1998395,1999673,1999465,0,0,0,0,0],0 NaNs: 0
    right:         2.0<=8.000389/26000494<=14.0 0,[0,0,2000947,1998056,2000748,1999685,1999656,2001355,1999450,1999049,1999232,1999621,2001681,2000403,2000611,0,0,0,0,0],0 NaNs: 0
    all fold     : 0.17137691521552612 (frequency).
    allFolded:     2.0<=8.294718/4455880<=14.0 0,[0,0,316127,316330,316724,315775,315915,326673,327005,333955,348843,379996,391867,374755,391915,0,0,0,0,0],0 NaNs: 0
    someoneOpened: 2.0<=7.939515/21544614<=14.0 0,[0,0,1684820,1681726,1684024,1683910,1683741,1674682,1672445,1665094,1650389,1619625,1609814,1625648,1608696,0,0,0,0,0],0 NaNs: 0
*/
    // the numbers above seem too similar: 7.9 vs 8.3?
    // seems like the difference should be more.
    void foo(int n) {
        double previousMean=Double.MIN_VALUE;
        for(int i=0;i<n;i++) {
            deck.shuffle();
            Card[] cards=deck.cards();
            int cardsInDeck=cards.length;
            for(int j=0;j<cardsInDeck;j++) { // half the deck to left and right
               hAll.add(cards[j].rank().ordinal());
                if(j<cardsInDeck/2) hLeft.add(cards[j].rank().ordinal());
                else hRight.add(cards[j].rank().ordinal());
            }
            boolean didAnyoneOpen=false;
            for(int j=0;j<cardsInDeck/2;j+=2) {
                Card card1=cards[j],card2=cards[j+1];
                SpecificHoldemHand h=card1.ordinal()>=card2.ordinal()?new SpecificHoldemHand(card1,card2)
                        :new SpecificHoldemHand(card2,card1);
                if(h.holdemHand().ordinal()<=HoldemHand.sevenSeven.ordinal()) { didAnyoneOpen=true; break; }
            }
            if(didAnyoneOpen) {
                for(int j=cardsInDeck/2;j<cardsInDeck;j++) // remaining cards to histogram
                    hSomeoneOpened.add(cards[j].rank().ordinal());
            } else {
                allFolded++;
                //System.out.println("all folded");
                // remaining cards to histogram
                for(int j=cardsInDeck/2;j<cardsInDeck;j++) hAllFolded.add(cards[j].rank().ordinal());
                if(false) {
                    for(int j=0;j<cardsInDeck/2;j+=2) {
                        Card card1=cards[j],card2=cards[j+1];
                        SpecificHoldemHand h=card1.ordinal()>=card2.ordinal()?new SpecificHoldemHand(card1,card2)
                                :new SpecificHoldemHand(card2,card1);
                        System.out.print(h.holdemHand()+" ");
                    }
                    System.out.println();
                }
            }
            if(i>0&&i%100==0) {
                for(Histogram histogram:histograms) {
                    if(histogram.n()<0) {
                        System.out.println("n is negative: "+histogram);
                        throw new RuntimeException("oops");
                    }
                    if(histogram.mean()<0) {
                        System.out.println("mean is negative: "+histogram);
                        throw new RuntimeException("oops");
                    }
                    if(histogram.mean()>Rank.ace.ordinal()) {
                        System.out.println("mean is too big: "+histogram);
                        throw new RuntimeException("oops");
                    }
                }
                print(i);
            }
            if(i>1000000) {
                double error=(hLeft.mean()-previousMean)/previousMean;
                //System.out.println("relative error: "+Math.abs(error));
                if(Math.abs((hLeft.mean()-previousMean)/previousMean)<.000000001) {
                    System.out.println("breaking out at: "+i);
                    System.out.println("relative error: "+error);
                    print(i);
                    break;
                }
            }
            previousMean=hLeft.mean();
        }
        print(n);
    }
    void run() {
        n=10_000_000;
        if(true) foo(n);
        else {
            int folds=52/2/2;
            System.out.println("folds: "+folds);
            for(int i=0;i<n;i++) { // try 10 players, so 4 folds
                deck.shuffle();
                Card[] cards=deck.cards();
                int cardsInDeck=cards.length;
                for(int j=0;j<cardsInDeck;j++) if(j<2*folds) hLeft.add(cards[j].rank().ordinal());
                else hRight.add(cards[j].rank().ordinal());
                boolean didAnyoneOpen=false;
                for(int j=0;j<2*folds;j+=2) {
                    Card card1=cards[j],card2=cards[j+1];
                    SpecificHoldemHand h=card1.ordinal()>=card2.ordinal()?new SpecificHoldemHand(card1,card2)
                            :new SpecificHoldemHand(card2,card1);
                    if(h.holdemHand().ordinal()<=HoldemHand.sevenSeven.ordinal()) { didAnyoneOpen=true; break; }
                }
                if(didAnyoneOpen) {
                    for(int j=2*folds;j<cardsInDeck;j++) hSomeoneOpened.add(cards[j].rank().ordinal());
                } else {
                    allFolded++;
                    //System.out.println("all folded");
                    for(int j=2*folds;j<cardsInDeck;j++) hAllFolded.add(cards[j].rank().ordinal());
                    if(false) {
                        for(int j=0;j<2*folds;j+=2) {
                            Card card1=cards[j],card2=cards[j+1];
                            SpecificHoldemHand h=card1.ordinal()>=card2.ordinal()?new SpecificHoldemHand(card1,card2)
                                    :new SpecificHoldemHand(card2,card1);
                            System.out.print(h.holdemHand()+" ");
                        }
                        System.out.println();
                    }
                }
                if(i>0&&i%1_000_000==0) {
                    for(Histogram histogram:histograms) {
                        if(histogram.n()<0) {
                            System.out.println("n is negative: "+histogram);
                            throw new RuntimeException("oops");
                        }
                        if(histogram.mean()<0) {
                            System.out.println("mean is negative: "+histogram);
                            throw new RuntimeException("oops");
                        }
                        if(histogram.mean()>Rank.ace.ordinal()) {
                            System.out.println("mean is too big: "+histogram);
                            throw new RuntimeException("oops");
                        }
                    }
                    print(i);
                }
            }
            print(n);
        }
    }
    public static void main(String[] args) { new CardRemoval().run(); }
    Deck deck=new Deck();
    int n;
    Histogram hLeft=new Histogram(20,0,20);
    Histogram hRight=new Histogram(20,0,20);
    Histogram hAllFolded=new Histogram(20,0,20);
    Histogram hSomeoneOpened=new Histogram(20,0,20);
    Histogram hAll=new Histogram(20,0,20);
    Set<Histogram> histograms=new LinkedHashSet<>();
    {
        histograms.add(hLeft);
        histograms.add(hRight);
        histograms.add(hAllFolded);
        histograms.add(hSomeoneOpened);
        histograms.add(hAll);
    }
    Integer allFolded=0;
}
