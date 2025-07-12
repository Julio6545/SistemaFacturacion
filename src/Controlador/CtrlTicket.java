package Controlador;

import Modelo.ConLote;
import Modelo.ConTicket;
import Modelo.ConsultaCliente;
import Modelo.ConsultaDetalleTick;
import Modelo.ConsultaProducto;
import Modelo.DetalleV;
import Modelo.Empresa;
import Modelo.ImprimirTicket;
import Modelo.Venta;
import Modelo.Lote;
import Modelo.Persona;
import Modelo.Producto;
import Vista.jfCliente;
import Vista.jfFiltro;
import Vista.jfTicket;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class CtrlTicket implements ActionListener, FocusListener{
    private Venta tic;
    private ConTicket cTic;
    private jfTicket jfTic;
    //Instanciar la clase Detalle Factura y Consulta Detalle
    DetalleV detF=new DetalleV();
    ConsultaDetalleTick conD=new ConsultaDetalleTick(); 
    private final Empresa emp = new Empresa();
    ImagenBoton imgF=new ImagenBoton();
    //Variable detalle de factura
    TableModel detalle;
    List<DetalleV> listaDetalle;
    DefaultTableModel dtmDetalle=new DefaultTableModel(); 
    //(Objetos/Variables)Instanciar la clase producto Colsuta Producto
    private Producto pro=new Producto();
    private ConsultaProducto conPro=new ConsultaProducto();
    private ConLote conL=new ConLote();
    private Lote l=new Lote();
       
    ImprimirTicket impT=new ImprimirTicket();
    //Objetos/Variables Persona, ConsultaCliente
    private Persona cli=new Persona();
    private ConsultaCliente conCli=new ConsultaCliente();    
    //variables auxiliares 
    int p;//precio del producto
    String fecha2;
    int ganancia;
    //Constructor
    public CtrlTicket(Venta tic,ConTicket cTic,jfTicket jfTic){
        this.tic=tic;
        this.cTic=cTic;
        this.jfTic=jfTic;               
        this.jfTic.btnAgregar.addActionListener(this);
        this.jfTic.btnRegistrar.addActionListener(this);
        this.jfTic.btnBuscar.addActionListener(this);
        this.jfTic.btnBuscarT.addActionListener(this);
        this.jfTic.jbtEliminar.addActionListener(this);
        this.jfTic.jbtAltaCliente.addActionListener(this);
        this.jfTic.jbtBuscarP.addActionListener(this);
        this.jfTic.txtCodigo.addFocusListener(this);
        this.jfTic.txtCantidad.addFocusListener(this);
    }
    
    //inicializar los valores en el formulario
    public void iniciar() throws SQLException{
        Calendar gc=new GregorianCalendar();
        this.jfTic.JDCfechafactura.setCalendar(gc);
        //obtener el siguiente numero de factura
        this.jfTic.txtNrofactura.setText(cTic.buscarUltimoTic());
        jfTic.txtNroci.setText("111");        
        BuscarCli();
        jfTic.txtCodigo.grabFocus();
        jfTic.txtCantidad.setText("1");
        jfTic.jrbContado.setSelected(true);
        jfTic.jrbMostrador.setSelected(true);
        jfTic.jbtBuscarP.setIcon(imgF.setIcono("/img/buscar3.png", jfTic.jbtBuscarP));
    }

    @Override
    public void actionPerformed(ActionEvent e) {//*****************************
        //controlar los eventos de los botones y otros componentes del formulario
        //Boton Buscar*********************************************************
        if(e.getSource()==jfTic.btnBuscarT){
            if(jfTic.jtxtBucarCi.getText().length()>0){
                tic.setIdCliente((jfTic.jtxtBucarCi.getText()));                
                try{
                    //Inicializar JTable si ya hubo una consulta anterior
                    if(jfTic.jtDetalle.getRowCount()!=0){limpiarJTable();}
                    if(cTic.obtenerTicket(tic)){
                        //obtener los datos del Presupuesto
                        //JOptionPane.showMessageDialog(jfTic,tic.getNroPresupuesto());
                        if (cTic.obtenerTCab(tic)){
                        //Copia la cabecera
                        //Taer Datos de Cliente
                        cli.setCi(tic.getIdCliente());                        
                        conCli.buscar(cli);
                        jfTic.txtNroci.setText(String.valueOf(cli.getCi()));
                        jfTic.txtRuc.setText(cli.getRuc());
                        jfTic.txtNombre.setText(cli.getNombre()+" "+cli.getApellido());
                        jfTic.jlUsuario.setText(tic.getIdUsuario());                        
                        jfTic.JDCfechafactura.setDate(tic.getFecha());
                        jfTic.jlNroCaja.setText(String.valueOf(tic.getNumCaja())); 
                        jfTic.txtNrofactura.setText(String.valueOf(tic.getNroPresupuesto()));
                        if(tic.getProcesado()==0){jfTic.jtxtEstado.setText("PENDIENTE");
                        }else{jfTic.jtxtEstado.setText("PROCESADO");}
                        if(tic.getTipoPago()==1){
                            jfTic.jrbContado.setSelected(true);
                        }else{
                            jfTic.jrbCredito.setSelected(true);
                        }
                        jfTic.txtTotalG.setText(String.valueOf(tic.getTotalF())); 
                        listaDetalle=cTic.obtenerTDet(tic);
                        //JOptionPane.showMessageDialog(jfTic,tic.getEstado());
                        dtmDetalle=(DefaultTableModel)jfTic.jtDetalle.getModel();                        
                        if(!listaDetalle.isEmpty()){//Verificamos si trajo filas                                                    
                            Object[] object = new Object[8];
                            String cadena="Sin Descripcion";
                            for(int i=0; i<listaDetalle.size();i++){
                                object[0]=listaDetalle.get(i).getIdProducto();
                                object[1]=listaDetalle.get(i).getIdLote();                           
                                //object[2]=listaDetalle.get(i).getIdProducto();
                                pro.setCodigo(listaDetalle.get(i).getIdProducto());
                                if(conPro.buscar(pro)){
                                    cadena=pro.getDescripcion()+" -P:"+pro.getPasillo()+"-E:"+pro.getEstante()+"-B:"+pro.getBandeja();                                
                                }
                                object[2]=cadena;
                                object[3]=listaDetalle.get(i).getPrecio();
                                object[4]=listaDetalle.get(i).getLimite();
                                object[5]=listaDetalle.get(i).getCantidad();
                                object[6]=listaDetalle.get(i).getPrecio()*listaDetalle.get(i).getCantidad();
                                object[7]=1;
                                dtmDetalle.addRow(object);                   
                        }
                        //hacer visible los datos en el JTable
                        jfTic.jtDetalle.setModel(dtmDetalle);                        
                        }else{                
                            JOptionPane.showMessageDialog(null, "Ticket sin detalle ","Informe",JOptionPane.INFORMATION_MESSAGE);
                        }                        
                    }
                    }else{                
                        JOptionPane.showMessageDialog(null, "No existe registros para el nro "+tic.getIdCliente()
                            ,"Informe",JOptionPane.INFORMATION_MESSAGE);}
                }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar "+ex);                    
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ingrese un valor valido");
                jfTic.jtxtBucarCi.grabFocus();
            }
        }
        
        //Agregar líneas/Productos al detelle
        if(e.getSource()==jfTic.btnAgregar){
            if(jfTic.txtCodigo.getText().length()>0 && jfTic.txtCantidad.getText().length()>0 
                    && jfTic.txtDescripcion.getText().length()>0 ){
                DefaultTableModel modelo=(DefaultTableModel) jfTic.jtDetalle.getModel();            
                int cant=Integer.valueOf(jfTic.txtCantidad.getText());
                p= Integer.valueOf(jfTic.txtPrecio.getText());
                modelo.addRow(new Object[]{jfTic.txtCodigo.getText(),jfTic.jcbLote.getSelectedItem().toString(),
                jfTic.txtDescripcion.getText()+" -P:"+jfTic.jtxtPasillo.getText()+" -E:"+jfTic.jtxtEstante.getText()+"-B:"+jfTic.jtxtBandeja.getText(),
                jfTic.txtPrecio.getText(),jfTic.jtxtLimite.getText(),jfTic.txtCantidad.getText(),
                p*cant,0    
                } );
            //limpiar los campos de ingreso
            jfTic.txtCodigo.grabFocus();
            jfTic.txtCodigo.setText("");
            jfTic.txtDescripcion.setText("");
            jfTic.txtPrecio.setText("");
            jfTic.txtCantidad.setText("1");
            jfTic.txtTotal.setText("");
            jfTic.jtxtBandeja.setText("");
            jfTic.jtxtPasillo.setText("");
            jfTic.jtxtEstante.setText("");
            jfTic.txtTotalG.setText(String.valueOf(calTotal(modelo)));            
            }else{
                JOptionPane.showMessageDialog(null,"Codigo y cantidad deben contener valores apropiados..");
                jfTic.txtCodigo.grabFocus();
                jfTic.txtCantidad.setText("1");
            }
        }        
        //Boton Buscar*********************************************************
        if(e.getSource()==jfTic.btnBuscar){
            if(jfTic.txtNroci.getText().length()>0){
                cli.setCi((jfTic.txtNroci.getText()));            
                try{
                    if(conCli.buscar(cli)){
                        jfTic.txtNombre.setText(cli.getNombre()+" "+cli.getApellido());                
                        jfTic.txtRuc.setText(cli.getRuc());                                        
                    }else{
                    JOptionPane.showMessageDialog(null,"No Existe Cliente..");
                    //limpiar();
                    }
                }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar Cliente");
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ingrese un valor valido");
                jfTic.txtNroci.grabFocus();
            }
        }
        //Registrar el ticket***************************************************
        if(e.getSource()==jfTic.btnRegistrar){
            if (jfTic.jtDetalle.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Favor ingrese datos de la venta");
            }else{
                //obtener datos del formulario factura
                //1-obtener dato de Cliente                
                tic.setIdCliente((jfTic.txtNroci.getText()));                
                //2-Obtener el nro de Factura
                //tic.setNroFactura(Integer.parseInt(jfTic.txtNrofactura.getText()));                
                //3-formatear la fecha para poder guardarlo en una base de datos                  
                  SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");                    
                                      
                  fecha2 = (formatofecha.format(jfTic.JDCfechafactura.getDate()));                
                  java.sql.Date fecha=java.sql.Date.valueOf(fecha2);
                  tic.setFecha(fecha);
                //4-Obtener la hora del Sistema
                  //Objetos tipo hora
                  Tiempo hora=new Tiempo();                    
                  java.sql.Time hhmm=java.sql.Time.valueOf(hora.hora);
                  tic.setHora(hhmm);
                //5-verificar si factura es contado o crédito
                if (jfTic.jrbContado.isSelected()){tic.setTipoPago(1);
                  }else{tic.setTipoPago(2);
                }   
                //6-Dato de Usuario
                tic.setIdUsuario(jfTic.jlUsuario.getText());               
                //7-Datos Nro Caja
                tic.setNumCaja(Integer.parseInt(jfTic.jlNroCaja.getText()));
                // Estado de ticket
                //Si es contado Cancelado=1, credito Cancelado=0
                if (jfTic.jrbContado.isSelected()){tic.setTipoPago(1);tic.setCancelado(1);
                  }else{tic.setTipoPago(2);tic.setCancelado(0);
                }                   
                //8-totalfactura
                String t=jfTic.txtTotalG.getText();                
                int T=Integer.valueOf(t);
                tic.setTotalF((T));
                //Calcular ganancia 
                calcularG();
                tic.setGanancia(ganancia);
                tic.setProcesado(0);
                tic.setEstado("APROBADO");
                //insertar la cabecera de la factura                
                try{
                    if(cTic.registrar(tic)){
                       cTic.obtenerNroTicket(tic); 
                        //regsistrar el detalle de la factura                
                        //obtener los datos de la tabla de detalle de la factura
                        detalle=jfTic.jtDetalle.getModel();
                        for(int i=0;i<detalle.getRowCount();i++){                
                            //System.out.println(table.getValueAt(j, x));
                            //detF.setIdVenta(Integer.valueOf(jfTic.txtNrofactura.getText()));                                                       
                            detF.setIdVenta(tic.getNroPresupuesto());
                            detF.setIdProducto(detalle.getValueAt(i, 0).toString());
                            detF.setIdLote(detalle.getValueAt(i, 1).toString());
                            String a=detalle.getValueAt(i, 3).toString();
                            detF.setPrecio(Integer.valueOf(a));
                            detF.setLimite(Integer.valueOf(detalle.getValueAt(i,4).toString()));
                            a=detalle.getValueAt(i, 5).toString();
                            detF.setCantidad(Integer.valueOf(a));                  
                            //insertar el detalle de la factura
                            conD.registrar(detF);
                        }
                        //Actualizar Stock
                        //actualizarStock();
                        //Buscar nro de ticket                    
                        JOptionPane.showMessageDialog(null,"Datos Actualizados correctamente!!!");
                        JOptionPane.showMessageDialog(jfTic, "Ticket generado "+tic.getNroPresupuesto(), "Ticket", 1);
                        
                        //consultar si desea imprimir
                        int op = JOptionPane.showConfirmDialog
                            (null, "¿Quieres imprimir comprovante?", 
                                    "Sistema Dolly", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                        //0=yes, 1=no, 2=cancel
                        if(op == 0) {
                            ImprimirFactura imp = null;
                           try {
                               imp = new ImprimirFactura();
                               emp.setIdEmpresa(19);//Casa Matirz
                               imp.imprimirFactura(tic,detalle,emp,2);//System.out.println("Has pulsado Yes");
                           } catch (NullPointerException ex) {
                               JOptionPane.showMessageDialog(jfTic, "Error "+ex, "Ticket", 1);
                           }
                            
                        }else if(op == 1){System.out.println("Has pulsado No");
                        }
                        limpiar();
                    }else{
                        JOptionPane.showMessageDialog(null,"No se pudo guardar Ticket, verifique los Datos");
                    }
                }catch(SQLException err){
                    JOptionPane.showMessageDialog(null,"No se pudo guardar la Ticket"+" "+err);
                //} catch (IOException ex) {
                  //  JOptionPane.showMessageDialog(null,"No se pudo imprimir la Venta"+" "+ex);
                }               
            }                
        }
        //Eliminar lineas/Productos del detalle********************************
        if(e.getSource()==jfTic.jbtEliminar){
            if(jfTic.jtDetalle.isRowSelected(jfTic.jtDetalle.getSelectedRow())){
            DefaultTableModel modelo=(DefaultTableModel) jfTic.jtDetalle.getModel();
            modelo.removeRow(jfTic.jtDetalle.getSelectedRow());
            jfTic.txtTotalG.setText(String.valueOf(calTotal(modelo)));
            }else{JOptionPane.showMessageDialog(null,"No Selecciono ninguna fila..");
            }
        }
         //Dar de alta un nuevo Cliente 
        if(e.getSource()==jfTic.jbtAltaCliente){
           jfCliente jfCli=new jfCliente();
           CtrlCliente ctrlCli=new CtrlCliente(cli,conCli,jfCli);
            try {
                ctrlCli.iniciar();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"No se pudo cargar lista de ciudades..");
            }
           jfCli.setVisible(true);
        }
        //Llamar al ormulario de Filtro de productos por nombre***************** 
        if(e.getSource()==jfTic.jbtBuscarP){
           jfFiltro jfF=new jfFiltro();           
           CtrlFiltro ctrlF=new CtrlFiltro(jfF,conPro,pro,jfTic,0);
           jfF.setVisible(true);
           ctrlF.mostrarProductos();
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
        //Acciones al perder foco campo Codigo formulario Factura**************
        if(fL.getComponent()==jfTic.txtCodigo){
            if(jfTic.txtCodigo.getText().length()>0){
                pro.setCodigo(jfTic.txtCodigo.getText());
                try{
                    if(conPro.buscar(pro)){
                        jfTic.txtDescripcion.setText(pro.getDescripcion());
                        //obtener lotes del producto si tiene vencimiento
                        llenarCombo();
                        /*if(jfTic.jrbConEntrega.isSelected()){
                            p=pro.getPrecio()+(50000);                                                        
                            jfTic.txtPrecio.setText(String.valueOf(p));
                        }else if(jfTic.jrbMostrador.isSelected()){*/
                            p=pro.getPrecio();
                            jfTic.txtPrecio.setText(String.valueOf(p));
                        /*}else if(jfTic.jrbRevendedor.isSelected()){
                            p=pro.getCosto()+(pro.getCosto()*15/100);
                            p=p+(p*pro.getIva()/100);
                            jfTic.txtPrecio.setText(String.valueOf(p));
                        }*/
                        jfTic.txtCantidad.grabFocus();
                        jfTic.jtxtLimite.setText(String.valueOf(pro.getCosto())); 
                        jfTic.jtxtStock.setText(String.valueOf(pro.getExistencia()));
                        jfTic.jtxtPasillo.setText(String.valueOf(pro.getPasillo()));
                        jfTic.jtxtEstante.setText(String.valueOf(pro.getEstante()));
                        jfTic.jtxtBandeja.setText(String.valueOf(pro.getBandeja()));
                    }else{
                        JOptionPane.showMessageDialog(null,"No Existe Producto..");
                        jfTic.txtCodigo.grabFocus();                        
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //Acciones al perder foco campo Cantidad formulario Factura************
        if(fL.getComponent()==jfTic.txtCantidad){            
            if(jfTic.txtCantidad.getText().length()>0){                
                try{
                    jfTic.txtTotal.setText(String.valueOf(p*Integer.valueOf(jfTic.txtCantidad.getText())));                
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Ingresado en Cantidad no es un número..");
                    jfTic.txtCantidad.grabFocus();
                }    
            }else{
                JOptionPane.showMessageDialog(null,"Favor ingrese la cantidad a vender..");
                jfTic.txtCantidad.grabFocus();
            }    
        }        
    }
    
    //Calcular total de la factura**************************************** 
    public int calTotal(DefaultTableModel detalle){
        int total=0;
        for(int i=0; i<detalle.getRowCount();i++){
            total+= (int)detalle.getValueAt(i, 6);               
        }
        //Bloquear/desbloquear tipo cliente una vez agregada un producto a la lista
        //Se asegura que la venta sea solo para un tipo de cliente Mostrador/Puesto en obra/Ferreteria
        if(total>0){
            for(Component c:jfTic.jpTipocliente.getComponents()){
               c.setEnabled(false);
            }
        }else{
            for(Component c:jfTic.jpTipocliente.getComponents()){
               c.setEnabled(true);
            }
        }
        return total;
    }
    //proceso limpiar******************************************************
    public void limpiar() throws SQLException{
        jfTic.txtNroci.setText("111");        
        jfTic.txtNombre.setText("");        
        jfTic.txtRuc.setText("");
        jfTic.txtNrofactura.setText("");
         //obtener el siguiente numero de factura
        this.jfTic.txtNrofactura.setText(cTic.buscarUltimoTic());
        BuscarCli();
        /*try {
            //obtener siguiente nro de factura
            jfTic.txtNrofactura.setText(cTic.buscarUltimoTic());
        } catch (SQLException ex) {
            Logger.getLogger(CtrlVenta.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No sepudo encontar el ultimo registro de Ticket!!");
        }*/
        jfTic.jrbContado.setSelected(true);
        jfTic.txtTotal.setText("");
        jfTic.txtTotalG.setText("");
       //limpiar las filas de la tabla detalle
        detalle=jfTic.jtDetalle.getModel();
        DefaultTableModel modelo=(DefaultTableModel) jfTic.jtDetalle.getModel();
        for(int i=((detalle.getRowCount())-1);i>-1;i--){       
            modelo.removeRow(i);
        }    
    
    }
    //metodo que actualiza stock de producto************************************
    void actualizarStock() throws SQLException{
        for(int i=0;i<detalle.getRowCount();i++){            
            pro.setCodigo((detalle.getValueAt(i, 0).toString()));
            int cant=Integer.parseInt(detalle.getValueAt(i,4).toString());
            conPro.buscar(pro);
            int sA=pro.getExistencia()-cant;
            conPro.ActualizarStock(pro,sA);
            //Actualizar stock en lote/producto/fechavencimiento en caso que
            //corresponda
            String nrolote=detalle.getValueAt(i, 2).toString();
            if(!"0".equals(nrolote)){
                l.setCantidad(sA);
                l.setNrolote(nrolote);
                conL.actualizarStock(l);
            }
        }
    }
    //Metodo llenar combo box con Categorias de productos***********************
    public void llenarCombo() throws SQLException{
        jfTic.jcbLote.removeAllItems();
        ArrayList<String> Lista=new ArrayList<>();
        Lista=conL.obtenerLote(pro);
        if(!Lista.isEmpty()){
            for(int i=0;i<Lista.size();i++){
                jfTic.jcbLote.addItem(Lista.get(i));
            }
        }else{jfTic.jcbLote.addItem("0");}
    }
    
    //Metodo buscar cliente
    public void BuscarCli() throws SQLException{
        if(jfTic.txtNroci.getText().length()>0){
            cli.setCi((jfTic.txtNroci.getText()));            
            try{
                if(conCli.buscar(cli)){
                   jfTic.txtNombre.setText(cli.getNombre()+" "+cli.getApellido());                
                   jfTic.txtRuc.setText(cli.getRuc());                                        
                }else{
                    JOptionPane.showMessageDialog(null,"No Existe Cliente..");
                    //limpiar();
                }
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al buscar Cliente");
                Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null,"Ingrese un valor valido");
            jfTic.txtNroci.grabFocus();
        }
    }
    public void Imprimir() throws IOException{
       impT.setCambio("0");
       impT.setCliente(jfTic.txtNroci.getText());
       impT.setDireccion(jfTic.txtNombre.getText());
       impT.setEmpresa(jfTic.txtRuc.getText());
       impT.setFecha(fecha2);
       impT.setFolio("123");
       impT.setMatriz("00555");
       impT.setPropietario("JFAA");
       impT.setRecibo("10000");
       impT.setRfc("Reg. Nº 00253");
       impT.setRfcCliente(jfTic.txtRuc.getText());
       impT.setSubTotal(jfTic.txtTotal.getText());
       impT.setTelefono("0994448693");
       impT.setTotal(jfTic.txtTotalG.getText());
       impT.setVendedor(jfTic.jlUsuario.getText());
       impT.setArticulos("Productos");
       impT.setTotalLetra("Gs");
       impT.print(true);
    }
    public void calcularG(){
        int i=0,tv=0;
        ganancia=0;
        //Verificar tipo de cliente, para saber incremento aplicado
        if(jfTic.jrbMostrador.isSelected()){i=20;
        }else if(jfTic.jrbConEntrega.isSelected()){i=30;
        }else if(jfTic.jrbRevendedor.isSelected()){i=15;
        }
        tv=Integer.valueOf(jfTic.txtTotalG.getText());
        tv=tv-(tv/11);
        ganancia=(tv*i/(100+i));
    }
    //Inicializar la tabla******************************************************
    public void limpiarJTable (){
        while(jfTic.jtDetalle.getRowCount()>0) {
            ((DefaultTableModel)jfTic.jtDetalle.getModel()).removeRow(0);
        }
    }
}
