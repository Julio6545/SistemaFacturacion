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
public class ConEntrada extends Conexion{
    //Métodos que realizan los ABMs sobre la tabla Factura-Detalle
   public boolean registrar(Entrada ent) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO entrada(nroentrada, identidad,tipoentrada,"
                + "idusuario,fecha,hora, totalentrada,nroventa,nroarqueo) VALUES (?,?,?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1,ent.getNroEntrada());
           ps.setInt(2,ent.getIdCliente());
           ps.setInt(3, ent.getTipoEntrada());
           ps.setString(4,ent.getIdUsuario());
           ps.setDate(5,ent.getFecha());
           ps.setTime(6,ent.getHora());         
           ps.setInt(7, ent.getTotalE());
           ps.setInt(8, ent.getNroventa());
           ps.setInt(9, ent.getIdarqueo());
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println(e+ " Error al insertar Entrada");
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
   public String buscarUltimaEnt() throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM entrada ORDER BY nroentrada DESC LIMIT 1";
        
        try{
           ps=con.prepareStatement(sql);
           //System.out.println("antes de rs");
           rs=ps.executeQuery();
           if (rs.next()){
               int nroF=(rs.getInt("nroentrada")+1);
               return String.valueOf(nroF);
           }else{
               return "1";
           }           
           
        }catch(SQLException e){
            System.err.println("Aun no hay registros de Entrada: "+e);            
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
   public ArrayList <DetalleV> obtenerE(Cierre c) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM entrada e INNER JOIN detalleentrada de "
               + "ON e.nroentrada=de.nroentrada WHERE e.fecha=?";
       List<DetalleV> D=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setDate(1, c.getFechaActividad());
           rs=ps.executeQuery();
           ResultSetMetaData md=rs.getMetaData();
           int cl=md.getColumnCount();
           while(rs.next()){          
               DetalleV dF=new DetalleV();
               dF.setIdDetalle(rs.getInt("iddetalleentrada"));
               dF.setIdVenta(rs.getInt("nroentrada"));
               dF.setIdProducto(rs.getString("codproducto"));
               dF.setCantidad(rs.getInt("cantidad"));
               dF.setPrecio(rs.getInt("precio"));
               D.add(dF);
           }           
       }catch(SQLException e){
            System.err.println("El Error al buscar Entradas: "+e);
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
   //Obtener total entrda de una fecha
   
}
