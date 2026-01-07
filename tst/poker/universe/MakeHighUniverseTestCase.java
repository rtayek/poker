package poker.universe;
import static poker.Constants.*;
import junit.framework.TestCase;
public class MakeHighUniverseTestCase extends TestCase {
	public static void main(String[] args) {
		junit.textui.TestRunner.run(MakeHighUniverseTestCase.class);
	}
	public MakeHighUniverseTestCase(String name) {
		super(name);
	}
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testMainWithNoArguments() {
		MakeHighUniverse.main(new String[0]);
		assertTotals();

	}
	public void testMainSummary() {
		MakeHighUniverse.main(new String[] {"-s"});
		assertTotals();
	}
	public void testMainDetail() {
		MakeHighUniverse.main(new String[] {"-d"});
		assertTotals();
	}
	public void testMainWild() {
		MakeHighUniverse.main(new String[] {"-w"});
		assertTotals();
	}
	private void assertTotals() {
		assertEquals(naturalFiveCardCombinations,MakeHighUniverse.t52);
		assertEquals(allFiveCardCombinationsWithOneJoker,naturalFiveCardCombinations+MakeHighUniverse.tj);
		assertEquals(MakeHighUniverse.N,MakeHighUniverse.n);
	}
}
