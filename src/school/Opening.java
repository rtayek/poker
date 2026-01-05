package school;
import static java.lang.Math.*;
import java.util.*;
import equipment.Rank;

public class Opening {
	static double pBestHand(double pOpeningRange,int seat) {
		double pBestHand=pow(1-pOpeningRange,handed-seat);
		return pBestHand; // probability of having the best hand with seat players left to act.
	}
	static double pRangeFor(double pBestHand,int seat) {
		double pRange=1-exp(log(pBestHand)/(handed-seat));
		return pRange; // required range for having the best hand with probability pBestHand
	}
	static void printBlinds() {
		for(int i=0;i<blinds;i++)
			System.out.println("blind "+(i+1));
	}
	public static void main(String[] args) {
		System.out.println("\n"+handed+" handed ring game.");
		double pRange=.05;
		System.out.println("\n"+"pOpeningHand="+pRange);
		for(int seat=1;seat<=handed-2;seat++) {
			double pBestHand=pBestHand(pRange,seat);
			// System.out.println("seat  "+seat+", players to act:  "+(handed-seat)+", pBestHand=(1-"+pRange+")^"+(handed-seat)+"="+pBestHand);
			System.out.printf("seat %2d, players to act: %2d, pBestHand = (1-%4.2f)^%2d = %4.2f",seat,handed-seat,pRange,handed-seat,pBestHand);
			System.out.println();
		}
		printBlinds();
		double pDesired=.5;
		System.out.println("\n"+"solving for pDesired="+pDesired);
		for(int seat=1;seat<=handed-1;seat++) {
			double pBestHand=.5;
			pRange=pRangeFor(pBestHand,seat);
			System.out.printf("seat %2d, players to act: %2d, pB=%7.2f, pR=%7.2f",seat,handed-seat,pBestHand,pRange);
			System.out.println();
		}
		printBlinds();
	}
	static int handed=2,blinds=2;
}
