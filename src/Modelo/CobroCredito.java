package Modelo;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author JFAA
 */
public class CobroCredito {
    int idcobro;
    int nroventa;
    Date fecha;
    Time hora;
    int monto;
    int pagado;
    String usuario;
    int nroArqueo;

    public int getIdcobro() {
        return idcobro;
    }

    public void setIdcobro(int idcobro) {
        this.idcobro = idcobro;
    }

    public int getNroventa() {
        return nroventa;
    }

    public void setNroventa(int nroventa) {
        this.nroventa = nroventa;
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

    public int getMonto() {
        return monto;
    }

    public void setMonto(int monto) {
        this.monto = monto;
    }

    public int getPagado() {
        return pagado;
    }

    public void setPagado(int saldo) {
        this.pagado = saldo;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public int getNroArqueo() {
        return nroArqueo;
    }

    public void setNroArqueo(int nroArqueo) {
        this.nroArqueo = nroArqueo;
    }
    
}
