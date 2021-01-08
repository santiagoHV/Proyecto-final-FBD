package Controlador;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_datos_ingreso implements Initializable {
    public Pane panel_nuevo_ingreso;
    public TextField nombres_in;
    public TextField apellidos_in;
    public ComboBox tipo_documento_in;
    public TextField telefono_in;
    public TextField no_documento_in;
    public DatePicker fecha_nacimiento_in;
    public Button validar_btn;
    public TextField direccion_in;
    public TextField srch_no_documento_in;
    public ComboBox srch_tipo_documento_in;
    public Pane panel_busqueda;
    public Pane subpanel_usuarios;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
