package net.bluedash.snippets.jackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.bluedash.snippets.io.StringOutputStream;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */
public class Main {

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        User user = mapper.readValue(new File("target/classes/user.json"), User.class);
        System.out.println(user);

        //raw
        Map<String, Object> userData = new HashMap<String, Object>();
        Map<String, String> nameStruct = new HashMap<String, String>();
        nameStruct.put("first", "Joe");
        nameStruct.put("last", "Sixpack");
        userData.put("name", nameStruct);
        userData.put("gender", "MALE");
        userData.put("verified", Boolean.FALSE);
        userData.put("userImage", "Rm9vYmFyIQ==");

        StringOutputStream out = new StringOutputStream();
        mapper.writeValue(out, userData);
        System.out.println(out.toString());

        //array
        out = out.renew();
        List<String> fruits = new ArrayList<String>();
        fruits.add("Apple");
        fruits.add("Tomato");
        fruits.add("Cucumber");
        mapper.writeValue(out, fruits);
        System.out.println(out.toString());

        //generic
        User tom = new User();
        tom.setName(new User.Name("Tom", "Sawyer"));
        tom.setGender(User.Gender.MALE);
        tom.setUserImage("Rm9vYmFyIQ==".getBytes());

        Map<String, User> entry = new HashMap<String, User>();
        entry.put("A", tom);

        out = out.renew();
        mapper.writeValue(out, entry);
        System.out.println(out.toString());
        Map<String, User> result = mapper.readValue(out.toString(),
                new TypeReference<Map<String, User>>() {
                });
        System.out.println(result.get("A"));

        //tree based
        ObjectMapper m = new ObjectMapper();
        // can either use mapper.readTree(source), or mapper.readValue(source, JsonNode.class);
        JsonNode rootNode = m.readTree(new File("target/classes/user.json"));
        JsonNode nameNode = rootNode.path("name");
        String lastName = nameNode.path("last").asText();
        System.out.println(lastName);

        //streaming api
        JsonFactory f = new JsonFactory();
        out = out.renew();
        JsonGenerator g = f.createJsonGenerator(out);

        g.writeStartObject();
        g.writeObjectFieldStart("name");
        g.writeStringField("first", "Joe");
        g.writeStringField("last", "Sixpack");
        g.writeEndObject(); // for field 'name'
        g.writeStringField("gender", User.Gender.MALE.toString());
        g.writeBooleanField("verified", false);
        g.writeFieldName("userImage"); // no 'writeBinaryField' (yet?)
        byte[] binaryData = "...".getBytes();
        g.writeBinary(binaryData);
        g.writeEndObject();
        g.close(); // important: will force flushing of output, close underlying output stream

        System.out.println(out.toString());

        //annotation
        Apple apple = new Apple();
        apple.setColor("Red");
        apple.setId("1");

        out = out.renew();

        mapper.writeValue(out, apple);
        System.out.println(out);
    }
}
