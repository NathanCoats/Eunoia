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

  // simple new edge where you can add nodes.
  public Edge() {
    this.weight = 0;
  }

  // used to generate a new edge without a weight
  public Edge(Node start, Node end) {
    this.start  = start;
    this.end    = end;
    this.weight = 0;
  }

  // construct in case they want to pass in the start and end nodes
  public Edge(Node start, Node end, double weight) {
    this.start  = start;
    this.end    = end;
    this.weight = weight;
  }

  // construct in case they only want to pass in the begin and end ids instead of nodes
  public Edge(String start, String end, double weight) {
    this.start  = new Node( start, true );
    this.end    = new Node( end, true );
    this.weight = weight;
  }

  public static List<Edge> allEdges() {
    //creates connection to the edges collection
    Connection connection = new Connection( "edges" );
    //creates empty edges array list
    List<Edge> edges = new ArrayList<Edge>();
    DBObject obj;

    try {
      // gets a mongo cursor to the entire edges collection
      DBCursor cursor = connection.col.find();
      // iterators through the collection
      while( cursor.hasNext() ) {
          obj = cursor.next();
          // creates a new edge in system memory to use in the graph
          edges.add( new Edge( obj.get("start").toString(), obj.get("end").toString(), Double.parseDouble( obj.get("weight").toString() ) ) );
      }
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    finally {
      // makes sure collection is closed to avoid any timeout errors.
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
