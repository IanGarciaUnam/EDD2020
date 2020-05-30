
package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.AccionVerticeArbolBinario;
import mx.unam.ciencias.edd.VerticeArbolBinario;
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

  public class SVGArbolRojiNegro extends ArbolColor{

    private ArbolRojinegro<Integer> arbol;
    private int tamanoAlto;
    private int tamanoAncho;


    /**
    *Constructor de SVGArbolRojinegro
    *
    *@param enteros Lista de enteros
    **/
    SVGArbolRojiNegro(Lista<Integer> enteros){
      super(enteros);

    }
      /**
      *Muestra en la salida estandar el código del árbol
      *
      **/
      public void transcribe(){

        String wrote=super.codea(getCode());
        System.out.println(wrote);
      }

      private String getCode(){
       Lista<VerticeArbolBinario<Integer>> lista = new Lista<>();
       lista=getListaDfs();
       String programa= recorridoDfs(lista);
       return programa;

      }









  }
