package Controlador.vistas_recepcionista;

import Modelo.entidades.Habitacion;
import javafx.scene.control.Button;

public class Controlador_Boton_Habitaciones
{

    public Button Btn_Habitacion;

    public void DefinirHabitacion(Habitacion habitacion)
    {
        this.Btn_Habitacion.setText(habitacion.getK_numero_habitacion()+"");
    }
}
