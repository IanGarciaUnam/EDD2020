package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.ArbolBinarioOrdenado;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.MeteSaca;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.AccionVerticeArbolBinario;
import java.util.NoSuchElementException;


  public class Arbol implements AccionVerticeArbolBinario<Integer>{


    /**
    *Es una clase protegida cuyo fin es facilitar todas las tareas básicas que ocurren
    *en los Arboles para generar sus gráficos
    *
    *
    **/

    private Lista<Integer> enteros;
    private int tamanioGrande;
    private int ancho;
    private int alto;
    private ArbolBinarioOrdenado<Integer> arbol;
    private Lista<VerticeArbolBinario<Integer>>   listaDfs= new Lista<VerticeArbolBinario<Integer>>();


    /**
    *Constructor de la clase Arbol
    *@param enteros Lista de enteros
    **/
    Arbol(Lista<Integer> enteros){
      this.enteros=enteros;
      arbol=new ArbolBinarioOrdenado<>(enteros);
    }
    /**
    *Devuelve la lista de enteros que contiene a los elementos del arbol
    *
    **/
    protected Lista<Integer> getLista(){
      return enteros;
    }
    /**
    *<p>Devuelve el numero de caracteres del número más grande</p>
    *@return setTamanioGrande() FileNotFoundException
    **/
    protected int getGrande(){
      tamanioGrande=setTamanioGrande();
      return tamanioGrande;
    }



    private int setTamanioGrande(){
      boolean neg= false;
      for(Integer i: enteros){
        if(i<0){
            i=i*-1;
            neg=true;
        }

        if(i>tamanioGrande)
          this.tamanioGrande=i;
      }

      String converse= String.valueOf(tamanioGrande);
      int tamanio=converse.length();

      if(neg)tamanioGrande = tamanio+1;
      else tamanioGrande=tamanio;

      return tamanioGrande;
    }

    private void setTamanoCanvas(){
      ancho=(100+(enteros.getElementos()*(getGrande()+ 180)));
      alto=100+(enteros.getElementos()*(getGrande()+ 100));
    }

    /**
    *Obtenemos el alto del canvas
    *
    *@return alto alto del Canvas
    **/
    protected int getAlto(){
      setTamanoCanvas();
      return alto;
    }
    /**
    *Devuelve el ancho del canvas
    *@return ancho ancho del canvas
    */
    protected int getAncho(){
      setTamanoCanvas();
      return ancho;
    }
    /**
    *Devuelve el código de la imagen SVG
    *@param programa código del programa
    *@return writeMe programa con el formato SVG
    **/
    protected String codea(String programa){
      String features="<svg width="+"'"+ ancho+"'"+" "+"height=\'"+alto+"\'>\n";
    String writeMe= "<?xml version=\'1.0\' encoding=\'UTF-8\' ?> \n "+features +"<g> \n "+"\n" + programa+"\n </g> \n </svg> \n"  ;
      return writeMe;
    }

    /**
    *Cuenta el numero de veces que un numero se encuentra contenido dentro de
    * la estructura a implementar
    *@param i Integer a verificar
    @return veces en las que se encontro el mismo numero
    **/
    protected int vecesContenido(Integer i){
      int veces=0;
      for(Integer e: enteros)
        if(e.equals(i))veces++;

      return veces;
    }



    /**
    *Agrega elementos a la lista dfs
    *@param v VerticeArbolBinario<Integer>
    **/
    public void actua(VerticeArbolBinario<Integer> v){
      listaDfs.agrega(v);
    }
    /**
    *Devuelve una lista con los vertices de un ArbolBinario
    *
    **/

    protected Lista<VerticeArbolBinario<Integer>> getListaDfs(){
      arbol.dfsInOrder(v->actua(v));
      return listaDfs;
    }

    /**
    *Escribe el codigo SVG correspondiende al ArbolBinarioOrdenado
    *@param dfs Lista<VerticeArbolBinario<Integer>>
    *@return programa escrito SVG
    *
    **/
    protected String recorridoDfs(Lista<VerticeArbolBinario<Integer>> dfs){
      setTamanoCanvas();
      int xPos=getAncho() /(dfs.getElementos() + 1);
      int yPos=getAlto()/(2 +dfs.getElementos()) ;
      int radio=getGrande()*20;
      int t=1;
      String programa="";

      Pila<Integer> paraX=new Pila<>();
      Pila<Integer> paraY=new Pila<>();


      while(!dfs.esVacia()){
        VerticeArbolBinario<Integer> v=dfs.eliminaPrimero();
        if(v.hayIzquierdo()){
             programa+=auxLinea(paraX.saca()+radio,xPos*t,paraY.saca(), yPos + yPos*v.profundidad());
           }

            if(v.hayPadre() && v.padre().hayDerecho() && v.padre().derecho()==v){
             programa+=auxLinea(paraX.saca()+radio,xPos*t,paraY.saca(), yPos + yPos*v.profundidad());
           }
            if(v.hayDerecho()){
             paraX.mete(xPos*t);
             paraY.mete(yPos + yPos*v.profundidad());
           }
           if(v.hayPadre() && v.padre().hayIzquierdo() && v.padre().izquierdo()==v){

             paraX.mete(xPos*t);
             paraY.mete(yPos + yPos*v.profundidad());
          }
        programa+=auxVerticeNumero(v.get(),xPos*t,yPos + yPos*v.profundidad() ,radio );
        t++;
      }
      return programa;
    }



    private String auxVerticeNumero(Integer elemento, int x, int y, int radio){
        String circle = "<circle cx=\""+x+"\" cy=\""+y+"\" r=\""+radio+"\" stroke=\"black\" stroke-width=\"3\" fill=\"white\" /> \n";
        int yNuevo=y+5;
        String text="<text fill=\'black\' font-family=\'sans-serif\' font-size=\'20\' x=\'"+x+"\' y=\'"+yNuevo+"\' text-anchor='middle'>"+elemento+"</text>\n";
          return  circle+text;
        }

    private String auxLinea( int x1, int x2, int y1, int y2){
      String line = "<line x1=\""+x1+"\" y1=\""+y1+"\" x2=\""+x2+"\" y2=\""+y2+"\" stroke=\"black\" stroke-width=\"3\" />\n";
          return line;
      }



  }
