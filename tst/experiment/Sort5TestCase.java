package experiment;
import java.util.Arrays;
import junit.framework.TestCase;
public class Sort5TestCase extends TestCase {
	protected void setUp() throws Exception {
		super.setUp();
	}
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	public void test() {
		int n=5;
		for(int i=1;i<=n;i++)
			for(int j=1;j<=n;j++)
				for(int k=1;k<=n;k++)
					for(int l=1;l<=n;l++)
						for(int m=1;m<=n;m++) {
							I[] is=Sort5.sort(new I(i),new I(j),new I(k),new I(l),new I(m));
							for(int o=0;o<is.length-1;o++) {
								if(!(is[o].i<=is[o+1].i)) {
									System.out.println(i+" "+j+" "+k+" "+l+" "+m);
									System.out.println(Arrays.asList(is));
								}
							//assertTrue(is[o].i<=is[o+1].i);
							}
						}
	}
}
