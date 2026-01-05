import equipment.Cards;


public class Bernoili {
	static double b(int x,int n,double p) {
		return Cards.c(x,n)*Math.pow(p,x)*Math.pow(1-p,n-x);
	}
	public static void main(String[] args) {
		System.out.println(b(500,1000,.5));
		// prints 0, n is too big
		//csawicz@dslextreme.com
		
	}
}
