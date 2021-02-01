package Controlador.vistas_recepcionista;

import Modelo.entidades.Reserva;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_Card_Reserva implements Initializable {

    public Label lab_fecha_inicio;
    public Label lab_estado_reserva;
    public Label lab_cod_reserva;
    public Label lab_fecha_final;
    public Label lab_id_titular;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setValoresCamposCardReserva(Reserva reserva){
        lab_cod_reserva.setText(reserva.getK_reserva()+"");
        lab_estado_reserva.setText(reserva.getEstado());
        lab_fecha_inicio.setText(reserva.getF_inicio().toString());
        lab_fecha_final.setText(reserva.getF_final().toString());
        lab_id_titular.setText(reserva.getPersona().getK_identificacion()+"");
    }
}
