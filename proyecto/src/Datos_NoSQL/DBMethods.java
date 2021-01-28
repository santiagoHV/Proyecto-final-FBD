package Datos_NoSQL;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DBMethods {
    private MongoDatabase database;

    public DBMethods(){
        this.database = new connectionDB().connect();
    }

    public FindIterable<Document> getUsuarios(){
        return database.getCollection("users").find();
    }
}
