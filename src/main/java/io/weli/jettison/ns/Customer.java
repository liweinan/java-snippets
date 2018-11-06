package io.weli.jettison.ns;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Customer {
   private long id;
   private String name;

   @XmlAttribute
   public long getId() {
      return id;
   }

   public void setId(long id) {
      this.id = id;
   }

   @XmlElement(namespace = "http://www.example.org/property")
   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
