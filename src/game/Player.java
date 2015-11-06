package game;
import equipment.Hand;
public class Player {
	@Override public String toString() {
		return (hand!=null?hand:"no hand")+" "+chips;
	}
	Hand hand;
	double chips;
}
