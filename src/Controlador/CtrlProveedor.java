/*
 *Clase que modela lasoperaciones sobre la entidad Proveedor
 */
package Controlador;

import Modelo.ConsultaEmpresa;
import Modelo.ConsultaProveedor;
import Modelo.Proveedor;
import Vista.jfProveedor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/** 
 * @author JFAA
 */
public class CtrlProveedor implements ActionListener{
    private Proveedor pro;
    private ConsultaProveedor conPro;
    private jfProveedor jfPro;
    
    ConsultaEmpresa conE=new ConsultaEmpresa();
    
    //Constructor
    public CtrlProveedor(Proveedor pro, ConsultaProveedor conPro, jfProveedor jfPro){
        this.pro=pro;
        this.conPro=conPro;
        this.jfPro=jfPro;
        
        //Habilitar botones
        this.jfPro.btnGuardar.addActionListener(this); 
        this.jfPro.btnBuscar.addActionListener(this);
        this.jfPro.btnModificar.addActionListener(this);
    }
    //Iniciar elementos del jfProente
    public void iniciar() throws SQLException{
        jfPro.txtNroCedula.grabFocus();
        llenarCombo();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        //reconocer los botones pulsados
        if (e.getSource()==jfPro.btnGuardar){
            pro.setCi((jfPro.txtNroCedula.getText()));
            try {
            if(false==conPro.buscar(pro)){
                pro.setRuc(jfPro.txtNroCedula.getText());
                pro.setNombre(jfPro.txtNombre.getText());
                pro.setApellido(jfPro.txtApellido.getText());
                pro.setTelefono(jfPro.txtTelefono.getText()); 
                pro.setEmail(jfPro.txtCorreo.getText());
                pro.setDireccion(jfPro.txtDireccion.getText());
                pro.setIdempresa(jfPro.jcbEmpresa.getSelectedIndex()+1);
                     
            try {
                if(conPro.registrar(pro)){
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
        //Modificar
        if(e.getSource()==jfPro.btnModificar){
            if(validar()==1){
                pro.setRuc(jfPro.txtNroCedula.getText());            
                pro.setNombre(jfPro.txtNombre.getText());
                pro.setApellido(jfPro.txtApellido.getText());
                pro.setTelefono(jfPro.txtTelefono.getText());            
                pro.setDireccion(jfPro.txtDireccion.getText());
                pro.setEmail(jfPro.txtCorreo.getText());
                pro.setIdempresa(jfPro.jcbEmpresa.getSelectedIndex()+1);
                try {
                    if(conPro.modificar(pro)){
                        JOptionPane.showMessageDialog(null,"Registro Modificado");
                        limpiar();
                    }else{
                        JOptionPane.showMessageDialog(null,"Error al Modificar");
                        //limpiar();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Faltan unos datos...!!!");
                jfPro.txtNroCedula.grabFocus();
            }
        }
        //boton Buscar
        if (e.getSource()==jfPro.btnBuscar){            
            pro.setCi((jfPro.txtNroCedula.getText()));
            try {
                if(conPro.buscar(pro)){
                    //System.out.println("Buscar");
                    jfPro.txtNroCedula.setText(pro.getCi());
                    jfPro.txtNombre.setText(pro.getNombre());
                    jfPro.txtApellido.setText(pro.getApellido());
                    jfPro.txtTelefono.setText(pro.getTelefono());
                    jfPro.txtCorreo.setText(pro.getEmail());
                    jfPro.txtDireccion.setText(pro.getDireccion());                    
                    jfPro.jcbEmpresa.setSelectedIndex((pro.getIdempresa()-1));                    
                                        
                }else{
                    JOptionPane.showMessageDialog(null,"No se encontro el registro!!");                   
                }
            } catch (SQLException ex) {                
               JOptionPane.showMessageDialog(null,"El error :"+ex);
            }
        }
    }
     //Metodo llenar combo box con Categorias de productos
    public void llenarCombo() throws SQLException{
        ArrayList<String> Lista=new ArrayList<>();
        Lista=conE.llenarCombo();
        for(int i=0;i<Lista.size();i++){
            jfPro.jcbEmpresa.addItem(Lista.get(i));
        }
    }
    //inicializar campos luego de realizar procesos
    public void limpiar(){        
        jfPro.txtNroCedula.setText("");
        jfPro.txtNroCedula.setText("");
        jfPro.txtNombre.setText("");
        jfPro.txtApellido.setText("");
        jfPro.txtTelefono.setText("");
        jfPro.txtDireccion.setText("");       
        jfPro.txtNroCedula.grabFocus();
    }
    public int validar(){
        if (jfPro.txtNroCedula.getText().length()!=0 && jfPro.txtNroCedula.getText().length()!=0 &&
                jfPro.txtNombre.getText().length()!=0 && jfPro.txtApellido.getText().length()!=0 &&
                jfPro.txtTelefono.getText().length()!=0 && jfPro.txtDireccion.getText().length()!=0){
            return 1;
        }else{
            return 0;
        }
    }
}
