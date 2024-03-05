
package entita;

import grafo.NodoInesistenteException;


public abstract class Player {

    private final String nome;
    private final Inventario inventario = new Inventario();

    public Player(final String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return this.nome;
    }

    public Inventario getInventario() {
        return this.inventario;
    }

    public void prendiItem(final Item item) {
        this.inventario.aggiungiItem(item);
    }

    public void lasciaItem(final Item item) {
        this.inventario.rimuoviItem(item);
    }

    public abstract void spostatiVerso(final Direzione direzione) throws NodoInesistenteException;
}
