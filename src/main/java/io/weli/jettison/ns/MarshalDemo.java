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

      {
         // JSON marshaller via Jettison
         JAXBContext jc = JAXBContext.newInstance(Customer.class);
         Configuration config = new Configuration();
         Map<String, String> xmlToJsonNamespaces = new HashMap<>(1);
         xmlToJsonNamespaces.put("http://www.example.org/package", "");
         xmlToJsonNamespaces.put("http://www.example.org/property", "prop");
         config.setXmlToJsonNamespaces(xmlToJsonNamespaces);
         MappedNamespaceConvention con = new MappedNamespaceConvention(config);
         Writer writer = new OutputStreamWriter(System.out);
         XMLStreamWriter xmlStreamWriter = new MappedXMLStreamWriter(con, writer);

         Marshaller marshaller = jc.createMarshaller();

         Customer customer = new Customer();
         customer.setId(1);
         customer.setName("Tom");

         marshaller.marshal(customer, xmlStreamWriter);
         // {"customer":{"@id":"1","prop.name":"Tom"}}
      }

      {
         // XML marshaller via JAXB
         // https://stackoverflow.com/questions/13788617/jaxb-marshalling-java-to-output-xml-file
         JAXBContext jc = JAXBContext.newInstance(Customer.class);
         Marshaller marshaller = jc.createMarshaller();
         marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8"); //NOI18N
         marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);


         Customer customer = new Customer();
         customer.setId(1);
         customer.setName("Tom");

         marshaller.marshal(customer, System.out);
      }

   }
}
