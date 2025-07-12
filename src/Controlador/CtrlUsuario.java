/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultaUsuario;
import Modelo.Usuario;
import Vista.jfUsuario;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author JFAA
 */
public class CtrlUsuario implements ActionListener{
    private Usuario usu;
    private ConsultaUsuario conU;
    private jfUsuario jfU;
   
    public CtrlUsuario (Usuario usu, ConsultaUsuario conU, jfUsuario jfU){
        this.usu=usu;
        this.conU=conU;
        this.jfU=jfU;
        
        this.jfU.btnGuardar.addActionListener(this);
        this.jfU.btnBuscar.addActionListener(this);
        this.jfU.btnModificar.addActionListener(this);
                
    }
    
    public void iniciar(){
        jfU.setTitle("Usuarios");
        jfU.setLocationRelativeTo(null);
        jfU.txtNroCedula.requestFocus();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        //reconocer los botones pulsados
        if (e.getSource()==jfU.btnGuardar){
            usu.setCi((jfU.txtNroCedula.getText()));
            try {
            if(false==conU.buscar(usu)){
            usu.setNombre(jfU.txtNombre.getText());
            usu.setApellido(jfU.txtApellido.getText());
            SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");                                               
            String fecha2 = (formatofecha.format(jfU.jdcFechaNac.getDate()));                
            java.sql.Date fecha=java.sql.Date.valueOf(fecha2);
            usu.setFechaNac(fecha);
            usu.setTelefono(jfU.txtTelefono.getText());            
            usu.setDireccion(jfU.txtDireccion.getText());
            usu.setEmail(jfU.txtCorreo.getText());
            usu.setNombreusu(jfU.txtUsuario.getText());            
            usu.setClave(jfU.txtClave.getText());
            usu.setRol(Integer.valueOf(jfU.txtRol.getText()));
                     
            try {
                if(conU.registrar(usu)){
                    JOptionPane.showMessageDialog(null,"Registro Guardado");
                    limpiar();
                }else{
                    JOptionPane.showMessageDialog(null,"Error al Guardar");
                    //limpiar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{JOptionPane.showMessageDialog(null,"Error al Guardar, Posible Cédula Existente");
            }//fin prueba Cedula repetida
            } catch (SQLException ex) {
                Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //boton Buscar
        if (e.getSource()==jfU.btnBuscar){
            //int a=Integer.parseInt(frm.txtCodigo.getText());
            usu.setCi((jfU.txtNroCedula.getText()));
            try {
                if(conU.buscar(usu)){
                    //System.out.println("Buscar");
                    jfU.txtNombre.setText(usu.getNombre());
                    jfU.txtApellido.setText(usu.getApellido());
                    jfU.jdcFechaNac.setDate(usu.getFechaNac());
                    jfU.txtTelefono.setText(usu.getTelefono());
                    jfU.txtDireccion.setText(usu.getDireccion());
                    jfU.txtCorreo.setText(usu.getEmail());
                    jfU.txtUsuario.setText(usu.getNombreusu());
                    jfU.txtClave.setText((usu.getClave()));
                    jfU.txtRol.setText(String.valueOf(usu.getRol()));                    
                                        
                }else{
                    JOptionPane.showMessageDialog(null,"No se encontro el registro!!");
                    limpiar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("El error :"+ex);
            }
        }
    }
    
    //inicializar campos luego de realizar procesos
    public void limpiar(){
        jfU.txtNroCedula.setText("");
        jfU.txtNombre.setText("");
        jfU.txtApellido.setText("");
        Calendar gc=new GregorianCalendar();
        jfU.jdcFechaNac.setCalendar(gc);
        jfU.txtTelefono.setText("");
        jfU.txtDireccion.setText("");
        jfU.txtCorreo.setText("");
        jfU.txtUsuario.setText("");
        jfU.txtClave.setText("");
        jfU.txtRol.setText("");   
    }
}
