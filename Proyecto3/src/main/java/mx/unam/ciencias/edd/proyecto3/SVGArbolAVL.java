
package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.ArbolAVL;
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

  public class SVGArbolAVL extends SVGArbol{

    ArbolAVL <Palabra> arbol=new ArbolAVL<>();
    /**
    *<p>Constructor de ArbolSVG</p>
    *@param enteros
    *
    */
    SVGArbolAVL(Lista<Palabra> palabra){ // El contructor solo manda a llamar a su padre
      super(palabra);

    }
      /**
      *Muestra en la salida estandar el código del árbol
      *@return c codigo de arbolAVL
      **/
      public String transcribe(){
        String c=elige("AVL");
        return c;

      }

      private String getCode(){
        return super.recorridoDfsAVL();
      }









  }
