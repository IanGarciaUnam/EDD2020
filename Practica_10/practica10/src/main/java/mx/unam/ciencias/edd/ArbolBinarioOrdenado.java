package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        public Iterador() {
            // Aquí va su código.
              pila= new Pila<>();
            metePila(raiz);

        }


        private void metePila(Vertice v){

        if(v==null)return;

          pila.mete(v);
          metePila(v.izquierdo);

                }




        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return !pila.esVacia();
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            // Aquí va su código.

            Vertice vista=pila.saca();
            T into=vista.get();


            if(vista.hayDerecho())
            {
              vista=vista.derecho;

              metePila(vista);

            }



                return into;

        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento==null)
        throw new IllegalArgumentException();



        if(raiz==null){
            Vertice agregando=nuevoVertice(elemento);
          ultimoAgregado=raiz= agregando;
          raiz.padre=null;
          raiz.izquierdo=null;
          raiz.derecho=null;
        }else{
        axlAgrega(this.raiz, elemento);
        }
          elementos++;


    }

    private void axlAgrega(Vertice papa, T elemento){


        if(elemento.compareTo(papa.get())<=0){

          if(!papa.hayIzquierdo()){
              Vertice nuevo= nuevoVertice(elemento);

            ultimoAgregado=papa.izquierdo=nuevo;
            nuevo.padre=papa;
          }else{
            axlAgrega(papa.izquierdo,elemento);

          }

        }else{

          if(!papa.hayDerecho()){
              Vertice nuevo= nuevoVertice(elemento);

            ultimoAgregado=papa.derecho=nuevo;
            nuevo.padre=papa;

          }else{
            axlAgrega(papa.derecho,elemento);
          }



        }


    }






    private boolean esHijoIzquierdo(Vertice c){
      if(c==null)return false;

      if(!c.hayPadre()) return false;

      return c.padre.izquierdo==c;

    }


    private boolean esHijoDerecho(Vertice c){
      if(c==null)return false;

        if(!c.hayPadre()) return false;

      return c.padre.derecho==c ;

    }

    /**
    *Preguntamos si tiene hijo hayIzquierdo y tiene hijo derecho en ese orden
    *
    */
    private boolean tieneAmbosHijos(Vertice c){
      if(c==null)return false;

      return c.hayIzquierdo() && c.hayDerecho();
    }


  private Vertice maximoEnSubarbol(Vertice v){
    if(v.derecho==null)
    return v;
    return maximoEnSubarbol(v.derecho);

  }

    private Vertice busca(Vertice v, T elemento){

      if(v==null || elemento == null)
      return null;


      Vertice vIzq=null;
      Vertice vDer=null;

      if(elemento.compareTo(v.get())==0)
      return v;

      if(elemento.compareTo(v.get())<=0)
      vIzq=busca(v.izquierdo, elemento);
      else
      vDer=busca(v.derecho,elemento);

      return vIzq != null ? vIzq : vDer;


    }




    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.

        Vertice v = vertice(busca(elemento));


        if(elemento==null || v == null)return;

        if(!v.hayDerecho() && !v.hayIzquierdo() )
          {
            eliminaVertice(v);
            elementos--;

            return;
          }else  if(!v.hayIzquierdo() && v.hayDerecho() )
            {
                eliminaVertice(v);
                elementos--;

                return ;
         }else  if(v.hayIzquierdo() && !v.hayDerecho() )
          {
            eliminaVertice(v);
            elementos--;

            return ;
          }else if(v.hayDerecho() && v.hayIzquierdo() )
          {
            Vertice axl=null;
            axl=intercambiaEliminable(v);
            eliminaVertice(axl);
            elementos--;

            return ;
          }





  }





    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        // Aquí va su código.
        Vertice maximal=maximoEnSubarbol(vertice.izquierdo);
        T axl=vertice.get();
        vertice.elemento=maximal.get();
        maximal.elemento=axl;

        return maximal;




    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        // Aquí va su código
        Vertice axl= vertice.hayIzquierdo()? vertice.izquierdo : vertice.derecho;

        if(vertice.hayPadre()){
          if(vertice.padre.izquierdo==vertice){
            vertice.padre.izquierdo=axl;


          }else{
            vertice.padre.derecho=axl;
          }


        }

        if(!vertice.hayPadre())
          raiz=axl;


          if(axl != null)
          axl.padre=vertice.padre;



    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        // Aquí va su código.
      return axlBusca2(raiz, elemento);
    }


    private VerticeArbolBinario<T> axlBusca2(Vertice v, T elemento){

      if(v==null || elemento==null)return null;

      if(elemento.compareTo(v.get())==0)
      return v;

      if(elemento.compareTo(v.get())<=0)
        return axlBusca2(v.izquierdo, elemento);



        return axlBusca2(v.derecho, elemento);
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        // Aquí va su código.
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        if (vertice == null || !vertice.hayIzquierdo()) {
              return;
          }
          Vertice paraGirar = vertice(vertice);
          Vertice nuevoAtras = paraGirar.izquierdo;
          nuevoAtras.padre = paraGirar.padre;
          if (paraGirar == raiz) {
            raiz = nuevoAtras;
          } else {
            if (this.esHijoIzquierdo(paraGirar)) {
                nuevoAtras.padre.izquierdo = nuevoAtras;
            } else {
                nuevoAtras.padre.derecho = nuevoAtras;
            }
          }
          paraGirar.izquierdo = nuevoAtras.derecho;
          if (nuevoAtras.hayDerecho()) {
              nuevoAtras.derecho.padre = paraGirar;
          }
          paraGirar.padre = nuevoAtras;
          nuevoAtras.derecho = paraGirar;

    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        if (vertice == null || !vertice.hayDerecho()) {
          return;
      }
      Vertice v = this.vertice(vertice);
      Vertice reemplazo = v.derecho;
      reemplazo.padre = v.padre;
      if (v != this.raiz) {
          if (this.esHijoIzquierdo(v)) {
              reemplazo.padre.izquierdo = reemplazo;
          } else {
              reemplazo.padre.derecho = reemplazo;
          }
      } else {
          this.raiz = reemplazo;
      }
      v.derecho = reemplazo.izquierdo;
      if (reemplazo.hayIzquierdo()) {
          reemplazo.izquierdo.padre = v;
      }
      v.padre = reemplazo;
      reemplazo.izquierdo = v;
  }



    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsPreOrder(raiz,accion);

    }

    private void dfsPreOrder(Vertice v, AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        if(v==null)return;

        accion.actua(v);

        dfsPreOrder(v.izquierdo, accion);
        dfsPreOrder(v.derecho, accion);


    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsInOrder(raiz, accion);
    }

    private void dfsInOrder(Vertice v, AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        if(v==null)return;

        dfsInOrder(v.izquierdo,accion);
        accion.actua(v);
        dfsInOrder(v.derecho, accion);
    }



    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        dfsPostOrder(raiz, accion);
    }

    private void dfsPostOrder(Vertice v, AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
        if(v==null)return ;

        dfsPostOrder(v.izquierdo,accion);
        dfsPostOrder(v.derecho,accion);
        accion.actua(v);

    }



    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
