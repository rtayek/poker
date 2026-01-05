package school;

import static java.lang.Math.abs;
import junit.framework.TestCase;

public class StrategyTestCase extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testSomeCasesFromeTheCompleatStrategyst() { // pp 32-45
		Strategy strategy=new Strategy(0,0,0,0);
		assertTrue(strategy.hasASaddlePoint);
		strategy=new Strategy(6,5,5,4);
		assertTrue(strategy.hasASaddlePoint);
		strategy=new Strategy(3,6,5,4);
		assertFalse(strategy.hasASaddlePoint);
		strategy=new Strategy(7,3,2,11);
		assertFalse(strategy.hasASaddlePoint);
		strategy=new Strategy(6,5,3,4);
		assertTrue(strategy.hasASaddlePoint);
		strategy=new Strategy(8,1,4,6);
		assertFalse(strategy.hasASaddlePoint);
		strategy=new Strategy(11,4,7,9);
		assertFalse(strategy.hasASaddlePoint);
		strategy=new Strategy(16,2,8,12);
		assertFalse(strategy.hasASaddlePoint);

	}
}
