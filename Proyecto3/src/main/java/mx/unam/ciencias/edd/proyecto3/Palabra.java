package mx.unam.ciencias.edd.proyecto3;
import java.text.Collator;
import java.text.Normalizer;

/**
  *<p>Clase Palabra</p>
  *
  *<p> Esta clase permite ingresar a los caracteres recibidos y comparar por
  *tipo de caracter donde A=a=á anulando las diferencias generadas por ASCII
  * permite ingresarlo, leerlo y sobreescribe compareTo e equals
  *al igual para evitar generar n-vaces dicha palabra solo indicamos sus veces
  *de existencia a traves de el numero de elementos
  *</p>
  *
  *
  *@author Ian García
  *@since 11/05/2020
  *@version 1.0.0
  *
  */
  public class Palabra implements Comparable<Palabra>{
    /**String que contiene a la palabra**/
  String palabra;
  /**No. de palabras iguales a la palabra**/
  int elementos;
  /** Objeto tipo Comparable**/
  Collator comparador= Collator.getInstance();

    /**
    *Constructor público cadena
    *@param palabra String de datos de comparación
    *
    */
   Palabra(String palabra){
    this.palabra=palabra.toLowerCase();
    elementos=1;
  }

 /**
    *Constructor público cadena
    *@param palabra String de datos de comparación
    *@param elementos elementos 
    */
  Palabra(String palabra, int elementos){
  	this(palabra);
  	this.elementos=elementos;
  }



  	/**
  	*Recibe los elementos iguales a Palabra; 
  	*sustituye el numero de elementos con el valor ingresado
  	*@param elementos
  	*/
  	public void setElementos(int elementos){
  		this.elementos=elementos;
  	}

    /**
    *Recibe el numero de palabras iguales a ella
    *@param i Numero a sumar
    */
    public void aumentaElementos(int i){
      elementos+=i;
    }
    /**
    *Regresa la cantidad de palabras ya tomadas
    *@return elementos palabra veces repetida
    */
    public int getElementos(){
      return this.elementos;
    }


  private String changeable(){
    String s= Normalizer.normalize(palabra, Normalizer.Form.NFD);
    s=s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    s=s.replaceAll("[^\\dA-Za-z]", "");
    return s;
  }


  @Override public boolean equals(Object o){
   if (o == null || getClass() != o.getClass())
      return false;
  @SuppressWarnings("unchecked") Palabra palabra = (Palabra)o;

  comparador.setStrength(Collator.PRIMARY);
  String here= this.changeable();
  String strange= palabra.changeable();
  return comparador.compare(here, strange)==0;

  }

  	@Override public String toString(){
  		return changeable();
  	}


  	/**
  	*Regresa el modelo original de las Palabras
  	*@return palabra
  	*/
  	public String getOriginal(){
  		return this.palabra;
  	}

  /**
  *Compara dos elementos tipo Palabra a traves del numero de elementos,
  *quien tiene más elementos 
  *
  */
  @Override public int compareTo(Palabra p){
  return  this.getElementos()-p.getElementos();
  }
}
