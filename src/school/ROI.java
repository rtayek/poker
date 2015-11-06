package school;
public class ROI {
	public static void main(String[] args) {
		double bankroll=4*312.5;
		double buyin=.04;
		int minimumBuyin=50;
		double buyinAmount=bankroll*buyin;
		double bigBlindSize=buyinAmount/minimumBuyin;
		System.out.print("bankroll=$"+bankroll);
		System.out.print(", risk="+buyin);
		System.out.print(", buyins="+1/buyin);
		System.out.print(", minimum buyin="+minimumBuyin+" BB");
		System.out.print(", buyin amount=$"+buyinAmount);
		System.out.println(", Big Blind=$"+bigBlindSize);
		int winRateBB100=4;
		for(winRateBB100=1;winRateBB100<=8;winRateBB100++)
			System.out.println("win rates: "+winRateBB100+" BB/100, "+calculateAndFormat(bankroll,bigBlindSize,winRateBB100));
	}
	private static String calculateAndFormat(double bankroll,double bigBlindSize,int winRateBB100) {
		double win=bigBlindSize*winRateBB100; // win amount/100 hand in bb's
		double winRate=win/bankroll;
		return String.format("$%4.2f/100, ",win)+String.format("%4.2f %%/hr, ",winRate*100)+String.format("%5.2f %%/day, ",24*winRate*100)
				+String.format("%7.2f %%/year, ",2000*winRate*100)+String.format("$%7.2f/month, ",2000*win/12)+String.format("$%8.2f/year, ",2000*win);
	}
}
