package mx.unam.ciencias.edd;

/**
 * Clase para colas genéricas.
 */
public class Cola<T> extends MeteSaca<T> {

    /**
     * Regresa una representación en cadena de la cola.
     * @return una representación en cadena de la cola.
     */
    @Override public String toString() {
        // Aquí va su código.
        Nodo axl = cabeza;
        int i =0;

        String toDevolve="";
        if(cabeza==null)
        return toDevolve;



        while(axl != null){
          toDevolve+=axl.elemento+",";
          axl=axl.siguiente;
        }
        return toDevolve;


    }

    /**
     * Agrega un elemento al final de la cola.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void mete(T elemento) {
        // Aquí va su código.

        if(elemento==null)
        throw new IllegalArgumentException();

        Nodo aux = new Nodo(elemento);
        if(super.cabeza==null){
        super.cabeza=super.rabo=aux;
        return;
      }

        if(super.cabeza != null){
        super.rabo.siguiente= aux;
        super.rabo= aux;
        return;
      }

    }
}
