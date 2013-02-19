package net.bluedash.snippets.jettison;

import org.codehaus.jettison.AbstractXMLStreamReader;
import org.codehaus.jettison.AbstractXMLStreamWriter;
import org.codehaus.jettison.badgerfish.BadgerFishXMLStreamReader;
import org.codehaus.jettison.badgerfish.BadgerFishXMLStreamWriter;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.mapped.MappedNamespaceConvention;
import org.codehaus.jettison.mapped.MappedXMLStreamReader;
import org.codehaus.jettison.mapped.MappedXMLStreamWriter;

import javax.xml.stream.XMLStreamReader;
import java.io.StringWriter;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Main {

    public static void main(String[] args) throws Exception {
        StringWriter strWriter = new StringWriter();

        // Mapped convention
        MappedNamespaceConvention con = new MappedNamespaceConvention();
//        AbstractXMLStreamWriter w = new MappedXMLStreamWriter(con, strWriter);

        // BadgerFish convention
        AbstractXMLStreamWriter w = new BadgerFishXMLStreamWriter(strWriter);
        w.writeStartDocument();
        w.writeStartElement("alice");
        w.writeCharacters("bob");
        w.writeEndElement();
        w.writeEndDocument();

        w.close();
        strWriter.close();
        System.out.println(strWriter.toString());

        //parsing
        // Mapped convention
        JSONObject obj = new JSONObject("{\"alice\":{\"$\":\"bob\"}}");
        AbstractXMLStreamReader reader = new BadgerFishXMLStreamReader(obj);
        System.out.println(reader.next() + ":" + reader.getName().getLocalPart());
        System.out.println(reader.next() + ":" + reader.getText());
        System.out.println(reader.next() + ":" + reader.getName().getLocalPart());
    }
}
