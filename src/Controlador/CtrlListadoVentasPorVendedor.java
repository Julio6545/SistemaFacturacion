package Controlador;
import Modelo.ConArqueo;
import Modelo.ConsultaUsuario;
import Modelo.ConsultaV;
import Modelo.Venta;
import Reportes.jfVentasPorVendedores;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author JFAA
 */
public class CtrlListadoVentasPorVendedor implements ActionListener{
    private Venta v;
    private ConArqueo cC;
    private final ConsultaV conV;
    private jfVentasPorVendedores jfVG;
    
    
    //Variable detalle de factura
    TableModel detalle;
    List<Venta> listaDetalle;
    DefaultTableModel dtmDetalle=new DefaultTableModel();  
    
    //Instanciar la clases 
    ConsultaUsuario conU=new ConsultaUsuario();
            
    //Constructor
    public CtrlListadoVentasPorVendedor(Venta v,ConsultaV conV,jfVentasPorVendedores jfVG){
        this.v=v;
        this.conV=conV;
        this.jfVG=jfVG;               
        this.jfVG.jbtConsultar.addActionListener(this);        
    }
    
    //inicializar los valores en el formulario
    public void iniciar() throws SQLException 
    {
        Calendar gc=new GregorianCalendar();
        this.jfVG.jdcDesde.setCalendar(gc);
        this.jfVG.jdcHasta.setCalendar(gc);
        llenarCombo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //controlar los eventos de los botones y otros componentes del formulario
        //Boton Consultar
        long ventas = 0, totalVts=0, ganancia=0;        
        if(e.getSource()==jfVG.jbtConsultar){
            //Inicializar JTable si ya hubo una consulta anterior
            if(jfVG.jtCierres.getRowCount()!=0){
                limpiarJTable();
            }            
            v.setFecha(formatoF(jfVG.jdcDesde.getDate()));
            v.setFechaCierre(formatoF(jfVG.jdcHasta.getDate()));
            String user=jfVG.jcbVendedor.getSelectedItem().toString();
            String[] usu = user.split("-");
            user = usu[1];
            v.setIdUsuario(user);
            try{
            listaDetalle=conV.obtenerVenPorVendedor(v);
            dtmDetalle=(DefaultTableModel)jfVG.jtCierres.getModel();
            if(!listaDetalle.isEmpty()){
                Object[] object = new Object[5];               
                for(int i=0; i<listaDetalle.size();i++){
                    object[0]=i+1;
                    object[1]=listaDetalle.get(i).getFecha();
                    object[2]=listaDetalle.get(i).getNroFactura();
                    object[3]=listaDetalle.get(i).getTotalCobrar();
                    object[4]=listaDetalle.get(i).getGanancia();                    
                    dtmDetalle.addRow(object);                    
                    ventas=ventas+listaDetalle.get(i).getTotalCobrar();
                    ganancia=ganancia+listaDetalle.get(i).getGanancia(); 
                    totalVts=totalVts+1;
                    //System.out.println(object[0]+","+object[2]);
                }
                
                //hacer visible los datos en el JTable
                jfVG.jtCierres.setModel(dtmDetalle);
                //habilitar botones            
                //jfVG.jbtExcel.setEnabled(true);
                //jfVG.jbtCsv.setEnabled(true);
                jfVG.jlVentas.setText(String.valueOf(ventas));
                jfVG.jlGanancia.setText(String.valueOf(ganancia));
                jfVG.jlItems.setText(String.valueOf(totalVts));                
                jfVG.jdcDesde.grabFocus();
        
            }else{                
                JOptionPane.showMessageDialog(null, "No existe registros de ventas para la fecha","",
                        JOptionPane.INFORMATION_MESSAGE);
                limpiar();
            }
            }catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null,"Error al buscar Cierres");
                    Logger.getLogger(CtrlProducto.class.getName()).log(Level.SEVERE, null, ex);
            }
        }         
    } 
        
    //Calcular total de la factura 
    public static double calTotal(DefaultTableModel detalle){
        int total=0;
        for(int i=0; i<detalle.getRowCount();i++){
            total+= (int)detalle.getValueAt(i, 4);               
        }
        return total;
    }
    //proceso limpiar
    public void limpiar(){       
        //limpiar las filas de la tabla detalle
        detalle=jfVG.jtCierres.getModel();
         DefaultTableModel modelo=(DefaultTableModel) jfVG.jtCierres.getModel();
        for(int i=((detalle.getRowCount())-1);i>-1;i--){       
            modelo.removeRow(i);
        }
        jfVG.jlVentas.setText("0");
        jfVG.jlGanancia.setText("0");
        jfVG.jlItems.setText("0");        
    }
    
    //Inicializar la tabla
    public void limpiarJTable (){
        while(jfVG.jtCierres.getRowCount()>0) {
            ((DefaultTableModel)jfVG.jtCierres.getModel()).removeRow(0);
        }
    }
    //Formatear la fecha para poder guardarlo en una base de datos                  
    public Date formatoF(java.util.Date f){
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");                    
        String fecha2;                    
        fecha2 = (formatofecha.format(f));                
        java.sql.Date fecha=java.sql.Date.valueOf(fecha2);
        return fecha;
    }
    //Metodo llenar combo box con Categorias de productos
    public void llenarCombo() throws SQLException{
        ArrayList<String> Lista=new ArrayList<>();
        Lista=conU.llenarCombo();
        for(int i=0;i<Lista.size();i++){
            jfVG.jcbVendedor.addItem(Lista.get(i));
        }
    }
}