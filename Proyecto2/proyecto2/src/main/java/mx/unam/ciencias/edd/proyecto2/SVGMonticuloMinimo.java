
package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.MonticuloMinimo;
import java.util.NoSuchElementException;


  /**
    *<p> Clase SVGArbolBinarioCompleto</p>
    *
    *<p>Permite escribir el archivo donde se establece el código según la clase
    *solicitada </p>
    *
    *
    *@author Ian García
    *@since 10/03/2020
    *@version 1.0
    **/

  public class SVGMonticuloMinimo{




    /**
    *SVGArbolBinarioCompleto nos permite instaciar dicha clase para gráficar
    *el monticulo minimo
    **/
      SVGArbolBinarioCompleto abc;

      /**
      *Constructor de SVGMonticulosMinimos
      *@param enteros lista de enteros
      **/
    SVGMonticuloMinimo(Lista<Integer> enteros){ // El contructor solo manda a llamar a su padre

      abc= new SVGArbolBinarioCompleto(Lista.mergeSort(enteros));

    }
      /**
      *Muestra en la salida estandar el código del árbol
      *
      **/
      public void transcribe(){
        abc.transcribe();
      }









  }
