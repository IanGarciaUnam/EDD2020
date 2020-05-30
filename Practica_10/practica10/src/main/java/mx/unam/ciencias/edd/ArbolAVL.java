package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            // Aquí va su código
            super(elemento);
          altura=0;
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.
            return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            // Aquí va su código.
            return super.elemento.toString()+" "+this.altura()+"/"+balance(this);
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            // Aquí va su código.
            return (altura==vertice.altura && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() { super(); }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeAVL(elemento);
    }

    /**
      *Casteamos a un Vertice ArbolBinarioOrdenado to VerticeAVL
      *
      */
    private VerticeAVL VerticeAVL(VerticeArbolBinario<T> v){
      return (VerticeAVL) v;
    }



    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);

        rebalanceoAxl(VerticeAVL(ultimoAgregado.padre));

    }

    private int balance(VerticeAVL v){
        if(v==null)return 0;
        return altura(VerticeAVL(v.izquierdo)) - altura(VerticeAVL(v.derecho));
    }

    private int altura(VerticeAVL avl){
      if(avl==null)return -1;

      return avl.altura;
    }

    private int actualiza(VerticeAVL v){
      return 1 + Integer.max(altura(VerticeAVL(v.izquierdo)), altura(VerticeAVL(v.derecho))) ;
    }

    private void rebalanceoAxl(VerticeAVL avl){

        if(avl==null)return; //Escape


        avl.altura=actualiza(avl);

        int balance=balance(avl);
        if(balance(avl)==-2){
            VerticeAVL q = VerticeAVL(avl.derecho);
            if(balance(q)==1){
              super.giraDerecha(q);
              q.altura=actualiza(q);
              q=VerticeAVL(avl.derecho);
            }
            super.giraIzquierda(avl);
          avl.altura = actualiza(avl);
        }else if(balance==2){
          VerticeAVL p = VerticeAVL(avl.izquierdo);

            if(balance(avl) == -1){
              super.giraIzquierda(p);
              p.altura=actualiza(p);
              p=VerticeAVL(avl.izquierdo);
            }
          super.giraDerecha(avl);
          avl.altura=actualiza(avl);
        }

        rebalanceoAxl(VerticeAVL(avl.padre));
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if(elemento==null) return;

        VerticeAVL e = VerticeAVL(super.busca(elemento));

        if(e==null)return;


        if(e.hayIzquierdo() && e.hayDerecho())
          e= VerticeAVL(super.intercambiaEliminable(e));
        /***Verificar   que solo tenga un hijo***/
        boolean fantasma=false;
        if(!e.hayIzquierdo() && !e.hayDerecho()){
          /**Agregamos hijo fantasma s.p.g -> hayIzquierdo**/
          e.izquierdo=nuevoVertice(null);
          e.izquierdo.padre=e;
          fantasma=true;
        }

        /****Desconectando vértices***/
        if(e.hayDerecho() ^e.hayIzquierdo())
        eliminaVertice(e);

        if(fantasma)
        eliminaVertice(e.izquierdo);


        rebalanceoAxl(VerticeAVL(e.padre));
        elementos--;
    }


    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
