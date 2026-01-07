package poker.universe;
import static poker.Constants.*;
import lookup.OldLookup;
import poker.*;
import equipment.Cards;
import equipment.Rank;
public class MakeLowUniverse extends MakeUniverse {
    private static void summary() {
        if(summary) {
            String s=(t52-pt52)+" "+(tj-ptj)+" "+(t52-pt52+tj-ptj)+" "+type.toCharacter();
            out.println(s);
            out.flush();
        }
        pt52=t52;
        ptj=tj;
    }
    private static final void put() {
        t52+=f52;
        tj+=fj;
        Rank[] ranks=new Rank[5];
        ranks[0]=Rank.fromInt(c1);
        ranks[1]=Rank.fromInt(c2);
        ranks[2]=Rank.fromInt(c3);
        ranks[3]=Rank.fromInt(c4);
        ranks[4]=Rank.fromInt(c5);
        n++;
        if(details) {
            OldLookup.toCanonicalForm(ranks);
            int key=(int)OldLookup.key(ranks,false);
            String s=n+" ";
            for(int i=0;i<5;i++) {
                if(ranks[i]==Rank.aceLow) ranks[i]=Rank.ace;
                s+=ranks[i].toCharacter();
            }
            s+=" "+f52+" "+fj+" "+type.toCharacter();
            s+=" "+key;
            out.println(s);
            out.flush();
        }
    }
    private static void noPair() {
        type=PokerHand.HighType.noPair;
        for(c1=five;c1<=king;c1++)
            for(c2=four;c2<c1;c2++)
                for(c3=trey;c3<c2;c3++)
                    for(c4=duece;c4<c3;c4++)
                        for(c5=aceLow;c5<c4;c5++) {
                            if(c1==five) j=5;
                            else if(c2==four) j=4;
                            else if(c3==trey) j=3;
                            else if(c4==duece) j=2;
                            else if(c5==aceLow) j=1;
                            else j=0;
                            f52=1024;
                            fj=j*256;
                            put();
                        }
    }
    private static void onePair() {
        type=PokerHand.HighType.onePair;
        for(c1=aceLow;c1<=king;c1++)
            for(c3=trey;c3<=king;c3++)
                if(c3!=c1) for(c4=duece;c4<c3;c4++)
                    if(c4!=c1) for(c5=aceLow;c5<c4;c5++)
                        if(c5!=c1) {
                            c2=c1;
                            if(c1==aceLow) {
                                if(c3==four) j=3;
                                else if(c4==trey) j=2;
                                else if(c5==duece) j=1;
                                else j=0;
                            } else if(c1==duece) {
                                if(c3==four) j=3;
                                else if(c4==trey) j=2;
                                else if(c5==aceLow) j=1;
                                else j=0;
                            } else if(c1==trey) {
                                if(c3==four) j=3;
                                else if(c4==duece) j=2;
                                else if(c5==duece) j=1;
                                else j=0;
                            } else {
                                if(c3==trey) j=3;
                                else if(c4==duece) j=2;
                                else if(c5==aceLow) j=1;
                                else j=0;
                            }
                            f52=(int)(Cards.c(4,2)*4*4*4);
                            fj=(int)(j*Cards.c(4,2)*4*4);
                            put();
                        }
    }
    private static void twoPair() {
        type=PokerHand.HighType.twoPair;
        for(c1=duece;c1<=king;c1++)
            for(c3=aceLow;c3<c1;c3++)
                for(c5=aceLow;c5<=king;c5++)
                    if(c5!=c1&&c5!=c3) {
                        c2=c1;
                        c4=c3;
                        if(c5<=trey) j=1;
                        else j=0;
                        if(c3==aceLow) {
                            if(!(c1==duece&&c5==trey||c1>=trey&&c5==duece)) j=0;
                        } else {
                            if(c5!=aceLow) j=0;
                        }
                        f52=(int)(Cards.c(4,2)*Cards.c(4,2)*Cards.c(4,1));
                        fj=(int)(j*Cards.c(4,2)*Cards.c(4,2));
                        put();
                    }
    }
    private static void threeOfAKind() {
        type=PokerHand.HighType.threeOfAKind;
        for(c1=aceLow;c1<=king;c1++)
            for(c4=duece;c4<=king;c4++)
                if(c4!=c1) for(c5=aceLow;c5<c4;c5++)
                    if(c5!=c1) {
                        c3=c2=c1;
                        if(c1==aceLow) {
                            if(c4==trey) j=2;
                            else if(c5==duece) j=1;
                            else j=0;
                        } else if(c1==duece) {
                            if(c4==trey) j=2;
                            else if(c5==aceLow) j=1;
                            else j=0;
                        } else {
                            if(c4==duece) j=2;
                            else if(c5==aceLow) j=1;
                            else j=0;
                        }
                        f52=(int)Cards.c(4,3)*4*4;
                        fj=(int)(j*Cards.c(4,3)*4);
                        put();
                    }
    }
    private static void fullHouse() {
        type=PokerHand.HighType.fullHouse;
        for(c1=aceLow;c1<=king;c1++)
            for(c4=aceLow;c4<=king;c4++)
                if(c4!=c1) {
                    c3=c2=c1;
                    c5=c4;
                    f52=(int)(Cards.c(4,3)*Cards.c(4,2));
                    fj=0;
                    put();
                }
    }
    private static void fourOfAKind() {
        type=PokerHand.HighType.fourOfAKind;
        for(c1=aceLow;c1<=king;c1++)
            for(c5=aceLow;c5<=king;c5++)
                if(c5!=c1) {
                    c4=c3=c2=c1;
                    if(c1==aceLow) {
                        if(c5==duece) j=1;
                        else j=0;
                    } else {
                        if(c5==aceLow) j=1;
                        else j=0;
                    }
                    f52=(int)(Cards.c(4,4)*Cards.c(4,1));
                    fj=(int)(j*Cards.c(4,4));
                    put();
                }
    }
    static void usage() {
        System.err.println("MakeLowUniverse -[wds]");
        System.err.println("-d - details");
        System.err.println("-s - summary");
        System.err.println("-w - wild");
    }
    static void option(String option) {
        switch (option) {
            case "d" -> details=true;
            case "s" -> summary=true;
            case "w" -> wild=true;
            default -> {
                usage();
                System.exit(1);
            }
        }
    }
    static void argument(String argument) {
        usage();
        System.exit(1);
    }
    public static void main(String[] arg) {
        for(String argValue:arg) {
            if(argValue.startsWith("-")) option(argValue.substring(1));
            else argument(argValue);
        }
        noPair();
        summary();
        onePair();
        summary();
        twoPair();
        summary();
        threeOfAKind();
        summary();
        fullHouse();
        summary();
        fourOfAKind();
        summary();
        int N=lowHands;
        System.err.println("verify: "+n+"="+N);
        if(n==N) System.err.println("verified");
        else {
            System.err.println("error");
            System.exit(1);
        }
        System.err.println("verify: Cards.c(52,5)="+t52+"="+naturalFiveCardCombinations+", Cards.c(53,5)-Cards.c(52,5)="+tj+"="+(allFiveCardCombinationsWithOneJoker-naturalFiveCardCombinations));
        if(t52==naturalFiveCardCombinations&&tj==allFiveCardCombinationsWithOneJoker-naturalFiveCardCombinations) System.err.println("verified");
        else {
            System.err.println("error");
            System.exit(1);
        }
        if(false) {
            System.err.println("type enter to exit");
        }
    }
    private static PokerHand.HighType type; // should this be high type?
    private static int c1,c2,c3,c4,c5,f52,fj,j;
    private static int pt52=0,ptj=0;
    private static int n,t52,tj;
    private static boolean wild=false;
    private static boolean details=false;
    private static boolean summary=false;
}
