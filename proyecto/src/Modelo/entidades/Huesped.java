package Modelo.entidades;

public class Huesped extends Persona
{
    private String n_direccion;
    private Persona persona;

    public Huesped(){
    }

    public Huesped(int k_identificacion, String k_tipo_documento_id, String f_nacimiento, String n_telefono, String n_nombre, String n_apellido, Habitacion habitacion) {
        super(k_identificacion, k_tipo_documento_id, f_nacimiento, n_telefono, n_nombre, n_apellido);
    }

    public String getN_direccion() {
        return n_direccion;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public void setN_direccion(String n_direccion) {
        this.n_direccion = n_direccion;
    }
}