
package Controlador;

import Modelo.Arqueo;
import Modelo.ConsultaCliente;
import Modelo.ConsultaProducto;
import Modelo.ConsultaTimbrado;
import Modelo.ConsultaUsuario;
import Modelo.ConsultaV;
import Modelo.DetalleV;
import Modelo.Persona;
import Modelo.Producto;
import Modelo.Timbrado;
import Modelo.Usuario;
import Modelo.Venta;
import Vista.jfImprimirFactura;
import Vista.jfMarket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import java.sql.SQLException;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author JFAA
 */
public class CtrlImpresion implements ActionListener{
    Venta v;
    jfImprimirFactura jfI;
    ConsultaCliente conCli= new ConsultaCliente();
    Persona p=new Persona();
    Timbrado t= new Timbrado();
    ConsultaTimbrado conT=new ConsultaTimbrado();
    ConsultaUsuario conU = new ConsultaUsuario();
    Usuario u = new Usuario();
    List<DetalleV> listaDetalle;
    DefaultTableModel dtmDetalle=new DefaultTableModel();
    ConsultaV conV = new ConsultaV();
    Producto pro = new Producto();
    ConsultaProducto conPro=new ConsultaProducto();
    
    public CtrlImpresion( Venta v, jfImprimirFactura jfI){
        this.jfI=jfI;
        this.v=v;
        this.jfI.jbtImprimir.addActionListener(this);
                
    }
    public void iniciar() throws SQLException{
        jfI.jlbCaja.setText(String.valueOf(v.getNumCaja()));        
        jfI.jlbClienteRuc.setText(String.valueOf(v.getIdCliente()));
        jfI.jlbCaja.setText(String.valueOf(v.getNumCaja()));
        u.setNombreusu(v.getIdVendedor());
        conU.buscarUsu(u);
        jfI.jlbCajero.setText(String.valueOf(u.getNombre()+" "+u.getApellido()));
        jfI.jlbClienteRuc.setText(String.valueOf(v.getIdCliente()));
        jfI.jlbFecha.setText(String.valueOf(v.getFecha()));
        jfI.jlbHora.setText(String.valueOf(v.getHora()));
        jfI.jlbNroFactura.setText(String.valueOf(v.getNroFactura()));
        p.setCi(v.getIdCliente());
        conCli.buscar(p);
        jfI.jlbClienteNombre.setText(p.getNombre()+" "+p.getApellido());
        t.setId(v.getIdTimbrado());
        conT.buscarTimbradoId(t);
        jfI.jlbTimbrado.setText(String.valueOf(t.getNumero_timbrado()));
        jfI.jlbFechaIni.setText(String.valueOf(t.getFecha_desde()));
        jfI.jlbFechaFin.setText(String.valueOf(t.getFecha_Hasta()));
        jfI.jlbSubTotal.setText(String.valueOf(v.getTotalF())+" Gs");
        jfI.jlbTotal.setText(String.valueOf(v.getTotalCobrar())+" Gs");
        jfI.jlbDescuento.setText(String.valueOf(v.getDescuento())+" Gs");
        //Cargar detalle
        cargarDetalle(v);   
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==jfI.jbtImprimir){
            JOptionPane.showMessageDialog(jfI, "Imprimiendo", "Impresion", 0);
            //Reabrir la factura
            jfMarket jfM=new jfMarket();
            Arqueo a=new Arqueo();
            a.setId(v.getIdArqueo());
            jfI.setVisible(false);
            jfM.iniciarVenta(a);
        }
    }
    public void cargarDetalle(Venta v) throws SQLException{
        listaDetalle=conV.obtenerVDet(v);                        
        dtmDetalle=(DefaultTableModel)jfI.jtDetalle.getModel();
        if(!listaDetalle.isEmpty()){//Verificamos si trajo filas                                                    
            Object[] object = new Object[7];
            String cadena="Sin Descripcion";
            for(int i=0; i<listaDetalle.size();i++){
                object[0]=listaDetalle.get(i).getIdProducto();
                //object[1]=listaDetalle.get(i).getIdLote();                           
                //object[2]=listaDetalle.get(i).getIdProducto();
                pro.setCodigo(listaDetalle.get(i).getIdProducto());
                if(conPro.buscar(pro)){
                    cadena=pro.getDescripcion();                                
                }
                object[1]=cadena;
                object[2]=listaDetalle.get(i).getCantidad();                
                object[3]=listaDetalle.get(i).getPrecio();                
                object[4]=listaDetalle.get(i).getPrecio()*listaDetalle.get(i).getCantidad();
                dtmDetalle.addRow(object);               
            }
            //hacer visible los datos en el JTable
            jfI.jtDetalle.setModel(dtmDetalle);
        }
    }    
    
}
