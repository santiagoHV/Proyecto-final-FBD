package Controlador.vistas_recepcionista;

import Modelo.entidades.Huesped;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

public class Controlador_Huesped
{

    public Label LBnom_completo;
    public Label LBnum_id;
    public Label LBedad;
    public Label LBDireccion;
    public Label LBTelefono;
    public AnchorPane AnchorBG;

    public void setValoresPanel(Huesped huesped)
    {
        LBnom_completo.setText(huesped.getN_nombre() + " " + huesped.getN_apellido());
        LBnum_id.setText(huesped.getK_identificacion()+"");
        LBedad.setText(huesped.getF_nacimiento().toString());
        LBDireccion.setText(huesped.getN_direccion());
        LBTelefono.setText(huesped.getN_telefono());
    }

    public void setValoresTemporales(String tipo)
    {
        LBnom_completo.setText("Por Asignar");
        LBnum_id.setText("Por Asignar");
        LBedad.setText("Por Asignar");
        LBDireccion.setText("Por Asignar");
        LBTelefono.setText("Por Asignar");

        if(tipo=="Bebe")
        {
            AnchorBG.getStyleClass().add("CardBebe");
        }
        else if(tipo=="Nino")
        {
            AnchorBG.getStyleClass().add("CardNinos");
        }
        else if (tipo=="Adulto")
        {
            AnchorBG.getStyleClass().add("CardAdultos");
        }
    }
}
