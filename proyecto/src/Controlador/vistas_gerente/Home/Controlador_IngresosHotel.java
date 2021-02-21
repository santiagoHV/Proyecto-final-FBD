package Controlador.vistas_gerente.Home;

import DatosSQL.DAOs.DAO_Pago;
import DatosSQL.DAOs.DAO_PyS;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controlador_IngresosHotel implements Initializable {
    public JFXComboBox<String> combo;
    public ProgressIndicator progresi;
    public Label label_ingreso_productos;
    public Label labe_total_pagos;
    public Label label_total_productos;
    public int total_pagos;
    public int total_productos;
    public double monto_productos;
    public Label label_ingresos;
    public double ingresos_totales;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        progresi.setVisible(false);
        ArrayList<String> opc = new ArrayList<>();
        opc.add("Siempre");
        opc.add("Ultimo Año");
        opc.add("Ultimo Mes");
        opc.add("Ultima Semana");
        opc.add("Hoy");
        ObservableList<String> obs = FXCollections.observableArrayList(opc);
        combo.setItems(obs);
        combo.getSelectionModel().selectFirst();
        loadDatos();
    }

    public void loadDatos(){
        progresi.setVisible(true);
        Task tareita = new Task() {
            @Override
            protected Object call() throws Exception {
                DAO_Pago dao = new DAO_Pago();
                if(combo.getSelectionModel().getSelectedItem().equals("Siempre") ){
                    monto_productos = dao.consultarMontoProductos();
                    total_pagos = dao.totalPagosRealizados();
                    total_productos = dao.totalProductosVendidos();
                    ingresos_totales = dao.VentaTotal();
                }else{
                    monto_productos = dao.consultarMontoProductos(combo.getSelectionModel().getSelectedItem());
                    total_pagos = dao.totalPagosRealizados(combo.getSelectionModel().getSelectedItem());
                    total_productos = dao.totalProductosVendidos(combo.getSelectionModel().getSelectedItem());
                    ingresos_totales = dao.VentaTotal(combo.getSelectionModel().getSelectedItem());
                }
                return null;
            }
        };
        tareita.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent workerStateEvent) {
                label_ingreso_productos.setText("Total ingresos productos: "+ String.format("$ %(,.2f",monto_productos));
                labe_total_pagos.setText("Total pagos realizados: " +total_pagos);
                label_total_productos.setText("Total productos vendidos: " + total_productos);
                label_ingresos.setText("INGRESOS TOTALES: " + String.format("$ %(,.2f",ingresos_totales));
                progresi.setVisible(false);
            }
        });
        Thread elhilito = new Thread(tareita);
        elhilito.start();
    }
}
