package Controlador;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author JFAA
 */
public class Tiempo {
    Calendar fecha=new GregorianCalendar();
    
    //obtener la fecha del sistema
    String aa=Integer.toString(fecha.get(Calendar.YEAR));
    //Se suma "1" al mes porque al extaer el mes trae 1 menos que el mes actual
    String MM=Integer.toString(fecha.get(Calendar.MONTH)+1);
    String dd=Integer.toString(fecha.get(Calendar.DATE));
    String fechaA=aa+"-"+MM+"-"+dd;
    //obtener hora del Sistema
    String hh=Integer.toString(fecha.get(Calendar.HOUR_OF_DAY));
    String mm=Integer.toString(fecha.get(Calendar.MINUTE));
    String ss=Integer.toString(fecha.get(Calendar.SECOND));
    
    //Concatenar la hora/minuto
    String hora=hh+":"+mm+":"+ss;
}
