package poker.holdem;
import poker.holdem.old.Donb404182010;
import junit.framework.TestCase;
public class DonTestCase extends TestCase {
	public static void main(String[] args) {
		junit.textui.TestRunner.run(DonTestCase.class);
	}
	public DonTestCase(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testOldDon() {
		Donb404182010.main(new String[] {/*"-v",*/"-5","AcKc","AhKd"});
		
	}
	public void testMain() {
//		Don.main(new String[] {/*"-v",*/"-5","AcAd","AhAs"});
		Don.main(new String[] {/*"-v",*/"-5","AcQd","9d9s","JhTh"});
//		Don.main(new String[] {/*"-v",*/"-5","QcQd","AhKs"});
//		Don.main(new String[] {/*"-v",*/"-5","AcKd","9c9s","7h6h"});
//		Don.main(new String[] {/*"-v",*/"-5","AcAd","3c3s","7h6h","Qc5s"});
	}
}
