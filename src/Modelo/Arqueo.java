package Modelo;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author JFAA
 */
public class Arqueo {
    int id;
    int caja;    
    String usuarioApertura;
    String usuarioCierre;
    Date fechaApertura;
    Date fechaCierre;
    Time horaApertura;
    Time horaCierre;
    String estado;
    String observacion;
    int totalcredito  ;
    int totalcontado  ;
    int total;
    int ganancia  ;
    int totalDescuento;
    int totalNota;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCaja() {
        return caja;
    }

    public void setCaja(int caja) {
        this.caja = caja;
    }

    public String getUsuarioApertura() {
        return usuarioApertura;
    }

    public void setUsuarioApertura(String usuarioApertura) {
        this.usuarioApertura = usuarioApertura;
    }

    public String getUsuarioCierre() {
        return usuarioCierre;
    }

    public void setUsuarioCierre(String usuarioCierre) {
        this.usuarioCierre = usuarioCierre;
    }

    public Date getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(Date fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }

    public Time getHoraApertura() {
        return horaApertura;
    }

    public void setHoraApertura(Time horaApertura) {
        this.horaApertura = horaApertura;
    }

    public Time getHoraCierre() {
        return horaCierre;
    }

    public void setHoraCierre(Time horaCierre) {
        this.horaCierre = horaCierre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public int getTotalcredito() {
        return totalcredito;
    }

    public void setTotalcredito(int totalcredito) {
        this.totalcredito = totalcredito;
    }

    public int getTotalcontado() {
        return totalcontado;
    }

    public void setTotalcontado(int totalcontado) {
        this.totalcontado = totalcontado;
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

    public int getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(int totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public int getTotalNota() {
        return totalNota;
    }

    public void setTotalNota(int totalNota) {
        this.totalNota = totalNota;
    }
        
}
