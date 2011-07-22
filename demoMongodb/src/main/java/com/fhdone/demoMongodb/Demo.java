package com.fhdone.demoMongodb;

import java.net.UnknownHostException;
import java.util.Set;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class Demo {
	public static void main(String[] arg) throws UnknownHostException, MongoException{
		Mongo m = new Mongo( "localhost" , 27017 );
		DB db = m.getDB( "mydb" );

		//drop database
		m.dropDatabase("mydb");


		boolean auth = db.authenticate("", new char[]{'1'});
		System.out.println("auth"+auth);

		//get all collection's name
		Set<String> colls = db.getCollectionNames();
		for (String s : colls) {
			System.out.println("collection's name"+s);
		}

		//create a object
		BasicDBObject doc = new BasicDBObject();
		doc.put("name", "MongoDB");
		doc.put("type", "database");
		doc.put("count", 1);

		//create complex object
		BasicDBObject info = new BasicDBObject();
		info.put("x", 203);
		info.put("y", 102);
		doc.put("info", info);

		//get collection
		DBCollection coll = db.getCollection("testCollection");

		//insert
		coll.insert(doc);

		//query
		DBObject myDoc = coll.findOne();
		System.out.println("collection count:"+coll.getCount());
		System.out.println("collection key-value:"+myDoc);


		//query by id
		BasicDBObject query = new BasicDBObject();
		for (int i=0; i < 100; i++) {
			coll.insert(new BasicDBObject().append("i", i));
		}

		DBCursor cur = coll.find();
		cur = coll.find(query);
		//		while(cur.hasNext()) {
		//			System.out.println(cur.next());
		//		}

		query = new BasicDBObject();
		// e.g. find all where i > 50
		query.put("i", new BasicDBObject("$gt", 50));  
		cur = coll.find(query);
		while(cur.hasNext()) {
			System.out.println("query by id:"+cur.next());
		}

		query = new BasicDBObject();
		// i.e.   20 < i <= 30
		query.put("i", new BasicDBObject("$gt", 20).append("$lte", 30));  
		cur = coll.find(query);
		while(cur.hasNext()) {
			System.out.println(cur.next());
		}


		//update
		myDoc.put("name", "MongoDB2");
		myDoc.put("type", "database2");
		coll.save(myDoc);
		//		coll.update(myDoc, myDoc);
		myDoc = coll.findOne();
		System.out.println("finish update collection key-value:"+myDoc);

		//remove
		coll.remove(myDoc);
		System.out.println("remove collection count:"+coll.getCount());


	}
}
