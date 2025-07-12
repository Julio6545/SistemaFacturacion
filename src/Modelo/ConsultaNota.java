package Modelo;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author JFAA
 */
public class ConsultaNota extends Conexion{
    //Métodos que realizan los ABMs sobre la tabla Factura-Detalle
   public boolean registrar(NotaCredito ven) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        
        String sql="INSERT INTO nota(nronota, idcliente, idusuario,idvendedor,fecha,hora,"
                + " numcaja, totalnota,numero_venta,estado,"
                + "id_arqueo,id_timbrado,"
                + " descuento,totaldescuento,totalcredito,ganancia "
                + ")"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1,ven.getNronota());
           ps.setString(2,ven.getIdCliente());
           ps.setString(3,ven.getIdUsuario());
           ps.setString(4,ven.getIdVendedor());
           ps.setDate(5,ven.getFecha());
           ps.setTime(6,ven.getHora());
           ps.setInt(7, ven.getNumCaja());           
           ps.setInt(8, ven.getTotalF());          
           ps.setInt(9, ven.getNroFactura());
           ps.setString(10,ven.getEstado());
           ps.setInt(11, ven.getIdArqueo());
           ps.setInt(12, ven.getIdTimbrado());
           ps.setInt(13, ven.getDescuento());
           ps.setInt(14, ven.getTotalDescuento());
           ps.setInt(15, ven.getGanancia());
           ps.setInt(16, ven.getTotalCobrar());           
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println(e+ " Error al insertar Nota");
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
   //Buscar ultima factura
   public String buscarUltimaNot() throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM nota ORDER BY nronota DESC LIMIT 1";
        
        try{
           ps=con.prepareStatement(sql);
           //System.out.println("antes de rs");
           rs=ps.executeQuery();
           if(rs.next()){
            //System.out.println("antes, numero index="+rs.getString("idfactura"));
            int nroF=(rs.getInt("nronota")+1);           
            return String.valueOf(nroF);
           }else{
               return "1";
           }
        }catch(SQLException e){
            System.err.println("Aun no hay registros de Notas: "+e);            
            return "1";
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }        
    }   
   //Traer todas las Notas de una fecha segun arqueo,caja,usuario
   public ArrayList <DetalleVC> obtenerV(Arqueo c) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM nota v " +
        "	INNER JOIN detallenota dv ON v.nronota=dv.nronota " +
        "	Inner join arqueo a on a.id=v.id_arqueo " +
        "	 WHERE v.fecha =? " +
        "	 and v.id_arqueo = ? " +
        "	 and a.estado=? " +
        "	 and a.caja=? " +
        "	 and a.usuario_apertura=? ";
       List<DetalleVC> D=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setDate(1, c.getFechaApertura());
           ps.setInt(2, c.getId());
           ps.setString(3, c.getEstado());
           ps.setInt(4,c.getCaja() );
           ps.setString(5, c.getUsuarioApertura());
           rs=ps.executeQuery();
           ResultSetMetaData md=rs.getMetaData();
           int cl=md.getColumnCount();
           while(rs.next()){          
               DetalleVC dF=new DetalleVC();
               dF.setIdDetalle(rs.getInt("iddetalle"));
               dF.setIdVenta(rs.getInt("nroventa"));
               dF.setIdProducto(rs.getString("idproducto"));
               dF.setIdLote(rs.getString("nrolote"));
               dF.setCantidad(rs.getInt("cantidad"));
               dF.setPrecio(rs.getInt("precio"));
               dF.setTipoPago(rs.getInt("modopago"));
               dF.setGanancia(rs.getInt("totalganancia"));
               dF.setCancelado(rs.getInt("cancelado"));               
               D.add(dF);
           }           
       }catch(SQLException e){
            System.err.println("El Error: "+e);
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
       return (ArrayList<DetalleVC>) D;
   }
   //Traer Venta credito 
   //ArrayList <DetalleV>
   public boolean obtenerVC(Venta v) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM venta WHERE nroventa=? AND modopago=?";
       //String sql="SELECT * FROM venta v INNER JOIN detalleventa dv "
       //        + "ON t.nroventa=dv.nroventa WHERE t.nroventa=?";
       List<DetalleV> D=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, v.getNroFactura());
           ps.setInt(2, 2);
           rs=ps.executeQuery();           
           if(rs.next()){                  
               //DetalleV dF=new DetalleV();
               v.setNroFactura(rs.getInt("nroventa"));
               v.setIdCliente(rs.getString("idcliente"));
               v.setIdUsuario(rs.getString("idusuario"));
               v.setIdVendedor(rs.getString("idvendedor"));
               v.setFecha(rs.getDate("fecha"));
               v.setHora(rs.getTime("hora"));
               v.setNumCaja(rs.getInt("numcaja"));
               v.setTipoPago(rs.getInt("modopago"));
               v.setTotalF(rs.getInt("totalventa"));                 
               v.setGanancia(rs.getInt("ganancia"));
               v.setCancelado(rs.getInt("cancelado"));
               v.setDescuento(rs.getInt("descuento"));
               v.setTotalDescuento(rs.getInt("totalDescuento"));
               v.setTotalCobrar(rs.getInt("totalCobrar"));
               /*
               dF.setIdDetalle(rs.getInt("iddetalle"));dF.setIdVenta(rs.getInt("idticket"));
               dF.setIdProducto(rs.getString("idproducto"));dF.setIdLote(rs.getString("nrolote"));
               dF.setCantidad(rs.getInt("cantidad"));dF.setPrecio(rs.getInt("precio"));               
               D.add(dF);*/
            return true;
           }
           
       }catch(SQLException e){
            System.err.println("El Error: "+e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
       //return (ArrayList<DetalleV>) D;     
       return false;
   }
   public boolean modificarEstado(Venta v) throws SQLException{
       PreparedStatement ps=null;
       Connection con= getConexion();
       String sql="UPDATE venta SET cancelado=? WHERE nroventa=?";
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, 1);
           ps.setInt(2, v.getNroFactura());
           ps.executeUpdate();
           return true;
       }catch(SQLException e){
           JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
           return false;
       }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
   }
}
