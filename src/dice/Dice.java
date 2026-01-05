package dice;
import static equipment.Cards.*;
//5 6 sided dice
//Five of a kind: 6/65 = 0.08% (obvious)
//Four of a kind: 5*6*5 = 1.93% (five possible positions for the singleton * 6 ranks for the four of a kind * 5 ranks for the singleton).
//Full house: combin(5,3)*6*5/65 = 3.86% (combin(5,3) positions for the three of a kind * 6 ranks for the three of a kind * 2 ranks for the pair).
//Three of a kind: COMBIN(5,3)*COMBIN(2,1)*6*COMBIN(5,2) / 65 = 15.43%. (combin(5,3) positions for the three of a kind * combin(2,1) positions for the larger of the singletons * 6 ranks of the three of a kind * combin(5,2) ranks for the two singletons.
//Two pair: COMBIN(5,2)*COMBIN(3,2)*COMBIN(6,2)*4 / 65 = 23.15% (combin(5,2) positions for the higher pair * combin(3,2) positions for the lower pair * combin(6,4) ranks for the two pair * 4 ranks for the singleton.
//Pair: COMBIN(5,2)*fact(3)*6*combin(5,3) / 65 = 46.30% (combin(5,2) positions for the pair * fact(3) positions for the three singletons * 6 ranks for the pair * combin(5,3) ranks for the singletons.
//Straight: 2*fact(5) / 65 = 3.09% (2 spans for the straight {1-5 or 2-6} * fact(5) ways to arrange the order).
//Nothing: ((COMBIN(6,5)-2)*FACT(5)) / 65 = 6.17% (combin(6,5) ways to choose 5 ranks out of six, less 2 for the straights, * fact(5) ways to arrange the order.

public class Dice {
    static void throwDice(int dice,int sides) {
        for(int i=0;i<=dice;i++) {
            double p=1./sides;
            int factor=(int)c(dice,i);
            double b=bernoulli(i,dice,p);
            double probability=factor*b;
            
            System.out.println(i+" "+dice+" 1/"+sides+" "+factor+" "+b+" "+probability);
        }
    }
    public static void main(String[] arg) {
        int dice=10;
        int sides=6;
        throwDice(5,sides);    
    }
}
// 5 f52=0;
// S f52=4;
// 4 f52=4;
// p f52=(int)(Cards.c(4,3)*Cards.c(4,2));
// f f52=4;
// s f52=4*4*4*4*4-4;
// 3 f52=(int)Cards.c(4,3)*4*4;
// 2 f52=(int)(Cards.c(4,2)*Cards.c(4,2)*4);
// 1 f52=(int)(Cards.c(4,2)*4*4*4);
// n f52=4*4*4*4*4-4;

