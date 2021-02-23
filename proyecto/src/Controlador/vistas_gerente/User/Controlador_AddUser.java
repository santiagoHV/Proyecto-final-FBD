package Controlador.vistas_gerente.User;

import Controlador.Alerta;
import Datos_NoSQL.Usuario;
import Datos_NoSQL.UsuarioDAO;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import javax.swing.SingleSelectionModel;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controlador_AddUser implements Initializable {

    public UsuarioDAO db;
    public TextField l_username;
    public ComboBox combobox;
    public Button add_button;
    public PasswordField l_password2;
    public PasswordField l_password;
    public ProgressIndicator progress;
    public JFXButton cancelar_button;
    public StackPane stack;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        db = new UsuarioDAO();
        ArrayList<String> opts = new ArrayList<>();
        opts.add("Recepcionista");
        opts.add("Trabajador");
        opts.add("Gerente");
        opts.add("Administrador");
        ObservableList<String> obs = FXCollections.observableArrayList(opts);
        combobox.setItems(obs);
        progress.setVisible(false);
    }

    public void addUser(){
        if(l_username.getText().isBlank() || l_password.getText().isBlank() || l_password2.getText().isBlank() || combobox.getValue() == null ){
            new Alerta("Advertencia!","Ingrese todos los datos", stack);
        }else{
            if(l_password.getText().equals(l_password2.getText())){
                String role = convPalabras(combobox.getValue().toString());
                Usuario user = new Usuario(l_username.getText(), l_password.getText(), role);
                addUserDB(user);
                new Alerta("Creado!","Usuario creado con exito", stack);
            }else{
                new Alerta("Error!","Las contraseñas deben coincidir", stack);
            }
        }
    }

    public String convPalabras(String x){
        if (x.equals("Recepcionista")) {
            return "recept";
        } else if (x.equals("Trabajador")) {
            return "worker";
        } else if (x.equals("Gerente")) {
            return "gerente";
        } else if (x.equals("Administrador")) {
            return "admin";
        }else{
            return "";
        }
    }

    public void addUserDB(Usuario user){
        progress.setVisible(true);
        Task<Void> tareita = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    boolean agregado = db.addUsuario(user);
                    progress.setVisible(false);
                    if(agregado){
                        new Alerta("Creado!","Usuario creado con exito!", stack);
                        l_username.setText("");
                        l_password.setText("");
                        l_password2.setText("");
                    }else{
                        new Alerta("Error!","Este usuario ya existe!", stack);
                    }
                }catch (Exception e){
                    progress.setVisible(false);
                    new Alerta("Error Critico!","Error al crear el usuario, intente luego", stack);

                }
                return null;
            }
        };
        tareita.setOnSucceeded(new EventHandler<WorkerStateEvent>(){
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {

            }
        });
        Thread hola2 = new Thread(tareita);
        hola2.start();
    }
}
