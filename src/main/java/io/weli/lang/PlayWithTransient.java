package io.weli.lang;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;

public class PlayWithTransient {

    public static class Foo {
        String v1;
        transient String v2;
        final transient String v3 = "v3 string";

        public String getV1() {
            return v1;
        }

        public void setV1(String v1) {
            this.v1 = v1;
        }

        public String getV2() {
            return v2;
        }

        public void setV2(String v2) {
            this.v2 = v2;
        }
    }

    public static void main(String[] args) {
        var jsonb =  JsonbBuilder.create();
        var foo = new Foo();
        foo.setV1("v1 str");
        foo.setV2("v2 str");

        // {"v1":"v1 str"}
        System.out.println(jsonb.toJson(foo));

    }
}
