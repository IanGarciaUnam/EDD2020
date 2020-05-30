
package mx.unam.ciencias.edd.proyecto2;

import mx.unam.ciencias.edd.Cola;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.ArbolBinarioCompleto;
import mx.unam.ciencias.edd.ArbolBinario;
import mx.unam.ciencias.edd.AccionVerticeArbolBinario;
import mx.unam.ciencias.edd.VerticeArbolBinario;


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

  public class SVGArbolBinarioCompleto extends SVGLista implements ListaInterface<Integer>, ArbolInterface<Integer>{



    private Cola<Integer> cola;
    private ArbolBinarioCompleto<Integer> arbol;
    private int tamanoAlto;
    private int tamanoAncho;
    private  VerticeArbolBinario<Integer> vertice;
    private String circle="";

    SVGArbolBinarioCompleto(Lista<Integer> enteros){ // El contructor solo manda a llamar a su padre
      super(enteros);
      convierteEnArbol(enteros);
    }




      @Override
      public void transcribe(){
        setTamanoCanvas(super.enteros.getElementos());
        super.setGrande(super.enteros);
        super.transcribe(codea());
      }



      /**
      *Convierte una lista en un arbol binario
      *@param enteros Lista de enteros
      **/
      public void convierteEnArbol(Lista<Integer> enteros){

        arbol=new ArbolBinarioCompleto<>();
        cola=new Cola<Integer>();
        for(Integer e: enteros){
            arbol.agrega(e);
            cola.mete(e);
        }
      }



      private String codea(){
        String features="<svg width="+"'"+ getAncho()+"'"+" "+"height=\'"+getAlto()+"\'>\n";
      String writeMe= "<?xml version=\'1.0\' encoding=\'UTF-8\' ?> \n "+features +"<g> \n "+"\n" + getVertice()+"\n </g> \n </svg> \n"  ;
        return writeMe;
      }




      private void setTamanoCanvas(int elementos){
        tamanoAlto=(100+(elementos*(getTamanioGrande()+ 15)));
        tamanoAncho=100+(elementos*(getTamanioGrande()+ 100));
      }
      /**
      *Obtiene el alto del canvas
      *@return tamanoAlto
      */
      protected int getAlto(){
        return tamanoAlto;
      }
      /**
      *Obtenemos el ancho del canvas
      *
      *@return tamanoAncho
      **/
      protected int getAncho(){
        return tamanoAncho;
      }





      private String getVertice(){
        int nivel=0;
        Lista<Integer>agregatoria = new Lista<Integer>();
        super.setGrande(super.enteros);
        String programa="";
        int elementosLeidos=0;
        int radio= super.getTamanioGrande()*10;
        boolean erradica=false;

        while(elementosLeidos<super.enteros.getElementos()){
          while(!cola.esVacia()){
               Integer e;
               vertice=arbol.busca(e=cola.saca());

               if(vertice.profundidad()==nivel || agregatoria.getElementos()< Math.pow(2,nivel) )
                 agregatoria.agrega(e);

                 if(agregatoria.getElementos()==Math.pow(2, nivel))break;

             }
          elementosLeidos+=agregatoria.getElementos();
          int xPos= getAncho()/ (int) Math.pow(2,nivel+1) ;
          int yPos= (10+ 2*radio);
          int t=1;
          int contadorPar=0;
          boolean izquierda=false;
          int trazo=1;
          izquierda=false;
          int oldXpos=getAncho()/ (int) Math.pow(2,nivel) ;

              for(Integer i: agregatoria){
                izquierda=!izquierda;

                if(nivel==1){ //Crea lineas
                  programa+=izquierda?auxLinea(xPos, 2*xPos-radio+5 , yPos*(nivel+1), yPos*(nivel)+radio/2):auxLinea(xPos*(trazo+1),  2*xPos+radio-5 , yPos*(nivel+1), (yPos*(nivel) +radio/2));
                }else if(nivel>1){
                  if(izquierda)
                   programa+=auxLinea(xPos*(trazo + trazo-1), oldXpos*(trazo)-radio+5, yPos*(nivel+1), yPos*(nivel)+radio/2);//Izquierda
                   if(!izquierda)
                   programa+=auxLinea(xPos*(trazo + trazo-1),oldXpos*(trazo-1) +radio-5 , yPos*(nivel+1), yPos*(nivel)+radio/2);//Derecha
                 }
                if(nivel==0){
                programa+=auxVerticeNumero(i,xPos,yPos*(nivel+1), radio);
              }else if(nivel == 1){
                  programa+=izquierda?auxVerticeNumero(i,xPos*t,yPos*(nivel+1), radio):auxVerticeNumero(i,xPos*(t + (t-1)),yPos*(nivel+1), radio);
                }else if(nivel>1){
                  programa+=auxVerticeNumero(i,xPos*(t + (t-1)),(yPos)*(nivel+1) , radio);
                }
                trazo++;
                t+=1;
              }
              agregatoria.limpia();
            nivel++;
          }
          return programa;
        }



        private String auxVerticeNumero(Integer elemento, int x, int y, int radio){
        String circle = "<circle cx=\""+x+"\" cy=\""+y+"\" r=\""+radio+"\" stroke=\"black\" stroke-width=\"3\" fill=\"white\" /> \n";
        int yNuevo=y+5;
        String text="<text fill=\'black\' font-family=\'sans-serif\' font-size=\'20\' x=\'"+x+"\' y=\'"+yNuevo+"\' text-anchor='middle'>"+elemento+"</text>\n";
          return elemento!=-605?circle+ text:"";
        }

        private String auxLinea( int x1, int x2, int y1, int y2){
        String line = "<line x1=\""+x1+"\" y1=\""+y1+"\" x2=\""+x2+"\" y2=\""+y2+"\" stroke=\"black\" stroke-width=\"2\" />\n";
        return line;
        }

  }
