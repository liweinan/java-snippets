package net.bluedash.snippets.jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;

/**
 * 09 23 2012
 *
 * mvn install
 * mvn -q exec:java -Dexec.mainClass="net.bluedash.snippets.jaxb.ParseWithStax"
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class ParseWithStax {

    public static void main(String[] args) throws Exception {
        InputStream dataXml = ParseWithStax.class.getResourceAsStream("/helloEntries.xml");
        XMLInputFactory inFactory = XMLInputFactory.newInstance();
        XMLStreamReader r = inFactory.createXMLStreamReader(dataXml);

        JAXBContext ctx = JAXBContext.newInstance("net.bluedash.snippets.jaxb");
        Unmarshaller um = ctx.createUnmarshaller();

        try {
            int event = r.getEventType();
            while (r.hasNext()) {
                if (event == XMLStreamConstants.START_ELEMENT && r.getName().toString().equals("hello")) {
                    Hello hello = (Hello) um.unmarshal(r);
                    // process hello
                    System.out.println(hello.getName());
                }
                event = r.next();
            }
        } finally {
            r.close();
        }

    }
}
