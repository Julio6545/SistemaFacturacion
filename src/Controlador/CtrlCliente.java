/*
 *
 */
package Controlador;

import Modelo.ConCiudad;
import Modelo.ConsultaCliente;
import Modelo.Persona;
import Vista.jfCliente;
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
public class CtrlCliente implements ActionListener{
    private Persona cli;
    private ConsultaCliente conCli;
    private jfCliente jfCli;
    
    ConCiudad conCiu=new ConCiudad();
    
    //Constructor
    public CtrlCliente(Persona cli, ConsultaCliente conCli, jfCliente jfCli){
        this.cli=cli;
        this.conCli=conCli;
        this.jfCli=jfCli;
        
        //Habilitar botones
        this.jfCli.btnGuardar.addActionListener(this); 
        this.jfCli.btnBuscar.addActionListener(this);
        this.jfCli.btnModificar.addActionListener(this);
    }
    //Iniciar elementos del jfCliente
    public void iniciar() throws SQLException{
        jfCli.txtNroCedula.grabFocus();
        llenarCombo();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        //reconocer los botones pulsados
        if (e.getSource()==jfCli.btnGuardar){
            cli.setCi((jfCli.txtNroCedula.getText()));
            try {
            if(false==conCli.buscar(cli)){
                cli.setRuc(jfCli.txtRuc.getText());
                cli.setNombre(jfCli.txtNombre.getText());
                cli.setApellido(jfCli.txtApellido.getText());
                cli.setTelefono(jfCli.txtTelefono.getText());            
                cli.setDireccion(jfCli.txtDireccion.getText());
                cli.setIdciudad(jfCli.jcbCiudad.getSelectedIndex()+1);
                cli.setTipoCliente(jfCli.jcbTipoCliente.getSelectedIndex());
                     
            try {
                if(conCli.registrar(cli)){
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
        if(e.getSource()==jfCli.btnModificar){
            if(validar()==1){
                cli.setRuc(jfCli.txtRuc.getText());            
                cli.setNombre(jfCli.txtNombre.getText());
                cli.setApellido(jfCli.txtApellido.getText());
                cli.setTelefono(jfCli.txtTelefono.getText());            
                cli.setDireccion(jfCli.txtDireccion.getText());
                cli.setIdciudad(jfCli.jcbCiudad.getSelectedIndex()+1);
                cli.setTipoCliente(jfCli.jcbTipoCliente.getSelectedIndex());
                try {
                    if(conCli.modificar(cli)){
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
                jfCli.txtNroCedula.grabFocus();
            }
        }
        //boton Buscar
        if (e.getSource()==jfCli.btnBuscar){            
            cli.setCi((jfCli.txtNroCedula.getText()));
            try {
                if(conCli.buscar(cli)){
                    //System.out.println("Buscar");
                    jfCli.txtRuc.setText(cli.getRuc());
                    jfCli.txtNombre.setText(cli.getNombre());
                    jfCli.txtApellido.setText(cli.getApellido());
                    jfCli.txtTelefono.setText(cli.getTelefono());
                    jfCli.txtDireccion.setText(cli.getDireccion());                    
                    //jfCli.jcbCiudad.selectWithKeyChar((cli.getIdciudad()-1));                    
                                        
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
        Lista=conCiu.llenarCombo();
        for(int i=0;i<Lista.size();i++){
            jfCli.jcbCiudad.addItem(Lista.get(i));
        }
    }
    //inicializar campos luego de realizar procesos
    public void limpiar(){        
        jfCli.txtNroCedula.setText("");
        jfCli.txtRuc.setText("");
        jfCli.txtNombre.setText("");
        jfCli.txtApellido.setText("");
        jfCli.txtTelefono.setText("");
        jfCli.txtDireccion.setText("");
       //fCli.jcbCiudad.selectWithKeyChar("");
       jfCli.txtNroCedula.grabFocus();
    }
    public int validar(){
        if (jfCli.txtNroCedula.getText().length()!=0 && jfCli.txtRuc.getText().length()!=0 &&
                jfCli.txtNombre.getText().length()!=0 && jfCli.txtApellido.getText().length()!=0 &&
                jfCli.txtTelefono.getText().length()!=0 && jfCli.txtDireccion.getText().length()!=0){
            return 1;
        }else{
            return 0;
        }
    }
}
