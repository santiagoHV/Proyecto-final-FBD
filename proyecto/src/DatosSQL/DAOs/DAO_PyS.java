package DatosSQL.DAOs;

import DatosSQL.Operaciones;
import Modelo.entidades.PyS;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO_PyS {

    public PyS consultarPyS(int ID) throws SQLException {
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM productoyservicio WHERE k_codigo_pys = "+ID+"");
            resultSet.next();

            PyS pys = new PyS(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4), resultSet.getString(5));

            return pys;


        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }

    public ArrayList<PyS> consultarPorCategoria(String categoriax){
        Operaciones op = new Operaciones();
        ArrayList<PyS> pys_array = new ArrayList<>();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM productoyservicio WHERE n_categoria = '"+ categoriax+"'");
            while (resultSet.next()){
                pys_array.add(new PyS(resultSet.getInt(1),resultSet.getString(2),resultSet.getInt(3),resultSet.getDouble(4), resultSet.getString(5)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return  pys_array;
    }

    public boolean modificarPrecio(String id,String precio){
        Operaciones op = new Operaciones();
        try{
            op.UpdateEsp("UPDATE productoyservicio SET v_precio_producto = "+ precio + " WHERE k_codigo_pys ="+ id );
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }
    public boolean modificarStock(String id,String monto){
        Operaciones op = new Operaciones();
        try{
            op.UpdateEsp("UPDATE productoyservicio SET q_stock = "+ monto + " WHERE k_codigo_pys ="+ id );
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean agregarProducto(String nombre, String stock, String precio, String categoria){
        Operaciones op = new Operaciones();
        try {
            op.UpdateEsp("INSERT INTO productoyservicio VALUES ("+(Integer.parseInt(getMaxID())+1)+", '"+nombre+"',"+stock+", "+precio+", '"+categoria+"')");
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            return false;
        }
    }

    private String getMaxID(){
        Operaciones op = new Operaciones();
        try {
            ResultSet res = op.ConsultaEsp("SELECT MAX(k_codigo_pys) FROM productoyservicio");
            res.next();
            return res.getString(1);
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

}
