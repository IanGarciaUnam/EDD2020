package mx.unam.ciencias.edd.proyecto3;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Diccionario;
   /**
    *<p> Clase ValorGraficable</p>
    *Permite acceder a los valores para ser graficados , estos son comparables
    *
    
    *@author Ian García
    *@since 1/06/2020
    *@version 1.0
    **/
  public class ValorGraficable implements Comparable<ValorGraficable>{

    /**Lista de archivos a directorio**/
    private Diccionario<String, Integer> diccionario ;
    /**Lista de Palabras**/
    private Lista<Palabra> lista;
    /**Numero de elementos**/
    private double elementos;
    /**Palabra **/
    private Palabra palabra;
    /**Porcentaje**/
    private double porcentaje;
    /** Palabra en String**/
    private String color;

    /**
    *Cosntructor con el nombre del archivo
    *
    */
    ValorGraficable(Lista<Palabra> lista,int elementos){    
        this.elementos=Double.valueOf(elementos);
        this.lista=lista;
    }
    ValorGraficable(Palabra palabra, int elementos){
        this.palabra=palabra;
        this.elementos=Double.valueOf(elementos);
    }

    /**
    *Obtiene el porcentaje del String en un Diccionario
    *@return porciento porcentaje respecto elementos en diccionario
    */
    public double getPorcentaje(){
         porcentaje=(palabra.getElementos()*100.00)/elementos;
        return this.porcentaje;
    }

    /**
    *Devuelve la palabra que forma al valor graficable
    *@return palabra Palabra 
    */
    public Palabra getPalabra(){
        return this.palabra;
    }

    /**
    *Devuelve los elementos totales que conforman al archivo
    *@return elementos
    */
    public Double getElementos(){
        return this.elementos;
    }

    
    /**
    *Devuelve una lista de Valorgraficable de mayor a menor
    *@return l lista de ValorGraficable
    */
    public Lista<ValorGraficable> getListaValorGraficable(){
        Lista<ValorGraficable> l= new Lista<>();

        for(Palabra p: lista){
            l.agrega(new ValorGraficable(p, (int)elementos));
        }
        return Lista.mergeSort(l).reversa();
    }
    /**
    *Compara los elementos de tipo valorGraficable
    *a través de sus porcentajes
    *
    */  
    @Override public int compareTo(ValorGraficable v){/**Revisión**/
        return this.getPalabra().getElementos()-v.getPalabra().getElementos();
 //(int)v.getPorcentaje()-(int)this.getPorcentaje();
    }


    
  }

