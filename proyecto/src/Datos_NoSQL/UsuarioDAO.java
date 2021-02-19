package Datos_NoSQL;

import Modelo.entidades.Persona;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;

public class UsuarioDAO {
    private MongoDatabase database;

    public UsuarioDAO(){
        this.database = new ConnectionDB().connect();
    }
    /**
     * Muestra todos los usuarios registrados por consola
     */
    public void verUsuarios(){
        FindIterable<Document> users = database.getCollection("users").find();
        System.out.println("|| <Usuario> : <Contraseña> : <Rol>||");
        System.out.println("--------------------------------------");
        for(Document user: users){
            System.out.println(user.get("user") + " : " + user.get("password") + " : " + user.get("role"));
        }
    }

    /**
     * Recibe un usuario y lo agrega a la conexion de usuarios de la base de datos,
     * y muestra por consola el resultado de la adición
     * @param usuario
     */
    public boolean addUsuario(Usuario usuario){
        MongoCollection col = database.getCollection("users");
        boolean existe = false;
        Document repetido = (Document) col.find(new Document().append("user", usuario.getUser())).first();
        if(repetido != null){
            System.out.println("Este usuario ya existe");
            existe = true;
            return false;
        }
        if(!existe) {
            if (usuario.getRole().equals("admin") || usuario.getRole().equals("gerente") || usuario.getRole().equals("recept") || usuario.getRole().equals("worker")) {
                try {
                    col.insertOne(new Document().append("user", usuario.getUser()).append("password", usuario.getPassword()).append("role", usuario.getRole()));
                    System.out.println("Agregado con exito");
                    return true;
                } catch (Exception e) {
                    System.out.println("Error inesperado");
                    return false;
                }
            } else {
                System.out.println("Ingrese bien los datos");
                return false;
            }
        }
        else{
            return false;
        }
    }

    /**
     * Recibe un objeto Usuario y devuelve un String que informa del estado
     * de la autenticación
     * @param usuario
     * @return
     */
    public String autenticarUsuario(Usuario usuario){
        MongoCollection col = database.getCollection("users");
        Document usuarioBase = (Document) col.find(new Document().append("user", usuario.getUser())).first();
        if(usuarioBase != null) {
            if (usuarioBase.get("password").equals(usuario.getPassword())) {
                if(usuarioBase.get("role").equals(usuario.getRole()) || usuarioBase.get("role").equals("admin")){
                    return "auth";
                }else{
                    return "no_permission";
                }
            } else {
                return "wrong_pass";
            }
        }else{
            return "not_exist";
        }
    }

    /**
     * Retorna todos los usuarios en un array de usuarios
     * @return
     */
    public ArrayList<Usuario> getUsuarios(){
        MongoCollection col = database.getCollection("users");
        ArrayList<Usuario> usersArray = new ArrayList<>();
        FindIterable<Document> users = col.find();
        for(Document user: users){
            usersArray.add(new Usuario((String)user.get("user"),(String)user.get("password"),(String)user.get("role")));
        }
        return usersArray;
    }

    /**
     * Retorna todos los usuarios de un determinado rol
     * dentro de un array de usuarios
     * @param role
     * @return
     */
    public ArrayList<Usuario> getUsuarios(String role){
        MongoCollection col = database.getCollection("users");
        ArrayList<Usuario> usersArray = new ArrayList<>();
        FindIterable<Document> users = col.find(new Document().append("role", role));
        for(Document user: users){
            usersArray.add(new Usuario((String)user.get("user"),(String)user.get("password"),(String)user.get("role")));
        }
        return usersArray;
    }

    /**
     * Busca los datos de un usuario en especifico y retorna un
     * objeto Usuario
     * @param user
     * @return
     */
    public Usuario searchUser(String user){
        MongoCollection col = database.getCollection("users");
        Document userx = (Document) col.find(new Document().append("user", user)).first();
        return new Usuario((String) userx.get("user"),(String) userx.get("password"), (String) userx.get("role"));
    }

    public boolean dropUser(Usuario user){
        MongoCollection col = database.getCollection("users");
        try{
            col.findOneAndDelete(new Document().append("user", user.getUser()).append("password", user.getPassword()).append("role", user.getRole()));
            return true;
        }catch (Exception e){
            return false;
        }
    }

}