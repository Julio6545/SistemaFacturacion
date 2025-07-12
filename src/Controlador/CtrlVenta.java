package Controlador;

import Modelo.ConLote;
import Modelo.ConTicket;
import Modelo.ConsultaCliente;
import Modelo.ConsultaDetalleV;
import Modelo.ConsultaV;
import Modelo.ConsultaProducto;
import Modelo.ConsultaTimbrado;
import Modelo.DetalleV;
import Modelo.Empresa;
import Modelo.Venta;
import Modelo.Lote;
import Modelo.Persona;
import Modelo.Producto;
import Modelo.Timbrado;
import Vista.jfFiltro;
import Vista.jfImprimirFactura;
import Vista.jfVenta;
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
public class CtrlVenta implements ActionListener, FocusListener{
    private final Venta ven;
    private final ConsultaV cVen;
    private final ConTicket cTic = new ConTicket();
    private final jfVenta jfVen;
    private final Empresa emp = new Empresa();
    
    private final Timbrado t= new Timbrado();
    private final ConsultaTimbrado conTim=new ConsultaTimbrado();
    //Variable detalle de factura
    TableModel detalle;    
    List<DetalleV> listaDetalle;
    DefaultTableModel dtmDetalle=new DefaultTableModel(); 
    //(Objetos/Variables)Instanciar la clase producto Colsuta Producto
    private final Producto pro=new Producto();
    private final ConsultaProducto conPro=new ConsultaProducto();
    private final ConLote conL=new ConLote();
    private final Lote l=new Lote();
    ImagenBoton imgF=new ImagenBoton();    
    //Instanciar la clase Detalle Factura y Consulta Detalle, Ticket
    DetalleV detF=new DetalleV();
    ConsultaDetalleV conD=new ConsultaDetalleV();
    ConTicket conT=new ConTicket();
    
    //Objetos/Variables Persona, ConsultaCliente
    private final Persona cli=new Persona();
    private final ConsultaCliente conCli=new ConsultaCliente();    
    //variables auxiliares 
    int p;//precio del producto
    int ganancia=0, timbrado, limite=0;
    //Constructor
    public CtrlVenta(Venta fac,ConsultaV cFac,jfVenta jfFac){
        this.ven=fac;
        this.cVen=cFac;
        this.jfVen=jfFac;               
        this.jfVen.btnAgregar.addActionListener(this);
        this.jfVen.btnRegistrar.addActionListener(this);
        this.jfVen.btnBuscar.addActionListener(this);
        this.jfVen.jbtBuscarCli.addActionListener(this);
        this.jfVen.jbtEliminar.addActionListener(this);
        this.jfVen.jbtBuscarP.addActionListener(this);
        this.jfVen.txtCodigo.addFocusListener(this);
        this.jfVen.txtCantidad.addFocusListener(this);
        this.jfVen.txtMonto.addFocusListener(this);
        this.jfVen.jtxtDescuento.addFocusListener(this);
    }
    
    //inicializar los valores en el formulario
    public void iniciar() throws SQLException{
        Calendar gc=new GregorianCalendar();
        this.jfVen.JDCfechafactura.setCalendar(gc);
        //obtener el siguiente numero de factura
        this.jfVen.txtNrofactura.setText(cVen.buscarUltimaVen());
        jfVen.txtNroci.setText("111");        
        jfVen.txtCantidad.setText("1");
        jfVen.jrbContado.setSelected(true);
        jfVen.jrbMostrador.setSelected(true);
        jfVen.txtTotalG.setText("0");
        jfVen.txtNroPresupuesto.setText("0");
        jfVen.txtNroPresupuesto.grabFocus();
        jfVen.jbtBuscarP.setIcon(imgF.setIcono("/img/buscar3.png", jfVen.jbtBuscarP));
        buscarTimbrado();
    }

    @Override
    public void actionPerformed(ActionEvent e) {//*****************************
        //controlar los eventos de los botones y otros componentes del formulario
        //Agregar líneas/Productos al detelle
        if(e.getSource()==jfVen.btnAgregar){
            if(jfVen.txtCodigo.getText().length()>0 && jfVen.txtCantidad.getText().length()>0 
                    && jfVen.txtDescripcion.getText().length()>0 )
            {
                DefaultTableModel modelo=(DefaultTableModel) jfVen.jtDetalle.getModel();
                jfVen.jtDetalle.setModel(modelo);
                //Establecer el ancho preferido de la segunda columna
                jfVen.jtDetalle.getColumnModel().getColumn(2).setPreferredWidth(350);
                //Establecer foco en Nro lote            
                jfVen.jtDetalle.grabFocus();
                int cant=Integer.valueOf(jfVen.txtCantidad.getText());
                p= Integer.valueOf(jfVen.txtPrecio.getText());
                modelo.addRow(
                        new Object[]{jfVen.txtCodigo.getText(),
                            jfVen.jcbLote.getSelectedItem().toString(),
                            jfVen.txtDescripcion.getText(),
                            Integer.valueOf(jfVen.jtxtLimite.getText()),
                            jfVen.txtPrecio.getText(),
                            jfVen.txtCantidad.getText(),                            
                            p*cant    
                        } 
                );
                //actuallizar ganancia
            calcularG(modelo);                
            //limpiar los campos de ingreso
            jfVen.txtCodigo.grabFocus();
            jfVen.txtCodigo.setText("");
            jfVen.txtDescripcion.setText("");
            jfVen.txtPrecio.setText("");
            jfVen.txtCantidad.setText("1");
            jfVen.txtTotal.setText("");
            jfVen.txtTotalG.setText(String.valueOf(calTotal(modelo)));
            jfVen.txtTotalCobrar.setText(String.valueOf(calTotal(modelo)));
            }else{
                JOptionPane.showMessageDialog(null,"Codigo y cantidad deben contener valores apropiados..");
                jfVen.txtCodigo.grabFocus();
                jfVen.txtCantidad.setText("1");
            }
        }        
        //Boton Buscar*********************************************************
        if(e.getSource()==jfVen.btnBuscar){
            if(jfVen.txtNroPresupuesto.getText().length()>0){
                ven.setNroPresupuesto(Integer.valueOf(jfVen.txtNroPresupuesto.getText()));                
                try{
                    //Inicializar JTable si ya hubo una consulta anterior
                    if(jfVen.jtDetalle.getRowCount()!=0){limpiarJTable();}
                    //obtener los datos del Presupuesto
                    if (conT.obtenerTCab(ven)){
                        //Copia la cabecera
                            //Taer Datos de Cliente
                            cli.setCi(ven.getIdCliente());                        
                            conCli.buscar(cli);
                            jfVen.txtNroci.setText(String.valueOf(cli.getCi()));
                            jfVen.txtRuc.setText(cli.getRuc());
                            jfVen.txtNombre.setText(cli.getNombre()+" "+cli.getApellido());
                            jfVen.jlUsuario.setText(ven.getIdUsuario());                        
                            jfVen.JDCfechafactura.setDate(ven.getFecha());
                            jfVen.jlNroCaja.setText(String.valueOf(ven.getNumCaja()));
                            if(ven.getProcesado()==0){jfVen.jtxtEstado.setText("PENDIENTE");
                                }else{jfVen.jtxtEstado.setText("PROCESADO");}
                            if(ven.getTipoPago()==1){
                                jfVen.jrbContado.setSelected(true);
                            }else{
                                jfVen.jrbCredito.setSelected(true);
                            }
                            jfVen.txtTotalG.setText(String.valueOf(ven.getTotalF()));
                        listaDetalle=conT.obtenerTDet(ven);                        
                        dtmDetalle=(DefaultTableModel)jfVen.jtDetalle.getModel();                        
                        if(!listaDetalle.isEmpty()){//Verificamos si trajo filas
                                                    
                            Object[] object = new Object[7];
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
                                object[3]=listaDetalle.get(i).getLimite();
                                object[4]=listaDetalle.get(i).getPrecio();
                                object[5]=listaDetalle.get(i).getCantidad();
                                object[6]=listaDetalle.get(i).getPrecio()*listaDetalle.get(i).getCantidad();
                                dtmDetalle.addRow(object);                   
                                limite = limite+listaDetalle.get(i).getLimite();
                        }
                        //hacer visible los datos en el JTable
                        jfVen.jtDetalle.setModel(dtmDetalle);
                        jfVen.jtxtLimiteDescuento.setText(String.valueOf(limite));
                        jfVen.txtTotalCobrar.setText(String.valueOf(ven.getTotalF()));
                         calcularG(dtmDetalle);
                        }else{                
                        JOptionPane.showMessageDialog(null, "Venta no tiene detalle "+ven.getNroPresupuesto()
                                ,"Informe",JOptionPane.INFORMATION_MESSAGE);
                        }
                    }else{                
                        JOptionPane.showMessageDialog(null, "No existe registros para el nro "+ven.getNroPresupuesto()
                                ,"Informe",JOptionPane.INFORMATION_MESSAGE);
                        }
                    
                }catch (SQLException ex) {
                    
                    JOptionPane.showMessageDialog(null,"Error al buscar "+ex);                    
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ingrese un valor valido");
                jfVen.txtNroPresupuesto.grabFocus();
            }
        }
        //Registrar la venta***************************************************
        if(e.getSource()==jfVen.btnRegistrar){
            if (jfVen.jtDetalle.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Favor ingrese datos de la venta");
            }
            else if(Validar()==false){
                JOptionPane.showMessageDialog(jfVen, "Favor complete los datos del Cliente...");
            }else{
                //obtener datos del formulario factura
                //1-obtener dato de Cliente                
                ven.setIdCliente((jfVen.txtNroci.getText()));                
                try {
                    //2-Obtener el nro de Factura
                    //ven.setNroFactura(Integer.parseInt(jfVen.txtNrofactura.getText()));
                    ven.setNroFactura(Integer.parseInt(cVen.buscarUltimaVen()));
                } catch (SQLException ex) {
                  JOptionPane.showMessageDialog(jfVen, "Error al recuperar Datos de la factura..");  
                  return;
                }
                //3-formatear la fecha para poder guardarlo en una base de datos                  
                  SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");                    
                  String fecha2;                    
                  fecha2 = (formatofecha.format(jfVen.JDCfechafactura.getDate()));                
                  java.sql.Date fecha=java.sql.Date.valueOf(fecha2);
                  ven.setFecha(fecha);
                //4-Obtener la hora del Sistema
                  //Objetos tipo hora
                  Tiempo hora=new Tiempo();                    
                  java.sql.Time hhmm=java.sql.Time.valueOf(hora.hora);
                  ven.setHora(hhmm);
                //5-verificar si factura es contado o crédito
                //Si es contado Cancelado=1, credito Cancelado=0
                if (jfVen.jrbContado.isSelected()){ven.setTipoPago(1);ven.setCancelado(1);
                  }else{ven.setTipoPago(2);ven.setCancelado(0);
                }   
                //6-Dato de Usuario
                ven.setIdVendedor(jfVen.jlUsuario.getText());
                //7-Datos Nro Caja
                ven.setNumCaja(Integer.parseInt(jfVen.jlNroCaja.getText()));
                //8-Datos Cajero
                ven.setIdUsuario((jfVen.jlbCajero.getText()));
                //8-totalfactura
                String t=jfVen.txtTotalG.getText();                
                int T=Integer.valueOf(t);
                ven.setTotalF((T));
                //Calcular ganancia 
                //calcularG();
                ven.setGanancia(ganancia);           
                //Estado de la venta
                ven.setEstado("APROBADO");
                ven.setNroPresupuesto(Integer.valueOf(jfVen.txtNroPresupuesto.getText()));
                ven.setIdArqueo(Integer.valueOf(jfVen.jtxtArqueo.getText()));
                ven.setIdTimbrado(timbrado);
                ven.setDescuento(Integer.valueOf(jfVen.jtxtDescuento.getText()));
                ven.setTotalDescuento(Integer.valueOf(jfVen.jtxtTotalDescuento.getText()));
                ven.setTotalCobrar(Integer.valueOf(jfVen.txtTotalCobrar.getText()));
                ven.setTotalGanancia(ganancia-ven.getTotalDescuento());
                //insertar la cabecera de la factura                
                try{
                    if(cVen.registrar(ven)){                
                    //regsistrar el detalle de la factura                
                    //obtener los datos de la tabla de detalle de la factura
                    detalle=jfVen.jtDetalle.getModel();
                    for(int i=0;i<detalle.getRowCount();i++){                
                        //System.out.println(table.getValueAt(j, x));
                        //detF.setIdVenta(Integer.valueOf(jfTic.txtNrofactura.getText()));                                                       
                        try{
                        detF.setIdVenta(ven.getNroFactura());
                        detF.setIdProducto((String)detalle.getValueAt(i, 0));
                        detF.setIdLote((String)detalle.getValueAt(i, 1));
                        String a=detalle.getValueAt(i, 4).toString();
                        detF.setPrecio(Integer.valueOf(a));
                        a=detalle.getValueAt(i, 5).toString();
                        detF.setCantidad(Integer.valueOf(a));                  
                        //insertar el detalle de la factura
                        conD.registrarDV(detF);
                        } catch (NumberFormatException | ClassCastException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(null,"Error al castear!!!"+ex);
                        }   
                    }
                    //Actualizar estado Ticket
                    cTic.modificarEstado(ven);
                    //Actualizar Stock
                    actualizarStock();
                    JOptionPane.showMessageDialog(null,"Datos Actualizados correctamente!!!");
                    //System.out.println("Total de la venta: "+ven.getTotalF());
                    //System.out.println("Ganancia de la venta: "+ganancia);                   
                    //consultar si desea imprimir
                        int op = JOptionPane.showConfirmDialog
                            (null, "¿Quieres imprimir comprovante?", 
                                    "Sistema Dolly", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                        //0=yes, 1=no, 2=cancel
                        if(op == 0) {
                            ImprimirFactura imp = null;
                           try {
                               imp = new ImprimirFactura();
                               emp.setIdEmpresa(19);//Casa Matriz
                               imp.imprimirFactura(ven,detalle,emp,1);//System.out.println("Has pulsado Yes");
                           } catch (NullPointerException ex) {
                               JOptionPane.showMessageDialog(jfVen, "Error "+ex, "Ticket", 1);
                           }
                            
                        }else if(op == 1){System.out.println("Has pulsado No");
                        }
                        limpiar();
                    }else{
                        JOptionPane.showMessageDialog(null,"No se pudo guardar Venta, verifique los Datos");
                    }
                }catch(SQLException err){
                        JOptionPane.showMessageDialog(null,"No se pudo guardar la Venta"+" "+err);
                }              
            }                
        }
        //Eliminar lineas/Productos del detalle********************************
        if(e.getSource()==jfVen.jbtEliminar){
            if(jfVen.jtDetalle.isRowSelected(jfVen.jtDetalle.getSelectedRow())){
            DefaultTableModel modelo=(DefaultTableModel) jfVen.jtDetalle.getModel();
            modelo.removeRow(jfVen.jtDetalle.getSelectedRow());
            jfVen.txtTotalG.setText(String.valueOf(calTotal(modelo)));
            jfVen.txtTotalCobrar.setText(String.valueOf(calTotal(modelo)));
            //actuallizar ganancia
            calcularG(modelo); 
            }else{JOptionPane.showMessageDialog(null,"No Selecciono ninguna fila..");
            }
        }
        //Buscar Cliente********************************************************
        if(e.getSource()==jfVen.jbtBuscarCli){
           if(jfVen.txtNroci.getText().length()!=0){
               cli.setCi((jfVen.txtNroci.getText()));            
                try{
                    if(conCli.buscar(cli)){
                        jfVen.txtNombre.setText(cli.getNombre()+" "+cli.getApellido());                
                        jfVen.txtRuc.setText(cli.getRuc());                                        
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
                jfVen.txtNroPresupuesto.grabFocus();
           } 
        }
        //Llamar al ormulario de Filtro de productos por nombre***************** 
        if(e.getSource()==jfVen.jbtBuscarP){
            int ban=1;
           jfFiltro jfF=new jfFiltro();           
           CtrlFiltro ctrlF=new CtrlFiltro(jfF,conPro,pro,jfVen,ban);
           jfF.setVisible(true);
           ctrlF.mostrarProductos();
        }
    }
     
    @Override
    public void focusGained(FocusEvent e) {
       if(e.getComponent()==jfVen.jtxtDescuento){
         jfVen.jtxtDescuento.setText("");
       }
    }

    @Override
    public void focusLost(FocusEvent fL) {
        //Acciones al perder foco campo Codigo formulario Factura**************
        if(fL.getComponent()==jfVen.txtCodigo){
            if(jfVen.txtCodigo.getText().length()>0){
                pro.setCodigo(jfVen.txtCodigo.getText());
                try{
                    if(conPro.buscar(pro)){
                        //En esta linea le agrego tambien el lugar del producto, esta parte es a la hora de obtener
                        jfVen.txtDescripcion.setText(pro.getDescripcion()+" -P:"+pro.getPasillo()+"-E:"+pro.getEstante()+"-B:"+pro.getBandeja());
                        //obtener lotes del producto si tiene vencimiento
                        llenarCombo();
                        if(jfVen.jrbMostrador.isSelected()){
                            p=pro.getPrecio();
                            jfVen.txtPrecio.setText(String.valueOf(p));
                        /*}else if(jfVen.jrbPuestoobra.isSelected()){
                            //p=pro.getCosto()+(pro.getCosto()*20/100);                            
                            //p=p+(p*pro.getIva()/100);
                            p=pro.getPrecio();
                            jfVen.txtPrecio.setText(String.valueOf(p));
                        }else if(jfVen.jrbFerreteria.isSelected()){
                            p=pro.getCosto()+(pro.getCosto()*15/100);
                            p=p+(p*pro.getIva()/100);
                            jfVen.txtPrecio.setText(String.valueOf(p));*/
                        }
                        jfVen.costo=pro.getCosto();
                        jfVen.jtxtLimite.setText(String.valueOf(pro.getCosto()));
                        jfVen.jtxtStock.setText(String.valueOf(pro.getExistencia()));
                        jfVen.txtCantidad.grabFocus();

                        
                    }else{
                        JOptionPane.showMessageDialog(null,"No Existe Producto..");
                        jfVen.txtCodigo.grabFocus();                        
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //Acciones al perder foco campo Cantidad formulario Factura************
        if(fL.getComponent()==jfVen.txtCantidad){            
            try {
                if(conPro.buscar(pro)){
                   if(pro.getExistencia()<=pro.getStock_minimo()){
                       JOptionPane.showMessageDialog(null, "Articulo con Stock al limite establecido..");
                   } 
                }
            } catch (SQLException ex) {
                Logger.getLogger(CtrlVenta.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(jfVen.txtCantidad.getText().length()>0){                
                try{
                    jfVen.txtTotal.setText(String.valueOf(p*Integer.valueOf(jfVen.txtCantidad.getText())));                    
                }catch(NumberFormatException e){
                    JOptionPane.showMessageDialog(null, "Ingresado en Cantidad no es un número.."+e);
                    jfVen.txtCantidad.grabFocus();
                }    
            }else{
                JOptionPane.showMessageDialog(null,"Favor ingrese la cantidad a vender..");
                jfVen.txtCantidad.grabFocus();
            }    
        }
        //Acciones por perdida de foco de txtMonto, calcular vuelto
        if(fL.getComponent()==jfVen.txtMonto){
            if(jfVen.txtMonto.getText().length()!=0){
                try{
                int m=Integer.valueOf(jfVen.txtMonto.getText());
                int t;
                    t = Integer.valueOf(jfVen.txtTotalCobrar.getText());
                int vuelto=m-t;
                jfVen.jlbVuelto.setText(String.valueOf(vuelto));
                }catch(NumberFormatException e){
                  JOptionPane.showMessageDialog(null, "Ingresado en Monto no es un número.."+e);
                    jfVen.txtMonto.grabFocus();  
                }
            }    
        }
        if(fL.getComponent()==jfVen.jtxtDescuento){
            if(jfVen.jtxtDescuento.getText().length()>0){
            jfVen.jtxtTotalDescuento.setText(String.valueOf(Integer.valueOf(jfVen.txtTotalG.getText())
                    *Integer.valueOf(jfVen.jtxtDescuento.getText())/100));
            jfVen.txtTotalCobrar.setText(String.valueOf(Integer.valueOf(jfVen.txtTotalG.getText())
                    -Integer.valueOf(jfVen.jtxtTotalDescuento.getText())));
            }else{
                JOptionPane.showMessageDialog(jfVen, "Favor ingresar un valor adecuado en descuento..", "Error en valor de campo",
                            0);
                jfVen.jtxtDescuento.grabFocus();
            }
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
        jfVen.txtNroci.setText("111");
        jfVen.txtNombre.setText("");        
        jfVen.txtRuc.setText("");
        jfVen.txtNrofactura.setText("");
        jfVen.jlbVuelto.setText("");
        jfVen.txtMonto.setText("");
        jfVen.txtNroPresupuesto.setText("0");
        try {
            //obtener siguiente nro de factura
            jfVen.txtNrofactura.setText(cVen.buscarUltimaVen());
            buscarTimbrado();
        } catch (SQLException ex) {
            Logger.getLogger(CtrlVenta.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No sepudo encontar el ultimo registro de Venta!!");
        }
        jfVen.jrbContado.setSelected(true);
        jfVen.txtTotal.setText("0");
        jfVen.txtTotalG.setText("0");
        jfVen.jtxtDescuento.setText("0");
        jfVen.jtxtTotalDescuento.setText("0");
        jfVen.txtTotalCobrar.setText("0");
        jfVen.jtxtLimiteDescuento.setText("0");
       //limpiar las filas de la tabla detalle
        detalle=jfVen.jtDetalle.getModel();
         DefaultTableModel modelo=(DefaultTableModel) jfVen.jtDetalle.getModel();
        for(int i=((detalle.getRowCount())-1);i>-1;i--){       
            modelo.removeRow(i);
        }
        jfVen.txtNroPresupuesto.grabFocus();
        
    }
    //metodo que actualiza stock de producto************************************
    void actualizarStock() throws SQLException{
        for(int i=0;i<detalle.getRowCount();i++){            
            pro.setCodigo((detalle.getValueAt(i, 0)).toString());
            String c=detalle.getValueAt(i, 5).toString();                     
            int cant=(Integer.valueOf(c));
            conPro.buscar(pro);
            int sA=pro.getExistencia()-cant;
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
        jfVen.jcbLote.removeAllItems();
        ArrayList<String> Lista=new ArrayList<>();
        Lista=conL.obtenerLote(pro);
        if(!Lista.isEmpty()){
            for(int i=0;i<Lista.size();i++){
                jfVen.jcbLote.addItem(Lista.get(i));
            }
        }else{jfVen.jcbLote.addItem("0");}
    }
    //Inicializar la tabla******************************************************
    public void limpiarJTable (){
        while(jfVen.jtDetalle.getRowCount()>0) {
            ((DefaultTableModel)jfVen.jtDetalle.getModel()).removeRow(0);
        }
    }
    public void calcularG(DefaultTableModel detalle){        
        ganancia=0;  
        int limite = 0;
        for(int i=0; i<detalle.getRowCount();i++){
            long cantidad=Integer.valueOf(detalle.getValueAt(i, 5).toString());
            long precio = Integer.valueOf(detalle.getValueAt(i, 4).toString());
            long costo = Integer.valueOf(detalle.getValueAt(i, 3).toString());
            ganancia+= (precio-costo)*cantidad;               
            limite+=costo*cantidad;
        }    
        jfVen.ganancia=ganancia;
        jfVen.jtxtLimiteDescuento.setText(String.valueOf(limite));
        
    }
    public boolean Validar(){
        return !(jfVen.txtNroci.getText().length()==0 ||
                jfVen.txtNrofactura.getText().length()==0 ||
               jfVen.txtNroPresupuesto.getText().trim().isEmpty()
                );        
    }
    public void buscarTimbrado() throws SQLException {
        conTim.buscarTimbradoActual(t);
        jfVen.jtxtTimbrado.setText(String.valueOf(t.getNumero_timbrado()));
        jfVen.jlbFechaInicio.setText(String.valueOf(t.getFecha_desde()));
        jfVen.jlbFechaFin.setText(String.valueOf(t.getFecha_Hasta()));
        timbrado=t.getId();
    }
    /*public void calcularG(){
        int i=0,tv=0;
        ganancia=0;
        //Verificar tipo de cliente, para saber incremento aplicado
        if(jfVen.jrbMostrador.isSelected()){i=20;
        }else if(jfVen.jrbPuestoobra.isSelected()){i=30;
        }else if(jfVen.jrbFerreteria.isSelected()){i=15;
        }
        tv=Integer.valueOf(jfVen.txtTotalG.getText());
        tv=tv-(tv/11);
        ganancia=(tv*i/(100+i));
    }*/
    public void Imprimir(Venta ven) throws IOException, SQLException{
       jfImprimirFactura jfI = new jfImprimirFactura(); 
       CtrlImpresion ctrlI  = new CtrlImpresion(ven,jfI);
       ctrlI.iniciar();
       jfI.setVisible(true);
       /*impT.setCambio("0");
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
       impT.print(true);*/
    }
}
