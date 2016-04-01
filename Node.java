// base inserts
import java.util.List;
import java.util.Random;

import org.bson.types.ObjectId;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.WriteResult;


public class Node {

  public String id;
  public String name;
  public String url;
  public double rank;

  public Node() {
    this.id    = "";
    this.rank = 1D;
    this.url   = "";
    this.name  = "";
  }

  public Node(String id, boolean load) {
    if(load) {
      Connection connection = new Connection( "nodes" );
      try {
        BasicDBObject search = new BasicDBObject("_id", new ObjectId(id) );
        DBObject result = connection.col.findOne(search);

        this.id = result.get("_id").toString();
        this.url = result.get("url").toString();
        this.rank = Double.parseDouble( result.get("rank").toString() );
        this.name = result.get("name").toString();

      }
      catch(Exception e) {
        e.printStackTrace();
      }
      finally{
        connection.close();
      }
    }
  }

  public Node(String url) {
    this.rank = 1D;
    setUrl(url);
    this.name  = "";
    this.save();
  }

  // public Node() {
  //   // get values from db
  // }

  /**
  * Sets new value of url
  * @param
  */
  public static String cleanUrl(String url) {
    try {
      while(url.charAt(0) == '.') {
        url = url.substring(1);
      }
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    return url;
  }

  public void save() {

    Connection connection = new Connection( "nodes" );

    BasicDBObject obj = new BasicDBObject( "name" , "" ).append("url",url).append("rank",rank);
    BasicDBObject search = new BasicDBObject("url", url);

    try {

      connection.col.update(search, obj, true, false);
    }
    catch(NullPointerException e) {
      e.printStackTrace();
    }
    finally {
      this.id = connection.col.findOne(search).get("_id").toString();
      connection.close();
    }

  }

  public double addPoints(double points) {
    rank += points;
    return rank;
  }

  public double minusPoints(double points) {
    rank -= points;
    return rank;
  }

	/**
	* Returns value of id
	* @return
	*/
	public String getId() {
		return id;
	}

	/**
	* Sets new value of id
	* @param
	*/
	public void setId(String id) {
		this.id = id;
	}

	/**
	* Returns value of name
	* @return
	*/
	public String getName() {
		return name;
	}

	/**
	* Sets new value of name
	* @param
	*/
	public void setName(String name) {
		this.name = name;
	}

	/**
	* Returns value of url
	* @return
	*/
	public String getUrl() {
		return url;
	}

	/**
	* Sets new value of url
	* @param
	*/
	public void setUrl(String url) {
    try {
      while(url.charAt(0) == '.') {
        url = url.substring(1);
      }
  		this.url = url;
    }
    catch(Exception e) {
      e.printStackTrace();
    }
	}

	/**
	* Returns value of value
	* @return
	*/
	public double getRank() {
		return rank;
	}

	/**
	* Sets new value of value
	* @param
	*/
	public void setRank(double rank) {
		this.rank = rank;
    this.save();
	}
}
