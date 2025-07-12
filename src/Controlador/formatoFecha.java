/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 *
 * @author JFAA
 */
public class formatoFecha {
    public Date formatoFecha( java.util.Date fecha){
        //Formatear la fecha para poder guardarlo en una base de datos                  
        SimpleDateFormat formatofecha = new SimpleDateFormat("yyyy-MM-dd");                             
        java.sql.Date f=java.sql.Date.valueOf(formatofecha.format(fecha));
        return f;
    }
    public Time hora(){
        LocalDateTime locaDate = LocalDateTime.now();
        int hours  = locaDate.getHour();
        int minutes = locaDate.getMinute();
        int seconds = locaDate.getSecond();
        System.out.println("Hora actual : " + hours  + ":"+ minutes +":"+seconds); 
        Time t= Time.valueOf(hours  + ":"+ minutes +":"+seconds);
        return t;
    }
    public Date fecha(){
        Calendar calendario = Calendar.getInstance();
        int y,m,d;
        d = calendario.get(Calendar.DAY_OF_MONTH);
        m = calendario.get(Calendar.MONTH)+1;
        y = calendario.get(Calendar.YEAR);
        String fec= y+"-"+m+"-"+d;
        System.out.println(fec);
        Date f=Date.valueOf(fec);
        return f;
    }
    
}
