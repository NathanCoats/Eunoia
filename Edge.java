// base imports
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.mongodb.BasicDBObject;

// Jsoup imports
import org.jsoup.nodes.Element;

public class Edge {

  public Node start, end;
  public Integer weight;

  public Edge() {
    this.weight = 0;
  }

  public Edge(Node start, Node end) {
    this.start  = start;
    this.end    = end;
    this.weight = 0;
  }

  public Edge(Node start, Node end, Integer weight) {
    this.start  = start;
    this.end    = end;
    this.weight = weight;
  }


  public static List<Edge> linksToEdges(List<Element> links, String base) {
    List<Edge> edges = new ArrayList<Edge>();
    Edge edge = null;
    String href = "";
    Element current  = null;
    Iterator<Element> it = links.iterator();
    Node start, end;

    while( it.hasNext() ) {

      current = it.next();

      start   = new Node(base);
      href    = Node.cleanUrl( current.attr("href") );
      end     = new Node( base + href );
      edges.add(edge);
      edge    = new Edge(start, end);
      edge.save();
    }

    return edges;
  }

  public void save() {
    Connection connection = new Connection( "edges" );
    BasicDBObject obj = new BasicDBObject("start",start.getId()).append("end", end.getId()).append("weight",weight);
    connection.col.insert(obj);
  }

  public void printPoints() {
    System.out.println("Start Point is " + start.url);
    System.out.println("End Point is " + end.url);
    System.out.println("Edge Weight is " + weight);

  }

  // simply gets back a list of ServerAddress to create a new mongo connection, this is obtained from the hosts array
  // public List<ServerAddress> getServerList() {
  //   List<ServerAddress> servers = new ArrayList<ServerAddress>();
  //   try {
  //     for(int i = 0; i < hosts.length; i++) {
  //       servers.add( new ServerAddress( hosts[i] ) );
  //     }
  //   }
  //   catch(Exception e) {
  //     e.printStackTrace();
  //   }
  //   return servers;
  // }

}
