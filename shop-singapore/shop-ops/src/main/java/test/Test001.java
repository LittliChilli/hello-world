package test;

import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class Test001 {
	
	 @SuppressWarnings("resource")
		public static void main( String args[] ){
		 
		 int numbercode = (int) ((Math.random() * 9000) + 1000);
		      try{   
		       // 连接到 mongodb 服务
		         MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
		       
		         // 连接到数据库
		         MongoDatabase mongoDatabase = mongoClient.getDatabase("test");  
		         MongoIterable<String> names = mongoDatabase.listCollectionNames();
		         MongoCursor<String> iterator2 = names.iterator();
		         while(iterator2.hasNext()){
		        	 System.out.println(iterator2.next());
		         }
		         
		         MongoCollection<Document> collection = mongoDatabase.getCollection("test");
		         FindIterable<Document> ite = collection.find();
		         MongoCursor<Document> iterator = ite.iterator();
		         while(iterator.hasNext()){
		        	 Document doc = iterator.next();
		        	 System.out.println(doc.toJson());
		        	 
		        	 Set<Entry<String, Object>> entrySet = doc.entrySet();
		        	 Iterator<Entry<String,Object>> ite1 = entrySet.iterator();
		        	 while(ite1.hasNext()){
		        		 Entry<String,Object> entry = ite1.next();
		        		 System.out.println(entry.getKey());
		        		 System.out.println(entry.getValue());
		        	 }
		         }
		         
		       System.out.println("Connect to database successfully");

		       
		      }catch(Exception e){
		        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
		     }
		   }
	 
	}
