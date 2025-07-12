package Modelo;

import java.sql.Date;
/**
 *
 * @author JFAA
 */
public class Lote {
    int idlote;
    String nrolote;
    String idproducto;
    String descripcion;
    double cantidad;
    Date fechaVence;

    public int getIdlote() {
        return idlote;
    }

    public void setIdlote(int idlote) {
        this.idlote = idlote;
    }

    public String getNrolote() {
        return nrolote;
    }

    public void setNrolote(String nrolote) {
        this.nrolote = nrolote;
    }

    public String getIdproducto() {
        return idproducto;
    }

    public void setIdproducto(String idproducto) {
        this.idproducto = idproducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaVence() {
        return fechaVence;
    }

    public void setFechaVence(Date fechaVence) {
        this.fechaVence = fechaVence;
    }
    
}
