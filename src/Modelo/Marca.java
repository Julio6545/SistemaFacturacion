/*
 * Calse que modela la entidad Marca de productos
 */
package Modelo;

/**
 *
 * @author JFAA
 */
public class Marca {
    int id;
    String nombre;
    String origen;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
    
}
