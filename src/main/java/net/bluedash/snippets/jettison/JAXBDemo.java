package net.bluedash.snippets.jettison;

import org.codehaus.jettison.mapped.Configuration;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class JAXBDemo {
    public static void main(String[] args) throws Exception {
        JAXBContext jc = JAXBContext.newInstance(Customer.class);

        Unmarshaller unmarshaller = jc.createUnmarshaller();
        Customer customer = (Customer) unmarshaller.unmarshal(new File("target/classes/input.xml"));

        Configuration config = new Configuration();
        MappedNamespaceConvention con = new MappedNamespaceConvention(config);
        Writer writer = new OutputStreamWriter(System.out);
        XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, writer);

        Marshaller marshaller = jc.createMarshaller();
        marshaller.marshal(customer, xmlStreamWriter);
    }
}
