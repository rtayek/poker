package poker;
import equipment.*;
import static equipment.Cards.*;
public class Ruin {
	public static void main(String[] args) {
		double p=.5;
		int n=10;
		for(int x=0;x<=10;x++)
			System.out.println(x+" "+bernoulli(x,n,p));
	}
}
