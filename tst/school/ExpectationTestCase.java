package school;
import static org.junit.Assert.*;
import static java.lang.Math.abs;
import junit.framework.TestCase;
public class ExpectationTestCase extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testCheck() {
		assertTrue(E.check(0.));
		assertTrue(E.check(.5));
		assertTrue(E.check(1.));
	}
	public void testOddsForProbablilty0() {
		assertEquals(Double.POSITIVE_INFINITY,E.odds(.0),E.epsilon);
	}
	public void testOddsForSomeTypicalCalues() {
		for(int i=1;i<=10;i++)
			assertEquals(i-1.,E.odds(1./i),E.epsilon);
	}
	public void testOddsForProbablilty1() {
		assertEquals(0.,E.odds(1.),E.epsilon);
	}
	public void testExpectation() {
		for(double bet=1;bet<=10;bet+=1) {
			for(double pot=0;pot<=10;pot+=1) {
				E e=new E(0,pot,bet);
				for(double pBluff=0;pBluff<=1;pBluff+=.1)
					for(double pCall=0;pCall<=1;pCall+=.1) {
						double e1=e.e1(pBluff,pCall);
						double e2=e.e2(pBluff,pCall);
						assertEquals(e.pot,e1+e2,E.epsilon);
					}
			}
		}
	}
	public void testSomeCasesFromeTheCompleatStrategyst() {
		Super super0=school.E.strategy(0,0,0,0);
		assertTrue(abs(super0.maxMin-super0.minMax)<E.epsilon);
		System.out.println("---------");
		Super super1=school.E.strategy(6,5,5,4);
		assertTrue(abs(super1.maxMin-super1.minMax)<E.epsilon);
		System.out.println("---------");
		school.E.strategy(3,6,5,4);
		System.out.println("---------");
		school.E.strategy(7,3,2,11);
		System.out.println("---------");
		school.E.strategy(6,5,3,4);
		System.out.println("---------");
		school.E.strategy(8,1,4,6);
		System.out.println("---------");
		school.E.strategy(11,4,7,9);
		System.out.println("---------");
		school.E.strategy(16,2,8,12);
		System.out.println("---------");

	}
}
