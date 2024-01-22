
package entita;

import grafo.NodoInesistenteException;


public class Player {
    
    private final String nome;
    private final Inventario inventario = new Inventario();
    private final Mappa mappa = new Mappa();
    private Room stanzaCorrente;

    public Player(String nome, Stanza stanzaCorrente) {
        this.nome = nome;
        this.stanzaCorrente = stanzaCorrente;
    }

    public Room getStanzaCorrente() {
        return this.stanzaCorrente;
    }

    public void setStanzaCorrente(Room stanzaCorrente) {
        this.stanzaCorrente = stanzaCorrente;
    }

    public String getNome() {
        return nome;
    }

    public Inventario getInventario() {
        return inventario;
    }
    
    public void prendiItem(final Item item) {
        this.inventario.aggiungiItem(item);
    }
    
    public void lasciaItem(final Item item) {
        this.inventario.togliItem(item);
    }
    
    public void spostatiVerso(final Direzione direz) throws NodoInesistenteException {
        Room stanzaSucc = this.mappa.getStanzaPerDirezione(stanzaCorrente, direz);
        this.setStanzaCorrente(stanzaSucc);
    }
    
    //usaItem in eventuale Controller
}
