package Controlador.vistas_recepcionista;

import DatosSQL.DAOs.DAO_Persona;
import DatosSQL.DAOs.DAO_Registro;
import DatosSQL.DAOs.DAO_Reserva;
import Modelo.entidades.*;
import Vista.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controlador_checkin implements Initializable {

    public GridPane GridPanel_Huespedes;
    public GridPane GridPanel_Btn_Habitaciones;
    public ScrollPane panel_ingreso_huespedes;
    public ScrollPane panel_ingreso_habitaciones;
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

    private Controlador_checkin controlador_checkin = this;

    private Persona titularDeReserva;
    private List<Huesped> huespedList = new ArrayList<>();

    private List<Registro> registroList = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Uso del método smoothScrolling para suavizar la animación de desplazamiento de los scrollPane:
        JFXScrollPane Prueba = new JFXScrollPane();
        Prueba.smoothScrolling(panel_ingreso_huespedes);
        Prueba.smoothHScrolling(panel_ingreso_habitaciones);

        AjusteScrollH(panel_ingreso_habitaciones);
    }

    //Método para poder controlar los scrolls horizontales con la rueda del ratón:
    public void AjusteScrollH(ScrollPane scrollPane)
    {
        scrollPane.setOnScroll(scrollEvent ->
        {
            if(scrollEvent.getDeltaX() == 0 && scrollEvent.getDeltaY() != 0) {
                scrollPane.setHvalue(scrollPane.getHvalue() - scrollEvent.getDeltaY() / this.GridPanel_Btn_Habitaciones.getWidth());
            }
        });
    }



    public void DefinirPanelDatosHuespedes() {

        DAO_Reserva dao_reserva = new DAO_Reserva();
        DAO_Registro dao_registro = new DAO_Registro();

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
                List<Registro> registroListTarea = dao_registro.consultarRegistroPorReserva(codReservaFinal);
                return registroListTarea;
            }
        };

        taskConReservaHabi.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {

                GridPanel_Huespedes.getChildren().clear();

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

                datos_q_bebes.setText(reserva.getCantidad_bebes()+"");
                datos_q_ninos.setText(reserva.getCantidad_ninos()+"");
                datos_q_adultos.setText(reserva.getCantidad_adultos()+"");

                datos_f_inicio.setText(reserva.getF_inicio()+"");
                datos_f_fin.setText(reserva.getF_final()+"");

                List<Habitacion> habitacionList = new ArrayList<>();

                //Definición datos de la reserva:
                for(int i = 0; i<taskConReservaHabi.getValue().size();i++)
                {
                    if(taskConReservaHabi.getValue().get(i).getHabitacion().getTipo_habitacion().getK_tipo_habitacion().equals("1"))
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

                    if(taskConReservaHabi.getValue().get(i).getHabitacion().getTipo_habitacion().getK_tipo_habitacion().equals("2"))
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

                    if(taskConReservaHabi.getValue().get(i).getHabitacion().getTipo_habitacion().getK_tipo_habitacion().equals("3"))
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

                Thread threadRegistro = new Thread(taskConRegistros);
                threadRegistro.start();

                taskConRegistros.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent workerStateEvent) {
                        System.out.println("Pasó Registros");
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
                                //Carga de las plantillas para los paneles con información de los huespedes
                                FXMLLoader loader = new FXMLLoader();
                                loader.setLocation(Main.class.getResource("../Vista/recepcionista/Panel_Huesped.fxml"));

                                //Definición de los anchorpane:
                                //Para el panel de los huespedes:
                                AnchorPane PanelHuespedes = loader.load();

                                Controlador_Huesped controlador_huesped = loader.getController();

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
                                        controlador_huesped.setValoresPanel(registroList.get(i).getHuesped(), habitacionList);

                                        controlador_huesped.btn_ingreso.setText("Actualizar");
                                        controlador_huesped.btn_ingreso.setDisable(false);

                                        controlador_huesped.btn_ingreso.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent actualizarReg)->
                                        {

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
                                        controlador_huesped.btn_ingreso.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent ingresarReg)->
                                        {

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
                                            }catch (Exception e){
                                                System.out.println(e + "Guardado fallido");
                                            }
                                        }else{
                                            titularDeReserva = controlador_datos_ingreso.solicitarPersona(false);
                                        }

                                        if(titularDeReserva.getClass().equals(Huesped.class))
                                        {
                                            controlador_huesped.setValoresPanel((Huesped) titularDeReserva, null);
                                            dialog.close();
                                            controlador_huesped.btn_ingreso.setDisable(false);

                                            //Lista de huespedes, se agregan elementos en caso de que la posición esté vacía
                                            //en caso de que no lo esté, se reemplaza el elemento
                                            if(huespedList.size()-j==0)
                                            {
                                                huespedList.add(j,(Huesped) titularDeReserva);
                                            }
                                            else
                                            {
                                                huespedList.set(j,(Huesped) titularDeReserva);
                                            }
                                        }
                                        else
                                        {
                                            FXMLLoader alertaDireccion = new FXMLLoader(getClass().getResource("../../Vista/recepcionista/alerta.fxml"));
                                            Parent contenedor = null;
                                            try {
                                                contenedor = alertaDireccion.load();
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }

                                            JFXDialog dialogAlerta = new JFXDialog(controlador_checkin.stackBG, (Region) contenedor, JFXDialog.DialogTransition.BOTTOM, true);

                                            AnchorPane alertaAP = (AnchorPane) contenedor.getChildrenUnmodifiable().get(0);
                                            JFXButton btn_aceptar = (JFXButton) alertaAP.getChildren().get(2);
                                            btn_aceptar.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventAceptar)->
                                            {
                                                dialogAlerta.close();
                                            });

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
                            progressConCheck.setVisible(false);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        Thread threadReserva = new Thread(taskConReservaHabi);
        threadReserva.start();
    }

    public void ClickBuscar(ActionEvent actionEvent) {
        if(!codigo_reserva.getText().equals(""))
        {
            progressConCheck.setVisible(true);
            DefinirPanelDatosHuespedes();
        }
    }

    public void btn_realizar_checkin(ActionEvent actionEvent) {
        for(Huesped h: huespedList)
        {
            System.out.println(h.getK_identificacion());
        }
    }
}
