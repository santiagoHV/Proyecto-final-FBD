package Controlador.vistas_recepcionista;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class Controlador_datos_ingreso implements Initializable {

    //OBJETOS FXML
    //Panel para nuevos usuarios
    public Pane backPanelNuevo;
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
    public Pane backPanelBuscar;
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
    public StackPane stack_pane;

    public String nombre;
    public CheckBox checkNuevoUsuario;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tipo_documento_in.getItems().add("CC");
        tipo_documento_in.getItems().add("CE");
        tipo_documento_in.getItems().add("RC");
        tipo_documento_in.getItems().add("TI");
    }

    public void close(ActionEvent actionEvent) {

    }

    public void select(ActionEvent actionEvent) {

    }
    public void validarNuevosDatos(ActionEvent actionEvent) {

        String tipoDoc = ;
        int noDocumento = Integer.parseInt(no_documento_in.getText());
        String telefono = telefono_in.getText();
        LocalDate fNacimiento = ;
        String direccion = direccion_in.getText();

        if(nombres_in.getText() == null){

        }else if(apellidos_in.getText() == null){

        }else if(validarFechaYDocumento((String) tipo_documento_in.getValue(), fecha_nacimiento_in.getValue())){

        }
    }

    private boolean validarFechaYDocumento(String tipoDoc, LocalDate fecha) {
        LocalDate fechaActual = LocalDate.now();
        if(fechaActual.isBefore(fecha)){
            return false;
        }else if(fechaActual.getYear() - fecha.getYear() >= 18 && (tipoDoc.equals("CC") || tipoDoc.equals("CE"))){

        }else{
            return false;
        }
        return false;
    }

    public void enablePanel(MouseEvent mouseEvent) {
        if(mouseEvent.getSource().equals(backPanelBuscar)){
            panel_busqueda.setDisable(false);
            panel_nuevo_ingreso.setDisable(true);
        }else{
            panel_busqueda.setDisable(true);
            panel_nuevo_ingreso.setDisable(false);
        }
    }


}
