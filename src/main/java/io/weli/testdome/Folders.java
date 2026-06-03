package io.weli.testdome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Folders {
    public static Collection<String> folderNames(String xml, char startingLetter) throws Exception {
        Collection<String> result = new ArrayList<>();

        // Pattern to match folder name attributes
        Pattern pattern = Pattern.compile("folder name=\"([^\"]+)\"");
        Matcher matcher = pattern.matcher(xml);

        while (matcher.find()) {
            String folderName = matcher.group(1);
            if (folderName.length() > 0 && folderName.charAt(0) == startingLetter) {
                result.add(folderName);
            }
        }

        return result;
    }

    public static void main(String[] args) throws Exception {
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                        "<folder name=\"c\">" +
                        "<folder name=\"program files\">" +
                        "<folder name=\"uninstall information\" />" +
                        "</folder>" +
                        "<folder name=\"users\" />" +
                        "</folder>";

        Collection<String> names = folderNames(xml, 'u');
        for(String name: names)
            System.out.println(name);
    }
}
