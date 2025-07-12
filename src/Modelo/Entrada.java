/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author JFAA
 */
public class Entrada {
    int idEntrada;
    int nroEntrada;
    int idCliente;
    String idUsuario;
    Date fecha;
    Time hora;    
    int tipoEntrada;
    int totalE;
    int nroventa;
    int idarqueo;

    public int getIdEntrada() {
        return idEntrada;
    }

    public void setIdEntrada(int idEntrada) {
        this.idEntrada = idEntrada;
    }

    public int getNroEntrada() {
        return nroEntrada;
    }

    public void setNroEntrada(int nroEntrada) {
        this.nroEntrada = nroEntrada;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getTipoEntrada() {
        return tipoEntrada;
    }

    public void setTipoEntrada(int tipoEntrada) {
        this.tipoEntrada = tipoEntrada;
    }

    public int getTotalE() {
        return totalE;
    }

    public void setTotalE(int totalE) {
        this.totalE = totalE;
    }

    public int getNroventa() {
        return nroventa;
    }

    public void setNroventa(int nroventa) {
        this.nroventa = nroventa;
    }

    public int getIdarqueo() {
        return idarqueo;
    }

    public void setIdarqueo(int idarqueo) {
        this.idarqueo = idarqueo;
    }
    
}