package Controlador;

import Modelo.ConSalida;
import Modelo.ConsultaCliente;
import Modelo.ConsultaDetalleTick;
import Modelo.ConsultaProducto;
import Modelo.DetalleV;
import Modelo.Persona;
import Modelo.Producto;
import Modelo.Salida;
import Vista.jfSalida;
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
public class CtrlSalida implements ActionListener, FocusListener{
    private Salida S;
    private ConSalida conS;
    private jfSalida jfS;
    
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
    public CtrlSalida(Salida S,ConSalida conS,jfSalida jfS){
        this.S=S;
        this.conS=conS;
        this.jfS=jfS;               
        this.jfS.btnAgregar.addActionListener(this);
        this.jfS.btnRegistrar.addActionListener(this);
        this.jfS.btnBuscar.addActionListener(this);
        this.jfS.jbtEliminar.addActionListener(this);        
        this.jfS.txtCodigo.addFocusListener(this);
        this.jfS.txtCantidad.addFocusListener(this);
    }
    
    //inicializar los valores en el formulario
    public void iniciar() throws SQLException{
        Calendar gc=new GregorianCalendar();
        this.jfS.jdcFechaSalida.setCalendar(gc);
        //obtener el siguiente numero de factura
        this.jfS.txtNroSalida.setText(conS.buscarUltimaSal());
        jfS.txtNroEntidad.setText("123");        
        jfS.txtCantidad.setText("1");
        jfS.jrbInventario.setSelected(true);        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //controlar los eventos de los botones y otros componentes del formulario
        //Agregar líneas/Productos al detelle
        if(e.getSource()==jfS.btnAgregar){
            if(jfS.txtCodigo.getText().length()>0 && jfS.txtCantidad.getText().length()>0 
                    && jfS.txtDescripcion.getText().length()>0 ){
            DefaultTableModel modelo=(DefaultTableModel) jfS.jtDetalle.getModel();
            int cant=Integer.valueOf(jfS.txtCantidad.getText());
            p= Integer.valueOf(jfS.txtPrecio.getText());
            modelo.addRow(new Object[]{jfS.txtCodigo.getText(),
                jfS.txtDescripcion.getText(),jfS.txtPrecio.getText(),jfS.txtCantidad.getText(),
                p*cant    
            } );
            //limpiar los campos de ingreso
            jfS.txtCodigo.grabFocus();
            jfS.txtCodigo.setText("");
            jfS.txtDescripcion.setText("");
            jfS.txtPrecio.setText("");
            jfS.txtCantidad.setText("1");
            jfS.txtTotal.setText("");
            jfS.txtTotalS.setText(String.valueOf(calTotal(modelo)));
            }else{
                JOptionPane.showMessageDialog(null,"Codigo y cantidad deben contener valores apropiados..");
                jfS.txtCodigo.grabFocus();
                jfS.txtCantidad.setText("1");
            }
        }        
        //Boton Buscar
        if(e.getSource()==jfS.btnBuscar){
            if(jfS.txtNroEntidad.getText().length()>0){
                cli.setCi((jfS.txtNroEntidad.getText()));            
                try{
                    if(conCli.buscar(cli)){
                        jfS.txtNombre.setText(cli.getNombre()+" "+cli.getApellido());                
                        jfS.txtRucEntidad.setText(cli.getRuc());
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
                jfS.txtNroEntidad.grabFocus();
            }
        }
        //Registrar la venta
        if(e.getSource()==jfS.btnRegistrar){
            if (jfS.jtDetalle.getRowCount()==0){
                JOptionPane.showMessageDialog(null,"Favor ingrese datos de la venta");
            }else{
                //obtener datos del formulario factura
                //1-obtener dato de Cliente                
                S.setIdCliente(Integer.parseInt(jfS.txtNroEntidad.getText()));                
                //2-Obtener el nro de Factura
                S.setNroSalida(Integer.parseInt(jfS.txtNroSalida.getText()));                
                //3-formatear la fecha para poder guardarlo en una base de datos                  
                  SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");                    
                  String fecha2;                    
                  fecha2 = (formatofecha.format(jfS.jdcFechaSalida.getDate()));                
                  java.sql.Date fecha=java.sql.Date.valueOf(fecha2);
                  S.setFecha(fecha);
                //4-Obtener la hora del Sistema
                  //Objetos tipo hora
                  Tiempo hora=new Tiempo();                    
                  java.sql.Time hhmm=java.sql.Time.valueOf(hora.hora);
                  S.setHora(hhmm);
                //5-verificar si factura es contado o crédito
                if (jfS.jrbInventario.isSelected()){S.setTipoSalida(1);}else{S.setTipoSalida(2);}   
                //6-Dato de Usuario
                S.setIdUsuario(jfS.jlUsuario.getText());                                
                //7-total Salida
                String t=jfS.txtTotalS.getText();                
                int T=Integer.parseInt(t);
                S.setTotalS((T));
                //insertar la cabecera de la factura                
                try{
                    if (conS.registrarS(S)){                
                    //regsistrar el detalle de la factura                
                    //obtener los datos de la tabla de detalle de la factura
                    detalle=jfS.jtDetalle.getModel();
                    for(int i=0;i<detalle.getRowCount();i++){                
                        //System.out.println(table.getValueAt(j, x));
                        detF.setIdVenta(Integer.valueOf(jfS.txtNroSalida.getText()));                                                       
                        detF.setIdProducto((String)detalle.getValueAt(i, 0));
                        String a=(String)detalle.getValueAt(i, 2);
                        detF.setPrecio(Integer.valueOf(a));
                        a=(String)detalle.getValueAt(i, 3);
                        detF.setCantidad(Integer.valueOf(a));                  
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
        if(e.getSource()==jfS.jbtEliminar){
            if(jfS.jtDetalle.isRowSelected(jfS.jtDetalle.getSelectedRow())){
            DefaultTableModel modelo=(DefaultTableModel) jfS.jtDetalle.getModel();
            modelo.removeRow(jfS.jtDetalle.getSelectedRow());
            jfS.txtTotalS.setText(String.valueOf(calTotal(modelo)));
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
        if(fL.getComponent()==jfS.txtCodigo){
            if(jfS.txtCodigo.getText().length()>0){
                pro.setCodigo(jfS.txtCodigo.getText());
                try{
                    if(conPro.buscar(pro)){
                        jfS.txtDescripcion.setText(pro.getDescripcion());
                        jfS.txtPrecio.setText(String.valueOf(pro.getCosto()));                        
                        jfS.txtCantidad.grabFocus();                        
                    }else{
                        JOptionPane.showMessageDialog(null,"No Existe Producto..");
                        jfS.txtCodigo.grabFocus();                        
                    }
                }catch (SQLException ex) {
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        //Acciones al perder foco campo Cantidad formulario Factura
        if(fL.getComponent()==jfS.txtCantidad){            
            if(jfS.txtCantidad.getText().length()>0){
                p=pro.getCosto();    
                jfS.txtTotal.setText(String.valueOf(p*Integer.valueOf(jfS.txtCantidad.getText())));                
            }else{
                JOptionPane.showMessageDialog(null,"Favor ingrese la cantidad a vender..");
                jfS.txtCantidad.grabFocus();
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
        jfS.txtNroEntidad.setText("123");
        jfS.txtNombre.setText("");        
        jfS.txtRucEntidad.setText("");
        jfS.txtNroSalida.setText("");
        try {
            //obtener siguiente nro de factura
            jfS.txtNroSalida.setText(conS.buscarUltimaSal());
        } catch (SQLException ex) {
            Logger.getLogger(CtrlSalida.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("No sepudo encontar el ultimo registro de factura!!");
        }
        jfS.jrbInventario.setSelected(true);
        jfS.txtTotal.setText("");
        jfS.txtTotalS.setText("");
       //limpiar las filas de la tabla detalle
        detalle=jfS.jtDetalle.getModel();
         DefaultTableModel modelo=(DefaultTableModel) jfS.jtDetalle.getModel();
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
            int sA=pro.getExistencia()-cant;
            conPro.ActualizarStock(pro,sA);
            
        }
    }
}
