package mx.unam.ciencias.edd.proyecto3;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.util.NoSuchElementException;

import mx.unam.ciencias.edd.*;

     
   /**
    *<p> Clase GraficaSVG </p>
    *
    *Permite crear una gráfica de paste o barras, dentro contiene algunas
    *subclases que permiten evaluar el comportamiento de estas gráficas
    *
    *
    *@author Ian García
    *@since 12/05/2020
    *@version 2.0
    **/

  public class GraficaSVG{
    
   
   /**
    *<p> Clase Rebanada - Comparable</p>
    *
    *Nace por la necesidad de modelar a una rebanada de la
    *gráfica de Paste (Pie Chart)
    *
    *
    *@author Ian García
    *@since 18/05/2020
    *@version 2.0
    **/
    public class Rebanada implements Comparable<Rebanada>{

        /**Lista de Valor Graficable**/
        private Lista<ValorGraficable> lista;
        /**Devuelve el porcentaje propio de la rebanada**/      
        private double porcentaje;
        /**Devuelve el valor graficable**/
        private ValorGraficable v;
        /**Color asiganado**/
        private String color;
        /**
        *Constructor de rebanada convierte un valor graficable en una rebanada
        *@param v ValorGraficable
        */
        Rebanada(ValorGraficable v){
            this.v=v;
            double p=v.getPorcentaje(); 
            this.porcentaje=(p*31.42)/100.00;
        }

        /**
        *Regresa el valor graficable
        *@return this.v ValorGraficable ingresado
        *
        **/
        public ValorGraficable getValorGraficable(){
            return this.v;
        }
        /**
        *Regresa el porcentaje contra para volverlo una rebanada de Pastel
        *@return porcentajes
        **/
        public double getPorcentaje(){
            return porcentaje;
        }

        /**
        *Este método es solo un agregado para poder modificar el color
        *en caso de ser requerido
        *@param color String de color
        */
        public void setColor(String color){
            this.color=color;
        }

        /**
        *Devuelve el color de acuerdo a la i ingresada
        *@param i entero de ubicación
        */
        public String getColor(int i){
        ColorValorGraficable color= new ColorValorGraficable(i);
        return color.getColorNumerico();
        }
        /**
        *Regresa el color de acuerdo a una dispersión del porcentaje 
        *@return c.getColorNumerico()
        */
        public String getColor(){
        Dispersor<String> djb=FabricaDispersores.dispersorCadena(AlgoritmoDispersor.DJB_STRING);
            int a = djb.dispersa(String.valueOf(porcentaje)+ this.getValorGraficable().getPalabra().toString());
            ColorValorGraficable c= new ColorValorGraficable(a);
            return c.getColorNumerico();
        }
        /**
        *Asigna porcentaje a la rebanada
        *@return porcentaje porcentaje a asignar
        */
          public void setPorcentaje(double porcentaje){
            this.porcentaje=porcentaje;
        }
        /**
        *Devuelve si un objeto Rebanada es igual a otro
        *@param o Objecto
        */
        @Override public boolean equals(Object o){
            if(o==null || o.getClass() != this.getClass())return false;
            Rebanada r= (Rebanada) o;
            return this.compareTo(r)==0 && this.v.equals(r.v);
        }

        /**
        *Regresa el valor del porcentaje -100 que permite graficar
        *@param r Rebanada
        *@return this.porcentaje-r.porcentaje
        */

        @Override public int compareTo(Rebanada r){
            return (int) Math.round(this.getPorcentaje()-r.getPorcentaje());
        }



    }




    /**
    *Clase FabricaGrafica 
    *Esta clase se crea con necesidad de tener un centro de proceso para generar 
    *el codigo de pertinente a cada tipo de gráfica
    *@author Ian_Garcia
    *@since 15/05/2019
    */
    public class FabricaGrafica{
     /**Lista de valores para gráficas**/
  private Lista<ValorGraficable> v;
    /**Lista de valores para gráficas**/
  private Lista<Palabra> palabra;

        /**
        *Constructor de Fabrica de Graficas 
        *@param v Lista<ValorGraficable>
        */
        FabricaGrafica(Lista<ValorGraficable> v){
            this.v=v;
        }
        /**
        *COnstructor de las Fábricas de Graficas
        *@param v Lista<ValorGraficable>
        * @param palabra Lista<Palabra>
        */
        FabricaGrafica(Lista<ValorGraficable> v , Lista<Palabra> palabra){
            this.v=v;
            this.palabra=palabra;
        }
        /**
        *Fabrica  una grafica de Barras
        *@return c condigo de Conformación SVG 600 x alturaY  
        */   
        public String fabricaGraficaBarras(){
            String codigo="";
            String texto="";
            GraficaSVG g= new GraficaSVG(v);
            int x=10;
            int y=5;
            int y2=10;
            for(ValorGraficable p: v){
                codigo+=g.generaRectangulo(p,y);
                y+=20;
            }
            y-=20;
            String linea="\n<line x1=\'"+x+"\' y1='5' x2=\'"+x+"\' y2=\'"+y+"\' stroke='black' stroke-width='1' />";
            y+=40;
            String text="\n<text fill=\'black\' font-family=\'sans-serif\' font-size=\'20\' x='"+290+"' y=\'"+y+"\' text-anchor=\'middle\'>Gráfica de Barras</text>\n";
            codigo+=linea+text;
            String c=g.conformaCodigoSVG(600, y+60,codigo )+"\n";     
            return c;
        }

        /**
        *Permite graficar una grafica de Pastel
        *@return g.generaPieChart(600, 600, codigo) imagen svg de 600x600
        */
        public String fabricaGraficaPastel(){
            String codigo="";
            Lista<Rebanada> rebanada=new Lista<>();
            for(ValorGraficable valor: v)
                rebanada.agrega(new Rebanada(valor));
            rebanada= Lista.mergeSort(rebanada).reversa();
            GraficaSVG g= new GraficaSVG(v);
            for(int i=0; i<rebanada.getElementos()-1;i++ ){
              
                codigo+= g.generaRebanada(rebanada.get(i),rebanada.get(i+1),i);
            
            }
            return g.generaPieChart(600, 600, codigo);


        }


        /**
        *Obtiene las concurrencias de cada palabra
        *@return text código de Inserción
        */
        public String fabricaConteoIndividual(){
            String text="";
            for(ValorGraficable word: v){
                text+= "<h2 class =\"why-item__title\"><b>  "+word.getPalabra().toString()+"</br></b>\n";
                   text+= "Concurrencias:"+word.getPalabra().getElementos()+"</br>\n";

                    text+="Porcentaje respecto al total: "+ String.format("%.1f", word.getPorcentaje())+"%</h2></div>"; 
                }
            
            return text;
            }
        }

    
    
 
    /**Lista de Palabras**/
    private Lista<ValorGraficable> valores;
    /**Numero de elementos**/
    private ValorGraficable valor;
    /**Constante de reducción para Grafica de pastel**/
    private double REDUCCION=31.42;
    /**Generación de texto para Gráfica de pastel**/
    private Lista<String> textoPieChart;
    /**Lista de ArchivosHTML**/
    private Lista<ArchivoHTML> arch;
    /**
    *Constructor GraficasSVG
    *@param lista Lista de Valores Graficables
    */
    GraficaSVG( Lista<ValorGraficable> lista){
       this.valores=lista;
       textoPieChart=new Lista<>();
    }

    /**
    *Constructor de GraficasSVG
    *@param valor  Valor graficable 
    */
    GraficaSVG(ValorGraficable valor){
       this.valor=valor;
       textoPieChart=new Lista<>();
    }
    /**
    *Constructor de GraficasSVG
    *@param valor  Valor graficable 
    */
    GraficaSVG(){
        textoPieChart=new Lista<>();
    }
    /**
    *Constructor de Graficas SVG
    *@param arch lista de Archivos
    *@param a  solo nos permite diferencias la lista arch de lista V. 
    *Graficable
    */
    GraficaSVG(Lista<ArchivoHTML> arch, int a){
        this.arch=arch;
    }



    /**
    *Devuelve el código plegable en un archivo HTML
    *@return codigo Código de la gráfica De Barras
    */
    public String getGraficaBarras(){
        Lista<ValorGraficable> l= reorganiza();
        FabricaGrafica fabrica= new FabricaGrafica(l);
        return fabrica.fabricaGraficaBarras();
    }



    /**
    *DEvuelve la gráfica de Pastel
    *@return codigo
    */
    public String getPieChart(){
        Lista<ValorGraficable> l= reorganiza();
        FabricaGrafica fabrica= new FabricaGrafica(l);
        return fabrica.fabricaGraficaPastel();
    }


    private int totalElementos(){
        int i=0;
        for(ValorGraficable v: valores)
            i+=v.getPalabra().getElementos();
        return i;
    }

    /***
    *DEvuelve una lista ordenada del TOP 9 de de las 
    *palabras más comunes, aunada al numero 10 de otras Palabras
    *un conglomerado de palabras con menor valor no onsideradas en el top 9
    * ordenadas 
    *@return valores|top  Lista de Valor graficable Ordenada
    */

    private Lista<ValorGraficable> reorganiza(){
        if(valores.getElementos()<15)return Lista.mergeSort(valores).reversa();//.reversa();

        valores=Lista.mergeSort(valores).reversa();

        Lista<ValorGraficable> top= new Lista<>();
        int resto=0;
        for(int i=0; i<=15; i++){
            top.agrega(valores.get(i));
            resto+=valores.get(i).getPalabra().getElementos();
        }
        int absoluto=totalElementos();

        int total=absoluto - resto;
        Palabra otrasPalabras=new Palabra("otras_Palabras",total);
        ValorGraficable valore = new ValorGraficable(otrasPalabras, absoluto);
        top.agrega(valore);

        return Lista.mergeSort(top);
    }


    private Lista<ValorGraficable> getTop(){
        Lista<ValorGraficable> top= new Lista<>();
      
        for(int i=0; i<=15; i++)
            top.agrega(valores.get(i));
            
        top=Lista.mergeSort(top);
        return top.reversa();
    }
    /**
    *Devuelve el conteo a cada palabra listo para insertarse en html a ArchivoHTML
    *@return #link fabrica.fabricaConteoIndividual()
    */
    public String getConteoPalabras(){
       FabricaGrafica fabrica= new FabricaGrafica(valores);
        return fabrica.fabricaConteoIndividual();
    }   



    private String generaDatosGraficas(ValorGraficable v, int x , String color){
        float f=(float) v.getPorcentaje();
        String s=String.format("%.1f", f);
        y+=10;
       int y2=y-10;
        String palabra=v.getPalabra().toString().equals("otraspalabras")?"Otras Palabras":v.getPalabra().toString();
        String  alfa = "\n<rect x=\'"+455+"\' y=\'"+y2+"\' width=\""+10+"\" height=\""+ 10+"\" stroke=\"black\" fill=\""+color+"\" stroke-width=\"1\"/>\n";
      String texto="\n<text fill=\'black\' font-family=\'sans-serif\' font-size=\'10\' x=\'"+x+"\' y=\'"+150+"\' text-anchor=\'middle\'>"+palabra+" ["+s+"%]</text>\n";
      return texto+alfa;
    }
 
    private String generaDatosGraficas(ValorGraficable v, int y , ColorValorGraficable color){
        float f=(float) v.getPorcentaje();
        String s=String.format("%.1f", f);
        y+=10;
       int y2=y-10;


        String palabra=v.getPalabra().toString().equals("otraspalabras")? "Otras Palabras":v.getPalabra().toString();
        String  alfa = "\n<rect x=\'"+455+"\' y=\'"+y2+"\' width=\""+10+"\" height=\""+ 10+"\" stroke=\"black\" fill=\""+color.getColor()+"\" stroke-width=\"1\"/>\n";
      String texto="\n<text fill=\'black\' font-family=\'sans-serif\' font-size=\'10\' x='520' y=\'"+y+"\' text-anchor=\'middle\'>"+palabra+" ["+s+"%]</text>\n";
      return texto+alfa;
    }

  
    private String generaDatosGraficas(ValorGraficable v, int y , int pos){
        float f=(float) v.getPorcentaje();
        String s=String.format("%.1f", f);
        y+=10;
       int y2=y-10;

       Rebanada r=new Rebanada(v);

        String palabra=v.getPalabra().toString().equals("otraspalabras")? "Otras Palabras":v.getPalabra().toString();
        String  alfa = "\n<rect x=\'"+455+"\' y=\'"+y2+"\' width=\""+10+"\" height=\""+ 10+"\" stroke=\"black\" fill=\""+r.getColor(pos)+"\" stroke-width=\"1\"/>\n";
      String texto="\n<text fill=\'black\' font-family=\'sans-serif\' font-size=\'10\' x='520' y=\'"+y+"\' text-anchor=\'middle\'>"+palabra+" ["+s+"%]</text>\n";
      return texto+alfa;
    }

    private String generaRectangulo(ValorGraficable v, int y){
        /**
        *100.0% ------ 300 px
        *x= (x*300)/100.0%
        */
        double ancho= (v.getPorcentaje()*450)/100.0;
        String w= v.getPalabra().toString();
        ColorValorGraficable color = new ColorValorGraficable(v);
        String  alfa = "\n<rect x=\'"+10+"\' y=\'"+y+"\' width=\""+ancho+"\" height=\""+ 15+"\" stroke=\"black\" fill=\""+color.getColor()+"\" stroke-width=\"1\"/>\n";
        alfa+=generaDatosGraficas(v,y,color); 
     return alfa;
    }
    

    private String conformaCodigoSVG(int width,int height, String codigo){
        String code="<?xml version=\'1.0\' encoding=\'UTF-8\' ?>\n";
      code+="<svg xmlns=\"http://www.w3.org/2000/svg\" width=\'"+width+"\' height=\'"+height+"\'>\n<g>";
      code+=codigo;
      code+= "</g>\n</svg>";
        return code;
    }
   
   /**Permite espaciaar ls acotaciones de la grafica de paste**/ 
    private int y= 20;
    private String generaRebanada(Rebanada r1, Rebanada r2, int i){

 
        String rebanadaDecorte="<circle r=\"5\" cx=\"10\" cy=\"10\" fill=\"transparent\"\n";
          rebanadaDecorte+="\tstroke=\""+r1.getColor(i)+"\"\n";
          rebanadaDecorte+="\tstroke-width=\"10\"\n";
         REDUCCION-=r1.getPorcentaje();
          rebanadaDecorte+="\tstroke-dasharray=\""+REDUCCION +" 31.42\"\n";
          rebanadaDecorte+="\ttransform=\"rotate(-90) translate(-20)\"/>\n";
          
            y++;
        return rebanadaDecorte;
    }

       private String generaPieChart(int width, int height, String codigo){
          String code="<?xml version=\'1.0\' encoding=\'UTF-8\'  ?>\n"; //\" 
      code+="<svg xmlns=\"http://www.w3.org/2000/svg\" height=\""+height+"\" width=\""+width+"\" viewBox=\"0 0 20 20\">\n";
      code+="<circle r=\"10\" cx=\"10\" cy=\"10\" fill=\""+"RoyalBlue"+"\" \n/>";
      code+=codigo;
      code+= "</svg>";
        return code;
    }

    /**
    *Genera las acotaciones de la gráfica SVG[pastel]
    *@return  #link ConformaCodigoSVG
    */
        public String getTextoPieChart(){
           Lista<ValorGraficable> local=reorganiza();
           local=local.mergeSort(local).reversa();
           int x=15;
            String devuelve="";
            for(int i=0; i<local.getElementos();i++){
                if(local.get(i).getPalabra().toString().equals("otraspalabras") || i==0){
                    ColorValorGraficable color=new ColorValorGraficable("RoyalBlue");
                    devuelve+=generaDatosGraficas(local.get(i),x,color);
                    
                }else{
                    
                     devuelve+=generaDatosGraficas(local.get(i),x, i-1);
                }
                x+=15;
            }
         return conformaCodigoSVG(600 ,600 ,devuelve);
        }
    
       /**
       *Regresa código svg envuelto listo para volverse archivos
       *@param width ancho
       *@param codigo codigoSVG
       */ 
      protected String envuelveCodigo(int width,String codigo){
            return conformaCodigoSVG(width, 600, codigo);
        }
  }