/*
 *Clase controladora de operaciones para la entidad/clase Marca
 */
package Controlador;

import Modelo.ConsultaMarca;
import Modelo.Marca;
import Vista.jfMarca;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JFAA
 */
public class CtrlMarca implements ActionListener{
    private Marca m;
    private ConsultaMarca conM;
    private jfMarca jfM;
   
    public CtrlMarca (Marca m, ConsultaMarca conM, jfMarca jfM){
        this.m=m;
        this.conM=conM;
        this.jfM=jfM;               
        this.jfM.jbtGuardar.addActionListener(this);
        this.jfM.jbtBuscar.addActionListener(this);
        this.jfM.jbtModificar.addActionListener(this);
                
    }
    
    public void iniciar(){
        jfM.setTitle("Marcas");
        jfM.setLocationRelativeTo(null);
        mostrarM();
        jfM.jtxtNombre.requestFocus();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        //reconocer los botones pulsados
        if(e.getSource()==jfM.jbtGuardar){
            if(Validar()){
                m.setNombre(jfM.jtxtNombre.getText());
                try {
                if(false==conM.buscar(m)){
                    m.setNombre(jfM.jtxtNombre.getText());
                    m.setOrigen(jfM.jtxtOrigen.getText());                               
                        try {
                            if(conM.registrar(m)){
                                JOptionPane.showMessageDialog(null,"Registro Guardado");
                                limpiar();
                                mostrarM();
                            }else{
                                JOptionPane.showMessageDialog(null,"Error al Guardar");
                                //limpiar();
                            }
                        }catch (SQLException ex) {
                            JOptionPane.showMessageDialog(jfM, "Error al Guardar.. "+ex);
                        }
                    }else{JOptionPane.showMessageDialog(null,"Error, Posible Marca Existente");
                    jfM.jtxtNombre.grabFocus();
                    }//fin prueba
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar marca..");
                }
            }else{
            JOptionPane.showMessageDialog(null,"Error, ingrese datos validos..GG");
            jfM.jtxtNombre.grabFocus();
            }
        }
    //Moficar Marca*************************************************************    
        if (e.getSource()==jfM.jbtModificar){
            if(Validar()){                                               
                m.setNombre(jfM.jtxtNombre.getText());
                m.setOrigen(jfM.jtxtOrigen.getText());                               
                    try {
                        if(conM.modificar(m)){
                            JOptionPane.showMessageDialog(null,"Registro Modificado");
                            limpiar();
                            mostrarM();
                        }else{
                            JOptionPane.showMessageDialog(null,"Error al Modificar");                          
                            //limpiar();
                        }
                    }catch (SQLException ex) {
                        JOptionPane.showMessageDialog(jfM, "Error al Guardar.. "+ex);
                    }                           
            }else{
                JOptionPane.showMessageDialog(null,"Error, ingrese datos validos..MOD");
                jfM.jtxtNombre.grabFocus();
            }
        }
        
    //boton Buscar**************************************************************
        if (e.getSource()==jfM.jbtBuscar){
            if(jfM.jtxtNombre.getText().length()!=0){
                m.setNombre((jfM.jtxtNombre.getText()));
                try {
                    if(conM.buscar(m)){                    
                        jfM.jtxtNombre.setText(m.getNombre());
                        jfM.jtxtOrigen.setText(m.getOrigen());
                    }else{
                        JOptionPane.showMessageDialog(null,"No se encontro el registro!!");
                        jfM.jtxtOrigen.grabFocus();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar registro!!"+ex);
                }
            }else{JOptionPane.showMessageDialog(jfM, "Ingrese Datos Válidos..BB");
                jfM.jtxtNombre.grabFocus();
            }
        }
    }
    
    //inicializar campos luego de realizar procesos
    public void limpiar(){
        jfM.jtxtNombre.setText("");
        jfM.jtxtOrigen.setText("");
    }
    //Validar campos
    public boolean Validar(){
        return jfM.jtxtNombre.getText().length()!=0 &&
                jfM.jtxtOrigen.getText().length()!=0;
    }
    //Metodo Mostrar lista de Marcas**************************************** 
    public void mostrarM(){
        //Variables listas
        List<Marca> listaMarcas;
        DefaultTableModel dtmMarcas;        
        if(jfM.jtMarcas.getRowCount()!=0){
                limpiarJTable();
            } 
        try{
            listaMarcas=conM.obtenerM();
            dtmMarcas=(DefaultTableModel)jfM.jtMarcas.getModel();
            if(!listaMarcas.isEmpty()){
                Object[] object = new Object[3];               
                for(int i=0; i<listaMarcas.size();i++){
                    object[0]=listaMarcas.get(i).getId();
                    object[1]=listaMarcas.get(i).getNombre();
                    object[2]=listaMarcas.get(i).getOrigen();                                      
                    dtmMarcas.addRow(object);                    
                }
                //hacer visible los datos en el JTable
                jfM.jtMarcas.setModel(dtmMarcas);
                //Establecer foco en Nro lote            
                jfM.jtxtNombre.grabFocus();
        
            }else{                
                JOptionPane.showMessageDialog(null, "No existe registros de Marcas","",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar Marcas");                    
            }
    }
    //Inicializar la tabla******************************************************
    public void limpiarJTable (){
        while(jfM.jtMarcas.getRowCount()>0) {
            ((DefaultTableModel)jfM.jtMarcas.getModel()).removeRow(0);
        }
    }
}
