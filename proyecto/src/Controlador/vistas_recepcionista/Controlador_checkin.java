package Controlador.vistas_recepcionista;

import DatosSQL.DAOs.DAO_Reserva;
import Modelo.entidades.Habitacion;
import Modelo.entidades.Huesped;
import Modelo.entidades.Persona;
import Modelo.entidades.Reserva;
import Vista.Main;
import com.jfoenix.controls.JFXScrollPane;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.net.URL;
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

        Task<Reserva> taskConReserva = new Task<Reserva>() {
            @Override
            protected Reserva call() throws Exception {
                return dao_reserva.consultarReserva(codReservaFinal);
            }
        };

        taskConReserva.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {

                GridPanel_Huespedes.getChildren().clear();

                Reserva reserva = taskConReserva.getValue();

                //Definición de los datos del titular:
                datos_no_i.setText(reserva.getPersona().getK_identificacion()+"");
                datos_nombre.setText(reserva.getPersona().getN_nombre() + " " +reserva.getPersona().getN_apellido());
                datos_tel.setText(reserva.getPersona().getN_telefono());

                int column = 0;
                int row = 0;

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
        Thread threadReserva = new Thread(taskConReserva);
        threadReserva.start();
    }

    public void ClickBuscar(ActionEvent actionEvent) {
        if(!codigo_reserva.getText().equals(""))
        {
            progressConCheck.setVisible(true);
            DefinirPanelDatosHuespedes();
        }
    }

//    public void DefinirBotonesHabitacion()
//    {
//        List<Habitacion> Habitaciones = ConsultarHabitacion();
//
//        int column = 0;
//        int row = 0;
//        try
//        {
//            for(int i=0; i<8;i++) {
//                //Carga de las plantillas para los botones de las habitaciones:
//                FXMLLoader loader = new FXMLLoader();
//                loader.setLocation(Main.class.getResource("../Vista/recepcionista/Boton_Habitaciones.fxml"));
//
//                //Definición de los anchorpane:
//                //Para los botones:
//                AnchorPane PanelHabitaciones = loader.load();
//
//                Controlador_Boton_Habitaciones controlador_boton_habitaciones = loader.getController();
//                controlador_boton_habitaciones.DefinirHabitacion(Habitaciones.get(i));
//
//                column++;
//                GridPanel_Btn_Habitaciones.add(PanelHabitaciones,column,row);
//                GridPane.setMargin(PanelHabitaciones,new Insets(7));
//
//                //Alto y Ancho:
//                //Ancho:
//                GridPanel_Btn_Habitaciones.setMaxWidth(Region.USE_COMPUTED_SIZE);
//                GridPanel_Btn_Habitaciones.setPrefWidth(Region.USE_COMPUTED_SIZE);
//                GridPanel_Btn_Habitaciones.setMinWidth(Region.USE_COMPUTED_SIZE);
//
//                //Alto:
//                GridPanel_Btn_Habitaciones.setMaxHeight(Region.USE_COMPUTED_SIZE);
//                GridPanel_Btn_Habitaciones.setPrefHeight(Region.USE_COMPUTED_SIZE);
//                GridPanel_Btn_Habitaciones.setMinHeight(Region.USE_COMPUTED_SIZE);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
