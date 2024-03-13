
package entita;


public class Giocatore extends Player {

    private Stanza stanzaCorrente;
    private final Mappa mappa = new Mappa();
    private boolean tutaIntegra = true;
    private boolean isUranioPreso = false;

    public Giocatore(final String nome) {
        super(nome);
        this.stanzaCorrente = this.mappa.getStanzaPerId(Stanze.CORTILE);
    }

    public boolean isTutaIntegra() {
        return this.tutaIntegra;
    }

    public void setTutaIntegra(final boolean tutaIntegra) {
        this.tutaIntegra = tutaIntegra;
    }

    public Stanza getStanzaCorrente() {
        return this.stanzaCorrente;
    }

    public void setStanzaCorrente(final Stanza stanzaCorrente) {
        this.stanzaCorrente = stanzaCorrente;
    }

    public Mappa getMappa() {
        return this.mappa;
    }

    public void spostatiVerso(final Direzione direzione) {
        this.stanzaCorrente = this.mappa.getStanzaPerDirezione(this.stanzaCorrente, direzione);
    }

    public boolean isUranioPreso() {
        return this.isUranioPreso;
    }

    public void setUranioPreso(final boolean isUranioPreso) {
        this.isUranioPreso = isUranioPreso;
    }
    
}
