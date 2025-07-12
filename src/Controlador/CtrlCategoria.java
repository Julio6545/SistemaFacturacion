
package Controlador;

import Modelo.Categoria;
import Modelo.ConsultaCategoria;
import Vista.jfCategoria;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JFAA
 */
public class CtrlCategoria implements ActionListener{
    private Categoria cat;
    private ConsultaCategoria modCat;
    private jfCategoria jfC;
    
    
    public CtrlCategoria(Categoria cat, ConsultaCategoria modCat, jfCategoria jfC){
        this.cat=cat;
        this.modCat=modCat;
        this.jfC=jfC;
        
        this.jfC.btnGuardar.addActionListener(this);
        this.jfC.jbtnActualizar.addActionListener(this);
        this.jfC.jbtBuscar.addActionListener(this);
        //this.frmC.btnLimpiar.addActionListener(this);
        this.jfC.btnModificar.addActionListener(this);       
    }
    
    public void iniciar (){
        jfC.setTitle("Categoría Producto");
        jfC.setLocationRelativeTo(null);
        jfC.txtNombre.setVisible(true);
        jfC.txtNombre.requestFocus();
        mostrarCat();
    }
    public void actionPerformed(ActionEvent e){
        //reconocer los botones pulsados****************************************
        if (e.getSource()==jfC.btnGuardar){
            if(validar()){
                //modCat.setCodigo(frmC.txtcodigo.getText());            
                cat.setNombre(jfC.txtNombre.getText());
                cat.setDescripcion(jfC.txtDescripcion.getText());
            try{
                if(!modCat.buscarCat(cat)){
                    if(modCat.registrar(cat)){
                    JOptionPane.showMessageDialog(null,"Registro Guardado");
                    limpiar();
                    }else{
                        JOptionPane.showMessageDialog(null,"Error al Guardar");
                        //limpiar();
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Ya existe Categoria..");
                }
            }catch (SQLException ex) {
                Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{
                JOptionPane.showMessageDialog(null,"Ingrese Valores Apropiados..");
                jfC.txtNombre.grabFocus();
            }
            
        }
        //Boton de buscar categorias********************************************
        if(e.getSource()==jfC.jbtBuscar){
            if(jfC.jtxtNroCategoria.getText().length()!=0){
                cat.setIdcategoria(Integer.valueOf(jfC.jtxtNroCategoria.getText()));
            try{
                if(modCat.buscar(cat)){
                  jfC.txtNombre.setText(cat.getNombre());
                  jfC.txtDescripcion.setText(cat.getDescripcion());
                }
            } catch (SQLException ex) {
                Logger.getLogger(CtrlCategoria.class.getName()).log(Level.SEVERE, null, ex);
            }
            }else{
                JOptionPane.showMessageDialog(null, "Ingrese identificador de categoria");
                jfC.jtxtNroCategoria.grabFocus();
            }
            
        }
        //Boton Modificar*******************************************************
        if(e.getSource()==jfC.btnModificar){
            if(validar()){
                cat.setIdcategoria(Integer.valueOf(jfC.jtxtNroCategoria.getText()));
                cat.setNombre(jfC.txtNombre.getText());
                cat.setDescripcion(jfC.txtDescripcion.getText());
            try{
                if(modCat.modificar(cat)){
                   JOptionPane.showMessageDialog(null, "Datos actualizados correctamente..");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Error al modificar.."+ex);
                jfC.txtNombre.grabFocus();
            }
            }else{
                JOptionPane.showMessageDialog(null, "Ingrese valores apropiados");
                jfC.txtNombre.grabFocus();
            }
        }
        //Boton Actualizar pantalla*********************************************
        if(e.getSource()==jfC.jbtnActualizar){
           jfC.jtxtNroCategoria.setText("");
           jfC.txtNombre.setText("");
           jfC.txtDescripcion.setText("");
           mostrarCat();
        }
                
    }
    //Metodo Mostrar lista de categorias**************************************** 
    public void mostrarCat(){
        //Variables listas
        List<Categoria> listaDetalle;
        DefaultTableModel dtmDetalle=new DefaultTableModel();
        ConsultaCategoria conC=new ConsultaCategoria();
        if(jfC.jtCategorias.getRowCount()!=0){
                limpiarJTable();
            } 
        try{
            listaDetalle=conC.obtenerCat();
            dtmDetalle=(DefaultTableModel)jfC.jtCategorias.getModel();
            if(!listaDetalle.isEmpty()){
                Object[] object = new Object[3];               
                for(int i=0; i<listaDetalle.size();i++){
                    object[0]=listaDetalle.get(i).getIdcategoria();
                    object[1]=listaDetalle.get(i).getNombre();
                    object[2]=listaDetalle.get(i).getDescripcion();                                      
                    dtmDetalle.addRow(object);                    
                }
                //hacer visible los datos en el JTable
                jfC.jtCategorias.setModel(dtmDetalle);
                //Establecer foco en Nro lote            
                jfC.txtNombre.grabFocus();
        
            }else{                
                JOptionPane.showMessageDialog(null, "No existe registros de Categorias","",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar Categoria");
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    //Inicializar la tabla******************************************************
    public void limpiarJTable (){
        while(jfC.jtCategorias.getRowCount()>0) {
            ((DefaultTableModel)jfC.jtCategorias.getModel()).removeRow(0);
        }
    }
    //Inicializar Campos *******************************************************
    public void limpiar(){
        jfC.txtNombre.setText(null);
        jfC.txtDescripcion.setText(null);
        jfC.txtNombre.requestFocus();
    }    
    //validar campos************************************************************
    public boolean validar(){
        if(jfC.txtNombre.getText().length()!=0 && jfC.txtDescripcion.getText().length()!=0){
            return true;
        }
        return false;
    }
}
