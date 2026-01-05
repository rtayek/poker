package school;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import static com.tayek.utilities.Utilities.*;
public class Csv {
	static String strip(String string) {
		if(string.startsWith("\"")) string=string.substring(1);
		if(string.endsWith("\"")) string=string.substring(0,string.length()-1);
		return string;
	}
	public static void main(String[] args) throws Exception {
		// System.out.println(System.getProperty("user.dir"));
		File file=new File("ReportExport.csv");
		List<String> strings=getFileAsListOfStrings(file);
		int i=0;
		int[] indices=new int[] {0,2,4,7,8,9};
		int n=strings.get(0).split(",").length;
		String[][] cells=new String[strings.size()][n];
		for(String string:strings) {
			String[] rowCells=strings.get(i).split(",");
			for(int j=0;j<n;j++)
				cells[i][j]=strip(rowCells[j]);
			i++;
		}
		i=0;
		for(String string:strings) {
			if(i==0) {
				for(int j=0;j<indices.length;j++)
					System.out.print(cells[i][indices[j]]+" ");
				System.out.println();
			} else {
				for(int j=0;j<indices.length;j++) {
					if(j==0) {
						String[] stake=cells[i][indices[j]].split(" ");
						String stake2=stake[0].substring(1);
						Double amount=Double.valueOf(stake2);
						System.out.printf("$%5.2f ",amount);
					} else System.out.print(cells[i][indices[j]]+" ");
				}
				System.out.println();
			}
			i++;
		}
	}
}
