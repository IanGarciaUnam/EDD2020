
package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.AccionVerticeArbolBinario;
import mx.unam.ciencias.edd.VerticeArbolBinario;


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

  public class SVGArbolRojiNegro extends SVGArbol{

    private ArbolRojinegro<Palabra> arbol;
    private int tamanoAlto;
    private int tamanoAncho;
    private Lista<Palabra> palabra;


    /**
    *Constructor de SVGArbolRojinegro
    *@param palabra Lista de enteros
    **/
    SVGArbolRojiNegro(Lista<Palabra> palabra){
      super(palabra);
    }


      /**
      *Muestra en la salida estandar el código del árbol
      *@return wrote codigo del ArbolRojinegro
      **/
      public String transcribe(){

        String wrote=elige("ARN");
        return wrote;
      }

      private String getCode(){
       Lista<VerticeArbolBinario<Palabra>> lista = new Lista<>();
       lista=getListaDfs();
       String programa= recorridoDfsRojiNegro(lista);
       return programa;

      }









  }
