package Controlador;

import Modelo.ConLote;
import Modelo.ConTicket;
import Modelo.ConsultaCliente;
import Modelo.ConsultaDetalleV;
import Modelo.ConsultaNota;
import Modelo.ConsultaNotaDetalle;
import Modelo.ConsultaV;
import Modelo.ConsultaProducto;
import Modelo.ConsultaTimbrado;
import Modelo.DetalleNota;
import Modelo.DetalleV;
import Modelo.Venta;
import Modelo.Lote;
import Modelo.NotaCredito;
import Modelo.Persona;
import Modelo.Producto;
import Modelo.Timbrado;
import Vista.jfFiltro;
import Vista.jfNotaCredito;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
public class CtrlNotaCredito implements ActionListener, FocusListener{
    private NotaCredito not;    
    private ConsultaNota cN;    
    private ConTicket cTic = new ConTicket();
    private jfNotaCredito jfNot;
    
    private Timbrado t= new Timbrado();
    private ConsultaTimbrado conTim=new ConsultaTimbrado();
    //Variable detalle de factura
    TableModel detalle;    
    List<DetalleV> listaDetalle;
    DefaultTableModel dtmDetalle=new DefaultTableModel(); 
    //(Objetos/Variables)Instanciar la clase producto Colsuta Producto
    private Producto pro=new Producto();
    private ConsultaProducto conPro=new ConsultaProducto();
    private ConLote conL=new ConLote();
    private Lote l=new Lote();
    ImagenBoton imgF=new ImagenBoton();    
    //Instanciar la clase Detalle Factura y Consulta Detalle, Ticket
    //DetalleV detF=new DetalleV();
    DetalleNota detF=new DetalleNota();
    //ConsultaDetalleV conD=new ConsultaDetalleV();
    ConsultaNotaDetalle conD=new ConsultaNotaDetalle();
    ConTicket conT=new ConTicket();
    private ConsultaV cVen=new ConsultaV();
    private Venta ven=new Venta();
    
    //Objetos/Variables Persona, ConsultaCliente
    private Persona cli=new Persona();
    private ConsultaCliente conCli=new ConsultaCliente();    
    //variables auxiliares 
    int p;//precio del producto
    int ganancia, timbrado;
    //Constructor
    public CtrlNotaCredito(NotaCredito fac,ConsultaNota cN,jfNotaCredito jfNot){
        this.not=fac;
        this.cN=cN;
        this.jfNot=jfNot;               
        this.jfNot.btnAgregar.addActionListener(this);
        this.jfNot.btnRegistrar.addActionListener(this);
        this.jfNot.btnBuscar.addActionListener(this);
        this.jfNot.jbtBuscarCli.addActionListener(this);
        this.jfNot.jbtEliminar.addActionListener(this);
        this.jfNot.jbtBuscarP.addActionListener(this);
        this.jfNot.txtCodigo.addFocusListener(this);
        this.jfNot.txtCantidad.addFocusListener(this);
        this.jfNot.txtMonto.addFocusListener(this);
        this.jfNot.jtxtDescuento.addFocusListener(this);
    }
    
    //inicializar los valores en el formulario
    public void iniciar() throws SQLException{
        Calendar gc=new GregorianCalendar();
        this.jfNot.JDCfechaNota.setCalendar(gc);
        //obtener el siguiente numero de factura
        this.jfNot.txtNroNota.setText(cN.buscarUltimaNot());
        jfNot.txtNroci.setText("111");        
        jfNot.txtCantidad.setText("1");
        jfNot.jrbContado.setSelected(true);
        jfNot.jrbMostrador.setSelected(true);
        jfNot.txtTotalG.setText("0");
        jfNot.txtNroFactura.setText("0");
        jfNot.txtNroFactura.grabFocus();
        jfNot.jtxtEstado.setText("APROVADO");
        jfNot.jbtBuscarP.setIcon(imgF.setIcono("/img/buscar3.png", jfNot.jbtBuscarP));
        buscarTimbrado();
    }

    @Override
    public void actionPerformed(ActionEvent e) {//*****************************
        //controlar los eventos de los botones y otros componentes del formulario
        //Agregar líneas/Productos al detelle
        if(e.getSource()==jfNot.btnAgregar){
            if(jfNot.txtCodigo.getText().length()>0 && jfNot.txtCantidad.getText().length()>0 
                    && jfNot.txtDescripcion.getText().length()>0 )
            {
                DefaultTableModel modelo=(DefaultTableModel) jfNot.jtDetalle.getModel();            
                int cant=Integer.valueOf(jfNot.txtCantidad.getText());
                p= Integer.valueOf(jfNot.txtPrecio.getText());
                modelo.addRow(
                        new Object[]{jfNot.txtCodigo.getText(),
                            jfNot.jcbLote.getSelectedItem().toString(),
                            jfNot.txtDescripcion.getText(),
                            p-Integer.valueOf(jfNot.jtxtLimite.getText()),
                            jfNot.txtPrecio.getText(),
                            jfNot.txtCantidad.getText(),                            
                            p*cant    
                        } 
                );
                //actuallizar ganancia
            calcularG(modelo);                
            //limpiar los campos de ingreso
            jfNot.txtCodigo.grabFocus();
            jfNot.txtCodigo.setText("");
            jfNot.txtDescripcion.setText("");
            jfNot.txtPrecio.setText("");
            jfNot.txtCantidad.setText("1");
            jfNot.txtTotal.setText("");
            jfNot.txtTotalG.setText(String.valueOf(calTotal(modelo)));
            jfNot.txtTotalCredito.setText(String.valueOf(calTotal(modelo)));
            }else{
                JOptionPane.showMessageDialog(null,"Codigo y cantidad deben contener valores apropiados..");
                jfNot.txtCodigo.grabFocus();
                jfNot.txtCantidad.setText("1");
            }
        }        
        //Boton Buscar*********************************************************
        if(e.getSource()==jfNot.btnBuscar){
            if(jfNot.txtNroFactura.getText().length()>0){
                ven.setNroFactura(Integer.valueOf(jfNot.txtNroFactura.getText()));                
                try{
                    //Inicializar JTable si ya hubo una consulta anterior
                    if(jfNot.jtDetalle.getRowCount()!=0){limpiarJTable();}
                    //obtener los datos del Presupuesto
                    ven=cVen.obtenerVCab(ven);
                    if ((ven)!=null){
                        //Copia la cabecera
                            //Taer Datos de Cliente
                            cli.setCi(ven.getIdCliente());                        
                            conCli.buscar(cli);
                            jfNot.txtNroci.setText(String.valueOf(cli.getCi()));                            
                            jfNot.txtRuc.setText(cli.getRuc());
                            jfNot.txtNombre.setText(cli.getNombre()+" "+cli.getApellido());
                            //jfNot.jlUsuario.setText(not.getIdUsuario());                        
                            //jfNot.JDCfechaNota.setDate(not.getFecha());
                            //jfNot.jlNroCaja.setText(String.valueOf(not.getNumCaja()));
                            /*if(not.getProcesado()==0){jfNot.jtxtEstado.setText("PENDIENTE");
                                }else{jfNot.jtxtEstado.setText("PROCESADO");}*/
                            /*if(not.getTipoPago()==1){
                                jfNot.jrbContado.setSelected(true);
                            }else{
                                jfNot.jrbCredito.setSelected(true);
                            }*/
                            jfNot.txtTotalG.setText(String.valueOf(ven.getTotalF()));
                            jfNot.jtxtDescuento.setText(String.valueOf(ven.getDescuento()));
                            jfNot.jtxtTotalDescuento.setText(String.valueOf(ven.getTotalDescuento()));
                            jfNot.txtTotalCredito.setText(String.valueOf(ven.getTotalCobrar()));
                        listaDetalle=cVen.obtenerVDet(ven);                        
                        dtmDetalle=(DefaultTableModel)jfNot.jtDetalle.getModel();                        
                        if(!listaDetalle.isEmpty()){//Verificamos si trajo filas
                                                    
                            Object[] object = new Object[7];
                            String cadena="Sin Descripcion";
                            for(int i=0; i<listaDetalle.size();i++){
                                object[0]=listaDetalle.get(i).getIdProducto();
                                object[1]=listaDetalle.get(i).getIdLote();                           
                                //object[2]=listaDetalle.get(i).getIdProducto();
                                pro.setCodigo(listaDetalle.get(i).getIdProducto());
                                if(conPro.buscar(pro)){
                                    cadena=pro.getDescripcion();                                
                                }
                                object[2]=cadena;
                                object[3]=listaDetalle.get(i).getLimite();
                                object[4]=listaDetalle.get(i).getPrecio();
                                object[5]=listaDetalle.get(i).getCantidad();
                                object[6]=listaDetalle.get(i).getPrecio()*listaDetalle.get(i).getCantidad();
                                dtmDetalle.addRow(object);                   
                        }
                        //hacer visible los datos en el JTable
                        jfNot.jtDetalle.setModel(dtmDetalle);       
                        }else{                
                        JOptionPane.showMessageDialog(null, "Venta no tiene detalle "+not.getNroPresupuesto()
                                ,"Informe",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }else{                
                        JOptionPane.showMessageDialog(null, "No existe registros para el nro "+not.getNroPresupuesto()
                                ,"Informe",JOptionPane.INFORMATION_MESSAGE);
                        }
                    
                }catch (SQLException | NullPointerException ex) {
                    
                    JOptionPane.showMessageDialog(null,"Error al buscar "+ex);                    
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ingrese un valor valido");
                jfNot.txtNroFactura.grabFocus();
            }
        }
        //Registrar la venta***************************************************
        if(e.getSource()==jfNot.btnRegistrar){
            if (jfNot.jtDetalle.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Favor ingrese datos de la venta");
            }
            else if(Validar()==false){
                JOptionPane.showMessageDialog(jfNot, "Favor complete los datos del Cliente...");
            }else{
                //obtener datos del formulario factura
                //1-obtener dato de Cliente                
                not.setIdCliente((jfNot.txtNroci.getText()));                
                //2-Obtener el nro de Factura
                not.setNroFactura(Integer.parseInt(jfNot.txtNroFactura.getText()));                
                //3-formatear la fecha para poder guardarlo en una base de datos                  
                  SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");                    
                  String fecha2;                    
                  fecha2 = (formatofecha.format(jfNot.JDCfechaNota.getDate()));                
                  java.sql.Date fecha=java.sql.Date.valueOf(fecha2);
                  not.setFecha(fecha);
                //4-Obtener la hora del Sistema
                  //Objetos tipo hora
                  Tiempo hora=new Tiempo();                    
                  java.sql.Time hhmm=java.sql.Time.valueOf(hora.hora);
                  not.setHora(hhmm);
                //5-verificar si factura es contado o crédito
                //Si es contado Cancelado=1, credito Cancelado=0
                if (jfNot.jrbContado.isSelected()){not.setTipoPago(1);not.setCancelado(1);
                  }else{not.setTipoPago(2);not.setCancelado(0);
                }   
                //6-Dato de Usuario
                not.setIdUsuario(jfNot.jlUsuario.getText());
                //7-Datos Nro Caja
                not.setNumCaja(Integer.parseInt(jfNot.jlNroCaja.getText()));
                //8-Datos Cajero
                not.setIdVendedor((jfNot.jlbCajero.getText()));
                //8-totalfactura
                String t=jfNot.txtTotalG.getText();                
                int T=Integer.valueOf(t);
                not.setTotalF((T));
                //Calcular ganancia 
                //calcularG();
                not.setGanancia(ganancia);           
                //Estado de la venta
                not.setEstado("APROBADO");
                not.setNronota(Integer.valueOf(jfNot.txtNroNota.getText()));
                not.setIdArqueo(Integer.valueOf(jfNot.jtxtArqueo.getText()));
                not.setIdTimbrado(timbrado);
                not.setDescuento(Integer.valueOf(jfNot.jtxtDescuento.getText()));
                not.setTotalDescuento(Integer.valueOf(jfNot.jtxtTotalDescuento.getText()));
                not.setTotalCobrar(Integer.valueOf(jfNot.txtTotalCredito.getText()));
                not.setTotalGanancia(ganancia-not.getDescuento());
                //insertar la cabecera de la factura                
                try{
                    if(cN.registrar(not)){                
                    //regsistrar el detalle de la factura                
                    //obtener los datos de la tabla de detalle de la factura
                    detalle=jfNot.jtDetalle.getModel();
                    for(int i=0;i<detalle.getRowCount();i++){                
                        //System.out.println(table.getValueAt(j, x));
                        //detF.setIdVenta(Integer.valueOf(jfTic.txtNrofactura.getText()));                                                       
                        try{
                        detF.setIdNota(not.getNronota());
                        detF.setIdProducto((String)detalle.getValueAt(i, 0));
                        detF.setIdLote((String)detalle.getValueAt(i, 1));
                        String a=detalle.getValueAt(i, 4).toString();
                        detF.setPrecio(Integer.valueOf(a));
                        a=detalle.getValueAt(i, 5).toString();
                        detF.setCantidad(Integer.valueOf(a));                  
                        //insertar el detalle de la factura
                        conD.registrarDV(detF);
                        } catch (NumberFormatException | ClassCastException ex) {
                            JOptionPane.showMessageDialog(null,"Error al castear!!!"+ex);
                        }   
                    }
                    //Actualizar estado Ticket
                    //cTic.modificarEstado(not);
                    //Actualizar Stock
                    actualizarStock();
                    JOptionPane.showMessageDialog(null,"Datos Actualizados correctamente!!!");
                    //System.out.println("Total de la venta: "+not.getTotalF());
                    //System.out.println("Ganancia de la venta: "+ganancia);
                    limpiar();
                    }else{
                        JOptionPane.showMessageDialog(null,"No se pudo guardar Nota, verifique los Datos");
                    }
                }catch(SQLException err){
                        JOptionPane.showMessageDialog(null,"No se pudo guardar la Nota"+" "+err);
                }               
            }                
        }
        //Eliminar lineas/Productos del detalle********************************
        if(e.getSource()==jfNot.jbtEliminar){
            if(jfNot.jtDetalle.isRowSelected(jfNot.jtDetalle.getSelectedRow())){
            DefaultTableModel modelo=(DefaultTableModel) jfNot.jtDetalle.getModel();
            modelo.removeRow(jfNot.jtDetalle.getSelectedRow());
            jfNot.txtTotalG.setText(String.valueOf(calTotal(modelo)));
            jfNot.txtTotalCredito.setText(String.valueOf(calTotal(modelo)));
            //actualizar ganancia
            calcularG(modelo); 
            }else{JOptionPane.showMessageDialog(null,"No Selecciono ninguna fila..");
            }
        }
        //Buscar Cliente********************************************************
        if(e.getSource()==jfNot.jbtBuscarCli){
           if(jfNot.txtNroci.getText().length()!=0){
               cli.setCi((jfNot.txtNroci.getText()));            
                try{
                    if(conCli.buscar(cli)){
                        jfNot.txtNombre.setText(cli.getNombre()+" "+cli.getApellido());                
                        jfNot.txtRuc.setText(cli.getRuc());                                        
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
                jfNot.txtNroFactura.grabFocus();
           } 
        }
        //Llamar al ormulario de Filtro de productos por nombre***************** 
        if(e.getSource()==jfNot.jbtBuscarP){
            int ban=2;
           jfFiltro jfF=new jfFiltro();           
           CtrlFiltro ctrlF=new CtrlFiltro(jfF,conPro,pro,jfNot,ban);
           jfF.setVisible(true);
           ctrlF.mostrarProductos();
        }
    }
     
    @Override
    public void focusGained(FocusEvent e) {
       if(e.getComponent()==jfNot.jtxtDescuento){
         jfNot.jtxtDescuento.setText("");
       }
    }

    @Override
    public void focusLost(FocusEvent fL) {
        //Acciones al perder foco campo Codigo formulario Factura**************
        if(fL.getComponent()==jfNot.txtCodigo){
            if(jfNot.txtCodigo.getText().length()>0){
                pro.setCodigo(jfNot.txtCodigo.getText());
                try{
                    if(conPro.buscar(pro)){
                        jfNot.txtDescripcion.setText(pro.getDescripcion());
                        //obtener lotes del producto si tiene vencimiento
                        llenarCombo();
                        if(jfNot.jrbMostrador.isSelected()){
                            p=pro.getPrecio();
                            jfNot.txtPrecio.setText(String.valueOf(p));
                        /*}else if(jfNot.jrbPuestoobra.isSelected()){
                            //p=pro.getCosto()+(pro.getCosto()*20/100);                            
                            //p=p+(p*pro.getIva()/100);
                            p=pro.getPrecio();
                            jfNot.txtPrecio.setText(String.valueOf(p));
                        }else if(jfNot.jrbFerreteria.isSelected()){
                            p=pro.getCosto()+(pro.getCosto()*15/100);
                            p=p+(p*pro.getIva()/100);
                            jfNot.txtPrecio.setText(String.valueOf(p));*/
                        }
                        jfNot.costo=pro.getCosto();
                        jfNot.jtxtLimite.setText(String.valueOf(pro.getCosto()));
                        jfNot.jtxtStock.setText(String.valueOf(pro.getExistencia()));
                        jfNot.txtCantidad.grabFocus();

                        
                    }else{
                        JOptionPane.showMessageDialog(null,"No Existe Producto..");
                        jfNot.txtCodigo.grabFocus();                        
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //Acciones al perder foco campo Cantidad formulario Factura************
        if(fL.getComponent()==jfNot.txtCantidad){            
            try {
                if(conPro.buscar(pro)){
                   if(pro.getExistencia()<=pro.getStock_minimo()){
                       JOptionPane.showMessageDialog(null, "Articulo con Stock al limite establecido..");
                   } 
                }
            } catch (SQLException ex) {
                Logger.getLogger(CtrlNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(jfNot.txtCantidad.getText().length()>0){                
                try{
                    jfNot.txtTotal.setText(String.valueOf(p*Integer.valueOf(jfNot.txtCantidad.getText())));                    
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Ingresado en Cantidad no es un número.."+e);
                    jfNot.txtCantidad.grabFocus();
                }    
            }else{
                JOptionPane.showMessageDialog(null,"Favor ingrese la cantidad a vender..");
                jfNot.txtCantidad.grabFocus();
            }    
        }
        //Acciones por perdida de foco de txtMonto, calcular vuelto
        if(fL.getComponent()==jfNot.txtMonto){
            if(jfNot.txtMonto.getText().length()!=0){
                try{
                int m=Integer.valueOf(jfNot.txtMonto.getText());
                int t;
                    t = Integer.valueOf(jfNot.txtTotalCredito.getText());
                int vuelto=m-t;
                jfNot.jlbVuelto.setText(String.valueOf(vuelto));
                }catch(NumberFormatException e){
                  JOptionPane.showMessageDialog(null, "Ingresado en Monto no es un número.."+e);
                    jfNot.txtMonto.grabFocus();  
                }
            }    
        }
        if(fL.getComponent()==jfNot.jtxtDescuento){
            if(jfNot.jtxtDescuento.getText().length()==0){
                jfNot.jtxtDescuento.setText("0");
            }
            jfNot.jtxtTotalDescuento.setText(String.valueOf(Integer.valueOf(jfNot.txtTotalG.getText())
                    *Integer.valueOf(jfNot.jtxtDescuento.getText())/100));
            jfNot.txtTotalCredito.setText(String.valueOf(Integer.valueOf(jfNot.txtTotalG.getText())
                    -Integer.valueOf(jfNot.jtxtTotalDescuento.getText())));
       }
    }
    
    //Calcular total de la factura**************************************** 
    public static int calTotal(DefaultTableModel detalle){
        int total=0;
        for(int i=0; i<detalle.getRowCount();i++){
            total+= (int)detalle.getValueAt(i, 6);               
        }
        return total;
    }
    //proceso limpiar******************************************************
    public void limpiar() throws SQLException{
        jfNot.txtNroci.setText("111");
        jfNot.txtNombre.setText("");        
        jfNot.txtRuc.setText("");
        jfNot.txtNroNota.setText("");
        jfNot.jlbVuelto.setText("");
        jfNot.txtMonto.setText("");
        jfNot.txtNroFactura.setText("0");
        try {
            //obtener siguiente nro de factura
            jfNot.txtNroNota.setText(cVen.buscarUltimaVen());
            buscarTimbrado();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlNotaCredito.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No sepudo encontar el ultimo registro de Venta!!");
        }
        jfNot.jrbContado.setSelected(true);
        jfNot.txtTotal.setText("0");
        jfNot.txtTotalG.setText("0");
        jfNot.jtxtDescuento.setText("0");
        jfNot.jtxtTotalDescuento.setText("0");
        jfNot.txtTotalCredito.setText("0");
        jfNot.jtxtLimiteDescuento.setText("0");
       //limpiar las filas de la tabla detalle
        detalle=jfNot.jtDetalle.getModel();
         DefaultTableModel modelo=(DefaultTableModel) jfNot.jtDetalle.getModel();
        for(int i=((detalle.getRowCount())-1);i>-1;i--){       
            modelo.removeRow(i);
        }
        jfNot.txtNroFactura.grabFocus();
        
    }
    //metodo que actualiza stock de producto************************************
    void actualizarStock() throws SQLException{
        for(int i=0;i<detalle.getRowCount();i++){            
            pro.setCodigo((detalle.getValueAt(i, 0)).toString());
            String c=detalle.getValueAt(i, 5).toString();                     
            int cant=(Integer.valueOf(c));
            conPro.buscar(pro);
            int sA=pro.getExistencia()+cant;
            try{
                conPro.ActualizarStock(pro,sA);
                //Actualizar stock en lote/producto/fechavencimientoen caso que
                //corresponda
                String nrolote=detalle.getValueAt(i, 1).toString();
                if(!"0".equals(nrolote)){
                    try{
                        l.setCantidad(sA);
                        l.setNrolote(nrolote);
                        conL.actualizarStock(l);
                    }catch(SQLException e){
                        JOptionPane.showMessageDialog(null, "Error al actualizar stock en lote", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null, "Error al actualizar stock", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    //Metodo llenar combo box con Categorias de productos***********************
    public void llenarCombo() throws SQLException{
        jfNot.jcbLote.removeAllItems();
        ArrayList<String> Lista=new ArrayList<>();
        Lista=conL.obtenerLote(pro);
        if(!Lista.isEmpty()){
            for(int i=0;i<Lista.size();i++){
                jfNot.jcbLote.addItem(Lista.get(i));
            }
        }else{jfNot.jcbLote.addItem("0");}
    }
    //Inicializar la tabla******************************************************
    public void limpiarJTable (){
        while(jfNot.jtDetalle.getRowCount()>0) {
            ((DefaultTableModel)jfNot.jtDetalle.getModel()).removeRow(0);
        }
    }
    public void calcularG(DefaultTableModel detalle){        
        ganancia=0;       
        for(int i=0; i<detalle.getRowCount();i++){
            ganancia+= Integer.valueOf(detalle.getValueAt(i, 5).toString())*Integer.valueOf(detalle.getValueAt(i, 3).toString());
        }    
        jfNot.ganancia=ganancia;
        jfNot.jtxtLimiteDescuento.setText(String.valueOf(ganancia));
        
    }
    public boolean Validar(){
        return !(jfNot.txtNroci.getText().length()==0 ||
                jfNot.txtNroNota.getText().length()==0 ||
               jfNot.txtNroFactura.getText().trim().isEmpty()
                );        
    }
    public void buscarTimbrado() throws SQLException {
        conTim.buscarTimbradoActual(t);
        jfNot.jtxtTimbrado.setText(String.valueOf(t.getNumero_timbrado()));
        jfNot.jlbFechaInicio.setText(String.valueOf(t.getFecha_desde()));
        jfNot.jlbFechaFin.setText(String.valueOf(t.getFecha_Hasta()));
        timbrado=t.getId();
    }
    /*public void calcularG(){
        int i=0,tv=0;
        ganancia=0;
        //Verificar tipo de cliente, para saber incremento aplicado
        if(jfNot.jrbMostrador.isSelected()){i=20;
        }else if(jfNot.jrbPuestoobra.isSelected()){i=30;
        }else if(jfNot.jrbFerreteria.isSelected()){i=15;
        }
        tv=Integer.valueOf(jfNot.txtTotalG.getText());
        tv=tv-(tv/11);
        ganancia=(tv*i/(100+i));
    }*/
}
