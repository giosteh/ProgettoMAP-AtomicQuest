
package entita;

import grafo.NodoInesistenteException;
import java.io.Serializable;

/**
 * Classe astratta che rappresenta un giocatore.
 */
public abstract class Player implements Serializable {

    private final String nome;
    private final Inventario inventario = new Inventario();

    /**
     * Costruttore di default.
     * @param nome il nome del giocatore
     */
    public Player(final String nome) {
        this.nome = nome;
    }

    /**
     * Metodo che restituisce il nome del giocatore.
     */
    public String getNome() {
        return this.nome;
    }

    /**
     * Metodo che restituisce l'inventario del giocatore.
     */
    public Inventario getInventario() {
        return this.inventario;
    }

    /**
     * Metodo astratto che sposta il giocatore verso una direzione.
     * @param direzione la direzione in cui spostarsi
     * @throws NodoInesistenteException se il nodo non esiste
     */
    public abstract void spostatiVerso(final Direzione direzione) throws NodoInesistenteException;
}
