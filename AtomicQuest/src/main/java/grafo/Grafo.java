
package grafo;

import java.util.Set;

/**
 *
 * @param <N>
 * @param <T>
 */
public interface Grafo<N, T> {
    
    public boolean isVuoto();
    
    public void inserisciNodo(final N nodo);
    
    public void inserisciArco(final N nodo1, final N nodo2) throws Exception;
    
    public boolean esisteNodo(final N nodo);
    
    public boolean esisteArco(final N nodo1, final N nodo2);
    
    public void scriviArco(final N nodo1, final N nodo2, final T val) throws Exception;
    
    public T leggiArco(final N nodo1, final N nodo2) throws Exception;
    
    public Set<N> adiacenti(final N nodo) throws Exception;
}
