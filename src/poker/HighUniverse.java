package poker;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import equipment.Rank;
public final class HighUniverse {
	public record Entry(int handNumber,PokerHand.HighType type,Rank[] ranks,String line) {}
	public static List<Entry> read(File file) throws IOException {
		final List<Entry> entries=new ArrayList<Entry>();
		try (BufferedReader reader=new BufferedReader(new FileReader(file))) {
			for(String line=reader.readLine();line!=null;line=reader.readLine()) {
				String[] parts=line.trim().split("\\s+");
				if(parts.length==0||parts[0].length()==0) continue;
				assert parts.length==10; /* 1 AAAAA 0 1 5 14 14 14 14 14 */
				final int handNumber=Integer.parseInt(parts[0]);
				final String cards=parts[1];
				final PokerHand.HighType type=PokerHand.HighType.fromCharacter(parts[4].charAt(0));
				final Rank[] ranks=Rank.fromCharacters(cards);
				entries.add(new Entry(handNumber,type,ranks,line));
			}
		}
		return entries;
	}
	private HighUniverse() {}
}
