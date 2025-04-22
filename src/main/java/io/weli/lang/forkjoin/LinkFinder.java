package io.weli.lang.forkjoin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.concurrent.RecursiveAction;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

/**
 * @author Madalin Ilie
 */
public class LinkFinder extends RecursiveAction {

   /**
    * Used fot statistics
    */
   private static final long t0 = System.nanoTime();
   private String url;
   private LinkHandler linkHandler;
   private static final long serialVersionUID = 1L;

   public LinkFinder(String url, LinkHandler handler) {
      this.url = url;
      this.linkHandler = handler;
   }

   @Override
   protected void compute() {
      if (url == null) {
         return;
      }
      try {
         List<RecursiveAction> actions = new ArrayList<>();
         URL uriLink = URI.create(url).toURL();
         Parser parser = new Parser(uriLink.openConnection());
         NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));

         for (int i = 0; i < list.size(); i++) {
            LinkTag extracted = (LinkTag) list.elementAt(i);
            if (!extracted.extractLink().isEmpty() && !linkHandler.visited(extracted.extractLink())) {
               actions.add(new LinkFinder(extracted.extractLink(), linkHandler));
            }
         }
         invokeAll(actions);
      } catch (Exception e) {
         // Ignore exceptions
      }
   }

   private void getSimpleLinks(String url) {
      //if not already visited
      if (!linkHandler.visited(url)) {
         try {
            URL uriLink = URI.create(url).toURL();
            Parser parser = new Parser(uriLink.openConnection());
            NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));
            List<String> urls = new ArrayList<String>();

            for (int i = 0; i < list.size(); i++) {
               LinkTag extracted = (LinkTag) list.elementAt(i);

               if (!extracted.getLink().isEmpty()
                     && !linkHandler.visited(extracted.getLink())) {

                  urls.add(extracted.getLink());
               }

            }
            //we visited this url
            linkHandler.addVisited(url);

            if (linkHandler.size() == 1500) {
               System.out.println("Time to visit 1500 distinct links = " + (System.nanoTime() - t0));
            }

            for (String l : urls) {
               linkHandler.queueLink(l);
            }

         } catch (Exception e) {
            //ignore all errors for now
         }
      }
   }

   public static void main(String[] args) throws IOException, URISyntaxException {
      String url = "https://example.com";
      List<String> links = findLinks(url);
      links.forEach(System.out::println);
   }

   public static List<String> findLinks(String url) throws IOException, URISyntaxException {
      List<String> links = new ArrayList<>();
      URL website = new URI(url).toURL();
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(website.openStream()))) {
         String line;
         Pattern pattern = Pattern.compile("<a\\s+href=\"([^\"]+)\"");
         while ((line = reader.readLine()) != null) {
            Matcher matcher = pattern.matcher(line);
            while (matcher.find()) {
               links.add(matcher.group(1));
            }
         }
      }
      return links;
   }
}
