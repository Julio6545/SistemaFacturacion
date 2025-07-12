/*
 * Clase que modela los procesos a realizar con la entidad de Compra
 */
package Controlador;

import Modelo.Compra;
import Modelo.ConsultaCompra;
import Modelo.ConDetComp;
import Modelo.ConsultaEmp;
import Modelo.ConsultaProducto;
import Modelo.ConsultaProveedor;
import Modelo.ConsultaVendedor;
import Modelo.DetalleC;
import Modelo.Empresa;
import Modelo.Producto;
import Modelo.Proveedor;
import Modelo.Vendedor;
import Vista.jfCompra;
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
public class CtrlCompra implements ActionListener, FocusListener{
    private Compra com;
    private ConsultaCompra cC;
    private jfCompra jfC;
    private formatoFecha fF=new formatoFecha();
    
    //Variable detalle de factura
    TableModel detalle;
    //(Objetos/Variables)Instanciar la clase producto Colsuta Producto
    private Producto pro=new Producto();
    private ConsultaProducto conPro=new ConsultaProducto();
    //Instanciar la clase Detalle Factura y Consulta Detalle
    DetalleC detF=new DetalleC();
    ConDetComp conD=new ConDetComp();
    
    
    //Objetos/Variables Vendedor, Consulta Vendedor
    private Vendedor vend=new Vendedor();
    private ConsultaVendedor conV=new ConsultaVendedor();    
        
    //Constructor
    public CtrlCompra(Compra com,ConsultaCompra cC,jfCompra jfC){
        this.com=com;
        this.cC=cC;
        this.jfC=jfC;               
        this.jfC.btnAgregar.addActionListener(this);
        this.jfC.btnRegistrar.addActionListener(this);
        this.jfC.btnBuscar.addActionListener(this);
        this.jfC.jbtBuscarP.addActionListener(this);
        this.jfC.jbtEliminar.addActionListener(this);
        this.jfC.txtCodigo.addFocusListener(this);
        this.jfC.txtCantidad.addFocusListener(this);
    }
    
    //inicializar los valores en el formulario
    public void iniciar(){
        Calendar gc=new GregorianCalendar();
        this.jfC.jdcFechaCompra.setCalendar(gc);
        this.jfC.jdcFechaVence.setCalendar(gc);
        this.jfC.txtNrofactura.setText("");
        jfC.txtRucEmpresa.setText("1");        
        jfC.txtCantidad.setText("1");
        jfC.txtCiVendedor.setText("1");
        jfC.jrbContado.setSelected(true);
        jfC.jrbFactura.setSelected(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //controlar los eventos de los botones y otros componentes del formulario
        //Agregar líneas/Productos al detelle
        if(e.getSource()==jfC.btnAgregar){
            if(jfC.txtCodigo.getText().length()>0 && jfC.txtCantidad.getText().length()>0 
                    && jfC.txtDescripcion.getText().length()>0 ){
            DefaultTableModel modelo=(DefaultTableModel) jfC.jtDetalle.getModel();
            int cant=Integer.valueOf(jfC.txtCantidad.getText());
            int precio= Integer.valueOf(jfC.txtPrecio.getText());
            modelo.addRow(new Object[]{jfC.txtCodigo.getText(),
                jfC.txtDescripcion.getText(),jfC.txtPrecio.getText(),jfC.jlbIncremento.getText(),jfC.txtCantidad.getText(),
                precio*cant    
            } );
            //limpiar los campos de ingreso
            jfC.txtCodigo.grabFocus();
            jfC.txtCodigo.setText("");
            jfC.txtDescripcion.setText("");
            jfC.txtPrecio.setText("");
            jfC.txtCantidad.setText("1");
            jfC.jlbIncremento.setText(null);
            jfC.jlbPrecio.setText(null);
            jfC.txtTotal.setText(null);
            jfC.txtTotalG.setText(String.valueOf(calTotal(modelo)));
            }else{
                JOptionPane.showMessageDialog(null,"Codigo y cantidad deben contener valores apropiados..");
                jfC.txtCodigo.grabFocus();
                jfC.txtCantidad.setText("1");
            }
        }        
        //Boton Buscar Empresa
        if(e.getSource()==jfC.btnBuscar){
            if(jfC.txtRucEmpresa.getText().length()>0){
                Empresa emp = new Empresa();
                emp.setRucempresa(jfC.txtRucEmpresa.getText());
                ConsultaEmp conE=new ConsultaEmp();                
                try{
                    if(conE.buscarEmpresa(emp)!=null){                        
                        jfC.txtNombreEmp.setText(emp.getNombre());                        
                    }else{JOptionPane.showMessageDialog(null,"No Existe Empresa..");
                    jfC.txtRucEmpresa.grabFocus();
                    }
                }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar Vendedor/Empresa");
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ingrese un valor valido");
                jfC.txtRucEmpresa.grabFocus();
            }            
        }
        //Buscar Proveedor/Vendedor
        if(e.getSource()==jfC.jbtBuscarP){
            if(jfC.txtCiVendedor.getText().length()>0){
                Proveedor p = new Proveedor();
                ConsultaProveedor conP=new ConsultaProveedor();
                p.setCi((jfC.txtCiVendedor.getText()));                
                try{
                    if(conP.buscar(p)!=false){                        
                        jfC.txtNombre.setText(p.getNombre()+" "+p.getApellido());                        
                        jfC.txtNrofactura.grabFocus();                                                                        
                    }else{JOptionPane.showMessageDialog(null,"No Existe Vendedor..");
                    jfC.txtCiVendedor.grabFocus();
                    }
                }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar Vendedor");
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Ingrese un valor valido");
                jfC.txtRucEmpresa.grabFocus();
            }            
        }
        //Registrar la compra
        if(e.getSource()==jfC.btnRegistrar){
            if (jfC.jtDetalle.getRowCount()==0 ){
                JOptionPane.showMessageDialog(null,"Favor ingrese articulos de la compra..");
                jfC.txtCodigo.grabFocus();
            }else if(jfC.txtRucEmpresa.getText().length()==0){
                JOptionPane.showMessageDialog(null,"Favor ingrese RUC Empresa..");
                jfC.txtRucEmpresa.grabFocus();
            }else if(jfC.txtNrofactura.getText().length()==0){
                JOptionPane.showMessageDialog(null,"Favor ingrese Nro Factura,..");
                jfC.txtNrofactura.grabFocus();
            }else if(jfC.jlNroCaja.getText().length()==0||jfC.jlNroCaja.getText().length()==0){
                JOptionPane.showMessageDialog(null,"Favor realice apertura de caja..");
            }else{
                //obtener datos del formulario compra
                //1-obtener dato de Vendedor y la Empresa
                com.setRucEmpresa(jfC.txtRucEmpresa.getText());
                com.setIdVendedor(Integer.parseInt(jfC.txtCiVendedor.getText()));                
                //2-Obtener el nro de Factura
                com.setNroCompra(Integer.parseInt(jfC.txtNrofactura.getText()));                
                //3-formatear la fecha para poder guardarlo en una base de datos                                                                
                com.setFecha(fF.formatoFecha(jfC.jdcFechaCompra.getDate()));                
                //fecha vencimiento
                com.setFechaVencimiento(fF.formatoFecha(jfC.jdcFechaVence.getDate()));
                com.setEstado("APROBADO");
                //4-Obtener la hora del Sistema
                  //Objetos tipo hora
                  Tiempo hora=new Tiempo();                    
                  java.sql.Time hhmm=java.sql.Time.valueOf(hora.hora);
                  com.setHora(hhmm);
                //5-verificar si factura es contado o crédito
                if (jfC.jrbContado.isSelected()){com.setTipoPago(1);
                  }else{com.setTipoPago(2);
                }                 
                //6-Dato de Usuario
                com.setIdUsuario(jfC.jlUsuario.getText());
                //7-Datos Nro Caja
                com.setNumCaja(Integer.parseInt(jfC.jlNroCaja.getText()));                
                //Tipo Recibo
                if(jfC.jrbFactura.isSelected()){com.setTipoRecibo(1);
                }else{com.setTipoRecibo(2);
                }
                //8-totalfactura
                String t=jfC.txtTotalG.getText();                
                int T=Integer.parseInt(t);
                com.setTotalC((T));
                //insertar la cabecera de la factura                
                try{
                    if(cC.registrarC(com)){                
                    //regsistrar el detalle de la factura                
                    //obtener los datos de la tabla de detalle de la factura
                    detalle=jfC.jtDetalle.getModel();
                    for(int i=0;i<detalle.getRowCount();i++){                
                        //System.out.println(table.getValueAt(j, x));
                        detF.setIdCompra(Integer.valueOf(jfC.txtNrofactura.getText()));                                                       
                        detF.setIdProducto((String)detalle.getValueAt(i, 0));
                        String a=(String)detalle.getValueAt(i, 2);
                        detF.setPrecio(Double.valueOf(a));
                        a=(String)detalle.getValueAt(i, 4);
                        detF.setCantidad(Double.parseDouble(a));
                       //insertar el detalle de la factura
                        conD.registrarC(detF);
                    }
                    actualizarStock();                
                    JOptionPane.showMessageDialog(null,"Datos Actualizados correctamente!!!");
                    limpiar();
                    }else{
                        JOptionPane.showMessageDialog(null,"No se pudo guardar Compra, verifique los Datos");
                    }
                }catch(SQLException err){
                        JOptionPane.showMessageDialog(null,"No se pudo guardar la Factura"+" "+err);
                }                                
            }                
        }
        //Eliminar lineas/Productos del detalle
        if(e.getSource()==jfC.jbtEliminar){
            if(jfC.jtDetalle.isRowSelected(jfC.jtDetalle.getSelectedRow())){
            DefaultTableModel modelo=(DefaultTableModel) jfC.jtDetalle.getModel();
            modelo.removeRow(jfC.jtDetalle.getSelectedRow());
            jfC.txtTotalG.setText(String.valueOf(calTotal(modelo)));
            }else{JOptionPane.showMessageDialog(null,"No Selecciono ninguna fila..");
            }
        }
    }
     
    @Override
    public void focusGained(FocusEvent e) {
        //
    }

    @Override
    public void focusLost(FocusEvent fL) {
        //Acciones al perder foco campo Codigo formulario Factura
        if(fL.getComponent()==jfC.txtCodigo){
            if(jfC.txtCodigo.getText().length()>0){
                pro.setCodigo(jfC.txtCodigo.getText());
                try{
                    if(conPro.buscar(pro)){
                        jfC.txtDescripcion.setText(pro.getDescripcion());
                        jfC.jlbIncremento.setText(String.valueOf(pro.getIncremento()));
                        jfC.jlbPrecio.setText(String.valueOf(pro.getCosto()));
                        jfC.txtPrecio.setText("0");                        
                        jfC.txtPrecio.grabFocus();
                        //JOptionPane.showMessageDialog(null,"");                
                    }else{
                        JOptionPane.showMessageDialog(null,"No Existe Producto..");
                        jfC.txtCodigo.grabFocus();
                        //limpiar();
                    }
                    }catch (SQLException ex) {
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }        
        }
        //Acciones al perder foco campo Cantidad formulario Compra
        if (fL.getComponent()==jfC.txtCantidad){            
            if(jfC.txtCantidad.getText().length()>0){                
                int costo=Integer.valueOf(jfC.txtPrecio.getText());
                jfC.txtTotal.setText(String.valueOf(costo*Integer.valueOf(jfC.txtCantidad.getText())));
                
            }else{
                JOptionPane.showMessageDialog(null,"Favor ingrese la cantidad a vender..");
                jfC.txtCantidad.grabFocus();
            }    
        }
    }
    
    //Calcular total de la Compra     
    public static int calTotal(DefaultTableModel detalle){
        int total=0;
        for(int i=0; i<detalle.getRowCount();i++){
            total+= (int)detalle.getValueAt(i, 5);               
        }
        return total;
    }
    //proceso limpiar
    public void limpiar(){
        jfC.txtRucEmpresa.setText("1");
        jfC.txtNrofactura.setText("");
        jfC.txtNombre.setText("");
        jfC.txtCiVendedor.setText("");
        jfC.txtNombreEmp.setText("");
        jfC.jlbPrecio.setText("");
        jfC.jlbIncremento.setText(null);        
        jfC.jrbContado.setSelected(true);
        jfC.jrbFactura.setSelected(true);
        jfC.txtTotal.setText("");
        jfC.txtTotalG.setText("");
       //limpiar las filas de la tabla detalle
        detalle=jfC.jtDetalle.getModel();
         DefaultTableModel modelo=(DefaultTableModel) jfC.jtDetalle.getModel();
        for(int i=((detalle.getRowCount())-1);i>-1;i--){                   
            modelo.removeRow(i);
        }    
    
    }
    //metodo que actualiza stock de producto
    void actualizarStock() throws SQLException{
        for(int i=0;i<detalle.getRowCount();i++){            
            try{
                pro.setCodigo(((String)detalle.getValueAt(i, 0)));
                pro.setIncremento(Integer.valueOf((String)detalle.getValueAt(i, 3)));
                int c=Integer.valueOf((String)detalle.getValueAt(i, 2));
                pro.setCosto(c);
                int iP=((pro.getIncremento()*c)/100);
                pro.setPrecio((iP+Integer.valueOf((String)detalle.getValueAt(i, 2))));
                //actualizar precio
                conPro.ActualizarPrecio(pro);
                int cant=Integer.parseInt((String)detalle.getValueAt(i,4));
                conPro.buscar(pro);
                int sA=pro.getExistencia()+cant;
                conPro.ActualizarStock(pro,sA);
                
            }catch(SQLException e){
                JOptionPane.showMessageDialog(null,"Error Al actualizar stock: "+e);
            }
        }
    }
}
