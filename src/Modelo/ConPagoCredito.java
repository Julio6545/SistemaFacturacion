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
public class ConPagoCredito extends Conexion{
    //Métodos que realizan los ABMs sobre la tabla Factura-Detalle
   public boolean registrarCobro(PagoCredito pc) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO pagocredito(nrocompra,nrorecibopago,"
                + "cicobrador,fecha,hora,monto,pagado,usuario,idarqueo) VALUES (?,?,?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1, pc.getNroventa());
           ps.setInt(2,pc.getNrorecibopago());
           ps.setInt(3, pc.getCicobrador());
           ps.setDate(4,pc.getFecha());
           ps.setTime(5,pc.getHora());           
           ps.setInt(6,pc.getMonto());
           ps.setInt(7,pc.getPagado());
           ps.setString(8, pc.getUsuario());
           ps.setInt(9,pc.getNroArqueo());
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println(e+ " Error al insertar Pago");
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
   public ArrayList <PagoCredito> obtenerPagos(PagoCredito pc) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM pagocredito WHERE nrocompra=?";
       List<PagoCredito> P=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, pc.getNroventa());           
           rs=ps.executeQuery();
           ResultSetMetaData md=rs.getMetaData();
           int cl=md.getColumnCount();
           while(rs.next()){          
               PagoCredito pC=new PagoCredito();
               pC.setIdcobro(rs.getInt("idpago"));
               pC.setNroventa(rs.getInt("nrocompra"));
               pC.setNrorecibopago(rs.getInt("nrorecibopago"));
               pC.setCicobrador(rs.getInt("cicobrador"));
               pC.setFecha(rs.getDate("fecha"));
               pC.setHora(rs.getTime("hora"));
               pC.setMonto(rs.getInt("monto"));
               pC.setPagado(rs.getInt("pagado"));
               pC.setUsuario(rs.getString("usuario")); 
               pC.setNroArqueo(rs.getInt("nroarqueo"));
               P.add(pC);
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
       return (ArrayList<PagoCredito>) P;
   }
}
