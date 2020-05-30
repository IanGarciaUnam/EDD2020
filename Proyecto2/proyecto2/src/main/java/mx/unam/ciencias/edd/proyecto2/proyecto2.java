package mx.unam.ciencias.edd.proyecto2;

import java.util.Random;
import java.text.NumberFormat;
import java.io.IOException;
import java.util.NoSuchElementException;

  /**
  *<p> Proyecto 2 - graficador de estructuras de datos</p>
  *Clase principal del acceso al programa proyecto 2
  *
  **/
  public class proyecto2{


      public static void main(String[] args) {


      if(args.length>1){
        System.out.println("No puedes ingresar más de un archivo");
        return;
      }

      if(args.length == 1){

        if(args[0].equals("--h")){
          Entradas.impresionAyuda();
          return;
        }

          Entradas e = new Entradas(args[0]);
          e.entradaArgs();
          }else{

          Entradas e = new Entradas();
          e.entradaEstandar();


        }




    }




}
