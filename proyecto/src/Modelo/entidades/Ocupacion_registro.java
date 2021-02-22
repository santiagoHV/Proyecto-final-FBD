package Modelo.entidades;

public class Ocupacion_registro {

    private String titular;
    private String fechai;
    private String fechaf;
    private int id_reserva;
    private String estado;

    public Ocupacion_registro(String titular, String fechai, String fechaf, int id_reserva, String estado) {
        this.titular = titular;
        this.fechai = fechai;
        this.fechaf = fechaf;
        this.id_reserva = id_reserva;
        this.estado = estado;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getFechai() {
        return fechai;
    }

    public void setFechai(String fechai) {
        this.fechai = fechai;
    }

    public String getFechaf() {
        return fechaf;
    }

    public void setFechaf(String fechaf) {
        this.fechaf = fechaf;
    }

    public int getId_reserva() {
        return id_reserva;
    }

    public void setId_reserva(int id_reserva) {
        this.id_reserva = id_reserva;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
