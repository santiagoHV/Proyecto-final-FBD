package Controlador.vistas_recepcionista;

import DatosSQL.DAOs.DAO_Huesped;
import DatosSQL.DAOs.DAO_Persona;
import Modelo.entidades.Huesped;
import Modelo.entidades.Persona;
import animatefx.animation.FadeOut;
import animatefx.animation.Shake;
import io.github.palexdev.materialfx.controls.MFXProgressSpinner;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
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
    public MFXProgressSpinner progressIndicatorUser;
    public Label nombreC_srch;

    //Botones de confirmacion general
    public Button seleccionar_usuario_btn;
    public Button cancelar_btn;
    public StackPane stack_pane;

    public String nombre;
    public CheckBox checkNuevoUsuario;

    public Persona personaEncontrada;
    public Huesped huespedEncontrado;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        seleccionar_usuario_btn.setDisable(true);

        tipo_documento_in.getItems().add("CC");
        tipo_documento_in.getItems().add("CE");
        tipo_documento_in.getItems().add("RC");
        tipo_documento_in.getItems().add("TI");

        srch_tipo_documento_in.getItems().add("CC");
        srch_tipo_documento_in.getItems().add("CE");
        srch_tipo_documento_in.getItems().add("RC");
        srch_tipo_documento_in.getItems().add("TI");
    }

    /**
     * busca una persona o huesped en el panel de busqueda
     * @param actionEvent
     */
    public void buscarCliente(ActionEvent actionEvent) throws SQLException {
        if(srch_tipo_documento_in.getValue() != null && !srch_no_documento_in.getText().equals("")){
            if(validarNoDocumento(srch_no_documento_in.getText())){
                progressIndicatorUser.setVisible(true);

                DAO_Persona dao_persona = new DAO_Persona();
                DAO_Huesped dao_huesped = new DAO_Huesped();

                Task<Persona> personaTask = new Task<Persona>() {
                    protected Persona call() throws Exception {
                        return dao_persona.consultarPersona(Integer.parseInt(srch_no_documento_in.getText()), srch_tipo_documento_in.getValue().toString());
                    }
                };

                Task<Huesped> huespedTask = new Task<Huesped>() {
                    @Override
                    protected Huesped call() throws Exception {
                        return dao_huesped.consultarHuesped(Integer.parseInt(srch_no_documento_in.getText()), srch_tipo_documento_in.getValue().toString());
                    }
                };

                Thread threadPersona = new Thread(personaTask);
                Thread threadHuesped = new Thread(huespedTask);

                personaTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent workerStateEvent) {
                        Persona persona = personaTask.getValue();
                        personaEncontrada = persona;

                        if(persona != null){
                            no_identificacion_srch.setText(String.valueOf(persona.getK_identificacion()));
                            nombreC_srch.setText(persona.getN_nombre() + " " + persona.getN_apellido());
                            ti_srch.setText(persona.getK_tipo_documento_id()+":");
                            Period edad = Period.between(persona.getF_nacimiento().toLocalDate(), LocalDate.now());
                            edad_srch.setText(String.valueOf(edad.getYears()));
                            telefono_srch.setText(persona.getN_telefono());

                            subpanel_usuarios.getStyleClass().add("controlValido");
                            seleccionar_usuario_btn.setDisable(false);

                        }else{
                            no_identificacion_srch.setText("--");
                            nombreC_srch.setText("No encontrado");
                            ti_srch.setText("--");
                            edad_srch.setText("--");
                            telefono_srch.setText("--");
                        }
                        progressIndicatorUser.setVisible(false);
                    }
                });

                huespedTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                    @Override
                    public void handle(WorkerStateEvent workerStateEvent) {
                        Huesped huesped = huespedTask.getValue();
                        huespedEncontrado = huesped;

                        if(huesped != null){
                            direccion_srch.setText(huesped.getN_direccion());

                        }else{
                            direccion_srch.setText("--");
                        }
                        threadPersona.start();
                    }
                });
                threadHuesped.start();
            }
        }
    }

    /**
     * obedece al boton validar y verifica que los datos en el panel de nuevos ususarios cumplan con caracteristicas basicas
     * @param actionEvent
     */
    public void validarNuevosDatos(ActionEvent actionEvent) {
        boolean accepted = true;

        if(nombres_in.getText().equals("")){
            nombres_in.getStyleClass().add("controlInvalido");
            new Shake(nombres_in).play();
            accepted = false;
        }
        if(apellidos_in.getText().equals("")){
            apellidos_in.getStyleClass().add("controlInvalido");
            new Shake(apellidos_in).play();
            accepted = false;
        }
        if(tipo_documento_in.getValue() == null){
            tipo_documento_in.getStyleClass().add("controlInvalido");
            new Shake(tipo_documento_in).play();
            accepted = false;
        }
        if(no_documento_in.getText().equals("") || !validarNoDocumento(no_documento_in.getText())){
            no_documento_in.getStyleClass().add("controlInvalido");
            new Shake(no_documento_in).play();
            accepted = false;
        }
        if(telefono_in.getText().equals("")){
            telefono_in.getStyleClass().add("controlInvalido");
            new Shake(telefono_in).play();
            accepted = false;
        }
        if(fecha_nacimiento_in.getValue() != null){
            if(!validarFechaYDocumento((String) tipo_documento_in.getValue(), fecha_nacimiento_in.getValue())) {
                fecha_nacimiento_in.getStyleClass().add("controlInvalido");
                new Shake(fecha_nacimiento_in).play();
                accepted = false;
            }
        }else if(fecha_nacimiento_in.getValue() == null){
            fecha_nacimiento_in.getStyleClass().add("controlInvalido");
            new Shake(fecha_nacimiento_in).play();
            accepted = false;
        }
        if(!direccion_in.isDisable() && direccion_in.getText().equals(""))
        {
            direccion_in.getStyleClass().add("controlInvalido");
            new Shake(direccion_in).play();
            accepted = false;
        }

        //habilitado
        if(accepted){
            nombres_in.getStyleClass().remove("controlInvalido");
            apellidos_in.getStyleClass().remove("controlInvalido");
            tipo_documento_in.getStyleClass().remove("controlInvalido");
            no_documento_in.getStyleClass().remove("controlInvalido");
            telefono_in.getStyleClass().remove("controlInvalido");
            fecha_nacimiento_in.getStyleClass().remove("controlInvalido");

            panel_nuevo_ingreso.getStyleClass().add("controlValido");

            seleccionar_usuario_btn.setDisable(false);
        }
    }

    /**
     * retorna un objeto tipo persona o huesped dependiendo de lo encontrado
     * @param isNew
     * @return
     */
    public Persona solicitarPersona(boolean isNew){
        if(isNew){
            if(direccion_in.getText().equals("")){
                return new Persona(Integer.parseInt(no_documento_in.getText()),tipo_documento_in.getValue().toString(),nombres_in.getText(),
                        apellidos_in.getText(), Date.valueOf(fecha_nacimiento_in.getValue()),telefono_in.getText());
            }else{
                return new Huesped(Integer.parseInt(no_documento_in.getText()),tipo_documento_in.getValue().toString(),nombres_in.getText(),
                        apellidos_in.getText(), Date.valueOf(fecha_nacimiento_in.getValue()),telefono_in.getText(),direccion_in.getText());
            }
        }else {
            if(direccion_srch.getText().equals("--")){
                return personaEncontrada;
            }else{
                return huespedEncontrado;
            }
        }
    }

    /**
     * verifica que el numero de documento ingresado sea unicamente numerico
     * @param numero
     * @return
     */
    private boolean validarNoDocumento(String numero){
        try {
            int numeroInt = Integer.parseInt(numero);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * verifica que el tipo de documento conincida con su fecha de nacimiento
     *
     * REFACTOREAR
     * @param tipoDoc
     * @param fecha
     * @return
     */
    private boolean validarFechaYDocumento(String tipoDoc, LocalDate fecha) {
        LocalDate fechaActual = LocalDate.now();
        if(fechaActual.isBefore(fecha)){
            return false;
        }else if(fechaActual.getYear() - fecha.getYear() > 18 && (tipoDoc.equals("CC") || tipoDoc.equals("CE"))){
            return true;
        }else if(fechaActual.getYear() - fecha.getYear() == 18 && (tipoDoc.equals("CC") || tipoDoc.equals("CE"))){
            if(fechaActual.getMonthValue() >= fecha.getMonthValue()){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     *  habilita e inhabilita los paneles segun selección
     * @param mouseEvent
     */
    public void enablePanel(MouseEvent mouseEvent) {
        if(mouseEvent.getSource().equals(backPanelBuscar)){
            panel_busqueda.setDisable(false);
            panel_nuevo_ingreso.setDisable(true);
            checkNuevoUsuario.setSelected(false);
        }else{
            panel_busqueda.setDisable(true);
            panel_nuevo_ingreso.setDisable(false);
            checkNuevoUsuario.setSelected(true);
        }
    }



    public void close(ActionEvent actionEvent) {

    }

    public void select(ActionEvent actionEvent) {
    }
}
