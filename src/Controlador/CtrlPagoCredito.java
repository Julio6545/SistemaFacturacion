package Controlador;

import Modelo.Compra;
import Modelo.ConPagoCredito;
import Modelo.ConsultaCompra;
import Modelo.PagoCredito;
import Vista.jfPagoCredito;
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
public class CtrlPagoCredito implements ActionListener{
    PagoCredito pCr;
    jfPagoCredito jfPcr;
    ConPagoCredito conPcr;
    Compra comp=new Compra();
    ConsultaCompra conC=new ConsultaCompra();
    CtrlCierre ctrlC=new CtrlCierre();
    
    //Variables TableModel,List,DefaultTableModel 
    TableModel detalle;    
    List<PagoCredito> listaDetalle;
    DefaultTableModel dtmDetalle=new DefaultTableModel();
    int r,s;//Variable auxiliar para sumar los montos pagados y el saldo
    public CtrlPagoCredito(PagoCredito cPr,jfPagoCredito jfPcr,ConPagoCredito conPcr){
        this.pCr=cPr;
        this.jfPcr=jfPcr;
        this.conPcr=conPcr;
        this.jfPcr.jbtBuscar.addActionListener(this);
        this.jfPcr.jbtRegistrar.addActionListener(this);
        
    }
    public void iniciar(){
        Calendar gc=new GregorianCalendar();
        this.jfPcr.jdcFecha.setCalendar(gc);
        this.jfPcr.jtxtNroVenta.grabFocus();
        this.jfPcr.setLocationRelativeTo(null);
        this.jfPcr.jbtRegistrar.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Buscar la venta y sus pagos si los hubiere**************************** 
        if(e.getSource()==jfPcr.jbtBuscar){
            r=0;s=0;
            if(jfPcr.jtxtNroVenta.getText().length()!=0){
                comp.setNroCompra(Integer.valueOf(jfPcr.jtxtNroVenta.getText()));
                try{
                    if(conC.obtenerCC(comp)){
                        jfPcr.jlbMontoVenta.setText(String.valueOf(comp.getTotalC()));         
                        //obtener los datos de cobros de la venta
                        pCr.setNroventa(Integer.valueOf(jfPcr.jtxtNroVenta.getText()));
                        //buscar los cobros de la venta y mostrarlos
                        if(comp.getTipoPago()==2){mostrarCobros();
                        jfPcr.jlbSaldo.setText(String.valueOf(s));}
                        if(comp.getCancelado()==1 && comp.getTipoPago()==2){                        
                            JOptionPane.showMessageDialog(null, "Factura Credito Cancelado...","Aviso!!"
                                ,JOptionPane.INFORMATION_MESSAGE);
                            jfPcr.jbtRegistrar.setEnabled(false);
                            jfPcr.jtxtNroVenta.grabFocus();
                        }else{jfPcr.jbtRegistrar.setEnabled(true);}
                    }else{
                       //Inicializar JTable y demas campos si ya hubo una consulta anterior
                       if(jfPcr.jtCobros.getRowCount()!=0){limpiarJTable();}                       
                       JOptionPane.showMessageDialog(null, "La compra Nº"+comp.getNroCompra()+
                               ", No se registro como crédito", "Error", JOptionPane.ERROR_MESSAGE);
                    this.jfPcr.jtxtNroVenta.grabFocus(); 
                    }                     
                }catch(SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error al buscar Venta "+ex, "Error", 
                            JOptionPane.ERROR_MESSAGE);
                    this.jfPcr.jtxtNroVenta.grabFocus();
                }                
            }else{
                JOptionPane.showMessageDialog(null, "Favor ingresar Nro venta", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                this.jfPcr.jtxtNroVenta.grabFocus();
            }
        }
        //Registrar cobro*******************************************************
        if(e.getSource()==jfPcr.jbtRegistrar){            
            if(r<comp.getTotalC()){
                if(jfPcr.jtxtNroVenta.getText().length()!=0 && jfPcr.jtxtMontoPagar.getText().length()!=0
                    && jfPcr.jlbUsuario.getText().length()!=0){
                    pCr.setNroventa(Integer.valueOf(jfPcr.jtxtNroVenta.getText()));
                    pCr.setFecha(ctrlC.formatoF(jfPcr.jdcFecha.getDate()));
                    //Objetos tipo hora
                    Tiempo hora=new Tiempo();                    
                    java.sql.Time hhmm=java.sql.Time.valueOf(hora.hora);
                    pCr.setHora(hhmm);
                    pCr.setMonto(comp.getTotalC());
                    pCr.setPagado(Integer.valueOf(jfPcr.jtxtMontoPagar.getText()));
                    pCr.setUsuario(jfPcr.jlbUsuario.getText());
                    try{
                        conPcr.registrarCobro(pCr);
                        JOptionPane.showMessageDialog(null,"Datos Actualizados Correctamente!!!");
                        mostrarCobros();
                        if(r==comp.getTotalC()){
                            if (conC.modificarEstadoC(comp)){
                            JOptionPane.showMessageDialog(null,"Credito Cancelado!!!",
                                "Aviso",JOptionPane.INFORMATION_MESSAGE);
                        }else{
                            JOptionPane.showMessageDialog(null,"Error al actualizar estado de credito a cancelado!!!");
                        }
                    }
                }catch(SQLException err){
                    JOptionPane.showMessageDialog(null,"Error al registrar Pago!!"+" "+err);
                }
                }else{
                    JOptionPane.showMessageDialog(null, "Verificar si datos son adecuados", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                    this.jfPcr.jtxtNroVenta.grabFocus();
                }
            }else{
                JOptionPane.showMessageDialog(null, "El crédito ya fue cancelado!!!", "Error", 
                        JOptionPane.ERROR_MESSAGE);
                this.jfPcr.jtxtNroVenta.grabFocus();
            }            
        }
    }
    //Inicializar la tabla y demas campos **************************************
    public void limpiarJTable (){
        while(jfPcr.jtCobros.getRowCount()>0) {
            ((DefaultTableModel)jfPcr.jtCobros.getModel()).removeRow(0);
        }
        jfPcr.jlbMontoVenta.setText("");
        jfPcr.jtxtMontoPagar.setText("");
        jfPcr.jlbSaldo.setText("");
    }
    //Mostrar cobros
    public void mostrarCobros() throws SQLException{
        r=0;s=0;
        //Inicializar JTable si ya hubo una consulta anterior
        if(jfPcr.jtCobros.getRowCount()!=0){limpiarJTable();}
        try{
        listaDetalle=conPcr.obtenerPagos(pCr);
        dtmDetalle=(DefaultTableModel)jfPcr.jtCobros.getModel();                        
            if(!listaDetalle.isEmpty()){//Verificamos si trajo filas
            Object[] object = new Object[5];                                    
            for(int i=0; i<listaDetalle.size();i++){
                object[0]=listaDetalle.get(i).getIdcobro();
                object[1]=listaDetalle.get(i).getNrorecibopago();
                object[2]=listaDetalle.get(i).getFecha();                           
                object[3]=listaDetalle.get(i).getPagado();
                r=r+listaDetalle.get(i).getPagado();
                s=listaDetalle.get(i).getMonto()-r;
                object[4]=s;                            
                dtmDetalle.addRow(object);                   
            }
            //hacer visible los datos en el JTable
            jfPcr.jtCobros.setModel(dtmDetalle);       
            }else{                
            JOptionPane.showMessageDialog(null, "Sin registros de Pagos para la Compra Nº "+comp.getNroCompra(),"",
            JOptionPane.INFORMATION_MESSAGE);                        
            }
        }catch(SQLException er){
            JOptionPane.showMessageDialog(null, "Error al buscar Pagos "+er,"Error",
                        JOptionPane.INFORMATION_MESSAGE);
        }
    }    
}
