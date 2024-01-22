
package grafo;

/**
 *
 * @author giosteh
 */
public class NodoInesistenteException extends Exception {
    
    @Override
    public String getMessage() {
        return "Nodo non esistente.\n";
    }
}
