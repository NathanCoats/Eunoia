import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

public class Graph {
  public List<Edge> edges = new ArrayList<Edge>();
  public double damping_factor = .85;

  public Graph() {}

  public Graph( List<Edge> edges ) {
    this.edges = new ArrayList<Edge>();
    Edge current;
    Iterator<Edge> it = edges.iterator();

    while( it.hasNext() ) {
      current = it.next();
      if(current instanceof Edge) {
        this.edges.add(current);
      }
    }

  }

  public void updateRank() {
    Iterator<Edge> it = edges.iterator();
    Edge current;
    while( it.hasNext() ) {
      current = it.next();

      Node start = current.getStart();
      Node end = current.getEnd();

      double new_rank = calculateAmount( start );
      start.setRank(new_rank);
    }
  }

  public double calculateAmount(Node start) {
    double rank_sums = 0;
    Integer amount    = 0;
    Node end;
    Connection connection = new Connection( "edges" );
    BasicDBObject search = new BasicDBObject( "start" , start.getId() );
    DBCursor cursor = connection.col.find(search);
    Edge current;

    amount = cursor.size();

    while( cursor.hasNext() ) {
      DBObject obj = cursor.next();
      end = new Node( obj.get("end").toString() , true );

      //current = new Edge( start, end );
      rank_sums += end.getRank();
    }

    connection.close();

    double rank = ( ( 1 - damping_factor) / amount ) + (damping_factor * rank_sums);
    return rank;
  }


  public void addEdge(String start, String end) {
    Node start_node = new Node(start);
    Node end_node = new Node(end);
    Edge edge = new Edge(start_node, end_node);
    edges.add(edge);
  }

  public void addEdge(String start, String end, double weight) {
    Node start_node = new Node(start);
    Node end_node = new Node(end);
    Edge edge = new Edge(start_node, end_node, weight);
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

  // public void run() {
  //   t = new Thread(this);
  //   t.start();
  // }

 public void start() {
   System.out.println();
 }

}
