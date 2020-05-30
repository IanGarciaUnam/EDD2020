package mx.unam.ciencias.edd.proyecto2;
import mx.unam.ciencias.edd.Lista;
  /**
  *<p>Interface para Lista Doble Enlazada y/o comparables de su tipo diferentes a Arboles
  * </p>
  *
  *<p>Estas no aceptan elementos diferentes a Integer, dichos elemetos son contenidos
  * en una lista que recibe solo Integer  El objetio de estas clases es convertir las estructuras de
  *datos en código svg para ser visibilizadas por el usuario</p>
  **/
    public interface ArbolInterface<Integer>{

    /**
      *<p>Convierte la Lista de enteros en un arbol dependiendo el tipo</p>
      *@param enteros Lista de enteros
      **/
      public void convierteEnArbol(Lista<Integer> enteros);




    }
