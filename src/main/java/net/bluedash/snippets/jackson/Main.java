package net.bluedash.snippets.jackson;

import com.fasterxml.jackson.databind.ObjectMapper;
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
        Map<String,Object> userData = new HashMap<String,Object>();
        Map<String,String> nameStruct = new HashMap<String,String>();
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
    }
}
