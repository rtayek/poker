package school;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
public class E {
	E(double p,double pot,double bet) {
		this.p=p;
		this.pot=pot;
		this.bet=bet;
	}
	@Override public String toString() {
		String probablility=String.format("%8.4f",p);
		String odds=String.format("%8.4f",odds(p));
		return "p="+probablility+"("+odds+":1), pot="+pot+", bet="+bet;
	}
	public static boolean check(double probability) {
		boolean rc=0<=probability&&probability<=1;
		if(!rc) {
			System.out.println(probability);
			throw new RuntimeException("oops");
		}
		return rc;
	}
	public static double odds(double p) {
		check(p);
		return p!=0?(1-p)/p:Double.POSITIVE_INFINITY;
	}
	public static double p1(double oddMent1,double oddMent2) {
		if(oddMent1<0||oddMent2<0) throw new RuntimeException("oops");
		double sum=oddMent1+oddMent2;
		if(sum==0) return Double.NaN;
		double p=oddMent1/(sum);
		check(p);
		return p;
	}
	public static double p2(double oddMent1,double oddMent2) {
		double p=oddMent2/(oddMent1+oddMent2);
		check(p);
		return p;
	}
	public static double probablility(double odds) {
		double p=1/(odds+1);
		check(p);
		return p;
	}
	double e1(double pBluff,double pCall) {
		double e1;
		check(p);
		check(pBluff);
		check(pCall);
		e1=p*(pCall*(pot+bet)+(1-pCall)*(pot))+(1-p)*(pBluff*(pCall*(-bet)+(1-pCall)*(pot))+(1-pBluff)*(0));
		return e1;
	}
	double e2(double pBluff,double pCall) {
		double e2;
		check(p);
		check(pBluff);
		check(pCall);
		e2=p*(pCall*(-bet)+(1-pCall)*(0))+(1-p)*(pBluff*(pCall*(pot+bet)+(1-pCall)*(0))+(1-pBluff)*(pot));
		return e2;
	}
	static void print(String s,double e1,double e2,double both) {
		System.out.printf("%20s",s);
		System.out.printf(" e1: %8.4f, e2: %8.4f, both: %8.4f",e1,e2,both);
		System.out.println();
	}
	void e(String s,double pBluff,double pCall) {
		double e1;
		double e2;
		double both;
		e1=e1(pBluff,pCall);
		e2=e2(pBluff,pCall);
		both=e1+e2;
		print(s,e1,e2,both);
	}
	void run() {
		System.out.println("p="+p+", odds="+odds(p)+", pot="+pot);
		e(" never bluff,  never call",0,0);
		e("always bluff,  never call",1,0);
		e(" never bluff, always call",0,1);
		e("always bluff, always call",1,1);
	}
	private void warmup() {
		double pBluff=0,pCall=0;
		double pBet=min(p+pBluff,1);
		System.out.println("pBluff "+pBluff+", pBet "+pBet+", pCall "+pCall);
		// always check
		System.out.println("pot="+pot);
		double e1=p*pot;
		double e2=(1-p)*pot;
		double both=e1+e2;
		System.out.println("always check:              e1 "+e1+", e2 "+e2+" ("+both+")");
		// bet when made, never call
		e1=p*pot+(1-p)*0;
		double temp=e1(0,0);
		if(e1!=temp) System.out.println(e1+"!="+temp);
		e2=p*(0)+(1-p)*pot;
		both=e1+e2;
		System.out.println("bet when hit, never call:  e1 "+e1+", e2 "+e2+" ("+both+")");
		// bet when made, call 50%
		e1=p*(.5*(pot+1)+(1-.5)*(pot))+(1-p)*(0);
		temp=e1(0,.5);
		if(e1!=temp) System.out.println(e1+"!="+temp);
		e2=p*(.5*(-1)+(1-.5)*(0))+(1-p)*(pot);
		both=e1+e2;
		System.out.println("bet when hit, call 50%   : e1 "+e1+", e2 "+e2+" ("+both+")");
		// bet when made, always call
		e1=p*(1*(pot+1))+(1-p)*(0);
		temp=e1(0,1);
		if(e1!=temp) System.out.println(e1+"!="+temp);
		e2=p*(1*(-1))+(1-p)*(pot);
		both=e1+e2;
		System.out.println("bet when hit, always call: e1 "+e1+", e2 "+e2+" ("+both+")");
		pBet=min(p+pBluff,1);
		pBet=0;
		pBluff=0;
		pCall=0;
		System.out.println("--------------------------------");
		e1=e1(0,0);
		e2=e2(0,0);
		both=e1+e2;
		System.out.println("never bluff,  never call:   e1 "+e1+", e2 "+e2+" ("+both+")");
		e1=e1(0,.5);
		e2=e2(0,.5);
		both=e1+e2;
		System.out.println("never bluff,    50% call:   e1 "+e1+", e2 "+e2+" ("+both+")");
		e1=e1(0,1);
		e2=e2(0,1);
		both=e1+e2;
		System.out.println("never bluff, always call:   e1 "+e1+", e2 "+e2+" ("+both+")");
		System.out.println("--------------------------------");
		e1=e1(1-p,0);
		e2=e2(1-p,0);
		both=e1+e2;
		System.out.println("always bluff,  never call:  e1 "+e1+", e2 "+e2+" ("+both+")");
		e1=e1(1-p,.5); // these are wrong!! sb 100%
		e2=e2(1-p,.5);
		both=e1+e2;
		System.out.println("always bluff,    50% call:  e1 "+e1+", e2 "+e2+" ("+both+")");
		e1=e1(1-p,1);
		e2=e2(1-p,1);
		both=e1+e2;
		print("always bluff, always call:",e1,e2,both);
		System.out.println("--------------------------------");
		e("bluff  0%, call   0%",p,0,0,pot);
		e("bluff  0%, call  25%",p,0,.25,pot);
		e("bluff  0%, call  50%",p,0,.5,pot);
		e("bluff  0%, call 100%",p,0,1,pot);
		System.out.println("--------------------------------");
		e("bluff  25%, call   0%",p,.25,0,pot);
		e("bluff  25%, call  25%",p,.25,.25,pot);
		e("bluff  25%, call  50%",p,.25,.5,pot);
		e("bluff  25%, call 100%",p,.25,1,pot);
		System.out.println("--------------------------------");
		e("bluff  50%, call   0%",p,.5,0,pot);
		e("bluff  50%, call  25%",p,.5,.25,pot);
		e("bluff  50%, call  50%",p,.5,.5,pot);
		e("bluff  50%, call 100%",p,.5,1,pot);
		System.out.println("--------------------------------");
		e("bluff 100%, call   0%",p,1-p,0,pot);
		e("bluff 100%, call  25%",p,1-p,.25,pot);
		e("bluff 100%, call  50%",p,1-p,.5,pot);
		e("bluff 100%, call 100%",p,1-p,1,pot);
		System.out.println("--------------------------------");
	}
	private static void e(String s,double p,double pBluff,double pCall,double pot) {
		double e1;
		double e2;
		double both;
		E e=new E(p,pot,1);
		e1=e.e1(pBluff,pCall);
		e2=e.e2(pBluff,pCall);
		both=e1+e2;
		print(s,e1,e2,both);
	}
	static Super strategy(double payoffMatrix[][]) {
		int rows=payoffMatrix.length;
		int columns=payoffMatrix[0].length;
		double p11=payoffMatrix[0][0];
		double p12=payoffMatrix[0][columns-1];
		double p21=payoffMatrix[rows-1][0];
		double p22=payoffMatrix[rows-1][columns-1];
		return school.E.strategy(p11,p12,p21,p22);
	}
	static Super strategy(double p11,double p12,double p21,double p22) {
		double[][] payoff=new double[2][2];
		payoff[0][0]=p11;
		payoff[0][1]=p12;
		payoff[1][0]=p21;
		payoff[1][1]=p22;
		Super super0=new Super(new double[] {1,2},new double[] {1,2},payoff);
		super0.minMax();
		super0.printMatrix();
		if(abs(super0.maxMin-super0.minMax)<epsilon) {
			System.out.println("saddle point");
			System.out.println("value of game is: "+super0.minMax);
		} else {
			System.out.println("no saddle point");
			// double red2=abs(payoffMatrix[0][0]-payoffMatrix[rows-1][0]);
			// double
			// red1=abs(payoffMatrix[0][columns-1]-payoffMatrix[rows-1][columns-1]);
			double red2Oddment=abs(p11-p21);
			double red1Oddment=abs(p12-p22);
			System.out.printf("Red  %6.4f:%6.4f (%6.4f,%6.4f)\n",red1Oddment,red2Oddment,p1(red1Oddment,red2Oddment),p2(red1Oddment,red2Oddment));
			double blue2Oddment=abs(p11-p12);
			double blue1Oddment=abs(p21-p22);
			System.out.printf("Blue %6.4f:%6.4f (%6.4f,%6.4f)\n",blue1Oddment,blue2Oddment,p1(blue1Oddment,blue2Oddment),p2(blue1Oddment,blue2Oddment));
			double redAgaintsBlue1=(red1Oddment*p11+red2Oddment*p12)/(red1Oddment+red2Oddment);
			double redAgaintsBlue2=(red1Oddment*p21+red2Oddment*p22)/(red1Oddment+red2Oddment);
			if(abs(redAgaintsBlue1-redAgaintsBlue2)>=epsilon) System.err.println("oops");
			System.out.println("red against blue1: "+redAgaintsBlue1);
			System.out.println("red against blue2: "+redAgaintsBlue2);
			// System.out.println("value of game is: "+super0.minMax);
		}
		return super0;
	}
	static void run2() {
		strategy(0,0,-0.1875,0.6250);
		System.out.println("---------");
		strategy(0.8125,0,0.6250,1.4375);
		System.out.println("---------");
	}
	public static void main(String[] args) {
		int outs=9,remaining=52-4;
		double p=outs/(double)remaining;
		// E e=new E(p,0,1);
		// e.run();
		// e=new E(p,1,1);
		// e.run();
		run2();
	}
	final double p,pot,bet;
	static final double epsilon=1e-10;
}
