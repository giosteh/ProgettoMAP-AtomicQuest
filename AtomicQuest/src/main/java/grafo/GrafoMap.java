
package grafo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Implementazione di un grafo mediante l'utilizzo di una mappa.
 */
public class GrafoMap<N, T> implements Grafo<N, T>, Serializable {

    private final Map<N, Map<N, T>> grafo = new HashMap<>();

    /**
     * Costruttore di default.
     */
    public GrafoMap() {
    }

    /**
     * Metodo che restituisce true se il grafo è vuoto, false altrimenti.
     */
    public boolean isVuoto() {
        return this.grafo.isEmpty();
    }

    /**
     * Metodo che restituisce true se esiste un nodo nel grafo, false altrimenti.
     * @param nodo il nodo da cercare
     */
    public boolean esisteNodo(final N nodo) {
        return this.grafo.containsKey(nodo);
    }

    /**
     * Metodo che restituisce true se esiste un arco tra due nodi, false altrimenti.
     * @param nodo1 il primo nodo
     * @param nodo2 il secondo nodo
     */
    public boolean esisteArco(final N nodo1, final N nodo2) {
        if (!this.esisteNodo(nodo1) || !this.esisteNodo(nodo2)) {
            return false;
        }
        return this.grafo.get(nodo1).containsKey(nodo2);
    }

    /**
     * Metodo che inserisce un nodo nel grafo.
     * @param nodo il nodo da inserire
     */
    public void inserisciNodo(final N nodo) {
        if (this.esisteNodo(nodo)) {
            return;
        }
        Map<N, T> adj = new HashMap<>();
        this.grafo.put(nodo, adj);
    }

    /**
     * Metodo che inserisce più nodi nel grafo.
     * @param nodi i nodi da inserire
     */
    public void inserisciNodi(final N... nodi) {
        for (N nodo : nodi) {
            if (!this.grafo.containsKey(nodo)) {
                Map<N, T> adj = new HashMap<>();
                this.grafo.put(nodo, adj);
            }
        }
    }

    /**
     * Metodo che inserisce un arco tra due nodi.
     * @param nodo1 il primo nodo
     * @param nodo2 il secondo nodo
     */
    public void inserisciArco(final N nodo1, final N nodo2)
            throws NodoInesistenteException {
        if (!this.esisteNodo(nodo1) || !this.esisteNodo(nodo2)) {
            throw new NodoInesistenteException();
        }
        if (this.esisteArco(nodo1, nodo2)) {
            return;
        }
        T val = null;
        this.grafo.get(nodo1).put(nodo2, val);
    }

    /**
     * Metodo che inserisce un arco tra due nodi con un'etichetta.
     * @param nodo1 il primo nodo
     * @param nodo2 il secondo nodo
     * @param val l'etichetta dell'arco
     */
    public void inserisciArcoConEtichetta(final N nodo1, final N nodo2, final T val)
            throws NodoInesistenteException {
        if (!this.esisteNodo(nodo1) || !this.esisteNodo(nodo2)) {
            throw new NodoInesistenteException();
        }
        if (this.esisteArco(nodo1, nodo2)) {
            return;
        }
        this.grafo.get(nodo1).put(nodo2, val);
    }

    /**
     * Metodo che restituisce l'etichetta di un arco tra due nodi.
     * @param nodo1 il primo nodo
     * @param nodo2 il secondo nodo
     */
    public T leggiArco(final N nodo1, final N nodo2)
            throws NodoInesistenteException, ArcoInesistenteException {
        if (!this.esisteNodo(nodo1) || !this.esisteNodo(nodo2)) {
            throw new NodoInesistenteException();
        }
        if (!this.esisteArco(nodo1, nodo2)) {
            throw new ArcoInesistenteException();
        }
        return this.grafo.get(nodo1).get(nodo2);
    }

    /**
     * Metodo che restituisce l'insieme dei nodi adiacenti a un nodo.
     * @param nodo il nodo di cui si vogliono conoscere i nodi adiacenti
     */
    public Set<N> adiacenti(final N nodo)
            throws NodoInesistenteException {
        if (!this.esisteNodo(nodo)) {
            throw new NodoInesistenteException();
        }
        return this.grafo.get(nodo).keySet();
    }

    /**
     * Metodo che restituisce l'insieme delle coppie nodo-etichetta adiacenti a un nodo.
     * @param nodo il nodo di cui si vogliono conoscere le coppie nodo-etichetta adiacenti
     */
    public Set<Map.Entry<N, T>> adiacentiEntries(final N nodo)
            throws NodoInesistenteException {
        if (!this.esisteNodo(nodo)) {
            throw new NodoInesistenteException();
        }
        return this.grafo.get(nodo).entrySet();
    }

}
