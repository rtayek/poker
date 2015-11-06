package bovada;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
public class FindDuplicateFiles {
	public static void fromFile(final StringBuffer stringBuffer,final File file) {
		try {
			Reader r=new FileReader(file);
			int c=0;
			while ((c=r.read())!=-1)
				stringBuffer.append((char)c);
			r.close();
		} catch(FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
	static boolean areEqual(File file1,File file2) {
		StringBuffer sb1=new StringBuffer();
		fromFile(sb1,file1);
		StringBuffer sb2=new StringBuffer();
		fromFile(sb1,file2);
		return sb1.toString().equals(sb2.toString());
	}
	void look(File dir) {
		String[] filenames=dir.list();
		for(int i=0;i<filenames.length;i++) {
			File fileI=new File(dir,filenames[i]);
			for(int j=i+1;j<filenames.length;j++) {
				File fileJ=new File(dir,filenames[j]);
				if(fileI.length()==fileJ.length()) {
					sameSize++;
					//System.out.println("files have the same length :");
					//System.out.println(filenames[i]);
					//System.out.println(filenames[j]);
					if(areEqual(fileI,fileJ)) {
						areEqual++;
						//System.out.println("files are equal:");
						//System.out.println(filenames[i]);
						//System.out.println(filenames[j]);
					}
				}
			}
		}
	}
	public static void main(String[] args) {
		FindDuplicateFiles x=new FindDuplicateFiles();
		File dir=Directories.aceCopiesOforiginalDirectory;
		x.look(dir);
		System.out.println(x.sameSize+" files with the same size.");
		System.out.println(x.areEqual+" files with are equal.");
	}
	int sameSize,areEqual;
}
