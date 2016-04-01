// mongo db imports
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

import java.util.ArrayList;
import java.util.List;

public class Connection {
  public String database = "eunoia" ;
  public String username = "" ;
  public String password = "";
  public String[] hosts = { "127.0.0.1" } ;
  public String port = "27017";
  public String type = "mongod://";

  public MongoClient client = null;
  public DB db = null;
  public DBCursor cursor = null;
  public DBCollection col = null;

  public Connection(String collection) {
    try {
      this.client = new MongoClient( getServerList() );
      this.db = client.getDB(database);
      this.col = db.getCollection(collection);
    }
    catch(Exception e) {
      //@TODO handle properly
      e.printStackTrace();
    }
  }


  public String getConnectionString() {
    return  getType() + getUsername() + ":" + getPassword() + "@" + getHostString() + ":" + getPort() + "/" + getDatabase() ;
  }


// simply gets back a list of ServerAddress to create a new mongo connection, this is obtained from the hosts array
public List<ServerAddress> getServerList() {
  List<ServerAddress> servers = new ArrayList<ServerAddress>();
  try {
    for(int i = 0; i < hosts.length; i++) {
      servers.add( new ServerAddress( hosts[i] ) );
    }
  }
  catch(Exception e) {
    e.printStackTrace();
  }
  return servers;
}

	/**
	* Returns value of database
	* @return
	*/
	public String getDatabase() {
		return database;
	}

  /**
	* Returns value of type
	* @return
	*/
	public String getType() {
		return type;
	}


	/**
	* Returns value of username
	* @return
	*/
	public String getUsername() {
		return username;
	}

	/**
	* Returns value of password
	* @return
	*/
	public String getPassword() {
		return password;
	}

	/**
	* Returns value of host
	* @return
	*/
	public String getHostString() {
    String host_string = "";
    for(int i = 0; i < hosts.length; i++) {
      host_string += hosts[i]+",";
    }
    // this is just a quick method to remove the trailing comma
    return host_string.substring( 0, host_string.length() - 1 );
	}

	/**
	* Returns value of port
	* @return
	*/
	public String getPort() {
		return port;
	}

}
