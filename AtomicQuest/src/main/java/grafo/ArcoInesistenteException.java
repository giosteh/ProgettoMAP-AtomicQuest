
package grafo;

/**
 *
 * @author giosteh
 */
public class ArcoInesistenteException extends Exception {

    @Override
    public String getMessage() {
        return "Arco non esistente.\n";
    }
}
