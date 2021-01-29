package Datos_NoSQL;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;
import java.util.ArrayList;

public class DBMethods {
    private MongoDatabase database;

    public DBMethods(){
        this.database = new connectionDB().connect();
    }

    public void verUsuarios(){
        FindIterable<Document> users = database.getCollection("users").find();
        System.out.println("|| <Usuario> : <Contraseña> : <Rol>||");
        System.out.println("--------------------------------------");
        for(Document user: users){
            System.out.println(user.get("user") + " : " + user.get("password") + " : " + user.get("role"));
        }
    }

    public void addUsuario(String user,String pass, String role){
        MongoCollection col = database.getCollection("users");
        boolean existe = false;
        Document repetido = (Document) col.find(new Document().append("user", user)).first();
        if(repetido != null){
            System.out.println("Este usuario ya existe");
            existe = true;
        }
        if(!existe) {
            if (role.equals("admin") || role.equals("gerente") || role.equals("recept") || role.equals("worker")) {
                try {
                    col.insertOne(new Document().append("user", user).append("password", pass).append("role", role));
                    System.out.println("Agregado con exito");
                } catch (Exception e) {
                    System.out.println("Error inesperado");
                }
            } else {
                System.out.println("Ingrese bien los datos enfermo");
            }
        }
    }
    public String autenticarUsuario(String user, String pass){
        MongoCollection col = database.getCollection("users");
        Document usuario = (Document) col.find(new Document().append("user", user)).first();
        if(usuario != null) {
            if (usuario.get("password").equals(pass)) {
                return "auth";
            } else {
                return "wrong_pass";
            }
        }else{
            return "not_exist";
        }
    }

    public ArrayList<Document> getUsuarios(){
        MongoCollection col = database.getCollection("users");
        ArrayList<Document> usersArray = new ArrayList<>();
        FindIterable<Document> users = col.find();
        for(Document user: users){
            usersArray.add(user);
        }
        return usersArray;
    }
    public ArrayList<Document> getUsuarios(String role){
        MongoCollection col = database.getCollection("users");
        ArrayList<Document> usersArray = new ArrayList<>();
        FindIterable<Document> users = col.find(new Document().append("role", role));
        for(Document user: users){
            usersArray.add(user);
        }
        return usersArray;
    }

    public Document searchUser(String user){
        MongoCollection col = database.getCollection("users");
        return (Document) col.find(new Document().append("user", user)).first();
    }

}
