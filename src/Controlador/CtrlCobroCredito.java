package Controlador;

import Modelo.CobroCredito;
import Modelo.ConCobroCredito;
import Modelo.ConTicket;
import Modelo.ConsultaV;
import Modelo.Venta;
import Vista.jfCobroCredito;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author JFAA
 */
public class CtrlCobroCredito implements ActionListener{
    CobroCredito cCr;
    jfCobroCredito jfCcr;
    ConCobroCredito conCcr;
    Venta ven=new Venta();
    ConsultaV conV=new ConsultaV();
    ConTicket conT=new ConTicket();
    CtrlCierre ctrlC=new CtrlCierre();
    
    //Variables TableModel,List,DefaultTableModel 
    TableModel detalle;    
    List<CobroCredito> listaDetalle;
    DefaultTableModel dtmDetalle=new DefaultTableModel();
    int r,s;//Variable auxiliar para sumar los montos pagados y el saldo
    public CtrlCobroCredito(CobroCredito cCr,jfCobroCredito jfCcr,ConCobroCredito conCcr){
        this.cCr=cCr;
        this.jfCcr=jfCcr;
        this.conCcr=conCcr;
        this.jfCcr.jbtBuscar.addActionListener(this);
        this.jfCcr.jbtRegistrar.addActionListener(this);
        
    }
    public void iniciar(){
        Calendar gc=new GregorianCalendar();
        this.jfCcr.jdcFecha.setCalendar(gc);
        this.jfCcr.jtxtNroVenta.grabFocus();
        this.jfCcr.setLocationRelativeTo(null);
        this.jfCcr.jbtRegistrar.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Buscar la venta y sus pagos si los hubiere**************************** 
        if(e.getSource()==jfCcr.jbtBuscar){
            r=0;s=0;
            if(jfCcr.jtxtNroVenta.getText().length()!=0){
                ven.setNroFactura(Integer.valueOf(jfCcr.jtxtNroVenta.getText()));
                try{
                    if(conV.obtenerVC(ven)){
                        jfCcr.jlbMontoVenta.setText(String.valueOf(ven.getTotalF()));         
                        //obtener los datos de cobros de la venta
                        cCr.setNroventa(Integer.valueOf(jfCcr.jtxtNroVenta.getText()));
                        //buscar los cobros de la venta y mostrarlos
                        if(ven.getTipoPago()==2){mostrarCobros();
                            jfCcr.jlbSaldo.setText(String.valueOf(s));}
                        if(ven.getCancelado()==1 && ven.getTipoPago()==2){                        
                            JOptionPane.showMessageDialog(null, "Factura Credito Cancelado...","Aviso!!"
                                ,JOptionPane.INFORMATION_MESSAGE);
                            jfCcr.jbtRegistrar.setEnabled(false);
                            jfCcr.jtxtNroVenta.grabFocus();
                        }else{jfCcr.jbtRegistrar.setEnabled(true);}
                    }else{
                       //Inicializar JTable y demas campos si ya hubo una consulta anterior
                       if(jfCcr.jtCobros.getRowCount()!=0){limpiarJTable();}                       
                       JOptionPane.showMessageDialog(null, "La venta Nº"+ven.getNroFactura()+
                               ", No se registro como crédito", "Error", JOptionPane.ERROR_MESSAGE);
                        this.jfCcr.jtxtNroVenta.grabFocus(); 
                    }                     
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error al buscar Venta "+ex, "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    this.jfCcr.jtxtNroVenta.grabFocus();
                }                
            }else{
                JOptionPane.showMessageDialog(null, "Favor ingresar Nro venta", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                this.jfCcr.jtxtNroVenta.grabFocus();
            }
        }
        //Registrar cobro*******************************************************
        if(e.getSource()==jfCcr.jbtRegistrar){            
            if(r<ven.getTotalF()){
                if(jfCcr.jtxtNroVenta.getText().length()!=0 && jfCcr.jtxtMontoPagar.getText().length()!=0
                    && jfCcr.jlbUsuario.getText().length()!=0){
                cCr.setNroventa(Integer.valueOf(jfCcr.jtxtNroVenta.getText()));
                cCr.setFecha(ctrlC.formatoF(jfCcr.jdcFecha.getDate()));
                //Objetos tipo hora
                Tiempo hora=new Tiempo();                    
                java.sql.Time hhmm=java.sql.Time.valueOf(hora.hora);
                cCr.setHora(hhmm);
                cCr.setMonto(ven.getTotalF());
                cCr.setPagado(Integer.valueOf(jfCcr.jtxtMontoPagar.getText()));
                cCr.setUsuario(jfCcr.jlbUsuario.getText());
                cCr.setNroArqueo(Integer.valueOf(jfCcr.jtxtArqueo.getText()));
                try{
                    conCcr.registrarCobro(cCr);
                    JOptionPane.showMessageDialog(null,"Datos Actualizados Correctamente!!!");
                    mostrarCobros();
                    if(r==ven.getTotalF()){
                      if (conT.modificarEstadoT(ven)){
                        JOptionPane.showMessageDialog(null,"Credito Cancelado!!!",
                                "Aviso",JOptionPane.INFORMATION_MESSAGE);
                      }else{
                        JOptionPane.showMessageDialog(null,"Error al actualizar estado de credito a cancelado!!!");
                      }
                    }
                }catch(SQLException err){
                    JOptionPane.showMessageDialog(null,"Error al registrar cobro!!"+" "+err);
                }
                }else{
                    JOptionPane.showMessageDialog(null, "Verificar si datos son adecuados", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    this.jfCcr.jtxtNroVenta.grabFocus();
                }
            }else{
                JOptionPane.showMessageDialog(null, "El crédito ya fue cancelado!!!", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                this.jfCcr.jtxtNroVenta.grabFocus();
            }            
        }
    }
    //Inicializar la tabla y demas campos **************************************
    public void limpiarJTable (){
        while(jfCcr.jtCobros.getRowCount()>0) {
            ((DefaultTableModel)jfCcr.jtCobros.getModel()).removeRow(0);
        }
        jfCcr.jlbMontoVenta.setText("");
        jfCcr.jtxtMontoPagar.setText("");
        jfCcr.jlbSaldo.setText("");
    }
    //Mostrar cobros
    public void mostrarCobros() throws SQLException{
        r=0;s=0;
        //Inicializar JTable si ya hubo una consulta anterior
        if(jfCcr.jtCobros.getRowCount()!=0){limpiarJTable();}
        try{
        listaDetalle=conCcr.obtenerCobros(cCr);
        dtmDetalle=(DefaultTableModel)jfCcr.jtCobros.getModel();                        
            if(!listaDetalle.isEmpty()){//Verificamos si trajo filas
            Object[] object = new Object[5];                                    
            for(int i=0; i<listaDetalle.size();i++){
                object[0]=listaDetalle.get(i).getIdcobro();
                object[1]=listaDetalle.get(i).getFecha();                           
                object[2]=listaDetalle.get(i).getPagado();
                r=r+listaDetalle.get(i).getPagado();
                s=listaDetalle.get(i).getMonto()-r;
                object[3]=s;
                object[4]=listaDetalle.get(i).getNroArqueo();
                dtmDetalle.addRow(object);                   
            }
            //hacer visible los datos en el JTable
            jfCcr.jtCobros.setModel(dtmDetalle);       
            }else{                
            JOptionPane.showMessageDialog(null, "Sin registros de cobro para la venta Nº "+ven.getNroFactura(),"",
            JOptionPane.INFORMATION_MESSAGE);                        
            }
        }catch(SQLException er){
            JOptionPane.showMessageDialog(null, "Error al buscar cobros "+er,"Error",
                        JOptionPane.INFORMATION_MESSAGE);
        }
    }    
}
