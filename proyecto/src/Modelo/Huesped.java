package Modelo;

public class Huesped extends Persona
{
    private String n_direccion;
    private Persona persona;

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