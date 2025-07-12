/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ConsultaCliente;
import Modelo.ConsultaEmp;
import Modelo.ConsultaProducto;
import Modelo.Empresa;
import Modelo.Persona;
import Modelo.Producto;
import Modelo.Venta;
import br.com.adilson.util.Extenso;
import br.com.adilson.util.PrinterMatrix;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.text.NumberFormat;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;

/**
 *
 * @author JFAA
 */
public class ImprimirFactura {
    //private final Image imagen;
    
    //public ImprimirFactura() throws IOException{
        //getClass().getResource("img\\logo.png");
        //this.imagen = ImageIO.read(new File("img\\logo.png"));
        
    //}
        
    void imprimirFactura(Venta tic, TableModel detalle, Empresa emp, int tipo) throws SQLException{
    
    ConsultaCliente conCli=new  ConsultaCliente();
    Persona cli=new Persona();
    Producto pr=new Producto();
    ConsultaProducto conPr=new ConsultaProducto();
    ConsultaEmp conEmp=new ConsultaEmp();
    //byte[] cutPaper = new byte[] { 0x1D, 'V', 1 };//\x1D\x56\x00
    //buscar datos de la empresa
    if (conEmp.buscarId(emp)!= null){
        System.out.println("Datos de empresa encontrados...");
    }else{
        JOptionPane.showMessageDialog(null, "Empresa con ID="+emp.getIdEmpresa()+" no existe en la Base de Datos...");
    }
    //tipo de comprobante
    int nro=0;
    if (tipo == 1){
        nro=tic.getNroFactura();
    }else{
        nro=tic.getNroPresupuesto();
    }
    //tipo venta
    String tipo_pago="";
    if (tic.getTipoPago() == 1){
        tipo_pago="CONTADO";
    }else{
        tipo_pago="CREDITO";
    }
    cli.setCi(tic.getIdCliente());
    conCli.buscar(cli);
    int filas =  detalle.getRowCount();
        PrinterMatrix printer = new PrinterMatrix();

        Extenso e = new Extenso();

        e.setNumber(101.85);
        //Definir el tamanho del papel para la impresion  aca 32 lineas y 42 columnas
        int largo=18+(filas*2)+8;
        printer.setOutSize(largo, 48);
        //Imprimir * de la 2da linea a 25 en la columna 1;
       // printer.printCharAtLin(2, 25, 1, "*");
        //Imprimir * 1ra linea de la columa de 1 a 40
       //printer.printCharAtCol(1, 1, 35, "=");
       printer.printTextWrap(1, 1, 1, 47, "---------------------------------------------");
        //Imprimir Encabezado nombre del La EMpresa        
       printer.printTextWrap(2, 2, 10, 47, "Motorepuestos y Lubricentro");         
       printer.printTextWrap(3, 3, 15, 47, emp.getNombre());
       printer.printTextWrap(4, 4, 15, 47, "Tel.:"+emp.getTelefono());
       printer.printTextWrap(5, 5, 15, 47, "RUC:"+emp.getRucempresa());
       printer.printTextWrap(6,6,10,47,emp.getDireccion());
       printer.printTextWrap(7, 7, 1, 47, "---------------------------------------------");
       printer.printTextWrap(8, 8, 10, 47, "COMPROBANTE DE VENTA "+tipo_pago);
       //printer.printTextWrap(linI, linE, colI, colE, null);
       printer.printTextWrap(9, 9, 1, 47, "Num. Boleta : 001-001-" + formatoNroFac(nro));
       printer.printTextWrap(10, 10, 1, 47, "Fecha de Emision: " + tic.getFecha());
       printer.printTextWrap(11, 11, 1, 47, "Hora:"+tic.getHora());
       printer.printTextWrap(12, 12, 1, 47, "Vendedor: "+tic.getIdUsuario());
       printer.printTextWrap(13, 13, 1, 47, "Cliente: " +cli.getNombre()+" "+cli.getApellido());
       printer.printTextWrap(14, 14, 1, 47, "Ruc/Ci.: " + cli.getRuc());
       printer.printTextWrap(15, 15, 1, 47, "Direccion: " + cli.getDireccion());
       //printer.printCharAtCol(11, 1, 35, "-");
       printer.printTextWrap(16, 16, 1, 47, "----------------------------------------------");
       printer.printTextWrap(17, 17, 1, 47, "Codigo___Descripcion__Cantidad_PUnitario_Total");
       printer.printTextWrap(18, 18, 1, 47, "----------------------------------------------");
       //printer.printCharAtCol(12, 1, 35, "-");       
       long total = 0;
       int inicioPie = filas;       
        for (int i = 0; i < filas; i++) {
            long cant=Integer.valueOf(detalle.getValueAt(i, 5).toString());
            long precio=Integer.valueOf(detalle.getValueAt(i, 4).toString());
            String codigo=detalle.getValueAt(i, 0).toString();
            pr.setCodigo(codigo);
            conPr.buscar(pr);
            if(i==0){
                String texto1=codigo+"_"+pr.getDescripcion();
                printer.printTextWrap(19 + i, 19+i+1, 1, 47, Descripcion(texto1)+"_");
                printer.printTextWrap(20 + i, 20+i+1, 1, 47, cant+"*"+formateoMiles(precio)+"="+formateoMiles(cant*precio));
            }
            else{
                String texto1=codigo+"_"+pr.getDescripcion();
                printer.printTextWrap(19 + i*2, 19+i*2+1, 1, 47, Descripcion(texto1)+"_");
                printer.printTextWrap(20 + i*2, 20+i*2+1, 1, 47, cant+"*"+formateoMiles(precio)+"="+formateoMiles(cant*precio));
            }
           total=total+(cant*precio); 
           inicioPie+=1;
        }
        //if(filas > 16){
        //printer.printCharAtCol(filas + 1, 1, 80, "=");
        printer.printTextWrap(19+inicioPie + 1, 1, 1, 47, "-----------------------------------------------");
        // Obtener una instancia de NumberFormat para el formato actual del sistema
        NumberFormat numberFormat = NumberFormat.getInstance();
        // Formatear el número con separadores de miles
        String totalF = numberFormat.format(total);
        printer.printTextWrap(19+inicioPie + 2, 1, 1, 47, "TOTAL A PAGAR: " + totalF +" Gs.");
        //printer.printCharAtCol(filas + 2, 1, 30, "=");
        printer.printTextWrap(19+inicioPie + 3,19+inicioPie + 4 , 1,47, "Boleta sin valor fiscal, solo para uso interno");        
        printer.printTextWrap(21+inicioPie + 3,21+inicioPie + 4 , 1,47, "Muchas gracias por su preferencia...");        
        //}else{
        //printer.printCharAtCol(25, 1, 30, "=");
       // printer.printTextWrap(26, 26, 1, 30, "TOTAL A PAGAR " + total);
        //printer.printCharAtCol(27, 1, 30, "=");
       // printer.printTextWrap(27, 28, 1, 30, "Est boleta no tiene valor fiscal, solo para uso interno.: + Descripciones........");

        //}        
        printer.toFile("C:\\Users\\avc\\OneDrive\\Documentos\\IntegralServices\\impresion.txt");    
        FileInputStream inputStream = null;
        try {            
            inputStream = new FileInputStream("C:\\Users\\avc\\OneDrive\\Documentos\\IntegralServices\\impresion.txt");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        if (inputStream == null) {
            return;
        }

        DocFlavor docFormat = DocFlavor.INPUT_STREAM.AUTOSENSE;
        Doc document = new SimpleDoc(inputStream, docFormat, null);

        PrintRequestAttributeSet attributeSet = new HashPrintRequestAttributeSet();

        PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();


        if (defaultPrintService != null) {
            DocPrintJob printJob = defaultPrintService.createPrintJob();
            try {
                printJob.print(document, attributeSet);                
                cortar();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            System.err.println("No existen impresoras instaladas");
        }

        //inputStream.close();

    }
    public String formatoNroFac(int nro){
        String nroFac = String.valueOf(nro);
         char [] cantCaract=nroFac.toCharArray();
         int cantCrt = cantCaract.length;
         //System.out.println("La cantidad "+cantCrt);
         int cantFaltante = 7- cantCrt;
         for(int i=0;i<cantFaltante;i++){
             nroFac = '0'+nroFac;
         }
         return nroFac;
    }
    
    void imprime(){
            /*imprime una imagen en la impresora predefinida.*/
        //public static void printIMG() {
            try {
                PrintService ps = PrintServiceLookup.lookupDefaultPrintService();
                DocPrintJob job = ps.createPrintJob();
                DocFlavor DF = DocFlavor.INPUT_STREAM.JPEG;
                FileInputStream FIS = new FileInputStream("C:/SistResto/qr.jpeg");
                Doc doc = new SimpleDoc(FIS, DF, null);
                PrintRequestAttributeSet attrib = new HashPrintRequestAttributeSet();
    //          attrib.add(new Copies(1));  
    //          job.print(doc, attrib);
                job.print(doc, null);
            } catch (Exception e) {
                e.printStackTrace();
            }       
        //}
        
    }
    public void cortar(){
        // Paper Cut
       //byte[] cutPaper = new byte[] { 0x1d, 'V', 1 };
       CtrlPruebaImprimir ctrlPI=new CtrlPruebaImprimir();
       ctrlPI.printMain();
       
    }
    public String formateoMiles(long total){
        // Obtener una instancia de NumberFormat para el formato actual del sistema
        NumberFormat numberFormat = NumberFormat.getInstance();
        // Formatear el número con separadores de miles
        String totalF = numberFormat.format(total);
        return totalF;
    }
    public String Descripcion(String descripcion){
        int cant=descripcion.length();
        if (cant>45) {
            descripcion=descripcion.substring(0,45);
        }
        return descripcion;
    }
}
