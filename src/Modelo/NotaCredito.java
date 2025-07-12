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
public class NotaCredito extends Venta{
    int nronota;
    int timbradoNota;

    public int getTimbradoNota() {
        return timbradoNota;
    }

    public void setTimbradoNota(int timbradoNota) {
        this.timbradoNota = timbradoNota;
    }

    public int getNronota() {
        return nronota;
    }

    public void setNronota(int nronota) {
        this.nronota = nronota;
    }
    
}
