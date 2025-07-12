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

/**
 *
 * @author JFAA
 */
public class ConsultaF extends Conexion{
    //Métodos que realizan los ABMs sobre la tabla Factura-Detalle
   public boolean registrar(Venta fac) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO factura(nrofactura, idcliente,idusuario,idvendedor, fecha, hora,"
                + "numcaja, modopago, totalfactura) VALUES (?,?,?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1,fac.getNroFactura());
           ps.setString(2,fac.getIdCliente());
           ps.setString(3,fac.getIdUsuario());
           ps.setString(4,fac.getIdVendedor());
           ps.setDate(5,fac.getFecha());
           ps.setTime(6,fac.getHora());
           ps.setInt(7, fac.getNumCaja());
           ps.setInt(8, fac.getTipoPago());
           ps.setInt(9, fac.getTotalF());
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
        
        String sql= "SELECT * FROM factura ORDER BY nrofactura DESC LIMIT 1";
        
        try{
           ps=con.prepareStatement(sql);
           //System.out.println("antes de rs");
           rs=ps.executeQuery();
           rs.next();
           //System.out.println("antes, numero index="+rs.getString("idfactura"));
           int nroF=(rs.getInt("nrofactura")+1);           
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
   //Traer todas las facturas 
   public ArrayList <DetalleVC> obtenerF(Cierre c) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM factura f INNER JOIN detallefactura df "
               + "ON f.idfactura=df.idfactura WHERE f.fecha=?";
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
               dF.setIdVenta(rs.getInt("idfactura"));
               dF.setIdProducto(rs.getString("idproducto"));
               dF.setCantidad(rs.getInt("cantidad"));
               dF.setPrecio(rs.getInt("precio"));
               dF.setTipoPago(rs.getInt("f.modopago"));
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
}
