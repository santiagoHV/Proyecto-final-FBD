package Controlador.vistas_gerente.Habitaciones;

import DatosSQL.DAOs.DAO_Habitacion;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controlador_Habitaciones implements Initializable {
    public StackPane stackPane1;
    public AnchorPane content;
    public TabPane e;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            mostrarHabitacionesOcupadas(new DAO_Habitacion().consultarHbitacionesOcupadasPorFecha(Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now())));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void abrirDialog(javafx.event.ActionEvent actionEvent) throws IOException {
        String habitacion = actionEvent.getSource().toString().substring(actionEvent.getSource().toString().length()-4, actionEvent.getSource().toString().length()-1);
        BoxBlur blur = new BoxBlur(3, 3, 3);
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

    public void mostrarHabitacionesOcupadas(ArrayList<Integer> habitacionesOcupadas) {
        ObservableList<Tab> Tabs = e.getTabs();
        for (Tab x: Tabs) {
            AnchorPane Contenedor = (AnchorPane) x.getContent();
            VBox vBox = (VBox) Contenedor.getChildren().get(0);
            HBox hBoxSup = (HBox) vBox.getChildren().get(0);
            HBox hBoxInf = (HBox) vBox.getChildren().get(2);
            List<Node> NodosAnchorTab = new ArrayList<>();
            NodosAnchorTab.addAll(hBoxSup.getChildren());
            NodosAnchorTab.addAll(hBoxInf.getChildren());
            for (Node n : NodosAnchorTab) {
                Button BotonConvertido = (Button) n;
                BotonConvertido.getStyleClass().add("");
                for(int habitacion: habitacionesOcupadas){
                    if(BotonConvertido.getId().equals(String.valueOf(habitacion))){
                        BotonConvertido.getStyleClass().set(3 ,"map-red");
                    }
                }
            }
        }
    }

}
