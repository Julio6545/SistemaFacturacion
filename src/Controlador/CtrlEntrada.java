package Controlador;

import Modelo.ConEntrada;
import Modelo.ConsultaCliente;
import Modelo.ConsultaDetalleTick;
import Modelo.ConsultaProducto;
import Modelo.ConsultaV;
import Modelo.DetalleV;
import Modelo.Entrada;
import Modelo.Persona;
import Modelo.Producto;
import Modelo.Venta;
import Vista.jfEntrada;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author JFAA
 */
public class CtrlEntrada implements ActionListener, FocusListener{
    private Entrada ent;
    private ConEntrada conE;
    private jfEntrada jfE;
    private Venta v=new Venta();
    private ConsultaV conV=new ConsultaV();
    
    //Variable detalle de factura
    TableModel detalle;
    //(Objetos/Variables)Instanciar la clase producto Colsuta Producto
    private Producto pro=new Producto();
    private ConsultaProducto conPro=new ConsultaProducto();
    //Instanciar la clase Detalle Factura y Consulta Detalle
    DetalleV detF=new DetalleV();
    ConsultaDetalleTick conD=new ConsultaDetalleTick();    
    
    //Objetos/Variables Persona, ConsultaCliente
    private Persona cli=new Persona();
    private ConsultaCliente conCli=new ConsultaCliente();    
    //variables auxiliares 
    int p;//precio del producto
    
    //Constructor
    public CtrlEntrada(Entrada ent,ConEntrada conE,jfEntrada jfE){
        this.ent=ent;
        this.conE=conE;
        this.jfE=jfE;               
        this.jfE.btnAgregar.addActionListener(this);
        this.jfE.btnRegistrar.addActionListener(this);
        this.jfE.btnBuscar.addActionListener(this);
        this.jfE.jbtEliminar.addActionListener(this);        
        this.jfE.txtCodigo.addFocusListener(this);
        this.jfE.txtCantidad.addFocusListener(this);
        this.jfE.jbtBuscarVenta.addActionListener(this);
    }
    
    //inicializar los valores en el formulario
    public void iniciar() throws SQLException{
        Calendar gc=new GregorianCalendar();
        this.jfE.jdcFechaEntrada.setCalendar(gc);
        //obtener el siguiente numero de factura
        this.jfE.txtNroEntrada.setText(conE.buscarUltimaEnt());
        jfE.txtNroEntidad.setText("111");        
        jfE.txtCantidad.setText("1");
        jfE.jrbDevolucion.setSelected(true);        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //controlar los eventos de los botones y otros componentes del formulario
        if(e.getSource()==jfE.jbtBuscarVenta){
            v.setNroFactura(Integer.valueOf(jfE.txtNrofactura.getText()));
            try {
                if(conV.obtenerVCab(v)!=null){                    
                   jfE.jtxtArqueo.setText(String.valueOf(v.getIdArqueo()));
                   jfE.jtxtEstado.setText(v.getEstado());
                   jfE.jtxtTimbrado.setText(String.valueOf(v.getIdTimbrado()));
                   jfE.jlbCajero.setText(v.getIdUsuario());
                   jfE.jlbNroCaja.setText(String.valueOf(v.getNumCaja()));
                   jfE.jlbUsuario1.setText(v.getIdVendedor());
                   jfE.JDCfechafactura.setDate(v.getFecha());
                } else {
                   JOptionPane.showMessageDialog(null,"No se encontro la venta, verifique Número..");
                }
            } catch (SQLException ex) {
                Logger.getLogger(CtrlEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Agregar líneas/Productos al detelle
        if(e.getSource()==jfE.btnAgregar){
            if(jfE.txtCodigo.getText().length()>0 && jfE.txtCantidad.getText().length()>0 
                    && jfE.txtDescripcion.getText().length()>0 ){
            DefaultTableModel modelo=(DefaultTableModel) jfE.jtDetalle.getModel();
            int cant=Integer.valueOf(jfE.txtCantidad.getText());
            p= Integer.valueOf(jfE.txtPrecio.getText());
            modelo.addRow(new Object[]{jfE.txtCodigo.getText(),
                jfE.txtDescripcion.getText(),jfE.txtPrecio.getText(),jfE.txtCantidad.getText(),
                p*cant    
            } );
            //limpiar los campos de ingreso
            jfE.txtCodigo.grabFocus();
            jfE.txtCodigo.setText("");
            jfE.txtDescripcion.setText("");
            jfE.txtPrecio.setText("");
            jfE.txtCantidad.setText("1");
            jfE.txtTotal.setText("");
            jfE.txtTotalE.setText(String.valueOf(calTotal(modelo)));
            }else{
                JOptionPane.showMessageDialog(null,"Codigo y cantidad deben contener valores apropiados..");
                jfE.txtCodigo.grabFocus();
                jfE.txtCantidad.setText("1");
            }
        }        
        //Boton Buscar
        if(e.getSource()==jfE.btnBuscar){
            if(jfE.txtNroEntidad.getText().length()>0){
                cli.setCi((jfE.txtNroEntidad.getText()));            
                try{
                    if(conCli.buscar(cli)){
                        jfE.txtNombre.setText(cli.getNombre()+" "+cli.getApellido());                
                        jfE.txtRucEntidad.setText(cli.getRuc());
                        //JOptionPane.showMessageDialog(null,"");                
                    }else{
                    JOptionPane.showMessageDialog(null,"No Existe Cliete/Entidad..");
                    //limpiar();
                    }
                }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar Cliente/Entidad");
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ingrese un valor valido");
                jfE.txtNroEntidad.grabFocus();
            }
        }
        //Registrar la venta
        if(e.getSource()==jfE.btnRegistrar){
            if (jfE.jtDetalle.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Favor ingrese datos de la venta");
            }else{
                //obtener datos del formulario factura
                //1-obtener dato de Cliente                
                ent.setIdCliente(Integer.parseInt(jfE.txtNroEntidad.getText()));                
                //2-Obtener el nro de Factura
                ent.setNroEntrada(Integer.parseInt(jfE.txtNroEntrada.getText()));                
                //3-formatear la fecha para poder guardarlo en una base de datos                  
                  SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");                    
                  String fecha2;                    
                  fecha2 = (formatofecha.format(jfE.jdcFechaEntrada.getDate()));                
                  java.sql.Date fecha=java.sql.Date.valueOf(fecha2);
                  ent.setFecha(fecha);
                //4-Obtener la hora del Sistema
                  //Objetos tipo hora
                  Tiempo hora=new Tiempo();                    
                  java.sql.Time hhmm=java.sql.Time.valueOf(hora.hora);
                  ent.setHora(hhmm);
                //5-verificar si factura es contado o crédito
                if (jfE.jrbInventario.isSelected()){ent.setTipoEntrada(1);}else{ent.setTipoEntrada(2);}   
                //6-Dato de Usuario
                ent.setIdUsuario(jfE.jlUsuario.getText());                                
                //7-total Salida
                String t=jfE.txtTotalE.getText();                
                int T=Integer.valueOf(t);
                ent.setTotalE((T));
                //insertar la cabecera de la factura                
                try{
                    if (conE.registrar(ent)){                
                    //regsistrar el detalle de la factura                
                    //obtener los datos de la tabla de detalle de la factura
                    detalle=jfE.jtDetalle.getModel();
                    for(int i=0;i<detalle.getRowCount();i++){                
                        //System.out.println(table.getValueAt(j, x));
                        detF.setIdVenta(Integer.valueOf(jfE.txtNroEntrada.getText()));                                                       
                        detF.setIdProducto((String)detalle.getValueAt(i, 0));
                        String a=(String)detalle.getValueAt(i, 2);
                        detF.setPrecio(Integer.valueOf(a));
                        a=(String)detalle.getValueAt(i, 3);
                        detF.setCantidad(Integer.parseInt(a));                  
                        //insertar el detalle de la factura
                        conD.registrar(detF);
                    }
                    //Actualizar Stock
                    actualizarStock();
                    JOptionPane.showMessageDialog(null,"Datos Actualizados correctamente!!!");
                    limpiar();
                    }else{
                        JOptionPane.showMessageDialog(null,"No se pudo guardar Salida, verifique los Datos");
                    }
                }catch(SQLException err){
                        JOptionPane.showMessageDialog(null,"No se pudo guardar la Salida"+" "+err);
                }               
            }                
        }
        //Eliminar lineas/Productos del detalle
        if(e.getSource()==jfE.jbtEliminar){
            if(jfE.jtDetalle.isRowSelected(jfE.jtDetalle.getSelectedRow())){
            DefaultTableModel modelo=(DefaultTableModel) jfE.jtDetalle.getModel();
            modelo.removeRow(jfE.jtDetalle.getSelectedRow());
            jfE.txtTotalE.setText(String.valueOf(calTotal(modelo)));
            }else{JOptionPane.showMessageDialog(null,"No Selecciono ninguna fila..");
            }
        }
         
    }
     
    @Override
    public void focusGained(FocusEvent e) {
       /*if(e.getComponent()==jfS.txtCantidad){
            System.out.println("Cantidad En Foco Ganado: "+Integer.valueOf(jfFac.txtCantidad.getText()));            
            jfS.txtTotal.setText(String.valueOf(Integer.valueOf(jfS.txtPrecio.getText())
                    *Integer.valueOf(jfS.txtCantidad.getText())));
       }*/
    }

    @Override
    public void focusLost(FocusEvent fL) {
        //Acciones al perder foco campo Codigo formulario Factura        
        if(fL.getComponent()==jfE.txtCodigo){
            if(jfE.txtCodigo.getText().length()>0){
                pro.setCodigo(jfE.txtCodigo.getText());
                try{
                    if(conPro.buscar(pro)){
                        jfE.txtDescripcion.setText(pro.getDescripcion());
                        jfE.txtPrecio.setText(String.valueOf(pro.getCosto()));                        
                        jfE.txtCantidad.grabFocus();                        
                    }else{
                        JOptionPane.showMessageDialog(null,"No Existe Producto..");
                        jfE.txtCodigo.grabFocus();                        
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //Acciones al perder foco campo Cantidad formulario Factura
        if(fL.getComponent()==jfE.txtCantidad){            
            if(jfE.txtCantidad.getText().length()>0){
                p=pro.getCosto();    
                jfE.txtTotal.setText(String.valueOf(p*Integer.valueOf(jfE.txtCantidad.getText())));                
            }else{
                JOptionPane.showMessageDialog(null,"Favor ingrese la cantidad a vender..");
                jfE.txtCantidad.grabFocus();
            }    
        }        
    }
    
    //Calcular total de la factura 
    public static int calTotal(DefaultTableModel detalle){
        int total=0;
        for(int i=0; i<detalle.getRowCount();i++){
            total+= (int)detalle.getValueAt(i, 4);               
        }
        return total;
    }
    //proceso limpiar
    public void limpiar(){
        jfE.txtNroEntidad.setText("123");
        jfE.txtNombre.setText("");        
        jfE.txtRucEntidad.setText("");
        jfE.txtNroEntrada.setText("");
        try {
            //obtener siguiente nro de factura
            jfE.txtNroEntrada.setText(conE.buscarUltimaEnt());
        } catch (SQLException ex) {
            Logger.getLogger(CtrlEntrada.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No sepudo encontar el ultimo registro de factura!!");
        }
        jfE.jrbInventario.setSelected(true);
        jfE.txtTotal.setText("");
        jfE.txtTotalE.setText("");
       //limpiar las filas de la tabla detalle
        detalle=jfE.jtDetalle.getModel();
         DefaultTableModel modelo=(DefaultTableModel) jfE.jtDetalle.getModel();
        for(int i=((detalle.getRowCount())-1);i>-1;i--){       
            modelo.removeRow(i);
        }    
    
    }
    //metodo que actualiza stock de producto
    void actualizarStock() throws SQLException{
        for(int i=0;i<detalle.getRowCount();i++){            
            pro.setCodigo(((String)detalle.getValueAt(i, 0)));
            int cant=Integer.parseInt((String)detalle.getValueAt(i,3));
            conPro.buscar(pro);
            int sA=pro.getExistencia()+cant;
            conPro.ActualizarStock(pro,sA);
            
        }
    }
}
