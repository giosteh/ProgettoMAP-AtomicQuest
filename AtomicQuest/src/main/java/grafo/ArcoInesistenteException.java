
package grafo;

/**
 * Eccezione lanciata quando si cerca di rimuovere un arco non esistente.
 */
public class ArcoInesistenteException extends Exception {

    @Override
    public String getMessage() {
        return "Arco non esistente.\n";
    }
}
