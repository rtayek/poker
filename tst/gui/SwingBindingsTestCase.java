package gui;
import junit.framework.TestCase;
public class SwingBindingsTestCase extends TestCase {
	public void testNameBack() {
		assertEquals("BACK1",SwingBindings.name("e"));
	}
	public void testNameCardAC() {
		assertEquals("CARDAC",SwingBindings.name("Ac"));
	}
	public void testNameCard2c() {
		assertEquals("CARD2C",SwingBindings.name("2c"));
	}
	public void testNameRejectsInvalidRank() {
		try {
			SwingBindings.name("as");
			fail("expected invalid rank");
		} catch(RuntimeException e) {
			// expected
		}
	}
	public void testNameRejectsInvalidSuit() {
		try {
			new SwingBindings().name("Ax");
			fail("expected invalid suit");
		} catch(RuntimeException e) {
			// expected
		}
	}
}
