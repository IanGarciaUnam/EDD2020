package mx.unam.ciencias.edd.proyecto3;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import mx.unam.ciencias.edd.*;
import java.util.NoSuchElementException;


  
   /**
    *<p> Clase ColorValorGraficable</p>
    *
    *Si bien existen n-maneras de generar color para elementos SVG
    *Esta clase surge por la necesidad de tener un contro unico sobre los colores y sus tareas
    *(Solo dar color) pero este se otorga de diferentes maneras en Graficas de barras y en gráficas de pastel
    *pues en gráficas de barras no existe problema alguno si nuestros elementos repitieran algun color por ello solo 
    *se opta por asignarlo de acuerdo a una dispersión de la palabra, solo es un acto que parecerá sinestésico al implementarlo.
    *Por otro lado Las gráficas de pastel necesitan que sus colores adyacentes sean diferentes o se mezclarán
    *y perderemo definición sobre los porncentajes, para evitarlo se supondría que al utilizar #getColorNumerico será casi consecuentemente asignado
    *si nuestros números son consecuentemente asignados
    *
    *
    *
    *
    *@author Ian García
    *@since 18/05/2020
    *@version 3.0
    **/

  public class ColorValorGraficable{
    /**Contiene un menu de colores**/
    private String[] color={"#0e053f","#594c90","#1d5cda","#1ddada","#1dda42","#95df8b","#3b77c7","#d7da1d",
                            "#e5a122","#f88826", "#e33806" ,"#d30073","#e306a0","#b93de3"};
    /**COntiene un nuevo menu de Colores más pequeño que color[]**/
    private String[] myColor={"red","white", "tomato", "orange","green", "black" };
    /**ValorGraficable**/
    private ValorGraficable v;
    /**Color nuevo por asignar**/
    private String data;
    /**asigna Colo**/
    private int asigna;
    /**
    *Constructor con el nombre del archivo
    *@param v ValorGraficable
    */
    ColorValorGraficable(ValorGraficable v){   
           this.v=v;
    }

    /**
    *Constructor de colores por usuario
    *@param data color a utilizar
    */
    ColorValorGraficable(String data){
      this.data=data;
      v=null;
    }
    /**
    *COnstructor de COlores por asiganción
    *@param asigna entero
    */
    ColorValorGraficable(int asigna){
      this.asigna=asigna;
    }

   /**
   *Regresa el color del valorGraficable en Hexadecimal
   *@return color[i] color para 
   */
    public String getColor(){


      if(v==null && data !=null)return getColorGenerico();
          int modifica=v.getPalabra().getElementos()*v.getPalabra().toString().length()*(int) v.getPorcentaje();
      
      
      Dispersor<String> djb=FabricaDispersores.dispersorCadena(AlgoritmoDispersor.DJB_STRING);
        int modificador=djb.dispersa(v.getPalabra().toString());
        modificador = modificador<=0? (int) Math.pow(modificador,2):modificador+modifica;
        int i=modificador%(color.length-1);
        return color[i];
    }

    /**
    *Regresa Color para graficas de Pastel asigando por el usuario
    *@return myColor
    */
    private String getColorGenerico(){
      if (data==null)throw new NoSuchElementException("No existe color genérico alguno");
    
      return data;
    }

    /**
    *Asigna Colores de acuerdo al numero ingresado
    *@return color[asigna] asiganción del color
    */
    public String getColorNumerico(){
      if(asigna<color.length-1 && asigna>=0)return color[asigna];

      if(asigna<0)asigna=(int)Math.pow(asigna,2);
      asigna=asigna%(color.length-1);
     
      return color[asigna]; 
    }

  

  }