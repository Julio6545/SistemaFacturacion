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
public class Cierre {
    int idcierre;
    int nrocierre;
    int nroCaja;
    String usuario;
    Date fechaCierre, fechaActividad;    
    Time hora;
    int totalCredito;
    int totalContado;
    int total;
    int ganancia;

    public int getIdcierre() {
        return idcierre;
    }

    public int getNrocierre() {
        return nrocierre;
    }

    public void setNrocierre(int nrocierre) {
        this.nrocierre = nrocierre;
    }

    public void setIdcierre(int idcierre) {
        this.idcierre = idcierre;
    }
    
    public int getNroCaja() {
        return nroCaja;
    }

    public void setNroCaja(int nroCaja) {
        this.nroCaja = nroCaja;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
    
    public Date getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(Date fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getTotalCredito() {
        return totalCredito;
    }

    public void setTotalCredito(int totalCredito) {
        this.totalCredito = totalCredito;
    }

    public int getTotalContado() {
        return totalContado;
    }

    public void setTotalContado(int totalContado) {
        this.totalContado = totalContado;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getGanancia() {
        return ganancia;
    }

    public void setGanancia(int ganancia) {
        this.ganancia = ganancia;
    }
      
}
