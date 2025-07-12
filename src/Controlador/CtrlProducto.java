package Controlador;

import Modelo.ConsultaCategoria;
import Modelo.ConsultaMarca;
import Modelo.ConsultaProducto;
import Modelo.Producto;
import Vista.jfProducto;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.lang.NumberFormatException;

/**
 *
 * @author JFAA
 */
public class CtrlProducto implements ActionListener{
    private Producto pro;
    private ConsultaProducto conPro;
    private jfProducto jfPro;
    private ConsultaCategoria conCat=new ConsultaCategoria();
    private ConsultaMarca conM=new ConsultaMarca();
    
    public CtrlProducto(Producto pro, ConsultaProducto conPro, jfProducto jfPro){
        this.pro=pro;
        this.conPro=conPro;
        this.jfPro=jfPro;        
        this.jfPro.btnGuardar.addActionListener (this);
        this.jfPro.btnBuscar.addActionListener(this);
        this.jfPro.jbtEliminar.addActionListener(this);        
        /*this.jfPro.btnLimpiar.addActionListener(this);*/
        this.jfPro.jbtModificar.addActionListener(this);
        //implementar eventos de perdida y ganancia de foco
        //a travez de una clase que implementa ambos eventos
        jfPro.txtIncremento.addFocusListener(new escucha());
        jfPro.txtPrecioSugerido.addFocusListener(new escucha());
        jfPro.txtIva.addFocusListener(new escucha());
        jfPro.txtPrecio.addFocusListener(new escucha());
        jfPro.txtCosto.addFocusListener(new escucha());
    }
    
    public void iniciar () throws SQLException{
        jfPro.setTitle("Productos");
        jfPro.setLocationRelativeTo(null);
        //jfPro.txtCodigo.setVisible(false);
        jfPro.txtCodigo.requestFocus();
        llenarCombo();
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        //reconocer los botones pulsados
        if (e.getSource()==jfPro.btnGuardar){
            pro.setCodigo(jfPro.txtCodigo.getText());
            try {
            if(false==conPro.buscar2(pro)){
            //pro.setCodigo(jfPro.txtCodigo.getText());
            pro.setDescripcion(jfPro.txtDescripcion.getText());
            pro.setStock_minimo(Integer.valueOf(jfPro.jtxtExistenciaMinima.getText()));
            pro.setIdcategoria((jfPro.jcbCategoria.getSelectedIndex()+1)); 
            pro.setMarca(jfPro.jcbMarcas.getSelectedIndex()+1);            
            pro.setExistencia(Integer.parseInt(jfPro.txtExistencia.getText()));
            pro.setCosto(Integer.valueOf(jfPro.txtCosto.getText()));            
            pro.setIncremento(Integer.valueOf(jfPro.txtIncremento.getText()));
            pro.setIva(Integer.valueOf(jfPro.txtIva.getText()));
            pro.setPrecio(Integer.valueOf(jfPro.txtPrecio.getText())); 
            pro.setDepoestante(Integer.valueOf(jfPro.txtDepEstante.getText()));
            pro.setBandeja(Integer.valueOf(jfPro.jtxtBandeja.getText()));
            pro.setEstante(Integer.valueOf(jfPro.jtxtEstante.getText()));
            pro.setPasillo(Integer.valueOf(jfPro.jtxtPasillo.getText()));
                try {
                    if(conPro.registrar(pro)){
                        JOptionPane.showMessageDialog(null,"Registro Guardado");
                        limpiar();
                    }else{
                        JOptionPane.showMessageDialog(null,"Error al Guardar");
                        //limpiar();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al Guardar..");
                }
            }else{JOptionPane.showMessageDialog(null,"Error al Guardar, código existente");
            }//fin prueba codigo repetido
            } catch (SQLException ex ) {
                JOptionPane.showMessageDialog(null,"Error al guardar.."+ex);
            }catch(NumberFormatException n){
                JOptionPane.showMessageDialog(null,"Error, Verifique los valores numéricos.."+n);
                jfPro.txtExistencia.grabFocus();
            }          
        }
        //boton Modificar*******************************************************
        if (e.getSource()==jfPro.jbtModificar){
            try{
                pro.setCodigo(jfPro.txtCodigo.getText());
                pro.setDescripcion(jfPro.txtDescripcion.getText());
                pro.setIdcategoria((jfPro.jcbCategoria.getSelectedIndex()+1));
                pro.setMarca(jfPro.jcbMarcas.getSelectedIndex()+1);
                pro.setExistencia(Integer.parseInt(jfPro.txtExistencia.getText()));
                pro.setStock_minimo(Integer.valueOf(jfPro.jtxtExistenciaMinima.getText()));
                pro.setCosto(Integer.valueOf(jfPro.txtCosto.getText()));            
                pro.setIncremento(Integer.valueOf(jfPro.txtIncremento.getText()));
                pro.setPrecio(Integer.valueOf(jfPro.txtPrecio.getText()));
                pro.setDepoestante(Integer.valueOf(jfPro.txtDepEstante.getText()));
                pro.setBandeja(Integer.valueOf(jfPro.jtxtBandeja.getText()));
                pro.setEstante(Integer.valueOf(jfPro.jtxtEstante.getText()));
                pro.setPasillo(Integer.valueOf(jfPro.jtxtPasillo.getText()));
                try {
                    if(conPro.modificar(pro)){
                        JOptionPane.showMessageDialog(null,"Registro Modificado");
                        limpiar();
                    }else{
                        JOptionPane.showMessageDialog(null,"Error al Modificar");
                        limpiar();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error la modificar.."+ ex);
                }
            }catch(NumberFormatException n){
                JOptionPane.showMessageDialog(null,"Error, Verifique los valores numéricos.."+n);
            }
        }
        //boton Buscar**********************************************************
        if (e.getSource()==jfPro.btnBuscar){
            //int a=Integer.parseInt(frm.txtCodigo.getText());
            if(!"".equals(jfPro.txtCodigo.getText())){
                pro.setCodigo(jfPro.txtCodigo.getText());
                try {                
                    if(conPro.buscar(pro)){
                        //System.out.println("Buscar");
                        //jfPro.txtIdProducto.setText(String.valueOf(pro.getId()));
                        jfPro.txtCodigo.setText(pro.getCodigo());
                        jfPro.txtDescripcion.setText(pro.getDescripcion());
                        jfPro.jcbCategoria.setSelectedIndex(pro.getIdcategoria()-1);
                        jfPro.jcbMarcas.setSelectedIndex(pro.getMarca()-1);
                        jfPro.txtCosto.setText(String.valueOf(pro.getCosto()));
                        jfPro.txtIncremento.setText(String.valueOf(pro.getIncremento()));
                        jfPro.txtPrecio.setText(String.valueOf(pro.getPrecio()));
                        jfPro.txtExistencia.setText(String.valueOf(pro.getExistencia()));
                        jfPro.jtxtExistenciaMinima.setText(String.valueOf(pro.getStock_minimo()));
                        jfPro.txtDepEstante.setText(String.valueOf(pro.getDepoestante()));
                        jfPro.jtxtBandeja.setText(String.valueOf(pro.getBandeja()));
                        jfPro.jtxtEstante.setText(String.valueOf(pro.getEstante()));
                        jfPro.jtxtPasillo.setText(String.valueOf(pro.getPasillo()));
                    }else{
                    JOptionPane.showMessageDialog(null,"No se encontro el registro!!");
                    jfPro.txtDescripcion.grabFocus();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("El error :"+ex);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Favor ingrese codigo");
                jfPro.txtCodigo.grabFocus();
            }
        }
        if (e.getSource()==jfPro.jbtEliminar){            
            if(!"".equals(jfPro.txtCodigo.getText())){
                pro.setCodigo(jfPro.txtCodigo.getText());
                try {      
                   if(conPro.eliminar(pro)){
                       limpiar();
                       JOptionPane.showMessageDialog(null, "El registro ha sido eliminado con exito...");
                   }
                } catch (SQLException ex) {                    
                    JOptionPane.showMessageDialog(null, "Error al intentar eliminar registro... "+ex);
                }
            }else{
                JOptionPane.showMessageDialog(null, "Favor ingrese codigo");
                jfPro.txtCodigo.grabFocus();
            }
        }
    }
    
    //Atrapar los eventos de perdida o ganancia de foco
    private class escucha implements FocusListener{
        @Override
        public void focusGained(FocusEvent e) {
            if(e.getComponent()==jfPro.txtPrecioSugerido){
                jfPro.txtPrecioSugerido.setBackground(Color.YELLOW);                                
            }       
            if(e.getComponent()==jfPro.txtPrecio){
                jfPro.txtPrecio.setText("");
            }
            if(e.getComponent()==jfPro.txtCosto){
                jfPro.txtCosto.setText("");
            }
        }
        @Override
        public void focusLost(FocusEvent e) {
            if(e.getComponent()==jfPro.txtCosto){
                if(jfPro.txtCosto.getText().length()==0){
                    JOptionPane.showMessageDialog(null, "Valor inapropiado en Costo...");
                    jfPro.txtCosto.grabFocus();
                }
            }
            if(e.getComponent()==jfPro.txtIncremento){                
                if(jfPro.txtCosto.getText().length()!=0){
                    int p=(Integer.valueOf(jfPro.txtCosto.getText()))+
                    (Integer.valueOf(jfPro.txtCosto.getText())*
                     Integer.valueOf(jfPro.txtIncremento.getText()))/100;
                    jfPro.txtPrecioSugerido.setText(String.valueOf(p));
                    jfPro.txtPrecio.setText(String.valueOf(p));
                }
            }
            if(e.getComponent()==jfPro.txtIva){
              if(!"".equals(jfPro.txtIncremento.getText())&&!"".equals(jfPro.txtCosto.getText())){
                int p=0,c=0,iva=0,i=0;
                c=Integer.valueOf(jfPro.txtCosto.getText());
                iva=Integer.valueOf(jfPro.txtIva.getText());
                i=Integer.valueOf(jfPro.txtIncremento.getText());
                p=c+(c*i/100);
                p=p+(p*iva/100);
                jfPro.txtPrecioSugerido.setText(String.valueOf(p));
              }
            }            
        }       
    }
    
    //Metodo llenar combo box con Categorias de productos
    public void llenarCombo() throws SQLException{
        ArrayList<String> Lista=new ArrayList<>();
        Lista=conCat.llenarCombo();
        for(int i=0;i<Lista.size();i++){
            jfPro.jcbCategoria.addItem(Lista.get(i));
        }
        Lista=conM.llenarCombo();
        for(int i=0;i<Lista.size();i++){
            jfPro.jcbMarcas.addItem(Lista.get(i));
        }
    }
    public void limpiar() throws SQLException{
        //jfPro.txtId.setText(null);
        jfPro.txtCodigo.setText(null);
        jfPro.txtDescripcion.setText(null);
        //llenarCombo();
        jfPro.txtExistencia.setText(null);
        jfPro.txtCosto.setText(null);
        jfPro.txtIncremento.setText("30");
        jfPro.txtPrecioSugerido.setText(null);
        jfPro.txtPrecioSugerido.setBackground(Color.lightGray);
        jfPro.txtPrecio.setText(null);
        jfPro.txtDepEstante.setText("1");
        jfPro.jtxtBandeja.setText("1");
        jfPro.jtxtEstante.setText("1");
        jfPro.jtxtPasillo.setText("1");
        jfPro.txtCodigo.requestFocus();
        
    }
}