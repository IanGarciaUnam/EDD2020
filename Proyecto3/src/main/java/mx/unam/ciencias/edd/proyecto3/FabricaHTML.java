package mx.unam.ciencias.edd.proyecto3;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.io.OutputStream;
import mx.unam.ciencias.edd.Lista;
import java.net.URL;


    /**
    *<p>Clase FabricaHTML</p>
    *Fabrica los HTML realmente estos son construidos uno por uno leyendo las plantillas escritas en resources
    *estos son escritos en el directorio previamente seleccionado
    *
    *
    *
    */
  public class FabricaHTML{
  

    /**Archivo HTML a digerir**/
    private ArchivoHTML pagina;
    /**Nombre de los archivos excliusivo para generar el index**/
    private String directorio;
    /** Lista de ArchivosHTML**/
    private Lista<ArchivoHTML> listaArchivo;
    /**Boton para páginas web**/
    private Boton boton;
    /**Grafica svg**/
    private SVGGrafica  graph;



     /**
    *Constructor de Fabrica HTML exclusivo para index
    *@param listaArchivo Lista de Archivos html
    *@param directorio Directorio
    */
    FabricaHTML(Lista<ArchivoHTML> listaArchivo, String directorio){
    this.listaArchivo=listaArchivo;
    this.directorio=directorio;
    boton=new Boton(listaArchivo, directorio);
    graph=new SVGGrafica(listaArchivo,directorio);

    }

   
    /**
    *Constructor vacío
    */
    FabricaHTML(){}



    public void generaHTML(){
    		try{
                for(ArchivoHTML a: this.listaArchivo){
                    pagina=a;
                    BufferedWriter bw = new BufferedWriter(new FileWriter(directorio+"/"+a.getRuta()+".html"));
                bw.write(getResource("html/archivo.html", a.getNombrePuro()));
                bw.close();
                }
    		
    	}catch(IOException ioe){
    		System.err.println("Error al escribir el archivo index.html en el directorio");
    		System.err.println("Para más ayuda java -jar target/proyecto3.jar --h");
    		System.exit(1);
    	}	
    	
    	
    }

    private void generaCSSArchivo(){
    	try{
    			BufferedWriter bw = new BufferedWriter(new FileWriter(directorio+"/archivo.css"));
				bw.write(getResource("css/archivo.css","None"));  
				bw.close(); 
    	}catch(IOException ioe){
    		System.err.println("Error al escribir el archivo index.html en el directorio");
    		System.exit(1);
    	}
    }

    /**
    *Itera el numero de archivos para generar el index y crea el archivo index.html en el directorio
    *además de escribir el index.html y su hoja de estilos index.css
    */
    public void generaIndex(){
     
			try{
    			BufferedWriter bw = new BufferedWriter(new FileWriter(directorio+"/index.html"));
				bw.write(getResourceIndex("html/index.html"));
				bw.close();

				bw = new BufferedWriter(new FileWriter(directorio+"/index.css"));
				bw.write(getResourceIndex("css/index.css"));  
				bw.close(); 


    	}catch(IOException ioe){
    		System.err.println("Error al escribir el archivo index.html en el directorio");
    		System.exit(1);
    	}	
    	copiaImagenes("img/index.jpg", "index.jpg");
        copiaImagenes("img/via.jpg", "via.jpg");
		generaCSSArchivo();
    }	



    private void  copiaImagenes(String archivo ,String salida){
    	 try{
    	InputStream ip= getClass().getClassLoader().getResourceAsStream(archivo);
    	OutputStream output = new FileOutputStream(new File(directorio+"/"+ salida));
    	copyStream(ip, output);

    }catch(IOException ioe){
    	System.err.println("Error al leer la plantilla, posiblemente has eliminado o modificado la carpeta resources");
    	System.exit(1);
    }



    }

    private  void copyStream(InputStream input, OutputStream output)throws IOException{
    byte[] buffer = new byte[1024]; // Adjust if you want
    int bytesRead;
    while ((bytesRead = input.read(buffer)) != -1)
  	    output.write(buffer, 0, bytesRead);
    
}

    /**
    *Crea el archivo html correpondiente a cada archivo html
    *
    */
	private String getResource(String archivo, String name){
		String salida="";
   		 boolean entraBoton=false;
   		 boolean saleBoton=false;
   		 try{
    	InputStream ip= getClass().getClassLoader().getResourceAsStream(archivo);
    	BufferedReader br = new BufferedReader(new InputStreamReader(ip));
    	String f;
    	while((f=br.readLine()) != null){
    		salida+=f+"\n";
            if(f.trim().equals("<h1 class=\"splashscreen__title\">"))
                salida+=name;

            if(f.trim().equals("<h1 class=\"why__title\">Palabras procesadas</h1>"))
                salida+=pagina.getSVG();

            if(f.trim().equals("<p class=\"about-us__description\" >"))
                salida+="El archivo se compone de:<br> "+pagina.conteoPalabras()+" palabras"+"<br>Con un total de: <br>"+ pagina.getPalabras().getElementos()+" Palabras concurrentes";


            if(f.trim().equals("<a href=\"javascript:window.print();\"> Imprimir página </a>"))
                salida+=pagina.getGraficaBarraSVG();
            if(f.trim().equals("</body>"))
                salida+=pagina.getGraficaPastel()+"\n"+pagina.getArbolRojiNegro()+"\n"+pagina.getArbolAVL();


    	}
    		
    	
    	return salida;
    		
    }catch(IOException ioe){
    	System.err.println("Error al leer la plantilla, posiblemente has eliminado o modificado la carpeta resources");
    	System.exit(1);
    }

    return salida;
   	
	}
  


   	private String getResourceIndex(String archivo){
   		 String salida="";
   		 boolean entraBoton=false;
   		 boolean saleBoton=false;
   		 try{
    	InputStream ip= getClass().getClassLoader().getResourceAsStream(archivo);
    	BufferedReader br = new BufferedReader(new InputStreamReader(ip));
    	String f;
    	while((f=br.readLine()) != null ){
    		salida+=f.trim()+"\n";

    		if(f.trim().equals("<section class=\"boton\">"))
    			entraBoton=true;

    		if(f.trim().equals("<button>Inicio</button>"))
    		 saleBoton=true;

    		if(entraBoton && saleBoton){
    			for(String m: boton.getBoton())
    				salida+=m+"\n";
    			entraBoton=saleBoton=false;
    		
				}

			if("<h1 class=\"why__title\">Archivos procesados</h1>".equals(f.trim()))
				for(String c : boton.getCaja()){
					salida+=c+"\n";
				}

				if(f.trim().equals("<p class=\"about-us__description\" >")){
					salida+= "Han sido procesados :</br>" + listaArchivo.getLongitud()+" Archivos</br>";
					salida+= "Con un total de:</br> "+ boton.getSuma()+ " Palabras diferentes leídas</br>";
                    salida+= "Con un total de:</br>"+boton.getSumaPalabra()+" Palabras colisionadas leídas";
				}

                if(f.trim().equals("<b>Grafica de Archivos</b><br>"))
                    salida+=graph.codeaGrafica();

                
						
    	}
    		
    	
    	return salida;
    		
    }catch(IOException ioe){
    	System.err.println("Error al leer la plantilla, posiblemente has eliminado o modificado la carpeta resources");
    	System.exit(1);
    }

    return salida;
   	}


    
  }
