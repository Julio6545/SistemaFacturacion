/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JFAA
 */
public class ConCierre extends Conexion{
    //Métodos que realizan los ABMs sobre la tabla Factura-Detalle
   public boolean registrar(Cierre c) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO cierre(nrocierre,nrocaja,usuario, fechaactividad,fechacierre, hora,"
                + "totalcredito, totalcontado, total, ganancia) VALUES (?,?,?,?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1, c.getNrocierre());
           ps.setInt(2,c.getNroCaja());           
           ps.setString(3,c.getUsuario());
           ps.setDate(4,c.getFechaActividad());
           ps.setDate(5,c.getFechaCierre());
           ps.setTime(6,c.getHora());
           ps.setInt(7,c.getTotalCredito());
           ps.setInt(8,c.getTotalContado());
           ps.setInt(9, c.getTotal());
           ps.setInt(10,c.getGanancia());
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
   public String buscarUltimoCierre() throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM cierre ORDER BY nrocierre DESC LIMIT 1";
        
        try{
           ps=con.prepareStatement(sql);           
           rs=ps.executeQuery();
           rs.next();           
           int nroF=(rs.getInt("nrocierre")+1);           
           return String.valueOf(nroF);
        }catch(SQLException e){
            System.err.println("Aun no hay registros de Cierre: "+e);            
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
   public ArrayList <Cierre> obtenerCierres(Cierre c) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM cierre WHERE fechaactividad>=? AND fechaactividad<=?";
       List<Cierre> D=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setDate(1, c.getFechaActividad());
           ps.setDate(2, c.getFechaCierre());
           rs=ps.executeQuery();
           ResultSetMetaData md=rs.getMetaData();
           int cl=md.getColumnCount();
           while(rs.next()){          
               Cierre dC=new Cierre();
               dC.setIdcierre(rs.getInt("idcierre"));
               dC.setNrocierre(rs.getInt("nrocierre"));
               dC.setNroCaja(rs.getInt("nrocaja"));
               dC.setUsuario(rs.getString("usuario"));
               dC.setFechaActividad(rs.getDate("fechaactividad"));
               dC.setFechaCierre(rs.getDate("fechacierre"));
               dC.setHora(rs.getTime("hora"));
               dC.setTotalCredito(rs.getInt("totalcredito"));
               dC.setTotalContado(rs.getInt("totalcontado"));
               dC.setTotal(rs.getInt("total"));
               dC.setGanancia(rs.getInt("ganancia"));
               D.add(dC);
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
       return (ArrayList<Cierre>) D;
   }
}
