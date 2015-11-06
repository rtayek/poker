package poker.holdem;
import java.util.List;
import poker.holdem.HoldemHand;
import junit.framework.TestCase;
public class HoldemHandTestCase extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}
	public void testType() {
		for(HoldemHand h:HoldemHand.values())
			assertEquals(h.type,h.type());
	}
	public void testOrder() {
		for(HoldemHand h:HoldemHand.values())
			assertTrue(h.r1.ordinal()>=h.r2.ordinal());
	}
	public void testRange() {
		for(HoldemHand holdemHand:HoldemHand.values()) {
			HoldemHand.Range range=new HoldemHand.Range(holdemHand);
			List<HoldemHand> hands=range.hands();
			int n=hands.size()*range.type.frequency();
			System.out.println(holdemHand+"+ "+hands.size()+" hands: "+hands+" "+n+" "+(float)(n/(double)HoldemHand.universe));
		}
		System.out.println(HoldemHand.values().length);
	}
}
