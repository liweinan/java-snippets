package wadl;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import wadl.jaxb.Grammars;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.sax.SAXSource;
import java.io.*;

public class TryUnmarshaller {
    /**
     * Unmarshal a jaxb bean into a type of {@code resultClass} from the given {@code inputStream}.
     *
     * @param inputStream      Input stream that contains input xml that should be processed.
     * @param saxParserFactory Sax parser factory for unmarshalling xml.
     * @param resultClass      Class of the result bean into which the content of {@code inputStream} should be unmarshalled.
     * @param <T>              Type of the result jaxb bean.
     * @return Unmarshalled jaxb bean.
     * @throws JAXBException                In case of jaxb problem.
     * @throws ParserConfigurationException In case of problem with parsing xml.
     * @throws SAXException                 In case of problem with parsing xml.
     */
    public static <T> T unmarshall(InputStream inputStream, SAXParserFactory saxParserFactory,
                                   Class<T> resultClass) throws JAXBException, ParserConfigurationException, SAXException {

        JAXBContext jaxbContext = JAXBContext.newInstance("wadl.jaxb");

        final SAXParser saxParser = saxParserFactory.newSAXParser();
        SAXSource source = new SAXSource(saxParser.getXMLReader(), new InputSource(inputStream));
        final Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        final Object result = unmarshaller.unmarshal(source);
        return resultClass.cast(result);
    }

    public static void main(String[] args) throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(Grammars.class);
        Marshaller marshaller = ctx.createMarshaller();
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        Grammars grammars = new Grammars();
        marshaller.marshal(grammars, writer);
        System.out.println(stringWriter.toString());

        FileWriter fileWriter = new FileWriter("/tmp/schema.xml");
        writer = new PrintWriter(fileWriter);
        marshaller.marshal(grammars, writer);
        writer.flush();


        Unmarshaller unmarshaller = ctx.createUnmarshaller();
        Grammars out = (Grammars) unmarshaller.unmarshal(new File("/tmp/schema.xml"));
        System.out.println(out);

    }
}
