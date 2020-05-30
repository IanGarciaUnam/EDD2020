package mx.unam.ciencias.edd;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo, mapeando un conjunto de <em>llaves</em> a una colección
 * de <em>valores</em>.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /* Clase interna privada para entradas. */
    private class Entrada {

        /* La llave. */
        public K llave;
        /* El valor. */
        public V valor;

        /* Construye una nueva entrada. */
        public Entrada(K llave, V valor) {
            // Aquí va su código.
            this.llave=llave;
            this.valor=valor;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador {

        /* En qué lista estamos. */
        private int indice;
        /* Iterador auxiliar. */
        private Iterator<Entrada> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario. */
        public Iterador() {
            // Aquí va su código.
            Lista<Entrada> e=null;
            for(int i=0; i< entradas.length; i++){
                if(entradas[i] != null){
                    indice=i;
                    e=entradas[i];
                    break;
                }
            }
            if(e==null)
                this.iterador=null;
            else
                this.iterador=e.iterator();
        }

        /* Nos dice si hay una siguiente entrada. */
        public boolean hasNext() {
            // Aquí va su código.
            return iterador!=null && iterador.hasNext();
        }

        /* Regresa la siguiente entrada. */
        public Entrada siguiente() {
            // Aquí va su código.
             if(iterador==null)throw new NoSuchElementException();
                Entrada e=iterador.next();
            
            if(!iterador.hasNext()){
                iterador=null;
                for(int i= indice+1; i<entradas.length; i++){
                    if(entradas[i]!=null){
                        indice=i;
                        iterador=entradas[i].iterator();
                        break;
                    }
                }
            }

           
            return e;
         }

        /* Mueve el iterador a la siguiente entrada válida. */
        private void mueveIterador() {
            // Aquí va su código.
            for(int i= indice+1; i<entradas.length; i++){
                    if(entradas[i]!=null){
                        indice=i;
                        iterador=entradas[i].iterator();
                        break;
                    }
                }
        }
            
    }

    /* Clase interna privada para iteradores de llaves. */
    private class IteradorLlaves extends Iterador
        implements Iterator<K> {

        /* Regresa el siguiente elemento. */
        @Override public K next() {
            // Aquí va su código.
               
            return this.siguiente().llave;
           
        }
    }

    /* Clase interna privada para iteradores de valores. */
    private class IteradorValores extends Iterador
     implements Iterator<V> {

        /* Regresa el siguiente elemento. */
        @Override public V next(){
            // Aquí va su código.
         return this.siguiente().valor;
        }
    }

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Capacidad mínima; decidida arbitrariamente a 2^6. */
    private static final int MINIMA_CAPACIDAD = 64;

    /* Dispersor. */
    private Dispersor<K> dispersor;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores. */
    private int elementos;

    /* Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked")
    private Lista<Entrada>[] nuevoArreglo(int n) {
        return (Lista<Entrada>[])Array.newInstance(Lista.class, n);
    }

    /**
     * Construye un diccionario con una capacidad inicial y dispersor
     * predeterminados.
     */
    public Diccionario() {
        this(MINIMA_CAPACIDAD, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial definida por el
     * usuario, y un dispersor predeterminado.
     * @param capacidad la capacidad a utilizar.
     */
    public Diccionario(int capacidad) {
        this(capacidad, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial predeterminada, y un
     * dispersor definido por el usuario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(Dispersor<K> dispersor) {
        this(MINIMA_CAPACIDAD, dispersor);
    }

    /**
     * Construye un diccionario con una capacidad inicial y un método de
     * dispersor definidos por el usuario.
     * @param capacidad la capacidad inicial del diccionario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(int capacidad, Dispersor<K> dispersor) {
        // Aquí va su código.
        
        this.dispersor=dispersor;
        if(capacidad<MINIMA_CAPACIDAD)
           capacidad=MINIMA_CAPACIDAD;

        
        int capacity=1;
        while(capacity < capacidad*2)
            capacity*=2;

        entradas=nuevoArreglo(capacity);  
        elementos=0;
      
        

    }

    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     * @throws IllegalArgumentException si la llave o el valor son nulos.
     */
    public void agrega(K llave, V valor) {
        // Aquí va su código.
        if(llave==null || valor == null)throw new IllegalArgumentException();
        
        
        int i= dispersor.dispersa(llave) & mascara();

        if(entradas[i]==null){
         Lista<Entrada> nueva= new Lista<>();
         nueva.agrega(new Entrada(llave, valor));
         entradas[i]=nueva;
         elementos++; 

        }else{

            boolean hayLlave=false;
            for(Entrada e: entradas[i]){
                if(hayLlave)break;
                if(e.llave.equals(llave)){
                    e.valor=valor;
                    hayLlave=true;
                    }

                }

                if(!hayLlave){
                    entradas[i].agrega(new Entrada(llave,valor));
                    elementos++;
                }


        }

        if(carga()>= MAXIMA_CARGA){
            Lista<Entrada> nuevaLista = new Lista<>();

            for(int e=0; e<entradas.length; e++){
                if(entradas[e] != null){
                    for(Entrada j: entradas[e])
                        nuevaLista.agrega(j);
                }
            }

            entradas= nuevoArreglo(entradas.length*2);
            elementos=0;

            for(Entrada k: nuevaLista)
                agrega(k.llave, k.valor);

        }
        
    }



    private int mascara(){
        return (entradas.length-1);
    }

    /**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no está en el diccionario.
     */
    public V get(K llave) {
        // Aquí va su código.
        if(llave == null) throw new IllegalArgumentException();

        if(!contiene(llave)) throw new NoSuchElementException();

        int i = dispersor.dispersa(llave) & mascara();

        for(Entrada e: entradas[i])
            if(e.llave.equals(llave))
                return e.valor;
        throw new NoSuchElementException();
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <code>true</code> si la llave está en el diccionario,
     *         <code>false</code> en otro caso.
     */
    public boolean contiene(K llave) {
        // Aquí va su código.
        if(llave==null) return false;

        int i= dispersor.dispersa(llave) & mascara();

        if(entradas[i]==null)return false;

        for(Entrada e: entradas[i])
            if(e.llave.equals(llave))
                return true;

        return false;
    }

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
        // Aquí va su código.
        if(llave==null) throw new IllegalArgumentException();
        if(!contiene(llave))throw new NoSuchElementException();

        int i= dispersor.dispersa(llave) & mascara();

        if(entradas[i]==null)throw new NoSuchElementException();

        for(Entrada e: entradas[i]){
           if(e.llave.equals(llave)){
             entradas[i].elimina(e); 
             elementos--;
             return;
           }
                
           }

           throw new NoSuchElementException();
        }
            
    



    

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
        // Aquí va su código.
        int c=0;
        for(int i=0; i<entradas.length; i++)
            if(entradas[i]!=null)
                c+= entradas[i].getLongitud()-1;
        
        return c;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
        // Aquí va su código.
        int c=0;
        for(int i=0; i<entradas.length; i++){
            if(entradas[i] != null){
                if(entradas[i].getLongitud()-1>c)
                    c= entradas[i].getLongitud()-1;
            } 
        }
        return c;   
                    
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
        // Aquí va su código.
        return (getElementos()+0.0)/(double)entradas.length;
    }

    /**
     * Regresa el número de entradas en el diccionario.
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
        // Aquí va su código.
        return elementos;
    }

    /**
     * Nos dice si el diccionario es vacío.
     * @return <code>true</code> si el diccionario es vacío, <code>false</code>
     *         en otro caso.
     */
    public boolean esVacia() {
        // Aquí va su código.
        return elementos==0;
    }

    /**
     * Limpia el diccionario de elementos, dejándolo vacío.
     */
    public void limpia() {
        // Aquí va su código.
        nuevoArreglo(entradas.length);
        elementos=0;        
    }

    /**
     * Regresa una representación en cadena del diccionario.
     * @return una representación en cadena del diccionario.
     */
    @Override public String toString() {
        // Aquí va su código.
        String s="{";


        for(int i=0; i<entradas.length; i++){
            if(entradas[i] != null){
                int h=0;
                for(Entrada e: entradas[i]){
                    s+=h==0?" ":"";
                    s+="\'"+e.llave+"\': ";
                    s+="\'"+e.valor+"\',";
                    h++;
                    
                }
            }
        }
        s+=esVacia()?"}":" }";
        return s;
    }

    /**
     * Nos dice si el diccionario es igual al objeto recibido.
     * @param o el objeto que queremos saber si es igual al diccionario.
     * @return <code>true</code> si el objeto recibido es instancia de
     *         Diccionario, y tiene las mismas llaves asociadas a los mismos
     *         valores.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Diccionario<K, V> d =
            (Diccionario<K, V>)o;
        // Aquí va su código.
            if(this.getElementos()!= d.getElementos())return false;

            for(int i=0; i<this.getElementos(); i++){
                if(entradas[i]!=null){
                    for(Entrada e: entradas[i]){
                        if(!d.contiene(e.llave) || !this.get(e.llave).equals(d.get(e.llave)))
                                return false;

                    }
                }
            }
            return true;
    }

    /**
     * Regresa un iterador para iterar las llaves del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar las llaves del diccionario.
     */
    public Iterator<K> iteradorLlaves() {
        return new IteradorLlaves();
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar los valores del diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new IteradorValores();
    }
}