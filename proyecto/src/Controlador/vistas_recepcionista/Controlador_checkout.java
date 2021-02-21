package Controlador.vistas_recepcionista;

import DatosSQL.DAOs.DAO_Cuenta;
import DatosSQL.DAOs.DAO_Cuenta_Productos;
import DatosSQL.DAOs.DAO_Reserva;
import Modelo.entidades.Reserva;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ResourceBundle;

public class Controlador_checkout implements Initializable {

    //busqueda
    public MFXProgressSpinner progressIndCheckout;
    public TextField codigo_reserva;
    public Button btn_buscar_reserva;

    public ScrollPane panel_salida_huespedes;
    public GridPane GridPanel_Huespedes;
    public Button btn_finalizar;

    //datos
    public Label datos_nombre;
    public Label datos_ti;
    public Label datos_no_i;
    public Label datos_tel;
    public Label datos_edad;
    //precio
    public Label valor_estadia;
    public Label valor_consumos;
    public Label valor_descuentos;
    public Label valor_total;
    //buttons
    public Button btn_ver_consumos;
    public Button btn_procesar_pago;
    public Button btn_finalizar_proceso;
    public ScrollPane panel_ingreso_habitaciones;
    public GridPane GridPanel_Btn_Habitaciones;

    public Reserva reserva;
    public int codigoReserva;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void buscar_Reserva_Por_ID(ActionEvent actionEvent) {

        DAO_Reserva dao_reserva = new DAO_Reserva();
        DAO_Cuenta dao_cuenta = new DAO_Cuenta();
        DAO_Cuenta_Productos dao_cuenta_productos = new DAO_Cuenta_Productos();

        progressIndCheckout.setVisible(true);

        Task<Reserva> reservaTask = new Task<Reserva>() {
            @Override
            protected Reserva call() throws Exception {
                return dao_reserva.consultarReserva(codigoReserva);
            }
        };

        Thread threadReserva = new Thread(reservaTask);

        reservaTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                reserva = reservaTask.getValue();
                progressIndCheckout.setVisible(false);
                llenarDatosReserva();
            }
        });

        try{
            codigoReserva = Integer.parseInt(codigo_reserva.getText());
            threadReserva.start();
        }catch (Exception e){
            //poner error en busqueda
        }
    }

    public void llenarDatosReserva(){

        this.datos_nombre.setText(this.reserva.getPersona().getN_nombre() + " " + this.reserva.getPersona().getN_apellido());
        this.datos_edad.setText(String.valueOf(Period.between(this.reserva.getPersona().getF_nacimiento().toLocalDate(), LocalDate.now()).getYears()));
        this.datos_tel.setText(this.reserva.getPersona().getN_telefono());
        this.datos_ti.setText(this.reserva.getPersona().getK_tipo_documento_id());
        this.datos_no_i.setText(String.valueOf(this.reserva.getPersona().getK_identificacion()));




    }
}
