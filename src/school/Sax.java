package school;
import java.io.*;
import java.util.*;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
//import com.sun.corba.se.spi.activation._LocatorImplBase;
//import com.sun.xml.internal.bind.v2.runtime.output.StAXExStreamWriterOutput;
import com.tayek.utilities.Pair;
import poker.holdem.HoldemHand;
public class Sax extends DefaultHandler {
    /*
    static class MyPair<R extends Comparable<R>,S>extends Pair<R,S> implements Comparable<Myair> {
        public MyPair(R first,S second) {
            super(first,second);
        }
        @Override public int compareTo(R o) {
            return first.compareTo(o.first);
        }
    }
    */
    static class MyPair extends Pair<HoldemHand,Double> implements Comparable<MyPair> {
        public MyPair(HoldemHand first,Double second) {
            super(first,second);
        }
        @Override public int compareTo(MyPair o) {
            return -(int)Math.signum(second.compareTo(o.second));
        }
    }
    @Override public void startDocument() throws SAXException {}
    @Override public void endDocument() throws SAXException {
        System.out.println("in endDocument(), row: "+row);
    }
    @Override public void startElement(String namespaceURI,String localName,String qName,Attributes atts) throws SAXException {
        String key=localName;
        System.out.println("<"+key+">");
        if(localName.equals("td")) {
            inTd=true;
        }
    }
    @Override public void endElement(String uri,String localName,String qName) throws SAXException {
        System.out.println("</"+localName+">");
        if(localName.equals("tr")) {
            row++;
            column=0;
            if(row>2) try {
                writer.write("\n");
            } catch(IOException e) {
                System.out.println("oops");
            }
        } else if(localName.equals("td")) {
            column++;
            inTd=false;
        }
    }
    @Override public void characters(char ch[],int start,int length) throws SAXException {
        String string="";
        for(int i=start;i<start+length;i++)
            string+=ch[i];
        String trimmed=string.trim();
        if(!trimmed.equals("")) if(inTd) {
            if(row>1) {
                if(column==0) {
                    hand[row-2]=trimmed;
                    try {
                        if(trimmed.length()>2&&trimmed.charAt(2)==' ')
                            trimmed=trimmed.substring(0,2)+'s';
                        writer.write(trimmed);
                    } catch(IOException e) {
                        System.out.println("oops");
                    }
                } else {
                    data[row-2][column-1]=Double.valueOf(trimmed);
                    try {
                        writer.write(" "+data[row-2][column-1]);
                    } catch(IOException e) {
                        System.out.println("oops");
                    }
                }
            }
        } else {
            System.out.println("empty or not in td: "+string);
        }
    }
    void print(int n) {
        for(int i=0;i<n;i++) {
            System.out.print(hand[i]+": ");
            for(int j=0;j<columns;j++)
                System.out.print(data[i][j]+" ");
            System.out.print("\u03BC="+mean[i]);
            System.out.println();
        }
    }
    void getHands() {
        int n=0;
        for(int i=0;i<rows;i++) {
            if(hand[i].contains(" s")) {
                hand[i]=hand[i].substring(0,2)+"s";
            }
            HoldemHand holdemHand=HoldemHand.fromCharacters(hand[i]);
            System.out.println(holdemHand+" "+hand[i]+": "+mean[i]+" "+holdemHand.type.frequency());
            cumulativeFrequency[i]=holdemHand.type.frequency();
            if(i>0) cumulativeFrequency[i]+=cumulativeFrequency[i-1];
            n+=holdemHand.type.frequency();
            map.put(holdemHand,mean[i]);
            if(!pairs.add(new MyPair(holdemHand,mean[i]))) System.out.println("oops: "+new MyPair(holdemHand,mean[i]));
        }
        Collections.sort(pairs,new Comparator<MyPair>() {
            @Override public int compare(MyPair o1,MyPair o2) {
                return -(int)Math.signum(o1.second.compareTo(o2.second));
            }
        });
    }
    void expectation(int rows_) {
        average=0.;
        int n=0;
        for(int i=0;i<rows_;i++) {
            HoldemHand holdemHand=pairs.get(i).first;
            average+=mean[i]*holdemHand.type.frequency();
            n+=holdemHand.type.frequency();
        }
        average/=n;
        System.out.println(rows_+"/"+rows+", average expectation: "+average);
    }
    void run() {
        System.out.println("after parse, row: "+row);
        print(rows);
        for(int i=0;i<rows;i++) {
            System.out.print(hand[i]+": ");
            double mean=0;
            for(int j=0;j<columns;j++)
                mean+=data[i][j];
            mean/=columns;
            System.out.println("mean["+i+"]="+mean);
            this.mean[i]=mean;
        }
        print(rows);
        getHands();
        expectation(rows);
        System.out.println("average expectation: "+average+" -ante: "+ante+", "+(average-ante));
        // use top 10%, 15%, 20%
        // let's just make a map for now.
        System.out.println(map.size()+" map: "+map);
        System.out.println(pairs.size()+" pairs: "+pairs);
        int i=0;
        for(MyPair pair:pairs) {
            System.out.println(i+" "+pair+" "+cumulativeFrequency[i]+" "+cumulativeFrequency[i]/(double)total);
            i++;
        }
        for(int j=1;j<=10;j++) {
            expectation(rows/j);
            System.out.println("average expectation: "+average+" -ante: "+ante+", "+(average-ante));
        }
    }
    public static void main(String[] args) throws ParserConfigurationException,SAXException,IOException {
        String filename="holdemstartinghandevs.html";
        SAXParserFactory spf=SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser=spf.newSAXParser();
        XMLReader xmlReader=saxParser.getXMLReader();
        Sax sax=new Sax();
        xmlReader.setContentHandler(sax);
        Reader reader=new FileReader(new File(filename));
        InputSource inputSource=new InputSource(reader);
        System.out.println("parse");
        File file=new File("tempcode.txt");
        sax.writer=new FileWriter(file);
        xmlReader.parse(inputSource);
        sax.writer.close();
        sax.run();
    }
    Writer writer;
    Integer row=0,column=0;
    boolean inTd;
    final int players=10;
    final Double ante=1.5/players;
    final int rows=169,columns=10;
    final int total=1326;
    String[] hand=new String[rows];
    Double[][] data=new Double[rows][columns];
    Double[] mean=new Double[rows];
    Double average;
    Map<HoldemHand,Double> map=new LinkedHashMap<>();
    List<MyPair> pairs=new ArrayList<>();
    Integer[] cumulativeFrequency=new Integer[rows];
}
