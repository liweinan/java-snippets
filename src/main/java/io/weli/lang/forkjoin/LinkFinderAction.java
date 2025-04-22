package io.weli.lang.forkjoin;

import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

/**
 * @author Madalin Ilie
 */
public class LinkFinderAction extends RecursiveAction {

   /**
    * Used for statistics
    */
   private static final long t0 = System.nanoTime();
   private String url;
   private LinkHandler cr;
   private static final long serialVersionUID = 1L;

   public LinkFinderAction(String url, LinkHandler cr) {
      this.url = url;
      this.cr = cr;
   }

   @Override
   protected void compute() {
      if (url == null) {
         return;
      }
      if (!cr.visited(url)) {
         try {
            List<RecursiveAction> actions = new ArrayList<RecursiveAction>();
            URL uriLink = URI.create(url).toURL();
            Parser parser = new Parser(uriLink.openConnection());
            NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));

            for (int i = 0; i < list.size(); i++) {
               LinkTag extracted = (LinkTag) list.elementAt(i);

               if (!extracted.extractLink().isEmpty()
                     && !cr.visited(extracted.extractLink())) {

                  actions.add(new LinkFinderAction(extracted.extractLink(), cr));
               }
            }
            cr.addVisited(url);

            if (cr.size() == 1500) {
               System.out.println("Time for visit 1500 distinct links= " + (System.nanoTime() - t0));
            }

            //invoke recursively
            invokeAll(actions);
         } catch (Exception e) {
            //ignore 404, unknown protocol or other server errors
         }
      }
   }
}
