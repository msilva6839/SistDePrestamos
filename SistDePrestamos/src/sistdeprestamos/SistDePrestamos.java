/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistdeprestamos;
import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;
/**
 *
 * @author symq9485
 */

class Solicitante
{
    int cedula;
    String nombre; 
    String apellido1;
    String apellido2;
    int telf;
    int movil;
    
    
    void registrar() throws IOException 
    {
        BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
        try
        {            
        System.out.print("Ingrese la cedula del solicitante: ");
        cedula = Integer.parseInt(br.readLine());
        
        System.out.print("Ingrese el nombre del solicitante: ");
        nombre = br.readLine();
        
        System.out.print("Ingrese el primer apellido del solicitante: ");
        apellido1 = br.readLine();
        
        System.out.print("Ingrese el segundo apellido del solicitante: ");
        apellido2 = br.readLine();
        
        
        System.out.print("Ingrese el numero de telefono de habitacion del solicitante: ");
        telf = Integer.parseInt(br.readLine());
        
        System.out.print("Ingrese numero de telefono movil del solicitante: ");
        movil = Integer.parseInt(br.readLine());
    
        }
        catch(IOException e)
        {
            System.err.println("Error: " + e.getMessage());
        }
    }
    @Override
    public String toString() 
    {
        return "Nombre: "+ nombre +" Apellidos: "+ apellido1 + " " + apellido2
                + "Cedula: "+cedula
                + "Telefono fijo: "+telf
                + "Movil: "+ movil;
    }
}    

public class prestamo {
    
    int N_Prestamo;
    int i;
    double Credito;
    double Valor_de_prestamo;
    boolean siguiente;
    String seguir;
    String[] Fecha_pagos = new String[6];
    
    void calendario() throws IOException
    {
        //Prestamo tendra que ser leido desde un archivo para seguir una secuencia
       
        Calendar c2 = GregorianCalendar.getInstance();
        Calendar c3 = GregorianCalendar.getInstance();
        
        c2.add(Calendar.DATE,7);
        c3.set(Calendar.DATE,20);
             
        if (c2.DATE < c3.DATE) // chequea si la fecha de autorizacion es mayor que fecha tope//
        {
            
            for(int i = 1; i <= 6; i++)
            {
                c2.add(Calendar.MONTH, (i));
                Fecha_pagos[i] =c2.toString() + " " + Valor_de_prestamo/6;

            }
        }
        
        else
        {
            System.out.print("La fecha de aprobacion exede los primeros 20 dias del mes");
        }
      
    }
    void calcular(){
        
        BufferedReader br = new BufferedReader (new InputStreamReader(System.in));

            try
            {
                System.out.print("Ingrese el valor del prestamo a solicitar");
                while (siguiente == true && Valor_de_prestamo < Credito) {
                    Credito=1000000.00d;
                    N_Prestamo = N_Prestamo + 1;
                    Valor_de_prestamo = Double.parseDouble(br.readLine());
                    Valor_de_prestamo = Valor_de_prestamo + Valor_de_prestamo;
                    if (Valor_de_prestamo >= Credito) {
                        System.out.println("Usted se ha excedido el limite");
                        break;
                    }
                    System.out.println("Quiere hacer otro prestamo? S/N");
                    seguir = br.readLine();
                    if (seguir=="S"){
                      siguiente = true;  
                    }
                }

            }
            catch(IOException e)
            {
                System.err.println("Error: " + e.getMessage());
            }
    }  
    
}
