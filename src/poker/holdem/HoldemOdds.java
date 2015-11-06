package poker.holdem;
import equipment.Cards;
public class HoldemOdds {
	private static double odds(double unfavorable,double favorable) {
		return unfavorable/favorable;
	}
	private static double odds(int remaining,int outs,int unfavorable) {
		int none=Cards.c(unfavorable,2);
		int one=Cards.c(unfavorable,1)*Cards.c(outs,1);
		int both=Cards.c(outs,2);
		int total=Cards.c(remaining,2);
		if(none+one+both!=total) throw new RuntimeException("oops");
		double odds=none/(double)(one+both);
		double p=(one+both)/(double)total;
		if(Math.abs((1-p)/p-odds)/odds>1e-10) {
			System.out.println(odds+" "+(1-p)/p+" "+p+" "+Math.abs((1-p)/p-odds));
			throw new RuntimeException("oops");
		}
		// System.out.println(none+" "+one+" "+both+" "+total+" "+(none+one+both));
		return odds;
	}
	public static void main(String[] args) {
		int remaining=52-5; // remaining cards on the flop.
		if(false) {
			System.out.println("turn");
			System.out.println("outs\todds");
			for(int outs=1;outs<=10;outs++) {
				int unfavorable=remaining-outs;
				System.out.println(outs+"\t"+odds(unfavorable,outs));
			}
			System.out.println("river");
			System.out.println("outs\todds");
			for(int outs=1;outs<=22;outs++) {
				int unfavorable=remaining-outs;
				double odds=odds(remaining,outs,unfavorable);
				System.out.println(outs+"\t"+odds+"\t"+(float)(1/(odds+1)));
			}
			System.out.println("both");
		}
		System.out.println("outs\t turn\t river\t   %\t %\n");
		for(int outs=1;outs<=22;outs++) {
			int unfavorable=remaining-outs;
			double odds=odds(unfavorable,outs);
			double odds2=odds(remaining,outs,unfavorable);
			// System.out.println(outs+"\t"+odds+"\t"+odds2+"\t"+(float)(1/(odds+1))+"\t"+(float)(1/(odds2+1)));
			System.out.printf("%3d\t%6.2f\t%6.2f\t%6.2f\t%6.2f\n",outs,odds,odds2,100/(odds+1),100/(odds2+1));
		}
	}
}
