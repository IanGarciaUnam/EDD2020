
package mx.unam.ciencias.edd.proyecto2;


import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
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

  public class SVGArbolBinarioOrdenado extends Arbol{




    private ArbolBinarioOrdenado<Integer> arbol;
    private int tamanoAlto;
    private int tamanoAncho;
    private  VerticeArbolBinario<Integer> vertice;
    private String circle="";

    SVGArbolBinarioOrdenado(Lista<Integer> enteros){ // El contructor solo manda a llamar a su padre
      super(enteros);
      arbol=new ArbolBinarioOrdenado<>(enteros);
    }




      /**
      *Muestra en la salida estandar el código del árbol
      *
      **/
      public void transcribe(){
        //convierteEnArbol(getLista());
        String wrote=super.codea(getCode());
        System.out.println(wrote);

      }
      
      /**
      *Convierte una lista en un arbol binario
      *@param enteros Lista de enteros
      **/
      public void convierteEnArbol(Lista<Integer> enteros){

        arbol=new ArbolBinarioOrdenado<>();
        for(Integer e: enteros)
            arbol.agrega(e);


      }

      private String getCode(){
        Lista<VerticeArbolBinario<Integer>> l1= new Lista<>();
        l1=super.getListaDfs();
        String  content=recorridoDfs(l1);
          return content;
      }

        private String auxVerticeNumero(Integer elemento, int x, int y, int radio){
        String circle = "<circle cx=\""+x+"\" cy=\""+y+"\" r=\""+radio+"\" stroke=\"black\" stroke-width=\"3\" fill=\"white\" /> \n";
        int yNuevo=y+5;
        String text="<text fill=\'black\' font-family=\'sans-serif\' font-size=\'20\' x=\'"+x+"\' y=\'"+yNuevo+"\' text-anchor='middle'>"+elemento+"</text>\n";
          return circle+text;
        }

        private String auxLinea( int x1, int x2, int y1, int y2){
        String line = "<line x1=\""+x1+"\" y1=\""+y1+"\" x2=\""+x2+"\" y2=\""+y2+"\" stroke=\"black\" stroke-width=\"2\" />\n";
        return line;
        }

  }
