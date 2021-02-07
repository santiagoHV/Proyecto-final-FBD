package Controlador.vistas_empleado;

import Vista.Main;
import com.jfoenix.controls.*;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.util.EventObject;
import java.util.ResourceBundle;

import static java.lang.Integer.*;

public class Controlador_empleado implements Initializable {


    private @FXML StackPane empleado;
    private @FXML Label RC1,RN1,RP1;
    private @FXML JFXTextArea REMuestra,RCA1;
    private @FXML JFXButton RCAR1,RPoner,RLimpiar;
    private @FXML AnchorPane RPane,PPane;
    private @FXML ComboBox<String> RDHabitacion,RDPiso;
    private int Total=0;


   //Se genera las dos listas que van a servir para todos los botones
    ObservableList<String> comboPisoContent =
            FXCollections.observableArrayList(
                    "2","3","4","5","6","7","8","9"
            );
    ObservableList<String> comboHabitacionContent =
            FXCollections.observableArrayList(
                    "01","02","03","04","05","06"
            );
    //Se insertan las listas a la interfaz grafica
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RDPiso.setItems(comboPisoContent);
        RDHabitacion.setItems(comboHabitacionContent);

    }

    //Aquí se determinan las acciones que va a realiza un boton al hacer click sobre el
    public void onRestauranteButtonClicked(javafx.scene.input.MouseEvent event) {
            if(RPane.isVisible()){
                RPane.setVisible(false);
            }
            RPane.setVisible(true);
            PPane.setVisible(false);
    }

    public void onPiscinaButtonClicked(javafx.scene.input.MouseEvent event) {
            RPane.setVisible(false);
            PPane.setVisible(true);
    }

    public void onCafeteriaButtonClicked(javafx.scene.input.MouseEvent event) {

    }


    public void onSalirButtonClicked(javafx.scene.input.MouseEvent event){
        //Cierre de sesión temporal hasta que se implementen usuarios en el sistema con la base de datos:

        //Usando JFXDialog:
        JFXDialogLayout EstrucMensaje = new JFXDialogLayout();
        EstrucMensaje.setBody(new Text("¿Está seguro de que quiere cerrar sesión?"));
        JFXButton BotonAceptar = new JFXButton("Sí, me quiero ir");
        JFXButton BotonCancelar = new JFXButton("No, me pagan horas extra");

        JFXDialog DialogoCerrar = new JFXDialog(empleado,EstrucMensaje, JFXDialog.DialogTransition.BOTTOM);

        BotonCancelar.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (javafx.scene.input.MouseEvent mouseEvent)->
        {
            DialogoCerrar.close();
        });

        BotonAceptar.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->
        {
            Stage stage = (Stage) BotonAceptar.getScene().getWindow();
            stage.close();

            Stage stageMenuInicio = new Stage();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("../Vista/ingreso/MenuInicio.fxml"));
            Pane ventana = null;

            //Try Catch creado por Intellij
            try {
                ventana = (Pane) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Show the scene containing the root layout.

            Scene scene = new Scene(ventana);
            scene.setFill(Color.TRANSPARENT);
            stageMenuInicio.setTitle("Menu Principal");
            stageMenuInicio.setScene(scene);
            //Undecorated y No Resizable:
            stageMenuInicio.setResizable(false);
            stageMenuInicio.initStyle(StageStyle.UNDECORATED);
            stageMenuInicio.initStyle(StageStyle.TRANSPARENT);

            //Mostrar Stage:
            stageMenuInicio.show();

            DialogoCerrar.close();
        });

        EstrucMensaje.setActions(BotonCancelar, BotonAceptar);
        DialogoCerrar.show();
    }


    public void onCargar1ButtonClicked(MouseEvent event) {
        REMuestra.appendText("No: "+RC1.getText()+" - "+RN1.getText()+" $ "+(parseInt(RP1.getText())*parseInt(RCA1.getText()))+ "\n");
        Total= Total+(parseInt(RP1.getText())*parseInt(RCA1.getText()));
    }

    public void onClickedCargar(MouseEvent event){
        REMuestra.appendText( "----------------------------\n");
        REMuestra.appendText( "Total:" + Total);
    }
    public void onClickedHabitacion(MouseEvent event){
            REMuestra.appendText( "Habitación No: " +  RDPiso.getValue() + RDHabitacion.getValue() + "\n");
    }

    public void onClickedLimpiar(MouseEvent event){
            REMuestra.clear();
    }

}
