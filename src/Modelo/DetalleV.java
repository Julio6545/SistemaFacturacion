/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author JFAA
 */
public class DetalleV {
    int idDetalle;
    int idVenta;
    String idProducto;
    String idLote;
    int precio;
    int cantidad;
    int Excenta;
    int Iva5;
    int Iva10;
    int limite;
    
    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String idProducto) {
        this.idProducto = idProducto;
    }

    public String getIdLote() {
        return idLote;
    }

    public void setIdLote(String idLote) {
        this.idLote = idLote;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getExcenta() {
        return Excenta;
    }

    public void setExcenta(int Excenta) {
        this.Excenta = Excenta;
    }

    public int getIva5() {
        return Iva5;
    }

    public void setIva5(int Iva5) {
        this.Iva5 = Iva5;
    }

    public int getIva10() {
        return Iva10;
    }

    public void setIva10(int Iva10) {
        this.Iva10 = Iva10;
    }

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }
    
}    