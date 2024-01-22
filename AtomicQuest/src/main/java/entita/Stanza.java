
package entita;


public class Stanza extends Room {
    
    
    private ModalitaDiAccesso modAccesso;
    private final LivelloRadioattivita esposizRadioattiva;
    private final boolean radioattiva;
    

    public Stanza(ModalitaDiAccesso modAccesso, LivelloRadioattivita esposizRadioattiva, 
            boolean radioattiva, String id, String nome, String descrizione, String osserva, String benvenuto) {
        super(id, nome, descrizione, osserva, benvenuto);
        this.modAccesso = modAccesso;
        this.esposizRadioattiva = esposizRadioattiva;
        this.radioattiva = radioattiva;
    }

    public ModalitaDiAccesso getModAccesso() {
        return this.modAccesso;
    }

    public void setModAccesso(ModalitaDiAccesso modAccesso) {
        this.modAccesso = modAccesso;
    }

    public LivelloRadioattivita getEsposizRadioattiva() {
        return this.esposizRadioattiva;
    }

    public boolean isRadioattiva() {
        return this.radioattiva;
    }
    
    

}
