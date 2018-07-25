package jaxb;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class JavaToXsd {
    public static void main(String[] args) throws Exception {
        JAXBContext context = JAXBContext.newInstance(ListType.class);
        SchemaOutputResolver resolver = new MyResolver();
        context.generateSchema(resolver);

    }

    private static class MyResolver extends SchemaOutputResolver {
        public Result createOutput(String namespaceURI, String suggestedFileName) throws IOException {
            File file = new File(suggestedFileName);
            StreamResult result = new StreamResult(file);
            result.setSystemId(file.toURI().toURL().toString());
            return result;
        }
    }
}
