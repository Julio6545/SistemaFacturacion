package Controlador;

import Modelo.ConsultaProducto;
import Modelo.Producto;
import Vista.jfFiltro;
import Vista.jfNotaCredito;
import Vista.jfTicket;
import Vista.jfVenta;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author JFAA
 */
public class CtrlFiltro implements ActionListener, KeyListener{
    public TableRowSorter trsFiltro;
    public jfFiltro jfF;
    public ConsultaProducto conP;
    public Producto pro;
    public jfTicket jfT;
    public jfVenta jfV;
    public jfNotaCredito jfNot;
    List<Producto> listaP;
    DefaultTableModel dtmDetalle=new DefaultTableModel();
    int ban=0;
    CtrlFiltro(jfFiltro jfF, ConsultaProducto conP, Producto pro,jfTicket jfTic, int ban){
        this.jfF=jfF;
        this.pro=pro;
        this.conP=conP;
        this.jfT=jfTic;
        this.jfF.jtxtNombre.addKeyListener(this);
        this.jfF.jbtOk.addActionListener(this);
    }
    CtrlFiltro(jfFiltro jfF, ConsultaProducto conP, Producto pro,jfVenta jfV, int ban){
        this.jfF=jfF;
        this.pro=pro;
        this.conP=conP;
        this.jfV=jfV;
        this.ban=ban;
        this.jfF.jtxtNombre.addKeyListener(this);
        this.jfF.jbtOk.addActionListener(this);
    }
    CtrlFiltro(jfFiltro jfF, ConsultaProducto conP, Producto pro,jfNotaCredito jfNot, int ban){
        this.jfF=jfF;
        this.pro=pro;
        this.conP=conP;
        this.jfNot=jfNot;
        this.ban=ban;
        this.jfF.jtxtNombre.addKeyListener(this);
        this.jfF.jbtOk.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jfF.jbtOk){
            if(jfF.jtTablaFiltro.isRowSelected(jfF.jtTablaFiltro.getSelectedRow())){
                jfT.codPro=(String)jfF.jtTablaFiltro.getValueAt(jfF.jtTablaFiltro.getSelectedRow(),0);                
                jfF.dispose();                
                if(ban==0){jfT.txtCodigo.setText(jfT.codPro);
                    jfT.txtCodigo.grabFocus();
                }else if(ban==2){
                    jfNot.txtCodigo.setText(jfT.codPro);
                    jfNot.txtCodigo.grabFocus();
                }else{jfV.txtCodigo.setText(jfT.codPro);
                    jfV.txtCodigo.grabFocus();}
                ban=0;
            }else{JOptionPane.showMessageDialog(null,"No Selecciono ningún Producto..");
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {/**/}
    @Override
    public void keyPressed(KeyEvent e) {/**/}
    @Override
    public void keyReleased(KeyEvent e) {
        //Metodo para filtrar productos de acuerdo a su nombre******************
        if(e.getSource()==jfF.jtxtNombre){           
            jfF.jtxtNombre.addKeyListener(new KeyAdapter(){
                public void keyReleased(final KeyEvent e){
                    //String cadena=(jtxtNombre.getText()).toUpperCase();
                    String cadena=jfF.jtxtNombre.getText();
                    jfF.jtxtNombre.setText(cadena);
                    //repaint();
                    filtro();
                }
            });
            trsFiltro=new TableRowSorter(jfF.jtTablaFiltro.getModel());
            jfF.jtTablaFiltro.setRowSorter(trsFiltro);                   
        }
    }
    
    //Metodo para filtrar productos de la tabla*********************************
    public void filtro(){
        trsFiltro.setRowFilter(RowFilter.regexFilter(jfF.jtxtNombre.getText(),1));
    }
    
    //Metodo para traer todos los productos ************************************
    public void mostrarProductos(){ 
        if(jfF.jtTablaFiltro.getRowCount()!=0){
                limpiarJTable();
            } 
        try{
            listaP=conP.obtenerProductos();
            dtmDetalle=(DefaultTableModel)jfF.jtTablaFiltro.getModel();
            jfF.jtTablaFiltro.setModel(dtmDetalle);
            //Establecer el ancho preferido de la segunda columna
            jfF.jtTablaFiltro.getColumnModel().getColumn(1).setPreferredWidth(300);
            //Establecer foco en Nro lote            
            jfF.jtxtNombre.grabFocus();
            if(!listaP.isEmpty()){
                Object[] object = new Object[4];               
                for(int i=0; i<listaP.size();i++){
                    object[0]=listaP.get(i).getCodigo();
                    //En esta linea le agrego tambien el lugar del producto, esta parte es a la hora de buscar
                    object[1]=listaP.get(i).getDescripcion()+" -P:"+listaP.get(i).getPasillo()+"-E:"+listaP.get(i).getEstante()+"-B:"+listaP.get(i).getBandeja();
                    object[2]=listaP.get(i).getPrecio();                    
                    object[3]=listaP.get(i).getExistencia();                    
                    dtmDetalle.addRow(object);                    
                }
                //hacer visible los datos en el JTable
                jfF.jtTablaFiltro.setModel(dtmDetalle);
                //Establecer foco en Nro lote            
                jfF.jtxtNombre.grabFocus();
        
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
    //Inicializar la tabla******************************************************
    public void limpiarJTable (){
        while(jfF.jtTablaFiltro.getRowCount()>0) {
            ((DefaultTableModel)jfF.jtTablaFiltro.getModel()).removeRow(0);
        }
    }
}
