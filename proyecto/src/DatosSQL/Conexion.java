package DatosSQL;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private static Conexion instancia;
    private static Connection connection;

    private Conexion(){
        try
        {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection("jdbc:postgresql://ec2-34-192-72-159.compute-1.amazonaws.com:5432/ddfjgbh9r2i8hk", "feiotjerudjgqs", "8a5160a6d7312ac99d078d65348a6840d6b97f06acf2b9b6238668dc926d00ce");
        }catch (ClassNotFoundException | SQLException ex){
            System.out.println("Error al conectar con la base de datos");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public static Conexion getInstance() throws SQLException{
        if(instancia == null) {
            instancia = new Conexion();
        } else if(instancia.getConnection().isClosed()) {
            instancia = new Conexion();
        }
        return instancia;
    }
}
