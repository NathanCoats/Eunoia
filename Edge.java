// base imports
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;


import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

// Jsoup imports
import org.jsoup.nodes.Element;

public class Edge {

  public Node start, end;
  public double weight;

  public Edge() {
    this.weight = 0;
  }

  public Edge(Node start, Node end) {
    this.start  = start;
    this.end    = end;
    this.weight = 0;
  }

  public Edge(Node start, Node end, double weight) {
    this.start  = start;
    this.end    = end;
    this.weight = weight;
  }

  public Edge(String start, String end, double weight) {
    this.start  = new Node(start);
    this.end    = new Node(end);
    this.weight = weight;
  }

  public static List<Edge> allEdges() {
    Connection connection = new Connection( "edges" );
    List<Edge> edges = new ArrayList<Edge>();
    DBObject obj;
    try {
      DBCursor cursor = connection.col.find();
      while( cursor.hasNext() ) {
          obj = cursor.next();
          edges.add( new Edge( obj.get("start").toString(), obj.get("end").toString(), Double.parseDouble( obj.get("weight").toString() ) ) );
      }
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    finally {
      connection.close();
    }
    return edges;
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
    BasicDBObject search = new BasicDBObject( "start", start.getId() ).append("end", end.getId());
    BasicDBObject obj = new BasicDBObject( "start", start.getId() ).append( "end", end.getId() ).append( "weight", weight );
    try {
      connection.col.update(search, obj, true, false);
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    finally {
      connection.close();
    }
  }

  public void printPoints() {
    System.out.println("Start Point is " + start.url);
    System.out.println("End Point is " + end.url);
    System.out.println("Edge Weight is " + weight);
  }

  public Node getStart() {
    return this.start;
  }

  public Node getEnd() {
    return this.end;
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
