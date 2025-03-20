package io.weli.jsonb;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import lombok.Getter;
import lombok.Setter;

public class Play {

    @Getter
    @Setter
    public static class Shape {
        int area;
        String type;
    }

    public static void main(String[] args) {
        Shape shape = new Shape();
//        shape.setArea(12);
//        shape.setType("RECTANGLE");
        Jsonb jsonb = JsonbBuilder.create();
// serialize an object to JSON
        String jsonString = jsonb.toJson(shape); // output : {"area" : 12, "type" : "RECTANGLE"}
// deserialize a JSON string to an object
        System.out.println(jsonString);

        Shape s = jsonb.fromJson("{\"area\" : 12, \"type\": \"TRIANGLE\"}", Shape.class);
        System.out.println(s);
    }
}
