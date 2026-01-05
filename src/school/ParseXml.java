package school;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLResolver;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
//import sun.misc.IOUtils;
public class ParseXml {
	public static void main(String[] args) throws ParserConfigurationException,SAXException,IOException {
		
		XMLInputFactory xmlInput = XMLInputFactory.newInstance();
		/*
		xmlInput.setXMLResolver(new XMLResolver() {
		    @Override
		    public Object resolveEntity(String publicID, String systemID, String baseURI, String namespace) throws XMLStreamException {
		        // Disable dtd validation
		        if ("The public id you except".equals(publicId)) {
		            return IOUtils.toInputStream("");
		        }
		    }
		});
		*/
	String filename="holdemstartinghandevs.html";
	DocumentBuilderFactory docBuilderFactory=DocumentBuilderFactory.newInstance();
	DocumentBuilder docBuilder=docBuilderFactory.newDocumentBuilder();
	Document doc=docBuilder.parse(new File(filename));
	System.out.println(doc);
	System.out.println(doc.getDocumentElement());
	}
}
