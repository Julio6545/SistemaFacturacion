/*
 * Clase que modela las consultas a los datos de la BD referentes a la clase
 * Producto.
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
public class ConsultaProducto extends Conexion{
    //Método para registrar producto 
    public boolean registrar(Producto pro) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO producto(codigo, descripcion, idcategoria, idmarca,existencia, costo, "
                + "stock_minimo,incremento,iva, precio,deposito,pasillo,estante,bandeja) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setString(1,pro.getCodigo());
           ps.setString(2,pro.getDescripcion());
           ps.setInt(3,pro.getIdcategoria());
           ps.setInt(4, pro.getMarca());
           ps.setInt(5,pro.getExistencia());
           ps.setInt(6,pro.getCosto());
           ps.setInt(7,pro.getStock_minimo());
           ps.setInt(8,pro.getIncremento());
           ps.setInt(9, pro.getIva());
           ps.setInt(10, pro.getPrecio());
           ps.setInt(11,pro.getDepoestante());           
           ps.setInt(12, pro.getPasillo());
           ps.setInt(13, pro.getEstante());
           ps.setInt(14, pro.getBandeja());       
           
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
    //Metodo para Modificar un Producto
    public boolean modificar(Producto pro) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="UPDATE producto SET descripcion=?, idcategoria=?, idmarca=?,"
                + "existencia=?, costo=?, incremento=?, iva=?, precio=?, deposito=?, "
                + "stock_minimo=?, pasillo=?, estante=? ,bandeja=? WHERE codigo=?";
        try{
           ps=con.prepareStatement(sql) ;           
           ps.setString(1,pro.getDescripcion());
           ps.setInt(2,pro.getIdcategoria());
           ps.setInt(3,pro.getMarca());
           ps.setInt(4,pro.getExistencia());           
           ps.setInt(5,pro.getCosto());
           ps.setInt(6,pro.getIncremento());
           ps.setInt(7,pro.getIva());
           ps.setInt(8,pro.getPrecio());
           ps.setInt(9,pro.getDepoestante());
           ps.setInt(10,pro.getStock_minimo());           
           ps.setInt(11, pro.getPasillo());
           ps.setInt(12, pro.getEstante());
           ps.setInt(13, pro.getBandeja());
           ps.setString(14, pro.getCodigo());           
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println("Error al modificar Producto"+e);
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
    public boolean buscar(Producto mod) throws SQLException{
        //System.out.println("La categoria es : 1");
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM producto WHERE codigo=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, mod.getCodigo());
           rs=ps.executeQuery();
           
           if(rs.next()){               
               mod.setIdproducto(Integer.parseInt(rs.getString("idproducto")));
               mod.setCodigo(rs.getString("codigo"));
               mod.setDescripcion(rs.getString("descripcion"));
               mod.setIdcategoria(rs.getInt("idcategoria"));
               mod.setMarca(rs.getInt("idmarca"));
               mod.setExistencia(rs.getInt("existencia"));
               mod.setStock_minimo(rs.getInt("stock_minimo"));
               mod.setCosto(rs.getInt("costo"));
               mod.setIncremento(rs.getInt("incremento"));
               mod.setIva(rs.getInt("iva"));
               mod.setPrecio(rs.getInt("precio"));
               mod.setDepoestante(rs.getInt("deposito"));
               mod.setBandeja(rs.getInt("bandeja"));
               mod.setEstante(rs.getInt("estante"));
               mod.setPasillo(rs.getInt("pasillo"));               
               return true;
           }
           return false;
           
        }catch(SQLException e){
            System.err.println("El Error: "+e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
    //Verificar al momento de registrar si no existe un producto con igual codigo
    public boolean buscar2(Producto pro) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM producto WHERE codigo=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, pro.getCodigo());
           rs=ps.executeQuery();
           if(rs.next()){
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
    //proceso actualizar stock Compra/Venta
    public boolean ActualizarStock(Producto p, int cant)throws SQLException{
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection con=getConexion();
        String sql="UPDATE producto SET existencia=? WHERE codigo=?";
        try{
            ps=con.prepareStatement(sql);
            ps.setInt(1, cant);
            ps.setString(2, p.getCodigo());
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al actualizar Stock -"+ e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }        
    }
    //Proceso actualizar precio Compra
    public boolean ActualizarPrecio(Producto p)throws SQLException{
        PreparedStatement ps=null;
        ResultSet rs=null;
        Connection con=getConexion();
        String sql="UPDATE producto SET costo=?, precio=? WHERE codigo=?";
        try{
            ps=con.prepareStatement(sql);
            ps.setInt(1, p.getCosto());
            ps.setInt(2, p.getPrecio());
            ps.setString(3, p.getCodigo());
            ps.executeUpdate();
            return true;
        }catch(SQLException e){
            JOptionPane.showMessageDialog(null,"Error al actualizar Precio: "+ e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }        
    }
    //Traer todos los productos ************************************************ 
   public List obtenerProductos() throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();         
       List<Producto> listaP=new ArrayList<>();        
       String sql="SELECT * FROM producto";         
       try{
           ps=con.prepareStatement(sql);           
           rs=ps.executeQuery();
           while(rs.next()){               
               //System.out.println(rs.getInt("l.idlote"));
               Producto p=new Producto();    
               p.setCodigo(rs.getString("codigo"));
               p.setDescripcion(rs.getString("descripcion"));
               p.setIdcategoria(rs.getInt("idcategoria"));
               p.setMarca(rs.getInt("idmarca"));
               p.setExistencia(rs.getInt("existencia"));
               p.setStock_minimo(rs.getInt("stock_minimo"));
               p.setCosto(rs.getInt("costo"));
               p.setIncremento(rs.getInt("incremento"));
               p.setIva(rs.getInt("iva"));
               p.setPrecio(rs.getInt("precio"));
               p.setDepoestante(rs.getInt("deposito"));
               p.setBandeja(rs.getInt("bandeja"));
               p.setEstante(rs.getInt("estante"));
               p.setPasillo(rs.getInt("pasillo"));
               listaP.add(p);
           }           
       }catch(SQLException e){
            System.err.println("Error al buscar productos : "+e);
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
       return listaP;
  }
   //Traer todos los productos ************************************************ 
   public List ProductosStockLimite(Producto pr) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();         
       List<Producto> listaP=new ArrayList<>();        
       String sql="SELECT * FROM producto where existencia<=stock_minimo and idmarca = ? order by descripcion";                
       try{
           ps=con.prepareStatement(sql); 
           ps.setInt(1, pr.getMarca());
           rs=ps.executeQuery();
           while(rs.next()){               
               //System.out.println(rs.getInt("l.idlote"));
               Producto p=new Producto();    
               p.setCodigo(rs.getString("codigo"));
               p.setDescripcion(rs.getString("descripcion"));
               p.setIdcategoria(rs.getInt("idcategoria"));
               p.setMarca(rs.getInt("idmarca"));
               p.setExistencia(rs.getInt("existencia"));
               p.setStock_minimo(rs.getInt("stock_minimo"));
               p.setCosto(rs.getInt("costo"));
               p.setIncremento(rs.getInt("incremento"));
               p.setIva(rs.getInt("iva"));
               p.setPrecio(rs.getInt("precio"));
               p.setDepoestante(rs.getInt("deposito"));
               p.setBandeja(rs.getInt("bandeja"));
               p.setEstante(rs.getInt("estante"));
               p.setPasillo(rs.getInt("pasillo"));
               listaP.add(p);
           }           
       }catch(SQLException e){
            System.err.println("Error al buscar productos : "+e);
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
       return listaP;
  }
   //Traer todos los productos por Categorias*********************************** 
   public List obtenerPCat(Producto pro) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();         
       List<Producto> listaP=new ArrayList<>();        
       String sql="SELECT * FROM producto WHERE idcategoria=? ";         
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, pro.getIdcategoria());
           rs=ps.executeQuery();
           while(rs.next()){               
               //System.out.println(rs.getInt("l.idlote"));
               Producto p=new Producto();    
               p.setCodigo(rs.getString("codigo"));
               p.setDescripcion(rs.getString("descripcion"));
               p.setIdcategoria(rs.getInt("idcategoria"));
               p.setMarca(rs.getInt("idmarca"));
               p.setExistencia(rs.getInt("existencia"));
               p.setStock_minimo(rs.getInt("stock_minimo"));
               p.setCosto(rs.getInt("costo"));
               p.setIncremento(rs.getInt("incremento"));
               p.setIva(rs.getInt("iva"));
               p.setPrecio(rs.getInt("precio"));
               p.setDepoestante(rs.getInt("deposito"));
               p.setBandeja(rs.getInt("bandeja"));
               p.setEstante(rs.getInt("estante"));
               p.setPasillo(rs.getInt("pasillo"));
               listaP.add(p);
           }           
       }catch(SQLException e){
            System.err.println("Error al buscar productos : "+e);
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
       return listaP;
  }
   //Traer todos los productos por Categorias*********************************** 
   public List obtenerPM(Producto pro) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();         
       List<Producto> listaP=new ArrayList<>();        
       String sql="SELECT * FROM producto WHERE idmarca=?";         
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, pro.getMarca());
           rs=ps.executeQuery();
           while(rs.next()){               
               //System.out.println(rs.getInt("l.idlote"));
               Producto p=new Producto();    
               p.setCodigo(rs.getString("codigo"));
               p.setDescripcion(rs.getString("descripcion"));
               p.setIdcategoria(rs.getInt("idcategoria"));
               p.setMarca(rs.getInt("idmarca"));
               p.setExistencia(rs.getInt("existencia"));
               p.setStock_minimo(rs.getInt("stock_minimo"));
               p.setCosto(rs.getInt("costo"));
               p.setIncremento(rs.getInt("incremento"));
               p.setIva(rs.getInt("iva"));
               p.setPrecio(rs.getInt("precio"));
               p.setDepoestante(rs.getInt("deposito"));
               p.setBandeja(rs.getInt("bandeja"));
               p.setEstante(rs.getInt("estante"));
               p.setPasillo(rs.getInt("pasillo"));
               listaP.add(p);
           }           
       }catch(SQLException e){
            System.err.println("Error al buscar productos : "+e);
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
       return listaP;
  }
   public boolean eliminar(Producto pro) throws SQLException{
        PreparedStatement ps=null;         
        Connection con= getConexion();
        
        String sql="delete FROM producto WHERE codigo=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, pro.getCodigo());
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
}
