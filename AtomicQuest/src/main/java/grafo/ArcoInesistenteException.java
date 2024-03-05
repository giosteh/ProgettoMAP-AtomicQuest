
package grafo;


public class ArcoInesistenteException extends Exception {

    @Override
    public String getMessage() {
        return "Arco non esistente.\n";
    }
}
