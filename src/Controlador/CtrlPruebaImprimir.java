/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

/**
 *
 * @author JFAA
 */
//public class CtrlPruebaImprimir {
    
//}
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class CtrlPruebaImprimir {

    /*public static void main(String[] args) {
        // Cadena de comandos ESC/POS para cortar el papel
        byte[] cutPapel = new byte[]{0x1D, 'V', 1};

        // Texto que deseas imprimir
        String textoAImprimir = "...\n";

        // Combinar los comandos de impresión y corte
        byte[] datosDeImpresion = unirBytes(textoAImprimir.getBytes(StandardCharsets.UTF_8), cutPapel);

        // Enviar a la impresora
        enviarAImpresora(datosDeImpresion);
    }*/

    // Método para unir dos arreglos de bytes
    private static byte[] unirBytes(byte[]... arrays) {
        int longitudTotal = 0;
        for (byte[] array : arrays) {
            longitudTotal += array.length;
        }

        byte[] resultado = new byte[longitudTotal];
        int offset = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, resultado, offset, array.length);
            offset += array.length;
        }

        return resultado;
    }

    // Método para enviar datos a la impresora
    private static void enviarAImpresora(byte[] datos) {
        try {
            // Obtener la impresora por nombre (puedes cambiar "Your Printer Name" al nombre de tu impresora)
            PrintService service = PrintServiceLookup.lookupDefaultPrintService();
            if (service != null) {
                // Crear un flujo de entrada desde los datos de impresión
                InputStream inputStream = new ByteArrayInputStream(datos);

                // Crear un documento con el flujo de entrada y el formato
                DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
                Doc doc = new SimpleDoc(inputStream, flavor, null);

                // Crear atributos de impresión
                PrintRequestAttributeSet attrs = new HashPrintRequestAttributeSet();

                // Obtener el trabajo de impresión
                DocPrintJob printJob = service.createPrintJob();

                // Imprimir el documento
                printJob.print(doc, attrs);
            } else {
                System.err.println("Impresora no encontrada.");
            }
        } catch (PrintException e) {
            e.printStackTrace();
        }
    }
    public void printMain ()
    {
        // Cadena de comandos ESC/POS para cortar el papel
        byte[] cutPapel = new byte[]{0x1D, 'V', 1};

        // Texto que deseas imprimir
        String textoAImprimir = "...\n";
        //String textoAImprimir = "";
        // Combinar los comandos de impresión y corte
        byte[] datosDeImpresion = unirBytes(textoAImprimir.getBytes(StandardCharsets.UTF_8), cutPapel);

        // Enviar a la impresora
        enviarAImpresora(datosDeImpresion);                
    }
}

