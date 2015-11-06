package teach;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
//http://www.poker1.com/archives/13348
public class MjcOpening {
	static List<String> toStrings(final BufferedReader r) {
		final List<String> l=new LinkedList<String>();
		String line=null;
		try {
			for(line=r.readLine();line!=null;line=r.readLine())
				l.add(line);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
		return l;
	}
	static List<String> toStrings(final File file) {
		BufferedReader r=null;
		try {
			r=new BufferedReader(new FileReader(file));
			final List<String> l=toStrings(r);
			r.close();
			return l;
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	static void printSpaces(int n) {
		for(int i=0;i<n;i++)
			System.out.print(" ");
	}
	public static void main(String[] args) {
		File file=new File("mjcopenedited.txt");
		System.out.println("n   pairs       suited        offsuit       %");
		List<String> l=toStrings(file);
		for(String s:l) {
			StringBuffer sb=new StringBuffer();
			String[] parts=s.split("\t");
			//System.out.println(parts.length+" parts.");
			if(!parts[0].equals(""))
				System.out.println();
			for(int i=0;i<parts.length;i++) {
				while(sb.length()<tabstops[i])
					sb.append(' ');
				sb.append(parts[i]);
			}
			System.out.println(sb.toString());
		}
	}
	static int[] tabstops={0,4,16,30,43};
}
