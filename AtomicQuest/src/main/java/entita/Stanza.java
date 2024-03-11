
package entita;


public class Stanza extends Room {

    private final LivelloRadioattivita esposizRadioattiva;
    private boolean visitata = false;

    public Stanza(final String id, final String nome, final String descrizione,
               final String osserva, final String benvenuto, final LivelloRadioattivita esposizRadioattiva) {
        super(id, nome, descrizione, osserva, benvenuto);
        this.esposizRadioattiva = esposizRadioattiva;
    }

    public LivelloRadioattivita getEsposizRadioattiva() {
        return this.esposizRadioattiva;
    }

    public boolean isRadioattiva() {
        return this.esposizRadioattiva == LivelloRadioattivita.MEDIO || this.esposizRadioattiva == LivelloRadioattivita.ELEVATO;
    }

    public boolean isVisitata() {
        return this.visitata;
    }

    public void setVisitata(final boolean visitata) {
        this.visitata = visitata;
    }
}
