package Modelo;

/**
 *
 * @author JFAA
 */
public class Usuario extends Persona{
    private String nombreusu;
    private String clave;
    private int roll;
    
    public Usuario() {
    }
    
    public String getNombreusu() {
        return nombreusu;
    }

    public void setNombreusu(String nombreusu) {
        this.nombreusu = nombreusu;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getRol() {
        return roll;
    }

    public void setRol(int rol) {
        this.roll = rol;
    }

    
    
    
}
