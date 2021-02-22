package Controlador.vistas_gerente.Home;

import DatosSQL.DAOs.DAO_Pago;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Controlador_IngresosHabitacion implements Initializable {
    public ProgressIndicator progresi;
    public Label label_ing_hab;
    public Label label_prod_vend;
    public Label label_ing_produt;
    public int productos_vendidos;
    public double ingreso_productos;
    public double ingreso_hab;
    public double ingresos_tot;
    public JFXTextField camp_id;
    public Label label_ing_tot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progresi.setVisible(false);
        label_ing_produt.setText("");
        label_prod_vend.setText("");
        label_ing_hab.setText("");
        label_ing_tot.setText("");
    }

    public void cargarDatos() {
        if (!camp_id.getText().isBlank()) {
            progresi.setVisible(true);
            Task tareita = new Task() {
                @Override
                protected Object call() throws Exception {
                    DAO_Pago dao = new DAO_Pago();
                    productos_vendidos = dao.cantidadProductosPorReserva(camp_id.getText());
                    ingreso_productos = dao.ingresosProductosPorReserva(camp_id.getText());
                    ingreso_hab = dao.ingresoHabitacionesPorReserva(camp_id.getText());
                    ingresos_tot = dao.totalIngresosPorReserva(camp_id.getText());
                    return null;
                }
            };
            tareita.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                @Override
                public void handle(WorkerStateEvent workerStateEvent) {
                    if (ingresos_tot != 0) {
                        label_ing_hab.setText("Total ingreso habitaciones: " + String.format("$ %(,.2f", ingreso_hab));
                        label_ing_produt.setText("Total ingresos productos: " + String.format("$ %(,.2f", ingreso_productos));
                        label_prod_vend.setText("Total productos vendidos: " + productos_vendidos);
                        label_ing_tot.setText("INGRESOS TOTALES: " + String.format("$ %(,.2f", ingresos_tot));
                        progresi.setVisible(false);

                    } else {
                        label_prod_vend.setText("Esta reserva aun no se ha pagado");
                        label_ing_produt.setText("o no existe");
                        label_ing_hab.setText("");
                        label_ing_tot.setText("");
                        progresi.setVisible(false);
                    }
                }
            });
            Thread elhilitoromeo = new Thread(tareita);
            elhilitoromeo.start();
        } else {
            JOptionPane.showMessageDialog(null, "Ingrese algun dato");
        }
    }
}
