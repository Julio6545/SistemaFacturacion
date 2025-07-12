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
public class ConsultaTimbrado extends Conexion{
    //Métodos que realizan los ABMs sobre la tabla Factura-Detalle
   public boolean registrar (Timbrado t) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO timbrado(numero_timbrado,fecha_desde,fecha_hasta,observacion,estado,"
                + " nro_factura_inicial,nro_factura_final)"
                + " VALUES (?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1,t.getNumero_timbrado());
           ps.setDate(2,t.getFecha_desde());
           ps.setDate(3, t.getFecha_Hasta());
           ps.setString(4,t.getObservación());  
           ps.setString(5, t.getEstado());
           ps.setInt(6, t.getNroFacturaInicial());
           ps.setInt(7, t.getNroFacturaFinal());
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println(e+ " Error al insertar timbrado...");
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
     public boolean modificar (Timbrado t) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="UPDATE timbrado SET numero_timbrado=?,fecha_desde=?,fecha_hasta=?,observacion=?, "
                + " nro_factura_inicial=?,nro_factura_final=?, estado=? "
                + " where id=?";
                
        try{
           ps=con.prepareStatement(sql);
           ps.setInt(1,t.getNumero_timbrado());
           ps.setDate(2,t.getFecha_desde());
           ps.setDate(3, t.getFecha_Hasta());
           ps.setString(4,t.getObservación());
           ps.setInt(5, t.getNroFacturaInicial());
           ps.setInt(6, t.getNroFacturaFinal());
           ps.setString(7, t.getEstado());
           ps.setInt(8, t.getId());
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println(e+ " Error al modificar timbrado...");
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
//Buscar ultima TIMBRADO por numero de timbrado
   public Timbrado buscarTimbrado(Timbrado t) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM timbrado where numero_timbrado=?";
        
        try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, t.getNumero_timbrado());
           rs=ps.executeQuery();
           if (rs.next()){
              t.setId(rs.getInt("id"));
              t.setFecha_desde(rs.getDate("fecha_desde"));
              t.setFecha_Hasta(rs.getDate("fecha_hasta"));
              t.setNumero_timbrado(rs.getInt("numero_timbrado"));
              t.setObservación((rs.getString("observacion")));
              t.setNroFacturaInicial(rs.getInt("nro_factura_inicial"));
              t.setNroFacturaFinal(rs.getInt("nro_factura_final"));
              t.setEstado(rs.getString("estado"));
           }
           return t;
        }catch(SQLException e){
            System.err.println("No existe registro con el parametro: "+e); 
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }        
    }  
   //Buscar TIMBRADO si ya existe
   public boolean buscarTim(Timbrado t) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM timbrado where numero_timbrado=?";
        
        try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, t.getId());
           rs=ps.executeQuery();
           if (rs.next()){
             return true;
           }
           
        }catch(SQLException e){
            System.err.println("No existe registro con el parametro: "+e); 
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
        return false;        
    }   
   //Buscar ultima TIMBRADO por numero de timbrado
   public void buscarTimFecha(Timbrado t) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM timbrado where fecha_inicio=?";
        
        try{
           ps=con.prepareStatement(sql);
           ps.setDate(1, t.getFecha_desde());
           rs=ps.executeQuery();
           if (rs.next()){
              t.setId(rs.getInt("id"));
              t.setFecha_desde(rs.getDate("fecha_dedes"));
              t.setFecha_Hasta(rs.getDate("fecha_hasta"));
              t.setNumero_timbrado(rs.getInt("numero_timbrado"));
              t.setObservación(rs.getString(rs.getString("observacion")));
              t.setEstado(rs.getString("estado"));
           }
        }catch(SQLException e){
            System.err.println("No existe registro con el parametro: "+e);                        
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }        
    }      
   //Trar timbrado actual   
   public Timbrado buscarTimbradoActual(Timbrado t) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM timbrado where estado='ACTIVO'";
        
        try{
           ps=con.prepareStatement(sql);           
           rs=ps.executeQuery();
           if (rs.next()){
              t.setId(rs.getInt("id"));
              t.setFecha_desde(rs.getDate("fecha_desde"));
              t.setFecha_Hasta(rs.getDate("fecha_hasta"));
              t.setNumero_timbrado(rs.getInt("numero_timbrado"));
              t.setObservación((rs.getString("observacion")));
              t.setNroFacturaInicial(rs.getInt("nro_factura_inicial"));
              t.setNroFacturaFinal(rs.getInt("nro_factura_final"));
              t.setEstado(rs.getString("estado"));
           }
           return t;
        }catch(SQLException e){
            System.err.println("No existe registro con el parametro: "+e); 
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }        
    }
   //Buscar ultima TIMBRADO por numero de timbrado
   public Timbrado buscarTimbradoId(Timbrado t) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM timbrado where id=?";
        
        try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, t.getId());
           rs=ps.executeQuery();
           if (rs.next()){
              t.setId(rs.getInt("id"));
              t.setFecha_desde(rs.getDate("fecha_desde"));
              t.setFecha_Hasta(rs.getDate("fecha_hasta"));
              t.setNumero_timbrado(rs.getInt("numero_timbrado"));
              t.setObservación((rs.getString("observacion")));
              t.setNroFacturaInicial(rs.getInt("nro_factura_inicial"));
              t.setNroFacturaFinal(rs.getInt("nro_factura_final"));
              t.setEstado(rs.getString("estado"));
           }
           return t;
        }catch(SQLException e){
            System.err.println("No existe registro con el parametro: "+e); 
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }        
    }  
}
