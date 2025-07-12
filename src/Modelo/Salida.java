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
public class Salida {
    int idSalida;
    int nroSalida;
    int idCliente;
    String idUsuario;
    Date fecha;
    Time hora;    
    int tipoSalida;
    int totalS;

    public int getIdSalida() {
        return idSalida;
    }

    public void setIdSalida(int idSalida) {
        this.idSalida = idSalida;
    }

    public int getNroSalida() {
        return nroSalida;
    }

    public void setNroSalida(int nroSalida) {
        this.nroSalida = nroSalida;
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

    public int getTipoSalida() {
        return tipoSalida;
    }

    public void setTipoSalida(int tipoSalida) {
        this.tipoSalida = tipoSalida;
    }

    public int getTotalS() {
        return totalS;
    }

    public void setTotalS(int totalS) {
        this.totalS = totalS;
    }
    
    
}