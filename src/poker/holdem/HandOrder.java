package poker.holdem;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Stream;
// http://holdemtight.com/pgs/od/oddpgs/3-169holdemhands.htm
public class HandOrder {
    enum Name {
	chen(),online(onlineOrder()),helmuth(helmuthOrder()),sklansky(sklanskyOrder()),mjc(mjcOrder()),slkanslyChubukov(sklanskyChubukovOrder()),other(
		someOtherOnlineOrder());
	Name(String[] strings) {
	    handOrder=new HandOrder(strings);
	}
	Name() {
	    handOrder=new HandOrder();
	}
	void print() {
	    double p=0;
	    int n=0;
	    for(Integer i:handOrder.r2hh.keySet()) {
		HoldemHand h=handOrder.r2hh.get(i);
		n+=h.declaredType().frequency();
		p+=h.declaredType().frequency()/(double)HoldemHand.universe;
		String s=h.toString();
		if(s.length()==2) s+=' ';
		System.out.println(s+" "+h.value()+" "+n+" "+(float)p);
	    }
	}
	final HandOrder handOrder;
	public static void printMatrix() {
	    for(int i=1;i<=50;i++) {
		System.out.print(i+" ");
		for(Name name:Name.values()) {
		    if(!name.handOrder.r2hh.containsKey(i)) System.out.print("\tnull");
		    System.out.print("\t"+name.handOrder.r2hh.get(i));
		}
		System.out.println();
	    }
	}
    }
    HandOrder() {
	for(int i=0;i<HoldemHand.values().length;i++) {
	    HoldemHand h=HoldemHand.values()[i];
	    hh2r.put(h,i+1);
	    r2hh.put(i+1,h);
	}
    }
    HandOrder(String[] strings) {
	for(int i=0;i<strings.length;i++) {
	    try {
		HoldemHand h=HoldemHand.fromCharacters(strings[i]);
		hh2r.put(h,i+1);
		r2hh.put(i+1,h);
	    } catch(Exception e) {
		System.out.println("'"+strings[i]+"'"+" caused: "+e);
	    }
	}
    }
    static void print() {
	double p=0;
	int n=0;
	for(HoldemHand holdemHand:HoldemHand.values()) {
	    n+=holdemHand.declaredType().frequency();
	    p+=holdemHand.declaredType().frequency()/(double)HoldemHand.universe;
	    System.out.println(""+holdemHand+" "+holdemHand.name()+" "+holdemHand.value()+" "+n+" "+(float)p);
	}
    }
    static String[] sklanskyOrder() {
	String s="AA KK QQ JJ AKs ";
	s+="TT AQs AJs KQs AK ";
	s+="99 ATs KJs QJs JTs AQ ";
	s+="88 KTs QTs J9s T9s 98s AJ KQ ";
	s+="77 A9s A8s A7s A6s A5s A4s A3s A2s Q9s T8s 97s 87s 76s KJ QJ JT ";
	s+="66 55 K9s J8s 86s 75s 54 AT KT QT";
	return s.split(" ");
    }
    static String[] helmuthOrder() {
	String s="AA KK AKs QQ AK ";
	s+="JJ TT 99 ";
	s+="88 77 AQs AQ ";
	s+="AJs ATs A9s A8s ";
	s+="A7s A6s A5s A4s A3s A2s KQs KQ ";
	s+="QJs JTs T9s 98s 87s 76s 65s";
	return s.split(" ");
    }
    static String[] onlineOrder() {
	String s="AA KK QQ JJ AKs ";
	s+="AQs TT AK AJs KQs 99 ";
	s+="ATs AQ KJs 88 KTs QJs ";
	s+="A9s AJ QTs KQ 77 JTs ";
	s+="A8s K9s AT A5s A7s ";
	s+="KJ 66 T9s A4s Q9s ";
	s+="J9s QJ A6s 55 A3s K8s KT ";
	s+="98s T8s K7s A2s ";
	s+="87s QT Q8s 44 A9 J8s 76s JT";
	return s.split(" ");
    }
    static String[] mjcOrder() {
	String s="AA KK QQ JJ AKs ";
	s+="TT AK AQs KQs AJs ATs ";
	s+="AQ 99 KJs KQ KTs A9s ";
	s+="AJ 88 QJs KJ A8s ";
	s+="AT QTs K9s JTs A5s A4s ";
	s+="QJ A7s K8s KT Q9s A3s ";
	s+="A6s A2s QT K7s 77 ";
	s+="J9s T9s JT ";
	return s.split(" ");
    }
    static String[] someOtherOnlineOrder() {
	Path file=Path.of("somehandranks.csv");
	try (Stream<String> lines=Files.lines(file,Charset.defaultCharset())) {
	    return lines.skip(1)
		    .map(line -> line.split(",")[0])
		    .toArray(String[]::new);
	} catch(IOException e) {
	    throw new RuntimeException(e);
	}
    }
    private static String format(Double x) {
	String s=Float.toString(x.floatValue());
	return (s.length()==4?"":" ")+s;
    }
    private static String format(String s) {
	return s+(s.length()==3?"":" ");
    }
    private static String format(String[] s,int i) {
	return (i<s.length?(format(s[i])):"   ");
    }
    static String[] sklanskyChubukovOrder() {
	Path file=Path.of("sklanskynumbers.txt");
	try (Stream<String> lines=Files.lines(file,Charset.defaultCharset())) {
	    return lines
		    .map(line -> line.split("\t")[0])
		    .map(HoldemHand::fromCharacters)
		    .map(HoldemHand::toString)
		    .toArray(String[]::new);
	} catch(IOException e) {
	    throw new RuntimeException(e);
	}
    }
    void foo() {
	int n=0;
	for(Integer i:r2hh.keySet()) {
	    HoldemHand h=r2hh.get(i);
	    if(h!=null) {
		n+=h.declaredType().frequency();
		System.out.println(h+" "+h.declaredType().frequency()+" "+(float)(n/(double)HoldemHand.universe));
	    }
	}
    }
    static void foo(String[] hands) {
	int n=0;
	for(String s:hands) {
	    HoldemHand h=HoldemHand.fromCharacters(s);
	    n+=h.declaredType().frequency();
	    System.out.println(h+" "+h.declaredType().frequency()+" "+(float)(n/(double)HoldemHand.universe));
	}
    }
    static void check(String name,String[] strings) {
	for(String string:strings)
	    try {
		HoldemHand h=HoldemHand.fromCharacters(string);
		// System.out.println("h="+h);
	    } catch(Exception e) {
		System.out.println(name+": '"+string+"'"+" caused: "+e);
	    }
    }
    static void check(String name,Map<String,Integer> map) {
	for(String string:map.keySet())
	    try {
		HoldemHand h=HoldemHand.fromCharacters(string);
		// System.out.println("h="+h);
	    } catch(Exception e) {
		System.out.println(name+": '"+string+"'"+" caused: "+e);
	    }
    }
    static void makeCsv() {
	for(int i=0;i<all.length;i++) {
	    System.out.println("check: "+i);
	    check(""+i,all[i]);
	}
	ArrayList<LinkedHashMap<String,Integer>> maps=new ArrayList<>(all.length);
	for(int i=0;i<all.length;i++)
	    maps.add(new LinkedHashMap<>());
	for(int i=0;i<all.length;i++) {
	    LinkedHashMap<String,Integer> map=maps.get(i);
	    for(int j=0;j<all[i].length;j++)
		map.put(all[i][j],(j+1));
	}
	for(int i=0;i<maps.size();i++) {
	    System.out.println("check: "+i);
	    check(""+i,maps.get(i));
	}
	int min=Integer.MAX_VALUE;
	for(Map<String,Integer> map:maps)
	    min=Math.min(min,map.size());
	System.out.println("smallest is: "+min);
	for(Map<String,Integer> map:maps)
	    System.out.println(map);
	System.out.println("hand\tchen*\tonline\tother\thelmuth\tsklansky\tmjc\tsklansky2");
	int first=0;
	LinkedHashMap<String,Integer> firstMap=maps.get(first);
	Iterator<String> x=firstMap.keySet().iterator();
	for(int i=0;i<min;i++) {
	    String key=x.next();
	    System.out.print(key);
	    for(int j=0;j<maps.size();j++)
		if(j!=first) {
		    LinkedHashMap<String,Integer> map=maps.get(j);
		    if(!map.containsKey(key)) System.out.println("no value for: "+key+" in map "+j);
		    System.out.print("\t"+map.get(key));
		}
	    System.out.println();
	}
    }
    static void oldPrint() {
	System.out.println("hand\tchen*\tonline\tother\thelmuth\tsklansky\tmjc\tsklansky2");
	for(int i=0;i<HoldemHand.values().length;i++) {
	    System.out.print(""+format(HoldemHand.values()[i].toString()));
	    System.out.print("\t"+format(HoldemHand.values()[i].value()));
	    System.out.print("\t"+format(online,i));
	    System.out.print("\t"+format(someOtherOnlineOrder,i));
	    System.out.print("\t"+format(helmuth,i));
	    System.out.print("\t"+format(sklansky,i));
	    System.out.print("\t"+format(mjc,i));
	    System.out.print("\t"+format(slkanslyChubukov,i));
	    System.out.println();
	}
    }
    public static void main(String[] args) {
	for(Name name:Name.values()) {
	    System.out.println(name.name()+" has "+name.handOrder.r2hh.size()+" entries.");
	    name.print();
	}
	Name.printMatrix();
	// printFrequencies();
	// print();
	// oldPrint();
	// print();
	// foo(slkanslyChubukov);
	// makeCsv();
    }
    final Map<HoldemHand,Integer> hh2r=new LinkedHashMap<>(HoldemHand.values().length);
    final Map<Integer,HoldemHand> r2hh=new LinkedHashMap<>(HoldemHand.values().length);
    static final String[] online=onlineOrder();
    static final String[] helmuth=helmuthOrder();
    static final String[] sklansky=sklanskyOrder();
    static final String[] mjc=mjcOrder();
    static final String[] slkanslyChubukov=sklanskyChubukovOrder();
    static final String[] someOtherOnlineOrder=someOtherOnlineOrder();
    static final String[][] all=new String[][] {online,helmuth,sklansky,mjc,slkanslyChubukov,someOtherOnlineOrder};
}
