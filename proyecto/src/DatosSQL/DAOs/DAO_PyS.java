package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.PyS;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO_PyS {

    public PyS consultarPyS(int ID){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM productoyservicio WHERE k_codigo_pys = "+ID+"");
            resultSet.next();

            PyS pys = new PyS(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4));

            return pys;


        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
