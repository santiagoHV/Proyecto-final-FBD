package Controlador.vistas_recepcionista;

import DatosSQL.DAOs.DAO_Reserva;
import Modelo.entidades.Reserva;
import com.jfoenix.controls.JFXButton;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
    public Label lab_nom_cliente;
    public Label lab_apel_cliente;
    public JFXButton btn_cancelar;
    public MFXProgressSpinner progressReserva;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setValoresCamposCardReserva(Reserva reserva){
        lab_cod_reserva.setText(reserva.getK_reserva()+"");
        lab_estado_reserva.setText(reserva.getEstado());
        lab_fecha_inicio.setText(reserva.getF_inicio().toString());
        lab_fecha_final.setText(reserva.getF_final().toString());
        lab_id_titular.setText(reserva.getPersona().getK_identificacion()+"");
        lab_nom_cliente.setText(reserva.getPersona().getN_nombre());
        lab_apel_cliente.setText(reserva.getPersona().getN_apellido());
        if(reserva.getEstado().equals("cancelada") || reserva.getEstado().equals("vencida") || reserva.getEstado().equals("en curso"))
        {
            btn_cancelar.setDisable(true);
        }
    }

    public void cancelarReservaEv(ActionEvent actionEvent) {
        progressReserva.setVisible(true);
        Task<Reserva> taskCancelarReserva =new Task<Reserva>() {
            @Override
            protected Reserva call() throws Exception {
                DAO_Reserva Dreserva;
                Dreserva = new DAO_Reserva();
                Dreserva.actualizarEstadoDeReservas("cancelada", Integer.parseInt(lab_cod_reserva.getText()));
                return Dreserva.consultarReserva(Integer.parseInt(lab_cod_reserva.getText()));
            }
        };
        taskCancelarReserva.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                progressReserva.setVisible(false);
                setValoresCamposCardReserva(taskCancelarReserva.getValue());
            }
        });

        new Thread(taskCancelarReserva).start();
    }
}
