package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para montículos mínimos (<i>min heaps</i>).
 */
public class MonticuloMinimo<T extends ComparableIndexable<T>>
    implements Coleccion<T>, MonticuloDijkstra<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int indice;

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return indice>=0 && indice<elementos;
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
            if(indice<0 ||indice>=elementos) throw new NoSuchElementException();
            return get(indice++);//arbol[indice++];
        }
    }

    /* Clase estática privada para adaptadores. */
    private static class Adaptador<T  extends Comparable<T>>
        implements ComparableIndexable<Adaptador<T>> {

        /* El elemento. */
        private T elemento;
        /* El índice. */
        private int indice;

        /* Crea un nuevo comparable indexable. */
        public Adaptador(T elemento) {
            // Aquí va su código.
            this.elemento=elemento;
            this.indice=-1;
        }

        /* Regresa el índice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return this.indice;
        }

        /* Define el índice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice=indice;
        }

        /* Compara un adaptador con otro. */
        @Override public int compareTo(Adaptador<T> adaptador) {
            // Aquí va su código.adaptador
            return this.elemento.compareTo(adaptador.elemento);
        }
    }

    /* El número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arbol;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link
     * #MonticuloMinimo(Coleccion)} o {@link #MonticuloMinimo(Iterable,int)},
     * pero se ofrece este constructor por completez.
     */
    public MonticuloMinimo() {
        // Aquí va su código.
        arbol=nuevoArreglo(100);
    }

    /**
     * Constructor para montículo mínimo que recibe una colección. Es más barato
     * construir un montículo con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloMinimo(Coleccion<T> coleccion) {
        // Aquí va su código.
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Constructor para montículo mínimo que recibe un iterable y el número de
     * elementos en el mismo. Es más barato construir un montículo con todos sus
     * elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el insertándolos
     * uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * @param iterable el iterable a partir de la cuál queremos construir el
     *                 montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloMinimo(Iterable<T> iterable, int n) {
        // Aquí va su código.
        arbol=nuevoArreglo(n);
        int indicator=0;
        elementos=n;
        for(T t: iterable){
          arbol[indicator]=t;
          t.setIndice(indicator);
          indicator++;
        }

        for(int i=(elementos/2)-1; i>=0; i--){
          heapifyDown(i);
        }


    }

    private boolean indiceValido(int indice){
      return indice>=0 && indice<elementos;

    }

    private void heapifyDown(int indice){
      int izquierdo=2*indice+1;
      int derecho=2*indice+2;

      int smallest=indice;
      if(indiceValido(izquierdo) ){
        if(arbol[izquierdo].compareTo(arbol[smallest])<0)
          smallest=izquierdo;

      }


      if(indiceValido(derecho)){
          if(arbol[derecho].compareTo(arbol[smallest])<0)
          smallest=derecho;
      }



      if(indice==smallest)return;

      intercambia(arbol[indice], arbol[smallest]);
      heapifyDown(smallest);

      }


          private void heapifyUp(int indice){
            if(indice==0)return;

            int p=(indice-1)/2;

            if(p<0)return;

            if(arbol[p].compareTo(arbol[indice])<0) return;

            intercambia(arbol[indice], arbol[p]);
            heapifyUp(p);
            }

      private void intercambia(T elemento1, T elemento2){
        T aux=elemento1;
        int ind=elemento1.getIndice();
        int axl=elemento2.getIndice();

        arbol[ind]=elemento2;
        elemento2.setIndice(ind); //Elemento 2 donde elemento 1

        arbol[axl]=aux;

        aux.setIndice(axl); // elemento 1 donde elemento 1

      }


    /**
     * Agrega un nuevo elemento en el montículo.
     * @param elemento el elemento a agregar en el montículo.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elementos==arbol.length){
          T[] nuevo= nuevoArreglo(arbol.length*2);
          for(T t: arbol)
            nuevo[t.getIndice()]=t;

            arbol=nuevo;
        }
        arbol[elementos] = elemento;
        elemento.setIndice(elementos);
        elementos++;
        heapifyUp(elemento.getIndice());
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
        // Aquí va su código.
        if(this.esVacia()) throw new IllegalStateException();

        intercambia(arbol[0], arbol[elementos-1]);
        T retorno= arbol[elementos-1];
        arbol[elementos-1].setIndice(-1);
        elementos--;
        heapifyDown(0);

        return retorno;

    }

    /**
     * Elimina un elemento del montículo.
     * @param elemento a eliminar del montículo.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
          int axl=elemento.getIndice();
        if(!indiceValido(axl) || elemento==null || this.esVacia())return;

          intercambia(arbol[axl],arbol[elementos-1]);
          elemento.setIndice(-1);
            elementos--;

        if(axl<elementos)
        reordena(arbol[axl]);

    }

    /**
     * Nos dice si un elemento está contenido en el montículo.
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        if(elemento.getIndice()<0 && elemento.getIndice()>=elementos)return false;

        for(T t: arbol){
          if(t.equals(elemento))return true;
        }
        return false;
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return this.elementos==0;
    }

    /**
     * Limpia el montículo de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        // Aquí va su código
        elementos=0;


    }

   /**
     * Reordena un elemento en el árbol.
     * @param elemento el elemento que hay que reordenar.
     */
    @Override public void reordena(T elemento) {
        // Aquí va su código.

        heapifyUp(elemento.getIndice());
        heapifyDown(elemento.getIndice());
    }

    /**
     * Regresa el número de elementos en el montículo mínimo.
     * @return el número de elementos en el montículo mínimo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return elementos;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @param i el índice del elemento que queremos, en <em>in-order</em>.
     * @return el <i>i</i>-ésimo elemento del árbol, por niveles.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        // Aquí va su código.
        if(i<0 || i >= elementos) throw new NoSuchElementException();

        return arbol[i];
    }

    /**
     * Regresa una representación en cadena del montículo mínimo.
     * @return una representación en cadena del montículo mínimo.
     */
    @Override public String toString() {
        // Aquí va su código.
        String regresa="";
        for(T t: arbol)
        regresa+=t.toString()+", ";

        return regresa;
    }

    /**
     * Nos dice si el montículo mínimo es igual al objeto recibido.
     * @param objeto el objeto con el que queremos comparar el montículo mínimo.
     * @return <code>true</code> si el objeto recibido es un montículo mínimo
     *         igual al que llama el método; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") MonticuloMinimo<T> monticulo =
            (MonticuloMinimo<T>)objeto;
          if(arbol.length!=monticulo.getElementos())return false;

          for(int i = 0; i < getElementos(); i++)
           if(!arbol[i].equals(monticulo.get(i)))
            return false;


            return true;


        // Aquí va su código.
    }

    /**
     * Regresa un iterador para iterar el montículo mínimo. El montículo se
     * itera en orden BFS.
     * @return un iterador para iterar el montículo mínimo.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Ordena la colección usando HeapSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param coleccion la colección a ordenar.
     * @return una lista ordenada con los elementos de la colección.
     */
    public static <T extends Comparable<T>>
    Lista<T> heapSort(Coleccion<T> coleccion) {
        // Aquí va su código.
        return HeapSort(coleccion);
    }

    private static <T extends Comparable<T>>
    Lista<T>  HeapSort(Coleccion<T> coleccion){
      Lista<Adaptador<T>> l1 = new Lista<Adaptador<T>>();
      for(T elemento: coleccion)
        l1.agrega(new Adaptador<T>(elemento));


        Lista<T> l2 = new Lista<T>();
        MonticuloMinimo<Adaptador<T>> m1= new MonticuloMinimo<>(l1);
        while(!m1.esVacia())
          l2.agrega(m1.elimina().elemento);

          return l2;




    }
}
