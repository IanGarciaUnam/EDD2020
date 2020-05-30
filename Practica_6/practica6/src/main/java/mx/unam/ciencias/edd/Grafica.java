package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            iterador = vertices.iterator();
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return iterador.hasNext();
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
          return iterador.next().get();
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La lista de vecinos del vértice. */
        public Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.
          this.elemento=elemento;
          this.color=Color.NINGUNO;
          vecinos=new Lista<>();
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            // Aquí va su código.
            return this. elemento;
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            // Aquí va su código.
            return vecinos.getElementos();
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            // Aquí va su código.
            return this.color;
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return vecinos;
        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código.
        vertices=new Lista<>();
        aristas=0;
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return vertices.getElementos();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        // Aquí va su código.
        return this.aristas;
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento es <code>null</code> o ya
     *         había sido agregado a la gráfica.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.

        if(elemento==null )throw new IllegalArgumentException();
        try{
          Vertice v = (Vertice)vertice(elemento);
          if(vertices.contiene(v))throw new IllegalArgumentException();
        }catch(NoSuchElementException n){
          Vertice a=(Vertice) nuevoVertice(elemento);
          vertices.agrega(a);
        }






    }


    private VerticeGrafica<T> nuevoVertice(T elemento){
      return new Vertice(elemento);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        // Aquí va su código.
        Vertice x=(Vertice)vertice(a); // lanzamos desde aqui NoSuchElementException
        Vertice y=(Vertice)vertice(b);


        if(sonVecinos(a,b) || a==b) throw new IllegalArgumentException();

        x.vecinos.agrega(y);
        y.vecinos.agrega(x);
        aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        // Aquí va su código.
        Vertice x=(Vertice)vertice(a);
        Vertice y=(Vertice)vertice(b);//Desde aqui lanza NoSuchElementException

        if(!sonVecinos(a,b) || a==b) throw new IllegalArgumentException();

        y.vecinos.elimina(x);
        x.vecinos.elimina(y);
        aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        try{
          Vertice a = (Vertice) vertice(elemento);
          return true;
        }catch(NoSuchElementException n){
              return false;
        }

    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        Vertice v = (Vertice) vertice(elemento);// lanza NoSuchElementException si no esta contenido en la grafica

        vertices.elimina(v);

        for(Vertice c: vertices){
            if(c.vecinos.contiene(v)){
              c.vecinos.elimina(v);
              aristas--;
            }
          }


    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        // Aquí va su código.
        Vertice x = (Vertice) vertice(a);
        Vertice y= (Vertice) vertice(b);

        return y.vecinos.contiene(x) && x.vecinos.contiene(y);
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        // Aquí va su código.}
        for(Vertice v: vertices){
            if(v.elemento.equals(elemento))
            return v;
        }

        throw new NoSuchElementException();


    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        // Aquí va su código.
        if (vertice == null || vertice.getClass() != Vertice.class)
            throw new IllegalArgumentException();


        Vertice v = (Vertice)vertice;
      if(!vertices.contiene(v))
        throw new IllegalArgumentException();

        v.color = color;



    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        // Aquí va su código.
          Vertice v = vertices.getPrimero();

          return bfs(v.get()) == vertices.getElementos();



    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        // Aquí va su código.

        for(Vertice v : this.vertices){
            accion.actua(v);
        }
    }

    /**
    *
    *
    *
    **/
    private int bfs(T elemento){
      Cola<Vertice> queue = new Cola<>();

      Vertice w= (Vertice) vertice(elemento);

      for(Vertice v: vertices)
        v.color=Color.ROJO;

        w.color=Color.NEGRO;
        queue.mete(w);

        while(!queue.esVacia()){
          Vertice c= queue.saca();

            for(Vertice u: c.vecinos)
              if(u.color==Color.ROJO){
                  u.color=Color.NEGRO;
                  queue.mete(u);
              }
        }

        int i=0;
        for(Vertice v: vertices){
          if(v.color==Color.NEGRO)
            i++;
        }

        for(Vertice f: vertices)
          f.color=Color.NINGUNO;

        return i;

    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        Cola<Vertice> queue = new Cola<>();

        Vertice w= (Vertice) vertice(elemento);

        for(Vertice v: vertices)
          v.color=Color.ROJO;

          w.color=Color.NEGRO;
          queue.mete(w);

          while(!queue.esVacia()){
            Vertice c= queue.saca();
              accion.actua(c);
              for(Vertice u: c.vecinos)
                if(u.color==Color.ROJO){
                    u.color=Color.NEGRO;
                    queue.mete(u);
                }
          }


          for(Vertice f: vertices)
            f.color=Color.NINGUNO;


    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        Vertice w = (Vertice) vertice(elemento);

        for(Vertice a: vertices)
          a.color=Color.ROJO;

          Pila<Vertice> pila = new Pila<>();

          w.color=Color.NEGRO;

          pila.mete(w);

          while(!pila.esVacia()){
            Vertice c= pila.saca();
              accion.actua(c);
              for(Vertice d: c.vecinos){
                if(d.color==Color.ROJO){
                  d.color=Color.NEGRO;
                  pila.mete(d);
                }
              }


          }
          for(Vertice f: vertices)
            f.color=Color.NINGUNO;

    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.

        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        aristas=0;
      vertices.limpia();
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        // Aquí va su código.

        String s="";
        String ar="";

        for(Vertice v: vertices)
          v.color=Color.ROJO;

            for(Vertice v: vertices){
              s+=v.get()+", ";
              for(Vertice a: v.vecinos){
                if(a.color==Color.ROJO){
                  ar+="("+v.get()+", "+ a.get()+"), ";
                  v.color=Color.NEGRO;
                }
              }



            }

            for(Vertice v: vertices)
            v.color=Color.NINGUNO;

        return "{"+s+"}, "+ "{"+ar+"}";
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        // Aquí va su código.
        if(this.getElementos() != grafica.getElementos() || this.aristas != grafica.aristas)return false;


        for(Vertice v1: this.vertices){
            if(!grafica.contiene(v1.get()))return false;

            Vertice v2= (Vertice) grafica.vertice(v1.get());

            if(v1.getGrado()==v2.getGrado())
                for(Vertice ve1: v2.vecinos)
                  if(lastComparator(ve1, v1.vecinos)==0) return false;

        }

        return true;
    }



    private int lastComparator(Vertice ve1, Lista<Vertice> ve2){
      int i=0;
      while(i<ve2.getElementos()){
        if(ve1.elemento.equals(ve2.get(i).elemento))
          return 1;
          i++;
      }

      return 0;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
