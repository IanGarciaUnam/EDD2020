package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolRojinegro;
import mx.unam.ciencias.edd.ArbolAVL;
import mx.unam.ciencias.edd.Color;
import mx.unam.ciencias.edd.VerticeArbolBinario;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.MeteSaca;
import mx.unam.ciencias.edd.Pila;
import mx.unam.ciencias.edd.AccionVerticeArbolBinario;


  public class SVGArbol{//extends ArbolBinario<Palabra> 

    /**
    *Es una clase protegida cuyo fin es facilitar todas las tareas básicas que ocurren
    *en los Arboles para generar sus gráficos
    *
    *
    **/

    private Lista<Palabra> palabra;
    private int tamanioGrande=0;
    private int ancho;
    private int alto;
    private ArbolRojinegro<Palabra> arbol;
    private ArbolAVL<Palabra> arbolAVL;
    private Lista<VerticeArbolBinario<Palabra>>   listaDfs= new Lista<>();
    private Class clase;

    /**
    *Constructor de la clase Arbol
    *@param palabra Lista de Palabras
    **/
    SVGArbol(Lista<Palabra> palabra){
      this.palabra=palabra;
      palabra=myTop();
      arbol=new ArbolRojinegro<>(palabra);

      arbolAVL=new ArbolAVL<>(palabra);
    }


    protected void setCLase(Class clase){
      this.clase=clase;
    }



    private Lista<Palabra> myTop(){
      palabra= Lista.mergeSort(palabra).reversa();

       if(palabra.getElementos()<=15)return palabra;

      Lista<Palabra> quinze= new Lista<>();
      for(int i=0; i<15; i++)
        quinze.agrega(palabra.get(i));
     /** int i=0;
      for(Palabra p: palabra){
          if(i>=15) break;
          quinze.agrega(p);
          i++;
      }**/
        

      return quinze;

    }



    /**
    *<p>Devuelve el numero de caracteres del número más grande</p>
    *@return setTamanioGrande() FileNotFoundException
    **/
    protected int getGrande(){
      return getTamanioGrande();
    }



    private int getTamanioGrande(){
      
      int i=0;
      for(Palabra p: myTop()){
          i=p.toString().length();
          if(i>=tamanioGrande)tamanioGrande=i;
      }
    return tamanioGrande>100? 70:tamanioGrande;
    }

    private void setTamanoCanvas(){
      ancho=10*(getGrande()+ getTamanioGrande()+100+tamanioGrande);
      alto=15*(getGrande()+ getTamanioGrande()+120+tamanioGrande);
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


    protected String elige(String clase){

      if(clase.equals("ARN"))
      {
          Lista<VerticeArbolBinario<Palabra>> lista = new Lista<>();
       lista=getListaDfs();
       String programa= recorridoDfsRojiNegro(lista);
       return codea(programa);
      }else if(clase.equals("AVL")){
        return codea( recorridoDfsAVL());
      }


      return "";
    }

    /**
    *Devuelve el código de la imagen SVG
    *@param programa código del programa
    *@return writeMe programa con el formato SVG
    **/
  private String  codea(String codigo){
        String code="<?xml version=\'1.0\' encoding=\'UTF-8\' ?>\n";
      code+="<svg xmlns=\"http://www.w3.org/2000/svg\" width=\'"+ancho+"\' height=\'"+alto+"\'>\n<g>";
      code+=codigo;
      code+= "</g>\n</svg>";
        return code;
    }

    /**
    *Agrega elementos a la lista dfs
    *@param v VerticeArbolBinario<Integer>
    **/
    public void actua(VerticeArbolBinario<Palabra> v){
      listaDfs.agrega(v);
    }
    /**
    *Devuelve una lista con los vertices de un ArbolBinario
    *
    **/

    protected Lista<VerticeArbolBinario<Palabra>> getListaDfs(){
      arbol.dfsInOrder(v->actua(v));
      return listaDfs;
    }

     protected Lista<VerticeArbolBinario<Palabra>> getListaDfsAVL(){
      arbolAVL.dfsInOrder(v->actua(v));
      return listaDfs;
    }

    /**
    *Escribe el codigo SVG correspondiende al ArbolBinarioOrdenado
    *@param dfs Lista<VerticeArbolBinario<Palabra>>
    *@return programa escrito SVG
    *
    **/
    protected String recorridoDfsRojiNegro(Lista<VerticeArbolBinario<Palabra>> dfs){
      setTamanoCanvas();
      int xPos=getAncho() /(dfs.getElementos() + 1);
      int yPos=getAlto()/(1 +dfs.getElementos()) ;
      int radio=getGrande()*3;
      int t=1;
      String programa="";

      Pila<Integer> paraX=new Pila<>();
      Pila<Integer> paraY=new Pila<>();

      while(!dfs.esVacia()){
        VerticeArbolBinario<Palabra> v=dfs.eliminaPrimero();
        /**Modificar accesso a izquierdos**/
        if(v.hayIzquierdo()){
          programa+=auxLinea(paraX.saca(),xPos*t-radio,paraY.saca()-radio, yPos+yPos*v.profundidad());
          }
        if(v.hayPadre() && v.padre().hayDerecho() && v.padre().derecho()==v){
          programa+=auxLinea(paraX.saca()+radio,xPos*t,paraY.saca()+radio/1200, yPos + yPos*v.profundidad()-radio);
          }

           

        if(v.hayDerecho()){
          paraX.mete(xPos*t);
          paraY.mete(yPos + yPos*v.profundidad());
        }
        if(v.hayPadre() && v.padre().hayIzquierdo() && v.padre().izquierdo()==v){
          paraX.mete(xPos*t);
          paraY.mete(yPos + yPos*v.profundidad());
                  }

       if(arbol.getColor(v) == Color.ROJO)
        programa+=auxVerticeNumero(v.get(),xPos*t,yPos + yPos*v.profundidad() ,radio, "red");
        else
        programa+=auxVerticeNumero(v.get(),xPos*t,yPos + yPos*v.profundidad() ,radio, "black");

        t++;
      }
      return programa;

    }


protected String recorridoDfsAVL(){
      Lista<VerticeArbolBinario<Palabra>> dfs=getListaDfsAVL();
      int xPos=getAncho() /(dfs.getElementos() + 1);
      int yPos=getAlto()/(2 +dfs.getElementos()) ;
      int radio=getGrande()*3;
      int t=1;
      String programa="";

      Pila<Integer> paraX=new Pila<>();
      Pila<Integer> paraY=new Pila<>();

    
      while(!dfs.esVacia()){
        VerticeArbolBinario<Palabra> v=dfs.eliminaPrimero();
    
        if(v.hayIzquierdo()){
          programa+=auxLinea(paraX.saca(),xPos*t-radio,paraY.saca()-radio, yPos+yPos*v.profundidad());
          }

        if(v.hayPadre() && v.padre().hayDerecho() && v.padre().derecho()==v){
          programa+=auxLinea(paraX.saca()+radio,xPos*t,paraY.saca()+radio/1200, yPos + yPos*v.profundidad()-radio);
          }
        if(v.hayDerecho()){
            paraX.mete(xPos*t);
            paraY.mete(yPos + yPos*v.profundidad());
          }
        if(v.hayPadre() && v.padre().hayIzquierdo() && v.padre().izquierdo()==v){
            paraX.mete(xPos*t);
            paraY.mete(yPos + yPos*v.profundidad());
            }

        programa+=auxVerticeNumero(v.get(),xPos*t,yPos + yPos*v.profundidad() ,radio);
        programa+=auxBalance(v,xPos*t,yPos*v.profundidad(),radio);



        t++;
      }
      return programa;

    }

    private String auxVerticeNumero(Palabra elemento, int x, int y, int radio, String color){
        String circle = "<circle cx=\""+x+"\" cy=\""+y+"\" r=\""+radio+"\" stroke=\"black\" stroke-width=\"1\" fill=\""+color+"\" /> \n";
        int yNuevo=y+5;
        String text="<text fill=\'white\' font-family=\'sans-serif\' font-size=\'9\' x=\'"+x+"\' y=\'"+yNuevo+"\' text-anchor='middle'>"+elemento.toString()+"</text>\n";
          return  circle+text;
        }




    private String auxLinea( int x1, int x2, int y1, int y2){

      String line = "<line x1=\""+x1+"\" y1=\""+y1+"\" x2=\""+x2+"\" y2=\""+y2+"\" stroke=\"black\" stroke-width=\"1\" />\n";
          return line;
      }



      private String auxVerticeNumero(Palabra elemento, int x, int y, int radio){

        String circle = "<circle cx=\""+x+"\" cy=\""+y+"\" r=\""+radio+"\" stroke=\"black\" stroke-width=\"1\" fill=\"white\" /> \n";
        int yNuevo=y+2;
        String text="<text fill=\'black\' font-family=\'sans-serif\' font-size=\'10\' x=\'"+x+"\' y=\'"+yNuevo+"\' text-anchor='middle'>"+elemento.toString()+"</text>\n";
          return  circle+text;
        }

    private String auxBalance(VerticeArbolBinario<Palabra> v, int x, int y,int radio){
      int izquierdo=v.hayIzquierdo()?v.izquierdo().altura():0;
      int derecho=v.hayDerecho()?v.derecho().altura():0;
      int balance=izquierdo-derecho;
      int altura=v.altura();
      String anexo="{"+String.valueOf(altura)+"/"+String.valueOf(balance)+"}";

      if(v.hayPadre() && v.padre().hayIzquierdo() && v.padre().izquierdo()==v ){
        x-=radio/2;
        y+=radio/2;
      }else if(v.hayPadre() && v.padre().hayDerecho() && v.padre().derecho()==v){
        x+=radio/2;
        y+=radio/2;
      }else if(!v.hayPadre()){
        y+=3*radio;

      }

      String text="<text fill=\'black\' font-family=\'sans-serif\' font-size=\'20\' x=\'"+x+"\' y=\'"+y+"\' text-anchor='middle'>"+anexo+"</text>\n";
      return text;
    }




  }
