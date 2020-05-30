
package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import java.util.NoSuchElementException;


  /**
    *<p> Clase SVGArbolAVL</p>
    *
    *<p>Permite escribir el archivo donde se establece el código según la clase
    *solicitada </p>
    *
    *
    *@author Ian García
    *@since 10/03/2020
    *@version 1.0
    **/

  public class SVGArbolAVL extends ArbolAVLsuper{

    /**
    *<p>Constructor de ArbolSVG</p>
    *@param enteros
    *
    */
    SVGArbolAVL(Lista<Integer> enteros){ // El contructor solo manda a llamar a su padre
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
        return super.recorridoDfs();
      }









  }
