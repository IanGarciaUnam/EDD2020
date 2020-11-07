package mx.unam.ciencias.edd.proyecto3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.File;
import java.util.Iterator;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.FabricaDispersores;
import mx.unam.ciencias.edd.Dispersor;
import mx.unam.ciencias.edd.Dispersores;
import mx.unam.ciencias.edd.AlgoritmoDispersor;
/**
    *<p> Clase Entrada</p>
    *
    *Recibe los argumentos y los procesa como archivos y directorio
    *asimismo el constructor verifica el directorio y que este sea pertinentemente nombrado
    *
    *
    *
    *
    *
    *@author Ian García
    *@since 18/05/2020
    *@version 3.0
    **/

  public class Entrada{

    /**Bandera de directorio**/
    boolean hayDirectorio;
    /**Nombre del directorio**/
    private String directorio;
    /**
    *Constructor de la clase Entrada, inicializa la Lista de Palabras
    *@param hayDirectorio boolean
    *@param directorio nombre del directorio
    */
    Entrada(boolean hayDirectorio, String directorio){

      if(directorio==null){
      	System.err.println("Tras la bandera \'-0\' deberas ingresar el nombre de tu directorio");
      	System.exit(1);
      }

      this.hayDirectorio=hayDirectorio;

      File dir= new File(directorio);
      if(dir.exists() && !dir.isDirectory()){
      	System.err.println("El directorio "+directorio+" No es un directorio");
      	System.err.println("Cambie de directorio o tecleé alguno diferente");
      	System.exit(1);
      }else if(dir.exists() && dir.isDirectory()){
      	if(!dir.canWrite()){
      		System.err.println("No cuentas con los permisos necesarios para ocupar el directorio "+ directorio);
      		System.err.println("Cambie de directorio o tecleé alguno diferente");
      		System.exit(1);
      	}
      	
      }
      this.directorio=directorio;

      
    }


    /**
    *Itera archivo por archivo para vaciar la Lista de palabras
    *@param args argumentos recibidos por terminal
    */
    public void entradaArgs(String[] args){


		Lista<ArchivoHTML> archivosHTML= new Lista<ArchivoHTML>();
		Diccionario<String,Integer> d=null;
       for(String s: args){
       	 if(!s.equals("-o") && !s.equals(directorio )){
       		//MODIFICAR NO OMITE CAMBIOS ^ "" EN PALABRAS
       	 	 d=getDiccionario(s);
       	 	 
       	 	ArchivoHTML a=new ArchivoHTML(s, d, directorio);
       	 	archivosHTML.agrega(a); 	
       	 }
       	  
	      	
	    }
	    

      if(archivosHTML.esVacia()){
        System.err.println("No has ingresado archivos [ERROR]");
        System.exit(1);
      }

	    escribeDirectorio();
	    FabricaHTML fabrica= new FabricaHTML(archivosHTML, directorio);
	    fabrica.generaIndex();
	    fabrica.generaHTML();



	}




    private Diccionario<String, Integer> getDiccionario(String archivo){
    			AlgoritmoDispersor a = AlgoritmoDispersor.BJ_STRING;
        		Dispersor<String> bj = FabricaDispersores.dispersorCadena(a);//Configura Dispersor
    	      Diccionario<String, Integer> d= new Diccionario<String, Integer>(bj);
      try{
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String firstLecture;
        while((firstLecture=br.readLine()) != null){
          String [] toRead=separador(firstLecture);
          for(String s:toRead){
          	s=s.trim();
          	if(!s.equals("")){
          		Palabra temp= new Palabra(s);
          	if(d.contiene(temp.toString())){
          		
           		d.agrega(temp.toString(),d.get(temp.toString())+1);
           	}else{	
           	d.agrega(temp.toString(),1);
           	}	
          }    	
        }
      }

        //System.out.println(d.toString());
        br.close();

      }catch(IOException io){
        System.err.println("Error al leer el archivo: "+ archivo+" Posibles permisos necesarios");
        
        System.exit(1);
      }

      return d;
    }



        

    private String[] separador(String s){
      String [] nuevo= s.split(" ");
      return nuevo;
    }


    private void escribeDirectorio(){
      if(hayDirectorio && directorio != null){
          File f = new File(directorio);
          if(f.exists()){
            if(f.canWrite())
              f.mkdir();
            else{
              System.err.println("No cuentas con permisos de escritura para el directorio:"+ directorio);
              return;
            }

          }else
            f.mkdirs();
      }
    }



  


  }
