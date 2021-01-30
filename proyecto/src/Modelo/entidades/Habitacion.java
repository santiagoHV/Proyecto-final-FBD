package Modelo.entidades;

import DatosSQL.Operaciones;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Habitacion
{
    private int k_numero_habitacion;
    private Tipo tipo_habitacion;

    public Habitacion(){
    }

    public Habitacion(int k_numero_habitacion, Tipo tipo_habitacion) {
        this.k_numero_habitacion = k_numero_habitacion;
        this.tipo_habitacion = tipo_habitacion;
    }

    public int getK_numero_habitacion() {
        return k_numero_habitacion;
    }

    public Tipo getTipo_habitacion() {
        return tipo_habitacion;
    }

    public void setTipo_habitacion(Tipo tipo_habitacion) {
        this.tipo_habitacion = tipo_habitacion;
    }

    public void setK_numero_habitacion(int k_numero_habitacion) {
        this.k_numero_habitacion = k_numero_habitacion;
    }

    public Habitacion ConsultarHabitacion(int ID, Habitacion habitacion){
        Operaciones op = new Operaciones();
        try {
            ResultSet resultSet = op.ConsultaEsp("SELECT * FROM Habitacion WHERE k_numero_habitacion = "+ID+"");
            resultSet.next();

            habitacion.setK_numero_habitacion(resultSet.getInt(1));

            Tipo tipo = new Tipo();
            habitacion.setTipo_habitacion(tipo.ConsultarTipoHab(resultSet.getInt(2),tipo));

            return habitacion;

        }catch (SQLException ex){
            System.out.println(ex);
        }
        return null;
    }
}
