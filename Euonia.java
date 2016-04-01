// Base imports
import java.util.List;
import java.math.BigInteger;

// Jsoup imports
import org.jsoup.nodes.Element;

public class Euonia {


  public static void main(String [] args) {
    String url = "http://159.203.123.108";
    parseSite(url);
    Graph g = loadGraph();
    g.updateRank();
  }

  public static void parseSite(String url) {
    List<Element> links = Parser.getLinks( url );
    Edge.linksToEdges(links, url);
  }

  public static Graph loadGraph() {
    List<Edge> edges = Edge.allEdges();
    Graph g = new Graph(edges);
    return g;
  }

}
