package io.weli.mapstruct;

// https://www.baeldung.com/mapstruct
public class Play {
    public static void main(String[] args) {
        SimpleSource simpleSource = new SimpleSource();
        simpleSource.setName("SourceName");
        simpleSource.setDescription("SourceDescription");

        System.out.println(
                SimpleSourceDestinationMapper
                        .INSTANCE
                        .sourceToDestination(simpleSource));


    }
}
