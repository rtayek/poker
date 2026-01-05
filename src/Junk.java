import static poker.Constants.*;
import static equipment.Cards.*;
public class Junk {
	public static void main(String[] args) {
		System.out.println();
		System.out.println(59800/(float)totalHighHands);
		//flushes=1287
		System.out.println("non flushes: "+c(ranks+5-1,5));
		System.out.println("flushes: "+c(ranks,5));
		System.out.println("non flushes: "+(totalHighHands-c(ranks,5)));
	}
}
