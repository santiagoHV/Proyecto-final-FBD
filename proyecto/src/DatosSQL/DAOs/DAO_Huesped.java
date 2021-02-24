package DatosSQL.DAOs;

import DatosSQL.Conexion;
import DatosSQL.Operaciones;
import Modelo.entidades.Huesped;
import Modelo.entidades.Persona;
import Modelo.entidades.Registro;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DAO_Huesped {

    Operaciones op;
    public DAO_Huesped(){
        this.op = new Operaciones();
    }

    public Huesped consultarHuesped(int ID, String Tipo) {

        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Huesped WHERE k_identificacion = "+ID+" and  k_tipo_documento = '"+Tipo+"'");
            resultSet.next();

            DAO_Persona dao_persona = new DAO_Persona();
            Persona persona = dao_persona.consultarPersona(resultSet.getInt(2),resultSet.getString(3));

            Huesped huesped = new Huesped(persona.getK_identificacion(),persona.getK_tipo_documento_id(),persona.getN_nombre(),
                    persona.getN_apellido(), persona.getF_nacimiento(), persona.getN_telefono(), resultSet.getString(1));

            return huesped;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
    public void insertarHuesped(Huesped huesped){
        try {
            PreparedStatement preparedStatement = Conexion.getInstance().getConnection().prepareStatement(
                    "INSERT INTO huesped VALUES(?,?,?);");

            preparedStatement.setString(1, huesped.getN_direccion());
            preparedStatement.setInt(2, huesped.getK_identificacion());
            preparedStatement.setString(3, huesped.getK_tipo_documento_id());

            System.out.println("insertado con exito");
            preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e + "Error en insercion de huesped");
        }
    }

    public int actualizarHuesped(Huesped huesped) {
        try {
            PreparedStatement preparedStatement = Conexion.getInstance().getConnection().prepareStatement(
                    "UPDATE huesped set n_direccion = ?, k_identificacion = ?, k_tipo_documento = ? " +
                            " WHERE k_identificacion = ?");

            preparedStatement.setString(1, huesped.getN_direccion());
            preparedStatement.setInt(2, huesped.getK_identificacion());
            preparedStatement.setString(3, huesped.getK_tipo_documento_id());
            preparedStatement.setInt(4, huesped.getK_identificacion());

            return preparedStatement.executeUpdate();

        }catch (Exception e){
            System.out.println(e + "En huesped");
        }
        return 0;
    }

    public boolean huespedExiste(int ID, String tipo) {
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Huesped WHERE k_identificacion = "+ID+" and  k_tipo_documento = '"+tipo+"'");
            resultSet.next();
            String direccion = resultSet.getString(1);
            System.out.println("pasa");
            return true;

        }catch (SQLException ex){
            System.out.println(ex + "huesped no encontrado");
            return false;
        }

    }

    public List<Huesped> BuscarHuespedes(int k_reserva, int num_doc, String nom_or_apel) {

        List<Huesped> huespedList = new ArrayList<>();

        Operaciones op = new Operaciones();
        try {
            String Query = "SELECT distinct Huesped.*, Persona.n_nombre, Persona.n_apellido, Persona.n_telefono, Persona.f_nacimiento " +
                    " FROM Huesped, Persona, registro_checkin WHERE (Huesped.k_identificacion = Persona.k_identificacion and Huesped.k_tipo_documento = Persona.k_tipo_documento and Registro_CheckIn.k_identificacion = Huesped.k_identificacion and Registro_CheckIn.k_tipo_documento = Huesped.k_tipo_documento) " +
                    " and (Huesped.k_identificacion = " + num_doc + " or Persona.n_nombre ILIKE '" + nom_or_apel + "%' or Persona.n_apellido ILIKE '" + nom_or_apel + "%'" +
                    " or registro_checkin.k_reserva="+k_reserva+")";

            if (nom_or_apel.equals("")) {
                Query = Query.replace("%","");
            }


            if(nom_or_apel.equals("") && k_reserva==0 && num_doc==0)
            {
                Query = "SELECT distinct Huesped.*, Persona.n_nombre, Persona.n_apellido, Persona.n_telefono, Persona.f_nacimiento " +
                        " FROM Huesped, Persona WHERE (Huesped.k_identificacion = Persona.k_identificacion and Huesped.k_tipo_documento = Persona.k_tipo_documento)";
            }
            ResultSet resultSet = op.ConsultaEsp(Query);

            Huesped Huesped = null;

            while (resultSet.next()) {

                Huesped = new Huesped(resultSet.getInt(2),resultSet.getString(3),resultSet.getString(4),
                        resultSet.getString(5),resultSet.getDate(7),resultSet.getString(6),resultSet.getString(1));
                huespedList.add(Huesped);
            }
            return huespedList;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
