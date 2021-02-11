package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.Tipo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO_Tipo {
    Operaciones op;

    public DAO_Tipo(){
        op = new Operaciones();
    }

    public Tipo consultarTipoHab(String ID){

        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM tipo WHERE k_tipo_habitacion = '"+ID+"'");
            resultSet.next();

            Tipo tipo = new Tipo(resultSet.getString(1),resultSet.getDouble(2),resultSet.getString(3));

            return tipo;

        }catch (SQLException ex){
            System.out.println(ex + "Tipo");
        }
        return null;
    }

    public ArrayList<Tipo> consultarTipos(){
        ArrayList<Tipo> tipos = new ArrayList<Tipo>();

        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM tipo");
            resultSet.next();

            while (resultSet.next()){
                tipos.add(new Tipo(resultSet.getString(1),resultSet.getDouble(2),resultSet.getString(3)));
            }

        }catch (SQLException ex){
            System.out.println(ex + "Tipo");
        }
        return tipos;
    }
}
