package school;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import junit.framework.TestCase;
public class MyIteratorTestCase extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testMyIteratorTTComparatorOfTUnaryOperatorOfT() {
		new MyIterator<Double>(1.2,2.8,(x,y) -> x.compareTo(y),x -> x+.4);
	}
	public void testRewind() {
		Byte expected=bytes.next();
		bytes.rewind();
		assertEquals(expected,bytes.next());
	}
	public void testMyIteratorMyIteratorOfT() {
		MyIterator<Integer> integers2=new MyIterator<Integer>(integers);
		assertEquals(integers.next(),integers2.next());
	}
	public void testSize() {
		assertEquals(3,bytes.size());
	}
	public void testHasNext() {
		assertTrue(integers.hasNext());
		integers.next();
		assertFalse(integers.hasNext());
	}
	public void testNext() {
		assertEquals(new Byte((byte)125),bytes.next());
	}
	public void testAddExact() {
		assertEquals(new Byte((byte)127),(Byte)MyIterator.addExact((byte)125,(byte)2));
		try {
			MyIterator.addExact((byte)125,(byte)3);
			fail("should throw");
		} catch(ArithmeticException e) {}
	}
	public void testOutOf() {
		Iterable<Number> list=MyIterator.outOf(Arrays.asList(new Integer[] {1,2,3}));
	}
	public void testIteratorOutOf() {
		Iterator<Number> list=MyIterator.iteratorOutOf(Arrays.asList(new Integer[] {1,2,3}).iterator());
	}
	public void testPrint() {
		//fail("Not yet implemented");
	}
	public void testMain() {
		MyIterator.main(new String[]{});
	}
	MyIterator<Integer> integers=new MyIterator<Integer>(1,1,(x,y) -> x.compareTo(y),x -> x+1);
	MyIterator<Byte> bytes=new MyIterator<Byte>((byte)125,(byte)127,(x,y) -> x.compareTo(y),x -> MyIterator.addExact(x,(byte)1));
	MyIterator<BigInteger> bigs=new MyIterator<BigInteger>(BigInteger.valueOf(Integer.MAX_VALUE),BigInteger.valueOf(Integer.MAX_VALUE+3l),
			(x,y) -> x.compareTo(y),x -> x.add(BigInteger.ONE));
}
