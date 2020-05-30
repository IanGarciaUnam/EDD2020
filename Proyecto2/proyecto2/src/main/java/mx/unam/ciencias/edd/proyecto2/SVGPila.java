
package mx.unam.ciencias.edd.proyecto2;


import mx.unam.ciencias.edd.Lista;


  /**
    *<p> Clase SVGPila</p>
    *
    *<p>Permite escribir el archivo donde se establece el código según la clase
    *solicitada </p>
    *
    *
    *@author Ian García
    *@since 10/03/2020
    *@version 1.0
    **/

  public class SVGPila extends SVGLista implements ListaInterface<Integer>{


    private int times=0;
    private int grande;
    private int xAncho;
    private int temps=0;

    SVGPila(Lista<Integer> enteros){ // El contructor solo manda a llamar a su padre
      super(enteros);
    }


    @Override
      public void transcribe(){
        super.transcribe(codea());
      }



      private String codea(){

        String programa="";
        String s="";
        super.setGrande(super.enteros);
        int tamanoAlto=20+(30*super.enteros.getElementos());
        int tamanoAncho=20+(30*super.getTamanioGrande());

        for(Integer e: enteros)
          programa+=this.getContenedor(times++)+ "\n";

        while(super.enteros.getElementos()>0)
          programa+="\n"+getObjeto(enteros.eliminaUltimo(), temps++)+"\n";

        String features="<svg width="+"'"+ tamanoAncho+"'"+" "+"height=\'"+tamanoAlto+"\'>\n";
        String writeMe= "<?xml version=\'1.0\' encoding=\'UTF-8\' ?> \n "+features +"<g> \n "+"\n" + programa +"\n </g> \n </svg> \n" ;
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
        int ancho=getTamanioGrande()*30; // valor fijo
        setAncho(ancho);

        int x= 10;
        int y=x+(ronda*30);

        String contenedor="<rect x=\""+x+"\""+ " y=\""+y +"\" width=\""+ancho+"\" height=\"30\" stroke=\"black\" fill=\"white\" stroke-width=\"2\"/> \n  ";
        //linea de union de la lista
        return contenedor;
      }

      private String getObjeto( Integer i ,int ronda){
        int y= 30 +(ronda*30);
        int x=10+getAncho()/2;
        String contenido = "<text fill='black' font-family='sans-serif' font-size='20' x=\'"+x+"\' y=\'"+ y+"\' text-anchor='middle'>"+i+"</text>\n";
        return contenido;
      }


    }
