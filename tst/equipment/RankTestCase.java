package equipment;
import java.util.*;
import junit.framework.TestCase;
public class RankTestCase extends TestCase {
	public void testFromInt() {
		for(Rank rank:Rank.values())
			assertEquals(rank,Rank.fromInt(rank.ordinal()));
	}
	public void testFromCharacters() {
		Rank[] ranks=Rank.fromCharacters("*a23456789TJQKA");
		List<Rank> expected=Arrays.asList(ranks);
		List<Rank> actual=Arrays.asList(Rank.values());
		assertEquals(expected,actual);
	}
	public void testToAndFtomCharacter() {
		for(Rank rank:Rank.values())
			assertEquals(rank,Rank.fromCharacter(rank.toCharacter()));
	}
	public void testToString() {
		System.out.println(("Not yet implemented"));
	}
	public void testToStringRankArray() {
		System.out.println(("Not yet implemented"));
	}
}
