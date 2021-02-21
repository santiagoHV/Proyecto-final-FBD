package Controlador.vistas_gerente.Productos;

import DatosSQL.DAOs.DAO_PyS;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controlador_AddProducto implements Initializable {
    public JFXButton BSalirDialog;
    public JFXButton ok_button;
    public JFXTextField nombre_textfield;
    public JFXTextField precio_textfield;
    public JFXComboBox<String> combo;
    public Label title_label;
    public JFXTextField stock_textfield;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<String> categorias = new ArrayList<>();
        categorias.add("Restaurante");
        categorias.add("Piscina");
        categorias.add("Spa");
        categorias.add("Cafeteria");
        categorias.add("Gimnasio");
        ObservableList<String> obs = FXCollections.observableArrayList(categorias);
        combo.setItems(obs);
    }


    public void agregarProducto(ActionEvent actionEvent) {
        if(nombre_textfield.getText().isBlank() || precio_textfield.getText().isBlank() || stock_textfield.getText().isBlank() || combo.getSelectionModel().getSelectedItem() == null){
            JOptionPane.showMessageDialog(null, "Llena todos los campos");
        }else{
            if(new DAO_PyS().agregarProducto(nombre_textfield.getText(), stock_textfield.getText(), precio_textfield.getText(), combo.getSelectionModel().getSelectedItem())){
                JOptionPane.showMessageDialog(null,"Producto creado con exito");
                nombre_textfield.setText("");
                stock_textfield.setText("");
                precio_textfield.setText("");
                combo.getSelectionModel().clearSelection();
            }else{
                JOptionPane.showMessageDialog(null, "Error al crear producto");
            }
        }
    }
}
