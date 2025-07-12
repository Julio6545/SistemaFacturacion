/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
public class ConTicket extends Conexion{
    //Métodos que realizan los ABMs sobre la tabla Factura-Detalle
   public boolean registrar(Venta tic) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO ticket(idcliente,idusuario, fecha, hora,"
                + "numcaja, modopago, totalticket,ganancia,cancelado,procesado,estado)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           //ps.setInt(1,tic.getNroFactura());
           ps.setString(1,tic.getIdCliente());
           ps.setString(2,tic.getIdUsuario());          
           ps.setDate(3,tic.getFecha());
           ps.setTime(4,tic.getHora());
           ps.setInt(5, tic.getNumCaja());
           ps.setInt(6, tic.getTipoPago());
           ps.setInt(7, tic.getTotalF());
           ps.setInt(8, tic.getGanancia());
           ps.setInt(9, tic.getCancelado());
           ps.setInt(10, tic.getProcesado());
           ps.setString(11, tic.getEstado());
           ps.execute();
           return true;
           
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error al insertar ticket "+e);            
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
   public String buscarUltimoTic() throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM ticket ORDER BY nroticket DESC LIMIT 1";
        
        try{
           ps=con.prepareStatement(sql);
           //System.out.println("antes de rs");
           rs=ps.executeQuery();
           if (rs.next()){
               int nroF=(rs.getInt("nroticket")+1); 
               return String.valueOf(nroF);
           }else{
               return "1";
           }
           //System.out.println("antes, numero index="+rs.getString("idfactura"));          
        }catch(SQLException e){
            System.err.println("Aun no hay registros de Ticket: "+e);         
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    return "1";        
    }   
   //Traer ticket y su detalle 
   public boolean obtenerTCab(Venta t) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM ticket t WHERE t.nroticket=?";
       List<DetalleV> D=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, t.getNroPresupuesto());
           rs=ps.executeQuery();           
            while(rs.next()){   
               //t.setIdFactura(rs.getInt("idticket"));               
               t.setNroFactura(rs.getInt("nroticket"));
               t.setIdCliente(rs.getString("idcliente"));
               t.setIdUsuario(rs.getString("idusuario"));
               t.setFecha(rs.getDate("fecha"));
               t.setHora(rs.getTime("hora"));
               t.setNumCaja(rs.getInt("numcaja"));
               t.setTipoPago(rs.getInt("modopago"));
               t.setTotalF(rs.getInt("totalticket"));
               t.setGanancia(rs.getInt("ganancia"));
               t.setCancelado(rs.getInt("cancelado"));
               t.setProcesado(rs.getInt("procesado"));               
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
       return true;
   }
//Traer ticket y su detalle 
   public ArrayList <DetalleV> obtenerTDet(Venta t) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM ticket t INNER JOIN detalleticket dt "
               + "ON t.nroticket=dt.idticket WHERE t.nroticket=?";
       List<DetalleV> D=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, t.getNroPresupuesto());
           rs=ps.executeQuery();           
            while(rs.next()){   
               //t.setIdFactura(rs.getInt("idticket"));
               DetalleV dF=new DetalleV();              
               dF.setIdDetalle(rs.getInt("iddetalle"));
               dF.setIdVenta(rs.getInt("idticket"));
               dF.setIdProducto(rs.getString("idproducto"));
               dF.setIdLote(rs.getString("nrolote"));
               dF.setCantidad(rs.getInt("cantidad"));
               dF.setPrecio(rs.getInt("precio")); 
               dF.setLimite(rs.getInt("limite"));
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
       return (ArrayList<DetalleV>) D;
   }
   //Obtener nro de Ticket
   public boolean obtenerTicket(Venta tic)throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con=getConexion();
       String sql="SELECT * FROM ticket WHERE idcliente=? ORDER BY nroticket DESC LIMIT 1";
       try{
           ps=con.prepareStatement(sql);
           ps.setString(1, tic.getIdCliente());
           //ps.setDate(2, tic.getFecha());
           //ps.setString(1, tic.getIdUsuario());
           rs=ps.executeQuery();
           if (rs.next()){
               tic.setNroPresupuesto(rs.getInt("nroticket"));
               return true;
           }
       }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Error al buscar ticket "+e);
       }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
       return false;
   }
   //Obtener nro de Ticket
   public boolean obtenerNroTicket(Venta tic)throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con=getConexion();
       String sql="SELECT * FROM ticket WHERE idusuario=? ORDER BY nroticket DESC LIMIT 1";
       try{
           ps=con.prepareStatement(sql);
           //ps.setInt(1, tic.getIdCliente());
           //ps.setDate(2, tic.getFecha());
           ps.setString(1, tic.getIdUsuario());
           rs=ps.executeQuery();
           if (rs.next()){
               tic.setNroPresupuesto(rs.getInt("nroticket"));
               return true;
           }
       }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Error al buscar ticket "+e);
       }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
       return false;
   }
   //Modificar estado de ticket al momento de hacerse efectiva la venta 
   public boolean modificarEstado(Venta v) throws SQLException{
       PreparedStatement ps=null;
       Connection con= getConexion();
       String sql="UPDATE ticket SET procesado=? WHERE nroticket=?";
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, 1);
           ps.setInt(2, v.getNroPresupuesto());
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
   //traer tickets según rango de fecha
   public ArrayList <DetalleVC> obtenerT(Cierre c) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM ticket t INNER JOIN detalleticket dt " 
               + "ON t.nroticket=dt.idticket WHERE t.fecha=?";
       List<DetalleVC> D=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setDate(1, c.getFechaActividad());
           rs=ps.executeQuery();
           ResultSetMetaData md=rs.getMetaData();
           int cl=md.getColumnCount();
           while(rs.next()){          
               DetalleVC dF=new DetalleVC();
               dF.setIdDetalle(rs.getInt("iddetalle"));
               dF.setIdVenta(rs.getInt("idticket"));
               dF.setIdProducto(rs.getString("idproducto"));
               dF.setIdLote(rs.getString("nrolote"));
               dF.setCantidad(rs.getInt("cantidad"));
               dF.setPrecio(rs.getInt("precio"));
               dF.setTipoPago(rs.getInt("modopago"));
               dF.setGanancia(rs.getInt("ganancia"));
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
   public boolean obtenerTC(Venta t) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM ticket WHERE nroticket=? AND modopago=?";
       //String sql="SELECT * FROM venta v INNER JOIN detalleventa dv "
       //        + "ON t.nroventa=dv.nroventa WHERE t.nroventa=?";
       List<DetalleV> D=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, t.getNroFactura());
           ps.setInt(2, 2);
           rs=ps.executeQuery();           
           if(rs.next()){                  
               //DetalleV dF=new DetalleV();
               t.setNroFactura(rs.getInt("nroticket"));
               t.setIdCliente(rs.getString("idcliente"));
               t.setIdUsuario(rs.getString("idusuario"));               
               t.setFecha(rs.getDate("fecha"));
               t.setHora(rs.getTime("hora"));
               t.setNumCaja(rs.getInt("numcaja"));
               t.setTipoPago(rs.getInt("modopago"));
               t.setTotalF(rs.getInt("totalticket"));                 
               t.setGanancia(rs.getInt("ganancia"));
               t.setCancelado(rs.getInt("cancelado"));
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
   public boolean modificarEstadoT(Venta t) throws SQLException{
       PreparedStatement ps=null;
       Connection con= getConexion();
       String sql="UPDATE ticket SET cancelado=? WHERE nroticket=?";
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, 1);
           ps.setInt(2, t.getNroFactura());
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
