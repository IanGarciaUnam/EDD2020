
package mx.unam.ciencias.edd.proyecto2;


import mx.unam.ciencias.edd.Lista;


  /**
    *<p> Clase SVGCola</p>
    *
    *<p>Permite escribir el archivo donde se establece el código según la clase
    *solicitada </p>
    *
    *
    *@author Ian García
    *@since 10/03/2020
    *@version 1.0
    **/

  public class SVGCola extends SVGLista implements ListaInterface<Integer>{


    private int times=0;
    private int grande;
    private int xAncho;


    SVGCola(Lista<Integer> enteros){ // El contructor solo manda a llamar a su padre
      super(enteros);
    }


    @Override
      public void transcribe(){
        super.transcribe(codea());
      }



      private String codea(){

        int tamanoAncho=0;
        String programa="";
        String s="";
        super.setGrande(enteros);

        for(Integer e: enteros)
          programa+= getContenedor(times++)+ getObjeto(e)+"\n";


          tamanoAncho+= super.getXrect()+getAncho()+15;
          String features="<svg width="+"'"+ tamanoAncho+"'"+" "+"height='50'>\n";
        String writeMe= "<?xml version=\'1.0\' encoding=\'UTF-8\' ?> \n "+features +"<g> \n "+"\n" + programa +"\n </g> \n </svg> \n"  ;
        return writeMe;
      }


      private void setAncho(int a){
        this.xAncho=a;
      }
      private int getAncho(){
        return this.xAncho;
      }


      /**
      *Funcion que devuleve código del contenedor en SVG así como su unión entre los mismos contenedores y su respectivos movimientos
      *@param ronda vuelta en la que corre el programa para obtener el numero de contenedores exactos
      *@return contenedor variable que contiene código SVG del contenedor
      */
      private String getContenedor(int ronda){

      int ancho=super.getTamanioGrande()*30;
      setAncho(ancho);
      int espacio=ancho+30;
      int x=15+(ronda*espacio);
      super.setXrect(x);
      String contenedor="<rect x=\""+x +"\""+ " y=\"10\" width=\""+ancho+"\" height=\"30\" stroke=\"black\" fill=\"white\" stroke-width=\"2\"/> \n  ";
      int ubicaLinea=x+ancho;
      int ubicaLinea2=x+espacio;


      if(ronda<enteros.getElementos()-1){
          contenedor+=super.getLineaFlecha(ubicaLinea, ubicaLinea2)+"\n";
          contenedor+=super.getLiticaIzquierda(ubicaLinea, ubicaLinea) +"\n";
          }
      return contenedor;

    }



    private String getObjeto( Integer i){
      int fijo=(getAncho()/2)+super.getXrect();
      String contenido = "<text fill='black' font-family='sans-serif' font-size='20' x=\'"+fijo+"\' y='30' text-anchor='middle'>"+i+"</text>\n";
      return contenido;
    }
  }
