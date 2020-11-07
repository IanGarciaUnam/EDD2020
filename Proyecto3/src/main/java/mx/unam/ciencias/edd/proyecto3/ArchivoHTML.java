package mx.unam.ciencias.edd.proyecto3;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.util.Iterator;
import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.Diccionario;
import mx.unam.ciencias.edd.FabricaDispersores;
import mx.unam.ciencias.edd.Dispersor;
import mx.unam.ciencias.edd.Dispersores;
import mx.unam.ciencias.edd.AlgoritmoDispersor;



  /**
  *<p>Clase ArchivoHTML</p>
  *Permite Crear un Archivo HTML y de el sus diversos métodos nos otorgan sus componentes
  *
  *
  *
  */

  public class ArchivoHTML{


    /**
    *<p>Clase FabricasSVG</p>
    *
    *Permite fabricar los SVG correspondientes a su 
    *archivo
    *
    */
  
  	public class FabricaSVG{

  		/**Nombre del archivo a escribir**/
  		private String archivo;
  		/**Dirección a escritura**/
  		private String directorio;
  		/**Forma la ruta del nuevoSVG**/
  		private String ruta;
  		FabricaSVG(String archivo, String directorio){
  			this.archivo=archivo;
  			this.directorio=directorio;
  		}

  		/**
  		*Recibe el Código SVG y lo convierte en una imagen SVG; 
  		*
  		*@return ruta Ruta del archivo SVG
  		*/
  		public void convierteArchivo(String codigo){
  			try{
  			BufferedWriter bw = new BufferedWriter(new FileWriter(directorio+"/"+archivo+".svg"));
  			bw.write(codigo);
  			bw.close();
  			}catch(IOException i){
  				System.out.println("Error al dibujar"+archivo+".svg");
				System.exit(1);
  			}
  			String ruta="./"+archivo+".svg";
  			this.ruta=ruta;
  			
  		}
  			/**
  		*Recibe el Código SVG y lo convierte en una imagen SVG; 
  		*
  		*@return ruta Ruta del archivo SVG
  		*/
  		public void convierteArchivo(String codigo, String tipo){
  			try{
  			BufferedWriter bw = new BufferedWriter(new FileWriter(directorio+"/"+archivo+tipo+".svg"));
  			bw.write(codigo);
  			bw.close();
  			}catch(IOException i){
  				System.out.println("Error al dibujar"+archivo+tipo+".svg");
				System.exit(1);
  			}
  			String ruta="./"+archivo+tipo+".svg";
  			this.ruta=ruta;
  			
  		}
  		/**
  		*Regresa la ruta del SVG
  		*@return ruta
  		*/
  		public String getRuta(){
  			return this.ruta;
  		}


  	}






    /**Lista de archivos a directorio**/
    private Lista<Palabra> palabra ;
    /**Nombre del archivo**/
    private String archivo;
    /**Diccionario empleado en el archivo**/
    private Diccionario<String, Integer> diccionario;
    /**DIrectorio para depositar las imágenes SVG**/
    private String directorio;
    

    /**
    *Constructor de la  para index
    *@param archivo Nombre del archivo a escribir
    @param palabra lista de las palabras
    */
    ArchivoHTML(String archivo, Diccionario<String,Integer> d, String directorio){
	this.palabra=palabra;
	this.archivo=archivo;
	this.diccionario=d;
	this.directorio=directorio;
    }
    
    /**
    *Constructor de la FabricaHTML para index
    *@param archivo Nombre del archivo a escribir
    *@param palabra lista de las palabras
    *@param directorio Directorio
    */
    ArchivoHTML(String archivo, Lista<Palabra> palabra, String directorio){
	this.palabra=palabra;
	this.archivo=archivo;
	this.directorio=directorio;
    }

    /**
    *Constructor de HTML
    *@param archivo archivo
    */
    ArchivoHTML(String archivo){
        this.archivo=archivo;
    }
    /**
    *Constructor de ArchivoHTML vacío
    *
    */
    ArchivoHTML(){}

    @Override
    public boolean equals(Object o){
      if(o==null || o.getClass() != getClass())return false;
      ArchivoHTML arch=(ArchivoHTML)o;

      return  this.getNombrePuro().equals(arch.getNombrePuro());
    }


    /**
    *Devuelve el código SVG para sus imagenes
    *@return code
    */
    public String getSVG(){
    	int elementos= conteoPalabras();

    	if(this.palabra==null)palabra=getPalabras();
    
    	ValorGraficable valorgraficable = new ValorGraficable(palabra,elementos);

    		GraficaSVG grafica= new GraficaSVG(valorgraficable.getListaValorGraficable());
    	
    		return grafica.getConteoPalabras()+"\n";
    }



    private FabricaSVG escribeSVG(String svg, String archivo){
    	FabricaSVG fabrica= new FabricaSVG(archivo,directorio);
    	fabrica.convierteArchivo(svg);
    	return fabrica;
    }	

    /**
    *Fabrica la gráfica de barras
    *@return rutaBarras ruta para ver las gráficas SVG
    */
    public String getGraficaBarraSVG(){
    	int elementos= conteoPalabras();
    	if(this.palabra==null)palabra=getPalabras();
    	ValorGraficable valorgraficable = new ValorGraficable(palabra,elementos);
    		GraficaSVG grafica= new GraficaSVG(valorgraficable.getListaValorGraficable());
    		FabricaSVG f= escribeSVG(grafica.getGraficaBarras(), this.getRuta()+"barra");
    		String rutaBarras=getInsercion(f);
    		return rutaBarras;
    }

    /**
    *Regresa la ruta en forma de inserción y esrbie el SVG Correspondiente
    *@return rutaBarras la ruta en forma de Insecion para el html 
    */
    public String getGraficaPastel(){
    	int elementos= conteoPalabras();

    	if(this.palabra==null)palabra=getPalabras();
    
    	ValorGraficable valorgraficable = new ValorGraficable(palabra,elementos);

    		GraficaSVG grafica= new GraficaSVG(valorgraficable.getListaValorGraficable());
    		FabricaSVG f= escribeSVG(grafica.getPieChart(), this.getRuta()+"pastel");
    		String rutaBarras=getInsercion(f);
    		FabricaSVG f2= escribeSVG(grafica.getTextoPieChart(), this.getRuta()+"texto_pastel");
    		rutaBarras+="\n"+getInsercion(f2);
    		return "<p>"+rutaBarras+"</p>\n";
    }

    /**
    *Genera la imagen de un arbolRojinegro
    *@return ruta de la imagen ArbolRojinegro
    */
    public String getArbolRojiNegro(){
    	Escribano e = new Escribano( "ArbolRojinegro", this.palabra);
    	FabricaSVG fabrica= escribeSVG(e.escribe(), this.getRuta()+"arn");
    	String rutaBarras= "\n"+getInsercion(fabrica)+"\n";
    	return rutaBarras;


    }

     /**
    *Genera la imagen de un arbolAVL
    *@return ruta de la imagen ArbolAVL
    */
    public String getArbolAVL(){
    	Escribano e = new Escribano( "ArbolAVL", this.palabra);
    	FabricaSVG fabrica= escribeSVG(e.escribe(), this.getRuta()+"avl");
    	String rutaBarras= "\n"+getInsercion(fabrica)+"\n";
    	return rutaBarras;
    }


    private  String getInsercion(FabricaSVG f){
    	return "<img src=\""+f.getRuta()+"\" alt=\"Gráfica de Barras con los 15 casos más sobresalientes\" >";
    }	
    /**
    *Regreso el Conteo Absoluto de Palabras
    *@return i numero de palabras totales del archivo
    */
    public int conteoPalabras(){

    	if(palabra==null)palabra=this.getPalabras();

    	int i=0;
    	for(Palabra p: palabra)
    		i+=p.getElementos();
    	return i;
    }

    /**
    *Convierte el diccionario en una Lista de Palabras
    *
    */
     public  Lista<Palabra> getPalabras(){
    	Lista<Palabra> lista= new Lista<>();
    	Iterator<String> iterator= diccionario.iteradorLlaves();
    		while(iterator.hasNext()){
    			String siguiente= iterator.next();
    			lista.agrega(new Palabra(siguiente, diccionario.get(siguiente)));
    		}
     	return lista;
    		
     } 

    /**
    *Devuelve el nombre del archivo HTML
    *@return archivo, nombre del archivo
    */
    @Override public String toString(){
        String copia=archivo.substring(0, archivo.indexOf("."));
			if(copia.contains("/")){
				int barra=0;
				char [] juego= copia.toCharArray();
				for(int i=0; i<juego.length;i++)
					if(juego[i]=='/')barra=i;
				copia=copia.substring(barra+1, copia.length());
			}
        	
        
     return copia;
    }
    

    /**
    *archivo Diferenciado
    *
    */
    public String getRuta(){
    	String copia=getNombrePuro();
    	AlgoritmoDispersor a = AlgoritmoDispersor.DJB_STRING;
        Dispersor<String> bj = FabricaDispersores.dispersorCadena(a);//Configura Dispersor
    	 int disp= bj.dispersa(copia)&-1;
    	 copia=this.toString();
    	 copia+="_"+String.valueOf(disp *-1);
    	   return copia;
    }

    /**
    *Regresa el nombre sin modificaciones
    *
    */
 	 public String getNombrePuro(){
  		return archivo;
  		}

    public Diccionario<String,Integer> getDiccionario(){
    	return this.diccionario;
    }
    /**
    *Devuelve el top de las 15 palabras más utilizadas
    *@return copia Lista de las 15 Palabras mas usadas
    */
   public Lista<Palabra> getTop(){
   if(palabra.getElementos()<15)
    return Lista.mergeSort(palabra);
    
    Lista<Palabra> copia= new Lista<>();
     Lista.mergeSort(palabra);
    for(int i=0; i<=15; i++)
    copia.agrega(palabra.get(i));
    return copia;
   }


    /**
    *Devuleve la lista de palabras del archivo
    *@return palabra LIsta de palabras
    */
    public Lista<Palabra> getPalabra(){
      return palabra;
    }

    public void codea(String directorio){
    	    try{

            BufferedWriter bw = new BufferedWriter(new FileWriter(directorio+"/"+this.toString()+".html"));
           
            for(Palabra p: Lista.mergeSort(getTop()))
            bw.write(p.toString()+"R:"+ p.getElementos()+"\n");

            bw.close();
        }catch(IOException i){
            System.out.println("Ha ocurrido un error al escribir el index, verifique los permisos");
        }
    }
    
  }
