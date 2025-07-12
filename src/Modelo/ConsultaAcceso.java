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

/**
 *
 * @author JFAA
 */
public class ConsultaAcceso extends Conexion {
    //Método para consultar la base de datos para verificar si 
    //Usuario y contraseña coinciden con los registros del usuario.

    /**
     *
     * @param usu Parámetro que contien los datos del usuario
     * @return 'usu' consulta exitosa (devuelve datos del usuario), 'null' consulta fallida
     * @throws SQLException en caso de error en la conexión y posterior consulta
     */
    public Usuario buscar(Usuario usu) throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql="SELECT * FROM usuario WHERE usuario=? and clave=?";
        try{
           ps=con.prepareStatement(sql);
           ps.setString(1, usu.getNombreusu());
           ps.setString(2, usu.getClave());
           rs=ps.executeQuery();
           if(rs.next()){
               usu.setId(Integer.parseInt(rs.getString("idusuario")));
               usu.setCi(rs.getString("nrocedula"));
               usu.setNombre(rs.getString("nombre"));
               usu.setApellido(rs.getString("apellido"));
               usu.setFechaNac(rs.getDate("fechaNacimiento"));
               usu.setDireccion(rs.getString("telefono"));
               usu.setTelefono(rs.getString("direccion"));
               usu.setEmail(rs.getString("email"));               
               usu.setNombreusu(rs.getString("usuario"));
               usu.setClave(rs.getString("clave"));
               usu.setRol(rs.getInt("roll"));
               return usu;
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
}
