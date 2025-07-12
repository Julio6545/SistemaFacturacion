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
public class ConLote extends Conexion{    
    //Método para registrar Lote************************************************ 
    public boolean registrar(Lote l) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO lote (nrolote,idproducto,cantidad,fechavencimiento)"
                + " VALUES (?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setString(1,l.getNrolote());
           ps.setString(2,l.getIdproducto());
           ps.setDouble(3, l.getCantidad());
           ps.setDate(4,l.getFechaVence());          
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println("Error al insertar Lote "+e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
    //Metodo para Modificar un Lote*********************************************
    public boolean modificar(Lote l) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();        
        String sql="UPDATE lote SET nrolote=?, idproducto=?, cantidad=?, "
                + "fechavencimiento=?  WHERE idlote=?";
        try{
           ps=con.prepareStatement(sql) ;           
           ps.setString(1,l.getNrolote());
           ps.setString(2,l.getIdproducto());
           ps.setDouble(3,l.getCantidad());
           ps.setDate(4,l.getFechaVence());
           ps.setInt(5,l.getIdlote());
           ps.execute();
           return true;           
        }catch(SQLException e){
            System.err.println("Error al modificar Lote"+e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
    //Metodo Buscar lote********************************************************
    public boolean buscar(Lote l) throws SQLException{
        //System.out.println("La categoria es : 1");
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM lote WHERE nrolote=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, l.getNrolote());
           rs=ps.executeQuery();           
           if(rs.next()){               
               l.setIdlote(Integer.parseInt(rs.getString("idlote")));
               l.setNrolote((rs.getString("nrolote")));
               l.setIdproducto(rs.getString("idproducto"));
               l.setCantidad(rs.getInt("cantidad"));
               l.setFechaVence((rs.getDate("fechavencimiento")));               
               return true;
           }
           return false;           
        }catch(SQLException e){
            System.err.println("Error al buscar lote : "+e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
    //Traer lotes asociados a productos ordenado por fecha de vencimiento****** 
   public List obtenerLotes() throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();         
       List<Lote> listaDetalle2=new ArrayList<>();        
       //String sql="SELECT * FROM lote";
       String sql="SELECT * FROM lote l INNER JOIN producto p "
               + "ON l.idproducto=p.codigo ORDER BY l.fechavencimiento";       
       try{
           ps=con.prepareStatement(sql);           
           rs=ps.executeQuery();
           while(rs.next()){               
               //System.out.println(rs.getInt("l.idlote"));
               Lote l=new Lote();    
               l.setIdlote(rs.getInt("l.idlote"));
               l.setNrolote(rs.getString("l.nrolote"));
               l.setIdproducto(rs.getString("l.idproducto"));               
               l.setDescripcion(rs.getString("p.descripcion"));               
               l.setCantidad(rs.getDouble("l.cantidad"));
               l.setFechaVence(rs.getDate("l.fechavencimiento"));                      
               listaDetalle2.add(l);
           }           
       }catch(SQLException e){
            System.err.println("Error al buscar lotes : "+e);
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
   //Traer lotes asociados a un producto ordenado por fecha de vencimiento***** 
   public ArrayList<String> obtenerLote(Producto p) throws SQLException{
        ArrayList<String> lista=new ArrayList<>();
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();                              
        String sql="SELECT * FROM lote WHERE idproducto=? "
                + "ORDER BY fechavencimiento";                      
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, p.getCodigo());
           rs=ps.executeQuery();
           while(rs.next()){                
                lista.add(rs.getString("nrolote"));
            }    
        }catch(SQLException e){
            System.err.println("Error al buscar lotes : "+e);
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
       return lista;
    }
   //Actualizar Stock en lotes
   public boolean actualizarStock(Lote l) throws SQLException{
       PreparedStatement ps=null;       
       Connection con=getConexion();
       String sql="UPDATE lote SET cantidad=? WHERE nrolote=?";
       try{
           ps=con.prepareStatement(sql);
           ps.setDouble(1, l.getCantidad());
           ps.setString(2, l.getNrolote());
           ps.execute();
           return true;
       }catch(SQLException ex){
           JOptionPane.showMessageDialog(null,"Error al actualizar stock en Lote "+ex);           
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
