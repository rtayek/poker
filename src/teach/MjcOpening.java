package teach;
import java.io.File;
import java.util.List;
import com.tayek.utilities.Utilities;
//http://www.poker1.com/archives/13348
public class MjcOpening {
	static void printSpaces(int n) {
		for(int i=0;i<n;i++)
			System.out.print(" ");
	}
	public static void main(String[] args) {
		File file=new File("mjcopenedited.txt");
		System.out.println("n   pairs       suited        offsuit       %");
		List<String> l=Utilities.getFileAsListOfStrings(file);
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
