package mx.unam.ciencias.edd;

import java.util.Iterator;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        private Vertice lastAdded;

        /* Inicializa al iterador. */
        public Iterador() {
          cola = new Cola<Vertice>();
          if(raiz != null)
            cola.mete(raiz);
            // Aquí va su código.
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return !cola.esVacia();
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            // Aquí va su código.
            Vertice salida= cola.saca();


            if(salida.hayIzquierdo())
            cola.mete(salida.izquierdo);
            if(salida.hayDerecho())
            cola.mete(salida.derecho);





            return salida.get();

        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.

        if(elemento == null )throw new IllegalArgumentException();


          agregaLineal(elemento);


        }







        private void agregaLineal(T elemento){

          Vertice agregando= nuevoVertice(elemento);
          elementos++;

          if(raiz==null){
            raiz=agregando;
            return ;
          }

          Vertice axl= raiz;
          Cola<Vertice> recogedor= new Cola<>();
          recogedor.mete(axl);

          while(!recogedor.esVacia())
          {

            axl=recogedor.saca();

            if(axl.hayIzquierdo())
            recogedor.mete(axl.izquierdo);
              else{
                axl.izquierdo=agregando;
                agregando.padre=axl;
                return;
              }

            if(axl.hayDerecho())
              recogedor.mete(axl.derecho);
             else{
              axl.derecho=agregando;
              agregando.padre=axl;
              return;
            }
        }


        }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.

        if (elemento == null || raiz== null)return;

        Vertice sujetoParaEliminar= busca(raiz, elemento);
        if(sujetoParaEliminar==null)
        return ;


            elementos--;
        if(elementos==0)
        {
          raiz=null;
          return;

        }




        Vertice axl=raiz;

        Cola<Vertice> recogedor= new Cola<>();
        recogedor.mete(axl);

        while(!recogedor.esVacia())
        {
          axl=recogedor.saca();

          if(axl.hayIzquierdo())
          recogedor.mete(axl.izquierdo);

          if(axl.hayDerecho())
          recogedor.mete(axl.derecho);
        }



        sujetoParaEliminar.elemento= axl.get();



        if(axl.padre.hayDerecho()){
          axl.padre.derecho=null;
        }else if(axl.padre.hayIzquierdo()){
          axl.padre.izquierdo=null;
        }








        }







    private Vertice busca(Vertice v, T elemento){

          if(v==null || elemento == null)
          return null;

          if(v.get().equals(elemento))
          return v;

          Vertice vIzq= busca(v.izquierdo, elemento);
          Vertice vDer =busca(v.derecho,elemento);




          return vIzq != null ? vIzq : vDer;


      }




    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        // Aquí va su código.

          if(raiz==null)return -1;

        return (int)(Math.log10(elementos)/Math.log10(2));

    }


    private Vertice bfsUltimoVertice(Vertice corredor){
      Vertice axl=corredor;

      Cola<Vertice> recogedor= new Cola<>();
      recogedor.mete(axl);

      while(!recogedor.esVacia())
      {
        axl=recogedor.saca();

        if(axl.hayIzquierdo())
        recogedor.mete(axl.izquierdo);

        if(axl.hayDerecho())
        recogedor.mete(axl.derecho);
      }
      return axl;
    }


    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        // Aquí va su código.
         Cola<Vertice> cola = new Cola<Vertice>();
        while(!cola.esVacia()){
          Vertice sacado=cola.saca();

          accion.actua(sacado);
          if(sacado.hayIzquierdo())
          cola.mete(sacado.izquierdo);
          if(sacado.hayDerecho());
          cola.mete(sacado.derecho);

        }
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
