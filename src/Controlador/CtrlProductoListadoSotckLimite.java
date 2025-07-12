package Controlador;

import Modelo.ConsultaCategoria;
import Modelo.ConsultaMarca;
import Modelo.ConsultaProducto;
import Modelo.Producto;
import Vista.jfListadoProductoStockLimite;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JFAA
 */
public class CtrlProductoListadoSotckLimite implements ActionListener{
    private Producto pro;
    private ConsultaProducto conPro;
    private jfListadoProductoStockLimite jfPro;
    private ConsultaCategoria conCat=new ConsultaCategoria();
    private ConsultaMarca conM=new ConsultaMarca();
    
    public CtrlProductoListadoSotckLimite(Producto pro, ConsultaProducto conPro, jfListadoProductoStockLimite jfPro){
        this.pro=pro;
        this.conPro=conPro;
        this.jfPro=jfPro;
        this.jfPro.jbtBuscar.addActionListener(this);
    }
    
    public void iniciar () throws SQLException{
        jfPro.setTitle("Listado de Productos");
        jfPro.setLocationRelativeTo(null);
        //jfPro.txtCodigo.setVisible(false); 
         Calendar gc=new GregorianCalendar();
        this.jfPro.jdcFecha.setCalendar(gc);        
        llenarCombo();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==jfPro.jbtBuscar){
            pro.setMarca(jfPro.jcbMarca.getSelectedIndex()+1);
            listar(conPro,pro);
        }
    }
    
    //Atrapar los eventos de perdida o ganancia de foco
    private class escucha implements FocusListener{
        @Override
        public void focusGained(FocusEvent e) {
           
        }
        @Override
        public void focusLost(FocusEvent e) {
           
        }       
    }
    
    //Metodo llenar combo box con Categorias de productos
    public void llenarCombo() throws SQLException{
        ArrayList<String> Lista=new ArrayList<>();
        Lista=conM.llenarCombo();
        for(int i=0;i<Lista.size();i++){
            jfPro.jcbMarca.addItem(Lista.get(i));
        }
    }
    public void listar(ConsultaProducto conPro, Producto pro){
        //Variables listas
        List<Producto> listaDetalle;
        DefaultTableModel dtmDetalle=new DefaultTableModel();
        
        if(jfPro.jtListado.getRowCount()!=0){
                limpiarJTable();
            } 
        try{
            listaDetalle=conPro.ProductosStockLimite(pro);
            dtmDetalle=(DefaultTableModel)jfPro.jtListado.getModel();
            if(!listaDetalle.isEmpty()){
                Object[] object = new Object[5];               
                for(int i=0; i<listaDetalle.size();i++){
                    object[0]=i+1;
                    object[1]=listaDetalle.get(i).getCodigo();
                    object[2]=listaDetalle.get(i).getDescripcion();
                    object[3]=listaDetalle.get(i).getStock_minimo();
                    object[4]=listaDetalle.get(i).getExistencia();
                    dtmDetalle.addRow(object);                    
                }
                //hacer visible los datos en el JTable
                jfPro.jtListado.setModel(dtmDetalle);
                //Establecer foco en Nro lote     
        
            }else{                
                JOptionPane.showMessageDialog(null, "No existe registros de Productos","",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar Productos");
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    //Inicializar la tabla******************************************************
    public void limpiarJTable (){
        while(jfPro.jtListado.getRowCount()>0) {
            ((DefaultTableModel)jfPro.jtListado.getModel()).removeRow(0);
        }
    }
}
