package Controlador;

import Modelo.Caja;
import Modelo.ConsultaAcceso;
import Modelo.ConsultaCaja;
import Modelo.Usuario;
import Vista.jfCaja;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.swing.JOptionPane;
/** 
 * @author JFAA
 */
public class CtrlCaja implements ActionListener{
    //Instanciar Clases, sus atributos y métodos
    Caja c;
    Usuario usu;
    ConsultaCaja conC;
    ConsultaAcceso conA=new ConsultaAcceso();
    jfCaja jfC=new jfCaja();
    
    
    public CtrlCaja(Caja c, ConsultaCaja conC, jfCaja jfC){
        this.c=c;
        this.conC=conC;
        this.jfC=jfC;        
        jfC.btnHabilitar.addActionListener(this);        
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        //Acciones para boton Habilitar
        if(e.getSource()==jfC.btnHabilitar){
           usu.setNombreusu(jfC.txtUsuario.getText());
           usu.setClave(jfC.jpfClave.getText());           
           try{
               if(conA.buscar(usu)!=null){
                   JOptionPane.showMessageDialog(null, "Bienvenido!!!");
                   jfC.dispose();                                      
                   //Obtener datos del formulario jfCaja
                   c.setnomUsuario(usu.getNombreusu());
                   c.setClaveU(usu.getClave());
                   //formatear la fecha para poder guardarlo en una base de datos                  
                    SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");                    
                    String fecha2;                    
                    fecha2 = (formatofecha.format(jfC.jdcFecha.getDate()));                
                    java.sql.Date fecha=java.sql.Date.valueOf(fecha2);
                   c.setFecha(fecha);                                      
                   //Objetos tipo hora
                    Tiempo hora=new Tiempo();
                    String horap=hora.hora;                    
                    java.sql.Time hhmm= java.sql.Time.valueOf(horap);                    
                   c.setHora(hhmm);                   
                   c.setNroCaja(Integer.valueOf(jfC.txtNroCaja.getText()));
                   //System.out.println("Fecha:"+c.getFecha()+", "+c.getHora());
                }else{
                   JOptionPane.showMessageDialog(null,"Error al autenticar. Verifique Usuario y Contaseña..");
                   jfC.txtUsuario.grabFocus();
               }
           }catch(SQLException ex){
               JOptionPane.showMessageDialog(null, ex);
           }
        }        
    }
    //Método para mostrar fecha
    public void mostrarFecha(){
        Calendar gc=new GregorianCalendar();
        Tiempo t=new Tiempo();        
        jfC.jdcFecha.setCalendar(gc);
        jfC.jlbHora.setText(t.hora);        
    }    
}
