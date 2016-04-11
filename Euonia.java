// Base imports
import java.util.List;

// Jsoup imports
import org.jsoup.nodes.Element;

public class Euonia {


  public static void main(String [] args) {
    String url = "http://159.203.123.108";
    parseSite(url);
    Graph g = new Graph();
    g.updateRank();
  }

  public static void parseSite(String url) {
    List<Element> links = Parser.getLinks( url );
    Edge.linksToEdges(links, url);
  }

}
