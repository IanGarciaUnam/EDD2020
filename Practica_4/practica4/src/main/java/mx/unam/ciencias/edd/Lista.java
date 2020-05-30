package mx.unam.ciencias.edd;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        public T elemento;
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nodo con un elemento. */
        public Nodo(T elemento) {
            // Aquí va su código.
            this.elemento=elemento;

        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        public Nodo anterior;
        /* El nodo siguiente. */
        public Nodo siguiente;

        /* Construye un nuevo iterador. */
        public Iterador() {
          anterior=null;
          siguiente=cabeza;
            // Aquí va su código.
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return siguiente!=null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            // Aquí va su código.
            if(siguiente == null)
              throw new NoSuchElementException();

              anterior=siguiente;
              siguiente=siguiente.siguiente;
              return anterior.elemento;
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            // Aquí va su código.
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            // Aquí va su código.
            if(anterior==null)
            throw new NoSuchElementException();

            siguiente=anterior;
            anterior = anterior.anterior;
            return siguiente.elemento;
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            // Aquí va su código.
            anterior =null;
            siguiente=cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            // Aquí va su código.
            anterior=rabo;
            siguiente=null;

        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return longitud;
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return cabeza==null;
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento==null)
          throw new IllegalArgumentException();

          Nodo n= new Nodo(elemento);

          if(longitud==0)
          cabeza=rabo=n;
          else{
            rabo.siguiente=n;
            n.anterior=rabo;
            rabo=n;
          }
          longitud++;
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        // Aquí va su código.
      agrega(elemento);
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        // Aquí va su código.

        if(elemento==null)
          throw new IllegalArgumentException();



          Nodo n= new Nodo(elemento);
          if(longitud == 0 ){
            cabeza=rabo=n;
            longitud++;
          }else{
            cabeza.anterior=n;
            n.siguiente=cabeza;
            cabeza=cabeza.anterior;
            longitud++;
          }
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        // Aquí va su código.

        if(elemento==null)
        throw new IllegalArgumentException();

        Nodo axl=cabeza;
        Nodo n= new Nodo(elemento);




      // Si el indice es mayor a la lista, se agrega al final

      if(i<1){
        this.agregaInicio(elemento);
        return;
      }

      if(i>=getLongitud()){
        this.agregaFinal(elemento);
        return;
      }



      if(i>0 && i<this.getLongitud()){
        int c=0;
        while(c<=i){

          if(c==i){
              n.anterior=axl.anterior;
              axl.anterior.siguiente=n;
              n.siguiente=axl;
              axl.anterior=n;
              longitud++;

          return;
          }
          axl=axl.siguiente;
          c++;

      }

      }
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.


        if(this.esVacia())
        return;

        if(elemento.equals(null))
        return;

        Nodo axl=cabeza;
        if(axl.elemento.equals(elemento)){
          this.eliminaPrimero();return;
        }




         axl = this.auxiliarBuscaNodo(elemento);
         if(axl != cabeza && axl != rabo){

           axl.anterior.siguiente= axl.siguiente;
           axl.siguiente.anterior = axl.anterior;
               longitud--;
               return;


         }else if(axl == rabo && rabo != null){
           this.eliminaUltimo();return;
         }

         return ;
    }



    /**
    *Metodo privado para buscar Nodo
    *
    */
    private Nodo auxiliarBuscaNodo(T elemento){
      if(elemento == null)
      return null;

      int i=0;
      Nodo axl = cabeza;
      while(i<getLongitud()){
        if(axl != null &&  elemento.equals(axl.elemento))
        return axl;

        axl=axl.siguiente;
        i++;
      }
      return null;
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        // Aquí va su código.
        if(this.esVacia())
          throw new NoSuchElementException();

          longitud--;
          T copuy= cabeza.elemento;
          if(cabeza==rabo){
          cabeza=rabo=null;

          return copuy;
          }

          cabeza=cabeza.siguiente;
          cabeza.anterior=null;



        return copuy;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        // Aquí va su código.
        if(longitud==0)
          throw new NoSuchElementException();

        T copy= rabo.elemento;
        if(longitud==1){
          cabeza=rabo=null;
          longitud=0;
        }else{
          rabo=rabo.anterior;
          rabo.siguiente=null;
          longitud--;
        }

        return copy;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        return auxiliarBuscaNodo(elemento) != null;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        // Aquí va su código.
        Lista<T> l = new Lista<T>();
        Nodo axl= rabo;
          while(axl!=null){
            l.agregaFinal(axl.elemento);
              axl=axl.anterior;;
          }

        return l;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        // Aquí va su código.
        if(cabeza==rabo && rabo==null){
        Lista<T> l = new Lista<>();
        return l;
        }
          Lista <T> l= new Lista<T>();

          Nodo axl= cabeza;

          while(axl != null){
            l.agrega(axl.elemento);
            axl=axl.siguiente;
          }



        return l;
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        longitud=0;
        cabeza=rabo=null;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        // Aquí va su código.
        if(this.esVacia())
        throw new NoSuchElementException();

        return cabeza.elemento;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        // Aquí va su código.
        if(this.esVacia())
        throw new NoSuchElementException();

        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        // Aquí va su código.
        if(i<0 || i>= getLongitud())
        throw new ExcepcionIndiceInvalido();

        int c=0;
        Nodo axl=cabeza;

        if(i==0)
        return cabeza.elemento;

        if(i==getLongitud()-1)
        return rabo.elemento;

        while(c<i){
          axl=axl.siguiente;
          c++;
        }
        return axl.elemento;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        // Aquí va su código.
        if( cabeza == null || elemento.equals(null))
        return -1;

        Nodo axl = this. auxiliarBuscaNodo(elemento);
        Nodo earth=cabeza;
        int c =0;

        while(c<getLongitud()){

          if(axl== earth){
            return c;
          }
            c++;
            earth=earth.siguiente;

        }

        return -1;

    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        // Aquí va su código.
        if(cabeza==null)
        return "[]";

        Nodo aux= cabeza;
        String show="["+aux.elemento;

        int c=0;
        while(c<getLongitud()-1 && aux != null){

          aux=aux.siguiente;
          show +=", ";
          show += aux.elemento;
          c++;

        }
        show += "]";

        return show;

    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        // Aquí va su código.

        if(this.getLongitud() != lista.getLongitud())
        return false;

        if(this.esVacia() && lista.esVacia())
        return true;


        Nodo in= this.cabeza;
        Nodo out = lista.cabeza;
        while(in != null && out != null){

          if(!in.elemento.equals(out.elemento))
          return false;

          in=in.siguiente;
          out=out.siguiente;

        }
        return true;

    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        // Aquí va su código.
        Lista<T> l1 = new Lista<>();
        Lista<T> l2 = new Lista<>();


        Nodo aux = this.cabeza;

        if(this.cabeza==this.rabo)
        return this.copia();

        int c=0;



        while(c<this.getLongitud()/2){
        l1.agrega(aux.elemento);
        aux=aux.siguiente;
        c++;
        }

        while(aux != null){
          l2.agrega(aux.elemento);
          aux=aux.siguiente;

        }


        return merge(l1.mergeSort(comparador), l2.mergeSort(comparador), comparador);

    }


    private Lista<T> merge (Lista<T> ordenada1, Lista<T> ordenada2 ,Comparator<T> comparador){

      Lista<T> nuevaOrdenada = new Lista<T>();
      Nodo n1= ordenada1.cabeza;
      Nodo n2= ordenada2.cabeza;


      while(n1 != null && n2 != null){

        if(comparador.compare(n1.elemento, n2.elemento)<=0){
          nuevaOrdenada.agrega(n1.elemento);// agregamos el elemento mas pequeño a nuestra lista
          n1=n1.siguiente;
        }else{
          nuevaOrdenada.agrega(n2.elemento);
          n2= n2.siguiente;
        }

      }// se ejecuta mientra haya elementos en las dos listas


        if(n1 == null){
          while(n2 != null){
              nuevaOrdenada.agrega(n2.elemento);
              n2=n2.siguiente;
          }

        }else{

          while(n1 != null){
              nuevaOrdenada.agrega(n1.elemento);
              n1=n1.siguiente;
          }


        }

        return nuevaOrdenada;
}

    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        // Aquí va su código.
        int i=0;
        while(i<getLongitud()){
          if(comparador.compare(elemento, get(i))==0)
          return true;

          if(comparador.compare(elemento,get(i))<0)return false;

          i++;

        }


        return false;
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
