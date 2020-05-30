package mx.unam.ciencias.edd.proyecto1;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.File;

import mx.unam.ciencias.edd.Lista;


  /**
    *<p> Clase Escribano</p>
    *
    *<p> En caso del lanzamiento de la bandera -o, es necesario que el texto ordenado sea
    *sea escrito en un archivo con el nombre pasado por el identificador es una implementación destructiva
    *
    *@author Ian García
    *@since 26/03/2020
    *@version 1.0
    **/

  public class Escribano{

    private String archivo_Nombre;
    private Lista<Cadena> nueva;
      Escribano(String archivo_Nombre, Lista<Cadena> nueva){
        this.archivo_Nombre=archivo_Nombre;
        this.nueva=nueva;
      }


      /**
      *Su trabajo especifico es compraobar la existencia del dicho archivo
      * y escribir en el archivo nombrado igual que el identificador
      */

      public void escritor(){

        File f = new File(archivo_Nombre);
        if(f.exists())
          f.delete();
          else{
            System.out.println("Archivo Inexistente");
            System.err.println("Para más ayuda  java -jar target/proyecto1.jar --h");
            return;
          }
          try{
            f.createNewFile();

        if(!f.canWrite()){
          System.out.println("No cuenta con los permisos de escritura necesarios");
          return;
        }



        BufferedWriter bw = new BufferedWriter(new FileWriter(archivo_Nombre));
        
          while(nueva.getElementos()>0){

            bw.write(nueva.eliminaPrimero().toString());
            bw.newLine();
            bw.flush();

          }
          //Impresion del escrito realizado
              String pencil="";
            BufferedReader br = new BufferedReader(new FileReader(archivo_Nombre));
            while((pencil=br.readLine())!= null){
              System.out.println(pencil);
            }
      }catch(IOException ioe){
        System.out.println("Error al intentar escribir el archivo" + ioe);
      }

  }












}
