package Modelo;

/**
 *
 * @author JFAA
 */
public class PagoCredito extends CobroCredito{
    int nrorecibopago;
    int cicobrador;
    
    public int getNrorecibopago() {
        return nrorecibopago;
    }

    public void setNrorecibopago(int nrorecibopago) {
        this.nrorecibopago = nrorecibopago;
    }

    public int getCicobrador() {
        return cicobrador;
    }

    public void setCicobrador(int cicobrador) {
        this.cicobrador = cicobrador;
    }
    
}
