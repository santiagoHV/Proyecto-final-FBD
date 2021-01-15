package Controlador.vistas_recepcionista;

import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_datos_ingreso implements Initializable {

    //OBJETOS FXML
    //Panel para nuevos usuarios
    public Pane panel_nuevo_ingreso;
    public Label titulo_ingresos;
    public TextField nombres_in;
    public TextField apellidos_in;
    public ComboBox tipo_documento_in;
    public TextField telefono_in;
    public TextField no_documento_in;
    public DatePicker fecha_nacimiento_in;
    public Button validar_btn;
    public TextField direccion_in;

    //Panel de busqueda de usuarios existentes
    public TextField srch_no_documento_in;
    public ComboBox srch_tipo_documento_in;
    public Pane panel_busqueda;
    //Subpanel de ususario encontrado
    public Pane subpanel_usuarios;
    public Label ti_srch;
    public Label no_identificacion_srch;
    public Label edad_srch;
    public Label telefono_srch;
    public Button srch_editar;
    public Label direccion_srch;

    //Botones de confirmacion general
    public Button seleccionar_usuario_btn;
    public Button cancelar_btn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
