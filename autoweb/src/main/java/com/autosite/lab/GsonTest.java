package com.autosite.lab;

import com.autosite.db.ChurExpense;
import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

public class GsonTest {

    public static void main(String[] args) throws Exception {
        

        /**** Connect to MongoDB ****/
        // Since 2.10.0, uses MongoClient
        MongoClient mongo = new MongoClient("localhost", 27017);

        /**** Get database ****/
        // if database doesn't exists, MongoDB will create it for you
        DB db = mongo.getDB("testdb");

        Gson gson = new Gson();
        
        ChurExpense ex = new ChurExpense();
        
        ex.setId(System.currentTimeMillis());
        
        /**** Get collection / table from 'testdb' ****/
        // if collection doesn't exists, MongoDB will create it for you
        DBCollection table = db.getCollection("ex");

        
        System.out.println( gson.toJson(ex));
        
        /**** Insert ****/
        // create a document to store key and value
        BasicDBObject document = new BasicDBObject();
        document.put("id", ex.getId());
        document.put("object", (DBObject)JSON.parse(gson.toJson(ex)));
        table.insert(document);
        
        
        /**** Find and display ****/
        BasicDBObject searchQuery = new BasicDBObject();
        searchQuery.put("id", "*");

        DBCursor cursor = table.find();

        while (cursor.hasNext()) {
            System.out.println(cursor.next());
        }
        
        
        
    }
}
