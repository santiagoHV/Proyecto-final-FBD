package Controlador.vistas_gerente;

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
import javafx.stage.Stage;

import javax.swing.*;
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
            JOptionPane.showMessageDialog(null, "Ingrese todos los datos");
        }else{
            if(l_password.getText().equals(l_password2.getText())){
                String role = convPalabras(combobox.getValue().toString());
                Usuario user = new Usuario(l_username.getText(), l_password.getText(), role);
                addUserDB(user);
            }else{
                JOptionPane.showMessageDialog(null,"Las contraseñas deben coincidir");
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
                    db.addUsuario(user);
                    progress.setVisible(false);
                    JOptionPane.showMessageDialog(null,"Usuario creado con exito!");
                }catch (Exception e){
                    progress.setVisible(false);
                    JOptionPane.showMessageDialog(null, "Error al crear el usuario, intente luego.");
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
