package Controlador.vistas_gerente.Productos;

import DatosSQL.DAOs.DAO_PyS;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_modificador implements Initializable {
    public JFXButton BSalirDialog;
    public Label title_label;
    public Label id_label;
    public JFXTextField value_input;
    public JFXButton ok_button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void modificar_valor(){
        System.out.println(title_label.getText());
        if(title_label.getText().equals("Modificar Stock")){
            if(new DAO_PyS().modificarStock(id_label.getText(), value_input.getText())){
                JOptionPane.showMessageDialog(null,"Stock modificado con exito");
                BSalirDialog.getOnAction();
            }else{
                JOptionPane.showMessageDialog(null, "Error al hacer esta operacion");
            }
        }else if(title_label.getText().equals("Editar precio de venta")){
            if(new DAO_PyS().modificarPrecio(id_label.getText(), value_input.getText())){
                JOptionPane.showMessageDialog(null,"Precio modificado con exito");
            }else{
                JOptionPane.showMessageDialog(null, "Error al hacer esta operacion");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Algo anda mal...");
        }
    }

}
