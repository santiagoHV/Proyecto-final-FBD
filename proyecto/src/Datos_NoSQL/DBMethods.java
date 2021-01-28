package Datos_NoSQL;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.print.Doc;

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
    public boolean autenticarUsuario(String user, String pass){
        boolean auth = false;
        MongoCollection col = database.getCollection("users");
        Document usuario = (Document) col.find(new Document().append("user", user)).first();
        if(usuario != null) {
            if (usuario.get("password").equals(pass)) {
                auth = true;
            } else {
                System.out.println("Contraseña incorrecta");
            }
        }else{
            System.out.println("Este usuario no existe");
        }
        return auth;
    }
}
