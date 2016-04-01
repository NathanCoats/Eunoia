// Base imports
import java.util.List;
import java.math.BigInteger;

// Jsoup imports
import org.jsoup.nodes.Element;

public class Eunoia {
  public static void main (String [] args) {
    String url = "http://159.203.123.108";
    List<Element> links = Parser.getLinks( url );
    List<Edge> edges = Edge.linksToEdges(links, url);
    //Graph g = new Graph(edges);
  }
}
