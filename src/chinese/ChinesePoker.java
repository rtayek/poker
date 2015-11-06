package chinese;
import java.util.*;

import lookup.OldLookup;
import poker.*;
import equipment.*;
import static equipment.Deck.*;
public class ChinesePoker {
	public static void main(String[] args) {
		Deck deck=new Deck();
		deck.shuffle();
		Card[] cards=deck.draw(13);
		System.out.println(cards);
		System.out.println(c(13,5)*c(8,5));
		Hand hand=new Hand(cards);
		System.out.println(hand);
		Combination combination=new Combination(13,5);
		int n=0,m=0;
		while(combination.hasNext()) {
			int[] indices=combination.next();
			List<Integer> l=makeList(indices);
			Hand pokerHand=new PokerHand(cards[indices[0]],cards[indices[1]],cards[indices[2]],cards[indices[3]],cards[indices[4]]);
			int handNumber=OldLookup.lookup(pokerHand);
			PokerHand.HighType typeOfPokerHand=PokerHand.HighType.type(handNumber);
			System.out.println(l+" "+pokerHand+" "+handNumber+" "+typeOfPokerHand);
			int[] indices2=new int[8];
			int j=0;
			for(int i=0;i<13;i++)
				if(!l.contains(i))
					indices2[j++]=i;
			List<Integer> l2=makeList(indices2);
			System.out.println("\t"+l2);
			Combination combination2=new Combination(8,5);
			while(combination2.hasNext()) {
				int[] indices3=combination2.next();
				List<Integer> l3=makeList(indices3);
				System.out.println("\t"+l3);
				Hand pokerHand2=new PokerHand(
						cards[indices2[indices3[0]]],
						cards[indices2[indices3[1]]],
						cards[indices2[indices3[2]]],
						cards[indices2[indices3[3]]],
						cards[indices2[indices3[4]]]);
				int handNumber2=OldLookup.lookup(pokerHand2);
				PokerHand.HighType typeOfPokerHand2=PokerHand.HighType.type(handNumber2);
				System.out.println("\t"+l+" "+pokerHand2+" "+handNumber2+" "+typeOfPokerHand2);
			
			Card[] cards2=new Card[8];
			m++;
			}
			n++;
			if(false&&n>10)
				break;
		}
		System.out.println(n);
		System.out.println(m);
		System.out.println(c(13,5)*c(8,5));
	}

	private static List<Integer> makeList(int[] indices) {
		List<Integer> l=new ArrayList<Integer>(indices.length);
		for(int i:indices)
			l.add(i);
		return l;
	}
}
