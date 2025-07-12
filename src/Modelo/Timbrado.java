/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Date;

/**
 *
 * @author JFAA
 */
public class Timbrado {
    int id;
    int numero_timbrado; 
    Date fecha_desde;
    Date fecha_Hasta;
    String observación;    
    String estado;
    int nroFacturaInicial;
    int nroFacturaFinal;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero_timbrado() {
        return numero_timbrado;
    }

    public void setNumero_timbrado(int numero_timbrado) {
        this.numero_timbrado = numero_timbrado;
    }

    public Date getFecha_desde() {
        return fecha_desde;
    }

    public void setFecha_desde(Date fecha_desde) {
        this.fecha_desde = fecha_desde;
    }

    public Date getFecha_Hasta() {
        return fecha_Hasta;
    }

    public void setFecha_Hasta(Date fecha_Hasta) {
        this.fecha_Hasta = fecha_Hasta;
    }

    public String getObservación() {
        return observación;
    }

    public void setObservación(String observación) {
        this.observación = observación;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getNroFacturaInicial() {
        return nroFacturaInicial;
    }

    public void setNroFacturaInicial(int nroFacturaInicial) {
        this.nroFacturaInicial = nroFacturaInicial;
    }

    public int getNroFacturaFinal() {
        return nroFacturaFinal;
    }

    public void setNroFacturaFinal(int nroFacturaFinal) {
        this.nroFacturaFinal = nroFacturaFinal;
    }
    
}
