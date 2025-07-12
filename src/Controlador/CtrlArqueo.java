/*
  */
package Controlador;

import Modelo.Arqueo;
import Modelo.ConArqueo;
import Modelo.ConTicket;
import Modelo.ConsultaDetalleTick;
import Modelo.ConsultaV;
import Modelo.DetalleV;
import Modelo.DetalleVC;
import Modelo.Venta;
import Vista.jfArqueo;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author JFAA
 */
public class CtrlArqueo implements ActionListener, FocusListener{
    private Arqueo c;
    private ConArqueo cC;
    private jfArqueo jfC;
    private Arqueo arqDes=new Arqueo();
    private Venta v=new Venta();
    
    //Variable detalle de factura
    TableModel detalle;
    List<DetalleVC> listaDetalle;
    DefaultTableModel dtmDetalle=new DefaultTableModel();  
    
    //Instanciar la clase Detalle Factura y Consulta Detalle
    ConsultaV conV=new ConsultaV();
    ConTicket conT=new ConTicket();
    DetalleV detF=new DetalleV();
    ConsultaDetalleTick conD=new ConsultaDetalleTick();
    formatoFecha fF=new formatoFecha();
    //Variables auxiliares
    int totalDia, totalCred, totalCont, ganancia, totalNota;
    //Constructor
    public CtrlArqueo(Arqueo c,ConArqueo cC,jfArqueo jfC){
        this.c=c;
        this.cC=cC;
        this.jfC=jfC;               
        this.jfC.jbtConsultar.addActionListener(this);
        this.jfC.jbtGuardar.addActionListener(this);  
        this.jfC.jbtExcel.addActionListener(this);
    }
    //constructor sin parámetros
    public CtrlArqueo(){        
    }
    
    //inicializar los valores en el formulario
    public void iniciar() throws SQLException{
        Calendar gc=new GregorianCalendar();
        this.jfC.jdcFechaCierre.setCalendar(gc);
        this.jfC.jdcFechaApertura.setCalendar(gc);
        this.jfC.jtxtUsuarioCierre.setText(jfC.jlbUsuario.getText());
        this.jfC.jlbHoraCierre.setText(String.valueOf(fF.hora()));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //controlar los eventos de los botones y otros componentes del formulario
        //Boton Consultar        
        if(e.getSource()==jfC.jbtConsultar){
            totalDia = 0; totalCred=0; totalCont=0; ganancia=0;
            //Inicializar JTable si ya hubo una consulta anterior
            if(jfC.jtCierre.getRowCount()!=0){
                limpiarJTable();
            }            
            v.setFecha(formatoF(jfC.jdcFechaCierre.getDate()));
            v.setIdUsuario(jfC.jlbUsuario.getText());
            v.setIdArqueo(Integer.valueOf(jfC.jtxtNroArqueo.getText()));
            c.setFechaApertura(formatoF(jfC.jdcFechaCierre.getDate()));
            c.setUsuarioApertura(jfC.jlbUsuario.getText());
            c.setCaja(Integer.parseInt(jfC.jlbCajaNro.getText()));
            c.setEstado("A");
            c.setId(Integer.parseInt(jfC.jtxtNroArqueo.getText()));
            try{
            listaDetalle=conV.obtenerV(c);
            conV.obtenerDescuento(v);
            arqDes.setTotalDescuento(v.getTotalDescuento());
            dtmDetalle=(DefaultTableModel)jfC.jtCierre.getModel();
            if(!listaDetalle.isEmpty()){
                Object[] object = new Object[8];               
                for(int i=0; i<listaDetalle.size();i++){
                    object[0]=listaDetalle.get(i).getIdVenta();
                    object[1]=listaDetalle.get(i).getIdDetalle();
                    object[2]=listaDetalle.get(i).getTipoPago();
                    object[3]=listaDetalle.get(i).getIdProducto();
                    object[4]=listaDetalle.get(i).getPrecio();
                    object[5]=listaDetalle.get(i).getCantidad();
                    object[6]=listaDetalle.get(i).getDescuento();
                    object[7]=listaDetalle.get(i).getPrecio()*listaDetalle.get(i).getCantidad();
                    dtmDetalle.addRow(object);
                    switch (listaDetalle.get(i).getTipoPago()) {
                        case 1:
                            totalCont=totalCont+listaDetalle.get(i).getPrecio()*listaDetalle.get(i).getCantidad();
                            break;
                        case 3:
                            totalNota=totalNota+listaDetalle.get(i).getPrecio()*listaDetalle.get(i).getCantidad();
                            break;
                        default:
                            totalCred=totalCred+listaDetalle.get(i).getPrecio()*listaDetalle.get(i).getCantidad();
                            break;
                    }
                    totalDia=totalDia+listaDetalle.get(i).getPrecio()*listaDetalle.get(i).getCantidad();
                    if(i>0){
                        if(listaDetalle.get(i).getIdVenta()!=listaDetalle.get(i-1).getIdVenta())
                        {ganancia=ganancia+listaDetalle.get(i).getGanancia();}
                    }else{ganancia=ganancia+listaDetalle.get(i).getGanancia();}
                }
                //hacer visible los datos en el JTable
                jfC.jtCierre.setModel(dtmDetalle);
                //habilitar botones            
                jfC.jbtExcel.setEnabled(true);
                jfC.jbtCsv.setEnabled(true);
                jfC.jlbNeto.setText(String.valueOf(totalDia));
                jfC.jlbContado.setText(String.valueOf(totalCont));
                jfC.jlbCredito.setText(String.valueOf(totalCred));
                jfC.jlbNotaCredito.setText(String.valueOf(totalNota));
                jfC.jlbDescuento.setText(String.valueOf(arqDes.getTotalDescuento()));
                jfC.jlbTotalGeneral.setText(String.valueOf(totalDia-arqDes.getTotalDescuento()-totalCred));
                jfC.jlbMensaje.setText("La ganancia del día: "+String.valueOf(ganancia));
                jfC.jlbNeto.grabFocus();
                    
            }else{
                jfC.jlbMensaje.setText("No existe registros de ventas para la fecha");
                JOptionPane.showMessageDialog(null, "No existe registros de ventas para la fecha","",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar Cliente");
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }    
              
        //Registrar la venta
        if(e.getSource()==jfC.jbtGuardar){
            if (jfC.jtCierre.getRowCount()==0 && jfC.jlbMensaje.getText().length()==0){
                JOptionPane.showMessageDialog(null,"Favor realizar consulta!!");
            }else{
                //obtener datos del formulario
                //1-Obtener el nro de Caja
                c.setCaja(Integer.valueOf(jfC.jlbCajaNro.getText()));
                //3-formatear la fecha para poder guardarlo en una base de datos                  
                  /*SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");                    
                  String fecha2;                    
                  fecha2 = (formatofecha.format(jfC.jdcFecha.getDate()));                
                  java.sql.Date fecha=java.sql.Date.valueOf(fecha2);*/
                  c.setFechaApertura(formatoF(jfC.jdcFechaCierre.getDate()));
                  c.setFechaCierre(formatoF(jfC.jdcFechaCierre.getDate()));
                //4-Obtener la hora del Sistema                  
                  c.setHoraApertura(Time.valueOf(jfC.jlbHoraApertura.getText()));
                  c.setHoraCierre(Time.valueOf(jfC.jlbHoraCierre.getText()));
                //5-Dato de Usuario
                c.setUsuarioApertura(jfC.jlbUsuario.getText());
                c.setUsuarioCierre(jfC.jtxtUsuarioCierre.getText());
                //6-TotalCredito
                c.setTotalcredito(totalCred);
                //7-TotalContado
                c.setTotalcontado(totalCont);                
                //8-totalfactura
                String t=jfC.jlbNeto.getText();                
                int T=Integer.valueOf(t);
                c.setTotal((T));
                //9-Ganancia
                c.setGanancia(ganancia);
                c.setObservacion(jfC.jtxtaObs.getText());
                c.setTotalDescuento(Integer.valueOf(jfC.jlbDescuento.getText()));
                c.setTotalNota(Integer.valueOf(jfC.jlbNotaCredito.getText()));
                //insertar registro de cierre                
                try{
                    c.setEstado("C");
                    if (cC.cerrar(c)){
                    JOptionPane.showMessageDialog(null,"Datos Actualizados Correctamente!!!");                    
                    limpiar();
                    }else{
                        JOptionPane.showMessageDialog(null,"Ocurrio un error al cerrar arqueo !!!");                    
                    }
                }catch(SQLException err){
                    JOptionPane.showMessageDialog(null,"Error al registrar cierre!!"+" "+err);
                }              
            }                
        }
        //******Método para controlar el evento del boton exportar 
        if (e.getSource()==jfC.jbtExcel){
            //Verificar si el contenido de la tabla Marcaciones tiene datos
            if(jfC.jtCierre.getRowCount()==0){
                JOptionPane.showMessageDialog(null, "No hay datos para exportar","Importacion de Datos",JOptionPane.INFORMATION_MESSAGE);
                jfC.jdcFechaCierre.grabFocus();
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
                    tb.add(jfC.jtCierre);
                    nom.add("Cierre");                    
                    String file=chooser.getSelectedFile().toString().concat(".xls");
                    //Ejecuciión del método de exportación "exportar"
                    try{
                        Controlador.ExportarExcel exp=new ExportarExcel(new File (file),tb,nom);
                        if(exp.expotar(1)){
                            JOptionPane.showMessageDialog(null, "Exportación Exitosa!!","Importacion de Datos",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(null, "Ha ocurrido un error, no ocurrió la importación!!","Importacion de Datos",JOptionPane.INFORMATION_MESSAGE);
                        JOptionPane.showMessageDialog(null,"El error "+ex);
                    }
                }
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
            total+= (int)detalle.getValueAt(i, 6);               
        }
        return total;
    }
    //proceso limpiar
    public void limpiar(){        
        try {
            //obtener siguiente nro de factura
            jfC.jtxtNroArqueo.setText(cC.buscarUltimoArqueo());
        } catch (SQLException ex) {
            Logger.getLogger(CtrlArqueo.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No sepudo encontar el ultimo registro de factura!!");
        }
        //limpiar las filas de la tabla detalle
        detalle=jfC.jtCierre.getModel();
         DefaultTableModel modelo=(DefaultTableModel) jfC.jtCierre.getModel();
        for(int i=((detalle.getRowCount())-1);i>-1;i--){       
            modelo.removeRow(i);
        }
        jfC.jlbNeto.setText("0");
        jfC.jlbContado.setText("0");
        jfC.jlbCredito.setText("0");
        jfC.jlbDescuento.setText("0");
        jfC.jlbNotaCredito.setText("0");
        jfC.jlbTotalGeneral.setText("0");
        jfC.jlbMensaje.setText("");
    }
    
    //Inicializar la tabla
    public void limpiarJTable (){
        while(jfC.jtCierre.getRowCount()>0) {
            ((DefaultTableModel)jfC.jtCierre.getModel()).removeRow(0);
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