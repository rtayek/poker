package bovada;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import junit.framework.TestCase;
public class ChectTestCase extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void testCount() {
		final String string="dog dog dog doggie dogg";
		int count=Check.count("\\bdog\\b",string);
		assertEquals(3,count);
	}
}
