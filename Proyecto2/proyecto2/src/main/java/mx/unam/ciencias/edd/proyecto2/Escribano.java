package mx.unam.ciencias.edd.proyecto2;

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
    *@version 2.0
    **/

  public class Escribano{

    private String tipoDeClase;
    private Lista<Integer> enteros;


      Escribano(String tipoDeClase, Lista<Integer> enteros){
        this.tipoDeClase=tipoDeClase;
        this.enteros=enteros;
      }
      Escribano(){}

        public void muestraVacio(){
          System.out.println("<?xml version=\'1.0\' encoding=\'UTF-8\' ?>\n");
           System.out.println("<svg width=\'400\' height=\'400\'>\n");
          System.out.println("<g>\n<circle cx=\"200\" cy=\"200\" r=\"140\" stroke=\"black\" stroke-width=\"3\" fill=\"white\"/>\n");
          System.out.println("<line x1=\'0\' y1=\'400\' x2=\'400\' y2=\'0\' stroke=\'black\' stroke-width=\'2\'/>");
           System.out.println("</g>\n </svg>\n");
        }

      /**
      *Su trabajo especifico es compraobar la existencia del dicho archivo
      * y escribir en el archivo el código correspondiente para crear una imagen svg
      *
      */

      public void escribe(){
        switch(tipoDeClase){
          case "Lista":
                SVGLista lista= new SVGLista(enteros);
                lista.transcribe();
                break;
          case "Pila":
                SVGPila pila= new SVGPila(enteros);
                pila.transcribe();
                break;
          case "Cola":
                SVGCola cola= new SVGCola(enteros);
                  cola.transcribe();
                  break;

          case "ArbolBinarioCompleto":
                  SVGArbolBinarioCompleto abc= new SVGArbolBinarioCompleto(enteros);
                  abc.transcribe();
                    break;
          case "ArbolBinarioOrdenado":
                  SVGArbolBinarioOrdenado abo= new SVGArbolBinarioOrdenado(enteros);
                  abo.transcribe();
                  break;
          case "ArbolRojinegro":
                  SVGArbolRojiNegro arn = new SVGArbolRojiNegro(enteros);
                  arn.transcribe();
                  break;
          case "ArbolAVL":
                SVGArbolAVL avl = new SVGArbolAVL(enteros);
                    avl.transcribe();
                    break;
          case "Grafica":
          if(enteros.getElementos()%2 != 0){
            System.out.println("Al ingresar una grafica debes tener un total de elementos pares");
            System.out.println("Para mas ayuda consulta java -jar target/proyecto2.jar --h ");
             break;
          }

                SVGGrafica grapho= new SVGGrafica(enteros);
                grapho.transcribe();
                  break;
          case "MonticuloMinimo":
                  SVGMonticuloMinimo m= new SVGMonticuloMinimo(enteros);
                  m.transcribe();
                  break;
          default:
              return;

          }

        }












}
