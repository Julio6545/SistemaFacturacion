package Controlador;

import Modelo.ConsultaAcceso;
import Modelo.Usuario;
import Vista.jfIngreso;
import Vista.jfMarket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
//hola

/**
 *
 * @author JFAA
 */
public class ControlAcceso implements ActionListener{
    private Usuario usu;
    private ConsultaAcceso consulta;
    private jfIngreso jfing;
    
    public jfMarket p= new jfMarket();
    Tiempo t=new Tiempo();   
    
    public ControlAcceso(Usuario usu, ConsultaAcceso consulta, jfIngreso jfing) {
        this.usu = usu;
        this.consulta = consulta;
        this.jfing = jfing;        
        this.jfing.btnAcceder.addActionListener(this);
    }
    
    public void iniciar (){
        Calendar gc=new GregorianCalendar();
        this.jfing.jdcFecha.setCalendar(gc);
        this.jfing.jlbHora.setText(t.hora);
        jfing.setLocationRelativeTo(null);
        jfing.jfpass.setVisible(true);
        jfing.txtUsuario.requestFocus();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent ae) {                
        //reconocer los botones pulsados
        if (ae.getSource()==jfing.btnAcceder){            
            usu.setNombreusu(jfing.txtUsuario.getText());
            usu.setClave(jfing.jfpass.getText());
            //realizar la consulta
            try{
            if(consulta.buscar(usu)!=null){                
                JOptionPane.showMessageDialog(null,"Bienvenido "+usu.getNombre()+" !!");
                p.Nombre=usu.getNombre()+" "+usu.getApellido();
                p.jlbUsuario.setText((usu.getCi()));
                p.jlbNombreUsu.setText(p.Nombre);
                jfing.dispose();
                if(usu.getRol()!=1){                  
                  p.jmUsuario.setEnabled(false);
                  p.jmiNotaCredito.setEnabled(false);
                  //p.jmAbmProductos.setEnabled(false);
                  //p.jmMovimientoPorInventario.setEnabled(false);
                }
                if(usu.getRol()==2){
                  //p.jmVentas.setEnabled(false);
                  // p.jmCompras.setEnabled(false);                  
                  // p.jmiCierre.setEnabled(false);
                }
                p.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Error al autenticar. Verifique Usuario y Contaseña..");
                jfing.txtUsuario.grabFocus();
                //limpiar();
            }
            }catch (SQLException ex) {
                JOptionPane.showMessageDialog(null,"Error al buscar en la base de datos..");
                Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }catch(NullPointerException ne){
                JOptionPane.showMessageDialog(null,"Falló la comunicación con BD, posible motor de BD apagado...");
            }
        }   
    }
        
}
