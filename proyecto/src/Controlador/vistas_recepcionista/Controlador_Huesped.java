package Controlador.vistas_recepcionista;

import Modelo.entidades.Huesped;
import javafx.scene.control.Label;

public class Controlador_Huesped
{

    public Label LBnom_completo;
    public Label LBnum_id;
    public Label LBedad;
    public Label LBDireccion;
    public Label LBTelefono;

    public void setValoresPanel(Huesped huesped)
    {
        LBnom_completo.setText(huesped.getN_nombre() + " " + huesped.getN_apellido());
        LBnum_id.setText(huesped.getK_identificacion()+"");
        LBedad.setText(huesped.getF_nacimiento().toString());
        LBDireccion.setText(huesped.getN_direccion());
        LBTelefono.setText(huesped.getN_telefono());
    }
}
