package Controlador;

import Datos_NoSQL.DBMethods;
import Vista.Main;
import animatefx.animation.Shake;
import com.jfoenix.controls.*;
import com.jfoenix.controls.events.JFXDialogEvent;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.effect.BoxBlur;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.bson.Document;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Controlador_Login implements Initializable
{
    public AnchorPane BGStackPane;
    public JFXButton BIniSes;
    public Label JLabel_TUsuario;
    public JFXTextField TUsuario;
    public JFXPasswordField TContrasena;
    public StackPane StackPane1;
    private double xOffset = 0;
    private double yOffset = 0;
    private DBMethods db;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        //Invocación del método para volver el stage arrastrable:
        db = new DBMethods();
        this.makeStageDragable();
    }

    //Método para arrastrar la ventana
    private void makeStageDragable()
    {
        //obtiene posicion de click
        BGStackPane.setOnMousePressed((mouseEvent ->
        {
            xOffset = mouseEvent.getSceneX();
            yOffset = mouseEvent.getSceneY();
        }));

        BGStackPane.setOnMouseDragged((mouseEvent ->
        {
            Main.stage.setX(mouseEvent.getScreenX()-xOffset);
            Main.stage.setY(mouseEvent.getScreenY()-yOffset);

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
    }

    public void Click(ActionEvent actionEvent) throws IOException, SQLException, InterruptedException {
        if(authUsuario()){
            //Código adicional para el stage de inicio de sesión y el menú de inicio
            JFXButton BotonAceptar = (JFXButton) actionEvent.getSource();
            Stage dialogActual = (Stage) BotonAceptar.getScene().getWindow();
            dialogActual.close();
        }
    }

    // Toma los datos ingresados y los compara con los de la base de datos
    // (Si el usuario es admin le dara acceso a recepcionista desde cualquier rol)
    public boolean authUsuario() throws IOException, InterruptedException {
        String rol = getRoleKey(JLabel_TUsuario.getText());
        new _Cargar().show();
        Thread.sleep(3000);
        Document userx = db.searchUser(TUsuario.getText());

        if(userx != null && (userx.get("role").equals(rol) || userx.get("role").equals("admin"))){
            boolean correct = TContrasena.getText().equals(userx.get("password"));
            if(userx.get("role").equals("recept") && correct){
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
                return true;
            }
            else if(userx.get("role").equals("worker") && correct){
                JOptionPane.showMessageDialog(null, "Aun no existe este menu");
                return false;
            }
            else if(userx.get("role").equals("gerente") && correct){
                JOptionPane.showMessageDialog(null, "Aun no existe este menu");
                return false;
            }
            else if(userx.get("role").equals("admin") && correct){
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
                return true;
            }
            else{
                JOptionPane.showMessageDialog(null,"Contraseña incorrecta");
                return false;
            }
        }else{
            JOptionPane.showMessageDialog(null,"Este usuario no existe en este rol");
            return false;
        }
    }
    //Convierte el rol visual al que se maneja en la DB
    public String getRoleKey(String role){
        if(role.equals("Recepcionista")){
            return "recept";
        }
        else if(role.equals("Empleado")){
            return "worker";
        }
        else if(role.equals("Administrador")){
            return "gerente";
        }
        else{
            return null;
        }
    }

    public void AbrirProgress() throws IOException {

        //Creación del efecto blur:
            BoxBlur blur = new BoxBlur(5,5,3);

            //Obtención del parent con la ruta del fxml a usar
            Parent parent = FXMLLoader.load(getClass().getResource("../Vista/Loading_Screen.fxml"));
            System.out.println(parent.getStyle());

            //Creación del Dialog usando el Parent como Region (cast) para poder personalizarlo:
            JFXDialog dialog = new JFXDialog();
            dialog.setContent((Region) parent);

            File f = new File("assets/css/ProgressStyle.css");
            dialog.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));

            dialog.setOnDialogClosed((JFXDialogEvent event)->
            {
                BGStackPane.setEffect(null);
            });

            //Aplicación del efecto
            BGStackPane.setEffect(blur);

            //Se muestra el dialog:
            dialog.show(StackPane1);
    }
    public class _Cargar implements Runnable{
        public void show(){
            new Thread(this).start();
        }
        public void run() {
            try {
                AbrirProgress();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
