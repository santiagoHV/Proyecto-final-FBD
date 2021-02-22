package Controlador.vistas_recepcionista;

import com.jfoenix.svg.SVGGlyph;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
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

    public AnchorPane anchorBG;
    public Pane panel_titular;
    private SVGGlyph information = new SVGGlyph(0,"information",
            "M12,2A10,10,0,1,0,22,12,10.01114,10.01114,0,0,0,12,2Zm0,18a8,8,0,1,1,8-8A8.00917,8.00917,0,0,1,12,20Zm0-8.5a1,1,0,0,0-1,1v3a1,1,0,0,0,2,0v-3A1,1,0,0,0,12,11.5Zm0-4a1.25,1.25,0,1,0,1.25,1.25A1.25,1.25,0,0,0,12,7.5Z"
            , Color.DARKGRAY);

    private SVGGlyph balance = new SVGGlyph(1,"balance",
            "M22.96423,13.82263a.94762.94762,0,0,0-.02819-.17419L20.63135,7.51135A2.99558,2.99558,0,0,0,22,5a1,1,0,0,0-2,0,1.00037,1.00037,0,0,1-1.88184.47266A2.8934,2.8934,0,0,0,15.54,4H13V3a1,1,0,0,0-2,0V4H8.46A2.8934,2.8934,0,0,0,5.88184,5.47266,1.00037,1.00037,0,0,1,4,5,1,1,0,0,0,2,5,2.99558,2.99558,0,0,0,3.36865,7.51135L1.064,13.64844a.94762.94762,0,0,0-.02819.17419A.94855.94855,0,0,0,1,14c0,.00928.00269.01782.00275.0271.0003.01318.003.02533.0039.03845a3.99379,3.99379,0,0,0,7.9867,0c.00085-.01312.0036-.02527.0039-.03845C8.99731,14.01782,9,14.00928,9,14a.94855.94855,0,0,0-.03577-.17737.94762.94762,0,0,0-.02819-.17419L6.62866,7.50421A2.98961,2.98961,0,0,0,7.64258,6.41992.917.917,0,0,1,8.46,6H11V20H8a1,1,0,0,0,0,2h8a1,1,0,0,0,0-2H13V6h2.54a.917.917,0,0,1,.81738.41992,2.98961,2.98961,0,0,0,1.01392,1.08429L15.064,13.64844a.94762.94762,0,0,0-.02819.17419A.94855.94855,0,0,0,15,14c0,.00928.00269.01782.00275.0271.0003.01318.003.02533.0039.03845a3.99379,3.99379,0,0,0,7.9867,0c.00085-.01312.0036-.02527.0039-.03845C22.99731,14.01782,23,14.00928,23,14A.94855.94855,0,0,0,22.96423,13.82263ZM5,8.85553,6.5564,13H3.4436ZM6.72266,15A2.02306,2.02306,0,0,1,5,16a2.00023,2.00023,0,0,1-1.73145-1ZM19,8.85553,20.5564,13H17.4436ZM19,16a2.00023,2.00023,0,0,1-1.73145-1h3.45411A2.02306,2.02306,0,0,1,19,16Z"
            , Color.DARKGRAY);

    private javafx.scene.control.Label promptTitular = new Label("Aquí se mostrará la información del titular de la reserva");
    private javafx.scene.control.Label promptPagos = new Label("Aquí se mostrarán los datos relacionados con los pagos de la reserva");

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
        //prueba por errores de saldamigo

        information.setLayoutX(248);
        information.setLayoutY(278-10);
        information.setPrefHeight(60);
        information.setPrefWidth(60);
        information.setMaxHeight(60);
        information.setMaxWidth(60);
        anchorBG.getChildren().add(information);

        balance.setLayoutX(248);
        balance.setLayoutY(278+180);
        balance.setPrefHeight(60);
        balance.setPrefWidth(60);
        balance.setMaxHeight(60);
        balance.setMaxWidth(60);
        anchorBG.getChildren().add(balance);

        promptTitular.setPrefHeight(70);
        promptTitular.setPrefWidth(480);
        promptTitular.setTextAlignment(TextAlignment.CENTER);
        promptTitular.setTextFill(Paint.valueOf("#575757"));
        promptTitular.setFont(Font.font("MAXWELL REGULAR",25));
        promptTitular.setAlignment(Pos.CENTER);
        promptTitular.setWrapText(true);
        promptTitular.setLayoutX(30);
        promptTitular.setLayoutY(334-10);
        promptTitular.setVisible(true);

        anchorBG.getChildren().add(promptTitular);

        promptPagos.setPrefHeight(70);
        promptPagos.setPrefWidth(480);
        promptPagos.setTextAlignment(TextAlignment.CENTER);
        promptPagos.setTextFill(Paint.valueOf("#575757"));
        promptPagos.setFont(Font.font("MAXWELL REGULAR",25));
        promptPagos.setAlignment(Pos.CENTER);
        promptPagos.setWrapText(true);
        promptPagos.setLayoutX(30);
        promptPagos.setLayoutY(334+180);
        promptPagos.setVisible(true);

        anchorBG.getChildren().add(promptPagos);

        information.setVisible(true);

        for(Node n: panel_titular.getChildren())
        {
            n.setVisible(false);
        }
    }

    public void ClickBuscar(ActionEvent actionEvent) {
    }

    public void btn_limpiar(ActionEvent actionEvent) {}

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
