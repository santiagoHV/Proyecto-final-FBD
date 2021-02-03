package Controlador;

import Controlador.vistas_recepcionista.Controlador_Card_Reserva;
import Datos_NoSQL.Usuario;
import Datos_NoSQL.UsuarioDAO;
import Vista.Main;
import animatefx.animation.BounceOut;
import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import com.sun.media.jfxmediaimpl.platform.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.BoxBlur;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controlador_Login implements Initializable {
    public AnchorPane BGStackPane;
    public JFXButton BIniSes;
    public Label JLabel_TUsuario;
    public JFXTextField TUsuario;
    public JFXPasswordField TContrasena;
    public StackPane StackPane1;
    public ProgressIndicator progress;
    private double xOffset = 0;
    private double yOffset = 0;
    private UsuarioDAO db;
    public String AUTH;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Invocación del método para volver el stage arrastrable:
        db = new UsuarioDAO();
        this.makeStageDragable();
    }

    //Método para arrastrar la ventana
    private void makeStageDragable() {
        //obtiene posicion de click
        BGStackPane.setOnMousePressed((mouseEvent ->
        {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        }));

        BGStackPane.setOnMouseDragged((mouseEvent ->
        {
            Main.stage.setX(mouseEvent.getScreenX() - xOffset);
            Main.stage.setY(mouseEvent.getScreenY() - yOffset);

            Main.stage.setOpacity(0.8);
        }));

        BGStackPane.setOnDragDone((dragEvent ->
        {
            Main.stage.setOpacity(1);
        }));

        BGStackPane.setOnMouseReleased(mouseEvent ->
        {
            Main.stage.setOpacity(1);
        });
        progress.setVisible(false);
    }

    /**
     * Metodo que inicia al dar click en ingresar, llama la autenticacion
     * y algunos metodos para modificar la interfaz
     *
     * @param actionEvent
     * @throws IOException
     * @throws SQLException
     * @throws InterruptedException
     */

    public void Click(ActionEvent actionEvent) throws IOException, SQLException, InterruptedException {
        progress.setVisible(true);
        Task tarea = new Task<Void>(){
            @Override
            protected Void call() throws Exception {
                try{
                    AUTH = authUsuario();
                }catch(Exception e) {
                    System.out.println(e);
                }
                return null;
            }

        };
        tarea.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                if(AUTH.equals("recept")){
                    try {
                        openReceptcion();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    JFXButton BotonAceptar = (JFXButton) actionEvent.getSource();
                    Stage dialogActual = (Stage) BotonAceptar.getScene().getWindow();
                    dialogActual.close();
                }
                else if(AUTH.equals("gerente")){

                    JFXButton BotonAceptar = (JFXButton) actionEvent.getSource();
                    Stage dialogActual = (Stage) BotonAceptar.getScene().getWindow();
                    dialogActual.close();
                }
                else if(AUTH.equals("worker")) {
                    JFXButton BotonAceptar = (JFXButton) actionEvent.getSource();
                    Stage dialogActual = (Stage) BotonAceptar.getScene().getWindow();
                    dialogActual.close();
                }
                progress.setVisible(false);

            }
        });
        Thread adios = new Thread(tarea);
        adios.start();
    }




    /**
     * Toma los datos ingresados y dependiendo de la situcion genera unas acciones
     * y datos determinadas
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    public String authUsuario() throws IOException, InterruptedException {
        String rol = getRoleKey(JLabel_TUsuario.getText());
        String result = db.autenticarUsuario(new Usuario(TUsuario.getText(), TContrasena.getText(), rol));
        if (result.equals("auth")) {
            if (rol.equals("recept") || rol.equals("admin")) {
                return "recept";
            } else if (rol.equals("gerente") || rol.equals("admin")) {
                JOptionPane.showMessageDialog(null, "Interfaz en mantenimiento");
                return "gerente";
            } else if (rol.equals("worker") || rol.equals("admin")) {
                JOptionPane.showMessageDialog(null, "Interfaz en mantenimiento");
                return "worker";
            } else {
                JOptionPane.showMessageDialog(null, "Error inesperado 2");
                return "";
            }
        } else if (result.equals("wrong_pass")) {
            JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
            return "";
        } else if (result.equals("not_exist")) {
            JOptionPane.showMessageDialog(null, "Este usuario no existe");
            return "";
        } else if (result.equals("no_permission")) {
            JOptionPane.showMessageDialog(null, "Este usuario no tiene acceso aqui");
            return "";
        } else {
            JOptionPane.showMessageDialog(null, "Error Inesperado");
            return "";
        }
    }


    /**
     * Toma el String de rol que ve en usuario y retorna el String que se maneja
     * en los metodos del DAO
     *
     * @param role
     * @return
     */
    public String getRoleKey(String role) {
        if (role.equals("Recepcionista")) {
            return "recept";
        } else if (role.equals("Empleado")) {
            return "worker";
        } else if (role.equals("Administrador")) {
            return "gerente";
        } else {
            return null;
        }
    }

    /**
     * Abre la interfaz grafica propia del recepcionista
     *
     * @throws IOException
     */
    public void openReceptcion() throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("../Vista/recepcionista/navbar_recepcionista.fxml"));
        Pane ventana = (Pane) loader.load();
        //Show the scene containing the root layout.

        Scene scene = new Scene(ventana);
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Menu Principal");
        stage.setScene(scene);
        //Undecorated y No Resizable:
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initStyle(StageStyle.TRANSPARENT);

        //Mostrar Stage:
        stage.show();
    }
}
