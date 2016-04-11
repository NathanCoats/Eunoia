import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;

public class Graph {
  public List<Edge> edges = new ArrayList<Edge>();
  public double damping_factor = .85;

  public Graph() {
    List<Edge> edges = Edge.allEdges();
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

  // public void run() {
  //   t = new Thread(this);
  //   t.start();
  // }


  public void updateRank() {
    Iterator<Edge> it = edges.iterator();
    Edge current;
    Node end = null;
    Node start = null;

    // goes through each edge in the graph
    while( it.hasNext() ) {
      current = it.next();

      //gets the start node so it can calculate the new_rank to append to the current rank
      start = current.getStart();

      //uses the start node of the edge to get its current rank, and the amount of pages it links to so the correct modification can be calculated
      double new_rank = calculateAmount( start );

      // updates the end node to its new rank.
      end = current.getEnd();
      end.updateRank(new_rank);
    }
  }

  public double calculateAmount(Node start) {
    double rank_sums = 0;
    Node end;
    Connection connection = new Connection( "edges" );

    //finds all documents where the start id = node
    BasicDBObject search = new BasicDBObject( "start" , start.getId() );
    DBCursor cursor = connection.col.find(search);

    // the amount of results the query yeilds
    Integer amount = cursor.size();
    double start_rank = start.getRank();

    connection.close();

    // if size = 0 then add 0 to the rank
    return (amount > 0) ? (1 - damping_factor) + ( damping_factor * (start_rank / amount) ) : 0;
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


}
