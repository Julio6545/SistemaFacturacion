/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package JFAA;

import Controlador.ControlAcceso;
import Modelo.ConsultaAcceso;
import Modelo.Usuario;
import Vista.jfIngreso;

/**
 *
 * @author JFAA
 */
public class JFAA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Acceder al sistema
        Usuario usu=new Usuario();
        ConsultaAcceso consulta=new ConsultaAcceso();        
        jfIngreso ing=new jfIngreso();
        ControlAcceso ctrl=new ControlAcceso(usu,consulta,ing);
        ctrl.iniciar();
        ing.setVisible(true); 
    }    
}

