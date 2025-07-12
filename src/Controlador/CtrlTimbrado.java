/*
 *
 */
package Controlador;

import Modelo.ConCiudad;
import Modelo.ConsultaTimbrado;
import Modelo.Timbrado;
import Vista.jfTimbrado;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/** 
 * @author JFAA
 */
public class CtrlTimbrado implements ActionListener{
    private Timbrado ti;
    private ConsultaTimbrado conTim;
    private jfTimbrado jfTim;
    
    ConCiudad conCiu=new ConCiudad();
    formatoFecha fF=new formatoFecha();
    
    //Constructor
    public CtrlTimbrado(Timbrado ti, ConsultaTimbrado conTim, jfTimbrado jfTim){
        this.ti=ti;
        this.conTim=conTim;
        this.jfTim=jfTim;
        
        //Habilitar botones
        this.jfTim.jbtnGuardar.addActionListener(this); 
        this.jfTim.jbtnBuscar.addActionListener(this);
        this.jfTim.jbtnModificar.addActionListener(this);
    }
    //Iniciar elementos del jfTimbrado
    public void iniciar() throws SQLException{
        jfTim.jtxtNumeroTimbrado.grabFocus();
        Calendar gc=new GregorianCalendar();
        this.jfTim.jdcFechaInicio.setCalendar(gc);
        this.jfTim.jdcFechaVence.setCalendar(gc);
    }
    
    @Override
    public void actionPerformed(ActionEvent e){
        //reconocer los botones pulsados
        if (e.getSource()==jfTim.jbtnGuardar){
            ti.setNumero_timbrado(Integer.parseInt(jfTim.jtxtNumeroTimbrado.getText()));
            try {
                if(!conTim.buscarTim(ti)){                    
                    ti.setFecha_desde(fF.formatoFecha(jfTim.jdcFechaInicio.getDate()));
                    ti.setFecha_Hasta(fF.formatoFecha(jfTim.jdcFechaVence.getDate()));  
                    ti.setObservación(jfTim.jtxtaObservacion.getText());
                    ti.setEstado(jfTim.jcbEstado.getSelectedItem().toString());
                    ti.setNroFacturaInicial(Integer.valueOf(jfTim.jtxtFacturaInicial.getText()));
                    ti.setNroFacturaFinal(Integer.valueOf(jfTim.jtxtFacturaFinal.getText()));
                    //System.out.println(ti.getEstado());
                    try {
                        if(conTim.registrar(ti)){
                            JOptionPane.showMessageDialog(null,"Registro Guardado");
                            iniciar();
                        }else{
                            JOptionPane.showMessageDialog(null,"Error al Guardar");
                            //limpiar();
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Error al Guardar, Posible timbrado Existente");
                }//fin prueba timbrado repetida
            } catch (SQLException ex) {
                Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        //Modificar
        if(e.getSource()==jfTim.jbtnModificar){
            if(validar()==1){
                ti.setId(Integer.parseInt(jfTim.jtxtId.getText()));
                ti.setNumero_timbrado(Integer.parseInt(jfTim.jtxtNumeroTimbrado.getText()));
                ti.setFecha_desde(fF.formatoFecha(jfTim.jdcFechaInicio.getDate()));
                ti.setFecha_Hasta(fF.formatoFecha(jfTim.jdcFechaVence.getDate())); 
                ti.setNroFacturaInicial(Integer.valueOf(jfTim.jtxtFacturaInicial.getText()));
                ti.setNroFacturaFinal(Integer.valueOf(jfTim.jtxtFacturaFinal.getText()));
                ti.setObservación(jfTim.jtxtaObservacion.getText());
                ti.setEstado(jfTim.jcbEstado.getSelectedItem().toString());
                try {
                    if(conTim.modificar(ti)){
                        JOptionPane.showMessageDialog(null,"Registro Modificado");
                       iniciar();
                    }else{
                        JOptionPane.showMessageDialog(null,"Error al Modificar");
                        //limpiar();
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Faltan unos datos...!!!");
                jfTim.jtxtNumeroTimbrado.grabFocus();
            }
        }
        //boton Buscar
        if (e.getSource()==jfTim.jbtnBuscar){            
            ti.setNumero_timbrado(Integer.parseInt(jfTim.jtxtNumeroTimbrado.getText()));
            try {
                if(conTim.buscarTimbrado(ti)!= null){
                    //System.out.println("Buscar");
                    jfTim.jtxtId.setText(String.valueOf(ti.getId()));
                    jfTim.jdcFechaInicio.setDate(ti.getFecha_desde());
                    jfTim.jdcFechaVence.setDate(ti.getFecha_Hasta());
                    jfTim.jtxtaObservacion.setText(ti.getObservación());
                    jfTim.jtxtFacturaInicial.setText(String.valueOf(ti.getNroFacturaInicial()));
                    jfTim.jtxtFacturaFinal.setText(String.valueOf(ti.getNroFacturaFinal()));
                    if("ACTIVO".equals(ti.getEstado())){
                       jfTim.jcbEstado.setSelectedIndex(0);
                    }else if("CERRADO".equals(ti.getEstado())){
                       jfTim.jcbEstado.setSelectedIndex(1); 
                    }else{
                        jfTim.jcbEstado.setSelectedIndex(2);
                    }                    
                    //jfTim.jcbCiudad.selectWithKeyChar((ti.getIdciudad()-1));                    
                                        
                }else{
                    JOptionPane.showMessageDialog(null,"No se encontro el registro!!");                   
                }
            } catch (SQLException ex) {                
               JOptionPane.showMessageDialog(null,"El error :"+ex);
            }
        }
    }     
   
    public int validar(){
        if (jfTim.jtxtNumeroTimbrado.getText().length()!=0 ){
            return 1;
        }else{
            return 0;
        }
    }
}
