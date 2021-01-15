package Controlador.vistas_recepcionista;

import Modelo.Habitacion;
import Modelo.Huesped;
import Vista.Main;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DefinirPanelDatosHuespedes();
        DefinirBotonesHabitacion();
    }


    //Esto es temporal:
    public List<Huesped> ConsultarHuespedes()
    {
        List<Huesped> Huespedes = new ArrayList<>();
        Huesped huesped;

        //Ciclo Temporal para llenar la interfaz mientras se conecta con la base de datos:
        for(int i=0; i<6;i++)
        {
            huesped = new Huesped();
            huesped.setK_identificacion("10000000");
            huesped.setN_direccion("Cualquier Calle");
            huesped.setF_nacimiento("20");
            huesped.setN_nombre("Juanito");
            huesped.setN_apellido("Vainas");
            huesped.setN_telefono("123");

            Huespedes.add(huesped);
        }

        return Huespedes;
    }

    //Esto también es temporal:
    public List<Habitacion> ConsultarHabitacion()
    {
        List<Habitacion> Habitaciones = new ArrayList<>();
        Habitacion habitacion;

        //Datos temporales, mientras se conecta la base de datos:
        habitacion = new Habitacion();
        habitacion.setK_numero_habitacion(102);
        Habitaciones.add(habitacion);

        habitacion = new Habitacion();
        habitacion.setK_numero_habitacion(210);
        Habitaciones.add(habitacion);

        habitacion = new Habitacion();
        habitacion.setK_numero_habitacion(209);
        Habitaciones.add(habitacion);

        habitacion = new Habitacion();
        habitacion.setK_numero_habitacion(106);
        Habitaciones.add(habitacion);

        habitacion = new Habitacion();
        habitacion.setK_numero_habitacion(101);
        Habitaciones.add(habitacion);

        habitacion = new Habitacion();
        habitacion.setK_numero_habitacion(202);
        Habitaciones.add(habitacion);

        habitacion = new Habitacion();
        habitacion.setK_numero_habitacion(204);
        Habitaciones.add(habitacion);

        habitacion = new Habitacion();
        habitacion.setK_numero_habitacion(205);
        Habitaciones.add(habitacion);

        return Habitaciones;
    }

    public void DefinirPanelDatosHuespedes()
    {
        List<Huesped> Huespedes = ConsultarHuespedes();

        int column = 0;
        int row = 0;

        //El i<6 es arbitrario mientras se añade la base de datos
        try
        {
            for(int i=0; i<6;i++) {
                //Carga de las plantillas para los paneles con información de los huespedes
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(Main.class.getResource("../Vista/recepcionista/Panel_Huesped.fxml"));

                //Definición de los anchorpane:
                //Para el panel de los huespedes:
                AnchorPane PanelHuespedes = loader.load();

                Controlador_Huesped controlador_huesped = loader.getController();
                controlador_huesped.setValoresPanel(Huespedes.get(i));

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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void DefinirBotonesHabitacion()
    {
        List<Habitacion> Habitaciones = ConsultarHabitacion();

        int column = 0;
        int row = 0;
        try
        {
            for(int i=0; i<8;i++) {
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
