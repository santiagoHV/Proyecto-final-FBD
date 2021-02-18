package Controlador.vistas_gerente.Productos;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_modificador implements Initializable {
    public JFXButton BSalirDialog;
    public Label title_label;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void modificar_valor(){
        if(title_label.equals("Modificar Stock")){

        }else if(title_label.equals("Editar predio de venta")){

        }else{
            JOptionPane.showMessageDialog(null, "Algo anda mal...");
        }
    }

}
