package Controlador.vistas_gerente.Productos;

import Controlador.Alerta;
import DatosSQL.DAOs.DAO_PyS;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_modificador implements Initializable {
    public JFXButton BSalirDialog;
    public Label title_label;
    public Label id_label;
    public JFXTextField value_input;
    public JFXButton ok_button;
    public StackPane stack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void modificar_valor(){
        System.out.println(title_label.getText());
        if(title_label.getText().equals("Modificar Stock")){
            if(new DAO_PyS().modificarStock(id_label.getText(), value_input.getText())){
                new Alerta("Modificado!","Stock modificado con exito", stack);
                BSalirDialog.getOnAction();
            }else{
                new Alerta("Error!","Error al hacer esta operacion", stack);
            }
        }else if(title_label.getText().equals("Editar precio de venta")){
            if(new DAO_PyS().modificarPrecio(id_label.getText(), value_input.getText())){
                new Alerta("Modificado!","Precio modificado con exito", stack);
            }else{
                new Alerta("Error!","Error al hacer esta operacion", stack);
            }
        }else{
            new Alerta("Error Critico!","Algo anda mal...", stack);
        }
    }

}
