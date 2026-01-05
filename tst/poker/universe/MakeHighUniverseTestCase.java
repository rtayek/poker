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
		assertEquals(naturalFiveCardCombinations,MakeHighUniverse.t52);
		assertEquals(allFiveCardCombinationsWithOneJoker,naturalFiveCardCombinations+MakeHighUniverse.tj);
		assertEquals(MakeHighUniverse.N,MakeHighUniverse.n);

	}
	public void testMainSummary() {
		MakeHighUniverse.main(new String[] {"-s"});
		assertEquals(naturalFiveCardCombinations,MakeHighUniverse.t52);
		assertEquals(allFiveCardCombinationsWithOneJoker,naturalFiveCardCombinations+MakeHighUniverse.tj);
		assertEquals(MakeHighUniverse.N,MakeHighUniverse.n);
	}
	public void testMainDetail() {
		MakeHighUniverse.main(new String[] {"-d"});
		assertEquals(naturalFiveCardCombinations,MakeHighUniverse.t52);
		assertEquals(allFiveCardCombinationsWithOneJoker,naturalFiveCardCombinations+MakeHighUniverse.tj);
		assertEquals(MakeHighUniverse.N,MakeHighUniverse.n);
	}
	public void testMainWild() {
		MakeHighUniverse.main(new String[] {"-w"});
		assertEquals(naturalFiveCardCombinations,MakeHighUniverse.t52);
		assertEquals(allFiveCardCombinationsWithOneJoker,naturalFiveCardCombinations+MakeHighUniverse.tj);
		assertEquals(MakeHighUniverse.N,MakeHighUniverse.n);
	}
}
