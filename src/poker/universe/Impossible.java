package poker.universe;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
public class Impossible { // http://suffe.cool/poker/7462.html
    public static void main(String[] args) throws NumberFormatException,IOException {
        final File f=new File("seven.txt");
        int lines=0;
        try (BufferedReader reader=new BufferedReader(new FileReader(f))) {
            for(String line=reader.readLine();line!=null;line=reader.readLine(),lines++) {
                String[] parts=line.trim().split("\\s+");
                if(parts.length<12) continue;
                if("0".equals(parts[3])) {
                    String s=parts[5]+parts[6]+parts[7]+parts[8]+parts[9]+" "+parts[10]+" "+parts[11];
                    System.out.println(s);
                }
            }
        }
    }
}
