package Datos_NoSQL;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class ConnectionDB {
    public MongoDatabase connect(){
        ConnectionString connString = new ConnectionString(
                "mongodb+srv://root:root@cluster0.l7diy.mongodb.net/hotel?retryWrites=true&w=majority"
        );
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("hotel");
        return database;
    }
}
