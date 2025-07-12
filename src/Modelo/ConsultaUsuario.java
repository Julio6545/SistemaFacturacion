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
import javax.swing.JOptionPane;

/**
 *
 * @author JFAA
 */
public class ConsultaUsuario extends Conexion {
    //Método para registrar producto 
    public boolean registrar(Usuario usu) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO usuario(nrocedula, nombre, apellido, fechaNacimiento,"
                + "telefono, direccion,email,usuario,clave,roll)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setString(1,usu.getCi());
           ps.setString(2,usu.getNombre());
           ps.setString(3,usu.getApellido());
           ps.setDate(4,usu.getFechaNac());
           ps.setString(5,usu.getTelefono());
           ps.setString(6,usu.getDireccion());
           ps.setString(7,usu.getEmail());
           ps.setString(8,usu.getNombreusu());
           ps.setString(9,usu.getClave());
           ps.setInt(10,usu.getRol());
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
    //Modificar ususario
    public boolean modificar(Usuario usu) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="UPDATE usuario SET nombre=?, apellido=?, fechaNacimiento=?,"
                + "telefono=?, direccion=?,email=?,usuario=?,clave=?,roll=?"
                + " WHERE nrocedula=?";
        try{
           ps=con.prepareStatement(sql) ;           
           ps.setString(1,usu.getNombre());
           ps.setString(2,usu.getApellido());
           ps.setDate(3,usu.getFechaNac());
           ps.setString(4,usu.getTelefono());
           ps.setString(5,usu.getDireccion());
           ps.setString(6,usu.getEmail());
           ps.setString(7,usu.getNombreusu());
           ps.setString(8,usu.getClave());
           ps.setInt(9,usu.getRol());
           ps.setString(10,usu.getCi()); 
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
    public boolean buscar(Usuario usu) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM usuario WHERE nrocedula=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, usu.getCi());
           rs=ps.executeQuery();
           if(rs.next()){
               usu.setId(Integer.parseInt(rs.getString("idusuario")));               
               usu.setCi(rs.getString("nrocedula"));
               usu.setNombre(rs.getString("nombre"));
               usu.setApellido(rs.getString("apellido"));
               usu.setFechaNac(rs.getDate("fechaNacimiento"));
               usu.setTelefono(rs.getString("telefono"));
               usu.setDireccion(rs.getString("direccion"));           
               usu.setEmail(rs.getString("email"));
               usu.setNombreusu(rs.getString("usuario"));
               usu.setClave(rs.getString("clave"));
               usu.setRol(rs.getInt("roll"));
               //usu.setIdciudad(Integer.parseInt(rs.getString("idciudad")));               
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
    //Buscar Ususario
    public Usuario buscarUsu(Usuario usu) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM usuario WHERE usuario=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, usu.getNombreusu());
           rs=ps.executeQuery();
           if(rs.next()){
               usu.setId(Integer.parseInt(rs.getString("idusuario")));               
               usu.setCi(rs.getString("nrocedula"));
               usu.setNombre(rs.getString("nombre"));
               usu.setApellido(rs.getString("apellido"));
               usu.setFechaNac(rs.getDate("fechaNacimiento"));
               usu.setTelefono(rs.getString("telefono"));
               usu.setDireccion(rs.getString("direccion"));           
               usu.setEmail(rs.getString("email"));
               usu.setNombreusu(rs.getString("usuario"));
               usu.setClave(rs.getString("clave"));
               usu.setRol(rs.getInt("roll"));
               //usu.setIdciudad(Integer.parseInt(rs.getString("idciudad")));               
               return usu;
           }
           return null;
           
        }catch(SQLException e){
            System.err.println(e);
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
    //Buscar Uusuario y enviar una lista de los nombres de Usuarios
    public ArrayList<String> llenarCombo() throws SQLException{
        ArrayList<String> lista=new ArrayList<>();
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();        
        String sql="SELECT * FROM usuario order by idusuario asc ";
        try{
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                //System.out.println(rs.getString("nombre"));
                lista.add(rs.getString("nombre")+" "+rs.getString("apellido")+"-"+rs.getString("usuario"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al buscar usuarios "+e);
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
        return lista;
    }
}
