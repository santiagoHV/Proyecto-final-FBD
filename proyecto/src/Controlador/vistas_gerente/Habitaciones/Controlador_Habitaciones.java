package Controlador.vistas_gerente.Habitaciones;

import DatosSQL.DAOs.DAO_Habitacion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_Habitaciones implements Initializable {
    public StackPane stackPane1;
    public AnchorPane content;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void abrirDialog(javafx.event.ActionEvent actionEvent) throws IOException {
        String habitacion = actionEvent.getSource().toString().substring(actionEvent.getSource().toString().length()-4, actionEvent.getSource().toString().length()-1);
        BoxBlur blur = new BoxBlur(3, 3, 3);
        System.out.println(habitacion);
        Parent parent;
        parent = FXMLLoader.load(getClass().getResource("../../../Vista/gerente/dialog_historial_ocupacion.fxml"));
        JFXDialog dialog = new JFXDialog(stackPane1, (Region) parent, JFXDialog.DialogTransition.BOTTOM, true);
        AnchorPane AP = (AnchorPane) parent.getChildrenUnmodifiable().get(0);
        ((Label) AP.getChildrenUnmodifiable().get(4)).setText(habitacion);
        JFXButton BSalirDialog = (JFXButton)AP.getChildrenUnmodifiable().get(1);
        BSalirDialog.addEventHandler(MouseEvent.MOUSE_CLICKED, (MouseEvent mouseEvent)->
        {
            dialog.close();
        });
        dialog.setOnDialogClosed((JFXDialogEvent event)->
        {
            content.setEffect(null);
        });
        content.setEffect(blur);

        dialog.show();
    }

    private void setEstilo(String id){
        if(new DAO_Habitacion().estadoHabitacion(id).equals("ocupado")){

        }else{

        }
    }
}
