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
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author JFAA
 */
public class ConCiudad extends Conexion{
    //Método para registrar producto 
    public boolean registrar(Ciudad c) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO ciudad (idciudad, nombre, idpais) VALUES (?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1,c.getIdciudad());
           ps.setString(2, c.getNombre());
           ps.setInt(3, c.getIdpais());          
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println("Error al insertar Ciudad "+e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
    //Metodo para Modificar un Producto
    public boolean modificar(Ciudad c) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="UPDATE ciudad SET nombre=?, idpais=?  WHERE idciudad=?";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setString(1, c.getNombre());
           ps.setInt(2, c.getIdpais());
           ps.setInt(3,c.getIdciudad());
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println("Error al modificar Ciudad"+e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
    //Metodo Buscar producto
    public boolean buscar(Ciudad c) throws SQLException{
        //System.out.println("La categoria es : 1");
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM ciudad WHERE idciudad=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, c.getIdciudad());
           rs=ps.executeQuery();
           
           if(rs.next()){               
               c.setIdciudad(rs.getInt("idciudad"));
               c.setNombre(rs.getString("nombre"));
               c.setIdpais(rs.getInt("idpais"));               
               return true;
           }
           return false;
           
        }catch(SQLException e){
            System.err.println("Error al buscar ciudad : "+e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
    //Traer todas las facturas 
   public ArrayList<String> llenarCombo() throws SQLException{
        ArrayList<String> lista=new ArrayList<>();
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();        
        String sql="SELECT * FROM ciudad";
        try{
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                //System.out.println(rs.getString("nombre"));
                lista.add(rs.getString("nombre"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al buscar Ciudad "+e);
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
