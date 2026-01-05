package omaha;
import static equipment.Cards.*;
import equipment.Cards;
public class Omaha {
	public static void main(String[] args) {
		System.out.println(startingHands+" startingHands");
		int fourOfAKind=(int)c(13,1);
		System.out.println(fourOfAKind+" fourOfAKinds");
		int trips=(int)(c(13,1)*c(4,3)*c(12,1)*c(4,1));
		// c(4,1) above looks bad. maybe should be c(11,1)
		// maybe needs *c(11,1)*c(4,1)?
		System.out.println(trips+" trips");
	}
	static final int startingHands=(int)Cards.c(52,4);
	private static int n;
}
