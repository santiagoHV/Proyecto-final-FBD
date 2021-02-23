package Controlador.vistas_recepcionista;

import Controlador.Controlador_alerta;
import DatosSQL.DAOs.*;
import Modelo.entidades.*;
import Vista.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.svg.SVGGlyph;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controlador_checkout implements Initializable {

    public AnchorPane anchorBG;
    public Pane panel_titular;
    public Pane panel_pagos;
    public StackPane stackBG;
    public Label lb_alert_pago;
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
    public Cuenta cuenta;
    public int codigoDePago;
    public int codigoReserva;
    public double totalPago;
    public double totalConsumos;

    private Pago pagoCuenta;

    private List<Habitacion> habitacionList = new ArrayList<>();

    private List<Registro> registroList = new ArrayList<>();

    private List<Integer> huespedIDList = new ArrayList<>();

    private Persona personaConsultada;

    private Controlador_alerta controlador_alerta;
    private JFXDialog dialogAlerta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Uso del método smoothScrolling para suavizar la animación de desplazamiento de los scrollPane:
        JFXScrollPane Prueba = new JFXScrollPane();
        Prueba.smoothScrolling(panel_salida_huespedes);

        information.setLayoutX(248);
        information.setLayoutY(278-10);
        information.setPrefHeight(60);
        information.setPrefWidth(60);
        information.setMaxHeight(60);
        information.setMaxWidth(60);
        anchorBG.getChildren().add(information);

        balance.setLayoutX(248);
        balance.setLayoutY(278+200);
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
        promptPagos.setLayoutY(334+200);
        promptPagos.setVisible(true);

        anchorBG.getChildren().add(promptPagos);

        information.setVisible(true);

        for(Node n: panel_titular.getChildren())
        {
            n.setVisible(false);
        }

        for(Node n: panel_pagos.getChildren())
        {
            n.setVisible(false);
        }

        FXMLLoader alertaDireccion = new FXMLLoader(getClass().getResource("../../Vista/recepcionista/alerta.fxml"));
        Parent contenedor = null;
        try {
            contenedor = alertaDireccion.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        dialogAlerta = new JFXDialog(stackBG, (Region) contenedor, JFXDialog.DialogTransition.BOTTOM, true);
        controlador_alerta = alertaDireccion.getController();

        AnchorPane alertaAP = (AnchorPane) contenedor.getChildrenUnmodifiable().get(0);
        JFXButton btn_aceptar = (JFXButton) alertaAP.getChildren().get(2);

        btn_aceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventAceptar)->
        {
            dialogAlerta.close();
        });
    }

    public void btn_limpiar(ActionEvent actionEvent) {}

    public void buscar_Reserva_Por_ID(ActionEvent actionEvent) {

        DAO_Reserva dao_reserva = new DAO_Reserva();
        DAO_Cuenta dao_cuenta = new DAO_Cuenta();
        DAO_Cuenta_Productos dao_cuenta_productos = new DAO_Cuenta_Productos();

        progressIndCheckout.setVisible(true);

        Task<Double> taskTotalConsumos = new Task<Double>() {
            @Override
            protected Double call() throws Exception {
                return dao_cuenta_productos.consultarTotalDeConsumosPorReserva(cuenta.getK_cuenta());
            }
        };
        Task<List<Reserva_Habitacion>> reservaTask = new Task<List<Reserva_Habitacion>>() {
            @Override
            protected List<Reserva_Habitacion> call() throws Exception {
                return dao_reserva.consultarReservaHabPorIdReserva(codigoReserva);
            }
        };

        Task<Cuenta> cuentaTask = new Task<Cuenta>() {
            @Override
            protected Cuenta call() throws Exception {
                return dao_cuenta.consultarCuentaPorReserva(codigoReserva);
            }
        };

        Thread threadReserva = new Thread(reservaTask);
        Thread threadCuenta = new Thread(cuentaTask);
        Thread threadConsumos = new Thread(taskTotalConsumos);

        reservaTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {

                if(reservaTask.getValue().size()!=0)
                {
                    reserva = reservaTask.getValue().get(0).getReserva();
                    for(int i = 0; i<reservaTask.getValue().size();i++)
                    {
                        habitacionList.add(reservaTask.getValue().get(i).getHabitacion());
                    }

                    obtener_huespedes();
                    threadCuenta.start();
                }
                else
                {
                    progressIndCheckout.setVisible(false);
                    controlador_alerta.titulo.setText("Reserva no encontrada");
                    controlador_alerta.mensaje.setText("El código de la reserva ingresado no se encuentra registrado o no se encuentra en curso, por favor revise la información revisada");
                    dialogAlerta.show();
                }
            }
        });

        cuentaTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                cuenta = cuentaTask.getValue();

                threadConsumos.start();
            }
        });

        taskTotalConsumos.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                totalConsumos = taskTotalConsumos.getValue();
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

    public void obtener_huespedes() {
        DAO_Registro dao_registro = new DAO_Registro();

        Task<List<Registro>> taskConRegistros = new Task<List<Registro>>() {
            @Override
            protected List<Registro> call() throws Exception {
                return dao_registro.consultarRegistroPorReserva(codigoReserva);
            }
        };

        Thread threadRegistro = new Thread(taskConRegistros);
        threadRegistro.start();

        taskConRegistros.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                registroList = taskConRegistros.getValue();
                GridPanel_Huespedes.getChildren().clear();
                huespedIDList.clear();
                pagoCuenta = new DAO_Pago().consultarPagoPorCuenta(cuenta.getK_cuenta());

                int column = 0;
                int row = 0;

                try
                {
                    for(int i = 0; i<registroList.size(); i++) {
                        final int j = i;
                        if(registroList.get(i).getF_salida()==null)
                        {
                            huespedIDList.add(registroList.get(i).getHuesped().getK_identificacion());

                            //Carga de las plantillas para los paneles con información de los huespedes
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(Main.class.getResource("../Vista/recepcionista/Panel_Huesped.fxml"));

                            //Definición de los anchorpane:
                            //Para el panel de los huespedes:
                            AnchorPane PanelHuespedes = loader.load();

                            Controlador_Huesped controlador_huesped = loader.getController();

                            controlador_huesped.setValoresPanel(registroList.get(i).getHuesped(), habitacionList);

                            huespedIDList.set(i,registroList.get(i).getHuesped().getK_identificacion());

                            //La parte concerniente al proceso de Checkout:

                            controlador_huesped.btn_ingreso.setText("Dar Salida");
                            controlador_huesped.comboHabitacion.setDisable(true);

                            VBox vBox = (VBox) controlador_huesped.AnchorBG.getChildren().get(10);
                            vBox.getChildren().remove(0);
                            lb_alert_pago.setVisible(true);

                            if(pagoCuenta==null)
                            {
                                controlador_huesped.btn_ingreso.setDisable(true);
                                lb_alert_pago.getStyleClass().set(1,"label_pago_alert");
                                btn_procesar_pago.setDisable(false);
                            }
                            else
                            {
                                controlador_huesped.btn_ingreso.setDisable(false);
                                lb_alert_pago.getStyleClass().set(1,"label_pago_alert_green");
                                lb_alert_pago.setText("El pago para esta cuenta ya ha sido realizado");
                                btn_procesar_pago.setDisable(true);
                            }

                            controlador_huesped.btn_ingreso.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent actualizarReg)->
                            {
                                controlador_huesped.progressActIng.setVisible(true);
                                Task<Registro> crearRegistroTask = crearRegistro(controlador_huesped, codigoReserva, registroList.get(j).getK_registro());

                                crearRegistroTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                    @Override
                                    public void handle(WorkerStateEvent workerStateEvent) {
                                        realizarCheckout(crearRegistroTask);
                                        controlador_huesped.progressActIng.setVisible(false);
                                    }
                                });

                                Thread crearRegistroThread = new Thread(crearRegistroTask);
                                crearRegistroThread.start();
                            });

                            row++;
                            GridPanel_Huespedes.add(PanelHuespedes,column,row);
                            GridPane.setMargin(PanelHuespedes,new Insets(8));

                            //Alto y Ancho:
                            //Ancho:
                            GridPanel_Huespedes.setMaxWidth(Region.USE_COMPUTED_SIZE);
                            GridPanel_Huespedes.setPrefWidth(Region.USE_COMPUTED_SIZE);
                            GridPanel_Huespedes.setMinWidth(Region.USE_COMPUTED_SIZE);

                            //Alto:
                            GridPanel_Huespedes.setMaxHeight(Region.USE_COMPUTED_SIZE);
                            GridPanel_Huespedes.setPrefHeight(Region.USE_COMPUTED_SIZE);
                            GridPanel_Huespedes.setMinHeight(Region.USE_COMPUTED_SIZE);
                        }
                    }
                    progressIndCheckout.setVisible(false);
                    GridPanel_Huespedes.setDisable(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Llena los datos de la reserva
     */
    public void llenarDatosReserva(){

        for(Node n: panel_titular.getChildren()) {
            n.setVisible(true);
        }
        for(Node n: panel_pagos.getChildren()){
            n.setVisible(true);
        }

        information.setVisible(false);
        promptPagos.setVisible(false);
        promptTitular.setVisible(false);
        balance.setVisible(false);

        int numNoches = Period.between(this.reserva.getF_inicio().toLocalDate(), this.reserva.getF_final().toLocalDate()).getDays();

        this.datos_nombre.setText(this.reserva.getPersona().getN_nombre() + " " + this.reserva.getPersona().getN_apellido());
        this.datos_edad.setText(String.valueOf(Period.between(this.reserva.getPersona().getF_nacimiento().toLocalDate(), LocalDate.now()).getYears()));
        this.datos_tel.setText(this.reserva.getPersona().getN_telefono());
        this.datos_ti.setText(this.reserva.getPersona().getK_tipo_documento_id());
        this.datos_no_i.setText(String.valueOf(this.reserva.getPersona().getK_identificacion()));

        this.valor_estadia.setText(String.format("$ %(,.2f",this.reserva.getPrecio_reserva()));
        this.valor_consumos.setText(String.format("$ %(,.2f",totalConsumos));
        //faltan los consumos


        if(numNoches >= this.reserva.getCondicion().getNumero_dias()){
            this.valor_descuentos.setText(String.format("$ %(,.2f",this.reserva.getPrecio_reserva() * this.reserva.getCondicion().getDescuento()));
            this.totalPago = (this.reserva.getPrecio_reserva() - (this.reserva.getPrecio_reserva() * this.reserva.getCondicion().getDescuento())) + this.totalConsumos;
            this.valor_total.setText(String.format("$ %(,.2f",totalPago));
        }else {
            this.valor_descuentos.setText("$ " + 0);
            this.totalPago = this.reserva.getPrecio_reserva() + this.totalConsumos;
            this.valor_total.setText(String.format("$ %(,.2f",totalPago));
        }

    }

    public Task<Registro> crearRegistro(Controlador_Huesped controlador_huesped, int codReservaFinal, int registroID) {
        return new Task<Registro>() {
            @Override
            protected Registro call() throws Exception {
                return new Registro(registroID, null,Date.valueOf(LocalDate.now()),
                        new DAO_Huesped().consultarHuesped(Integer.parseInt(controlador_huesped.LBnum_id.getText()), controlador_huesped.LB_TipoDoc.getText().replace(":","")),
                        new DAO_Reserva().consultarReserva(codReservaFinal),
                        new DAO_Habitacion().consultarHabitacion(Integer.parseInt(controlador_huesped.comboHabitacion.getValue().toString())));
            }
        };
    }

    public void realizarCheckout(Task<Registro> crearRegistroTask) {

        FXMLLoader alertaDireccion = new FXMLLoader(getClass().getResource("../../Vista/recepcionista/alerta.fxml"));
        Parent contenedor = null;
        try {
            contenedor = alertaDireccion.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFXDialog dialogAlerta = new JFXDialog(stackBG, (Region) contenedor, JFXDialog.DialogTransition.BOTTOM, true);
        Controlador_alerta controlador_alerta = alertaDireccion.getController();

        AnchorPane alertaAP = (AnchorPane) contenedor.getChildrenUnmodifiable().get(0);
        JFXButton btn_aceptar = (JFXButton) alertaAP.getChildren().get(2);
        btn_aceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventAceptar)->
        {
            dialogAlerta.close();
        });

        if(new DAO_Registro().actualizarRegistro(crearRegistroTask.getValue())>0)
        {
            controlador_alerta.titulo.setText("Actualización Realizada");
            controlador_alerta.mensaje.setText("Se actualizó correctamente el registro del checkin realizado por este huésped.");
            GridPanel_Huespedes.setDisable(true);
            progressIndCheckout.setVisible(true);
            obtener_huespedes();
            if(huespedIDList.size()==1)
            {
                new DAO_Reserva().actualizarEstadoDeReservas("vencida",cuenta.getReserva().getK_reserva());
            }
        }
        dialogAlerta.show();
    }

    public void mostrarConsumosEv(ActionEvent actionEvent) {
        FXMLLoader loaderConsumos = new FXMLLoader(getClass().getResource("../../Vista/recepcionista/vista_consumos.fxml"));
        Parent contenido = null;
        try {
            contenido = loaderConsumos.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFXDialog consumosDialog = new JFXDialog(stackBG, (Region) contenido, JFXDialog.DialogTransition.BOTTOM, true);
        Controlador_Consumos controlador_consumos = loaderConsumos.getController();
        Task<List<Cuenta_Productos>> listTask = new Task<List<Cuenta_Productos>>() {
            @Override
            protected List<Cuenta_Productos> call() throws Exception {
                return new DAO_Cuenta_Productos().consultarCuentaProdPorCuenta(new DAO_Cuenta().consultarCuentaPorReserva(codigoReserva).getK_cuenta());
            }
        };
        controlador_consumos.definirColumnas(listTask);
        consumosDialog.show();
    }

    public void procesar_pago(ActionEvent actionEvent) {
        FXMLLoader loaderConsumos = new FXMLLoader(getClass().getResource("../../Vista/recepcionista/metodos_de_pago.fxml"));
        Parent contenido = null;
        try {
            contenido = loaderConsumos.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Controlador_pagos controlador_pagos = loaderConsumos.getController();


        JFXDialog consumosDialog = new JFXDialog(stackBG, (Region) contenido, JFXDialog.DialogTransition.BOTTOM, true);
        JFXButton btnAceptar = (JFXButton) consumosDialog.lookup("#btn_aceptar");
        JFXButton btnCancelar = (JFXButton) consumosDialog.lookup("#btn_cancelar");

        btnAceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->
        {
            String metodoDePago = controlador_pagos.getMetodoDePago();
            crearPago(metodoDePago, consumosDialog);
            btn_procesar_pago.setDisable(true);
        });
        btnCancelar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->
        {
            consumosDialog.close();
        });

        consumosDialog.show();
    }
    public void crearPago(String metodoDePago, JFXDialog dialog){

        DAO_Pago dao_pago = new DAO_Pago();
        progressIndCheckout.setVisible(true);

        Task taskGuardarPago = new Task() {
            @Override
            protected Object call() throws Exception {
                Pago pago = new Pago(codigoDePago,Date.valueOf(LocalDate.now()),cuenta.getPrecio_acumulado(),metodoDePago,cuenta);
                dao_pago.insertarPago(pago);
                return null;
            }
        };
        Task<Integer> numeroPagoTask = new Task<Integer>() {
            @Override
            protected Integer call() throws Exception {
                return dao_pago.consultarUltimoPagoID();
            }
        };

        Thread pagoThread = new Thread(taskGuardarPago);
        Thread ultPagoThread = new Thread(numeroPagoTask);

        taskGuardarPago.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                progressIndCheckout.setVisible(false);
                btn_procesar_pago.setDisable(true);
                dialog.close();
                progressIndCheckout.setVisible(true);
                obtener_huespedes();
            }
        });

        numeroPagoTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                codigoDePago = numeroPagoTask.getValue() + 1;
                System.out.println(codigoDePago);
                pagoThread.start();
            }
        });

        ultPagoThread.start();



    }
}
