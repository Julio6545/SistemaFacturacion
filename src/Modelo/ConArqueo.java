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

/**
 *
 * @author JFAA
 */
public class ConArqueo extends Conexion{
    //Métodos que realizan los ABMs sobre la tabla Factura-Detalle
    /**
     *
     * @param a Parámetro que contiene los datos del arqueo
     * @return 'a' consulta exitosa (devuelve datos del arqueo), 'null' consulta fallida
     * @throws SQLException en caso de error en la conexión y posterior consulta
     */
    public Arqueo buscar(Arqueo a) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM arqueo WHERE usuario_apertura=? and estado=? and caja=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, a.getUsuarioApertura());
           ps.setString(2, a.getEstado());
           ps.setInt(3, a.getCaja());
           rs=ps.executeQuery();
           if(rs.next()){
               a.setId((rs.getInt("id")));
               a.setCaja((rs.getInt("caja")));
               a.setFechaApertura(rs.getDate("fecha_apertura"));
               a.setFechaCierre(rs.getDate("fecha_cierre"));
               a.setHoraApertura(rs.getTime("hora_apertura"));
               a.setHoraCierre(rs.getTime("hora_cierre"));
               a.setUsuarioApertura(rs.getString("usuario_apertura"));
               a.setUsuarioCierre(rs.getString("usuario_cierre"));
               a.setEstado(rs.getString("estado"));
               a.setObservacion(rs.getString("observacion"));
               a.setTotalDescuento(rs.getInt("totaldescuento"));
               return a;
           }
           return null;
           
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
    public boolean insertar(Arqueo a) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO arqueo (caja, usuario_apertura, usuario_cierre, fecha_apertura, " +
                    " fecha_cierre,hora_apertura, hora_cierre, estado, observacion)"
                +   " VALUES (?,?,?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1,a.getCaja());
           ps.setString(2, a.getUsuarioApertura());
           ps.setString(3, a.getUsuarioCierre());
           ps.setDate(4,a.getFechaApertura());
           ps.setDate(5,a.getFechaCierre());
           ps.setTime(6,a.getHoraApertura());
           ps.setTime(7, a.getHoraCierre());
           ps.setString(8, a.getEstado());
           ps.setString(9, a.getObservacion());
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
   //Cerrar arqueo
    public boolean cerrar(Arqueo a) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql=" UPDATE arqueo set caja=?, usuario_apertura=?, usuario_cierre=?, fecha_apertura=?, " +
                    " fecha_cierre=?, hora_apertura=?, hora_cierre=?, estado=?, "
                + " ganancia=?,total=?,totalcontado=?,totalcredito=?, observacion=?,  "
                + " totaldescuento=?, totalnota=? "
                + " WHERE id=? ";                
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1,a.getCaja());
           ps.setString(2, a.getUsuarioApertura());
           ps.setString(3, a.getUsuarioCierre());
           ps.setDate(4,a.getFechaApertura());
           ps.setDate(5,a.getFechaCierre());
           ps.setTime(6,a.getHoraApertura());
           ps.setTime(7, a.getHoraCierre());
           ps.setString(8, a.getEstado());
           ps.setInt(9, a.getGanancia());
           ps.setInt(10, a.getTotal());
           ps.setInt(11, a.getTotalcontado());
           ps.setInt(12, a.getTotalcredito());
           ps.setString(13, a.getObservacion());
           ps.setInt(14,a.getTotalDescuento());
           ps.setInt(15, a.getTotalNota());
           ps.setInt(16, a.getId());           
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println("Error al Cerrar Arqueo "+e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
   //Metodo para Modificar un Arqueo
    public boolean modificar(Arqueo a) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="UPDATE arqueo SET estado=?  WHERE id=?";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setString(1, a.getEstado());
           ps.setInt(2, a.getId());
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println("Error al modificar estado de Arqueo "+e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
   //Buscar ultimo arqueo
   public String buscarUltimoArqueo() throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM arqueo ORDER BY id DESC LIMIT 1";
        
        try{
           ps=con.prepareStatement(sql);           
           rs=ps.executeQuery();
           rs.next();           
           int nroA=(rs.getInt("id"));           
           return String.valueOf(nroA);
        }catch(SQLException e){
            System.err.println("Aun no hay registros de Cierre: "+e);            
            return "1";
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }        
    }
    //buscar arqueo usuario
   public ArrayList <Arqueo> buscarListaArqueoUsuario(Arqueo a) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        List<Arqueo> D=new ArrayList<>();
        String sql= "SELECT * FROM arqueo where usuario_apertura=? and caja=? and estado=?";
        
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, a.getUsuarioApertura());
           ps.setInt(2, a.getCaja());
           ps.setString(3, a.getEstado()); 
           rs=ps.executeQuery();
           while(rs.next()){          
               Arqueo dC=new Arqueo();
               dC.setId((rs.getInt("id")));
               dC.setCaja((rs.getInt("caja")));
               dC.setFechaApertura(rs.getDate("fecha_apertura"));
               dC.setFechaCierre(rs.getDate("fecha_cierre"));
               dC.setHoraApertura(rs.getTime("hora_apertura"));
               dC.setHoraCierre(rs.getTime("hora_cierre"));
               dC.setUsuarioApertura(rs.getString("usuario_apertura"));
               dC.setUsuarioCierre(rs.getString("usuario_cierre"));
               dC.setEstado(rs.getString("estado"));
               dC.setObservacion(rs.getString("observacion"));
               D.add(a);              
           }
        }catch(SQLException e){
            System.err.println("Aun no hay registros de Arqueo: "+e);   
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }        
        return (ArrayList<Arqueo>) D;
    }
   //buscar arqueo usuario
   public ArrayList <Arqueo> buscarArqueoGanancia(Arqueo a) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        List<Arqueo> D=new ArrayList<>();
        String sql= "SELECT * FROM arqueo where fecha_apertura>=? and fecha_cierre<=? and estado=?";
        
        try{
           ps=con.prepareStatement(sql);
           ps.setDate(1, a.getFechaApertura());
           ps.setDate(2, a.getFechaCierre());
           ps.setString(3, "C"); 
           rs=ps.executeQuery();
           while(rs.next()){          
               Arqueo dC=new Arqueo();
               dC.setId((rs.getInt("id")));
               dC.setCaja((rs.getInt("caja")));
               dC.setFechaApertura(rs.getDate("fecha_apertura"));
               dC.setFechaCierre(rs.getDate("fecha_cierre"));
               dC.setHoraApertura(rs.getTime("hora_apertura"));
               dC.setHoraCierre(rs.getTime("hora_cierre"));
               dC.setUsuarioApertura(rs.getString("usuario_apertura"));
               dC.setUsuarioCierre(rs.getString("usuario_cierre"));
               dC.setEstado(rs.getString("estado"));
               dC.setObservacion(rs.getString("observacion"));
               dC.setTotalcredito(rs.getInt("totalcredito"));
               dC.setTotalcontado(rs.getInt("totalcontado"));
               dC.setTotal(rs.getInt("total"));
               dC.setGanancia(rs.getInt("ganancia"));
               D.add(dC);              
           }
        }catch(SQLException e){
            System.err.println("Aun no hay registros de Arqueo: "+e);   
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }        
        return (ArrayList<Arqueo>) D;
    }
    //buscar arqueo usuario
   public Arqueo buscarArqueoUsuario(Arqueo a) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();        
        String sql= "SELECT * FROM arqueo where usuario_apertura=? and caja=? and estado=?";
        
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, a.getUsuarioApertura());
           ps.setInt(2, a.getCaja());
           ps.setString(3, a.getEstado()); 
           rs=ps.executeQuery();
           while(rs.next()){                         
               a.setId((rs.getInt("id")));
               a.setCaja((rs.getInt("caja")));
               a.setFechaApertura(rs.getDate("fecha_apertura"));
               a.setFechaCierre(rs.getDate("fecha_cierre"));
               a.setHoraApertura(rs.getTime("hora_apertura"));
               a.setHoraCierre(rs.getTime("hora_cierre"));
               a.setUsuarioApertura(rs.getString("usuario_apertura"));
               a.setUsuarioCierre(rs.getString("usuario_cierre"));
               a.setEstado(rs.getString("estado"));
               a.setObservacion(rs.getString("observacion"));               
           }
        }catch(SQLException e){
            System.err.println("Aun no hay registros de Arqueo: "+e);   
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }        
        return a;
    }
}
