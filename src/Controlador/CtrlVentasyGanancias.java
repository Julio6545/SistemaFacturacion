/*
  */
package Controlador;

import Modelo.Arqueo;
import Modelo.ConArqueo;
import Vista.jfVentasyGanancias;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author JFAA
 */
public class CtrlVentasyGanancias implements ActionListener, FocusListener{
    private Arqueo c;
    private ConArqueo cC;
    private jfVentasyGanancias jfVG;
    
    //Variable detalle de factura
    TableModel detalle;
    List<Arqueo> listaDetalle;
    DefaultTableModel dtmDetalle=new DefaultTableModel();  
    
    //Instanciar la clase Consulta Cierre
    //ConArqueo conC=new ConArqueo();
            
    //Constructor
    public CtrlVentasyGanancias(Arqueo c,ConArqueo cC,jfVentasyGanancias jfVG){
        this.c=c;
        this.cC=cC;
        this.jfVG=jfVG;               
        this.jfVG.jbtConsultar.addActionListener(this);        
    }
    
    //inicializar los valores en el formulario
    public void iniciar() 
    {
        Calendar gc=new GregorianCalendar();
        this.jfVG.jdcDesde.setCalendar(gc);
        this.jfVG.jdcHasta.setCalendar(gc);              
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //controlar los eventos de los botones y otros componentes del formulario
        //Boton Consultar
        int ventas = 0, impuesto=0, ganancia=0;        
        if(e.getSource()==jfVG.jbtConsultar){
            //Inicializar JTable si ya hubo una consulta anterior
            if(jfVG.jtCierres.getRowCount()!=0){
                limpiarJTable();
            }            
            c.setFechaApertura(formatoF(jfVG.jdcDesde.getDate()));
            c.setFechaCierre(formatoF(jfVG.jdcHasta.getDate()));
            try{
            listaDetalle=cC.buscarArqueoGanancia(c);
            dtmDetalle=(DefaultTableModel)jfVG.jtCierres.getModel();
            if(!listaDetalle.isEmpty()){
                Object[] object = new Object[3];               
                for(int i=0; i<listaDetalle.size();i++){
                    object[0]=listaDetalle.get(i).getFechaApertura();
                    object[1]=listaDetalle.get(i).getTotal();
                    object[2]=listaDetalle.get(i).getGanancia();                    
                    dtmDetalle.addRow(object);                    
                    ventas=ventas+listaDetalle.get(i).getTotal();
                    ganancia=ganancia+listaDetalle.get(i).getGanancia(); 
                    //System.out.println(object[0]+","+object[2]);
                }
                impuesto=ventas/11;
                //hacer visible los datos en el JTable
                jfVG.jtCierres.setModel(dtmDetalle);
                //habilitar botones            
                //jfVG.jbtExcel.setEnabled(true);
                //jfVG.jbtCsv.setEnabled(true);
                jfVG.jlVentas.setText(String.valueOf(ventas));
                jfVG.jlGanancia.setText(String.valueOf(ganancia));
                jfVG.jlImpuesto.setText(String.valueOf(impuesto));                
                jfVG.jdcDesde.grabFocus();
        
            }else{                
                JOptionPane.showMessageDialog(null, "No existe registros de ventas para la fecha","",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar Cierres");
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }         
    } 
    @Override
     public void focusGained(FocusEvent e) {
       /*if(e.getComponent()==jfFac.txtCantidad){
            System.out.println("Cantidad En Foco Ganado: "+Integer.valueOf(jfFac.txtCantidad.getText()));            
            jfFac.txtTotal.setText(String.valueOf(Integer.valueOf(jfFac.txtPrecio.getText())
                    *Integer.valueOf(jfFac.txtCantidad.getText())));
       }*/
    }

    @Override
    public void focusLost(FocusEvent fL) {
        //Acciones al perder foco campo Codigo formulario Factura        
               
    }
    
    //Calcular total de la factura 
    public static double calTotal(DefaultTableModel detalle){
        int total=0;
        for(int i=0; i<detalle.getRowCount();i++){
            total+= (int)detalle.getValueAt(i, 4);               
        }
        return total;
    }
    //proceso limpiar
    public void limpiar(){       
        //limpiar las filas de la tabla detalle
        detalle=jfVG.jtCierres.getModel();
         DefaultTableModel modelo=(DefaultTableModel) jfVG.jtCierres.getModel();
        for(int i=((detalle.getRowCount())-1);i>-1;i--){       
            modelo.removeRow(i);
        }
        jfVG.jlVentas.setText("0");
        jfVG.jlGanancia.setText("0");
        jfVG.jlImpuesto.setText("0");        
    }
    
    //Inicializar la tabla
    public void limpiarJTable (){
        while(jfVG.jtCierres.getRowCount()>0) {
            ((DefaultTableModel)jfVG.jtCierres.getModel()).removeRow(0);
        }
    }
    //Formatear la fecha para poder guardarlo en una base de datos                  
    public Date formatoF(java.util.Date f){
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");                    
        String fecha2;                    
        fecha2 = (formatofecha.format(f));                
        java.sql.Date fecha=java.sql.Date.valueOf(fecha2);
        return fecha;
    }
}