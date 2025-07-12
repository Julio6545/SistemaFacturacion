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

/**
 *
 * @author JFAA
 */
public class ConsultaCliente extends Conexion{
    //Método para registrar producto 
    public boolean registrar(Persona cli) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO cliente(nrocedula, ruc, nombre, apellido,"
                + "telefono, direccion,idciudad,tipo_cliente)"
                + " VALUES (?,?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setString(1,cli.getCi());
           ps.setString(2, cli.getRuc());
           ps.setString(3,cli.getNombre());
           ps.setString(4,cli.getApellido());           
           ps.setString(5,cli.getTelefono());
           ps.setString(6,cli.getDireccion());
           ps.setInt(7,cli.getIdciudad());
           ps.setInt(8, cli.getTipoCliente());
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
    public boolean modificar(Persona cli) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="UPDATE cliente SET ruc=?, nombre=?, apellido=?,"
                + "telefono=?, direccion=?,idciudad=?, tipo_cliente=? WHERE nrocedula=?";
        try{
           ps=con.prepareStatement(sql) ;           
           ps.setString(1, cli.getRuc());
           ps.setString(2,cli.getNombre());
           ps.setString(3,cli.getApellido());           
           ps.setString(4,cli.getTelefono());
           ps.setString(5,cli.getDireccion());
           ps.setInt(6,cli.getIdciudad());           
           ps.setInt(7, cli.getTipoCliente());
           ps.setString(8,cli.getCi());
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
    public boolean buscar(Persona cli) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM cliente WHERE nrocedula=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, cli.getCi());
           rs=ps.executeQuery();
           if(rs.next()){
               cli.setId(rs.getInt("idcliente"));
               cli.setCi(rs.getString("nrocedula"));
               cli.setRuc(rs.getString("ruc"));
               cli.setNombre(rs.getString("nombre"));
               cli.setApellido(rs.getString("apellido"));               
               cli.setTelefono(rs.getString("telefono"));
               cli.setDireccion(rs.getString("direccion"));           
               cli.setIdciudad(rs.getInt("idciudad"));
               cli.setTipoCliente(rs.getInt("tipo_cliente"));
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
