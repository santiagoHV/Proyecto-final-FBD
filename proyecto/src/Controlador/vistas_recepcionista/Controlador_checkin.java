package Controlador.vistas_recepcionista;

import Controlador.Controlador_alerta;
import DatosSQL.DAOs.*;
import Modelo.entidades.*;
import Vista.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.svg.SVGGlyph;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controlador_checkin implements Initializable {

    public GridPane GridPanel_Huespedes;
    public ScrollPane panel_ingreso_huespedes;
    public TextField codigo_reserva;
    public Button btn_buscar;
    public MFXProgressSpinner progressConCheck;
    public Label datos_nombre;
    public Label datos_ti;
    public Label datos_no_i;
    public Label datos_tel;
    public Label datos_edad;
    public Label datos_hab_sencillas;
    public Label datos_hab_dobles;
    public Label datos_hab_triples;
    public Label datos_f_inicio;
    public Label datos_f_fin;
    public Label datos_q_bebes;
    public Label datos_q_ninos;
    public Label datos_q_adultos;
    public StackPane stackBG;
    public JFXButton btn_finalizar;
    public Label datos_hab_matrimoniales;
    public Pane panel_datos;
    public Label til_reserva;
    public Label lb_reserva;
    public Label promp_reserva;

    private Controlador_checkin controlador_checkin = this;

    private Persona titularDeReserva;
    private List<Integer> huespedIDList = new ArrayList<>();

    private List<Registro> registroList = new ArrayList<>();

    private SVGGlyph svgGlyph = new SVGGlyph(0,"pageCollection",
            "M2.5,10.56l9,5.2a1,1,0,0,0,1,0l9-5.2a1,1,0,0,0,0-1.73l-9-5.2a1,1,0,0,0-1,0l-9,5.2a1,1,0,0,0,0,1.73ZM12,5.65l7,4-7,4.05L5,9.69Zm8.5,7.79L12,18.35,3.5,13.44a1,1,0,0,0-1.37.36,1,1,0,0,0,.37,1.37l9,5.2a1,1,0,0,0,1,0l9-5.2a1,1,0,0,0,.37-1.37A1,1,0,0,0,20.5,13.44Z"
            , Color.DARKGRAY);


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Uso del método smoothScrolling para suavizar la animación de desplazamiento de los scrollPane:
        JFXScrollPane Prueba = new JFXScrollPane();
        Prueba.smoothScrolling(panel_ingreso_huespedes);

        svgGlyph.setLayoutX(208);
        svgGlyph.setLayoutY(145);
        svgGlyph.setPrefHeight(85);
        svgGlyph.setPrefWidth(85);
        svgGlyph.setMaxHeight(85);
        svgGlyph.setMaxWidth(85);
        panel_datos.getChildren().add(svgGlyph);
        for(Node n: panel_datos.getChildren())
        {
            n.setVisible(false);
        }

        svgGlyph.setVisible(true);
        promp_reserva.setVisible(true);
        promp_reserva.setText("Aquí se mostrarán los datos de" +
                " la reserva consultada");
    }

    public void DefinirPanelDatosHuespedes() {

        FXMLLoader alertaDireccion = new FXMLLoader(getClass().getResource("../../Vista/recepcionista/alerta.fxml"));
        Parent contenedor = null;
        try {
            contenedor = alertaDireccion.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JFXDialog dialogAlerta = new JFXDialog(controlador_checkin.stackBG, (Region) contenedor, JFXDialog.DialogTransition.BOTTOM, true);
        Controlador_alerta controlador_alerta = alertaDireccion.getController();

        AnchorPane alertaAP = (AnchorPane) contenedor.getChildrenUnmodifiable().get(0);
        JFXButton btn_aceptar = (JFXButton) alertaAP.getChildren().get(2);
        btn_aceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventAceptar)->
        {
            dialogAlerta.close();
        });

        progressConCheck.setVisible(true);

        DAO_Reserva dao_reserva = new DAO_Reserva();
        DAO_Registro dao_registro = new DAO_Registro();

        huespedIDList.clear();
        registroList.clear();

        int codReserva = 0;
        if(!codigo_reserva.getText().equals("")){
            codReserva = Integer.parseInt(codigo_reserva.getText());
        }

        final int codReservaFinal = codReserva;


        Task<List<Reserva_Habitacion>> taskConReservaHabi = new Task<List<Reserva_Habitacion>>() {
            @Override
            protected List<Reserva_Habitacion> call() throws Exception {
                return dao_reserva.consultarReservaHabPorIdReserva(codReservaFinal);
            }
        };

        Task<List<Registro>> taskConRegistros = new Task<List<Registro>>() {
            @Override
            protected List<Registro> call() throws Exception {
                return dao_registro.consultarRegistroPorReserva(codReservaFinal);
            }
        };

        taskConReservaHabi.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {

                GridPanel_Huespedes.getChildren().clear();

                if(taskConReservaHabi.getValue().size()!=0)
                {
                    Reserva reserva = taskConReservaHabi.getValue().get(0).getReserva();

                    //Definición de los datos del titular:
                    datos_no_i.setText(reserva.getPersona().getK_identificacion()+"");
                    datos_nombre.setText(reserva.getPersona().getN_nombre() + " " +reserva.getPersona().getN_apellido());
                    datos_tel.setText(reserva.getPersona().getN_telefono());
                    datos_ti.setText(reserva.getPersona().getK_tipo_documento_id()+":");

                    Period period = Period.between(reserva.getPersona().getF_nacimiento().toLocalDate(), LocalDate.now());
                    datos_edad.setText(period.getYears() + " Años");

                    datos_hab_sencillas.setText("");
                    datos_hab_dobles.setText("");
                    datos_hab_triples.setText("");
                    datos_hab_matrimoniales.setText("");

                    datos_q_bebes.setText(reserva.getCantidad_bebes()+"");
                    datos_q_ninos.setText(reserva.getCantidad_ninos()+"");
                    datos_q_adultos.setText(reserva.getCantidad_adultos()+"");

                    datos_f_inicio.setText(reserva.getF_inicio()+"");
                    datos_f_fin.setText(reserva.getF_final()+"");

                    List<Habitacion> habitacionList = new ArrayList<>();

                    //Definición datos de la reserva:
                    for(int i = 0; i<taskConReservaHabi.getValue().size();i++)
                    {
                        if(taskConReservaHabi.getValue().get(i).getHabitacion().getTipo_habitacion().getK_tipo_habitacion().equals("sencilla"))
                        {
                            //Habitaciones sencillas:
                            if(datos_hab_sencillas.getText().equals(""))
                            {
                                datos_hab_sencillas.setText(taskConReservaHabi.getValue().get(i).getHabitacion().getK_numero_habitacion()+"");
                            }
                            else
                            {
                                datos_hab_sencillas.setText(datos_hab_sencillas.getText() + ", " + taskConReservaHabi.getValue().get(i).getHabitacion().getK_numero_habitacion());
                            }
                        }

                        if(taskConReservaHabi.getValue().get(i).getHabitacion().getTipo_habitacion().getK_tipo_habitacion().equals("doble"))
                        {
                            //Habitaciones dobles:
                            if(datos_hab_dobles.getText().equals(""))
                            {
                                datos_hab_dobles.setText(taskConReservaHabi.getValue().get(i).getHabitacion().getK_numero_habitacion()+"");
                            }
                            else
                            {
                                datos_hab_dobles.setText(datos_hab_dobles.getText() + ", " + taskConReservaHabi.getValue().get(i).getHabitacion().getK_numero_habitacion());
                            }
                        }

                        if(taskConReservaHabi.getValue().get(i).getHabitacion().getTipo_habitacion().getK_tipo_habitacion().equals("triple"))
                        {
                            //Habitaciones triples:
                            if(datos_hab_triples.getText().equals(""))
                            {
                                datos_hab_triples.setText(taskConReservaHabi.getValue().get(i).getHabitacion().getK_numero_habitacion()+"");
                            }
                            else
                            {
                                datos_hab_triples.setText(datos_hab_triples.getText() + ", " + taskConReservaHabi.getValue().get(i).getHabitacion().getK_numero_habitacion());
                            }
                        }

                        if(taskConReservaHabi.getValue().get(i).getHabitacion().getTipo_habitacion().getK_tipo_habitacion().equals("matrimonial"))
                        {
                            //Habitaciones matrimoniales:
                            if(datos_hab_matrimoniales.getText().equals(""))
                            {
                                datos_hab_matrimoniales.setText(taskConReservaHabi.getValue().get(i).getHabitacion().getK_numero_habitacion()+"");
                            }
                            else
                            {
                                datos_hab_matrimoniales.setText(datos_hab_matrimoniales.getText() + ", " + taskConReservaHabi.getValue().get(i).getHabitacion().getK_numero_habitacion());
                            }
                        }
                        habitacionList.add(taskConReservaHabi.getValue().get(i).getHabitacion());
                    }

                    if(datos_hab_sencillas.getText().equals(""))
                    {
                        datos_hab_sencillas.setText("Ninguna Reservada");
                    }
                    if(datos_hab_dobles.getText().equals(""))
                    {
                        datos_hab_dobles.setText("Ninguna Reservada");
                    }
                    if(datos_hab_triples.getText().equals(""))
                    {
                        datos_hab_triples.setText("Ninguna Reservada");
                    }
                    if(datos_hab_matrimoniales.getText().equals(""))
                    {
                        datos_hab_matrimoniales.setText("Ninguna Reservada");
                    }

                    for(Node n: panel_datos.getChildren())
                    {
                        if(!n.equals(til_reserva) || !n.equals(lb_reserva))
                        {
                            n.setVisible(true);
                        }
                    }
                    promp_reserva.setVisible(false);
                    svgGlyph.setVisible(false);

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
                                int cantBebes = reserva.getCantidad_bebes();
                                int cantNinos = reserva.getCantidad_ninos();
                                int cantAdultos = reserva.getCantidad_adultos();
                                int cantTotalHuespedes = cantBebes + cantNinos + cantAdultos;


                                for(int i = 0; i<cantTotalHuespedes; i++) {
                                    final int j = i;

                                    huespedIDList.add(0);

                                    //Carga de las plantillas para los paneles con información de los huespedes
                                    FXMLLoader loader = new FXMLLoader();
                                    loader.setLocation(Main.class.getResource("../Vista/recepcionista/Panel_Huesped.fxml"));

                                    //Definición de los anchorpane:
                                    //Para el panel de los huespedes:
                                    AnchorPane PanelHuespedes = loader.load();

                                    Controlador_Huesped controlador_huesped = loader.getController();
                                    controlador_huesped.txt_num_noches.setVisible(true);
                                    controlador_huesped.txt_num_noches.setText((Period.between(reserva.getF_inicio().toLocalDate(),reserva.getF_final().toLocalDate()).getDays()-1)+"");

                                    if(registroList.size()==0)
                                    {
                                        if(cantBebes>0)
                                        {
                                            controlador_huesped.setValoresTemporales("Bebe", habitacionList);
                                            cantBebes--;
                                        }
                                        else if(cantNinos>0)
                                        {
                                            controlador_huesped.setValoresTemporales("Niño", habitacionList);
                                            cantNinos--;
                                        }
                                        else if(cantAdultos>0)
                                        {
                                            controlador_huesped.setValoresTemporales("Adulto", habitacionList);
                                            cantAdultos--;
                                        }


                                        controlador_huesped.btn_ingreso.setText("Ingresar");
                                        controlador_huesped.btn_ingreso.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ingresarReg)->
                                        {
                                            controlador_huesped.progressActIng.setVisible(true);
                                            Task<Registro> crearRegistroTask = crearRegistro(controlador_huesped, codReservaFinal,0);

                                            crearRegistroTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                                @Override
                                                public void handle(WorkerStateEvent workerStateEvent) {
                                                    comprobarIDRepetido(crearRegistroTask, "Ingreso");
                                                    controlador_huesped.progressActIng.setVisible(false);
                                                }
                                            });
                                            Thread crearRegistroThread = new Thread(crearRegistroTask);
                                            crearRegistroThread.start();
                                        });
                                    }
                                    else
                                    {
                                        if(i<registroList.size())
                                        {
                                            if(Period.between(registroList.get(i).getHuesped().getF_nacimiento().toLocalDate(), LocalDate.now()).getYears()<=2)
                                            {
                                                cantBebes--;
                                            }
                                            else if(Period.between(registroList.get(i).getHuesped().getF_nacimiento().toLocalDate(), LocalDate.now()).getYears()>2 && Period.between(registroList.get(i).getHuesped().getF_nacimiento().toLocalDate(), LocalDate.now()).getYears()<18)
                                            {
                                                cantNinos--;
                                            }
                                            else if(Period.between(registroList.get(i).getHuesped().getF_nacimiento().toLocalDate(), LocalDate.now()).getYears()>18)
                                            {
                                                cantAdultos--;
                                            }

                                            controlador_huesped.setValoresPanel(registroList.get(i).getHuesped(), habitacionList, codReservaFinal);

                                            huespedIDList.set(i,registroList.get(i).getHuesped().getK_identificacion());

                                            controlador_huesped.btn_ingreso.setText("Actualizar");
                                            controlador_huesped.btn_ingreso.setDisable(false);

                                            controlador_huesped.btn_ingreso.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent actualizarReg)->
                                            {
                                                controlador_huesped.progressActIng.setVisible(true);
                                                Task<Registro> crearRegistroTask = crearRegistro(controlador_huesped, codReservaFinal, registroList.get(j).getK_registro());

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
                                        }
                                        else
                                        {
                                            if(cantBebes>0)
                                            {
                                                controlador_huesped.setValoresTemporales("Bebe", habitacionList);
                                                cantBebes--;
                                            }
                                            else if(cantNinos>0)
                                            {
                                                controlador_huesped.setValoresTemporales("Niño", habitacionList);
                                                cantNinos--;
                                            }
                                            else if(cantAdultos>0)
                                            {
                                                controlador_huesped.setValoresTemporales("Adulto", habitacionList);
                                                cantAdultos--;
                                            }


                                            controlador_huesped.btn_ingreso.setText("Ingresar");
                                            controlador_huesped.btn_ingreso.setDisable(true);

                                            controlador_huesped.btn_ingreso.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ingresarReg)->
                                            {
                                                controlador_huesped.progressActIng.setVisible(true);
                                                Task<Registro> crearRegistroTask = crearRegistro(controlador_huesped, codReservaFinal, 0);

                                                crearRegistroTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                                                    @Override
                                                    public void handle(WorkerStateEvent workerStateEvent) {
                                                        if(huespedIDList.contains(crearRegistroTask.getValue().getHuesped().getK_identificacion()))
                                                        {
                                                            comprobarIDRepetido(crearRegistroTask, "Ingreso");
                                                            controlador_huesped.progressActIng.setVisible(false);
                                                        }
                                                    }
                                                });
                                                Thread crearRegistroThread = new Thread(crearRegistroTask);

                                                crearRegistroThread.start();
                                            });
                                        }
                                    }

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
                                        JFXDialog dialog = new JFXDialog(controlador_checkin.stackBG, (Region) parent, JFXDialog.DialogTransition.BOTTOM, true);
                                        AnchorPane AP = (AnchorPane) parent.getChildrenUnmodifiable().get(0);
                                        HBox HB = (HBox) AP.getChildren().get(0);
                                        Button BSalirDialog = (Button)HB.getChildrenUnmodifiable().get(1);
                                        Button btnCargarDatos = (Button)HB.getChildrenUnmodifiable().get(0);

                                        Controlador_datos_ingreso controlador_datos_ingreso = loaderIngreso.getController();

                                        btnCargarDatos.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventIngreso) ->
                                        {
                                            CheckBox checkNuevo = (CheckBox) dialog.lookup("#checkNuevo");

                                            if(checkNuevo.isSelected()){
                                                titularDeReserva = controlador_datos_ingreso.solicitarPersona(true);
                                                try{
                                                    DAO_Persona dao_persona = new DAO_Persona();
                                                    dao_persona.insertarPersona(titularDeReserva);
                                                    if(titularDeReserva.getClass().equals(Huesped.class))
                                                    {
                                                        DAO_Huesped dao_huesped = new DAO_Huesped();
                                                        dao_huesped.insertarHuesped((Huesped) titularDeReserva);
                                                    }
                                                }catch (Exception e){
                                                    System.out.println(e + "Guardado fallido");
                                                }
                                            }else{
                                                titularDeReserva = controlador_datos_ingreso.solicitarPersona(false);
                                            }

                                            if(titularDeReserva.getClass().equals(Huesped.class))
                                            {
                                                if(controlador_huesped.txt_tipo_huesped.getText().equals("Bebe") && Period.between(titularDeReserva.getF_nacimiento().toLocalDate(),LocalDate.now()).getYears()>2)
                                                {
                                                    controlador_alerta.titulo.setText("El huésped ingresado no cumple con la edad establecida");
                                                    controlador_alerta.mensaje.setText("El huésped seleccionado no se encuentra dentro del rango de edad apropiado para el campo," +
                                                            " revise la información ingresada e intentélo nuevamente. El huesped ingresado debe ser un bebe");
                                                    dialogAlerta.show();
                                                } else if(controlador_huesped.txt_tipo_huesped.getText().equals("Niño") && (Period.between(titularDeReserva.getF_nacimiento().toLocalDate(),LocalDate.now()).getYears()<=2 || Period.between(titularDeReserva.getF_nacimiento().toLocalDate(),LocalDate.now()).getYears()>=18))
                                                {
                                                    controlador_alerta.titulo.setText("El huésped ingresado no cumple con la edad establecida");
                                                    controlador_alerta.mensaje.setText("El huésped seleccionado no se encuentra dentro del rango de edad apropiado para el campo," +
                                                            " revise la información ingresada e intentélo nuevamente. El huesped ingresado debe ser un niño");
                                                    dialogAlerta.show();
                                                }else if(controlador_huesped.txt_tipo_huesped.getText().equals("Adulto") && Period.between(titularDeReserva.getF_nacimiento().toLocalDate(),LocalDate.now()).getYears()<2)
                                                {
                                                    controlador_alerta.titulo.setText("El huésped ingresado no cumple con la edad establecida");
                                                    controlador_alerta.mensaje.setText("El huésped seleccionado no se encuentra dentro del rango de edad apropiado para el campo," +
                                                            " revise la información ingresada e intentélo nuevamente. El huesped ingresado debe ser adulto");
                                                    dialogAlerta.show();
                                                } else {
                                                    controlador_huesped.setValoresPanel((Huesped) titularDeReserva, habitacionList, codReservaFinal);
                                                    dialog.close();
                                                    controlador_huesped.btn_ingreso.setDisable(false);

                                                    //Lista de huespedes, se agregan elementos en caso de que la posición esté vacía
                                                    //en caso de que no lo esté, se reemplaza el elemento
                                                    huespedIDList.set(j,titularDeReserva.getK_identificacion());
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

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
                else
                {
                    controlador_alerta.titulo.setText("Reserva no encontrada");
                    controlador_alerta.mensaje.setText("El código de la reserva ingresado no se encuentra registrado o no se encuentra en curso, por favor revise la información revisada");
                    dialogAlerta.show();
                }
                progressConCheck.setVisible(false);
            }
        });
        Thread threadReserva = new Thread(taskConReservaHabi);
        threadReserva.start();
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

        JFXDialog dialogAlerta = new JFXDialog(controlador_checkin.stackBG, (Region) contenedor, JFXDialog.DialogTransition.BOTTOM, true);
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

                    DefinirPanelDatosHuespedes();
                }
            }
            else
            {
                if(new DAO_Registro().actualizarRegistro(crearRegistroTask.getValue())>0)
                {
                    controlador_alerta.titulo.setText("Actualización Realizada");
                    controlador_alerta.mensaje.setText("Se actualizó correctamente el registro del checkin realizado por este huésped.");

                    DefinirPanelDatosHuespedes();
                }
            }
        }
        dialogAlerta.show();
    }

    public void ClickBuscar(ActionEvent actionEvent) {
        if(!codigo_reserva.getText().equals(""))
        {
            DefinirPanelDatosHuespedes();
        }
    }

    public void btn_limpiar(ActionEvent actionEvent) {
        for(Node n: panel_datos.getChildren())
        {
            n.setVisible(false);
        }

        svgGlyph.setVisible(true);
        promp_reserva.setVisible(true);

        GridPanel_Huespedes.getChildren().clear();
    }
}
