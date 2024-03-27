
package grafo;

/**
 * Eccezione lanciata quando si cerca di inserire un arco con un nodo non esistente.
 */
public class NodoInesistenteException extends Exception {

    @Override
    public String getMessage() {
        return "Nodo non esistente.\n";
    }
}
