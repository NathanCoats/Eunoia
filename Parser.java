import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Document;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Parser {

  public static List<Element> getLinks(String destination) {
    List<Element> elements = new ArrayList<Element>();
    Element current = null;
    String html = "";
    try {
      Document doc = Jsoup.connect(destination).get();
      html = doc.outerHtml();
      Iterator<Element> it = doc.select("a").iterator();
      while( it.hasNext() ) {
        current = it.next();
        elements.add( current );
      }
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    finally {
      return elements;
    }
  }

}
