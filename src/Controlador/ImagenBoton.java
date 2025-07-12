package Controlador;

import java.awt.Image;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author JFAA
 */
public class ImagenBoton {
    //Metodo para adecuar tamaño de imagen al tamaño del boton
    public Icon setIcono(String url,JButton boton){
        ImageIcon icon=new ImageIcon(getClass().getResource(url));
        int ancho=boton.getWidth();
        int alto=boton.getHeight();
        ImageIcon icono=new ImageIcon(icon.getImage().getScaledInstance(ancho, alto, Image.SCALE_DEFAULT));
        return icono;
    }
}
