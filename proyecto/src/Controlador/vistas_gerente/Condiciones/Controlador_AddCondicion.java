package Controlador.vistas_gerente.Condiciones;

import Controlador.Alerta;
import DatosSQL.DAOs.DAO_CondicionHotel;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_AddCondicion implements Initializable {
    public JFXTextField descripcion_textfield;
    public JFXTextField dias_textfield1;
    public Spinner aforo_spinner;
    public Spinner descuento_spinner;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        aforo_spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0));
        descuento_spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0,100,0));
    }

    public void addCondicion(){
        Double aforo = Double.parseDouble(aforo_spinner.getValue().toString())/100;
        Double descuento = Double.parseDouble(descuento_spinner.getValue().toString())/100;
        if(new DAO_CondicionHotel().addCondicion(descuento, aforo, dias_textfield1.getText(),descripcion_textfield.getText())){
            dias_textfield1.setText("");
            descripcion_textfield.setText("");
        }else {
            JOptionPane.showMessageDialog(null, "Ocurrio un error");
        }
    }
}
