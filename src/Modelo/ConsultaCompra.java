/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author JFAA
 */
public class ConsultaCompra extends Conexion{
    //Métodos que realizan los ABMs sobre la tabla Factura-Detalle
   public boolean registrarC(Compra com) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO compra(nrocompra, idempresa,idvendedor,idusuario,tiporecibo, fecha, hora,"
                + "numcaja, modopago, totalC,cancelado,fecha_vence,estado) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1,com.getNroCompra());
           ps.setString(2,com.getRucEmpresa());
           ps.setInt(3,com.getIdVendedor());
           ps.setString(4,com.getIdUsuario());
           ps.setInt(5, com.getTipoRecibo());
           ps.setDate(6,com.getFecha());
           ps.setTime(7,com.getHora());
           ps.setInt(8,com.getNumCaja());
           ps.setInt(9,com.getTipoPago());
           ps.setInt(10,com.getTotalC());
           ps.setInt(11, com.getCancelado());
           ps.setDate(12,com.getFechaVencimiento());
           ps.setString(13, com.getEstado());
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println(e+ " Error al insertar");
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
   public String buscarUltimaFac() throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM compra ORDER BY nrocompra DESC LIMIT 1";
        
        try{
           ps=con.prepareStatement(sql);
           //System.out.println("antes de rs");
           rs=ps.executeQuery();
           rs.next();
           //System.out.println("antes, numero index="+rs.getString("idfactura"));
           int nroF=(rs.getInt("nrocompra")+1);           
           return String.valueOf(nroF);
        }catch(SQLException e){
            System.err.println("Aun no hay registros de Factura: "+e);            
            return "1";
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }        
    }
   //Traer Venta credito 
   //ArrayList <DetalleV>
   public boolean obtenerCC(Compra c) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM compra WHERE nrocompra=? AND modopago=?";
       //List<DetalleC> D=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, c.getNroCompra());
           ps.setInt(2, 2);
           rs=ps.executeQuery();           
           if(rs.next()){                  
               //DetalleV dF=new DetalleV();
               c.setIdCompra(rs.getInt("idcompra"));
               c.setNroCompra(rs.getInt("nrocompra"));
               c.setRucEmpresa(rs.getString("idempresa"));
               c.setIdVendedor(rs.getInt("idvendedor"));
               c.setIdUsuario(rs.getString("idusuario"));
               c.setTipoRecibo(rs.getInt("tiporecibo"));
               c.setFecha(rs.getDate("fecha"));
               c.setFechaVencimiento(rs.getDate("fecha_vence"));
               c.setHora(rs.getTime("hora"));
               c.setNumCaja(rs.getInt("numcaja"));
               c.setTipoPago(rs.getInt("modopago"));
               c.setTotalC(rs.getInt("totalC"));
               c.setCancelado(rs.getInt("cancelado"));
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
   public boolean modificarEstadoC(Compra c) throws SQLException{
       PreparedStatement ps=null;
       Connection con= getConexion();
       String sql="UPDATE compra SET cancelado=? WHERE nrocompra=?";
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, 1);
           ps.setInt(2, c.getNroCompra());
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
