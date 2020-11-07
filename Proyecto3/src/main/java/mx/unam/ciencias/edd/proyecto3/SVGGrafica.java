package mx.unam.ciencias.edd.proyecto3;

import mx.unam.ciencias.edd.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.util.Iterator;

    
       /**
    *<p> Clase SVGGrafica <- son <- GraficaSVG</p>
    *
    *Permite gráficar una gráfica del tipo que se requiera (conexa ó disconexa)
    *Puesto que en la anterior versión solo podiamos tener vértiecs cuyo grado máximo fuera 2
    *SOlucionamos eso realizando curvas y graficando como si se tratase de un eje X donde los vértices son contiguos pero 
    *pueden conectarse con todos sus vecinos sin problema mayor, es una mejora importante pero para nada radical
    *los véticecs mantienen una pequeña oscilancia entre ellos que les permite bajar y subir poco para darle dinámica a la imagen
    *
    *@author Ian García
    *@since 1/06/2020
    *@version 1.0
    **/

  public class SVGGrafica extends GraficaSVG{

    /**
    *<p> Clase VecinoGrafico</p>
    *
    *Asocia un vertice a sus coordenadas
    *
    *
    *@author Ian García
    *@since 1/06/2020
    *@version 1.0
    **/
    public class VecinoGrafico{
        /**ArchivoHTML**/
        private ArchivoHTML archivo;
        /**Coordenadas en X**/
        private double coordenadasX;
        /**Coordenadas en Y**/
        private double coordenadasY;
        /**Radio del vertice**/
        private int radio;
        /**Mando permite saber si nuestras curvas correrán hacia arriba o abajo**/
        private boolean mando;

            /**
            *Constructor de VecinoGráfico
            *@param a ArchivoHTML
            *@param coordenadasX coordenadas en X
            *@param coordenadasY coordenadas en Y
            */
            VecinoGrafico(ArchivoHTML a, double coordenadasX, double coordenadasY, int radio, boolean mando){
                archivo=a;
                this.coordenadasX=coordenadasX;
                this.coordenadasY=coordenadasY;
                this.radio=radio;
                this.mando=mando;
            }
            /**
            *Devuelve el mndo
            *@return mando
            */
            public boolean getMando(){
                return this.mando;
            }
            /**
            *Devuelve ArchivoHTML
            *@return archivo 
            */
            public ArchivoHTML get(){
                return archivo;
            }
            /**
            *Devuelve coordenada X
            *@return coordenadasX
            */
            public double getX(){
                return coordenadasX;
            }
            /**
            *Devuelve Y
            *@return coordenadasY 
            */
            public double getY(){
                return coordenadasY;
            }
            /**
            *Devuelve radio
            *@return radio 
            */
            public int getRadio(){
                return radio;
            }

            /**
            *Devuelve un color asignado en caso de que se desee,no utilizado en este proyecto
            *@return color.getColor()
            */
            public String getColorValorGraficable(){
                ColorValorGraficable color= new ColorValorGraficable(archivo.getRuta());
                return color.getColor();

            }
    }


    /**
    *<p> Clase FabricaSVG</p>
    *
    *Grafica el SVG De gráficas
    *
    *
    *@author Ian García
    *@since 08/04/2020
    *@version 3.0
    **/
    public class FabricaSVG{
        String envoltura;
        String directorio;
        FabricaSVG(String envoltura, String directorio){ 
            this.envoltura=envoltura;
            this.directorio=directorio;
        }


        public String fabrica(){
            Dispersor<String> djb=FabricaDispersores.dispersorCadena(AlgoritmoDispersor.DJB_STRING);
        int modificador=djb.dispersa(envoltura);
            try{
                BufferedWriter bw=new BufferedWriter(new FileWriter(directorio+"/grapho"+modificador+".svg"));
                bw.write(envoltura);
                bw.close();
            }catch(IOException i){
                System.err.println("Error al crear la imagen Grafica.svg");
                System.exit(1);
            }

            return getInsercion("./"+"grapho"+modificador+".svg");

        }

          private  String getInsercion(String ruta){
        return "<img src=\""+ruta+"\" alt=\"Gráfica de Barras con los 15 casos más sobresalientes\" >";
    }   

    }

    /**
    *Permite crear una gráfica al no encontrar una manera
    *más sútil utilizaremos un circulo
    *
    *
    *
    **/


    private String codigoSVG="";
    private Lista<ArchivoHTML> archivos;
    private Grafica<ArchivoHTML> grapho;
    private ArchivoHTML archivo;
    private String directorio;

    private Conjunto<VecinoGrafico> vecinos;
    /**
    *Constructor de la clase SVGGrafica
    *
    *@param enteros Lista de enteros
    **/
    SVGGrafica(Lista<ArchivoHTML> archivos, String directorio){
        this.directorio=directorio;
        this.archivos=archivos;
        grapho=new Grafica<ArchivoHTML>();
        vecinos=new Conjunto<>(); 
        grafica();
      
        asignaCodigoSVG();
    }
    /**Constructor vacío**/
    SVGGrafica(){}
    /**
    *Devuelve el código sin formato de inserción de una 
    *gráfica
    *@return codigoSVG
    */
    public String codeaGrafica(){
       FabricaSVG fabrica=new FabricaSVG(codigoSVG, directorio);
       return fabrica.fabrica();
    }


    private void asignaCodigoSVG(){

        String code="";
        double PI=3.1592;

        int x= 110;
        int y= 300;
        boolean mando=true;
        for(ArchivoHTML a: grapho){
            int radio=a.getNombrePuro().length()*3;
            double xx=x;
             double yy=y;
         code+=circula( xx,  yy,radio,a.getNombrePuro());
            vecinos.agrega(new VecinoGrafico(a,xx,yy,radio, mando));

         mando =!mando;  
        x+=10+ 2*70;
        y+= mando? -30: 30;
            
           
        }


        for(VecinoGrafico v1: vecinos){
            for(VecinoGrafico v2: vecinos){
                if(grapho.sonVecinos(v1.get(), v2.get())){
                    code+=unir(v1,v2);
                    grapho.desconecta(v1.get(),v2.get());
                    }
                    
                }
            }

        
        codigoSVG=envuelveCodigo(grapho.getElementos()*250,code);


    }
    /**
    *Ingresa los elementos a la Gráfica
    */
    private void grafica(){
       for(ArchivoHTML arch: archivos)
            grapho.agrega(arch); 
              
        this.vecinos(); 
    }


    /**
    *Conecta los vertices de los archivos <=> el archivo y su vecino comparten una palabra de 
    *a lo menos 7 char 
    *
    */
     private void  vecinos(){
        for(ArchivoHTML a: grapho){
            Diccionario<String, Integer> alfa=a.getDiccionario();
            for(ArchivoHTML b: grapho){
                Diccionario<String, Integer> beta= b.getDiccionario();
                if(!a.equals(b) && !grapho.sonVecinos(a,b)){
                    Iterator<String> iteraA=alfa.iteradorLlaves();
                    while(iteraA.hasNext()){
                        String word=iteraA.next();
                        if(word.length()>6 && beta.contiene(word) && !grapho.sonVecinos(a,b))
                            grapho.conecta(a,b);
                            /**
                            *Verificamos que el otro archivo contenga una palabra de longitud al menos 7
                            *y que estos no hayan sido conectados previamente; la segunda erificación es encesario pues al ser conectados se continua iterando 
                            *y así evitamos caer en #link IllegaArgumentException
                            */
                               
                        }
                    }

                }
            }

        }  
          

        /**
        *Permite unir los vertices de Acuerdo a su VecinoGrafico
        *@param a VecinoGrafico
        *@param b VecinoGrafico
        *@return camino Curva de bezier en formato SVG
        */
        public String unir(VecinoGrafico a, VecinoGrafico b){
            int x1=(int)a.getX();
            int y1=(int)a.getY();
            int x2= (int)b.getX();
            int y2= (int)b.getY();
            int Q1x= (int) Math.round((x1+x2)/2); 
            int Q1y= ((x1+x2)/2) <600? (x1+x2)/2:(x1+x2)/2 - (x1+x2)/7 ;


            if(b.getMando()){
                Q1y=700-(x1)/10;
                y1+=10;
                y1+=10;
            }else{
                Q1y=(x2/10)-(x1/10);
                y2-=15;
                y1-=15;
            }
    	
    			while(Q1y<=0){
    				Q1y+=20;
    			}
    			if(Q1y>=300 && Q1y<=315)Q1y+=300-15;
    
              return "<path d=\"M"+x1+","+y1+" Q"+Q1x+","+Q1y+" "+x2+","+y2+"\" style=\"stroke:black; stroke-width:3; fill:none;\"></path>";  
        }


        /**
        *Crea los vertices de la gráfica, en caso de que la palabra sea mayor a 50 caracteres
        *la palabra se abrevia y el color de fondo cambia de negro a blanco [es algo mero estético]
        *
        *@param x double de ubicación en X
        *@param y coordenadas en Y
        *@param radio radio del vertice
        *@param nombre contendio del vertice
        */
    public String circula(double x, double y, double radio, String nombre){
        String color="white";
        String fill="black";
        if(radio>70)
            radio=60;
            //color="black";
            //fill="white";
        
        
        if(nombre.length()>50){
         nombre=nombre.substring(0,25);
         nombre+="...";
        }
       String circulo= "<circle cx=\""+x+"\" cy=\""+y+"\" r=\""+radio+"\" stroke=\"black\" stroke-width=\"1\" fill=\""+fill+"\" />\n";
       String texto="<text fill='"+color+"' font-family='sans-serif' font-size='9' x=\'"+x+"\' y=\'"+y+"\' text-anchor=\'middle\'>"+nombre+"</text>\n";
    
       return circulo+texto;
    }
}