package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
  /**
    *<p> Clase Escribano</p>
    *
    *<p>Permite escribir el archivo donde se establece el código según la clase
    *solicitada </p>
    *
    *
    *@author Ian García
    *@since 08/04/2020
    *@version 3.0
    **/

  public class Escribano{
    /**tipo de Clase**/
    private String tipoDeClase;
    /**Lista de palabras**/
    private Lista<Palabra> palabra;

    /**
    *Constructor para ubicar el tipo de SVG a escribir 
    *@param tipoDeClase tipo de SVG a escribir
    *@param palabra Lista de palabras
    */
      Escribano(String tipoDeClase, Lista<Palabra> palabra){
        this.tipoDeClase=tipoDeClase;
        this.palabra=palabra;
      }
      /**
      *Su trabajo especifico es compraobar la existencia del dicho archivo
      * y escribir en el archivo el código correspondiente para crear una imagen svg
      *@return código de Arboles en SVG
      */

      public String escribe(){
        switch(tipoDeClase){
          case "ArbolRojinegro":
                  SVGArbolRojiNegro arn = new SVGArbolRojiNegro(palabra);
                  return arn.transcribe();
                  
         case "ArbolAVL":
                SVGArbolAVL avl = new SVGArbolAVL(palabra);
                   return avl.transcribe();
                   
          default:
              return "";

          }

        }












}
