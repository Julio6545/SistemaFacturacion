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
public class Venta {
    int idFactura;
    int idTimbrado;    
    int nroFactura;
    int nroPresupuesto;
    String idCliente;
    String idUsuario;
    String idVendedor;
    Date fecha;
    Date fechaCierre;
    Time hora;
    int numCaja;
    int tipoPago;
    int totalExcenta;
    int totalIva5;
    int totalIva10;
    int totalF;
    int ganancia;
    int cancelado;
    String estado;
    int procesado;
    int idArqueo;
    int descuento;
    int totalDescuento;
    int totalCobrar;
    int totalGanancia;

    public int getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(int idFactura) {
        this.idFactura = idFactura;
    }
    public int getIdTimbrado() {
        return idTimbrado;
    }

    public void setIdTimbrado(int idTimbrado) {
        this.idTimbrado = idTimbrado;
    }
    public int getNroFactura() {
        return nroFactura;
    }

    public void setNroFactura(int nroFactura) {
        this.nroFactura = nroFactura;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public int getNroPresupuesto() {
        return nroPresupuesto;
    }

    public void setNroPresupuesto(int nroPresupuesto) {
        this.nroPresupuesto = nroPresupuesto;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getFechaCierre() {
        return fechaCierre;
    }

    public void setFechaCierre(Date fechaCierre) {
        this.fechaCierre = fechaCierre;
    }
    
    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public int getNumCaja() {
        return numCaja;
    }

    public void setNumCaja(int numCaja) {
        this.numCaja = numCaja;
    }

    public int getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(int tipoPago) {
        this.tipoPago = tipoPago;
    }

    public int getTotalExcenta() {
        return totalExcenta;
    }

    public void setTotalExcenta(int totalExcenta) {
        this.totalExcenta = totalExcenta;
    }

    public int getTotalIva5() {
        return totalIva5;
    }

    public void setTotalIva5(int totalIva5) {
        this.totalIva5 = totalIva5;
    }

    public int getTotalIva10() {
        return totalIva10;
    }

    public void setTotalIva10(int totalIva10) {
        this.totalIva10 = totalIva10;
    }

    public int getTotalF() {
        return totalF;
    }

    public void setTotalF(int totalF) {
        this.totalF = totalF;
    }

    public int getGanancia() {
        return ganancia;
    }

    public void setGanancia(int ganancia) {
        this.ganancia = ganancia;
    }

    public int getCancelado() {
        return cancelado;
    }

    public void setCancelado(int cancelado) {
        this.cancelado = cancelado;
    }    

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getProcesado() {
        return procesado;
    }

    public void setProcesado(int procesado) {
        this.procesado = procesado;
    }

    public int getIdArqueo() {
        return idArqueo;
    }

    public void setIdArqueo(int idArqueo) {
        this.idArqueo = idArqueo;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }

    public int getTotalDescuento() {
        return totalDescuento;
    }

    public void setTotalDescuento(int totalDescuento) {
        this.totalDescuento = totalDescuento;
    }

    public int getTotalCobrar() {
        return totalCobrar;
    }

    public void setTotalCobrar(int totalCobrar) {
        this.totalCobrar = totalCobrar;
    }

    public int getTotalGanancia() {
        return totalGanancia;
    }

    public void setTotalGanancia(int totalGanancia) {
        this.totalGanancia = totalGanancia;
    }
    
    
}
