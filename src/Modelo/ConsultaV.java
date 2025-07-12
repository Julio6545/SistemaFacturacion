package Modelo;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author JFAA
 */
public class ConsultaV extends Conexion{
    //Métodos que realizan los ABMs sobre la tabla Factura-Detalle
   public boolean registrar(Venta ven) throws SQLException{
        PreparedStatement ps=null; 
        Connection con= getConexion();
        
        String sql="INSERT INTO venta(nroventa, idcliente,idusuario,idvendedor, fecha, hora,"
                + "numcaja, modopago, totalventa,ganancia,cancelado,numero_presupuesto,estado,"
                + "id_arqueo,id_timbrado,descuento,totalDescuento,totalCobrar,totalGanancia)"
                + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try{
           ps=con.prepareStatement(sql) ;
           ps.setInt(1,ven.getNroFactura());
           ps.setString(2,ven.getIdCliente());
           ps.setString(3,ven.getIdUsuario());
           ps.setString(4,ven.getIdVendedor());
           ps.setDate(5,ven.getFecha());
           ps.setTime(6,ven.getHora());
           ps.setInt(7, ven.getNumCaja());
           ps.setInt(8, ven.getTipoPago());
           ps.setInt(9, ven.getTotalF());
           ps.setInt(10, ven.getGanancia());
           ps.setInt(11,ven.getCancelado());
           ps.setInt(12, ven.getNroPresupuesto());
           ps.setString(13,ven.getEstado());
           ps.setInt(14, ven.getIdArqueo());
           ps.setInt(15, ven.getIdTimbrado());
           ps.setInt(16, ven.getDescuento());
           ps.setInt(17, ven.getTotalDescuento());
           ps.setInt(18, ven.getTotalCobrar());
           ps.setInt(19, ven.getTotalGanancia());
           ps.execute();
           return true;
           
        }catch(SQLException e){
            System.err.println(e+ " Error al insertar");
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
   //Buscar ultima factura
   public String buscarUltimaVen() throws SQLException{
        PreparedStatement ps=null; 
        ResultSet rs=null;
        Connection con= getConexion();
        
        String sql= "SELECT * FROM venta ORDER BY nroventa DESC LIMIT 1";
        
        try{
           ps=con.prepareStatement(sql);
           //System.out.println("antes de rs");
           rs=ps.executeQuery();
           rs.next();
           //System.out.println("antes, numero index="+rs.getString("idfactura"));
           int nroF=(rs.getInt("nroventa")+1);           
           return String.valueOf(nroF);
        }catch(SQLException e){
            System.err.println("Aun no hay registros de Ventas: "+e);            
            return "1";
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }        
    }   
   //Traer todas las facturas de una fecha segun arqueo,caja,usuario
   public ArrayList <DetalleVC> obtenerV(Arqueo c) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="select iddetalle,nrotran,idproducto,nrolote, " +
                "      cantidad,precio,modopago,totalganancia,cancelado,tipo, fecha, id_arqueo, " +
                "      estado, caja,usuario_apertura, descuento " +
                "      from " +
                "      (SELECT iddetalle,v.nroventa nrotran,idproducto,nrolote, " +
                "       cantidad,precio,modopago,totalganancia,cancelado, 'V' as tipo,v.fecha fecha, "+ 
                "             v.id_arqueo, a.estado, a.caja,a.usuario_apertura,v.totaldescuento descuento " +
                "      	 FROM venta v " +
                "              	INNER JOIN detalleventa dv ON v.nroventa=dv.nroventa " +
                "              	Inner join arqueo a on a.id=v.id_arqueo " +
                "       union all           " +
                "       SELECT iddetalle,n.nronota nrotran,idproducto,nrolote, " +
                "       	 cantidad,precio*-1,3 modopago,0 totalganancia, 0 cancelado,'N' as tipo, n.fecha fecha, " +
                "              n.id_arqueo id_arqueo ,a.estado, a.caja,a.usuario_apertura, n.totaldescuento descuento " +
                "       	 FROM nota n " +
                "       		Inner join arqueo a on n.id_arqueo=a.id " +
                "       		inner join detallenota d on n.nronota = d.nronota "+
                "       union all" +
                "		select nroventa iddetalle,idcobro  nrotran,'Cobro Credito' idproducto,'0' nrolote," +
                "		1 cantidad,pagado precio,1 modopago,0 totalganancia, 0 cancelado,'CbrCr' tipo, fecha," +
                "		idarqueo id_arqueo, 'P' estado, 1 caja, usuario usuario_apertura,0 descuento " +
                "		from cobrocredito c  " +
                "           inner join arqueo a on a.id=c.idarqueo) resumen"+
                "       WHERE id_arqueo = ? "; 
                /*"	 WHERE fecha =? " +
                "	 and id_arqueo = ? " +
                "	 and estado=? " +
                "	 and caja=? " +
                "	 and usuario_apertura=? ";*/
       List<DetalleVC> D=new ArrayList<>();
       try{           
           ps=con.prepareStatement(sql);
           //ps.setDate(1, c.getFechaApertura());
           ps.setInt(1, c.getId());
           //ps.setString(3, c.getEstado());
           //ps.setInt(4,c.getCaja() );
           //ps.setString(5, c.getUsuarioApertura());           
           rs=ps.executeQuery();
           ResultSetMetaData md=rs.getMetaData();
           int cl=md.getColumnCount();
           while(rs.next()){          
               DetalleVC dF=new DetalleVC();
               dF.setIdDetalle(rs.getInt("iddetalle"));
               dF.setIdVenta(rs.getInt("nrotran"));
               dF.setIdProducto(rs.getString("idproducto"));
               dF.setIdLote(rs.getString("nrolote"));
               dF.setCantidad(rs.getInt("cantidad"));
               dF.setPrecio(rs.getInt("precio"));
               dF.setTipoPago(rs.getInt("modopago"));
               dF.setGanancia(rs.getInt("totalganancia"));
               dF.setCancelado(rs.getInt("cancelado"));
               dF.setDescuento(rs.getInt("descuento"));
               
               D.add(dF);
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
       return (ArrayList<DetalleVC>) D;
   }
   //Traer Venta credito 
   //ArrayList <DetalleV>
   public boolean obtenerVC(Venta v) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM venta WHERE nroventa=? AND modopago=?";
       //String sql="SELECT * FROM venta v INNER JOIN detalleventa dv "
       //        + "ON t.nroventa=dv.nroventa WHERE t.nroventa=?";
       List<DetalleV> D=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, v.getNroFactura());
           ps.setInt(2, 2);
           rs=ps.executeQuery();           
           if(rs.next()){                  
               //DetalleV dF=new DetalleV();
               v.setNroFactura(rs.getInt("nroventa"));
               v.setIdCliente(rs.getString("idcliente"));
               v.setIdUsuario(rs.getString("idusuario"));
               v.setIdVendedor(rs.getString("idvendedor"));
               v.setFecha(rs.getDate("fecha"));
               v.setHora(rs.getTime("hora"));
               v.setNumCaja(rs.getInt("numcaja"));
               v.setTipoPago(rs.getInt("modopago"));
               v.setTotalF(rs.getInt("totalventa"));                 
               v.setGanancia(rs.getInt("ganancia"));
               v.setCancelado(rs.getInt("cancelado"));
               v.setDescuento(rs.getInt("descuento"));
               v.setTotalDescuento(rs.getInt("totalDescuento"));
               v.setTotalCobrar(rs.getInt("totalCobrar"));
               /*
               dF.setIdDetalle(rs.getInt("iddetalle"));dF.setIdVenta(rs.getInt("idticket"));
               dF.setIdProducto(rs.getString("idproducto"));dF.setIdLote(rs.getString("nrolote"));
               dF.setCantidad(rs.getInt("cantidad"));dF.setPrecio(rs.getInt("precio"));               
               D.add(dF);*/
            return true;
           }
           
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
       //return (ArrayList<DetalleV>) D;     
       return false;
   }
   public boolean modificarEstado(Venta v) throws SQLException{
       PreparedStatement ps=null;
       Connection con= getConexion();
       String sql="UPDATE venta SET cancelado=? WHERE nroventa=?";
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, 1);
           ps.setInt(2, v.getNroFactura());
           ps.executeUpdate();
           return true;
       }catch(SQLException e){
           JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
           return false;
       }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
   }
   public Venta obtenerDescuento(Venta v) throws SQLException 
   {
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con=getConexion();
       String sql="SELECT SUM(totaldescuento) total	FROM venta  " +
                    " where fecha=? and idusuario =?" +
                    " and id_arqueo=?; ";
       
       try{
           ps=con.prepareStatement(sql);
           ps.setDate(1, v.getFecha());
           ps.setString(2, v.getIdUsuario());
           ps.setInt(3, v.getIdArqueo());
           rs=ps.executeQuery();
           if(rs.next()){ 
               v.setTotalDescuento(rs.getInt("total"));               
           }
           return v;
       }catch(SQLException ex){
           System.err.println("Error al buscar descuento, "+ex);
       }finally{
           try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
       }
       return null;
   }
   //Obtener cabecera venta
   public Venta obtenerVCab(Venta v) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con=getConexion();
       String sql="SELECT * FROM venta  " +
                    " where nroventa=? ";
       
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, v.getNroFactura());           
           rs=ps.executeQuery();
           if(rs.next()){ 
             v.setFecha(rs.getDate("fecha"));
             v.setIdUsuario(rs.getString("idusuario"));
             v.setEstado(rs.getString("estado"));
             v.setIdTimbrado(rs.getInt("id_timbrado"));
             v.setNumCaja(rs.getInt("numcaja"));
             v.setIdVendedor(rs.getString("idvendedor"));
             v.setIdCliente(rs.getString("idcliente"));
             v.setHora(rs.getTime("hora"));
             v.setTipoPago(rs.getInt("modopago"));
             v.setTotalF(rs.getInt("totalventa"));
             v.setGanancia(rs.getInt("ganancia"));
             v.setCancelado(rs.getInt("cancelado"));             
             v.setDescuento(rs.getInt("descuento"));
             v.setTotalDescuento(rs.getInt("totalDescuento"));
             v.setTotalCobrar(rs.getInt("totalCobrar"));
           }
           return v;
       }catch(SQLException ex){
           System.err.println("Error al buscar venta, "+ex);
       }finally{
           try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
       }
       return null;
   }
   //Traer Venta y su detalle 
   public ArrayList <DetalleV> obtenerVDet(Venta t) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con = getConexion();  
       String sql="SELECT * FROM venta t INNER JOIN detalleventa dt "
               + "ON t.nroventa=dt.nroventa WHERE t.nroventa=?";
       List<DetalleV> D=new ArrayList<>();
       try{
           ps=con.prepareStatement(sql);
           ps.setInt(1, t.getNroFactura());
           rs=ps.executeQuery();           
            while(rs.next()){   
               //t.setIdFactura(rs.getInt("idticket"));
               DetalleV dF=new DetalleV();              
               dF.setIdDetalle(rs.getInt("iddetalle"));
               dF.setIdVenta(rs.getInt("nroventa"));
               dF.setIdProducto(rs.getString("idproducto"));
               dF.setIdLote(rs.getString("nrolote"));
               dF.setCantidad(rs.getInt("cantidad"));
               dF.setPrecio(rs.getInt("precio")); 
               dF.setLimite(0);
               D.add(dF);
           }           
       }catch(SQLException e){
            System.err.println("El Error: "+e);
            return null;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println("obtenerVDet:"+e);
            }
        }
       return (ArrayList<DetalleV>) D;
   }
   //Obtener cabecera venta
   public List<Venta> obtenerVenPorVendedor(Venta v) throws SQLException{
       PreparedStatement ps=null;
       ResultSet rs=null;
       Connection con=getConexion();
       String sql="SELECT * FROM venta  " +
                    " where idvendedor=? and fecha between ? and ? ";
       List<Venta> ventaList=new ArrayList<>(); 
       try{
           ps=con.prepareStatement(sql);
           ps.setString(1, v.getIdUsuario());
           ps.setDate(2, v.getFecha());
           ps.setDate(3, v.getFechaCierre());
           rs=ps.executeQuery();
           while(rs.next()){
               Venta  vt= new Venta();
             vt.setFecha(rs.getDate("fecha"));
             vt.setNroFactura(rs.getInt("nroventa"));
             vt.setIdUsuario(rs.getString("idusuario"));
             vt.setEstado(rs.getString("estado"));
             vt.setIdTimbrado(rs.getInt("id_timbrado"));
             vt.setNumCaja(rs.getInt("numcaja"));
             vt.setIdVendedor(rs.getString("idvendedor"));
             vt.setIdCliente(rs.getString("idcliente"));
             vt.setHora(rs.getTime("hora"));
             vt.setTipoPago(rs.getInt("modopago"));
             vt.setTotalF(rs.getInt("totalventa"));
             vt.setGanancia(rs.getInt("ganancia"));
             vt.setCancelado(rs.getInt("cancelado"));             
             vt.setDescuento(rs.getInt("descuento"));
             vt.setTotalDescuento(rs.getInt("totalDescuento"));
             vt.setTotalCobrar(rs.getInt("totalCobrar"));
             ventaList.add(vt);
           }
           
       }catch(SQLException ex){
           System.err.println("Error al buscar venta, "+ex);
           return null;
       }finally{
           try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
       }
       return ventaList;
   }

}
