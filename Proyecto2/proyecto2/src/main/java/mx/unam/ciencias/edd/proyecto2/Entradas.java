package mx.unam.ciencias.edd.proyecto2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import mx.unam.ciencias.edd.Lista;



    /**
    *<p> Clase Entradas</p>
    *<p> Esta clase nos permite manejar el tipo de entrada seleccionado por el
    *usuario ya sea la entrada Standar o la entrada por argumentos</p>
    *
    *@author Ian García
    *@since  8/04/2020
    *@version 2.0
    **/



public class Entradas{
/** Lista con elementos string  con los nombres de los archivos**/
  private String file;
  /**Lista con los elementos que serán graficado**/
  private Lista<Integer> enteros;

  private String clase;




  /**
  *<p>Constructor de la clase Entradas</p>
  *Nos permite Construir  Entrada por argumentos
  *@param files Lista de nombres de archivo tipo String
  *@param content Lista de contenido de cada uno de los archivos
  *
  */
  Entradas(String file){
    this.file= file;
  }
  /**
  *<p>Constructor vacío</p>
  *Constructor para la entrada estandar
  *
  **/
  Entradas(){}

  /**
  *<p> ingresa los archivos de la entrada standard y finalmente son impresos y/o escritos</p>
  *
  *
  */

  public void entradaArgs(){

    String archivo="";
    try{

      BufferedReader br = new BufferedReader(new FileReader(archivo=file));

      if(!br.ready()){
        System.out.println("Acceso al archivo no garantizado");
        return;
      }

      String g;
      String result="";
      Lista<String> elementos=new Lista<>();

      while((g=br.readLine())!= null){
        result+= g.trim()+"\n";
        }
        char [] debug=result.toCharArray();




        String clase="";

        for(int i=0; i< debug.length ; i++){ // eliminar  comentarios #
          if(debug[i] == '#'){
            while(debug[i]!=10){
              debug[i]=32;
              i++;
            }
          }

          if(debug[i]!=32 && !isNumeric(String.valueOf(debug[i])) && debug[i] != '#' && debug[i]!=10 && debug[i]!='-'){//escribir tipo de clase
            clase+=String.valueOf(debug[i]);
              int adelante= i+1;
              if(adelante<debug.length){
                if(isNumeric(String.valueOf(debug[adelante])))
                  if(debug[i]!= 32 && debug[i]!=10 ){
                    System.out.println("No se puede encontrar el símbolo en:");
                    System.out.println(clase.trim() + String.valueOf(debug[adelante]).trim());
                    return;
                  }
              }
            debug[i]=32;// lo escrito como clase se transforma en espacio tras ser agregado como nombre de lcase
          }

          if(debug[i]==10 ){// los saltos de linea ahora serán espacios
            debug[i]=32;
          }
        }

        clase=clase.trim();
        String enteros=String.valueOf(debug).trim();//quitamos los espacios de delante y tras para poder analizar los caracteres
        char[] interos=enteros.toCharArray();



        Lista<Integer> lista_enteros= new Lista<>();

    lista_enteros=auxListaEnteros(interos, enteros);
    if(lista_enteros==null && preparacionClase(clase)){
      Escribano e= new Escribano();
      e.muestraVacio();
      return;
    }else if(lista_enteros==null && !preparacionClase(clase)){
      System.out.println("Error al escribir" + clase);
      return;
    }


        setListaEnteros(lista_enteros);// Guardamos nuestra lista con enteros
        setClase(clase);
        ultimaEvaluacionClase();



        br.close();

        Escribano e = new Escribano(getClase(), getListaEnteros());
        e.escribe();

        }catch(IOException io){
      System.out.println("*************Archivo dañado o ilegible ***********");
      System.err.println("Para más ayuda  java -jar target/proyecto2.jar --h");
    }




  }

  /**
  *Comprobamos si ya se logro escribir la clase que se desea
  *
  **/
  private boolean preparacionClase(String clase){

    switch(clase){

      case "Lista":return true;

      case "Pila": return true;

      case "Cola": return true;

      case "ArbolBinarioCompleto": return true;

      case "ArbolBinarioOrdenado": return true;

      case  "ArbolRojinegro": return true;

      case "ArbolAVL": return true;

      case "MonticuloMinimo": return true;

      case "Grafica": return true;

      default:
      if(clase.equals("") || clase.equals(" ")){
          System.out.println("No has insertado un valor de clase");
          setClase("CLASE VACÍA");
      }


      return false;


    }



  }


    public Lista<Integer> getListaEnteros(){
      return this.enteros;
    }

    private void setListaEnteros(Lista<Integer> l){
      this.enteros=l;
    }

    /**
    *<p>Regresa el nombre de la clase que se solicita elaborar y null si no se encuentra</p>
    *@return clase el nombre de la clase si es que existe
    **/
    public String getClase(){
      return clase;
    }

    private void setClase(String clase){
      this.clase=clase;

    }

    //Comprobamos que no haya letras o cualquier caracter diferente a numeros
    private void comprobator(char []a){
      for(char i: a){
        if(i!=32 && i!= 10 && !isNumeric(String.valueOf(i))){
          System.out.println("Error en el archivo- No entero escrito");
          System.out.println(">>"+String.valueOf(a));
          System.out.println(">>[ERROR]>>");
          return;
        }


      }

    }

    private  boolean isNumeric(String cadena) {

        boolean resultado;

        try {
            Integer.parseInt(cadena);
            resultado = true;
        } catch (NumberFormatException excepcion) {
            resultado = false;
        }

        return resultado;
    }

  /**
  *Ingresa los archivos y su contenido para ser leído y ordenado correctamente
  *
  */
  public  void entradaEstandar(){
    try{
      BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
      if(!br.ready()){
        System.out.println("Acceso al archivo no garantizado");
        return;
      }

      String g;
      String result="";
      Lista<String> elementos=new Lista<>();

      while((g=br.readLine())!= null){
        result+= g.trim()+"\n";
        }

        br.close();
        char [] debug=result.toCharArray();

        String clase="";

        for(int i=0; i< debug.length ; i++){ // eliminar  comentarios
          if(debug[i] == '#'){
            while(debug[i]!=10){
              debug[i]=32;
              i++;
            }
          }

          if(debug[i]!=32 && !isNumeric(String.valueOf(debug[i]))  && debug[i] != '-' && debug[i] != '#' && debug[i]!=10){
            clase+=String.valueOf(debug[i]);
            int adelante= i+1;
            if(adelante<debug.length){ // Verificamos el posible error de que se encuentren juntos un numero y un caracter del nombre de la clase

              if(isNumeric(String.valueOf(debug[adelante])))
                if(debug[i]!= 32 && debug[i]!=10 && debug[i] != '-'){
                  System.out.println("No se puede encontrar el símbolo en:");
                  System.out.println(clase.trim() + String.valueOf(debug[adelante]).trim());
                  return;
                }
            }


            debug[i]=32;
          }

          if(debug[i]==10){
            debug[i]=32;
          }
        }
        clase=clase.trim();

        String enteros=String.valueOf(debug).trim();//quitamos los espacios de delante y tras para poder analizar los caracteres
        char[] interos=enteros.toCharArray();
        Lista<Integer> lista_enteros= new Lista<>();

        lista_enteros=auxListaEnteros(interos,enteros);
          if(lista_enteros==null && preparacionClase(clase)){
            Escribano e= new Escribano();
            e.muestraVacio();
            return;
          }else if(lista_enteros==null && !preparacionClase(clase)){
            System.out.println("Error al escribir" + clase);
            return;
          }


          setListaEnteros(lista_enteros);// Guardamos nuestra lista con enteros
          setClase(clase);
          ultimaEvaluacionClase();
        Escribano e = new Escribano(getClase(), getListaEnteros());
        e.escribe();

    }catch(IOException ioe){
        System.out.println("Falla en la entrada estandar -- Posible Archivo Inexistente o Ilegible");
        System.out.println("Para más ayuda consulta java -jar target/proyecto2 --h;");
        return;
        }


    }



          private Lista<Integer> auxListaEnteros(char [] interos, String enteros){
            String numero="";
            Lista<Integer> lista_enteros= new Lista<>();

            if(interos.length==0){
              return null;
          }


            if(!isNumeric(String.valueOf(interos[0]))){
                  String error = (enteros.length()-1>0)? enteros.substring(enteros.length()-2,enteros.length()) :enteros.substring(enteros.length());
              System.out.println("Error en ultimos caracteres legibles :" +"\"" +error+"\"");
              return null;
            }


            for(int i=0; i<interos.length ; i++){
              if(!isNumeric(String.valueOf(interos[i])) && interos[i]!=32 && interos[i]!='-'){
                System.out.println("No has ingresado un número en tu documento");
                System.out.println("Para mas ayuda utiliza java -jar target/proyecto2.jar --h");
                return null;
              }
              if(isNumeric(String.valueOf(interos[i])) && interos[i]!=32 && interos[i]!=10 || interos[i]=='-')
              numero+=String.valueOf(interos[i]);
                if(i==interos.length-1 && interos[i]=='-'){
                  System.out.println("Caracter no númerico en \'-\'");
                  System.out.println("Para más ayuda consulta java -jar target/proyecto2.jar --h");
                  return null;
                }



                if(!isNumeric(String.valueOf(interos[i])) && numero != "" && interos[i]!='-' ||  (isNumeric(numero) && i==interos.length-1))
                    if(isNumeric(numero)){
                      lista_enteros.agrega(Integer.parseInt(numero));
                      numero="";
                    }else{
                        String alert="Se ha ingresado un caracter alfanumerico que no representa a un entero\n Revisa :  \""+numero+ "\"";
                        String javaCommon="Para mas ayuda consulta java -jar target/proyecto2 --h";
                        System.out.println(alert+"\n"+ javaCommon);
                        return null;
                      }


            }


            return lista_enteros;

          }


          private void ultimaEvaluacionClase(){

            if(!preparacionClase(getClase())){
              System.out.println("Haz escritos caracteres distintios a los requeridos");
              System.out.println("Error en: "+ getClase());
              System.out.println("Para más ayuda consulta java -jar target/proyecto2 --h");
              return;
            }

          }




      public static void impresionAyuda(){
          System.out.println("Uso:");
          System.out.println("java -jar target/proyecto2.jar <Archivo>      -------> 1");
          System.out.println("cat <file> | java -jar target/proyecto2.jar   -------> 2");
          System.out.println("-------------- Solo puedo leer un [1] archivo --------------");
          System.out.println(" Las clases disponibles a graficar son: ");
          System.out.println("Lista");
          System.out.println("Pila");
          System.out.println("Cola");
          System.out.println("ArbolBinarioCompleto");
          System.out.println("ArbolBinarioOrdenado");
          System.out.println("ArbolRojinegro");
          System.out.println("ArbolAVL");
          System.out.println("MonticuloMinimo");
          System.out.println("TU archivo debería usar un formato :");
          System.out.println("Listas 12 2 1 3 4 6");
          System.out.println("#------------> Lo escrito despues de este signo en la misma línea será ignorado");
          System.out.println("Ejemplo:");
          String f = "#Clase:\nCola\n 12 # Agregando elementos\n1 2 3 42 1 2 4\n#Fin";
            String r= "Cola 12 1 2 3 42 1 2 4 ";
          System.out.println(f);
          System.out.println("Equivalente:");
          System.out.println(r);

          return;
      }










  }
