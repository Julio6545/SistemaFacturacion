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
public class ConsultaEmp extends Conexion{
    //Métodos que realizan los ABMs sobre la tabla Factura-Detalle
   public boolean registrar(Empresa emp) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO empresa(rucempresa, nombre, direccion, telefono, correo) VALUES (?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setString(1,emp.getRucempresa());
           ps.setString(2,emp.getNombre());           
           ps.setString(3,emp.getDireccion());
           ps.setString(4, emp.getTelefono());
           ps.setString(5,emp.getCorreo());
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println(e+ " Error al insertar");
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println("Error: "+e);
            }
        }
    }
   //Buscar ultima factura
   public Empresa buscarEmpresa(Empresa emp) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM empresa WHERE rucempresa=?";
        
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, emp.getRucempresa());
           rs=ps.executeQuery();
           if(rs.next()){
               emp.setIdEmpresa(rs.getInt("idempresa"));
               emp.setNombre(rs.getString("nombre"));
               emp.setRucempresa(rs.getString("rucempresa"));
               emp.setTelefono(rs.getString("telefono"));
               emp.setDireccion(rs.getString("direccion"));
               emp.setCorreo(rs.getString("correo"));
               return emp;
           }else return null;
        }catch(SQLException e){
            System.err.println("Error: "+e);            
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }            
    }
   //Buscar Empresa por id
   public Empresa buscarId(Empresa emp) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM empresa WHERE idempresa=?";
        
        try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, emp.getIdEmpresa());
           rs=ps.executeQuery();
           if(rs.next()){
               emp.setIdEmpresa(rs.getInt("idempresa"));
               emp.setNombre(rs.getString("nombre"));
               emp.setRucempresa(rs.getString("rucempresa"));
               emp.setTelefono(rs.getString("telefono"));
               emp.setDireccion(rs.getString("direccion"));
               emp.setCorreo(rs.getString("correo"));
               return emp;
           }else return null;
        }catch(SQLException e){
            System.err.println("Error: "+e);            
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
