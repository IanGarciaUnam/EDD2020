package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * Clase para conjuntos.
 */
public class Conjunto<T> implements Coleccion<T> {

    /* El conjunto de elementos. */
    private Diccionario<T, T> conjunto;

    /**
     * Crea un nuevo conjunto.
     */
    public Conjunto() {
        // Aquí va su código.
        conjunto=new Diccionario<T,T>();
    }

    /**
     * Crea un nuevo conjunto para un número determinado de elementos.
     * @param n el número tentativo de elementos.
     */
    public Conjunto(int n) {
        // Aquí va su código.
    	conjunto=new Diccionario<T,T>(n);
    }

    /**
     * Agrega un elemento al conjunto.
     * @param elemento el elemento que queremos agregar al conjunto.
     * @throws IllegalArgumentException si el elemento es <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.

        conjunto.agrega(elemento, elemento);
    }

    /**
     * Nos dice si el elemento está en el conjunto.
     * @param elemento el elemento que queremos saber si está en el conjunto.
     * @return <code>true</code> si el elemento está en el conjunto,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        return conjunto.contiene(elemento);
    }

    /**
     * Elimina el elemento del conjunto, si está.
     * @param elemento el elemento que queremos eliminar del conjunto.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
   
        if(elemento!=null)
         conjunto.elimina(elemento);
    }

    /**
     * Nos dice si el conjunto es vacío.
     * @return <code>true</code> si el conjunto es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return conjunto.esVacia();
    }

    /**
     * Regresa el número de elementos en el conjunto.
     * @return el número de elementos en el conjunto.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return conjunto.getElementos();
    }

    /**
     * Limpia el conjunto de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código.
        conjunto.limpia();
    }

    /**
     * Regresa la intersección del conjunto y el conjunto recibido.
     * @param conjunto el conjunto que queremos intersectar con éste.
     * @return la intersección del conjunto y el conjunto recibido.
     */
    public Conjunto<T> interseccion(Conjunto<T> conjunto) {
        // Aquí va su código.
        Conjunto<T> s= new Conjunto<>();

        Iterator<T> iterador=conjunto.iterator();
        while (iterador.hasNext()){
        	T myElement=iterador.next();
        	if(this.contiene(myElement))s.agrega(myElement);
        }
        return s;

    }

    /**
     * Regresa la unión del conjunto y el conjunto recibido.
     * @param conjunto el conjunto que queremos unir con éste.
     * @return la unión del conjunto y el conjunto recibido.
     */
    public Conjunto<T> union(Conjunto<T> conjunto) {
        // Aquí va su código.
        Conjunto<T> s= new Conjunto<>();
         Iterator<T> iterador=conjunto.iterator();
        while (iterador.hasNext()){
        	T myElement=iterador.next();
        	s.agrega(myElement);
        }

        iterador=this.iterator();
        while (iterador.hasNext()){
        	T myElement=iterador.next();
        	s.agrega(myElement);
        }
        return s;

    }

    /**
     * Regresa una representación en cadena del conjunto.
     * @return una representación en cadena del conjunto.
     */
    @Override public String toString() {
        // Aquí va su código.
    	String regresa="{ ";
    	Iterator<T> it=conjunto.iterator();

    	//if(this.getElementos()==1)return "{ "+it.next().toString()+" }";

    	while(it.hasNext()){
    		regresa+=it.next().toString();	
    		
    		if(it.hasNext())regresa+=", ";
    			
    	}



    	return regresa+" }";
    }

    /**
     * Nos dice si el conjunto es igual al objeto recibido.
     * @param o el objeto que queremos saber si es igual al conjunto.
     * @return <code>true</code> si el objeto recibido es instancia de Conjunto,
     *         y tiene los mismos elementos.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Conjunto<T> c = (Conjunto<T>)o;
        // Aquí va su código.
        return this.conjunto.equals(c.conjunto);
    }

    /**
     * Regresa un iterador para iterar el conjunto.
     * @return un iterador para iterar el conjunto.
     */
    @Override public Iterator<T> iterator() {
        // Aquí va su código.
        return conjunto.iterator();
    }
}
