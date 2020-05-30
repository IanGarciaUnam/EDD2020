
package mx.unam.ciencias.edd.proyecto2;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.File;

import mx.unam.ciencias.edd.Lista;


  /**
    *<p> Clase SVGLista</p>
    *
    *<p>Permite escribir el archivo donde se establece el código según la clase
    *solicitada </p>
    *
    *
    *@author Ian García
    *@since 10/03/2020
    *@version 1.0
    **/

  public class SVGLista implements ListaInterface<Integer>{


    protected Lista<Integer> enteros;
    private int times=0;
    private int grande;
    private int xAncho;
    private int Xrect;

    SVGLista(Lista<Integer> enteros){
      this.enteros=enteros;
    }

      /**
      *Su trabajo especifico es compraobar la existencia del dicho archivo
      *y escribir en el archivo el código correspondiente para crear una imagen svg
      *
      */

      public void transcribe(){
        String codeo= codea();
        System.out.println(codeo);
      }

      /**
      *Les permite a su hijos regresar el resultado por la salida estandar
      *
      *@param svg contenido del archivo a SVG a devolver
      */

      protected void transcribe(String svg){
        System.out.println(svg);
      }

      private String codea(){
        int tamanoAncho=0;
        String programa="";
        String s="";
        setGrande(enteros);

        for(Integer e: enteros)
          programa+=this.getContenedor(times++)+ this.getObjeto(e)+"\n";


          tamanoAncho+= getXrect()+getAncho()+15;
          String features="<svg width="+"'"+ tamanoAncho+"'"+" "+"height='50'>\n";
        String writeMe= "<?xml version=\'1.0\' encoding=\'UTF-8\' ?> \n "+features +"<g> \n "+"\n" + programa +"\n </g> \n </svg> \n"  ;
        return writeMe;

      }


      protected void setGrande(Lista<Integer> ent){
        boolean neg=false;
      for(Integer i:ent){
          if(i<0){
            i=i*-1;
            neg=!neg;
          }
          if(i>grande)
            this.grande=i;
        }

        String converse= String.valueOf(grande);
        int tamanio=converse.length();

        if(neg)grande = tamanio+1;
        else grande=tamanio;

      }

      protected int getTamanioGrande(){
        return grande;
      }

      private void setAncho(int a){
        xAncho=a;
      }
      private int getAncho(){
        return xAncho;
      }


      /**
      *Funcion que devuleve código del contenedor en SVG así como su unión entre los mismos contenedores
      *@param ronda vuelta en la que corre el programa para obtener el numero de contenedores exactos en BFS
      *@return contenedor variable que contiene código SVG del contenedor
      */
      private String getContenedor(int ronda){
        int ancho=getTamanioGrande()*30;
        setAncho(ancho);
        int espacio=ancho+30;
        int x=15+(ronda*espacio);
        setXrect(x);
        String contenedor="<rect x=\""+x +"\""+ " y=\"10\" width=\""+ancho+"\" height=\"30\" stroke=\"black\" fill=\"white\" stroke-width=\"2\"/> \n  ";
        int ubicaLinea=x+ancho;
        int ubicaLinea2=x+espacio;
        //linea de union de la lista

        if(ronda<enteros.getElementos()-1){
          contenedor+=getLineaFlecha(ubicaLinea, ubicaLinea2)+"\n";
          contenedor+=getLiticaIzquierda(ubicaLinea, ubicaLinea) +"\n";
          contenedor+=getLiticaDerecha(ubicaLinea, ubicaLinea2,espacio) +"\n";

        }


        return contenedor;
      }



      /**
      *Devuelve el código para la linea de una flecha
      *@param ubicaLinea Ubica el inicio de la recta con respecto a x
      *@param ubicaLinea2 ubica la linea en final de su union
      +*/
      protected String getLineaFlecha(int ubicaLinea, int ubicaLinea2){
        return "<line x1=\'" +ubicaLinea +"\' y1=\'"+ 25+"\' x2=\'"+ ubicaLinea2+"\' y2='25' stroke='black' stroke-width='2' />\n";
      }

      /**
      *Devuelve el código para la litica de una flecha -- lado Izquierdo
      *@param ubicaLinea Ubica el inicio de la recta con respecto a x
      *@param ubicaLinea2 ubica la linea en final de su union
      +*/
      protected String getLiticaIzquierda(int ubicaLinea, int ubicaLinea2){

        int totalNorte=ubicaLinea+5;
        int totalSur=ubicaLinea2+5;
      String norte="<line x1=\'" +ubicaLinea +"\' y1='25' x2=\'"+totalNorte+"\' y2='30' stroke='black' stroke-width='2' />\n";
      String sur="<line x1=\'" +ubicaLinea +"\' y1='25' x2=\'"+ totalNorte+"\' y2='20' stroke='black' stroke-width='2' />\n";

      return norte+"\n"+sur;
      }

      /**
      *Devuelve el código para la litica de una flecha -- Lado derecho
      *@param ubicaLinea Ubica el inicio de la recta con respecto a x
      *@param ubicaLinea2 ubica la linea en final de su union
      +*/
      protected String getLiticaDerecha(int ubicaLinea, int ubicaLinea2, int espacio){

        int totalNorte=ubicaLinea2-5;
        int totalSur=ubicaLinea2;


      String norte="<line x1=\'" +totalSur +"\' y1='25' x2=\'"+totalNorte+"\' y2='30' stroke='black' stroke-width='2' />\n";
      String sur="<line x1=\'" +totalSur +"\' y1='25' x2=\'"+ totalNorte+"\' y2='20' stroke='black' stroke-width='2' />\n";

      return norte+"\n"+sur;
      }



      /**
      *Permite insertar el valor en X par Xrect
      *@param x valor en X
      *
      **/
      protected void setXrect(int x){
        Xrect=x;
      }


      /**
      *Método protegido que permite obtener el espacio respecto al eje X del
      *contenerdor para Listas
      *@return Xrect espacio en X
      */
    protected int getXrect(){
        return Xrect;
      }

      private String getObjeto( Integer i){
        int fijo=(getAncho()/2)+getXrect();
        String contenido = "<text fill='black' font-family='sans-serif' font-size='20' x=\'"+fijo+"\' y='30' text-anchor='middle'>"+i+"</text>\n";
        return contenido;
      }


}
