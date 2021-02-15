package Controlador.vistas_recepcionista;

import DatosSQL.DAOs.DAO_Persona;
import DatosSQL.DAOs.DAO_Reserva;
import Modelo.entidades.*;
import Vista.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXScrollPane;
import com.jfoenix.controls.events.JFXDialogEvent;
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
import javafx.scene.effect.BoxBlur;
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
    public Label datos_direccion;
    public Label datos_hab_sencillas;
    public Label datos_hab_dobles;
    public Label datos_hab_triples;
    public Label datos_f_inicio;
    public Label datos_f_fin;
    public Label datos_q_bebes;
    public Label datos_q_ninos;
    public Label datos_q_adultos;
    public StackPane stackBG;


    private Persona titularDeReserva;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Uso del método smoothScrolling para suavizar la animación de desplazamiento de los scrollPane:
        JFXScrollPane Prueba = new JFXScrollPane();
        Prueba.smoothScrolling(panel_ingreso_huespedes);
        Prueba.smoothHScrolling(panel_ingreso_habitaciones);
//        DefinirPanelDatosHuespedes();
//        DefinirBotonesHabitacion();

        AjusteScrollH(panel_ingreso_habitaciones);
        Controlador_datos_ingreso controlador_datos_ingreso = new Controlador_datos_ingreso();
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
                    System.out.println(taskConReservaHabi.getValue().get(i).getHabitacion().getTipo_habitacion().getK_tipo_habitacion());
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

                int column = 0;
                int row = 0;

                DefinirBotonesHabitacion(habitacionList);

                try
                {
                    int cantBebes = reserva.getCantidad_bebes();
                    int cantNinos = reserva.getCantidad_ninos();
                    int cantAdultos = reserva.getCantidad_adultos();
                    int cantTotalHuespedes = cantBebes + cantNinos + cantAdultos;

                    for(int i=0; i<cantTotalHuespedes;i++) {
                        //Carga de las plantillas para los paneles con información de los huespedes
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(Main.class.getResource("../Vista/recepcionista/Panel_Huesped.fxml"));

                        //Definición de los anchorpane:
                        //Para el panel de los huespedes:
                        AnchorPane PanelHuespedes = loader.load();

                        //////////////////////////////////////////////
                        VBox vBox = (VBox) PanelHuespedes.getChildren().get(10);
                        JFXButton BCambiar = (JFXButton) vBox.getChildren().get(0);
                        JFXButton BIngreso = (JFXButton) vBox.getChildren().get(1);

                        //Agregado evento a cada botón "cambiar" para acceder al JFXDialog de Ingreso de Datos
                        BCambiar.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->
                        {
                            BoxBlur blur = new BoxBlur(3,3,3);
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
                            controlador_datos_ingreso.direccion_in.setDisable(true);

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
                                dialog.close();
                                BIngreso.setDisable(false);
                            });

                            BSalirDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEventIngreso)->
                            {
                                dialog.close();
                            });

                            dialog.setOnDialogClosed((JFXDialogEvent event)->
                            {
                                stackBG.setEffect(null);
                            });

                            //stackBG.setEffect(blur);
                            dialog.show();
                        });

                        Controlador_Huesped controlador_huesped = loader.getController();

                        if(cantBebes>0)
                        {
                            controlador_huesped.setValoresTemporales("Bebe");
                            cantBebes--;
                        }
                        else if(cantNinos>0)
                        {
                            controlador_huesped.setValoresTemporales("Nino");
                            cantNinos--;
                        }
                        else if(cantAdultos>0)
                        {
                            controlador_huesped.setValoresTemporales("Adulto");
                            cantAdultos--;
                        }

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
                    progressConCheck.setVisible(false);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

    public void DefinirBotonesHabitacion(List<Habitacion> Habitaciones)
    {
        int column = 0;
        int row = 0;
        try
        {
            for(int i=0; i<Habitaciones.size();i++) {
                //Carga de las plantillas para los botones de las habitaciones:
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("../Vista/recepcionista/Boton_Habitaciones.fxml"));

                //Definición de los anchorpane:
                //Para los botones:
                AnchorPane PanelHabitaciones = loader.load();

                Controlador_Boton_Habitaciones controlador_boton_habitaciones = loader.getController();
                controlador_boton_habitaciones.DefinirHabitacion(Habitaciones.get(i));

                column++;
                GridPanel_Btn_Habitaciones.add(PanelHabitaciones,column,row);
                GridPane.setMargin(PanelHabitaciones,new Insets(7));

                //Alto y Ancho:
                //Ancho:
                GridPanel_Btn_Habitaciones.setMaxWidth(Region.USE_COMPUTED_SIZE);
                GridPanel_Btn_Habitaciones.setPrefWidth(Region.USE_COMPUTED_SIZE);
                GridPanel_Btn_Habitaciones.setMinWidth(Region.USE_COMPUTED_SIZE);

                //Alto:
                GridPanel_Btn_Habitaciones.setMaxHeight(Region.USE_COMPUTED_SIZE);
                GridPanel_Btn_Habitaciones.setPrefHeight(Region.USE_COMPUTED_SIZE);
                GridPanel_Btn_Habitaciones.setMinHeight(Region.USE_COMPUTED_SIZE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
