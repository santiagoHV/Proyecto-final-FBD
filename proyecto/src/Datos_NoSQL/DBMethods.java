package Datos_NoSQL;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.sql.SQLException;
import java.util.Collection;

public class DBMethods {
    private MongoDatabase database;

    public DBMethods(){
        this.database = new connectionDB().connect();
    }

    public FindIterable<Document> getUsuarios(){
        return database.getCollection("users").find();
    }

    public void addUsuario(String user,String pass, String role){
        if(role.equals("admin") || role.equals("client") || role.equals("recept")) {
            MongoCollection col = database.getCollection("users");
            try {
                col.insertOne(new Document().append("user", user).append("password", pass).append("role", role));
                System.out.println("Agregado con exito");
            } catch (Exception e) {
                System.out.println("Error inesperado");
            }
        }else{
            System.out.println("Ingrese bien los datos enfermo");
        }
    }
}
