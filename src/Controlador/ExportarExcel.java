/*
 Clase que modela proceso para exportar datos a formato excel
 */
package Controlador;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.Workbook;
import jxl.write.WriteException;

/**
 * @author JFAA
 * @version 1.0
 */
public class ExportarExcel {
    private File file;
    private List<JTable> tabla;
    private List<String> nomFile;
    private int c=0;

    /**
     * Constructor con parámetros
     * @param file contiene la ruta y el nombre del archivo
     * @param tabla contenido a escribir en el archivo
     * @param nomFile nombre de la hoja a escribir
     * @throws Exception en caso de ocurrir un error al momento de exportar el archivo.
     */
    public ExportarExcel(File file, List<JTable> tabla, List<String> nomFile) throws Exception {
        this.file = file;
        this.tabla = tabla;
        this.nomFile = nomFile;
        if(nomFile.size()!=tabla.size()){
            throw new Exception("Error");
        }
    }
    /**
     * Método para exportar los datos del Jtable a Excel
     * @param o Parametro que indica quien invoca al proceso de exportar
     * @return 'false'= no se pudo exportar, 'true'= exportación exitosa
     */
    public boolean expotar(int o){
        try{
            DataOutputStream out=new DataOutputStream(new FileOutputStream(file));
            WritableWorkbook w=Workbook.createWorkbook(out);
            for(int i=0;i<tabla.size();i++){
                JTable table=tabla.get(i);
                WritableSheet s=w.createSheet(nomFile.get(i),0);
                if(o==1){
                for(int x=1;x<table.getColumnCount()+1;x++){
                    if (x==1){s.addCell(new jxl.write.Label (x,0,String.valueOf("Vta Nro")));}
                    if (x==2){s.addCell(new jxl.write.Label (x,0,String.valueOf("Dlle Nro")));}
                    if (x==3){s.addCell(new jxl.write.Label (x,0,String.valueOf("Producto")));}
                    if (x==4){s.addCell(new jxl.write.Label (x,0,String.valueOf("Precio")));}
                    if (x==5){s.addCell(new jxl.write.Label (x,0,String.valueOf("Cantidad")));}
                    if (x==6){s.addCell(new jxl.write.Label (x,0,String.valueOf("Total")));}
                    for(int j=1;j<table.getRowCount()+1;j++){
                        //System.out.println(table.getValueAt(j, x));
                        Object objeto=table.getValueAt(j-1, x-1);
                        s.addCell(new jxl.write.Label (x,j,String.valueOf(objeto)));
                    }
                }}else{
                    for(int x=1;x<table.getColumnCount()+1;x++){
                        for(int j=1;j<table.getRowCount()+1;j++){
                            //System.out.println(table.getValueAt(j, x));
                            Object objeto=table.getValueAt(j-1, x-1);
                            s.addCell(new jxl.write.Label (x,j,String.valueOf(objeto)));
                        }
                    }
                }
            }
            w.write();
            w.close();
            return true;
        }catch (IOException | WriteException e){
            JOptionPane.showMessageDialog(null,"Error al exportar"+e);
            return false;
        }        
    }    
}
