// base inserts
import java.util.List;
import java.util.Random;
import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;


public class Node {

  public String id;
  public String name;
  public String url;
  public double value;

  public Node() {
    this.id    = "";
    this.value = 0D;
    this.url   = "";
    this.name  = "";
  }

  public Node(String url) {
    this.value = 0D;
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
    BasicDBObject obj = new BasicDBObject( "name" , "" ).append("url",url).append("value",0D);
    WriteResult result = connection.col.insert(obj);
    id = result.getUpsertedId().toString();
    System.out.println(id);
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
	public double getValue() {
		return value;
	}

	/**
	* Sets new value of value
	* @param
	*/
	public void setValue(double value) {
		this.value = value;
	}
}
