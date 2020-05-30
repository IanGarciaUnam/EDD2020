package mx.unam.ciencias.edd.proyecto1;
import java.text.Collator;
import java.text.Normalizer;
  /**
    *<p>Clase Cadena</p>
    *
    *<p> Esta clase permite ingresar a los caracteres recibidos y comparar por
    *tipo de caracter donde A=a=á anulando las diferencias generadas por ASCII
    * permite ingresarlo, leerlo y sobreescribe compareTo
    *</p>
    *
    *
    *@author Ian García
    *@since 18/03/2020
    *@version 1.0.0
    *
    */
    public class Cadena implements Comparable<Cadena>{

    String cadena;
      Collator comparador= Collator.getInstance();

      /**
      *Constructor público cadena
      *@param cadena String de datos de comparación
      *
      */
    public Cadena(String cadena){
      this.cadena=cadena;
    }


    @Override public String toString(){
      return cadena;
    }

    private String changeable(){

      String s= Normalizer.normalize(cadena, Normalizer.Form.NFD);
      s=s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
      s=s.replaceAll("[^\\dA-Za-z]", "");
      return s; //this.cadena.replace('ñ','n').replace('á', 'a').replace('é', 'e').replace('í','i').replace('ó','o').replace('ú','u').replaceAll("[^\\dA-Za-z]", "");//reemplazamos n x ñ y los que no son número ni letras por espacios
    }


    @Override public int compareTo(Cadena c){
    comparador.setStrength(Collator.PRIMARY);
    String here= this.changeable();
    String strange= c.changeable();

    return comparador.compare(here, strange);
    }
}
