package bovada;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import com.tayek.utilities.Utilities;
class Directories {
	static final File originalBovadaDirectory=new File("C:/Bovada/Hand History/10626201");
	static final File userDirectory=new File("C:/Users/ray");
	static final File aceCopiesOforiginalDirectory=new File(userDirectory,"Documents/Bovada Original Hands");
	static final File aceConvertedDirectory=new File(userDirectory,"Documents/BovadaHandHistory");
	static final File otherOriginalDirectory=new File(userDirectory,"Documents/bovadaOriginal");
	static final File otherConvertedDirectory=new File(userDirectory,"Documents/copybhh");
}
class Hand {
	Hand(long number,File file,List<String> lines) {
		this.number=number;
		this.file=file;
		this.lines=lines;
	}
	final long number;
	final File file;
	final List<String> lines;
}
class Different {
	/*
	  	String s=cat(lines);
		String t=cat(in);
		if(!s.equals(t)) {
			record(handNumber,in,lines);
			System.out.println(handNumber+" is a duplicate hand number!");
			System.out.println(" and hands are not equal!");
		}
	*/
	Different(Hand hand1x,Hand hand2x) {
		this.hand1=hand1x;
		this.hand2=hand2x;
		List<String> lines1=hand1x.lines;
		List<String> lines2=hand2x.lines;
		String s1=Check.cat(hand1x.lines);
		String s2=Check.cat(hand2x.lines);
		String regex="\\bmucked\\b";
		int count1=Check.count(regex,s1);
		int count2=Check.count(regex,s2);
		sameMucks=count1==count2;
		List<String> oldSorted=new ArrayList<String>(lines1);
		Collections.sort(oldSorted);
		List<String> newSorted=new ArrayList<String>(lines2);
		Collections.sort(newSorted);
		s1=Check.cat(oldSorted);
		s2=Check.cat(newSorted);
		sameOrder=s1.equals(s2);
	}
	final boolean sameMucks,sameOrder;
	final Hand hand1,hand2;
}
public class Check {
	Check(File original,File converted) {
		this.originalDirectory=original;
		this.convertedDirectory=converted;
	}
	public static String cat(final List<String> data) {
		final StringBuffer sb=new StringBuffer();
		for(String string:data)
			sb.append(string).append('\n');
		return sb.toString();
	}
	static int count(final String regex,final String string) {
		Pattern p=Pattern.compile(regex);
		Matcher m=p.matcher(string);
		int count=0;
		while (m.find())
			count++;
		return count;
	}
	static void record(Long handNumber,List<String> handInMap,List<String> hand) {
		String s1=cat(handInMap);
		String s2=cat(hand);
		String regex="\\bmucked\\b";
		int count1=count(regex,s1);
		int count2=count(regex,s2);
		if(count1!=count2) System.out.println("mucks are the different! ****************************");
		List<String> oldSorted=new ArrayList<String>(handInMap);
		Collections.sort(oldSorted);
		List<String> newSorted=new ArrayList<String>(hand);
		Collections.sort(newSorted);
		s1=cat(oldSorted);
		s2=cat(newSorted);
		if(s1.equals(s2)) System.out.println("but lines are the same in a different order");
		File dir=new File("data");
		File file1=new File(dir,handNumber+".1.txt");
		File file2=new File(dir,handNumber+".2.txt");
		Utilities.toFile(cat(handInMap),file1);
		Utilities.toFile(cat(hand),file2);
	}
	static List<String> endOfHand(List<String> strings,int startIndex,int i,Long handNumber,Map<Long,Hand> hands) {
		List<String> lines=new ArrayList<>();
		int endIndex;
		if(startIndex!=-1) {
			endIndex=i;
			for(int j=startIndex;j<endIndex;j++)
				lines.add(strings.get(j));
		}
		return lines;
	}
	private Long handNumber(String target,char delimiter,List<String> strings,int i) {
		Long handNumber;
		String part=strings.get(i).substring(target.length());
		int index=part.indexOf(delimiter);
		if(index<0) throw new RuntimeException("oops");
		part=part.substring(0,index);
		handNumber=new Long(part);
		if(handNumber<=0) throw new RuntimeException("oops");
		return handNumber;
	}
	private void processHand(File file,Map<Long,Hand> hands,ArrayList<Long> duplicates,Hand hand) {
		if(hand.lines==null||hand.lines.size()==0||hand.number<0) return;
		if(hands.containsKey(hand.number)) {
			Hand old=hands.get(hand.number);
			if(!hand.lines.equals(old.lines)) {
				Different different=new Different(old,hand);
				// save these and report on them
				duplicates.add(hand.number);
			}
		} else {
			Hand old=hands.put(hand.number,hand);
			// System.out.println(lines.size());
			if(hand.lines.get(0).startsWith("PokerStars")) {
				// System.out.println(hand.get(0));
				// throw new RuntimeException("oops");
			}
		}
	}
	List<Long> processFile(File file,String target,char delimiter,Map<Long,Hand> hands) {
		ArrayList<Long> duplicates=new ArrayList<>();
		List<String> strings=Utilities.getFileAsListOfStrings(file);
		int startIndex=-1;
		Long handNumber=-1l,oldHandNumber=-1l;
		for(int i=0;i<strings.size();i++)
			if(strings.get(i).contains(target)) { // start of hand data
				handNumber=handNumber(target,delimiter,strings,i);
				List<String> lines=endOfHand(strings,startIndex,i,oldHandNumber,hands);
				Hand hand=new Hand(oldHandNumber,file,lines);
				if(lines.size()>0) {
					// System.out.println("process hand "+handNumber);
					processHand(file,hands,duplicates,hand);
				} else
				;// System.out.println("no lines at i="+i);
				startIndex=i;
				oldHandNumber=handNumber;
			}
		List<String> lines=endOfHand(strings,startIndex,strings.size(),oldHandNumber,hands);
		Hand hand=new Hand(oldHandNumber,file,lines);
		processHand(file,hands,duplicates,hand);
		return duplicates; // empty for now
	}
	List<Long> processFiles(File dir,String target,char delimiter,Map<Long,Hand> map,Set<String> filenames) {
		ArrayList<Long> duplicates=new ArrayList<>();
		String[] names=dir.list();
		System.out.println(dir+" has "+names.length+" files.");
		for(String name:names) {
			File file=new File(dir,name);
			// System.out.println("processing "+file);
			List<Long> newDuplicates=processFile(file,target,delimiter,map);
			if(newDuplicates.size()>0) {
				// System.out.println(newDuplicates.size()+" duplicate hands in with different data in file: "+file);
			}
			duplicates.addAll(newDuplicates);
			if(!filenames.add(name)) {
				System.out.println("duplicate file name "+name+" in "+dir);
				System.out.println("check and see if the files are identical!");
			}
		}
		return duplicates;
	}
	void print() {
		System.out.println(originalHands.size()+" original hands");
		System.out.println(convertedHands.size()+" converted hands");
		System.out.println(originalDuplicates.size()+" original duplicates");
		System.out.println(convertedDuplicates.size()+" converted duplicates");
	}
	void run() {
		List<Long> duplicates=processFiles(originalDirectory,originalTarget,originalDelimiter,originalHands,originalFilenames);
		Set<Long> set=new TreeSet<>(duplicates);
		System.out.println(duplicates.size()+" dups for "+set.size()+" different hand numbers from original files.");
		originalDuplicates.addAll(duplicates);
		List<Long> duplicates2=processFiles(convertedDirectory,convertedTarget,convertedDelimiter,convertedHands,convertedFilenames);
		set=new TreeSet<>(duplicates2);
		System.out.println(duplicates2.size()+" dups for "+set.size()+" different hand numbers from converted files.");
		convertedDuplicates.addAll(duplicates2);
		print();
	}
	public static void main(String[] args) {
		File original=Directories.aceCopiesOforiginalDirectory;
		File converted=Directories.aceConvertedDirectory;
		Check check=new Check(original,converted);
		check.run();
	}
	final File originalDirectory,convertedDirectory;
	final SortedSet<String> originalFilenames=new TreeSet<>();
	final SortedSet<String> convertedFilenames=new TreeSet<>();
	final SortedSet<Long> originalDuplicates=new TreeSet<>();
	final SortedSet<Long> convertedDuplicates=new TreeSet<>();
	final SortedMap<Long,Hand> originalHands=new TreeMap<>();
	final SortedMap<Long,Hand> convertedHands=new TreeMap<>();
	static final String originalTarget="Bovada Hand #";
	static final String convertedTarget="PokerStars Zoom Hand #";
	static final char originalDelimiter=' ';
	static final char convertedDelimiter=':';
}
