package bovada;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import static com.tayek.utilities.Utilities.*;
public class OldCheckConvertedFiles {
	private void endOfHand(ArrayList<String> strings,int startIndex,int i,Long handNumber,Map<Long,List<String>> hands) {
		int endIndex;
		if(startIndex!=-1) {
			endIndex=i;
			List<String> hand=new ArrayList<>();
			for(int j=startIndex;j<endIndex;j++)
				hand.add(strings.get(j));
			if(hands.containsKey(handNumber)) {
				List<String> in=hands.get(handNumber);
				if(!hand.equals(in)) {
					System.out.println(handNumber+" is a duplicate!");
					System.out.println("hand numbers are not equal!");
					System.out.println("in"+in);
					System.out.println("new"+hand);
				}
			} else {
				List<String> old=hands.put(handNumber,hand);
			}
		}
	}
	// return a list of dupes?
	List<Long> processFile(File dir,String name,String target,char delimiter,Set<Long> handNumbers,Map<Long,List<String>> hands) {
		ArrayList<Long> duplicates=new ArrayList<>();
		File file=new File(dir,name);
		// System.out.println(file);
		ArrayList<String> strings=new ArrayList<String>();
		try {
			final BufferedReader r=new BufferedReader(new FileReader(file));
			String line=null;
			try {
				for(line=r.readLine();(line=r.readLine())!=null;)
					strings.add(line);
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		} catch(FileNotFoundException e) {
			throw new RuntimeException(e);
		}
		int startIndex=-1,endIndex=-1;
		Long handNumber=-1l,oldHandNumber=-1l;
		for(int i=0;i<strings.size();i++)
			if(strings.get(i).contains(target)) {
				String part=strings.get(i).substring(target.length());
				int index=part.indexOf(delimiter);
				if(index<0) throw new RuntimeException("oops");
				part=part.substring(0,index);
				handNumber=new Long(part);
				if(handNumber<=0) throw new RuntimeException("oops");
				endOfHand(strings,startIndex,i,oldHandNumber,hands);
				startIndex=i;
				oldHandNumber=handNumber;
				if(!handNumbers.add(handNumber)) duplicates.add(handNumber);
			}
		endOfHand(strings,startIndex,strings.size(),oldHandNumber,hands);
		duplicatesInADirectory+=duplicates.size();
		return duplicates;
	}
	private List<Long> processFiles(File dir,String target,char delimiter,Set<Long> set,Map<Long,List<String>> map) {
		ArrayList<Long> duplicates=new ArrayList<>();
		String[] names=dir.list();
		System.out.println(dir+" has "+names.length+" files.");
		for(String name:names) {
			duplicates.addAll(processFile(dir,name,target,delimiter,set,map));
			if(!this.names.add(name)) {
				System.out.println("duplicate file name "+name+" in "+dir);
				// check and see if they are identical
			}
		}
		System.out.println(duplicates.size()+" duplicate hand numbers in "+dir);
		return duplicates;
	}
	private void compareHandNumbers(Set<Long> originals,Set<Long> converted,Set<Long> misingFromOriginals,Set<Long> missingFromConverted) {
		if(!converted.equals(originals)) System.out.println("sets are not equal!");
		missingFromOriginal.clear();
		missingFromConverted.clear();
		ArrayList<Long> list=new ArrayList<>(originals);
		if(list.size()>0) System.out.println("originals are in the range: "+list.get(0)+"-"+list.get(list.size()-1));
		list=new ArrayList<>(converted);
		if(list.size()>0) System.out.println("converted are in the range: "+list.get(0)+"-"+list.get(list.size()-1));
		for(Long l:converted)
			if(!originals.contains(l)) {
				missingFromOriginal.add(l);
				// System.out.println("originals does not contain converted "+l);
			}
		for(Long l:originals)
			if(!converted.contains(l)) {
				missingFromConverted.add(l);
				// System.out.println("converted does not contain original "+l);
			}
	}
	private void processMainDirectoies() {
		List<Long> duplicates=processFiles(originalDirectory,originalTarget,originalDelimiter,originalHandNumbers,originalHands);
		check(false);
		List<Long> duplicates2=processFiles(convertedDirectory,convertedTarget,convertedDelimiter,convertedHandNumbers,convertedHands);
		check(true); // somehow the previous line is putting converted hands into
					// originalHands!
		System.out.println(originalHandNumbers.size()+" original hands");
		System.out.println(convertedHandNumbers.size()+" converted hands");
		// do this more than once!
	}
	private List<Long> processSomeOtherOriginals() { // just some originals i
														// copied at
		// one time
		List<Long> duplicates=processFiles(otherOriginalDirectory,originalTarget,' ',otherOriginalHandNumbers,originalHands);
		System.out.println(otherOriginalHandNumbers.size()+" other original hands");
		for(Long l:otherOriginalHandNumbers)
			if(originalHandNumbers.contains(l)) {
				// System.out.println(l+" is also conatined in orginal hand numberss");
				alsoInOriginal.add(l);
			}
		System.out.println(alsoInOriginal.size()+" other original hands are in main original hands: \n"+alsoInOriginal);
		return duplicates;
	}
	private List<Long> processSomeOtheConverted() { // just some converted that
													// were
		// laying around
		List<Long> duplicates=processFiles(otherConvertedDirectory,convertedTarget,':',otherConvertedHandNumbers,convertedHands);
		System.out.println(otherConvertedHandNumbers.size()+" other converted hands");
		for(Long l:otherConvertedHandNumbers)
			if(convertedHandNumbers.contains(l)) {
				// System.out.println(l+" is also contained in converted hand numberss");
				alsoInConverted.add(l);
			}
		System.out.println(alsoInConverted.size()+" other converted hands are in main converted hands: \n"+alsoInConverted);
		return duplicates;
	}
	private List<Long> processFilesStuckInBovadaHandHistory() { // stuck there
																// for
		List<Long> duplicates=processFiles(originalBovadaDirectory,originalTarget,originalDelimiter,originalBovadaHandNumbers,originalHands);
		System.out.println(otherOriginalHandNumbers.size()+" other original hands");
		for(Long l:originalBovadaHandNumbers)
			if(originalHandNumbers.contains(l)) {
				System.out.println(l+" is also contained in orginal hands");
				alsoInOriginal.add(l);
			}
		System.out.println(alsoInOriginal.size()+" other original hands are in main original hands: \n"+alsoInOriginal);
		return duplicates;
	}
	private void check(boolean exit) {
		List<Long> list=new ArrayList<>(originalHands.keySet());
		if(true) {
			for(int i=0;i<Math.min(list.size(),10);i++)
				System.out.println(originalHands.get(list.get(i)));
			if(exit) System.exit(0);
		}
	}
	void run() {
		processMainDirectoies();
		compareHandNumbers(originalHandNumbers,convertedHandNumbers,missingFromOriginal,missingFromconverted);
		System.out.println(missingFromOriginal.size()+" hands missing from original "+missingFromOriginal);
		System.out.println(missingFromconverted.size()+" hands missing from converted "+missingFromconverted);
		System.out.println("------------");
		processSomeOtherOriginals();
		compareHandNumbers(originalHandNumbers,convertedHandNumbers,missingFromOriginal,missingFromconverted);
		System.out.println(missingFromOriginal.size()+" hands missing from original "+missingFromOriginal);
		System.out.println(missingFromconverted.size()+" hands missing from converted "+missingFromconverted);
		System.out.println("------------");
		// processSomeOtheConverted();
		// System.out.println("------------");
		// processFilesStuckInBovadaHandHistory();
	}
	public static void main(String[] args) {
		new OldCheckConvertedFiles().run();
	}
	final String originalTarget="Bovada Hand #";
	final String convertedTarget="PokerStars Zoom Hand #";
	final char originalDelimiter=' ';
	final char convertedDelimiter=':';
	final File originalBovadaDirectory=new File("C:/Bovada/Hand History/10626201");
	final File userDirectory=new File("C:/Users/ray");
	final File originalDirectory=new File(userDirectory,"Documents/Bovada Original Hands");
	final File convertedDirectory=new File(userDirectory,"Documents/BovadaHandHistory");
	final File otherOriginalDirectory=new File(userDirectory,"Documents/bovadaOriginal");
	final File otherConvertedDirectory=new File(userDirectory,"Documents/copybhh");
	final Set<String> names=new LinkedHashSet<>();
	final SortedSet<Long> originalBovadaHandNumbers=new TreeSet<>();
	final SortedSet<Long> originalHandNumbers=new TreeSet<>();
	final SortedSet<Long> convertedHandNumbers=new TreeSet<>();
	final SortedSet<Long> missingFromconverted=new TreeSet<>();
	final SortedSet<Long> missingFromOriginal=new TreeSet<>();
	final SortedSet<Long> otherOriginalHandNumbers=new TreeSet<>();
	final SortedSet<Long> otherConvertedHandNumbers=new TreeSet<>();
	final SortedSet<Long> alsoInOriginal=new TreeSet<>();
	final SortedSet<Long> alsoInConverted=new TreeSet<>();
	final SortedMap<Long,List<String>> originalHands=new TreeMap<>();
	final SortedMap<Long,List<String>> convertedHands=new TreeMap<>();
	transient int duplicatesInADirectory,duplicateOriginalHands;
	//
}
