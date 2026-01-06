package poker.universe;
import java.io.*;
import java.util.*;
import equipment.*;
import lookup.OldLookup;
import poker.*;
public class Impossible { // http://suffe.cool/poker/7462.html
    public static void main(String[] args) throws NumberFormatException,IOException {
        final File f=new File("seven.txt");
        final BufferedReader reader=new BufferedReader(new FileReader(f));
        int lines=0;
        for(String line=reader.readLine();line!=null;line=reader.readLine(),lines++) {
            final List<String> l=new LinkedList<String>();
            for(StringTokenizer st=new StringTokenizer(line," ");st.hasMoreTokens();)
                l.add(st.nextToken());
            String column4=l.get(3);
            if(column4.equals("0")) {
                String s=l.get(5)+l.get(6)+l.get(7)+l.get(8)+l.get(9)+" "+l.get(10)+" "+l.get(11);
                System.out.println(s);
            }
        }
    }
}
