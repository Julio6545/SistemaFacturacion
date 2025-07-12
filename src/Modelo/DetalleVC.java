/*
 * Clase que complementa al detalle venta para consulta de ventas del dia
 */
package Modelo;

/**
 *
 * @author JFAA
 */
public class DetalleVC extends DetalleV{
    int tipoPago;
    int ganancia;
    int cancelado;
    int totalnota;
    int nronota;
    int descuento;
    String tipo;

    public int getTipoPago() {
        return tipoPago;
    }

    public void setTipoPago(int tipoPago) {
        this.tipoPago = tipoPago;
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

    public int getTotalnota() {
        return totalnota;
    }

    public void setTotalnota(int totalnota) {
        this.totalnota = totalnota;
    }

    public int getNronota() {
        return nronota;
    }

    public void setNronota(int nronota) {
        this.nronota = nronota;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDescuento() {
        return descuento;
    }

    public void setDescuento(int descuento) {
        this.descuento = descuento;
    }
    
    
}
