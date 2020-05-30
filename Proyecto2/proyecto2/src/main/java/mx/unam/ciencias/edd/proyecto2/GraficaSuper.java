package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Lista;

import mx.unam.ciencias.edd.VerticeGrafica;
import mx.unam.ciencias.edd.Coleccion;
import mx.unam.ciencias.edd.MeteSaca;
import mx.unam.ciencias.edd.Grafica;

import java.util.NoSuchElementException;


  public class GraficaSuper {

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

    private Grafica<Integer> grapho;
    /**
    *Constructor de la clase SVGGrafica
    *
    *@param enteros Lista de enteros
    **/
    GraficaSuper(Lista<Integer> enteros){
      this.enteros=enteros;
      grapho=new Grafica<Integer>();
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
      ancho=enteros.getElementos() >grapho.getElementos()?100+(enteros.getElementos()*(getGrande()+ 150)):200+(grapho.getElementos()*(getGrande()+ 150));
      alto=enteros.getElementos() >grapho.getElementos()?100+(enteros.getElementos()*(getGrande()+ 130)):200+(grapho.getElementos()*(getGrande()+ 130));
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
    *Devuelve una lista de enteros con aquellos vertices que será disconexos
    *a la gráfica
    *@return desconectados Enteros que se encuentran a parte de la gráfica
    **/
    protected Lista<Integer> getVerticesDesconectados(){
      Lista<Integer> desconectados= new Lista<>();
      for(Integer i: enteros){
        if(vecesContenido(i)>1){
            desconectados.agrega(i);
            eliminaTotal(i);
        }
      }
      return desconectados;

    }

    private void eliminaTotal(Integer i){
      while(enteros.contiene(i)){
        enteros.elimina(i);
      }
    }

    private Lista<Integer> getConectadosVertices(){
      Lista<Integer> conected = new Lista<>();
    for(Integer i: enteros){
      if(vecesContenido(i)==1){
        conected.agrega(i);
        grapho.agrega(i);
      }

    }

    for(int i=0; i<grapho.getElementos();i++)
      if(i+1<grapho.getElementos()-1)
      grapho.conecta(conected.get(i),conected.get(i+1));


    return conected;


    }

    /**
    *Escribe el codigo SVG correspondiende a la Grafica
    *
    *@return programa escrito SVG
    *
    **/
    protected String getGrafica(){

      Lista<Integer> desconectados=getVerticesDesconectados();
      Lista<Integer> conectados=getConectadosVertices();
          ancho+=desconectados.getElementos()*250;

      String programa="";
      if(conectados.getElementos()==0)programa+=codeaDesconectados(desconectados);
      else
      programa+=codeaConectados(conectados,desconectados);

          return programa;
    }

    private String codeaDesconectados(Lista<Integer> desconectados){
      String retornable ="";
      int radio=getGrande()*15;

      int xPos=getAncho()/(desconectados.getElementos()+2);
      int yPos=getAlto()/2;
      int t=1;
      while(desconectados.getElementos()>0){
            if(t%2==0)
            retornable+=auxVerticeNumero(desconectados.eliminaPrimero(),xPos*t,yPos+(yPos/4)+(t/2),radio, "red");
            else
            retornable+=auxVerticeNumero(desconectados.eliminaPrimero(),xPos*t,yPos-(yPos/4)-(t/2),radio, "red");
            t++;
        }
        return retornable;
    }

    private String codeaConectados(Lista<Integer> conectados, Lista<Integer> desconectados){
      String retornable="";
      int radio=getGrande()*20;


      int xPos=getAncho()/(conectados.getElementos()+desconectados.getElementos() +2);
      int yPos=getAlto()/2;
      boolean subida=false;
      int t=0;

      while(!conectados.esVacia() || !desconectados.esVacia()){
        t++;
        if(conectados.getElementos()>0){
          if(t%2==0){
              if(conectados.getElementos()>1)
              retornable+=auxLinea(radio+(xPos*t),radio+(xPos*(t+1)), yPos-(yPos/t) +2*radio,yPos+(yPos/(t+1))-2*radio);

              retornable+=auxVerticeNumero(conectados.eliminaPrimero(),radio+(xPos*t),yPos-(yPos/t) +2*radio ,radio,"blue");
          }else{
            if(conectados.getElementos()>1)
            retornable+=auxLinea(radio+(xPos*t),radio+(xPos*(t+1)), yPos+(yPos/t)-2*radio,yPos-(yPos/(t+1))+2*radio);

            retornable+=auxVerticeNumero(conectados.eliminaPrimero(),radio+(xPos*t),yPos+(yPos/t)-2*radio ,radio, "black");
          }
        }

        if(desconectados.getElementos()>0){
          if(t>1){
            if(t%2==0)
            retornable+=auxVerticeNumero(desconectados.eliminaPrimero(), radio + xPos*t ,yPos+(yPos/3)+(t/2),radio, "red");
            else
            retornable+=auxVerticeNumero(desconectados.eliminaPrimero(), radio +xPos*t ,yPos-(yPos/3)-(t/2),radio, "red");

          }

          }
      }

      return retornable;

    }



    private String auxVerticeNumero(Integer elemento, int x, int y, int radio, String color){
        String circle = "<circle cx=\""+x+"\" cy=\""+y+"\" r=\""+radio+"\" stroke=\"black\" stroke-width=\"3\" fill=\""+color+"\" /> \n";
        int yNuevo=y+5;
        String text="<text fill=\'white\' font-family=\'sans-serif\' font-size=\'20\' x=\'"+x+"\' y=\'"+yNuevo+"\' text-anchor='middle'>"+elemento+"</text>\n";
          return  circle+text;
        }

    private String auxLinea( int x1, int x2, int y1, int y2){
      String line = "<line x1=\""+x1+"\" y1=\""+y1+"\" x2=\""+x2+"\" y2=\""+y2+"\" stroke=\"black\" stroke-width=\"4\" />\n";
          return line;
      }





  }
