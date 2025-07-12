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
public class ConsultaVendedor extends Conexion{
    //Método para registrar Vendedor
    public boolean registrar(Vendedor vend) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO proveedor(nrocedula,nombre, apellido,"
                + "telefono, correo, direccion,idempresa)"
                + " VALUES (?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setString(1,vend.getCi());           
           ps.setString(2,vend.getNombre());
           ps.setString(3,vend.getApellido());           
           ps.setString(4,vend.getTelefono());
           ps.setString(5,vend.getEmail());
           ps.setString(6,vend.getDireccion());
           ps.setInt(7,vend.getIdEmpresa());
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
    //Buscar Vendedor
    public boolean buscar(Vendedor vend) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM proveedor WHERE nrocedula=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, vend.getCi());
           rs=ps.executeQuery();
           if(rs.next()){
               vend.setId(rs.getInt("idvendedor"));
               vend.setCi(rs.getString("nrocedula"));               
               vend.setNombre(rs.getString("nombre"));
               vend.setApellido(rs.getString("apellido"));               
               vend.setTelefono(rs.getString("telefono"));
               vend.setEmail(rs.getString("correo"));
               vend.setDireccion(rs.getString("direccion"));           
               vend.setIdEmpresa(rs.getInt("idempresa"));                                      
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
    //Buscar Vendedor asociado a una Empresa
    public boolean buscarVE( Empresa emp, Vendedor vend) throws SQLException{
        //Vendedor vend=new Vendedor();
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM proveedor WHERE idempresa=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, emp.getIdEmpresa());
           rs=ps.executeQuery();
           if(rs.next()){
               vend.setId(rs.getInt("idvendedor"));
               vend.setCi(rs.getString("nrocedula"));               
               vend.setNombre(rs.getString("nombre"));
               vend.setApellido(rs.getString("apellido"));               
               vend.setTelefono(rs.getString("telefono"));
               vend.setEmail(rs.getString("correo"));
               vend.setDireccion(rs.getString("direccion"));           
               vend.setIdEmpresa(rs.getInt("idempresa"));
               return true;
           }else {System.out.println("No exite Vendedor..");
               return false;}
           
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
