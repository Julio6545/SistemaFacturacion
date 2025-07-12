/*
 * Clase que modela prcesos de control sobre operaciones de filtro de productos 
 * segun tipo de filtro seleccionado
 */
package Controlador;

import Modelo.ConsultaCategoria;
import Modelo.ConsultaMarca;
import Modelo.ConsultaProducto;
import Modelo.Producto;
import Vista.jfFiltraProducto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JFAA
 */
public class CtrlFiltroProducto implements ActionListener{
    ConsultaProducto conPro;
    jfFiltraProducto jfF;
    ConsultaCategoria conCat=new ConsultaCategoria();
    ConsultaMarca conM=new ConsultaMarca();
    Producto pro=new Producto();
    
    
    public CtrlFiltroProducto(ConsultaProducto conPro, jfFiltraProducto jfF){
        this.conPro=conPro;
        this.jfF=jfF;        
        this.jfF.jbtExcel.addActionListener(this);
        this.jfF.jbtFiltrar.addActionListener(this);  
        this.jfF.jrbPorCategoria.addActionListener(this);
        this.jfF.jrbPorMarca.addActionListener(this);
    }
    public void iniciar() throws SQLException{
        llenarCombo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jfF.jrbPorCategoria){
            jfF.jcbCategoria.setEnabled(true);
            jfF.jcbMarca.setEnabled(false);
        }
        if(e.getSource()==jfF.jrbPorMarca){
            jfF.jcbCategoria.setEnabled(false);
            jfF.jcbMarca.setEnabled(true);
        }
        if(e.getSource()==jfF.jbtFiltrar){
            if(jfF.jrbPorCategoria.isSelected()){
                if(jfF.jtListado.getRowCount()!=0){limpiarJT();}
                pro.setIdcategoria(jfF.jcbCategoria.getSelectedIndex()+1);
                mostrarProductos(0);
            }
            if(jfF.jrbPorMarca.isSelected()){
                if(jfF.jtListado.getRowCount()!=0){limpiarJT();} 
                pro.setMarca(jfF.jcbMarca.getSelectedIndex()+1);
                mostrarProductos(1);
            }
        }
        //Boton exportar excel
        if(e.getSource()==jfF.jbtExcel){
            //Verificar si el contenido de la tabla Marcaciones tiene datos
            if(jfF.jtListado.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "No hay datos para exportar","Importacion de Datos",JOptionPane.INFORMATION_MESSAGE);
                
            }else{
                //Creación de FileChooser para seleccionar ubicación donde guardar el archivo
                JFileChooser chooser=new JFileChooser();
                FileNameExtensionFilter filter=new FileNameExtensionFilter("Archivos Excel","xls");
                chooser.setFileFilter(filter);
                chooser.setDialogTitle("Guardar Archivo");
                chooser.setMultiSelectionEnabled(false);
                chooser.setAcceptAllFileFilterUsed(false);
                if(chooser.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
                    List<JTable> tb=new ArrayList<>();
                    List<String> nom=new ArrayList<>();
                    tb.add(jfF.jtListado);
                    nom.add("Cierre");                    
                    String file=chooser.getSelectedFile().toString().concat(".xls");
                    //Ejecuciión del método de exportación "exportar"
                    try{
                        Controlador.ExportarExcel exp=new ExportarExcel(new File (file),tb,nom);
                        if(exp.expotar(2)){
                            JOptionPane.showMessageDialog(null, "Exportación Exitosa!!","Importacion de Datos",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error, no ocurrió la importación!!","Importacion de Datos",JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(null, "El error :"+ex);
                    }
                }
            }
        }
    }
    //Metodo llenar combo box con Categorias de productos
    public void llenarCombo() throws SQLException{
        ArrayList<String> Lista=new ArrayList<>();
        Lista=conCat.llenarCombo();
        for(int i=0;i<Lista.size();i++){
            jfF.jcbCategoria.addItem(Lista.get(i));
        }
        Lista=conM.llenarCombo();
        for(int i=0;i<Lista.size();i++){
            jfF.jcbMarca.addItem(Lista.get(i));
        }
    }
    void mostrarProductos(int op){
        try{
            List<Producto> listaP = null;
            DefaultTableModel dtmDetalle;
            if(op==0){listaP=conPro.obtenerPCat(pro);
            }else{listaP=conPro.obtenerPM(pro);}
            dtmDetalle=(DefaultTableModel)jfF.jtListado.getModel();
            if(!listaP.isEmpty()){
                Object[] object = new Object[7];               
                for(int i=0; i<listaP.size();i++){
                    object[0]=listaP.get(i).getCodigo();
                    object[1]=listaP.get(i).getDescripcion();                    
                    object[2]=listaP.get(i).getIdcategoria(); 
                    object[3]=listaP.get(i).getMarca();
                    object[4]=listaP.get(i).getExistencia();                    
                    object[5]=listaP.get(i).getCosto();
                    object[6]=listaP.get(i).getPrecio();
                    dtmDetalle.addRow(object);                    
                }
                //hacer visible los datos en el JTable
                jfF.jtListado.setModel(dtmDetalle);        
            }else{
                //jfL.jlbMensaje.setText("No existe registros de ventas para la fecha");
                JOptionPane.showMessageDialog(null, "No existe registros de Productos","",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar Producto");
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    void limpiarJT(){
        while(jfF.jtListado.getRowCount()>0) {
            ((DefaultTableModel)jfF.jtListado.getModel()).removeRow(0);
        }
    }
}
