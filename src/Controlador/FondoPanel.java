/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author JFAA
 */
public class FondoPanel extends JPanel{
    private Image imagen;
    public  String url="/img/3p3d5v.jpg";
    
    public FondoPanel(String url){
        this.url=url;
    }
    public FondoPanel(){
        //
    }
    @Override
    public void paint (Graphics g){
        imagen=new ImageIcon(getClass().getResource(url)).getImage();
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        //Para que no dibuje el fondo por defecto, se desactiva con false
        setOpaque(false);
        //Dibuja los componentes que se establecieron dentro del JPanel, botones, Jlabel, etc.
        super.paint(g);
    }    
}
