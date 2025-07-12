/*
 * 
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author JFAA
 */
public class Conexion {
    
    private final String base="aparteiBD";
    private final String user="postgres";
    private final String password="123";
    private final String url="jdbc:postgresql://localhost:5432/"+base;
    //private final String url="jdbc:postgresql://192.168.1.39/"+base;
    private Connection con =null;
    
    public Connection getConexion() throws SQLException{
        try{
            con=DriverManager.getConnection(this.url,this.user,this.password);
        }catch(SQLException e){
            System.err.println(e);
        }
        return con;
    }
    
}
