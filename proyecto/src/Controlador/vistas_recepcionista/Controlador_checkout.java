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

    private List<Habitacion> habitacionList = new ArrayList<>();

    private List<Registro> registroList = new ArrayList<>();

    private List<Integer> huespedIDList = new ArrayList<>();

    private Persona personaConsultada;

    private Controlador_alerta controlador_alerta;
    private JFXDialog dialogAlerta;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //prueba por errores de saldamigo

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

        Task<List<Reserva_Habitacion>> reservaTask = new Task<List<Reserva_Habitacion>>() {
            @Override
            protected List<Reserva_Habitacion> call() throws Exception {
                return dao_reserva.consultarReservaHabPorIdReserva(codigoReserva);
            }
        };

        Thread threadReserva = new Thread(reservaTask);

        reservaTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                reserva = reservaTask.getValue().get(0).getReserva();
                for(int i = 0; i<reservaTask.getValue().size();i++)
                {
                    habitacionList.add(reservaTask.getValue().get(i).getHabitacion());
                }
                llenarDatosReserva();
            }
        });

        try{
            codigoReserva = Integer.parseInt(codigo_reserva.getText());
            threadReserva.start();
            obtener_huespedes();
        }catch (Exception e){
            //poner error en busqueda
        }
    }

    public void obtener_huespedes()
    {
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

                int column = 0;
                int row = 0;

                try
                {
                    for(int i = 0; i<registroList.size(); i++) {
                        final int j = i;
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

                        controlador_huesped.btn_ingreso.setText("Actualizar");
                        controlador_huesped.btn_ingreso.setDisable(false);

                        controlador_huesped.btn_ingreso.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent actualizarReg)->
                        {
                            controlador_huesped.progressActIng.setVisible(true);
                            Task<Registro> crearRegistroTask = crearRegistro(controlador_huesped, codigoReserva, registroList.get(j).getK_registro());

                            crearRegistroTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                @Override
                                public void handle(WorkerStateEvent workerStateEvent) {
                                    if(huespedIDList.contains(crearRegistroTask.getValue().getHuesped().getK_identificacion()))
                                    {
                                        comprobarIDRepetido(crearRegistroTask, "Actualizar");
                                        controlador_huesped.progressActIng.setVisible(false);
                                    }
                                }
                            });
                            Thread crearRegistroThread = new Thread(crearRegistroTask);

                            crearRegistroThread.start();
                        });

                        row++;
                        GridPanel_Huespedes.add(PanelHuespedes,column,row);
                        GridPane.setMargin(PanelHuespedes,new Insets(8));

                        //---------------------------------------------------------------
                        //Acceso al ingreso de Datos:

                        controlador_huesped.btn_cambiar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventCambio)->
                        {
                            FXMLLoader loaderIngreso = new FXMLLoader(getClass().getResource("../../Vista/recepcionista/ingreso_datos.fxml"));
                            Parent parent = null;
                            try {
                                parent = loaderIngreso.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            JFXDialog dialog = new JFXDialog(stackBG, (Region) parent, JFXDialog.DialogTransition.BOTTOM, true);
                            AnchorPane AP = (AnchorPane) parent.getChildrenUnmodifiable().get(0);
                            HBox HB = (HBox) AP.getChildren().get(0);
                            Button BSalirDialog = (Button)HB.getChildrenUnmodifiable().get(1);
                            Button btnCargarDatos = (Button)HB.getChildrenUnmodifiable().get(0);

                            Controlador_datos_ingreso controlador_datos_ingreso = loaderIngreso.getController();

                            btnCargarDatos.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventIngreso) ->
                            {
                                CheckBox checkNuevo = (CheckBox) dialog.lookup("#checkNuevo");

                                if(checkNuevo.isSelected()){
                                    personaConsultada = controlador_datos_ingreso.solicitarPersona(true);
                                    try{
                                        DAO_Persona dao_persona = new DAO_Persona();
                                        dao_persona.insertarPersona(personaConsultada);
                                    }catch (Exception e){
                                        System.out.println(e + "Guardado fallido");
                                    }
                                }else{
                                    personaConsultada = controlador_datos_ingreso.solicitarPersona(false);
                                }

                                if(personaConsultada.getClass().equals(Huesped.class))
                                {
                                    if(controlador_huesped.txt_tipo_huesped.getText().equals("Bebe") && Period.between(personaConsultada.getF_nacimiento().toLocalDate(),LocalDate.now()).getYears()>2)
                                    {
                                        controlador_alerta.titulo.setText("El huésped ingresado no cumple con la edad establecida");
                                        controlador_alerta.mensaje.setText("El huésped seleccionado no se encuentra dentro del rango de edad apropiado para el campo," +
                                                " revise la información ingresada e intentélo nuevamente. El huesped ingresado debe ser un bebe");
                                        dialogAlerta.show();
                                    } else if(controlador_huesped.txt_tipo_huesped.getText().equals("Niño") && (Period.between(personaConsultada.getF_nacimiento().toLocalDate(),LocalDate.now()).getYears()<=2 || Period.between(personaConsultada.getF_nacimiento().toLocalDate(),LocalDate.now()).getYears()>=18))
                                    {
                                        controlador_alerta.titulo.setText("El huésped ingresado no cumple con la edad establecida");
                                        controlador_alerta.mensaje.setText("El huésped seleccionado no se encuentra dentro del rango de edad apropiado para el campo," +
                                                " revise la información ingresada e intentélo nuevamente. El huesped ingresado debe ser un niño");
                                        dialogAlerta.show();
                                    }else if(controlador_huesped.txt_tipo_huesped.getText().equals("Adulto") && Period.between(personaConsultada.getF_nacimiento().toLocalDate(),LocalDate.now()).getYears()<2)
                                    {
                                        controlador_alerta.titulo.setText("El huésped ingresado no cumple con la edad establecida");
                                        controlador_alerta.mensaje.setText("El huésped seleccionado no se encuentra dentro del rango de edad apropiado para el campo," +
                                                " revise la información ingresada e intentélo nuevamente. El huesped ingresado debe ser adulto");
                                        dialogAlerta.show();
                                    } else {
                                        controlador_huesped.setValoresPanel((Huesped) personaConsultada, habitacionList);
                                        dialog.close();
                                        controlador_huesped.btn_ingreso.setDisable(false);

                                        huespedIDList.set(j, personaConsultada.getK_identificacion());
                                    }
                                }
                                else
                                {
                                    dialogAlerta.show();
                                }
                            });

                            BSalirDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventIngreso)->
                            {
                                dialog.close();
                            });

                            dialog.show();
                        });

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
                    progressIndCheckout.setVisible(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void llenarDatosReserva(){

        for(Node n: panel_titular.getChildren())
        {
            n.setVisible(true);
        }

        information.setVisible(false);
        promptPagos.setVisible(false);
        promptTitular.setVisible(false);
        balance.setVisible(false);

        this.datos_nombre.setText(this.reserva.getPersona().getN_nombre() + " " + this.reserva.getPersona().getN_apellido());
        this.datos_edad.setText(String.valueOf(Period.between(this.reserva.getPersona().getF_nacimiento().toLocalDate(), LocalDate.now()).getYears()));
        this.datos_tel.setText(this.reserva.getPersona().getN_telefono());
        this.datos_ti.setText(this.reserva.getPersona().getK_tipo_documento_id());
        this.datos_no_i.setText(String.valueOf(this.reserva.getPersona().getK_identificacion()));
    }

    public Task<Registro> crearRegistro(Controlador_Huesped controlador_huesped, int codReservaFinal, int registroID) {
        return new Task<Registro>() {
            @Override
            protected Registro call() throws Exception {
                return new Registro(registroID, Date.valueOf(LocalDate.now()),null,
                        new DAO_Huesped().consultarHuesped(Integer.parseInt(controlador_huesped.LBnum_id.getText()), controlador_huesped.LB_TipoDoc.getText().replace(":","")),
                        new DAO_Reserva().consultarReserva(codReservaFinal),
                        new DAO_Habitacion().consultarHabitacion(Integer.parseInt(controlador_huesped.comboHabitacion.getValue().toString())));
            }
        };
    }

    public void comprobarIDRepetido(Task<Registro> crearRegistroTask, String Operacion) {

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

        int cantRepetida = 0;
        for(int i: huespedIDList)
        {
            if(i==crearRegistroTask.getValue().getHuesped().getK_identificacion())
            {
                cantRepetida++;
            }
        }

        if(cantRepetida>1)
        {
            controlador_alerta.titulo.setText("Huésped ya seleccionado");
            controlador_alerta.mensaje.setText("Este huesped ya fue registrado para realizar su proceso de checkin en otra tarjeta, revise esta información" +
                    " e inténtelo nuevamente.");
        }
        else
        {
            if(Operacion.equals("Ingreso"))
            {
                if(new DAO_Registro().insertarRegistro(crearRegistroTask.getValue())>0)
                {
                    controlador_alerta.titulo.setText("Inserción Realizada");
                    controlador_alerta.mensaje.setText("El proceso de Checkin para este huésped se realizó correctamente");

                    //DefinirPanelDatosHuespedes();
                }
            }
            else
            {
                if(new DAO_Registro().actualizarRegistro(crearRegistroTask.getValue())>0)
                {
                    controlador_alerta.titulo.setText("Actualización Realizada");
                    controlador_alerta.mensaje.setText("Se actualizó correctamente el registro del checkin realizado por este huésped.");

                    //DefinirPanelDatosHuespedes();
                }
            }
        }
        dialogAlerta.show();
    }
}
