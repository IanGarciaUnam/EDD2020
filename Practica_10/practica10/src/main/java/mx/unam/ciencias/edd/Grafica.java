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
            // Aquí va su código.
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
    private class Vertice implements VerticeGrafica<T>,
                          ComparableIndexable<Vertice> {

        /* El elemento del vértice. */
        public T elemento;
        /* El color del vértice. */
        public Color color;
        /* La distancia del vértice. */
        public double distancia;
        /* El índice del vértice. */
        public int indice;
        /* La lista de vecinos del vértice. */
        public Lista<Vecino> vecinos;

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

        /* Define el índice del vértice. */
        @Override public void setIndice(int indice) {
            // Aquí va su código.
            this.indice=indice;
        }

        /* Regresa el índice del vértice. */
        @Override public int getIndice() {
            // Aquí va su código.
            return indice;
        }

        /* Compara dos vértices por distancia. */
        @Override public int compareTo(Vertice vertice) {
            // Aquí va su código.
            if (distancia > vertice.distancia)
              return 1;
            else if (distancia < vertice.distancia)
              return -1;
            return 0;
	        }

    }

    /* Clase interna privada para vértices vecinos. */
    private class Vecino implements VerticeGrafica<T> {

        /* El vértice vecino. */
        public Vertice vecino;
        /* El peso de la arista conectando al vértice con su vértice vecino. */
        public double peso;

        /* Construye un nuevo vecino con el vértice recibido como vecino y el
         * peso especificado. */
        public Vecino(Vertice vecino, double peso) {
            // Aquí va su código.
            this.vecino = vecino;
          this.peso = peso;

        }

        /* Regresa el elemento del vecino. */
        @Override public T get() {
            // Aquí va su código.
            return vecino.elemento;
        }

        /* Regresa el grado del vecino. */
        @Override public int getGrado() {
            // Aquí va su código.
            return vecino.getGrado();

        }

        /* Regresa el color del vecino. */
        @Override public Color getColor() {
            // Aquí va su código.
            return vecino.getColor();
        }

        /* Regresa un iterable para los vecinos del vecino. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return vecino.vecinos;
        }
    }

    /* Interface para poder usar lambdas al buscar el elemento que sigue al
     * reconstruir un camino. */
    @FunctionalInterface
    private interface BuscadorCamino {
        /* Regresa true si el vértice se sigue del vecino. */
        public boolean seSiguen(Grafica.Vertice v, Grafica.Vecino a);
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
          Vertice a= new Vertice(elemento);
          vertices.agrega(a);
        }
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
        if(a.equals(b) || sonVecinos(a,b)) throw new IllegalArgumentException();

        Vertice x=(Vertice)vertice(a); // lanzamos desde aqui NoSuchElementException
        Vertice y=(Vertice)vertice(b);

        x.vecinos.agrega(new Vecino(y,1));
        y.vecinos.agrega(new Vecino(x,1));
        aristas++;
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @param peso el peso de la nueva vecino.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, si a es
     *         igual a b, o si el peso es no positivo.
     */
    public void conecta(T a, T b, double peso) {
        // Aquí va su código.
        Vertice x=(Vertice)vertice(a); // lanzamos desde aqui NoSuchElementException
        Vertice y=(Vertice)vertice(b);

        if(a.equals(b) || sonVecinos(a,b) || peso<=0) throw new IllegalArgumentException();

        x.vecinos.agrega(new Vecino(y,peso));
        y.vecinos.agrega(new Vecino(x,peso));
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
          Vertice va = (Vertice) vertice(a);
          Vertice vb = (Vertice) vertice(b);
          if(a.equals(b) || !sonVecinos(a,b))
            throw new IllegalArgumentException();

        Vecino deA = null, deB = null;
          for(Vecino v : va.vecinos)
              if(v.vecino.equals(vb))
                    deA = v;
          for (Vecino v : vb.vecinos)
              if (v.vecino.equals(va))
                    deB = v;
            va.vecinos.elimina(deA);
            vb.vecinos.elimina(deB);
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
              Vertice eliminable = (Vertice) vertice(elemento);
              for(Vertice v : vertices)
                  for(Vecino vec : v.vecinos)
                      if(vec.vecino.equals(eliminable)) {
                          v.vecinos.elimina(vec);
                          aristas--;
                      }
              vertices.elimina(eliminable);
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
        for (Vecino ve : x.vecinos)
                  if (ve.vecino.equals(y))
                      return true;
          return false;
    }

    /**
     * Regresa el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return el peso de la arista que comparten los vértices que contienen a
     *         los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public double getPeso(T a, T b) {
        // Aquí va su código.
        if(!contiene(a) || !contiene(b))
            throw new NoSuchElementException();
        if(!sonVecinos(a,b))
            throw new IllegalArgumentException();
        Vertice x = (Vertice) vertice(a);
        Vertice y = (Vertice) vertice(b);
        for(Vecino v : x.vecinos)
            if(v.vecino.equals(y))
                return v.peso;
       return -1;
    }

    /**
     * Define el peso de la arista que comparten los vértices que contienen a
     * los elementos recibidos.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @param peso el nuevo peso de la arista que comparten los vértices que
     *        contienen a los elementos recibidos.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados, o si peso
     *         es menor o igual que cero.
     */
    public void setPeso(T a, T b, double peso) {

      Vertice x = (Vertice)vertice(a);
      Vertice y = (Vertice)vertice(b);

      if(a.equals(b) || !sonVecinos(a,b) || peso <= 0)
          throw new IllegalArgumentException();

      for(Vecino v : x.vecinos)
          if(v.vecino.equals(y))
              v.peso = peso;
      for(Vecino v : y.vecinos)
         if(v.vecino.equals(x))
              v.peso = peso;
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        // Aquí va su código.
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
        String m;
        if(vertice==null || (vertice.getClass() != Vertice.class && vertice.getClass() != Vecino.class)){
            m = "Vértice inválido";
          throw new IllegalArgumentException(m);
        }

        if(vertice.getClass() == Vertice.class){
          Vertice v = (Vertice)vertice;
          v.color=color;
        }
        if(vertice.getClass() == Vecino.class){
          Vecino v=(Vecino)vertice;
          v.vecino.color=color;
        }
    }

    /**
    * Nos dice si la gráfica es conexa.
    * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
    *         otro caso.
    */
   public boolean esConexa() {
       // Aquí va su código.
       Cola<Vertice> c= new Cola<>();
       Vertice v= vertices.getPrimero();
       return recorreDFSBFS(v.get(),c) == vertices.getElementos();
   }

   /**
   *Nos regresa un int para comparar con todos los elementos
   *@param elemento el tipo de elemento
   *@param m MeteSaca<Vertice>
   */
   private int recorreDFSBFS(T elemento, MeteSaca<Vertice> m){
     Vertice v=(Vertice)vertice(elemento);
     Vertice v1;
     int i=0;
     paraCadaVertice(x-> setColor(x,Color.NINGUNO));
     m.mete(v);
     v.color=Color.NEGRO;
     while(!m.esVacia()){
       v1=m.saca();

       i++;
         for(Vecino vec: v1.vecinos){
           if(vec.vecino.color==Color.NINGUNO){
             m.mete(vec.vecino);
             vec.vecino.color=Color.NEGRO;
           }
         }
       }

       paraCadaVertice(x-> setColor(x,Color.NINGUNO));
       return i;
   }


    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        for(Vertice v : this.vertices)
            accion.actua(v);
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
      Cola<Vertice> c= new Cola<>();
      recorreDFSBFS(elemento, accion, c);
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
      Pila<Vertice> p= new Pila<>();
      recorreDFSBFS(elemento, accion, p);
    }

    /**
    *Permite recorrer por DFS  o BFS
    *@param elemento elemento
    *@param accion de AccionVerticeGrafica
    *@param m MeteSaca<Vertice>
    */
    private void recorreDFSBFS(T elemento, AccionVerticeGrafica<T> accion, MeteSaca<Vertice> m){
      Vertice v=(Vertice)vertice(elemento);
      Vertice v1;
      paraCadaVertice(x-> setColor(x,Color.NINGUNO));
      m.mete(v);
      v.color=Color.NEGRO;
      while(!m.esVacia()){
        v1=m.saca();
        accion.actua(v1);
          for(Vecino vec: v1.vecinos){
            if(vec.vecino.color==Color.NINGUNO){
              m.mete(vec.vecino);
              vec.vecino.color=Color.NEGRO;
            }
          }
        }

        paraCadaVertice(x-> setColor(x,Color.NINGUNO));

    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return  vertices.esVacia();
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
              for(Vecino a: v.vecinos){
                if(a.getColor()==Color.ROJO){
                  ar+="("+v.get()+", "+ a.get()+"), ";
                  setColor(v, Color.NEGRO);
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


        if(this.getElementos()!= grafica.getElementos() || this.getAristas()!=grafica.getAristas())
          return false;

        for(Vertice v : vertices) {
            v.color = Color.ROJO;
            if(!grafica.contiene(v.elemento))
                  return false;
                }

                for(Vertice v : vertices) {

                    for(Vecino y : v.vecinos){
                      if(y.getColor() == Color.ROJO)
                        if(!grafica.sonVecinos(y.get(),v.elemento))
                            return false;
                    }
          v.color = Color.NEGRO;
       }



        return true;
        // Aquí va su código.
    }


    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Calcula una trayectoria de distancia mínima entre dos vértices.
     * @param origen el vértice de origen.
     * @param destino el vértice de destino.
     * @return Una lista con vértices de la gráfica, tal que forman una
     *         trayectoria de distancia mínima entre los vértices <code>a</code> y
     *         <code>b</code>. Si los elementos se encuentran en componentes conexos
     *         distintos, el algoritmo regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> trayectoriaMinima(T origen, T destino) {
        // Aquí va su código.
        Vertice x =(Vertice) vertice(origen);
        Vertice y =(Vertice) vertice(destino);
      Lista<VerticeGrafica<T>> lista = new Lista<VerticeGrafica<T>>();
      Cola<Vertice> cola = new Cola<Vertice>();
            if(origen.equals(destino)) {
                  lista.agrega(x);
                  return lista;
              }
      for(Vertice v : vertices)
            v.distancia = Double.MAX_VALUE;

            x.distancia = 0;
            cola.mete(x);
            while(!cola.esVacia()){
                  x = cola.saca();
                for(Vecino vecino : x.vecinos)
                  if(vecino.vecino.distancia == Double.MAX_VALUE) {
                    vecino.vecino.distancia = x.distancia +1;
                    cola.mete(vecino.vecino);
                    }
                  }
              if(y.distancia == Double.MAX_VALUE)
                  return lista;
              lista.agrega(y);
                  while(!x.elemento.equals(origen))
                  for(Vecino vertice : x.vecinos)
                      if(x.distancia == vertice.vecino.distancia + 1) {
                          lista.agrega(vertice.vecino);
                          x = vertice.vecino;
                      }
              return lista.reversa();

    }




    /**
     * Calcula la ruta de peso mínimo entre el elemento de origen y el elemento
     * de destino.
     * @param origen el vértice origen.
     * @param destino el vértice destino.
     * @return una trayectoria de peso mínimo entre el vértice <code>origen</code> y
     *         el vértice <code>destino</code>. Si los vértices están en componentes
     *         conexas distintas, regresa una lista vacía.
     * @throws NoSuchElementException si alguno de los dos elementos no está en
     *         la gráfica.
     */
    public Lista<VerticeGrafica<T>> dijkstra(T origen, T destino) {
        // Aquí va su código.
        Lista<VerticeGrafica<T>> l= new Lista<>();
        Vertice x=(Vertice) vertice(origen);
        Vertice y=(Vertice) vertice(destino);

        for(Vertice v : vertices)
              v.distancia = Double.MAX_VALUE;

              x.distancia = 0;
    MonticuloMinimo<Vertice> monticulo = new MonticuloMinimo<Vertice>(vertices);
    Vertice u;
    while(!monticulo.esVacia()){
      u=monticulo.elimina();

      for(Vecino vecino: u.vecinos){
        if((vecino.vecino.distancia) > (u.distancia + vecino.peso)){
           vecino.vecino.distancia = u.distancia + vecino.peso;
           monticulo.reordena(vecino.vecino);
        }
      }
    }

    if(y.distancia == Double.MAX_VALUE)
      return l;
      l.agrega(y);
      while(!y.elemento.equals(origen))
         for(Vecino ve : y.vecinos)
         if(y.distancia == ve.vecino.distancia + ve.peso) {
                 l.agrega(ve.vecino);
                    y = ve.vecino;
                }



        return l.reversa();
      }

}
