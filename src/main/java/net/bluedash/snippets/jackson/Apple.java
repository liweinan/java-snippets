package net.bluedash.snippets.jackson;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;

/**
 * @author <a href="mailto:l.weinan@gmail.com">Weinan Li</a>
 */

public class Apple {

    @JsonProperty("_color")
    private String color;

    @JsonIgnore
    private String id;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
