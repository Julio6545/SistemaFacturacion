/*
 * Clase que modela las consultas a la tabla Categorias
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
public class ConsultaCategoria extends Conexion{
    //Método para registrar producto 
    public boolean registrar(Categoria cat) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO categoria(nombre, descripcion) VALUES (?,?)";
        try{
           ps=con.prepareStatement(sql) ;           
           ps.setString(1,cat.getNombre());
           ps.setString(2,cat.getDescripcion());
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
    //Buscar categoria y enviar una lista de los nombres de categorias
    public ArrayList<String> llenarCombo() throws SQLException{
        ArrayList<String> lista=new ArrayList<>();
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();        
        String sql="SELECT * FROM categoria order by idcategoria asc";
        try{
            ps=con.prepareStatement(sql);
            rs=ps.executeQuery();
            while(rs.next()){
                //System.out.println(rs.getString("nombre"));
                lista.add(rs.getString("nombre"));
            }
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al buscar categorias "+e);
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
        return lista;
    }
    
    //Metodo que despliega categorias de productos 
    public List obtenerCat() throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();                     
          
       List<Categoria> listaDetalle2=new ArrayList<>();        
       String sql="SELECT * FROM categoria order by idcategoria asc";
       //String sql="SELECT * FROM lote L INNER JOIN producto p ON L.idproducto=p.idproducto";
       try{
           ps=con.prepareStatement(sql);           
           rs=ps.executeQuery();
           while(rs.next()){               
               //System.out.println(rs.getInt("idlote"));
               Categoria c=new Categoria();
               c.setIdcategoria(rs.getInt("idcategoria"));
               c.setNombre(rs.getString("nombre"));
               c.setDescripcion(rs.getString("descripcion"));                                  
               listaDetalle2.add(c);
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
       return listaDetalle2;
    }
    //Buscar una categoria 
    public boolean buscar(Categoria cat) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();
       String sql="SELECT * FROM categoria WHERE idcategoria=?";
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, cat.getIdcategoria());
           rs=ps.executeQuery();
           if(rs.next()){
             cat.setNombre(rs.getString("nombre"));
             cat.setDescripcion(rs.getString("descripcion"));
             return true;
           }
       }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Error al buscar Categoria "+e);
       }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
       return false;
    }
    //Buscar una categoria 
    public boolean buscarCat(Categoria cat) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();
       String sql="SELECT * FROM categoria WHERE nombre=?";
       try{
           ps=con.prepareStatement(sql);
           ps.setString(1, cat.getNombre());
           rs=ps.executeQuery();
           if(rs.next()){
             cat.setNombre(rs.getString("nombre"));
             cat.setDescripcion(rs.getString("descripcion"));
             return true;
           }
       }catch(SQLException e){
           JOptionPane.showMessageDialog(null, "Error al buscar Categoria "+e);
       }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
       return false;
    }
    //Modificar Categoria
    public boolean modificar(Categoria cat) throws SQLException{
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection con = getConexion();
        String sql="UPDATE categoria SET nombre=?, descripcion=? WHERE idcategoria=?";
        try{
            ps=con.prepareStatement(sql);
            ps.setString(1, cat.getNombre());
            ps.setString(2,cat.getDescripcion());
            ps.setInt(3, cat.getIdcategoria());
            ps.execute();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null, "Error Mod. Categoria"+e);
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
        return false;
    }
    
}
