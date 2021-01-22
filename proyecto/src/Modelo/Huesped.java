package Modelo;

public class Huesped extends Persona
{
    private String n_direccion;
    private Persona persona;

    public Huesped(){
    }

    public Huesped(String n_direccion, Persona persona) {
        this.n_direccion = n_direccion;
        this.persona = persona;
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