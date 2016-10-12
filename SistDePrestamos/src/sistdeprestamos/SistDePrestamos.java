/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sistdeprestamos;
import java.io.*;
import java.util.Arrays;
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
    String telf;
    String movil;
    
    
    void registrar() throws IOException 
    {
        BufferedReader br = new BufferedReader (new InputStreamReader(System.in));
        
        boolean x = true;
        
        while (x != false)
        {
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
            telf = br.readLine();

            System.out.print("Ingrese numero de telefono movil del solicitante: ");
            movil = br.readLine();
            
            x=false;
            
            }

            catch(Exception e)
            {
                System.err.println("Se produjo un Error: " + e.getMessage() + ".Por favor vuelva a ingresar los datos");
                //x = true;
            }
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

class Prestamo {

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

        if (c2.DATE < c3.DATE) // chequea si la fecha de autorizaciones mayor que fecha tope//
        {

            for(int i = 1; i <= 6; i++)
            {
                c2.add(Calendar.MONTH, (i));
                Fecha_pagos[i] =c2.toString() + " " + Valor_de_prestamo/6 + " " + N_Prestamo;

            }
        }

        else
        {
            System.out.print("La fecha de aprobacion exede los primeros 20 dias del mes");
        }

    }
    void calcular()
    {

        BufferedReader br = new BufferedReader (new InputStreamReader(System.in));

        boolean x = true;
        
        while(x!=false)
        {
            try
            {
                System.out.print("Ingrese el valor del prestamo a solicitar");
                while (siguiente == true && Valor_de_prestamo < Credito) 
                {
                    Credito=1000000.00d;
                    N_Prestamo = N_Prestamo + 1;
                    Valor_de_prestamo = Double.parseDouble(br.readLine());
                    Valor_de_prestamo = Valor_de_prestamo + Valor_de_prestamo;

                    if (Valor_de_prestamo >= Credito) 
                    {
                        System.out.println("Usted se ha excedido el limite");
                        break;
                    }

                    System.out.println("Quiere hacer otro prestamo? S/N");

                    seguir = br.readLine();

                    if (seguir == "S")
                    {
                      siguiente = true;
                    }
                }
                
                x = false;
            }
            catch(Exception e)
            {
                System.err.println("Se produjo un Error: " + e.getMessage() + ".Por favor vuelva a ingresar los datos");
                //x = true
            }
        }
    }

    public String capturar() 
    {
        return "Fecha de pagos: " + Arrays.toString(Fecha_pagos);
    }
}

class Archivo
{
    PrintWriter pf;
    FileReader fr;
    
    void crear(String pSolici, String pPresta) throws IOException
    {
        pf = new PrintWriter(new FileWriter(pSolici, true));
        pf.println(pSolici);
        pf.println(pPresta);
        pf.close();
        System.out.println("El archivo fue creado correctamente.");
    }
    
    void leer (String pSolici) throws IOException
    {
        fr = new FileReader(pSolici);
        BufferedReader br = new BufferedReader(fr);
        String linea;
        while((linea = br.readLine())!=null)
        {
            String[]datos=new String[4];
            datos = linea.split("");
            for(int i = 0; i <= datos.length-1;i++)
            {
                System.out.println(datos[i]);
            }
        }
    }
}

public class SistDePrestamos 
{

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException 
    {
        // TODO code application logic here
        Solicitante sol = new Solicitante();
                sol.registrar();
    }
    
}