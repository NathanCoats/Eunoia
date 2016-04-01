// import com.mongodb.BasicDBObject;
// import com.mongodb.DB;
// import com.mongodb.DBCollection;
// import com.mongodb.DBCursor;
// import com.mongodb.DBObject;
// import com.mongodb.MongoClient;
// import com.mongodb.ServerAddress;
// import com.mongodb.WriteConcern;

import java.util.*;

import java.lang.reflect.*;

public class Mongo {
	
	public String database;
	public String collection;
	public String username;
	public String password;
	public String[] hosts;
	public String port;
	public String type = "mongodb://";

	public LinkedList<DBObject> results;

	// public MongoClient client;
	// public BasicDBOject search;
	// public DB db;
	// public DBCursor cursor;
	// public DBCollection collection;

	public Mongo(String database, String collection, String[] hosts ,String port ) {
		this.database   = database;
		this.collection = collection;
		this.hosts      = hosts;
		this.port       = port;
		this.username   = "";
		this.password   = "";
	}

	public Mongo(String database, String collection, String[] hosts ,String port, String username, String password ) {
		this.database   = database;
		this.collection = collection;
		this.hosts      = hosts;
		this.port       = port;
		this.username   = username;
		this.password   = password;
	}

	public void connect() {
		try {
	      client = new MongoClient( getServerList() );
	      db = client.getDB(database);
	      collection = db.getCollection(collection);
		}
		catch(Exception e) {
			//@TODO handle properly
		}
	}

	public void close() {
		try {
			this.client.close();
		}
		catch(Exception e) {
			//@TODO handle properly make sure it closes no matter what, maybe add a finally
		}
	}

	public boolean save(LinkedList<HashMap<String, String>> map) {
		connect();
		BasicDBOject obj = new BasicDBOject();
		Iterator<HashMap<String, String>> it = map.iterator();
		while( it.hasNext() ) {
			HashMap<String, String> current = it.next();
			// insert reflection stuff here
		}

		try {
			result = collection.findOne(search);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close();
		}

		return true;
	}

	public LinkedList<DBObject> where(String delimeter, String value) {
		results = new LinkedList<DBObject>();
		connect();
		search = new BasicDBOject(delimeter, value);
		try {
			results.add( collection.find(search) );
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close();
		}

		return results;
	}

	public DBObject first(String delimeter, String value) {
		connect();
		search = new BasicDBOject(delimeter, value);
		DBOject result;
		try {
			result = collection.findOne(search);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close();
		}

		return result;
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