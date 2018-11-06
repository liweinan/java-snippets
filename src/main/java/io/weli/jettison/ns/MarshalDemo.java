package io.weli.jettison.ns;

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
import java.util.HashMap;
import java.util.Map;

/**
 * http://blog.bdoughan.com/2011/04/jaxb-and-json-via-jettison-namespace.html
 */
public class MarshalDemo {
   public static void main(String[] args) throws Exception {
      JAXBContext jc = JAXBContext.newInstance(Customer.class);

      Unmarshaller unmarshaller = jc.createUnmarshaller();
      Customer customer = (Customer) unmarshaller.unmarshal(new File("./target/classes/input.xml"));

      Configuration config = new Configuration();
      Map<String, String> xmlToJsonNamespaces = new HashMap<>(1);
      xmlToJsonNamespaces.put("http://www.example.org/package", "");
      xmlToJsonNamespaces.put("http://www.example.org/property", "prop");
      config.setXmlToJsonNamespaces(xmlToJsonNamespaces);
      MappedNamespaceConvention con = new MappedNamespaceConvention(config);
      Writer writer = new OutputStreamWriter(System.out);
      XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, writer);

      Marshaller marshaller = jc.createMarshaller();
      marshaller.marshal(customer, xmlStreamWriter);
   }
}
