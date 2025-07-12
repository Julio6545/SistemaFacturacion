
package Controlador;
import Modelo.ConLote;
import Modelo.ConsultaProducto;
import Modelo.Lote;
import Modelo.Producto;
import Vista.jfLote;
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

/**
 *
 * @author JFAA
 */
public class CtrlLote implements ActionListener{
    private Lote l;
    private ConLote conL;
    private jfLote jfL;
    
    Producto pro=new Producto();
    ConsultaProducto conP=new ConsultaProducto();
    List<Lote> listaDetalle;
    DefaultTableModel dtmDetalle=new DefaultTableModel();
    int ban=0;
    public CtrlLote(Lote l, ConLote conL,jfLote jfL){
        this.l=l;
        this.conL=conL;
        this.jfL=jfL;
        
        this.jfL.jbtRegistrar.addActionListener (this);
        this.jfL.jbtBuscar.addActionListener(this);
        this.jfL.jbtEliminar.addActionListener(this);                
        this.jfL.jbtModificar.addActionListener(this);
        this.jfL.jbtActualizar.addActionListener(this);
        //implementar eventos de perdida y ganancia de foco
        //a travez de una clase que implementa ambos eventos
        jfL.jtxtCodProducto.addFocusListener(new escucha());        
    }
    
    public void iniciar (){
        //jfL.setTitle("Lotes");
        Calendar gc=new GregorianCalendar();
        this.jfL.jdcFechaVence.setCalendar(gc);
        jfL.setLocationRelativeTo(null);        
        jfL.jtxtLote.requestFocus();
        mostrarLotes();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        //reconocer los botones pulsados
        if (e.getSource()==jfL.jbtRegistrar){
            if(validar()==1){
            l.setNrolote((jfL.jtxtLote.getText()));
            try {
            if(false==conL.buscar(l)){
                l.setIdproducto(jfL.jtxtCodProducto.getText());
                try{
                    l.setCantidad(Integer.valueOf(jfL.jtxtCantidad.getText()));            
                    l.setFechaVence(formatoF(jfL.jdcFechaVence.getDate()));                                       
                    try {
                        if(conL.registrar(l)){
                        JOptionPane.showMessageDialog(null,"Registro Guardado");
                        limpiar();
                        }else{
                        JOptionPane.showMessageDialog(null,"Error al Guardar");                    
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(CtrlLote.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }catch(NumberFormatException er){
                    JOptionPane.showMessageDialog(null, "Ingresar valor numérico..");
                    jfL.jtxtCantidad.grabFocus();
                }
            }else{JOptionPane.showMessageDialog(null,"Error al Guardar, código existente");
            }//fin prueba codigo repetido
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al Guardar");
            }
            }else{
                JOptionPane.showMessageDialog(null, "Ingrese Valores apropiados");
                jfL.jtxtLote.grabFocus();
            }
        }
        //boton Modificar
        if (e.getSource()==jfL.jbtModificar){
            if(validar()==1){
                l.setNrolote(jfL.jtxtLote.getText());
                l.setIdproducto(jfL.jtxtCodProducto.getText());
                l.setFechaVence(formatoF(jfL.jdcFechaVence.getDate()));
                try{
                    l.setCantidad(Integer.valueOf(jfL.jtxtCantidad.getText()));
                    try {
                    if(conL.modificar(l)){
                        JOptionPane.showMessageDialog(null,"Registro Modificado");
                        limpiar();
                    }else{
                        JOptionPane.showMessageDialog(null,"Error al Modificar");
                        limpiar();
                    }
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null,"Error al Modificar");
                    }
                }catch(NumberFormatException er){
                    JOptionPane.showMessageDialog(null, "Ingresar valor numérico..");
                    jfL.jtxtCantidad.grabFocus();
                }    
            }else{
                JOptionPane.showMessageDialog(null,"Ingrese valores apropiados..");
                jfL.jtxtLote.grabFocus();
            }           
        }
        //boton Buscar
        if (e.getSource()==jfL.jbtBuscar){
            //int a=Integer.parseInt(frm.txtCodigo.getText());
            if(!"".equals(jfL.jtxtLote.getText())){
            l.setNrolote((jfL.jtxtLote.getText()));
            try {                
                if(conL.buscar(l)){
                    //System.out.println("Buscar");
                    jfL.jtxtLote.setText(String.valueOf(l.getNrolote()));
                    jfL.jtxtCodProducto.setText(l.getIdproducto());
                    pro.setCodigo(l.getIdproducto());
                    try {                
                        if(conP.buscar(pro)){                    
                            jfL.jlbDescripcion.setText(pro.getDescripcion());                   
                        }else{
                            JOptionPane.showMessageDialog(null,"No se encontro Producto!!");                  
                        }
                    } catch (SQLException ex) {  
                        System.out.println("El error :"+ex);
                    }                    
                    jfL.jtxtCantidad.setText(String.valueOf(l.getCantidad()));
                    jfL.jdcFechaVence.setDate(l.getFechaVence());                  
                }else{
                    JOptionPane.showMessageDialog(null,"No se encontro el registro!!");                  
                }
            } catch (SQLException ex) {
                Logger.getLogger(CtrlLote.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("El error :"+ex);
            }
            }else{
                JOptionPane.showMessageDialog(null, "Favor ingrese número lote");
                jfL.jtxtLote.grabFocus();
            }
        }
        //Boton Actualizar, refrescar la lista de lotes
        if(e.getSource()==jfL.jbtActualizar){
            mostrarLotes();
        }
    }
    
    //Atrapar los eventos de perdida o ganancia de foco
    private class escucha implements FocusListener{
        @Override
        public void focusGained(FocusEvent e) {
            //if(e.getComponent()==jfL.jtxtCodProducto){}            
        }
        @Override
        public void focusLost(FocusEvent e) {
            if(e.getComponent()==jfL.jtxtCodProducto){
               if(jfL.jtxtCodProducto.getText().length()==0){
               } else {
                   pro.setCodigo(jfL.jtxtCodProducto.getText());
                   try {
                       if(conP.buscar(pro)){
                           jfL.jlbDescripcion.setText(pro.getDescripcion());
                       }else{                  
                           JOptionPane.showMessageDialog(null,"No se encontro el registro!!");
                       }
                   } catch (SQLException ex) {
                       Logger.getLogger(CtrlLote.class.getName()).log(Level.SEVERE, null, ex);
                       System.out.println("El error :"+ex);
                   }
                }
            }else{
                JOptionPane.showMessageDialog(null, "Favor ingrese codigo");
                jfL.jtxtLote.grabFocus();
            }
                      
        }       
    }
    //Metodo Mostrar lista de lotes********************************************* 
    public void mostrarLotes(){          
        if(jfL.jtLotes.getRowCount()!=0){
                limpiarJTable();
            } 
        try{
            listaDetalle=conL.obtenerLotes();
            dtmDetalle=(DefaultTableModel)jfL.jtLotes.getModel();
            if(!listaDetalle.isEmpty()){
                Object[] object = new Object[5];               
                for(int i=0; i<listaDetalle.size();i++){
                    object[0]=listaDetalle.get(i).getNrolote();
                    object[1]=listaDetalle.get(i).getIdproducto();
                    object[2]=listaDetalle.get(i).getDescripcion();
                    object[3]=listaDetalle.get(i).getCantidad();
                    object[4]=listaDetalle.get(i).getFechaVence();                    
                    dtmDetalle.addRow(object);                    
                }
                //hacer visible los datos en el JTable
                jfL.jtLotes.setModel(dtmDetalle);
                //Establecer foco en Nro lote            
                jfL.jtxtLote.grabFocus();
        
            }else{
                //jfL.jlbMensaje.setText("No existe registros de ventas para la fecha");
                JOptionPane.showMessageDialog(null, "No existe registros de Lotes","",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar Lote");
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    //Formatear fecha***********************************************************
    public Date formatoF(java.util.Date f){
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");                    
        String fecha2;                    
        fecha2 = (formatofecha.format(f));                
        java.sql.Date fecha=java.sql.Date.valueOf(fecha2);
        return fecha;
    }
    public void limpiar(){
        jfL.jtxtLote.setText(null);
        jfL.jtxtCodProducto.setText(null);
        jfL.jtxtCantidad.setText(null);
        jfL.jtxtLote.requestFocus();
        jfL.jlbDescripcion.setText("");
    }
    //Inicializar la tabla
    public void limpiarJTable (){
        while(jfL.jtLotes.getRowCount()>0) {
            ((DefaultTableModel)jfL.jtLotes.getModel()).removeRow(0);
        }
    }
    //Metodo validar ingreso de datos
    public int validar(){        
        ban=0;
        if(jfL.jtxtLote.getText().length()!=0 && jfL.jtxtCodProducto.getText().length()!=0
                    && jfL.jtxtCantidad.getText().length()!=0){
            ban=1;        
        }
        return ban;
    }
}
