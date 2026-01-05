package school;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.*;
import org.xml.sax.helpers.*;
public class SmallSax extends DefaultHandler {
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
                } else {
                    data[row-2][column-1]=Double.valueOf(trimmed);
                }
                print(2);
            }
        } else {
            System.out.println("empty or not in td: "+string);
        }
    }
    void print(int n) {
        System.out.println("n: "+n);
        for(int i=0;i<n;i++) {
            System.out.print(hand[i]+": ");
            for(int j=0;j<columns;j++)
                System.out.print(data[i][j]+" ");
            System.out.println();
        }
    }
    public static void main(String[] args) throws ParserConfigurationException,SAXException,IOException {
        String filename="smallholdemstartinghandevs.html";
        SAXParserFactory spf=SAXParserFactory.newInstance();
        spf.setNamespaceAware(true);
        SAXParser saxParser=spf.newSAXParser();
        XMLReader xmlReader=saxParser.getXMLReader();
        SmallSax smallSax=new SmallSax();
        xmlReader.setContentHandler(smallSax);
        Reader reader=new FileReader(new File(filename));
        InputSource inputSource=new InputSource(reader);
        System.out.println("parse");
        xmlReader.parse(inputSource);
        System.out.println("after parse, row: "+smallSax.row);
        smallSax.print(smallSax.rows);
    }
    Integer row=0,column=0;
    boolean inTd;
    final int rows=7,columns=10;
    String[] hand=new String[rows];
    Double[][] data=new Double[rows][columns];
}
