/*
 *
 */
package Controlador;

import Modelo.ConsultaEmpresa;
import Modelo.Empresa;
import Vista.jfEmpresa;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/** 
 * @author JFAA
 */
public class CtrlEmpresa implements ActionListener{
    private Empresa em;
    private ConsultaEmpresa conE;
    private jfEmpresa jfE;
    //Variables TableModel,List,DefaultTableModel          
    TableModel detalle;    
    List<Empresa> lisEmpresa;
    DefaultTableModel dtmDetalle=new DefaultTableModel();
    //Constructor
    public CtrlEmpresa(Empresa em, ConsultaEmpresa conE, jfEmpresa jfE){
        this.em=em;
        this.conE=conE;
        this.jfE=jfE;        
        //Habilitar botones
        this.jfE.jbtGuardar.addActionListener(this); 
        this.jfE.jbtBuscar.addActionListener(this);
        this.jfE.jbtModificar.addActionListener(this);
        
    }
    //Iniciar elementos del jfEmpresa
    public void iniciar() throws SQLException{
        jfE.jtxtDenominacion.grabFocus();
        if(conE.buscarE()){
            mostrarEmpresas();
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        //reconocer los botones pulsados
        if (e.getSource()==jfE.jbtGuardar){
            em.setRucempresa((jfE.jtxtRuc.getText()));
            try {
            if(false==conE.buscar(em)){
                em.setRucempresa(jfE.jtxtRuc.getText());
                em.setNombre(jfE.jtxtDenominacion.getText());                
                em.setTelefono(jfE.jtxtTelefono.getText());            
                em.setDireccion(jfE.jtxtDireccion.getText());
                em.setCorreo(jfE.jtxtCorreo.getText());
                     
            try {
                if(conE.registrar(em)){
                    JOptionPane.showMessageDialog(null,"Registro Guardado");
                    limpiar();
                }else{
                    JOptionPane.showMessageDialog(null,"Error al Guardar");
                    //limpiar();
                }
            } catch (SQLException ex) {
                Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{JOptionPane.showMessageDialog(null,"Error al Guardar, Empresa existente");
            }//fin prueba Cedula repetida
            } catch (SQLException ex) {
                Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Modificar
        if(e.getSource()==jfE.jbtModificar){
            if(validar()==1){
                em.setRucempresa(jfE.jtxtRuc.getText());            
                em.setNombre(jfE.jtxtDenominacion.getText());                
                em.setTelefono(jfE.jtxtTelefono.getText());            
                em.setDireccion(jfE.jtxtDireccion.getText());
                em.setCorreo(jfE.jtxtCorreo.getText());
                try {
                    if(conE.modificar(em)){
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
                jfE.jtxtDenominacion.grabFocus();
            }
        }
        //boton Buscar
        if (e.getSource()==jfE.jbtBuscar){            
            em.setRucempresa((jfE.jtxtRuc.getText()));
            try {
                if(conE.buscar(em)){
                    //System.out.println("Buscar");
                    jfE.jtxtRuc.setText(em.getRucempresa());
                    jfE.jtxtDenominacion.setText(em.getNombre());
                    jfE.jtxtTelefono.setText(String.valueOf(em.getTelefono()));
                    jfE.jtxtDireccion.setText(em.getDireccion());
                    jfE.jtxtCorreo.setText(em.getCorreo());
                                        
                }else{
                    JOptionPane.showMessageDialog(null,"No se encontro el registro!!");                   
                }
            } catch (SQLException ex) {                
               JOptionPane.showMessageDialog(null,"El error :"+ex);
            }
        }
    }
     
    //inicializar campos luego de realizar procesos
    public void limpiar(){        
        jfE.jtxtRuc.setText("");
        jfE.jtxtDenominacion.setText("");
        jfE.jtxtTelefono.setText("");
        jfE.jtxtDireccion.setText("");
        jfE.jtxtCorreo.setText("");
    }
    public int validar(){
        if (jfE.jtxtRuc.getText().length()!=0 && jfE.jtxtDenominacion.getText().length()!=0 &&
                jfE.jtxtTelefono.getText().length()!=0 && jfE.jtxtDireccion.getText().length()!=0 &&
                jfE.jtxtCorreo.getText().length()!=0){
            return 1;
        }else{
            return 0;
        }
    }
    //Mostrar Empresas
    public void mostrarEmpresas() throws SQLException{        
        //Inicializar JTable si ya hubo una consulta anterior
        if(jfE.jtEmpresas.getRowCount()!=0){limpiarJTable();}
        try{
        lisEmpresa=conE.obtenerE();
        dtmDetalle=(DefaultTableModel)jfE.jtEmpresas.getModel();                        
            if(!lisEmpresa.isEmpty()){//Verificamos si trajo filas
            Object[] object = new Object[6];                                    
            for(int i=0; i<lisEmpresa.size();i++){
                object[0]=lisEmpresa.get(i).getIdEmpresa();
                object[1]=lisEmpresa.get(i).getNombre();                           
                object[2]=lisEmpresa.get(i).getRucempresa();                   
                object[3]=lisEmpresa.get(i).getDireccion();
                object[4]=lisEmpresa.get(i).getTelefono();                           
                object[5]=lisEmpresa.get(i).getCorreo();
                dtmDetalle.addRow(object);                   
            }
            //hacer visible los datos en el JTable
            jfE.jtEmpresas.setModel(dtmDetalle);       
            }else{                
                JOptionPane.showMessageDialog(null, "Sin registros de Empresas","",
                JOptionPane.INFORMATION_MESSAGE);                        
            }
        }catch(SQLException er){
            JOptionPane.showMessageDialog(null, "Error al buscar Empresas "+er,"Error",
                        JOptionPane.INFORMATION_MESSAGE);
        }
    }
    //Inicializar la tabla y demas campos **************************************
    public void limpiarJTable (){
        while(jfE.jtEmpresas.getRowCount()>0) {
            ((DefaultTableModel)jfE.jtEmpresas.getModel()).removeRow(0);
        }        
    }
}
