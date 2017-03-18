package xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Created by weli on 4/8/16.
 */
public class PlayWithXmlTypeAdapter {

    @XmlRootElement
//    @XmlAccessorType(XmlAccessType.FIELD)
    public static class House {

        @XmlJavaTypeAdapter(PersonAdapter.class)
        private Person host;

        public Person getHost() {
            return host;
        }

        public void setHost(Person host) {
            this.host = host;
        }
    }

    public static class Person {
        private String name;
        private String gender;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }
    }

    public static class PersonAdapter extends XmlAdapter<String, Person> {
        @Override
        public Person unmarshal(String str) throws Exception {
            Person p = new Person();
            p.setName(str.split(":")[0]);
            p.setGender(str.split(":")[1]);
            return p;
        }

        @Override
        public String marshal(Person person) throws Exception {
            return (person.getName() + ":" + person.getGender());
        }
    }

    public static void main(String[] args) throws Exception {
        JAXBContext ctx = JAXBContext.newInstance(House.class);

        House house = new House();
        Person host = new Person();
        host.setName("Weinan");
        host.setGender("Male");
        house.setHost(host);

        ctx.createMarshaller().marshal(house, System.out);
    }
}
