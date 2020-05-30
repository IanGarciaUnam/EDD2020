
package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Grafica;
import java.util.NoSuchElementException;


  /**
    *<p> Clase SVGGrafica</p>
    *
    *<p>Permite escribir el archivo donde se establece el código según la clase<Graficas>
    *solicitada </p>
    *
    *
    *@author Ian García
    *@since 29/04/2020
    *@version 1.0
    **/

  public class SVGGrafica extends GraficaSuper{


    /**
    *contructor de SVGGrafica
    *@param enteros lista de enteros
    **/
    SVGGrafica(Lista<Integer> enteros){ // El contructor solo manda a llamar a su padre
        super(enteros);
    }

      /**
      *Muestra en la salida estandar el código del árbol
      *
      **/
      public void transcribe(){
        String c=codea(getCode());
        System.out.println(c);
      }

      private String getCode(){
        return super.getGrafica();
      }

  }
