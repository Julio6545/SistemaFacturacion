/*
 * Clase que modela las consultas a la tabla marca
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
public class ConsultaMarca extends Conexion {
    //Método para registrar Marca
    public boolean registrar(Marca m) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO marca(nombre,origen)"
                + " VALUES (?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setString(1,m.getNombre());
           ps.setString(2,m.getOrigen());
           
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
    public boolean modificar(Marca m) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="UPDATE marca SET nombre=?,origen=?"
                + " WHERE idmarca=? ";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setString(1, m.getNombre());
           ps.setString(2, m.getOrigen());
           ps.setInt(3,m.getId());          
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
    //Buscar Marca
    public boolean buscar(Marca m) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM marca WHERE nombre=? ";
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, m.getNombre());
           rs=ps.executeQuery();
           if(rs.next()){ 
               m.setId(rs.getInt("idmarca"));
               m.setNombre(rs.getString("nombre"));
               m.setOrigen(rs.getString("origen"));          
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
    //Metodo que despliega marcas de productos 
    public List obtenerM() throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();                     
          
       List<Marca> listaMarca=new ArrayList<>();        
       String sql="SELECT * FROM marca order by idmarca asc";
       //String sql="SELECT * FROM lote L INNER JOIN producto p ON L.idproducto=p.idproducto";
       try{
           ps=con.prepareStatement(sql);           
           rs=ps.executeQuery();
           while(rs.next()){                              
               Marca m=new Marca();
               m.setId(rs.getInt("idmarca"));
               m.setNombre(rs.getString("nombre"));
               m.setOrigen(rs.getString("origen"));                                  
               listaMarca.add(m);
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
       return listaMarca;
    }
    //Buscar categoria y enviar una lista de los nombres de categorias
    public ArrayList<String> llenarCombo() throws SQLException{
        ArrayList<String> lista=new ArrayList<>();
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();        
        String sql="SELECT * FROM marca order by idmarca asc ";
        try{
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                //System.out.println(rs.getString("nombre"));
                lista.add(rs.getString("nombre"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al buscar Marcas "+e);
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
