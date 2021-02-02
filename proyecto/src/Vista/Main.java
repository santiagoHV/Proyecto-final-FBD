package Vista;

import Modelo.entidades.Condicion_Hotel;
import Modelo.entidades.Huesped;
import Modelo.entidades.Persona;
import Modelo.entidades.Reserva;
import animatefx.animation.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    public static Stage stage = null;

    @Override
    public void start(Stage primaryStage) throws Exception {

        //Forma 1 (Youtube)
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("./ingreso/MenuInicio.fxml"));
        Pane ventana = (Pane) loader.load();

        //Show the scene containing the root layout.

        Scene scene = new Scene(ventana);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.setTitle("Menu De Inicio");
        primaryStage.setScene(scene);
        //Undecorated y No Resizable:
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        //Método para poder arrastrar la ventana:
        this.stage = primaryStage;

        //Mostrar Stage:
        primaryStage.show();

        //REBOTE SEXY:
        //new BounceInDown(ventana).play();

        new Pulse(ventana).play();
    }
}