
package entita;

import java.io.Serializable;

/**
 * Classe che rappresenta il giocatore.
 */
public class Giocatore extends Player implements Serializable {

    private Stanza stanzaCorrente;
    private final Mappa mappa = new Mappa();
    private boolean tutaIntegra = true;
    private boolean uranioPreso = false;
    private boolean codiceScoperto = false;

    /**
     * Costruttore di default.
     * @param nome il nome del giocatore
     */
    public Giocatore(final String nome) {
        super(nome);
        this.stanzaCorrente = this.mappa.getStanzaPerId(Stanze.CORTILE);
        this.stanzaCorrente.setVisitata(true);
    }

    /**
     * Metodo che restituisce true se la tuta è integra, false altrimenti.
     * @return tutaIntegra
     */
    public boolean isTutaIntegra() {
        return this.tutaIntegra;
    }

    /**
     * Metodo che imposta la tuta integra.
     * @param tutaIntegra true se la tuta è integra, false altrimenti
     */
    public void setTutaIntegra(final boolean tutaIntegra) {
        this.tutaIntegra = tutaIntegra;
    }

    /**
     * Metodo che restituisce la stanza corrente.
     * @return stanzaCorrente
     */
    public Stanza getStanzaCorrente() {
        return this.stanzaCorrente;
    }

    /**
     * Metodo che imposta la stanza corrente.
     * @param stanzaCorrente la stanza corrente
     */
    public void setStanzaCorrente(final Stanza stanzaCorrente) {
        this.stanzaCorrente = stanzaCorrente;
    }

    /**
     * Metodo che restituisce la mappa.
     * @return mappa
     */
    public Mappa getMappa() {
        return this.mappa;
    }

    /**
     * Metodo che sposta il giocatore verso una direzione.
     * @param direzione la direzione in cui spostarsi
     */
    public void spostatiVerso(final Direzione direzione) {
        this.stanzaCorrente = this.mappa.getStanzaPerDirezione(this.stanzaCorrente, direzione);
    }

    /**
     * Metodo che restituisce true se l'uranio è stato preso, false altrimenti.
     * @return uranioPreso
     */
    public boolean isUranioPreso() {
        return this.uranioPreso;
    }

    /**
     * Metodo che imposta l'uranio preso.
     * @param uranioPreso true se l'uranio è stato preso, false altrimenti
     */
    public void setUranioPreso(final boolean uranioPreso) {
        this.uranioPreso = uranioPreso;
    }

    /**
     * Metodo che restituisce true se il codice è stato scoperto, false altrimenti.
     * @return codiceScoperto
     */
    public boolean isCodiceScoperto() {
        return this.codiceScoperto;
    }

    /**
     * Metodo che imposta il codice scoperto.
     * @param codiceScoperto true se il codice è stato scoperto, false altrimenti
     */
    public void setCodiceScoperto(final boolean codiceScoperto) {
        this.codiceScoperto = codiceScoperto;
    }
    
}
