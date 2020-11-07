package mx.unam.ciencias.edd.proyecto3;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import mx.unam.ciencias.edd.Lista;


  public class Boton{
  
    public class Texto{
        private Lista<ArchivoHTML> arch;
        Texto(Lista<ArchivoHTML> arch){
            this.arch=arch;
        }

        public int suma(){
            int i=0;
            for(ArchivoHTML a: arch)
                i+=a.conteoPalabras();
            return i;
        }

        public int sumaPalabras(){
        	int i=0;
        	for(ArchivoHTML a: arch)
        		i+=a.getPalabras().getElementos();
        	return i;
        }
        public Lista<String> getTexto(){
            Lista<String> texto = new Lista<>();
            for(ArchivoHTML a: arch)
                texto.agrega(generaTexto(a.getNombrePuro(), a.getPalabras().getElementos(), a.conteoPalabras()));

        return texto;
        }

        private String generaTexto(String nombre, int palabra, int palabrasIguales){
          String a= "<div class = \"why-item\">\n";
            a+="<h2 class =\"why-item__title\"><b>\t"+nombre+"</br></b>\nPalabras  diferentes leídas: "+palabra+"\n Palabras concurrentes leídas: "+palabrasIguales +" </h2>";
            a+="</div>";
            return a;
        }


    }

    /**Lista de archivos a directorio**/
    private Lista<ArchivoHTML> archivo ;
    /**Nombre del archivo**/
    private String Archivo;
    /**Nombre del directorio**/
  	private String directorio;
    /**
    *Cosntructor con el nombre del archivo
    *
    */
    Boton(Lista<ArchivoHTML> archivo, String directorio){
        this.archivo=archivo;
        this.directorio=directorio;
    }

    public String getTotalTexto(){
        int b=0;
        for(ArchivoHTML a: archivo)
            b=a.getDiccionario().getElementos();
        return String.valueOf(b);
    }

    public Lista<String> getBoton(){
        Lista <String> devuelveBoton= new Lista<>();
        for(ArchivoHTML a: this.archivo)
            devuelveBoton.agrega(getBoton(a));
        return devuelveBoton;


    }

    /**
    *Devuelve el nombre del archivo HTML
    *@return archivo, nombre del archivo
    */
    public String getBoton(ArchivoHTML a){
        String copia="<a class= \"boton__comienza\" href = \""+a.getRuta()+".html\"><button>"+a.toString()+"</button>";

     return copia;
    }
    
    public Lista<String> getCaja(){
       Texto t=new Texto(archivo);
       return t.getTexto();
    }

    public int getSuma(){
        Texto t=new Texto(archivo);
       return t.suma();
    }

    public int getSumaPalabra(){
    	 Texto t= new Texto(archivo);
    	return t.sumaPalabras();
    }





   
    
  }

