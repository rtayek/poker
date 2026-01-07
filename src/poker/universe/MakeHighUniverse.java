package poker.universe;
import static poker.Constants.*;
import lookup.OldLookup;
import equipment.Cards;
import equipment.Rank;
import poker.*;
public class MakeHighUniverse extends MakeUniverse {
    private static void summary() {
        if(includeSummary) {
            String s=(t52-pt52)+"\t"+(tj-ptj)+"\t"+(t52-pt52+tj-ptj)+"\t"+type.toCharacter();
            out.println(s);
            out.flush();
        }
        pt52=t52;
        ptj=tj;
    }
    private static final void put() {
        t52+=f52;
        tj+=fj;
        Rank[] rank=new Rank[5];
        rank[0]=Rank.fromInt(c1);
        rank[1]=Rank.fromInt(c2);
        rank[2]=Rank.fromInt(c3);
        rank[3]=Rank.fromInt(c4);
        rank[4]=Rank.fromInt(c5);
        n++;
        if(includeDetails) {
            OldLookup.toCanonicalForm(rank); // should not need this
            boolean isSuited=type==PokerHand.HighType.flush||type==PokerHand.HighType.straightFlush;
            String s=n+" ";
            for(int i=0;i<5;i++) {
                if(rank[i]==Rank.aceLow) rank[i]=Rank.ace;
                s+=rank[i].toCharacter();
            }
            s+=" "+f52+" "+fj+" "+type.toCharacter();
            for(int i=0;i<5;i++) {
                int r=rank[i].ordinal();
                s+=" ";
                if(r<10) s+="0";
                s+=r;
            }
            out.println(s);
            out.flush();
        }
    }
    private static final void fiveOfAKind() {
        int stop;
        type=PokerHand.HighType.fiveOfAKind;
        stop=includeAllFiveOfAKinds?duece:ace;
        for(c1=ace;c1>=stop;c1--) {
            c5=c4=c3=c2=c1;
            j=1;
            f52=0;
            fj=c1==ace?1:0; // aces straights and flushes
            put();
        }
    }
    private static final void straightFlush() {
        type=PokerHand.HighType.straightFlush;
        for(c1=ace;c1>=five;c1--) {
            c2=c1-1;
            c3=c2-1;
            c4=c3-1;
            c5=c4-1;
            f52=4;
            if(c1==ace) j=5;
            else j=4;
            fj=j*4;
            put();
        }
    }
    private static final void fourOfAKind() {
        type=PokerHand.HighType.fourOfAKind;
        for(c1=ace;c1>=duece;c1--)
            for(c5=ace;c5>=duece;c5--)
                if(c1!=c5) {
                    c2=c3=c4=c1;
                    f52=4;
                    if(c1==ace) {
                        j=1;
                        fj=(int)(j*Cards.c(4,3)*4);
                    } else {
                        if(c5==ace) j=1;
                        else j=0;
                        fj=j;
                    }
                    put();
                }
    }
    private static final void fullHouse() {
        type=PokerHand.HighType.fullHouse;
        for(c1=ace;c1>=duece;c1--)
            for(c4=ace;c4>=duece;c4--)
                if(c1!=c4) {
                    c2=c3=c1;
                    ;
                    c5=c4;
                    f52=(int)(Cards.c(4,3)*Cards.c(4,2));
                    if(c1==ace) {
                        j=1;
                        fj=(int)(j*Cards.c(4,2)*Cards.c(4,2));
                    } else {
                        if(c4==ace) j=1;
                        else j=0;
                        fj=(int)(j*Cards.c(4,3)*4);
                    }
                    put();
                }
    }
    private static final void flush() {
        type=PokerHand.HighType.flush;
        for(c1=ace;c1>=six;c1--)
            for(c2=c1-1;c2>=five;c2--)
                for(c3=c2-1;c3>=four;c3--)
                    for(c4=c3-1;c4>=trey;c4--)
                        for(c5=c4-1;c5>=duece;c5--)
                            if(c1-c5!=4&&!(c1==ace&&c2==five)) { /* no straights */
                                f52=4;
                                if(c5==ten) j=5;
                                else if(c4==jack) j=4;
                                else if(c3==queen) j=3;
                                else if(c2==king) j=2;
                                else if(c1==ace) j=1;
                                else j=0;
                                if(c1==ace) {
                                    if(c2-c5<=4) j--;
                                    if(c2==king&&c3<=five) j--; /* wow */
                                }
                                fj=4*j;
                                put();
                            }
    }
    private static final void straight() {
        type=PokerHand.HighType.straight;
        for(c1=ace;c1>=five;c1--) {
            c2=c1-1;
            c3=c2-1;
            c4=c3-1;
            c5=c4-1;
            f52=4*4*4*4*4-4;
            if(c1==ace) j=5;
            else j=4;
            fj=j*(4*4*4*4-4);
            put();
        }
    }
    private static final void threeOfAKind() {
        type=PokerHand.HighType.threeOfAKind;
        for(c1=ace;c1>=duece;c1--)
            for(c4=ace;c4>=trey;c4--)
                for(c5=c4-1;c5>=duece;c5--)
                    if(c4!=c1&&c5!=c1) {
                        c2=c3=c1;
                        f52=(int)Cards.c(4,3)*4*4;
                        if(c1==ace) {
                            j=1;
                            fj=(int)(j*Cards.c(4,2)*4*4);
                        } else {
                            if(c4==ace) j=1;
                            else j=0;
                            fj=(int)(j*Cards.c(4,3)*Cards.c(4,1));
                        }
                        put();
                    }
    }
    private static final void twoPair() {
        type=PokerHand.HighType.twoPair;
        for(c1=ace;c1>=trey;c1--)
            for(c3=c1-1;c3>=duece;c3--)
                for(c5=ace;c5>=duece;c5--)
                    if(c5!=c1&&c5!=c3) {
                        c2=c1;
                        c4=c3;
                        f52=(int)(Cards.c(4,2)*Cards.c(4,2)*4);
                        if(c1==ace) {
                            j=1;
                            fj=(int)(Cards.c(4,1)*Cards.c(4,2)*4);
                        } else {
                            if(c5==ace) j=1;
                            else j=0;
                            fj=(int)(j*Cards.c(4,2)*Cards.c(4,2));
                        }
                        put();
                    }
    }
    private static final void onePair() {
        type=PokerHand.HighType.onePair;
        for(c1=ace;c1>=duece;c1--)
            for(c3=ace;c3>=four;c3--)
                if(c1!=c3) for(c4=c3-1;c4>=duece;c4--)
                    if(c1!=c4) for(c5=c4-1;c5>=duece;c5--)
                        if(c1!=c5) {
                            c2=c1;
                            f52=(int)(Cards.c(4,2)*4*4*4);
                            if(c1==ace) {
                                if(c3<=five||c5>=ten) j=0;
                                else j=1;
                                fj=j*(4*4*4*4-4);
                            } else {
                                if(c3==ace) j=1;
                                else j=0;
                                fj=(int)(j*Cards.c(4,2)*4*4);
                            }
                            put();
                        }
    }
    private static final void noPair() {
        type=PokerHand.HighType.noPair;
        for(c1=ace;c1>=six;c1--)
            for(c2=c1-1;c2>=five;c2--)
                for(c3=c2-1;c3>=four;c3--)
                    for(c4=c3-1;c4>=trey;c4--)
                        for(c5=c4-1;c5>=duece;c5--)
                            if(c1-c5!=4&&!(c1==ace&&c2==five)) /* no straights */
                            {
                                if(c1==ace) {
                                    j=1;
                                    if(c2-c5<=4) j--;
                                } else j=0;
                                f52=4*4*4*4*4-4;
                                fj=j*(4*4*4*4-4);
                                put();
                            }
    }
    @Override protected String commandName() {
        return "MakeHighUniverse";
    }
    @Override protected void setDetails(boolean value) {
        includeDetails=value;
    }
    @Override protected void setSummary(boolean value) {
        includeSummary=value;
    }
    @Override protected void setWild(boolean value) {
        includeAllFiveOfAKinds=value;
    }
    public static void main(String[] arg) {
        new MakeHighUniverse().parseArguments(arg);
        n=t52=tj=0;
        fiveOfAKind();
        summary();
        straightFlush();
        summary();
        fourOfAKind();
        summary();
        fullHouse();
        summary();
        flush();
        summary();
        straight();
        summary();
        threeOfAKind();
        summary();
        twoPair();
        summary();
        onePair();
        summary();
        noPair();
        summary();
        N=includeAllFiveOfAKinds?totalHighHands:naturalHighHands+/*five aces*/1;
        System.err.println("verify: "+n+"="+N);
        if(n==N) System.err.println("verified");
        else {
            System.err.println("error n!=N");
            System.exit(1);
        }
        System.err.println("verify: c(52,5)="+t52+"="+naturalFiveCardCombinations+", c(53,5)-c(52,5)="+tj+"="+(allFiveCardCombinationsWithOneJoker-naturalFiveCardCombinations));
        if(t52==naturalFiveCardCombinations&&tj==allFiveCardCombinationsWithOneJoker-naturalFiveCardCombinations) System.err.println("verified");
        else {
            System.err.println("error t52 ot tj");
            System.exit(1);
        }
    }
    private static PokerHand.HighType type;
    private static int c1,c2,c3,c4,c5,f52,fj,j;
    static int pt52,ptj;
    static int N,n,t52,tj;
    static boolean includeAllFiveOfAKinds=false; // i.e. not just five aces.
    private static boolean includeDetails=false;
    private static boolean includeSummary=false;
}
