/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author JFAA
 */
public class ConCobroCredito extends Conexion{
    //Métodos que realizan los ABMs sobre la tabla Factura-Detalle
   public boolean registrarCobro(CobroCredito cc) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO cobrocredito(nroventa,fecha,hora,monto,pagado,usuario,idarqueo) VALUES (?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1, cc.getNroventa());           
           ps.setDate(2,cc.getFecha());
           ps.setTime(3,cc.getHora());           
           ps.setInt(4,cc.getMonto());
           ps.setInt(5,cc.getPagado());
           ps.setString(6, cc.getUsuario());
           ps.setInt(7, cc.getNroArqueo());
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println(e+ " Error al insertar Cobro");
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
   
   //Traer todos los cobros 
   public ArrayList <CobroCredito> obtenerCobros(CobroCredito cc) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM cobrocredito WHERE nroventa=?";
       List<CobroCredito> D=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, cc.getNroventa());           
           rs=ps.executeQuery();
           ResultSetMetaData md=rs.getMetaData();
           int cl=md.getColumnCount();
           while(rs.next()){          
               CobroCredito cC=new CobroCredito();
               cC.setIdcobro(rs.getInt("idcobro"));
               cC.setNroventa(rs.getInt("nroventa"));
               cC.setFecha(rs.getDate("fecha"));
               cC.setHora(rs.getTime("hora"));
               cC.setMonto(rs.getInt("monto"));
               cC.setPagado(rs.getInt("pagado"));
               cC.setUsuario(rs.getString("usuario"));
               cC.setNroArqueo(rs.getInt("idarqueo"));
               D.add(cC);
           }           
       }catch(SQLException e){
            System.err.println("Error al buscar Pagos: "+e);
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
       return (ArrayList<CobroCredito>) D;
   }
}
