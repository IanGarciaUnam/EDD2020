package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            // Aquí va su código.
            super(elemento);
            color=color.NINGUNO;

        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        public String toString() {
            // Aquí va su código.
            if(getColor(this)==color.ROJO)
            return "R{"+elemento.toString()+"}";

            return "N{"+elemento.toString()+"}";
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
            // Aquí va su código.
            return (color==vertice.color && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeRojinegro(elemento);
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        VerticeRojinegro vRN=(VerticeRojinegro)vertice;
        return vRN.color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);
        VerticeRojinegro vrn= (VerticeRojinegro)ultimoAgregado;
        vrn.color=Color.ROJO;
        rebalanceoAux(vrn);

          //Caso-----------------------------1
    }

    /**
    *Método recursivo de rebalanceo
    *
    */

    private void rebalanceoAux(VerticeRojinegro v){
      VerticeRojinegro tio,padre,abuelo=null;

      //caso 1 no hay padre
      if(!v.hayPadre()){
        raiz=v;
        v.color=Color.NEGRO;
        return;
      }

      //Caso2
      /** como padre es negro y el vertice agregado rojo, no desbalanceamos y terminamos**/
        padre= (VerticeRojinegro)v.padre;
          if(esNegro(padre))
          return;


          abuelo=(VerticeRojinegro)padre.padre;

          /**Encontran5do a  tio**/

          tio=getTio(abuelo, padre);


          /**tio encontrado**/

          /**Caso 3 rebalancea el subarbol T(A)**/

          if( esRojo(tio)){
            tio.color=padre.color=Color.NEGRO;
            abuelo.color=Color.ROJO;
            rebalanceoAux(abuelo);
            return;
          }




/** CASO 4  vertices cruzados CONTINUA AL 5**/


          if(padre.izquierdo==v ^ abuelo.izquierdo==padre){
            if(abuelo.izquierdo==padre)
              super.giraIzquierda(padre);
            else
              super.giraDerecha(padre);

              VerticeRojinegro aux= padre;
              padre=v;
              v=aux;
          }




  /**caso 5 sin necesidad de verificación**/
            padre.color= Color.NEGRO;
            abuelo.color=Color.ROJO;

            if(padre.izquierdo==v)
            super.giraDerecha(abuelo);
            else
            super.giraIzquierda(abuelo);

    }

    private VerticeRojinegro getTio(VerticeRojinegro abuelo, VerticeRojinegro padre){
      if (abuelo.izquierdo== padre)
      return (VerticeRojinegro) abuelo.derecho;
      else
      return (VerticeRojinegro)abuelo.izquierdo;
    }





    private boolean esRojo(VerticeRojinegro vrn){
      return(vrn != null && vrn.color==Color.ROJO);

      }

    private boolean esNegro(VerticeRojinegro vrn){
      return(vrn == null || vrn.color==Color.NEGRO);

    }

    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> v){
      VerticeRojinegro rjn= (VerticeRojinegro) v;
      return rjn;

    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {


        // Aquí va su código.
        VerticeRojinegro vrn= verticeRojinegro(busca(elemento));

        //No hay elemento
        if(vrn==null)return;

        VerticeRojinegro vrni=null;//erticeRojinegro(nuevoVertice(null));



        if(vrn.hayIzquierdo() && vrn.hayDerecho()){

          vrn= verticeRojinegro(super.intercambiaEliminable(vrn));
        }

        /*****Verificar   que solo tenga un hijo****/

        VerticeRojinegro fantasma=null;
        if(!vrn.hayIzquierdo() && !vrn. hayDerecho()){
          /**Agregamos hijo fantasma s.p.g -> hayIzquierdo**/
          vrn.izquierdo=nuevoVertice(null);
          vrni=verticeRojinegro(vrn.izquierdo);
          vrni.padre=vrn;
          vrni.color=Color.NEGRO;
          fantasma=vrni;
        }




        /****Desconectando vértices***/
          super.eliminaVertice(vrn);
          VerticeRojinegro hijo= verticeRojinegro((vrn.hayIzquierdo()? vrn.izquierdo : vrn.derecho));
        if(esRojo(hijo)){

          hijo.color=Color.NEGRO;
        }else if(esNegro(vrn) && esNegro(hijo)){
          rebalanceoElimina(hijo);
        }

        if(vrni != null)
        eliminaVertice(vrni);
        elementos--;

    }


    private void rebalanceoElimina(VerticeRojinegro vrn){
  /**    //Caso 1**/
      if(!vrn.hayPadre())
        return;


      VerticeRojinegro p=  verticeRojinegro(vrn.padre);
      VerticeRojinegro h = getHermano(vrn);
      //Caso2
      if(esRojo(h) && esNegro(p)){

        p.color=Color.ROJO;
        h.color=Color.NEGRO;

        if(vrn.padre.izquierdo==vrn)
        super.giraIzquierda(p);
        else
        super.giraDerecha(p);

        p=verticeRojinegro(vrn.padre);
        h=getHermano(vrn);

      }
        /**Fin de caso 2**/

        VerticeRojinegro  hi=verticeRojinegro(h.izquierdo);
        VerticeRojinegro hd=verticeRojinegro(h.derecho);


        /**Caso3**/
        if(esNegro(p) && esNegro(h) && esNegro(hi)  && esNegro(hd)) {
          //ACTUALIZAR H PARA VOLVER A SER HERMANO DE VRN

          h.color=Color.ROJO;
          rebalanceoElimina(p);
          return;

        }

        /**Caso 4**/
          if(esRojo(p) && esNegro(h) && esNegro(hi) && esNegro(hd)){

            h.color=Color.ROJO;
            p.color=Color.NEGRO;
            return;
          }

        /**FIn caso 4**/
        /**Caso 5**/

        if(vrn.padre.izquierdo==vrn  && esRojo(hi) && esNegro(hd)){

          h.color=Color.ROJO;
          hi.color=Color.NEGRO;
          super.giraDerecha(h);

        }else if(vrn.padre.derecho==vrn && esNegro(hi) && esRojo(hd)){

          h.color=Color.ROJO;
          hd.color=Color.NEGRO;
          super.giraIzquierda(h);

        }

        h=getHermano(vrn);
        hi=verticeRojinegro(h.izquierdo);
        hd=verticeRojinegro(h.derecho);


        /**Caso 6**/

          h.color=p.color;
          p.color=Color.NEGRO;

          if(vrn.padre.izquierdo == vrn){
              hd.color=Color.NEGRO;

            super.giraIzquierda(p);

          }else{
            
            hi.color=Color.NEGRO;
            super.giraDerecha(p);
          }





    }




    private VerticeRojinegro getHermano(VerticeRojinegro vrn){

        return  vrn.padre.hayIzquierdo()  && vrn.padre.izquierdo != vrn? (VerticeRojinegro)vrn.padre.izquierdo : (VerticeRojinegro)vrn.padre.derecho;
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
