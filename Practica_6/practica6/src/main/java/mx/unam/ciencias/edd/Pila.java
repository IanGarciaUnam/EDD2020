package mx.unam.ciencias.edd;

/**
 * Clase para pilas genéricas.
 */
public class Pila<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la pila.
     * @return una representación en cadena de la pila.
     */
    @Override public String toString() {
        // Aquí va su código.
        String s="";
        Nodo axl= super.cabeza;
        if(axl==null){
        s="";return s;
        }

        while(axl != null){
          s+=axl.elemento+"\n";

          axl=axl.siguiente;
        }



        return s;
    }

    /**
     * Agrega un elemento al tope de la pila.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        // Aquí va su código.
        if(elemento==null)
        throw new IllegalArgumentException();




        if(cabeza==null){
          Nodo n= new Nodo(elemento);
        cabeza=rabo=n;
        return;
        }

        if(cabeza != null){
          Nodo n= new Nodo(elemento);
        n.siguiente=cabeza;
        cabeza= n;
      }



    }

}
