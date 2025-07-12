/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author JFAA
 */
public class ConsultaDetalleTick extends Conexion{
    //Metodos de consulta a la base de datos
    public boolean registrar(DetalleV det) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO detalleticket("
                + "idticket, idproducto, nrolote, cantidad, precio,limite) VALUES (?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1,det.getIdVenta());
           ps.setString(2,det.getIdProducto());
           ps.setString(3,det.getIdLote());
           ps.setDouble(4, det.getCantidad());
           ps.setDouble(5,det.getPrecio());
           ps.setInt(6,det.getLimite());
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
