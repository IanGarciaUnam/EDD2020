package mx.unam.ciencias.edd.proyecto1;

import java.io.IOException;

import mx.unam.ciencias.edd.Lista;

/**
 * <p>Proyecto 1: Ordenador léxico-gráficos.</p>
 * <p>  Programa ordenador lexicográfico , recibe uno o más archivos, por la
 * entrada estandar , imprime su salida en la salida estandar</p>
 *@author Ian Israel García Vázquez
 *@version 1.0
 *@since 26/03/2020
 */

 public class proyecto1{

   proyecto1(){}
    /** Lista tipo String para guardar el nombre de los archivos**/
    private static Lista<String> archivos= new Lista<>();
    /** Lista tipo Cadena para guardar las lineas de texto dentro de los archivos**/
    private static Lista<Cadena> texto= new Lista<>();
    /** booleano en caso de que se haya enviado la bandera -r**/
    private static boolean bandera_Reversa=false;
    /** booleano en caso de que se haya enviado la bandera -o**/
    private static boolean b_OperacionDestructiva=false;
    /** booleano en caso de que se haya enviado la bandera --h para recibir ayuda**/
    private static boolean aide=false;
    /**En caso de que se pase mas archivos antes de -o guarda la cantida de archivos previos**/
    static int grande;



    public static void main(String[] args)throws IOException{


      archivosEnParametro(args);

      if(aide){
        System.out.println("=======================Ordenador Léxico-Gráfico Proyecto 1=======================");
        System.out.println("usage:\n ");
        System.out.print("cat <File(s)> | java -jar target/proyecto1.jar [options] <File>\n");
        System.out.print("=====or=======\n java -jar target/proyecto1.jar [options] <File(s)> \n");
        System.out.println("=====or=======\n cat <File(s)> | java -jar target/proyecto1.jar [options] <File>\n\n\n");
        System.out.print("=====or=======\n java -jar target/proyecto1.jar [options] <File> [option] <File> \n");
        System.out.println("Options:");
        System.out.println("-r \t\t After ordering the text line by line it will be returned in reverse by the standard exit");
        System.out.println("-r \t\t Tras ordenar el texto ingresado línea por línea este será devuelto por la salida estandar en reversa");
        System.out.println("-o \t\t It is a destructive operation, the first file will be ordered and written into the second file");
        System.out.println("-o \t\t Es una operación destructiva, el primer archivo o los primeros  serán ordenados y escritos sobre el archivo ubicado despues de la bandera -o\n\n");
        System.out.println("En caso de usar cat <file(s)> | java -jar target/proyecto1.jar <file(s)> solo los archivos que pasen por argumentos serán ordenados");

        return;
      }



      if(archivos.getElementos()==0 )
        {
            Entradas in= new Entradas(archivos, texto, bandera_Reversa, b_OperacionDestructiva);
          in.entradaEstandar();
          return;
        }else if(archivos.getElementos()==1 && b_OperacionDestructiva){
          Entradas en= new Entradas(archivos, texto, bandera_Reversa, b_OperacionDestructiva);
          en.entradaEstandar();
        return;
      }else if(archivos.getElementos()>0){


        if(b_OperacionDestructiva && args[0].equals("-o")){
          System.out.println("El archivo a pasar debe encontrarse antes que el indentificador");
          System.out.println("Para más ayuda java -jar target/proyecto1.jar --h");
          return;
        }
          Entradas un= new Entradas(archivos, texto, bandera_Reversa, b_OperacionDestructiva);
          un.entradaArgs();
          return;

        }






    }


    // Recibe los argumentos del programa y segrega entre las banderas --h -o -r y los archivos
    private static void archivosEnParametro(String[] args){
      int deR=0 ,deO=0;
      for(int i=0; i<args.length ; i++){

          if(args[i].equals("-r")){
            bandera_Reversa=true;
            deR=i;
          }else if(args[i].equals("-o")){
              b_OperacionDestructiva=true;
              deO=i;

          }else if(!args[i].equals("-r") && !args[i].equals("-o") && !args[i].equals("--h")){
            archivos.agrega(args[i]);

          }else if(args[i].equals("--h")){
              aide=true;
          }
        }

            if(!bandera_Reversa && !b_OperacionDestructiva){
              return;
            }else if(deR>deO && bandera_Reversa){

              setGrande(deO);
            }else if(!bandera_Reversa && deO>deR){
                setGrande(deO);
            }else if(deR>0 && deR<deO){
              setGrande(deO-1);
            }
          }






      private static void setGrande(int g){
        grande=g;
      }

      /**
      *Devuelve el numero de archivos a pasar para la entrada por args
      *
      *@return grande entero de ubicación
      */
      public static int getGrande(){
        return grande;
      }







}
