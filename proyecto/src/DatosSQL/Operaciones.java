package DatosSQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Operaciones {

    public ResultSet ConsultaEsp(String Query) throws SQLException {

        Conexion conexion = Conexion.getInstance();
        Statement stmt = conexion.getConnection().createStatement();
        ResultSet rs = stmt.executeQuery(Query);
        return rs;
    }
    public void UpdateEsp(String Update) throws SQLException {
        Conexion conexion = Conexion.getInstance();
        Statement stmt = conexion.getConnection().createStatement();
        stmt.executeUpdate(Update);
    }
}