package mx.unam.ciencias.edd.proyecto3;

  /**
    *<p> Clase proyecto3 - main/p>
    *
    *Contador de palabras HTML en esta clase solo se recibe 
    * los parametros de archivos la bandera -o seguida del nombre de directorio
    *El objetivo es contar las palabras y realizar un reporte en html 
    *por cada archivo y su correspondientes graficas, arboles AVL y ArbolesROjiNegros
    *
    *
    *
    *
    *@author Ian García
    *@since 10/05/2020
    *@version 2.0
    **/
  public class proyecto3{

    public static void main(String[] args){
    	if( args.length==0 || args[0]==null ){
    		proyecto3.ayuda();
    		return;
    	}

      boolean hayDirectorio=hayDirectorio(args);
    	if(!hayDirectorio){
    		System.err.println("Debe ingresar la bandera \'-o\' seguida del nombre del Directorio");
    		return;
	    	}
      Entrada e= new Entrada(hayDirectorio, getDirectorio(args));
      e.entradaArgs(args);

    }
    private static void ayuda(){
    	System.out.println("Usó:  java -jar target/proyecto3 <Archivos> <Archivos> -o <Directorio> ");
    	System.out.println("Tras la bandera \'-o\' debera escirbir un Directorio\n asegurese de contar con los permisos necesarios,\n en caso de que el directorio no exista se creará un directorio con el mismo nombre");
    }

    private static boolean hayDirectorio(String[] args){
      for(String s:args)
        if(s.equals("-o"))return true;
      return false;
    }

    private static String getDirectorio(String[] args){
      if(!hayDirectorio(args)) return null;

      for(int i=0; i<args.length; i++)
        if(args[i].equals("-o")){
          i++;
            return i>=args.length? null:args[i];
        }
      return null;
    }


  

  }
