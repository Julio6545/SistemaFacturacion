/*
 * Clase que modela las operaciones de consultas sobre la BD
 * Con los datos referentes a la clase Proveedor   
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author JFAA
 */
public class ConsultaProveedor extends Conexion{
    //Método para registrar producto 
    public boolean registrar(Proveedor pro) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO proveedor(nrocedula, nombre, apellido,"
                + "telefono, correo,direccion,idempresa)"
                + " VALUES (?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setString(1,pro.getCi());
           ps.setString(2, pro.getNombre());
           ps.setString(3,pro.getApellido());           
           ps.setString(4,pro.getTelefono());
           ps.setString(5, pro.getEmail());
           ps.setString(6,pro.getDireccion());
           ps.setInt(7,pro.getIdempresa());
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println(e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
    //Modificar  
    public boolean modificar(Proveedor pro) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="UPDATE proveedor SET nombre=?, apellido=?,"
                + "telefono=?, correo=?, direccion=?,idempresa=? WHERE nrocedula=?";
        try{
           ps=con.prepareStatement(sql) ;                      
           ps.setString(1,pro.getNombre());
           ps.setString(2,pro.getApellido());           
           ps.setString(3,pro.getTelefono());
           ps.setString(4, pro.getEmail());
           ps.setString(5,pro.getDireccion());
           ps.setInt(6,pro.getIdempresa());
           ps.setString(7,pro.getCi());
           ps.execute();
           return true;           
        }catch(SQLException e){
            System.err.println(e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
    //Buscar Ususario
    public boolean buscar(Proveedor pro) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM proveedor WHERE nrocedula=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, pro.getCi());
           rs=ps.executeQuery();
           if(rs.next()){
               pro.setId(rs.getInt("id"));
               pro.setCi(rs.getString("nrocedula"));               
               pro.setNombre(rs.getString("nombre"));
               pro.setApellido(rs.getString("apellido"));               
               pro.setTelefono(rs.getString("telefono"));
               pro.setEmail(rs.getString("correo"));
               pro.setDireccion(rs.getString("direccion"));           
               pro.setIdempresa(rs.getInt("idempresa"));                                      
               return true;
           }
           return false;
           
        }catch(SQLException e){
            System.err.println(e);
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
