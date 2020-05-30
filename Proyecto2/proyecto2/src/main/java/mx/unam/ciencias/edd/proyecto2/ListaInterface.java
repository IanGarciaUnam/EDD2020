package mx.unam.ciencias.edd.proyecto2;

  /**
  *<p>Interface para Lista Doble Enlazada y/o comparables de su tipo diferentes a Arboles
  * Pilas y Colas</p>
  *
  *<p>Estas no aceptan elementos diferentes a Integer, dichos elemetos son contenidos
  * en una lista que recibe solo Integer  El objetio de estas clases es convertir las estructuras de
  *datos en código svg para ser visibilizadas por el usuario</p>
  **/
    public interface ListaInterface<Integer>{

    /**
      *<p>MUestra en pantalla el código SVG generado de dicha estructura de datos</p>
      *
      **/
      public void transcribe();

    }
