package mx.unam.ciencias.edd.proyecto1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import mx.unam.ciencias.edd.Lista;

import mx.unam.ciencias.edd.proyecto1.proyecto1;

    /**
    *<p> Clase Entradas</p>
    *<p> Esta clase nos permite manejar el tipo de entrada seleccionado por el
    *usuario ya sea la entrada Standar o la entrada por argumentos</p>
    *
    *@author Ian García
    *@since  18/03/2020
    *@version 1.0
    **/



public class Entradas{
/** Lista con elementos string  con los nombres de los archivos**/
  Lista<String> files;
  /** Lista con elementos cadena  con el contenido de los archivos*/
  Lista<Cadena> content;
  /**booleano que indica si se activo la bandera de reversa -r**/
  boolean reversa;
  /**booleano que indica si se activo la bandera de reversa -r**/
  boolean destructiva;


  /**
  *<p>Constructor de la clase Entradas</p>
  *Nos permite Construir ambos tipos de Entradas
  *@param files Lista de nombres de archivo tipo String
  *@param content Lista de contenido de cada uno de los archivos
  *@param reversa variable booleana de la bandera reversa
  *@param destructiva variable booleana de la bandera de Operación destructiva -o
  */
  Entradas(Lista<String> files, Lista<Cadena> content,boolean reversa, boolean destructiva ){
    this.files= files;
    this.content=content;
    this.reversa=reversa;
    this.destructiva=destructiva;
  }

  /**
  *<p> ingresa los archivos de la entrada standard y finalmente son impresos y/o escritos</p>
  *@throws IOException
  *
  */

  public void entradaArgs()throws IOException{
    String show;
    BufferedReader br=null;
    try{


      if(!destructiva){


        while(files.getElementos()>0){
          br=new BufferedReader(new FileReader(files.eliminaPrimero()));

            while((show=br.readLine()) != null){
              Cadena c= new Cadena(show);
              content.agrega(c);
            }

          }

    }else if(destructiva && proyecto1.getGrande()>1){

      int counter= proyecto1.getGrande();

      Lista<String> subFiles= new Lista<>();
      while(counter >0){
        subFiles.agrega(files.eliminaPrimero());
        counter--;
      }

      while(subFiles.getElementos()>0){
         br=new BufferedReader(new FileReader(subFiles.eliminaPrimero()));
          while((show=br.readLine()) != null){
            Cadena c= new Cadena(show);
            content.agrega(c);
          }

      }

    }else if(proyecto1.getGrande()==1){

       br=new BufferedReader(new FileReader(files.eliminaPrimero()));
       while((show=br.readLine()) != null){
         Cadena c= new Cadena(show);

         content.agrega(c);
       }

      }

      operacionAuxArgs(reversa, destructiva);




          content.limpia();

    }catch(IOException io){
      System.out.println("*************Archivo dañado o ilegible ***********");
      System.err.println("Para más ayuda  java -jar target/proyecto1.jar --h");
    }



  }

  private void operacionAuxArgs(boolean reversa, boolean destructiva){
    if(reversa && !destructiva){
    Lista<Cadena> l=Lista.mergeSort(content);
    while(l.getElementos()>0){
      System.out.println(l.eliminaUltimo().toString());
    }

    }if(destructiva && !reversa){
      if(files.getElementos()==0){
        System.err.println("Necesitas introducir un identificador despues de la bandera -o");
        System.err.println("Para más ayuda  java -jar target/proyecto1.jar --h");
        return;
      }
        Lista<Cadena> l=Lista.mergeSort(content);

        Escribano e = new Escribano(files.eliminaPrimero(), l);
        e.escritor();
    }

    if(destructiva && reversa){
      if(files.getElementos()==0){
        System.err.println("Necesitas introducir un identificador despues de la bandera -o");
        System.err.println("Para más ayuda  java -jar target/proyecto1.jar --h");
        return;
      }

        Lista<Cadena> l=Lista.mergeSort(content);

        Escribano e = new Escribano(files.eliminaPrimero(), l.reversa());
          e.escritor();
    }else if(!reversa && !destructiva){
      Lista<Cadena> l=Lista.mergeSort(content);



      while(l.getElementos()>0){
        System.out.println(l.eliminaPrimero().toString());
      }
    }
  }

  /**
  *Ingresa los archivos y su contenido para ser leído y ordenado correctamente
  *@throws IOException
  */
  public  void entradaEstandar()throws IOException{
    String show;

      try{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        if(!br.ready()){
        System.out.println("Archivo no Legible o entrada vacía");
        System.err.println("Para más ayuda  java -jar target/proyecto1.jar --h");
        return;
        }

        while((show=br.readLine()) != null){
          Cadena c = new Cadena(show);
          content.agrega(c);
        }

        if(content.getElementos()==0){
          System.out.println("No hay elementos para realizar la acción");
          System.err.println("Para más ayuda  java -jar target/proyecto1.jar --h");
          return;
        }

        operacionAuxStd(reversa, destructiva);

        files.limpia();
        content.limpia();
        br.close();

    }catch(IOException ioe){
        System.out.println("Falla en la entrada estandar -- Posible Archivo Inexistente o Ilegible");
      }


  }


  private void operacionAuxStd(boolean reversa, boolean destructiva){
    if(reversa && !destructiva){
    Lista<Cadena> l=Lista.mergeSort(content);
    while(l.getElementos()>0){
      System.out.println(l.eliminaUltimo().toString());// se recorre la lista de atrás hacia delante
    }

  }else if( !reversa && destructiva){
    if(files.getElementos()==0){
      System.out.println("No existe un archivo para escribir");
      return;
    }
      Lista<Cadena> l=Lista.mergeSort(content);
      while(files.getElementos()>0){
            Escribano e = new Escribano(files.eliminaPrimero(), l);
          e.escritor();
      }

      while(l.getElementos()>0){
        System.out.println(l.eliminaPrimero().toString());
      }

    }else if(reversa && destructiva){
        Lista<Cadena> l=Lista.mergeSort(content);
        while(files.getElementos()>0){

            Escribano e = new Escribano(files.eliminaPrimero(), l.reversa());
            e.escritor();
          }


    }else if(!reversa && !destructiva){

         Lista<Cadena> l=Lista.mergeSort(content);
          while(l.getElementos()>0){
            System.out.println(l.eliminaPrimero().toString());
          }
        }
  }



}
