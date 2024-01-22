
package entita;

public class Giocatore extends Player {
    
    private boolean tutaIntegra = true;

    public Giocatore(String nome, Stanza stanzaCorrente) {
        super(nome, stanzaCorrente);
    }

    public boolean isTutaIntegra() {
        return tutaIntegra;
    }

    public void setTutaIntegra(boolean tutaIntegra) {
        this.tutaIntegra = tutaIntegra;
    }
    
}
