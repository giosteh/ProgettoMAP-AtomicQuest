
package grafo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class GrafoMap<N, T> implements Grafo<N, T>, Serializable {

    private final Map<N, Map<N, T>> grafo = new HashMap<>();

    public GrafoMap() {
    }

    public boolean isVuoto() {
        return this.grafo.isEmpty();
    }

    public boolean esisteNodo(final N nodo) {
        return this.grafo.containsKey(nodo);
    }

    public boolean esisteArco(final N nodo1, final N nodo2) {
        if (!this.esisteNodo(nodo1) || !this.esisteNodo(nodo2)) {
            return false;
        }
        return this.grafo.get(nodo1).containsKey(nodo2);
    }

    public void inserisciNodo(final N nodo) {
        if (this.esisteNodo(nodo)) {
            return;
        }
        Map<N, T> adj = new HashMap<>();
        this.grafo.put(nodo, adj);
    }

    public void inserisciNodi(final N... nodi) {
        for (N nodo : nodi) {
            if (!this.grafo.containsKey(nodo)) {
                Map<N, T> adj = new HashMap<>();
                this.grafo.put(nodo, adj);
            }
        }
    }

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

    public Set<N> adiacenti(final N nodo)
            throws NodoInesistenteException {
        if (!this.esisteNodo(nodo)) {
            throw new NodoInesistenteException();
        }
        return this.grafo.get(nodo).keySet();
    }

    public Set<Map.Entry<N, T>> adiacentiEntries(final N nodo)
            throws NodoInesistenteException {
        if (!this.esisteNodo(nodo)) {
            throw new NodoInesistenteException();
        }
        return this.grafo.get(nodo).entrySet();
    }
}
