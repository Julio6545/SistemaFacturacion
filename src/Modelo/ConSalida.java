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
public class ConSalida extends Conexion{
    //Métodos que realizan los ABMs sobre la tabla Factura-Detalle
   public boolean registrarS(Salida sal) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO salida(nrosalida, identidad,tiposalida,"
                + "idusuario,fecha,hora, totalsalida) VALUES (?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1,sal.getNroSalida());
           ps.setInt(2,sal.getIdCliente());
           ps.setInt(3, sal.getTipoSalida());
           ps.setString(4,sal.getIdUsuario());
           ps.setDate(5,sal.getFecha());
           ps.setTime(6,sal.getHora());         
           ps.setInt(7, sal.getTotalS());
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
   public String buscarUltimaSal() throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM salida ORDER BY nrosalida DESC LIMIT 1";
        
        try{
           ps=con.prepareStatement(sql);
           //System.out.println("antes de rs");
           rs=ps.executeQuery();
           rs.next();
           //System.out.println("antes, numero index="+rs.getString("idfactura"));
           int nroF=(rs.getInt("nrosalida")+1);           
           return String.valueOf(nroF);
        }catch(SQLException e){
            System.err.println("Aun no hay registros de Salida: "+e);            
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
   public ArrayList <DetalleV> obtenerS(Cierre c) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM salida s INNER JOIN detallesalida ds "
               + "ON s.nrosalida=ds.nrosalida WHERE s.fecha=?";
       List<DetalleV> D=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setDate(1, c.getFechaActividad());
           rs=ps.executeQuery();
           ResultSetMetaData md=rs.getMetaData();
           int cl=md.getColumnCount();
           while(rs.next()){          
               DetalleV dF=new DetalleV();
               dF.setIdDetalle(rs.getInt("iddetallesalida"));
               dF.setIdVenta(rs.getInt("nrosalida"));
               dF.setIdProducto(rs.getString("codproducto"));
               dF.setCantidad(rs.getInt("cantidad"));
               dF.setPrecio(rs.getInt("precio"));
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
}
