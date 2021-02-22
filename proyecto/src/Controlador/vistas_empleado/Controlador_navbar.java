package Controlador.vistas_empleado;

import DatosSQL.DAOs.DAO_PyS;
import Modelo.entidades.PyS;
import Vista.Main;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_navbar implements Initializable {
    @FXML
    private StackPane ruta;
    @FXML
    private AnchorPane ERuta,Enavegar;
    @FXML
    private JFXButton BRestaurante,BPiscina,BCafeteria,ESalir;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setRuta("../Vista/empleado/piscina.fxml");
    }

    public void navigate(ActionEvent actionEvent) {

        if (actionEvent.getSource() == BRestaurante) {
            setRuta("../Vista/empleado/piscina.fxml");
        } else if (actionEvent.getSource() == BPiscina) {
            setRuta("../Vista/empleado/piscina.fxml");
        } else if (actionEvent.getSource() == BCafeteria) {
            setRuta("../Vista/empleado/piscina.fxml");
        }
    }

    public void setRuta(String url) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource(url));
        Node Contenido = null;
        try {
            Contenido = (Node) loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Enavegar.getChildren().setAll(Contenido);
    }

    public void onSalirButtonClicked(javafx.scene.input.MouseEvent event){
        //Cierre de sesión temporal hasta que se implementen usuarios en el sistema con la base de datos:

        //Usando JFXDialog:
        JFXDialogLayout EstrucMensaje = new JFXDialogLayout();
        EstrucMensaje.setBody(new Text("¿Está seguro de que quiere cerrar sesión?"));
        JFXButton BotonAceptar = new JFXButton("Sí, me quiero ir");
        JFXButton BotonCancelar = new JFXButton("No, me pagan horas extra");

        JFXDialog DialogoCerrar = new JFXDialog(ruta,EstrucMensaje, JFXDialog.DialogTransition.BOTTOM);

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
}
