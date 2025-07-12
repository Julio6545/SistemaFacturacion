/*
 *
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
public class ConsultaEmpresa extends Conexion{
    //Método para registrar producto 
    public boolean registrar(Empresa e) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO empresa(rucempresa, nombre,"
                + "direccion,telefono,correo)"
                + " VALUES (?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setString(1,e.getRucempresa());
           ps.setString(2,e.getNombre());
           ps.setString(3,e.getDireccion());
           ps.setString(4,e.getTelefono());           
           ps.setString(5,e.getCorreo());
           ps.execute();
           return true;
           
        }catch(SQLException er){
            System.err.println(er);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException er){
                System.err.println(er);
            }
        }
    }
    //Modificar  
    public boolean modificar(Empresa e) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="UPDATE empresa SET rucempresa=?, nombre=?, direccion=?,"
                + "telefono=?, correo=? WHERE idempresa=?";
        try{
           ps=con.prepareStatement(sql) ;           
           ps.setString(1,e.getRucempresa());
           ps.setString(2,e.getNombre());
           ps.setString(3,e.getDireccion());
           ps.setString(4,e.getTelefono());           
           ps.setString(5,e.getCorreo());
           ps.setInt(6, e.getIdEmpresa());
           ps.execute();
           return true;           
        }catch(SQLException er){
            System.err.println(er);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException er){
                System.err.println(er);
            }
        }
    }
    //Buscar registros de Empresa
    public boolean buscar(Empresa e) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM empresa WHERE rucempresa=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, e.getRucempresa());
           rs=ps.executeQuery();
           if(rs.next()){
               e.setIdEmpresa(rs.getInt("idempresa"));
               e.setRucempresa(rs.getString("rucempresa"));
               e.setNombre(rs.getString("nombre"));
               e.setDireccion(rs.getString("direccion"));           
               e.setTelefono(rs.getString("telefono"));
               e.setCorreo(rs.getString("correo"));                                      
               return true;
           }
           return false;
           
        }catch(SQLException er){
            System.err.println(e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException er){
                System.err.println(e);
            }
        }
    }
    //Buscar si hay registros de Empresa
    public boolean buscarE() throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM empresa";
        try{
           ps=con.prepareStatement(sql);           
           rs=ps.executeQuery();
           return rs.next();           
        }catch(SQLException er){
            System.err.println(er);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException er){
                System.err.println(er);
            }
        }
    }
    //traer lista de empresas
    public List obtenerE() throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();         
       List<Empresa> Empresas=new ArrayList<>();               
       String sql="SELECT * FROM empresa";       
       try{
           ps=con.prepareStatement(sql);           
           rs=ps.executeQuery();
           while(rs.next()){                              
               Empresa e=new Empresa();    
               e.setIdEmpresa(rs.getInt("idempresa"));
               e.setRucempresa(rs.getString("rucempresa"));
               e.setNombre(rs.getString("nombre"));
               e.setDireccion(rs.getString("direccion"));           
               e.setTelefono(rs.getString("telefono"));
               e.setCorreo(rs.getString("correo"));  
               Empresas.add(e);
           }           
       }catch(SQLException e){
            System.err.println("Error al buscar Empresas : "+e);
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
       return Empresas;
    }
    //Traer todas las Empresas 
   public ArrayList<String> llenarCombo() throws SQLException{
        ArrayList<String> lista=new ArrayList<>();
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();        
        String sql="SELECT * FROM empresa";
        try{
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                //System.out.println(rs.getString("nombre"));
                lista.add(rs.getString("nombre"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al buscar Empresa "+e);
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
