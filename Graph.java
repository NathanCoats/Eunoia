import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.math.BigInteger;

public class Graph {
  public List<Edge> edges = new ArrayList<Edge>();

  public Graph() {}

  public Graph( List<Edge> edges ) {
    this.edges = edges;
  }

  public void addEdge(BigInteger start, BigInteger end) {
    Edge edge = new Edge(start, end);
    edges.add(edge);
  }

  public void addEdge(BigInteger start, BigInteger end, Integer weight) {
    Edge edge = new Edge(start, end, weight);
    edges.add(edge);
  }

  public void addEdge(Edge edge) {
    edges.add(edge);
  }

  public void printEdges() {

    if( !edges.isEmpty() ) {
      Iterator<Edge> it = edges.iterator();
      while( it.hasNext() ) {
          it.next().printPoints();
      }
    }
    else {
      System.out.println("Sorry this graph has no edges.");
    }

  }

}
